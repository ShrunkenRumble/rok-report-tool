package shrunken.rok.reportreader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Parser {
    private Map<String, String> hIDMap;

    public Parser() {
        hIDMap = new HashMap<String, String>();

        // Pull list of Cmdr name to HId mappings
        File file = new File("HIds.csv");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                hIDMap.put(data[1].trim(), data[0].trim());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private int toUInt(byte[] b, int off) {
        return ((b[off] & 0xFF)) |
               ((b[off + 1] & 0xFF) << 8) |
               ((b[off + 2] & 0xFF) << 16) |
               ((b[off + 3] & 0xFF) << 24);
    }

    public String getHeroID(String id) {
        return hIDMap.get(id) == null ? id : hIDMap.get(id);
    }

    private Map<String, List<Object>> parseReport(Path filePath) throws IOException {
        // Read the file into a byte array
        byte[] data = Files.readAllBytes(filePath);
        
        Map<String, List<Object>> reportContents = new LinkedHashMap<>();
        final int n = data.length;
        int pos = 0;
        
        // Each key-value pair is structured like this:
        // 0x04 | 4 bytes for key length | key as a string | 1 byte for value type | value
        // For keys with strings as values:
        // 0x04 | 4 bytes for key length | key as a string | 1 byte for value type | 4 bytes for value length | value as a string
        while (pos < n) {
            // Find the next key marker 0x04
            if (data[pos] != 0x04) {
                pos++;
                continue;
            }
            int start = pos;
            pos++;

            // Read key length 
            if (pos + 4 > n) break;
            int keyLen = toUInt(data, pos);
            pos += 4;

            // Read key bytes
            if (keyLen < 0 || pos + keyLen > n) break;
            String key = new String(data, pos, keyLen);
            pos += keyLen;

            // Next is value type
            if (pos >= n) break;
            // The "& 0xFF" converts byte to an unsigned integer value
            int type = data[pos++] & 0xFF;

            Object value;
            switch (type) {
                // Value is string
                case 0x04: {
                    if (pos + 4 > n) { 
                        pos = start + 1; 
                        continue; 
                    }
                    int strLen = toUInt(data, pos);
                    pos += 4;
                    if (strLen < 0 || pos + strLen > n) { 
                        pos = start + 1; 
                        continue; 
                    }
                    value = new String(data, pos, strLen);
                    pos += strLen;
                    break;
                }
                // Value is double
                case 0x03: {
                    if (pos + 8 > n) { 
                        pos = start + 1; 
                        continue; 
                    }
                    if (key.equals("time")) {
                        double time = ByteBuffer.wrap(data, pos, 8).order(ByteOrder.BIG_ENDIAN).getDouble();
                        long microSec = (long) time;
                        long seconds = microSec / 1_000_000L;
                        int nanoSec = (int) ((microSec % 1_000_000L) * 1_000);
                        Instant instant = Instant.ofEpochSecond(seconds, nanoSec);
                        value = instant;
                        pos += 8;
                        break;
                    } else {
                        double d = ByteBuffer.wrap(data, pos, 8).order(ByteOrder.BIG_ENDIAN).getDouble();
                        pos += 8;
                        value = d;
                        break;
                    }
                }
                // Value is boolean
                case 0x05: {
                    value = Boolean.TRUE;
                    break;
                }
                default: {
                    // Unknown type. Try resync at the next 0x04
                    value = "[Unknown type 0x" + Integer.toHexString(type) + " at 0x" + Integer.toHexString(pos - 1) + "]";
                    break;
                }
            }
            reportContents.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
        }
        return reportContents;
    }

    public ArrayList<Report> extractData(Path filePath, String filename) throws IOException {
        Map<String, List<Object>> reportContents = parseReport(filePath);

        // Number of battles in a report can be determined based on occurrences of "Max"
        int numBattles = (reportContents.get("Max").size() / 2) - 1;
        String reportID = filename;
        Report report;
        ArrayList<Report> reports = new ArrayList<>();


        int myUnits_sum = 0, myHeals_sum = 0, myDead_sum = 0, mySev_sum = 0, mySlight_sum = 0, myRemaining_sum = 0, myKP_sum = 0, myPowerLoss_sum = 0;
        int oppUnits_sum = 0, oppHeals_sum = 0, oppDead_sum = 0, oppSev_sum = 0, oppSlight_sum = 0, oppRemaining_sum = 0, oppKP_sum = 0, oppPowerLoss_sum = 0;
        
        String myPrimCmdr = getHeroID(reportContents.get("HId").get(0).toString());
        String mySecCmdr = getHeroID(reportContents.get("HId2").get(0).toString());

        // Verify heals, deaths, commander ids
        // Add data from each report to the report log
        for (int i = 0; i < numBattles; i++) {
            String oppPrimCmdr = getHeroID(reportContents.get("HId").get(1 + (i * 2)).toString());
            String oppSecCmdr = getHeroID(reportContents.get("HId2").get(1 + (i * 2)).toString());

            int myUnits = ((Double) reportContents.get("Max").get(1 + (i * 2))).intValue();
            int myHeals = ((Double) reportContents.get("Healing").get(1 + (i * 2))).intValue();
            int myDead = ((Double) reportContents.get("Death").get(1 + (i * 2))).intValue();
            int mySev = ((Double) reportContents.get("BadHurt").get(1 + (i * 2))).intValue();
            int mySlight = ((Double) reportContents.get("Hurt").get(1 + (i * 2))).intValue();
            int myRemaining = ((Double) reportContents.get("Cnt").get((reportContents.get("Cnt").size() - (numBattles * 2 + 2)) + (1 + (i * 2)))).intValue();
            int myKP = ((Double) reportContents.get("KillScore").get(1 + (i * 2))).intValue();
            int myPowerLoss = ((Double) reportContents.get("Power").get(1 + (i * 2))).intValue();
            
            int oppUnits = ((Double) reportContents.get("Max").get(i * 2)).intValue();
            int oppHeals = ((Double) reportContents.get("Healing").get(i * 2)).intValue();
            int oppDead = ((Double) reportContents.get("Death").get(i * 2)).intValue();
            int oppSev = ((Double) reportContents.get("BadHurt").get(i * 2)).intValue();
            int oppSlight = ((Double) reportContents.get("Hurt").get(i * 2)).intValue();
            int oppRemaining = ((Double) reportContents.get("Cnt").get((reportContents.get("Cnt").size() - (numBattles * 2 + 2)) + (i * 2))).intValue();
            int oppKP = ((Double) reportContents.get("KillScore").get(i * 2)).intValue();
            int oppPowerLoss = ((Double) reportContents.get("Power").get(i * 2)).intValue();

            myUnits_sum += myUnits;
            myHeals_sum += myHeals;
            myDead_sum += myDead;
            mySev_sum += mySev;
            mySlight_sum += mySlight;
            myRemaining_sum += myRemaining;
            myKP_sum += myKP;
            myPowerLoss_sum += myPowerLoss;

            oppUnits_sum += oppUnits;
            oppHeals_sum += oppHeals;
            oppDead_sum += oppDead;
            oppSev_sum += oppSev;
            oppSlight_sum += oppSlight;
            oppRemaining_sum += oppRemaining;
            oppKP_sum += oppKP;
            oppPowerLoss_sum += oppPowerLoss;

            report = new Report(myPrimCmdr, mySecCmdr, oppPrimCmdr, oppSecCmdr, myUnits, myHeals, myDead, 
                                mySev, mySlight, myRemaining, myKP, myPowerLoss, oppUnits, oppHeals, oppDead, 
                                oppSev, oppSlight, oppRemaining, oppKP, oppPowerLoss, reportID + "-" + String.valueOf(i), (Instant) reportContents.get("time").get(0));
            reports.add(report);
            
        }
        report = new Report(myPrimCmdr, mySecCmdr, "-", "-", myUnits_sum, myHeals_sum, myDead_sum, 
                            mySev_sum, mySlight_sum, myRemaining_sum, myKP_sum, myPowerLoss_sum, oppUnits_sum, oppHeals_sum, oppDead_sum, 
                            oppSev_sum, oppSlight_sum, oppRemaining_sum, oppKP_sum, oppPowerLoss_sum, reportID + "-SUM", (Instant) reportContents.get("time").get(0));
        reports.add(report);
        return reports;
    }

}