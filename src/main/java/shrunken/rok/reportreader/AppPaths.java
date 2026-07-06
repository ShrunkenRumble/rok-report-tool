package shrunken.rok.reportreader;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class AppPaths {
    private AppPaths() {}

    /** Directory where the app is running from (JAR/native). Falls back to CWD. */
    public static Path exeDir() {
        try {
            // Directory of the loaded class/JAR
            Path jar = Paths.get(AppPaths.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI());
            Path dir = jar;
            if (!java.nio.file.Files.isDirectory(dir)) dir = jar.getParent();
            if (dir != null) return dir.toAbsolutePath();
        } catch (URISyntaxException ignored) {}
        // Fallback: current working directory
        return Paths.get("").toAbsolutePath();
    }

    //public static Path dbPath() { return exeDir().resolve("parsed.db"); }
}
