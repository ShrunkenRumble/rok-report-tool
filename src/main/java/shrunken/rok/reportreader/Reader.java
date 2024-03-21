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

    public ReportGroup compileReports(List<List<Integer>> data, List<List<String>> cmdrs) {
        ReportGroup reportGroup = new ReportGroup();

        boolean isMulti = cmdrs.get(0).size() == 4 ? false : true;

        if (!isMulti) {
            Report report = new Report();
            report.selfInitUnits(data.get(0).get(1), data.get(0).get(0));
            report.setHealing(data.get(1).get(1), data.get(1).get(0));
            report.setDead(data.get(2).get(1), data.get(2).get(0));
            report.setSevWound(data.get(3).get(1), data.get(3).get(0));
            report.setSlightWound(data.get(4).get(1), data.get(4).get(0));
            report.setRemaining(data.get(5).get(3), data.get(5).get(2));
            report.setPower(data.get(6).get(1), data.get(6).get(0));
            report.setKP(data.get(7).get(1), data.get(7).get(0));
            report.setSelfCmdrs(cmdrs.get(0).get(0), cmdrs.get(1).get(0));
            report.setOppCmdrs(cmdrs.get(0).get(2), cmdrs.get(1).get(2));
        } else {

        }

        return reportGroup;
    }

    public List<List<String>> extractData(String fileName) {
        final int numTerms = 10;
        int[] matchIndices = new int[numTerms];

        byte[][] searchTerms = {
                { 0x00, 0x4D, 0x61, 0x78 },                                 // Max = Initial Units
                { 0x48, 0x65, 0x61, 0x6C, 0x69, 0x6E, 0x67 },               // Healing
                { 0x44, 0x65, 0x61, 0x74, 0x68 },                           // Death
                { 0x42, 0x61, 0x64, 0x48, 0x75, 0x72, 0x74 },               // BadHurt
                { 0x00, 0x48, 0x75, 0x72, 0x74 },                           // Hurt
                { 0x00, 0x43, 0x6E, 0x74 },                                 // Cnt = Remaining Troops
                { 0x00, 0x50, 0x6F, 0x77, 0x65, 0x72 },                     // Power
                { 0x4B, 0x69, 0x6C, 0x6C, 0x53, 0x63, 0x6F, 0x72, 0x65 },   // KillScore
                { 0x48, 0x49, 0x64, 0x32 },                                 // HId2
                { 0x48, 0x49, 0x64, 0x03 }                                  // HId
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
                            if (i != 9) {
                                fileInputStream.skip(1);
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

        // Decode Data
        List<List<Integer>> data = new ArrayList<>();
        for (int i = 0; i < numTerms-2; i++) {
            data.add(new ArrayList<>());
        }
        List<List<String>> cmdrs = new ArrayList<>();
        cmdrs.add(new ArrayList<>());
        cmdrs.add(new ArrayList<>());

        for (int i = 0; i < numTerms; i++) {
            for (int j = 0; j < hexStrings.get(i).size(); j++) {
                if (i == 6) {
                    data.get(i).add(100);
                } else if (i == 8) {
                    cmdrs.get(1).add(hexStrings.get(i).get(j));
                } else if (i == 9) {
                    cmdrs.get(0).add(hexStrings.get(i).get(j));
                } else {
                    data.get(i).add(decodeNumeric(hexStrings.get(i).get(j)));
                }
            }
        }
        /*
        for (int i = 0; i < numTerms-2; i++) {
            for (int j = 0; j < data.get(i).size(); j++) {
                System.out.print(data.get(i).get(j) + " ");
            }
            System.out.println();
        }
        */

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

        return hexStrings;
    }

    public static void main(String[] args) {
        Reader reader = new Reader();
        //reader.extractData("Report_2.126292588170875758623");
        reader.extractData("test_2b.77597268171057404131");
    }
}