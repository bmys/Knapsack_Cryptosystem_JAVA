import Knapsack.BigInt;
import Knapsack.BinaryUtil;
import Knapsack.Knapsack;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static Knapsack.FileEncrypt.calculatePadding;
import static Knapsack.FileEncrypt.decryptFile;
import static Knapsack.FileEncrypt.encryptFile;

public class TestFileEncrypt {
    List<BigInt> privKey = new LinkedList<>(Arrays.asList(
            new BigInt(1),
            new BigInt(2),
            new BigInt(4),
            new BigInt(10),
            new BigInt(20),
            new BigInt(40),
            new BigInt(120)));

    BigInt m =  new BigInt(256);
    BigInt n =  new BigInt(53);

  @Test
  public void testFileEncryptDecrypt() {


      List<BigInt> publicKey = Knapsack.createPublicKey(privKey, n, m);
    System.out.println("Public key: " + publicKey);
//        BigInt cipher = Knapsack.toCipher("100", publicKey);
//
//        System.out.println(cipher);

      BigInt invMod = Knapsack.inverseMod(n, m);

      String plain = BinaryUtil.getFileAsBitset("/home/arch/IdeaProjects/knapsack/test/test.txt");
    //      System.out.println("plain: " + plain);

    //      List<BigInt> ll = Knapsack.encryptString(plain, publicKey);
    //      System.out.println("cipher: " + ll);
    //
    //      for(BigInt op: ll){
    //      System.out.println(op.getArr());
    //      }

    //      List<BigInt> result = Knapsack.decryptString(ll, invMod, m);

    //    System.out.println("First " + Knapsack.toBinaryPlain(result.get(0), privKey));

    //      StringBuilder stringBuilder = new StringBuilder();
    //      for (BigInt op : result) {
    //          stringBuilder.append(Knapsack.toBinaryPlain(op, privKey));
    //          System.out.println(Knapsack.toBinaryPlain(op, privKey));
    //      }

    //      String binaryResult = stringBuilder.toString();
    //      System.out.println(binaryResult);

    System.out.println("Plain: " + plain);


      String h = encryptFile("/home/arch/IdeaProjects/knapsack/test/test.txt", publicKey);
      System.out.println("Encrypted: " + h);
      BinaryUtil.writeBitsetToFile("/home/arch/IdeaProjects/knapsack/test/test.enc", h);

      String s = decryptFile("/home/arch/IdeaProjects/knapsack/test/test.enc", privKey, n, m, calculatePadding(publicKey));
      System.out.println("Decrypted: " + s);
      BinaryUtil.writeBitsetToFile("/home/arch/IdeaProjects/knapsack/test/test.dec", new StringBuilder(s).reverse().toString());


  }
}
