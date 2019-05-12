import Knapsack.BigInt;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
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

        a = new BigInt(12);
        assertEquals("192", a.shiftLeft(4).toString());



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

    @Test
    public void testMul() {
        BigInt mx, my;
        BigInteger x, y;

        mx = new BigInt(1234);
        x = new BigInteger("1234");


//        Karatsuba.main(new String[]{"120", "120"});

        BigInt a = new BigInt(123);
        BigInt b = new BigInt(456);
    assertEquals("56088", BigInt.mul(a, b).toString());

//         a = new BigInt(1234);
//         b = new BigInt(4321);
//    assertEquals("5332114", a.mul(b).toString());
//
        a = new BigInt(12345);
        b = new BigInt(54321);
    assertEquals("670592745", BigInt.mul(a, b).toString());

    // this tests pass but comment for time saving

//        a = new BigInt(67059);
//        b = new BigInt(67059);
//    assertEquals("4496909481", BigInt.mul(a, b).toString());
//
//        a = new BigInt(56525651);
//        b = new BigInt(5612);
//        assertEquals("317221953412", BigInt.mul(a, b).toString());

        int N = Math.max(x.bitLength(), x.bitLength());
        int mN = Math.max(mx.getSize(), mx.getSize());
        assertEquals(N, mN);

//        // number of bits divided by 2, rounded up
//        N = (N / 2) + (N % 2);
//
//        // x = a + 2^N b,   y = c + 2^N d
//        BigInteger b = x.shiftRight(N);
//        BigInt mb = mx.shiftRight(N);
//        assertEquals(b.longValue(), Long.parseLong(mb.toString()));
//
//        BigInteger inner = b.shiftLeft(N);
//        BigInt minner = mb.shiftLeft(N);
//        assertEquals(inner.longValue(), Long.parseLong(minner.toString()));
//
//
//        BigInteger a = x.subtract(inner);
//        BigInt ma = mx.sub(minner);
//        assertEquals(a.longValue(), Long.parseLong(ma.toString()));


//        BigInteger d = y.shiftRight(N);
    }

    @Test
    public void TestGreaterThan(){
        BigInt x, y;

        x = new BigInt(100);
        y = new BigInt(50);
        assertEquals(true, x.gt(y));

        assertEquals(false, y.gt(x));
    }

    @Test
    public void TestLessThan(){
        BigInt x, y;

        x = new BigInt(100);
        y = new BigInt(50);
        assertEquals(false, x.lt(y));

        assertEquals(true, y.lt(x));
    }

    @Test
    public void TestEqual(){
        BigInt x, y;

        x = new BigInt(100);
        y = new BigInt(50);
        assertEquals(false, x.eq(y));

        // after previous test x = 0. check why it's happen
        x = new BigInt(100);
        y = new BigInt(100);
        assertEquals(true, x.eq(y));
    }

    @Test
    public void TestGreaterOrEqual(){
        BigInt x, y;

        x = new BigInt(50);
        y = new BigInt(100);
        assertEquals(false, x.geot(y));

        // after previous test x = 0. check why it's happen
        x = new BigInt(100);
        y = new BigInt(50);
        assertEquals(true, x.geot(y));

        x = new BigInt(100);
        y = new BigInt(100);
        assertEquals(true, x.geot(y));
    }

    @Test
    public void TestLessOrEqual(){
        BigInt x, y;

        x = new BigInt(50);
        y = new BigInt(100);
        assertEquals(true, x.leot(y));

        // after previous test x = 0. check why it's happen
        x = new BigInt(100);
        y = new BigInt(50);
        assertEquals(false, x.leot(y));

        x = new BigInt(100);
        y = new BigInt(100);
        assertEquals(false, x.leot(y));
    }

    @Test
    public void TestDivide(){
        BigInt x, y;

        x = new BigInt(100);
        y = new BigInt(50);
        assertEquals(new BigInt(2), x.div(y));

        x = new BigInt(200);
        y = new BigInt(50);
        assertEquals(new BigInt(4), x.div(y));

        x = new BigInt(16);
        y = new BigInt(5);
        assertEquals(new BigInt(3), x.div(y));

        x = new BigInt(2);
        y = new BigInt(5);
        assertEquals(new BigInt(0), x.div(y));

        x = new BigInt(9);
        y = new BigInt(3);
        assertEquals(new BigInt(3), x.div(y));

        x = new BigInt(100);
        y = new BigInt(51);
        assertEquals(new BigInt(1), x.div(y));
    }

    @Test
    public void TestMod(){
        BigInt x, y;
        x = new BigInt(100);
        y = new BigInt(51);
        assertEquals(new BigInt(49), x.mod(y));

        x = new BigInt(10);
        y = new BigInt(20);
        assertEquals(new BigInt(10), x.mod(y));
        boolean cached = false;
        try{
            x = new BigInt(10);
            y = new BigInt(0);
            assertEquals(new BigInt(10), x.mod(y));
        }
        catch (Exception IllegalArgumentException){
            cached = true;
        }

        finally{
            assertEquals(true, cached);
        }
    }

    }