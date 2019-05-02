import java.util.LinkedList;
import java.util.List;

public class BigInt {
    private List<Boolean> arr = new LinkedList<>();
    private boolean sign = false;
    private long size = 0;

    BigInt(long number) {
        if(number < 0){
            this.sign = true;
            number = Math.abs(number);
        }

        if(number == 0){
            arr.add(false);
            return;
        }

        while(true){
            arr.add( (number % 2) == 1 );
            if((number /= 2) == 0) {
                break;
            }
        }

        this.size = arr.size();
    }

    BigInt(List<Boolean> arr, boolean sign){
        this.arr = arr;
        this.sign = sign;
    }

    public BigInt add(BigInt other){
        List<Boolean> res = new LinkedList<>();

        long newSize = (this.size > other.getSize()) ? this.size : other.getSize();

        boolean carry = false;

        for (int i = 0; i < newSize; i++) {
            boolean a = getEl(i);
            boolean b = other.getEl(i);

            if(carry){
                // (0, 0)
                if(!a && !b){
                    res.add(true);
                    carry = false;
                }
                // (1, 1)
                else if(a && b){
                    res.add(true);
                    // carry not changed, still true.
                }
                // (1, 0) or (0, 1)
                else{
                    res.add(false);
                    // carry not changed, still true.
                }
            }
            // Carry is false
            else{
                // (0, 0)
                if(!a && !b){
                    res.add(false);
                    // carry not changed, still false.
                }
                // (1, 1)
                else if(a && b){
                    res.add(false);
                    carry = true;
                }
                // (1, 0) or (0, 1)
                else{
                    res.add(true);
                    // carry not changed, still false.
                }
            }

            if(carry){
                res.add(true);
            }
        }

        return new BigInt(res, true);
    }
    
    public long getSize() {
        return size;
    }

    public List<Boolean> getArr() {
        return arr;
    }

    public boolean isSign() {
        return sign;
    }

    public boolean getEl(int i) {
        if(i >= size){
            return false;
        }
        else{
            return arr.get(i);
        }
    }


    @Override
    public String toString() {
        long res = 0;
        long num = 1;

        for(Boolean bln: arr){
            if(bln) res += num;
            num *= 2;
        }

        String si = sign ? "-" : "";
        return si + res;
    }
}
