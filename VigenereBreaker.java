import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder slice = new StringBuilder();
        for(int i = whichSlice; i < message.length();i += totalSlices)
        {
            slice.append(message.charAt(i));
        }
        return slice.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker cc = new CaesarCracker(mostCommon);
        String[] sliced = new String[klength];
        for(int i = 0; i< klength; i++)
        {
            sliced[i]=sliceString(encrypted, i, klength);
            key[i] = cc.getKey(sliced[i]);
        }
        return key;
    }

    public void breakVigenere () {
        FileResource f = new FileResource();
        String message = f.asString();
        //FileResource dict = new FileResource();
        //HashSet dictionary = readDictionary(dict);
        //String decrypted = breakForLanguage(message, dictionary);
        //System.out.println(decrypted);
        HashMap<String, HashSet<String>> hs = new HashMap<String, HashSet<String>>();
        FileResource eng = new FileResource("English");
        hs.put("English", readDictionary(eng));
        System.out.println("Finding English");
        
        FileResource dan = new FileResource("Danish");
        hs.put("Danish", readDictionary(dan));
        System.out.println("Finding Danish");
        
        FileResource dut = new FileResource("Dutch");
        hs.put("Dutch", readDictionary(dut));
        System.out.println("Finding Dutch");
        
        FileResource fre = new FileResource("French");
        hs.put("French", readDictionary(fre));
        System.out.println("Finding French");
        
        FileResource ger = new FileResource("German");
        hs.put("German", readDictionary(ger));
        System.out.println("Finding German");
        
        FileResource ita = new FileResource("Italian");
        hs.put("Italian", readDictionary(ita));
        System.out.println("Finding Italian");
        
        FileResource por = new FileResource("Portuguese");
        hs.put("Portuguese", readDictionary(por));
        System.out.println("Finding Portuguese");
        
        FileResource spa = new FileResource("Spanish");
        hs.put("Spanish", readDictionary(spa));
        System.out.println("Finding Spanish");
        
        breakForAllLangs(message, hs);
    }
    
    public HashSet<String> readDictionary(FileResource fr)
    {
        HashSet<String> hs = new HashSet<String>();
        for (String s : fr.lines())
        {
            String lcs = s.toLowerCase();
            hs.add(lcs);
        }
        return hs;
    }
    
    public int countWords(String message, HashSet<String> dictionary)
    {
        int cnt = 0;
        for(String s : message.split("\\W"))
        {
            if(dictionary.contains(s))
            {
                cnt+=1;
            }
            //System.out.println("\t" +  cnt);
        }
        return cnt;
    }
    
    public String breakForLanguage(String encrypted, HashSet<String> dictionary)
    {
        int mostWords = 0;
        int bestKey = 0;
        String broken = "N/A";
        char mcci = mostCommonCharIn(dictionary);
        for(int i = 1; i <=100; i++)
        {
            int[] keys = tryKeyLength(encrypted, i, mcci);
            VigenereCipher vc = new VigenereCipher(keys);
            String decrypted = vc.decrypt(encrypted);
            String ld = decrypted.toLowerCase();
            int wordcount = countWords(decrypted, dictionary);
            
            if(wordcount > mostWords)
            {
                mostWords = wordcount;
                broken = decrypted;
            }
            //System.out.print(bestKey + " ");
            
        }
        return broken;
    }
    
    public char mostCommonCharIn(HashSet<String> dictionary)
    {
        int[] charcnt = new int[Character.MAX_VALUE + 1];
        int cnt = 0;
        char mcci = ' ';
        for(String s : dictionary)
        {
            for(int i=0; i<s.length();i++)
            {
                char ch = s.charAt(i);
                charcnt[ch]++;
                if(charcnt[ch] >= cnt)
                {
                    cnt = charcnt[ch];
                    mcci = ch;
                }
            }
        }
        return mcci;
    }
    
    public void breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> languages)
    {
        int mostWords = 0;
        String lang = "N/A";
        String message = "N/A";
        for(String s : languages.keySet())
        {
            String broken = breakForLanguage(encrypted, languages.get(s));
            int cntWord = countWords(broken, languages.get(s));
            if(cntWord > mostWords)
            {
                mostWords = cntWord;
                lang = s;
                message = broken;
            }
        }
        System.out.println("Language - " + lang);
        System.out.println("Message - " + message);
    }
}
