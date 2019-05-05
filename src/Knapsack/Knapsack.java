package Knapsack;

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

    public static BigInt toPlain(BigInt cipher, BigInt n, BigInt m){
        return BigInt.mul(cipher, n).mod(m);
    }
}
