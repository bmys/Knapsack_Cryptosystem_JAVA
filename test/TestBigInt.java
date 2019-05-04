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

    @Test
    public void testSum() {
        BigInt a = new BigInt(0);
        BigInt b = new BigInt(0);
        BigInt c = a.add(b);
        assertEquals( "0", c.toString());

        a = new BigInt(1);
        b = new BigInt(0);
        c = a.add(b);
        assertEquals("1", c.toString());

        a = new BigInt(1);
        b = new BigInt(1);
        c = a.add(b);
        assertEquals("2", c.toString());

        a = new BigInt(1);
        b = new BigInt(2);
        c = a.add(b);
        assertEquals("3", c.toString());

        a = new BigInt(2);
        b = new BigInt(5);
        c = a.add(b);
        assertEquals("7", c.toString());

        a = new BigInt(5);
        b = new BigInt(5);
        c = a.add(b);
        assertEquals("10", c.toString());

        a = new BigInt(12);
        b = new BigInt(5);
        c = a.add(b);
        assertEquals("17", c.toString());

         a = new BigInt(15);
         b = new BigInt(15);
         c = a.add(b);
        assertEquals("30", c.toString());

        a = new BigInt(-15);
        b = new BigInt(-15);
        c = a.add(b);
        assertEquals("-30", c.toString());
    }

    @Test
    public void testSub() {
        BigInt a, b, c;

        a = new BigInt(15);
        b = new BigInt(5);
        c = a.sub(b);
        assertEquals("10", c.toString());

        a = new BigInt(8);
        b = new BigInt(1);
        c = a.sub(b);
        assertEquals("7", c.toString());

        a = new BigInt(12);
        b = new BigInt(3);
        c = a.sub(b);
        assertEquals("9", c.toString());

        a = new BigInt(32);
        b = new BigInt(11);
        c = a.sub(b);
        assertEquals("21", c.toString());

        a = new BigInt(5);
        b = new BigInt(5);
        c = a.sub(b);
        assertEquals("0", c.toString());

        a = new BigInt(5);
        b = new BigInt(4);
        c = a.sub(b);
        assertEquals("1", c.toString());

        a = new BigInt(14);
        b = new BigInt(3);
        c = a.sub(b);
        assertEquals("11", c.toString());

        a = new BigInt(32);
        b = new BigInt(31);
        c = a.sub(b);
        assertEquals("1", c.toString());

        a = new BigInt(-15);
        b = new BigInt(-15);
        c = a.sub(b);
        assertEquals("0", c.toString());

        a = new BigInt(-15);
        b = new BigInt(15);
        c = a.sub(b);
        assertEquals("-30", c.toString());

        a = new BigInt(9);
        b = new BigInt(-8);
        c = a.sub(b);
        assertEquals("17", c.toString());

        a = new BigInt(0);
        b = new BigInt(-8);
        c = a.sub(b);
        assertEquals("8", c.toString());

//        a = new BigInt(5);
//        b = new BigInt(15);
//        c = a.sub(b);
//        assertEquals("-10", c.toString());
    }

    @Test
    public void testLeftShift() {
        BigInt a = new BigInt(6);

        assertEquals("12", a.shiftLeft(1).toString());
    }

    @Test
    public void testRightShift() {
        BigInt a = new BigInt(12);
        assertEquals("6", a.shiftRight(1).toString());

        a = new BigInt(5);
        assertEquals("0", a.shiftRight(10).toString());

        a = new BigInt(7);
        assertEquals("0", a.shiftRight(3).toString());
    }
    }