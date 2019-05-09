package Knapsack;

import com.google.common.base.Splitter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BinaryUtil {
    public static String getFileAsBitset(final String filePathString) {
        final Path filePath = Paths.get(filePathString);
        StringBuilder bitSetBuilder = new StringBuilder();
        byte[] fileData = null;

        try {
            fileData = Files.readAllBytes(filePath);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        for (byte eachByte : fileData) {
            for (int i = 7; i >= 0; i--) {
                boolean bit = ((byte) ((eachByte >> i) & 1)) == 1;
                bitSetBuilder.append((bit) ? '1' : '0');
            }
        }

        return bitSetBuilder.reverse().toString();
    }

    public static String convertTextToBitset(final String string) {
        StringBuilder bitSetBuilder = new StringBuilder();
        byte[] fileData = null;

        fileData = string.getBytes(StandardCharsets.UTF_8);

        for (byte eachByte : fileData) {
            for (int i = 7; i >= 0; i--) {
                boolean bit = ((byte) ((eachByte >> i) & 1)) == 1;
                bitSetBuilder.append((bit) ? '1' : '0');
            }
        }

        return bitSetBuilder.toString();
    }

    public static String convertBitsetToText(String input) {
        int inputModulo = input.length() % 8;

        if (inputModulo != 0) {
            input = padBitset(input, 8 - inputModulo);
        }

        byte[] byteArray = new byte[input.length() / 8];

        for (int i = 0; i < byteArray.length; i++) {
            for (int bit = 0; bit < 8; bit++) {
                if (input.charAt(i * 8 + bit) == '1') {
                    byteArray[i] |= (128 >> bit);
                }
            }
        }

        return new String(byteArray);
    }

    public static void writeBitsetToFile(final String filePathString, String input) {
        int inputModulo = input.length() % 8;

        if (inputModulo != 0) {
            input = padBitset(input, 8 - inputModulo);
        }

        final Path filePath = Paths.get(filePathString);
        byte[] byteArray = new byte[input.length() / 8];

        for (int i = 0; i < byteArray.length; i++) {
            for (int bit = 0; bit < 8; bit++) {
                if (input.charAt(i * 8 + bit) == '1') {
                    byteArray[i] |= (128 >> bit);
                }
            }
        }

        try {
            Files.write(filePath, byteArray);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static String padBitset(String bitset, int amount) {
        while (amount-- > 0) {
            bitset += '0';
        }

        return bitset;
    }

    public static String binaryStringToUTF8(String binaryString){
        StringBuilder stringBuilder = new StringBuilder();
        Iterable<String> chunks  = Splitter.fixedLength(8).split(binaryString);

        for (String chunk : chunks ) {
            if(chunk.length() < 8) continue;
            stringBuilder.append(convertBitsetToText(chunk));
        }

        return stringBuilder.toString();

    }
}