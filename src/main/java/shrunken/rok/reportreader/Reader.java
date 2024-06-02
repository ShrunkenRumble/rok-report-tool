package shrunken.rok.reportreader;

import shrunken.rok.reportreader.Report;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Reader {

    public Reader() {}

    private int decodePower(String hexStr) {
        return 0;
    }

    private String decodeHeroID(String hexStr, Map<String, String> hIDMap) {
        return hIDMap.get(hexStr) == null ? hexStr : hIDMap.get(hexStr);
    }

    private int decodeNumeric(String hexStr) {
        long decNum = Long.parseLong(hexStr, 16);
        long base = 1072693248L;
        long increment = 1048576L;
        int cnt = 1;

        if (decNum == 0) {
            return 0;
        }

        while (base < decNum) {
            base += increment;
            cnt += 1;
            if ((cnt & (cnt - 1)) == 0) {
                increment = increment / 2;
            }
        }
        return cnt;
    }

    private List<List<String>> extractHex(String fileName) {
        final int numTerms = 10;
        int[] matchIndices = new int[numTerms];

        byte[][] searchTerms = {
            { 0x00, 0x4D, 0x61, 0x78, 0x03 },                                       // Max = Initial Units
            { 0x00, 0x48, 0x65, 0x61, 0x6C, 0x69, 0x6E, 0x67, 0x03 },               // Healing
            { 0x00, 0x44, 0x65, 0x61, 0x74, 0x68, 0x03 },                           // Death
            { 0x00, 0x42, 0x61, 0x64, 0x48, 0x75, 0x72, 0x74, 0x03 },               // BadHurt
            { 0x00, 0x48, 0x75, 0x72, 0x74, 0x03 },                                 // Hurt
            { 0x00, 0x47, 0x74, 0x4D, 0x61, 0x78, 0x03 },                           // GtMax ... Cnt = Remaining
            { 0x00, 0x50, 0x6F, 0x77, 0x65, 0x72, 0x03 },                           // Power
            { 0x00, 0x4B, 0x69, 0x6C, 0x6C, 0x53, 0x63, 0x6F, 0x72, 0x65, 0x03 },   // KillScore
            { 0x00, 0x48, 0x49, 0x64, 0x32, 0x03 },                                 // HId2
            { 0x00, 0x48, 0x49, 0x64, 0x03 }                                        // HId
        };

        List<List<String>> hexStrings = new ArrayList<>();
        for (int i = 0; i < numTerms; i++) {
            hexStrings.add(new ArrayList<>());
        }

        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            int character;
            while ((character = fileInputStream.read()) != -1) {
                for (int i = 0; i < numTerms; i++) {
                    if (character == (searchTerms[i][matchIndices[i]] & 0xFF)) {
                        if (++matchIndices[i] == searchTerms[i].length) {
                            if (i == 5) {
                                fileInputStream.skip(17);
                            }
                            byte[] buffer = new byte[4];
                            if (fileInputStream.read(buffer) == 4) {
                                String hexStr = String.format("%02X%02X%02X%02X",
                                                                buffer[0],
                                                                buffer[1],
                                                                buffer[2],
                                                                buffer[3]);

                                if (!"65647563".equals(hexStr) && !"6E74456E".equals(hexStr)) {
                                    hexStrings.get(i).add(hexStr);
                                }

                            } else {
                                System.out.println("Could not read the 4 bytes.");
                            }
                            matchIndices[i] = 0;
                        }
                    } else {
                        matchIndices[i] = 0;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hexStrings;
    }

    public MultiReport extractReports(String filename) {
        MultiReport reportGroup = new MultiReport();
        List<List<String>> hexStrings = extractHex(filename);
        int numReports = (hexStrings.get(9).size() / 2) - 1;
        Map<String, String> hIDMap = new HashMap<String, String>();

        // Pull list of Cmdr names to HIds
        File file = new File("HIds.csv");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                hIDMap.put(data[1].trim(), data[0].trim());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        // Populate report objects and add them to the report group
        Report report;
        for (int i = 0; i < numReports; i++) {
            report = new Report();
            report.setInitUnits(
                    decodeNumeric(hexStrings.get(0).get(1 + (i * 2))),
                    decodeNumeric(hexStrings.get(0).get(i * 2)));
            report.setHealing(
                    decodeNumeric(hexStrings.get(1).get(1 + (i * 2))),
                    decodeNumeric(hexStrings.get(1).get(i * 2)));
            report.setDead(
                    decodeNumeric(hexStrings.get(2).get(1 + (i * 2))),
                    decodeNumeric(hexStrings.get(2).get(i * 2)));
            report.setSevWound(
                    decodeNumeric(hexStrings.get(3).get(1 + (i * 2))),
                    decodeNumeric(hexStrings.get(3).get(i * 2)));
            report.setSlightWound(
                    decodeNumeric(hexStrings.get(4).get(1 + (i * 2))),
                    decodeNumeric(hexStrings.get(4).get(i * 2)));
            report.setRemaining(
                    decodeNumeric(hexStrings.get(5).get(1 + (i * 2))),
                    decodeNumeric(hexStrings.get(5).get(i * 2)));
            report.setPower(
                    decodePower(hexStrings.get(6).get(1 + (i * 2))),
                    decodePower(hexStrings.get(6).get(i * 2)));
            report.setKP(
                    decodeNumeric(hexStrings.get(7).get(1 + (i * 2))),
                    decodeNumeric(hexStrings.get(7).get(i * 2)));
            report.setSelfCmdrs(
                    decodeHeroID(hexStrings.get(9).get(0), hIDMap),
                    decodeHeroID(hexStrings.get(8).get(0), hIDMap));
            report.setOppCmdrs(
                    decodeHeroID(hexStrings.get(9).get(2 + (i * 2)), hIDMap),
                    decodeHeroID(hexStrings.get(8).get(2 + (i * 2)), hIDMap));
            reportGroup.addReport(report);
        }

        reportGroup.printReportGroup();
        return reportGroup;
    }

    /*  Information to scrape:
            Skill Levels
            Player names
            Player IDs
            Report Data/Time
            Equipment
            Armamemts
    */
    public static void main(String[] args) {
        Reader reader = new Reader();
        //reader.extractReports("test_1.77586448171057379031");
        //reader.extractReports("test_2.5100208171152867331");
        //reader.extractReports("test_3.98785259171110544431");
        //reader.extractReports("test_4.98798611171110585131");
        //reader.extractReports("Persistent.Mail.20240523_165632_6044165171648339223");
        reader.extractReports("Persistent.Mail.20240523_173145_6131792171648550523");
    }
}