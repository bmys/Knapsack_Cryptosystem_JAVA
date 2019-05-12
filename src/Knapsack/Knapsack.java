package Knapsack;

import com.google.common.base.Splitter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Knapsack {

    public static List<BigInt> generateSuperIncreasingSeq(BigInt start, int count){
        BigInt totalSum = start;
        Random rand = new Random();
        List<BigInt> sequence = new LinkedList<>();

    for (int i = 0; i < count; i++) {
        BigInt newValue = totalSum.add(new BigInt(rand.nextInt(250)));
        sequence.add(newValue);
        totalSum = totalSum.add(newValue);
    }
        return sequence;
    }

    public static List<BigInt> createPublicKey(List<BigInt> privateKey, BigInt n, BigInt m){
        BigInt sum = new BigInt(0);
        List<BigInt> sequence = new LinkedList<>();

        for (BigInt pk : privateKey) {
            sum = sum.add(pk);
        }

        if(sum.geot(m)){
            throw new IllegalArgumentException("m value should be greater than sum of private key");
        }

        if (areCoprimes(n, m)) {
            throw new IllegalArgumentException("n and m have to be coprimes!");
        }

        for (BigInt pk : privateKey) {
            BigInt newValue = BigInt.mul(pk, n).mod(m);
            sequence.add(newValue);
        }

        return sequence;
    }

    public static BigInt inverseMod(BigInt n, BigInt m){
        BigInt i = new BigInt(1);
        BigInt one = new BigInt(1);

        while(true){
            BigInt value = BigInt.mul(n, i).mod(m);
            if(value.eq(one)){
                return i;
            }
            else{
                i = i.inc();
            }
        }
    }

    public static BigInt toCipher(String msg, List<BigInt> publicKey){
        BigInt sum = new BigInt(0);
        int i =0;
        for(char ch: msg.toCharArray()){
            if(ch == '1'){
                sum = sum.add(publicKey.get(i));
            }
            i++;
        }
        return sum;
    }

    public static BigInt toPlain(BigInt cipher, BigInt inverseMod, BigInt m){
        return BigInt.mul(cipher, inverseMod).mod(m);
    }

    public static String toBinaryPlain(BigInt cipher, List<BigInt> privateKey){
        BigInt zero = new BigInt(0);
        String k = "";

        StringBuilder stringBuilder = new StringBuilder();
        List<BigInt> newKey = new LinkedList<>(privateKey);
        Collections.reverse(newKey);

        for(BigInt i: newKey){
            //if(cipher.eq(zero)) break;
            if(cipher.geot(i)){
                stringBuilder.append('1');
                cipher = cipher.sub(i);
            }
            else{
                stringBuilder.append('0');
            }
        }
        stringBuilder.reverse();
//        int finish = cipher.getArr().size() - stringBuilder.length();
//        for (int n = 0; n < finish; n++){
//            stringBuilder.append('0');
//        }
        return stringBuilder.toString();
    }

    public static List<BigInt> encryptString(String str, List<BigInt> pubkey){
        int keySize = pubkey.size();

        List<BigInt> cipher = new LinkedList<>();

        Iterable<String> chunks  = Splitter.fixedLength(keySize).split(str);

        for(String chunk: chunks){
            cipher.add(toCipher(chunk, pubkey));
        }

    return cipher;
    }

    public static String decryptString(List<BigInt> cipher, List<BigInt> privKey, BigInt inverseMod, BigInt m){
        List<BigInt> binary = decryptString(cipher, inverseMod, m);

        StringBuilder stringBuilder = new StringBuilder();
        for (BigInt bi : binary) {
            stringBuilder.append(toBinaryPlain(bi, privKey));
            stringBuilder.append("  ");
        }

    return stringBuilder.toString();
}

    public static List<BigInt> decryptString(List<BigInt> cipher, BigInt inverseMod, BigInt m){
        List<BigInt> result = new LinkedList<>();

        for (BigInt bigInt : cipher) {
            BigInt bi = toPlain(bigInt, inverseMod, m);
            result.add(bi);
        }

        return result;
    }

    public static boolean areCoprimes(BigInt a, BigInt b) {
        // need to clone due to mutability
        BigInt f = (BigInt) a.clone();
        BigInt s = (BigInt) b.clone();

        return !f.eq(s) && BigInt.gcd(f, s).eq(new BigInt(1));
    }
}
