package test;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;
import java.math.BigInteger;

public class BloomFilter {
    //Class members:
    private BitSet bitSet;
    private String arg1;
    private String arg2;
    private int size;


    public BloomFilter(int size , String arg1 , String arg2){
        this.size = size;
        bitSet = new BitSet(size); //init bitset all cells 0
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public void add(String word){
        try {
            MessageDigest hash1 = MessageDigest.getInstance(arg1);
            MessageDigest hash2 = MessageDigest.getInstance(arg2);
            byte [] byteArr1 = hash1.digest(word.getBytes());
            byte [] byteArr2 = hash2.digest(word.getBytes());
            BigInteger b1 = new BigInteger(byteArr1);
            BigInteger b2 = new BigInteger(byteArr2);
            int index1 = b1.intValue() , index2 = b2.intValue();
            bitSet.set(Math.abs(index1) % this.size);
            bitSet.set(Math.abs(index2) % this.size);
        }

        catch (NoSuchAlgorithmException e){
            System.out.println("Error with getInstance");
        }

    }

    public boolean contains (String word){
        boolean b = false;
        try {
            MessageDigest hash1 = MessageDigest.getInstance(arg1);
            MessageDigest hash2 = MessageDigest.getInstance(arg2);
            byte[] byteArr1 = hash1.digest(word.getBytes());
            byte[] byteArr2 = hash2.digest(word.getBytes());
            BigInteger b1 = new BigInteger(byteArr1);
            BigInteger b2 = new BigInteger(byteArr2);
            int index1 = Math.abs(b1.intValue()) % this.size;
            int index2 = Math.abs(b2.intValue()) % this.size;

            if(bitSet.isEmpty()) {
                return false;
            }

            if(bitSet.length() > bitSet.size()){
                return false;
            }

            if(bitSet.get(index1) && bitSet.get(index2)){
                b = true;
            }

        }

        catch (NoSuchAlgorithmException e){
            System.out.println("Error with getInstance");
        }
        return b;
    }


    @Override
    public String toString() {
        String tmp="";
        for(int i = 0; i < bitSet.length(); i++){
            if(bitSet.get(i)){
                tmp += "1";
            }
            else{
                tmp+="0";
            }
        }
        return tmp;
    }

//end of class
}
