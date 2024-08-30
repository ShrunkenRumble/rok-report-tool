package shrunken.rok.reportreader;

import shrunken.util.Decoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import tech.tablesaw.api.*;


public class Reader {
    private Table reportLog;
    private Map<String, String> hIDMap;

    public Reader() {
        reportLog = Table.create("Reports",
                            StringColumn.create("MyPrimCmdr"), StringColumn.create("MySecCmdr"), IntColumn.create("MyUnits"), IntColumn.create("MyHeals"), IntColumn.create("MyDead"), IntColumn.create("MySev"),
                                    IntColumn.create("MySlight"), IntColumn.create("MyRemaining"),
                                    IntColumn.create("MyKP"), IntColumn.create("MyPower"),
                                    StringColumn.create("OppPrimCmdr"), StringColumn.create("OppSecCmdr"),
                                    IntColumn.create("OppUnits"), IntColumn.create("OppHeals"),
                                    IntColumn.create("OppDead"), IntColumn.create("OppSev"),
                                    IntColumn.create("OppSlight"), IntColumn.create("OppRemaining"),
                                    IntColumn.create("OppKP"), IntColumn.create("OppPower"), StringColumn.create("ID"));

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
            { 0x00, 0x4B, 0x69, 0x6C, 0x6C, 0x53, 0x63, 0x6F, 0x72, 0x65, 0x03 },   // KillScore
            { 0x00, 0x50, 0x6F, 0x77, 0x65, 0x72, 0x03 },                           // Power
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

    public void extractData(String filename, String reportID) {
        if (!(reportLog.columns("ID").contains(reportID))) {
            List<List<String>> hexStrings = extractHex(filename);
            int numReports = (hexStrings.get(9).size() / 2) - 1;

            // Add data from each report to the report log
            for (int i = 0; i < numReports; i++) {
                reportLog.stringColumn("MyPrimCmdr").append(Decoder.getHeroID(hexStrings.get(9).get(0), hIDMap));
                reportLog.stringColumn("MySecCmdr").append(Decoder.getHeroID(hexStrings.get(8).get(0), hIDMap));
                reportLog.stringColumn("OppPrimCmdr").append(
                        Decoder.getHeroID(hexStrings.get(9).get(2 + (i * 2)), hIDMap));
                reportLog.stringColumn("OppSecCmdr").append(
                        Decoder.getHeroID(hexStrings.get(8).get(2 + (i * 2)), hIDMap));
                reportLog.intColumn("MyPower").append(Decoder.getPower(hexStrings.get(6).get(1 + (i * 2))));
                reportLog.intColumn("OppPower").append(Decoder.getPower(hexStrings.get(6).get(i * 2)));
                for (int j = 0; j < 7; j++) {
                    reportLog.intColumn(j+2).append(Decoder.getNumeric(hexStrings.get(j).get(1 + (i * 2))));
                    reportLog.intColumn(j+12).append(Decoder.getNumeric(hexStrings.get(j).get(i * 2)));
                }
                reportLog.stringColumn("ID").append(reportID + "-"+ String.valueOf(i));
            }
        }
    }

    public void printTable() {
        System.out.println(reportLog.print());
    }

}