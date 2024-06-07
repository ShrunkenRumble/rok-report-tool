package shrunken.util;

import java.util.Map;

public class Decoder {

    public static int getPower(String hexStr) {
        return 10;
    }

    public static String getHeroID(String hexStr, Map<String, String> hIDMap) {
        return hIDMap.get(hexStr) == null ? hexStr : hIDMap.get(hexStr);
    }

    public static int getNumeric(String hexStr) {
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

}
