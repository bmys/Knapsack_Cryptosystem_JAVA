import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Knapsack {

    public static List<BigInt> generateSuperIncreasingSeq(BigInt start, int count){
        BigInt totalSum = start;
        Random rand = new Random();
        List<BigInt> sequence = new LinkedList<>();

    for (int i = 0; i < count; i++) {
        BigInt newValue = totalSum.add(new BigInt(rand.nextLong()));
        sequence.add(newValue);
        totalSum = totalSum.add(newValue);
    }
        return new LinkedList<>();
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


}
