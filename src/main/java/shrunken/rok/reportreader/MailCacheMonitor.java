package shrunken.rok.reportreader;

import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class MailCacheMonitor implements AutoCloseable {
    private final Path sourceDir;
    private final Path targetDir;
    private final Duration stabilityWindow;
    private final Consumer<String> log;
    private final Consumer<Path> onStableFile;

    private WatchService watchService;
    private ExecutorService watcherExec;
    private ScheduledExecutorService scheduler;
    private final Map<Path, Long> lastSizes = new ConcurrentHashMap<>();
    private final Map<Path, ScheduledFuture<?>> stabilizers = new ConcurrentHashMap<>();
    private volatile boolean running;

    public MailCacheMonitor(Path sourceDir, Path targetDir, Duration stabilityWindow, Consumer<String> log, Consumer<Path> onStableFile) {
        this.sourceDir = Objects.requireNonNull(sourceDir);
        this.targetDir = Objects.requireNonNull(targetDir);
        this.stabilityWindow = stabilityWindow != null ? stabilityWindow : Duration.ofMillis(300);
        this.log = log != null ? log : s -> {};
        this.onStableFile = onStableFile != null ? onStableFile : p -> {};
    }

    public void start() throws IOException {
        if (running) return;
        Files.createDirectories(targetDir);
        watchService = FileSystems.getDefault().newWatchService();
        sourceDir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);

        watcherExec = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r, "mailcache-watcher"); t.setDaemon(true); return t;
        });
        scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "mailcache-stabilizer"); t.setDaemon(true); return t;
        });

        running = true;
        initialSweep();
        watcherExec.submit(this::loop);
        log.accept("Started monitoring: " + sourceDir);
    }

    public void stop() {
        running = false;
        try { if (watchService != null) watchService.close(); } catch (IOException ignored) {}
        if (watcherExec != null) watcherExec.shutdownNow();
        if (scheduler != null) scheduler.shutdownNow();
        log.accept("Stopped monitoring.");
    }

    @Override public void close() { stop(); }

    private void loop() {
        while (running) {
            WatchKey key;
            try { key = watchService.take(); }
            catch (InterruptedException | ClosedWatchServiceException e) { break; }

            for (WatchEvent<?> evt : key.pollEvents()) {
                if (evt.kind() == StandardWatchEventKinds.OVERFLOW) continue;
                Path name = ((WatchEvent<Path>) evt).context();
                Path file = sourceDir.resolve(name);
                if (Files.isRegularFile(file)) scheduleStabilityCheck(file);
            }
            key.reset();
        }
    }

    private void scheduleStabilityCheck(Path file) {
        ScheduledFuture<?> prev = stabilizers.remove(file);
        if (prev != null) prev.cancel(false);

        lastSizes.put(file, sizeOrNeg(file));
        ScheduledFuture<?> fut = scheduler.schedule(() -> {
            long now = sizeOrNeg(file);
            Long before = lastSizes.get(file);
            if (before != null && before == now && now >= 0) {
                try {
                    Path dst = targetDir.resolve(file.getFileName());
                    copyWithRetry(file, dst, 3);
                    log.accept("Copied: " + file.getFileName());
                    onStableFile.accept(file);
                } catch (Exception ex) {
                    log.accept("Copy failed: " + file.getFileName() + " :: " + ex.getMessage());
                } finally {
                    lastSizes.remove(file);
                    stabilizers.remove(file);
                }
            } else {
                scheduleStabilityCheck(file);
            }
        }, stabilityWindow.toMillis(), TimeUnit.MILLISECONDS);
        stabilizers.put(file, fut);
    }

    private void initialSweep() {
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(sourceDir)) {
            for (Path p : ds) if (Files.isRegularFile(p)) scheduleStabilityCheck(p);
        } catch (IOException e) {
            log.accept("Initial sweep error: " + e.getMessage());
        }
    }

    private static void copyWithRetry(Path src, Path dst, int attempts) throws IOException {
        IOException last = null;
        for (int i = 0; i < attempts; i++) {
            try {
                Files.copy(src, dst, REPLACE_EXISTING, COPY_ATTRIBUTES);
                return;
            } catch (IOException ex) {
                last = ex;
                try { Thread.sleep(50L * (i + 1)); } catch (InterruptedException ignored) {}
            }
        }
        throw last != null ? last : new IOException("copy failed");
    }

    private static long sizeOrNeg(Path p) {
        try { return Files.size(p); } catch (IOException e) { return -1L; }
    }
}
