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

    public List<List<Integer>> extract(String fileName) {
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
        List<List<Integer>> data = new ArrayList<>();

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
                                System.out.println("Could not read the 4 bytes following 'BadHurt'.");
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

        for (int i = 0; i < numTerms; i++) {
            for (int j = 0; j < hexStrings.get(i).size(); j++) {
                if (i != 6 && i != 8 && i != 9) {
                    System.out.print(decodeNumeric(hexStrings.get(i).get(j)) + " ");
                } else {
                    System.out.print(hexStrings.get(i).get(j) + " ");
                }
            }
            System.out.println();
        }

        return data;
    }

    public static void main(String[] args) {
        Reader reader = new Reader();
        reader.extract("Report_2.126292588170875758623");
    }
}