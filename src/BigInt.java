import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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
    this.sign = sign;
  }

  private BigInt(BigInt other) {
        this.arr = other.arr;
        this.sign = other.sign;
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
          return add(other);
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
          res.add(false);
      }

      // (1, 1)
      else if(a && b){
          res.add(false);
      }

      // (0, 1)
      else{
          if(i >= size){
              return cutFillAndChangeSign(i, other);
          }

          else{
              // borrow
              int idx = min.arr.subList(i, min.arr.size()).indexOf(true);

              if(idx == -1){
                  return cutFillAndChangeSign(i, other);
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

    // TODO: implement
    return new BigInt(res, true);
  }

  private BigInt cutFillAndChangeSign(int i, BigInt num){
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

      newArr.addAll(num.arr.subList(i, arr.size()-1));

      return new BigInt(newArr, true);
  }

  private long getSize() {
    return size;
  }

  public List<Boolean> getArr() {
    return arr;
  }

    public void setSign(boolean sign) {
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

    String si = sign ? "-" : "";
    return si + res;
  }
}