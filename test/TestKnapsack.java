import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestKnapsack {
    @Test
    public void testPublicKeyGenerator(){
        List<BigInt> privKey = new LinkedList<>(Arrays.asList(
                new BigInt(1),
                new BigInt(2),
                new BigInt(4),
                new BigInt(10),
                new BigInt(20),
                new BigInt(40)
        ));

        List<BigInt> expected = new LinkedList<>(Arrays.asList(
                new BigInt(53),
                new BigInt(106),
                new BigInt(92),
                new BigInt(50),
                new BigInt(100),
                new BigInt(80)
        ));

        List<BigInt> publicKey = Knapsack.createPublicKey(privKey, new BigInt(53), new BigInt(120));
// somewhere false is most significant bit!
        assertEquals(true, publicKey.toString().equals(expected.toString()));
    }
}
