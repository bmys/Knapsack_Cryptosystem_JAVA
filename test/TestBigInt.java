import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBigInt {

    @Test
    public void testLongToBigInt() {
       BigInt bigInt = new BigInt(1);
       assertEquals(bigInt.getArr().get(0), true);
       assertEquals(bigInt.getArr().size(), 1);

       bigInt = new BigInt(0);
       assertEquals(bigInt.getArr().get(0), false);
       assertEquals(bigInt.getArr().size(), 1);

        bigInt = new BigInt(2);
        assertEquals(bigInt.getArr().get(0), false);
        assertEquals(bigInt.getArr().get(1), true);
        assertEquals(bigInt.getArr().size(), 2);

        bigInt = new BigInt(6);
        assertEquals(bigInt.getArr().get(0), false);
        assertEquals(bigInt.getArr().get(1), true);
        assertEquals(bigInt.getArr().get(2), true);
        assertEquals(bigInt.getArr().size(), 3);

        bigInt = new BigInt(8);
        List<Boolean> testArr = Arrays.asList(false, false, false, true);
        assertEquals(bigInt.getArr(), testArr);
        assertEquals(bigInt.getArr().size(), 4);

        bigInt = new BigInt(9);
        testArr = Arrays.asList(true, false, false, true);
        assertEquals(bigInt.getArr(), testArr);
        assertEquals(bigInt.getArr().size(), 4);

        bigInt = new BigInt(15);
        testArr = Arrays.asList(true, true, true, true);
        assertEquals(bigInt.getArr(), testArr);
        assertEquals(bigInt.getArr().size(), 4);

        bigInt = new BigInt(-15);
        assertEquals(bigInt.isSign(), true);
        assertEquals(bigInt.getArr(), testArr);
        assertEquals(bigInt.getArr().size(), 4);
    }
}