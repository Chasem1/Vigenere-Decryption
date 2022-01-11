import edu.duke.*;
import java.util.*;

/**
 * Write a description of tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class tester {
    public void vCipherTest()
    {
        
        VigenereBreaker vb = new VigenereBreaker();;
        String slice = vb.sliceString("abcdefhgijklm",1,3);
        System.out.println(slice);
        System.out.println("\n");
        
        
    }
    public void testTryKLength()
    {
        
        VigenereBreaker vb = new VigenereBreaker();
        FileResource f = new FileResource();
        String message = f.asString();
        int[] key = vb.tryKeyLength(message, 5, 'e');
        for(int i : key)
            System.out.print(i + ", ");
        System.out.println("\n");
    }
}
