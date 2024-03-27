package shrunken.rok.reportreader;

import shrunken.rok.reportreader.Report;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    public Reader() {}

    private String decodeHeroID(String hexStr) {
        // Implement decoding logic here
        return ""; // Return a decoded string based on the hex string
    }

    private int decodePower(String hexStr) {
        return 0;
    }

    private int decodeNumeric(String hexStr) {
        long decNum = Long.parseLong(hexStr, 16);
        if (decNum == 0) {
            return 0;
        }

        long base = 1072693248L;
        long increment = 1048576L;
        int step = 1;

        while (base < decNum) {
            base += increment;
            step += 1;
            if ((step & (step - 1)) == 0) {
                increment = increment / 2;
            }
        }
        return step;
    }

    public ReportGroup extractReports(String filename) {
        ReportGroup reportGroup = new ReportGroup();
        List<List<String>> hexStrings = extractHex(filename);

        int numReports = (hexStrings.get(9).size() / 2) - 1;
        Report report;
        for (int i = 0; i < numReports; i++) {
            report = new Report();
            report.setInitUnits(
                decodeNumeric(hexStrings.get(0).get(1+(i*2))),
                decodeNumeric(hexStrings.get(0).get(i*2)));
            report.setHealing(
                decodeNumeric(hexStrings.get(1).get(1+(i*2))),
                decodeNumeric(hexStrings.get(1).get(i*2)));
            report.setDead(
                decodeNumeric(hexStrings.get(2).get(1+(i*2))),
                decodeNumeric(hexStrings.get(2).get(i*2)));
            report.setSevWound(
                decodeNumeric(hexStrings.get(3).get(1+(i*2))),
                decodeNumeric(hexStrings.get(3).get(i*2)));
            report.setSlightWound(
                decodeNumeric(hexStrings.get(4).get(1+(i*2))),
                decodeNumeric(hexStrings.get(4).get(i*2)));
            report.setRemaining(
                decodeNumeric(hexStrings.get(5).get(1+(i*2))),
                decodeNumeric(hexStrings.get(5).get(i*2)));
            report.setPower(
                decodePower(hexStrings.get(6).get(1+(i*2))),
                decodePower(hexStrings.get(6).get(i*2)));
            report.setKP(
                decodeNumeric(hexStrings.get(7).get(1+(i*2))),
                decodeNumeric(hexStrings.get(7).get(i*2)));
            report.setSelfCmdrs(
                hexStrings.get(9).get(0),
                hexStrings.get(8).get(0));
            report.setOppCmdrs(
                hexStrings.get(9).get(2+(i*2)),
                hexStrings.get(8).get(2+(i*2)));
            reportGroup.addReport(report);
        }

        reportGroup.printReportGroup();
        return reportGroup;
    }

    public List<List<String>> extractHex(String fileName) {
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

        /*
        for (int i = 0; i < numTerms; i++) {
            if (i == 0) {
                System.out.print("Units: ");
            }
            if (i == 1) {
                System.out.print("Healing: ");
            }
            if (i == 2) {
                System.out.print("Dead: ");
            }
            if (i == 3) {
                System.out.print("Sev Wound: ");
            }
            if (i == 4) {
                System.out.print("Slight Wound: ");
            }
            if (i == 5) {
                System.out.print("Remaining: ");
            }
            if (i == 6) {
                System.out.print("Power: ");
            }
            if (i == 7) {
                System.out.print("KP: ");
            }
            if (i == 8) {
                System.out.print("HId2: ");
            }
            if (i == 9) {
                System.out.print("HId: ");
            }
            for (int j = 0; j < hexStrings.get(i).size(); j++) {
                if (i != 6 && i != 8 && i != 9) {
                    System.out.print(decodeNumeric(hexStrings.get(i).get(j)) + " ");
                } else {
                    System.out.print(hexStrings.get(i).get(j) + " ");
                }
            }
            System.out.println();
        }
        */
        return hexStrings;
    }

    public static void main(String[] args) {
        Reader reader = new Reader();
        //reader.extractReports("test_1.77586448171057379031");
        //reader.extractReports("test_2.5100208171152867331");
        //reader.extractReports("test_3.98785259171110544431");
        reader.extractReports("test_4.98798611171110585131");
    }
}