package Knapsack;

import com.google.common.base.Splitter;

import java.util.LinkedList;
import java.util.List;

public class FileEncrypt {
    public static String encryptFile(String fileName, List<BigInt> publicKey){
        String fileBinary = BinaryUtil.getFileAsBitset(fileName);
        // list of cipher big ints
        List<BigInt> ll = Knapsack.encryptString(fileBinary, publicKey);

        StringBuilder stringBuilder = new StringBuilder();

        int padding = calculatePadding(publicKey);

        for (BigInt bi : ll) {
            StringBuilder innerBuilder = new StringBuilder();
            for (Boolean x : bi.getArr()) {
                innerBuilder.append(x ? "1" : "0");
            }
            // add padding
            int size = innerBuilder.length();
            for(int i = 0; i < (padding - size); i++){
                innerBuilder.append("0");
            }
            stringBuilder.append(innerBuilder);
        }

        return stringBuilder.toString();
    }

    public static String decryptFile(String fileName, List<BigInt> privateKey, BigInt n, BigInt m, int chunkSize){
        String fileBinary = BinaryUtil.getFileAsBitset(fileName);
        fileBinary = new StringBuilder(fileBinary).reverse().toString();
        List<BigInt> bigIntCipher = new LinkedList<>();

        BigInt invMod = Knapsack.inverseMod(n, m);


        Iterable<String> chunks  = Splitter.fixedLength(chunkSize).split(fileBinary);
        for(String chunk: chunks){
//          System.out.println(chunk);
          String k = new StringBuilder(chunk).reverse().toString();
            bigIntCipher.add(new BigInt(Long.parseLong(k, 2)));
        }

        List<BigInt> decrypted = new LinkedList<>();
        for (BigInt bi: bigIntCipher) {
            decrypted.add(Knapsack.toPlain(bi, invMod, m));
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (BigInt bi : decrypted) {
            stringBuilder.append(Knapsack.toBinaryPlain(bi, privateKey));
        }

        int strlen = stringBuilder.length();

        return stringBuilder.substring(0, strlen - (strlen % 8));
    }




    public static int calculatePadding(List<BigInt> publicKey){
        BigInt accumulator = new BigInt(0);
        for(BigInt keyValue: publicKey){
            accumulator = accumulator.add(keyValue);
        }
        return accumulator.getArr().size();
    }
}
