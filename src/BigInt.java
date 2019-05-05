import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class BigInt {
  private List<Boolean> arr = new LinkedList<>();
  private boolean sign = false;
  private int size = 0;

  BigInt(long number) {
    if (number < 0) {
      this.sign = true;
      number = Math.abs(number);
    }

    if (number == 0) {
      arr.add(false);
      return;
    }

    while (true) {
      arr.add((number % 2) == 1);
      if ((number /= 2) == 0) {
        break;
      }
    }

    this.size = arr.size();
  }

  private BigInt(List<Boolean> arr, boolean sign) {
    this.arr = arr;
    this.size = this.arr.size();
    this.sign = sign;
  }

  private BigInt(BigInt other) {
        this.arr = other.arr;
        this.sign = other.sign;
        this.size = this.arr.size();
    }

  public BigInt add(BigInt other) {

    // (this = negative, other = positive)
    if (sign && !other.isSign()) {
      return other.sub(this);
    }

    // (this = positive, other = negative)
    if (!sign && other.isSign()) {
      return sub(other);
    }

    // (this = positive, other = positive) or (this = negative, other = negative)
    Boolean newSign = sign && other.isSign();

    List<Boolean> res = new LinkedList<>();

    // get longest array size
    long newSize = (this.size > other.getSize()) ? this.size : other.getSize();

    boolean carry = false;

    for (int i = 0; i < newSize; i++) {
      // if index out of range it returns false, no padding handling
      boolean a = getEl(i);
      boolean b = other.getEl(i);

      if (carry) {

        // (0, 0)
        if (!a && !b) {
          res.add(true);
          carry = false;
        }

        // (1, 1)
        else if (a && b) {
          res.add(true);
          // carry not changed, still true.
        }

        // (1, 0) or (0, 1)
        else {
          res.add(false);
          // carry not changed, still true.
        }
      }
      // Carry is false
      else {

        // (0, 0)
        if (!a && !b) {
          res.add(false);
          // carry not changed, still false.
        }

        // (1, 1)
        else if (a && b) {
          res.add(false);
          carry = true;
        }

        // (1, 0) or (0, 1)
        else {
          res.add(true);
          // carry not changed, still false.
        }
      }

      // if carry left after addition

    }
      if (carry) {
          res.add(true);
      }
    return new BigInt(res, newSign);
  }

  public BigInt sub(BigInt other) {

      // (this = positive, other = negative) :  1 -(-2) = 1 + 2
      if (!sign && other.isSign()) {
          BigInt newOther = new BigInt(other);
          newOther.setSign(false);
          return add(newOther);
      }

      // (this = negative, other = positive) : -1 - 2 = -1 + (-2)
      if (sign && !other.isSign()) {
          BigInt newOther = new BigInt(other);
          newOther.setSign(true);
          return add(newOther);
      }

      // not sure if this is necessary
      // (this = negative, other = negative) : -1 - (-2) = -1 + 2 => 2 - 1
      if (sign && !other.isSign()) {
          BigInt newOther = new BigInt(other);
          newOther.setSign(false);
          return newOther.sub(this);
      }

      List<Boolean> res = new LinkedList<>();

      // get longest array size
      long newSize = (this.size > other.getSize()) ? this.size : other.getSize();

      // copy of minuend
      BigInt min = new BigInt(this);

    for (int i = 0; i < newSize; i++) {
      // if index out of range it returns false, no padding handling
      boolean a = min.getEl(i);
      boolean b = other.getEl(i);

      // (0, 0)
      if(!a && !b ){
          res.add(false);
      }

      // (1, 0)
      else if(a && !b){
          res.add(true);
      }

      // (1, 1)
      else if(a && b){
          res.add(false);
      }

      // (0, 1)
      else{
          if(i >= size){
//              return cutFillAndChangeSign(i, other);
              BigInt overFlow = new BigInt(other).sub(new BigInt(other));
              overFlow.setSign(true);
              return overFlow;
          }

          else{
              // borrow
              List k = min.arr.subList(i, min.arr.size());
              int idx = k.indexOf(true) + i;

              if(idx == -1){
//                  return cutFillAndChangeSign(i, other);
                  BigInt overFlow = new BigInt(other).sub(new BigInt(other));
                  overFlow.setSign(true);
                  return overFlow;
              }
              // 10 - 01 = 1
              res.add(true);
          // change all min elements between i and borrow idx to 1

              for (int j = i+1; j < idx; j++){
                min.setEl(j, true);
              }

              // set borrow idx to false
              min.setEl(idx, false);
          }
      }
    }

    // TODO: add negative result. example: a < b => a - b = -c
      res = res.subList(0, res.lastIndexOf(true)+1);
    return new BigInt(res, false);
  }

  public BigInt naiveMul(BigInt other) {
      // TODO: implement
      return new BigInt(5);
  }

  public static BigInt mul(BigInt first, BigInt other){
      int N = Math.max(first.size, other.size);
      if (N <= 4){
          long number = Long.parseLong(first.toString()) * Long.parseLong(other.toString());
          return new BigInt(number);
      }

      // number of bits divided by 2, rounded up
      N = (N / 2) + (N % 2);

      // x = a + 2^N b,   y = c + 2^N d
      BigInt b = first.shiftRight(N);
      BigInt a = first.sub(b.shiftLeft(N));
      BigInt d = other.shiftRight(N);
      BigInt c = other.sub(d.shiftLeft(N));

      // compute sub-expressions
      BigInt ac    = mul(a, c);
      BigInt bd    = mul(b, d);
      BigInt abcd  = mul(a.add(b), c.add(d));

      return ac.add(abcd.sub(ac).sub(bd).shiftLeft(N)).add(bd.shiftLeft(2*N));
  }

  public BigInt div(BigInt other){
      return divide(other, false);
  }

  public BigInt mod(BigInt other){
        return divide(other, true);
    }

  private BigInt divide(BigInt other, boolean mod){

      if(other.toString().equals("0")){
          throw new IllegalArgumentException("Zero Division!");
      }

      // if other larger
      if(this.lt(other)){
          if(mod) return new BigInt(this);
          else return new BigInt(0);
      }

      // zero
      BigInt counter = new BigInt(0);

      BigInt dividend = new BigInt(this);
      BigInt divider = new BigInt(other);

      while(dividend.geot(divider)){
          dividend = dividend.sub(divider);
          counter = counter.inc();
      }

      if(mod) return dividend;

      return counter;
  }

  private BigInt cutFillAndChangeSign(int i, BigInt num){
      // not working don.t use it.
        /*
          example: 5 - 18 => 0 - 13 = -13
          if borrow needed and size of minuend reached
          we sub current result from subtrahend and change its sign.
          sub of subtrahend way is just cut off processed bits;
       */

      // processed elements become false
      Boolean[] padding = new Boolean[i-1];
      Arrays.fill(padding, Boolean.FALSE);

      List<Boolean> newArr = new LinkedList<>(Arrays.asList(padding));

      newArr.addAll(num.arr.subList(newArr.size(), num.arr.size()));

      return new BigInt(newArr, true);
  }

  public BigInt shiftLeft(int bits){
      List<Boolean> narr = new LinkedList<>();
      Boolean[] padding = new Boolean[bits];
      Arrays.fill(padding, Boolean.FALSE);
      narr.addAll(Arrays.asList(padding));
      narr.addAll(this.arr);
      return new BigInt(narr, this.sign);
  }

  public BigInt shiftRight(int bits){
        List<Boolean> narr = new LinkedList<>();

        if(bits >= size){
            return new BigInt(0);
        }

//        Boolean[] padding = new Boolean[bits];
//        Arrays.fill(padding, Boolean.FALSE);
//        narr.addAll(this.arr.subList(padding.length, arr.size()));
//        narr.addAll(Arrays.asList(padding));
        narr.addAll(this.arr.subList(bits, arr.size()));
        return new BigInt(narr, this.sign);
    }

    private static int lastTrue(BigInt bigInt){
      return bigInt.arr.lastIndexOf(true);
    }

    public boolean gt(BigInt other){
      // no sign support yet

        if(size != other.size){
            return lastTrue(this) > lastTrue(other);
        }

        else{
      for (int i = size - 1; i >= 0; i--) {
          boolean a = this.getEl(i);
          boolean b = other.getEl(i);
          if(a ^ b){
              return a;
          }
      }
        }
        return false;
    }

    public boolean lt(BigInt other){
        // no sign support yet
        return !gt(other);
    }

    public boolean geot(BigInt other){
        if(arr.equals(other.arr)) return true;
        return gt(other);
    }

    public boolean leot(BigInt other){
        // no sign support yet
        return !geot(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BigInt bigInt = (BigInt) o;
        // delete size check size ||     == bigInt.size &&
        return sign == bigInt.sign &&
                Objects.equals(arr, bigInt.arr);
    }

    @Override
    public int hashCode() {

        return Objects.hash(arr, sign, size);
    }

    public boolean eq(BigInt other){
        String res = sub(other).toString();
        return res.equals("0");
    }

    public BigInt inc(){
        BigInt res = new BigInt(this);
        BigInt one = new BigInt(1);
        return res.add(one);
    }


  public int getSize() {
    return size;
  }

  public List<Boolean> getArr() {
    return arr;
  }

  private void setSign(boolean sign) {
        this.sign = sign;
  }

  public boolean isSign() {
    return sign;
  }

  private boolean getEl(int i) {
    if (i >= size) {
      return false;
    } else {
      return arr.get(i);
    }
  }

  private void setEl(int i, boolean value) {

      if (i >= size) {
          // fill with zeros
          int paddingSize = i - size;

          Boolean[] padding = new Boolean[paddingSize];
          Arrays.fill(padding, Boolean.FALSE);
          arr.addAll(Arrays.asList(padding));
          arr.set(i, value);
          size = arr.size();

      } else {
          arr.set(i, value);
      }
    }

  @Override
  public String toString() {
    long res = 0;
    long num = 1;

    for (Boolean bln : arr) {
      if (bln) res += num;
      num *= 2;
    }
//    sometimes return -0 temporary fix
      if(res == 0) return "0";

    String si = sign ? "-" : "";
    return si + res;
  }
}