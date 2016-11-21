package ngordnet;
import ngordnet.YearlyRecord;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
public class YearlyRecordDemo {
    public static void main(String[] args) {
        YearlyRecord yr = new YearlyRecord();
        yr.put("my name is", 181);
        yr.put("quayside", 95);        
        yr.put("surrogate", 340);
        yr.put("merchantman", 181);      
        yr.put("harro", 95);
        System.out.println(yr.rank("surrogate")); // should print 1
        System.out.println(yr.rank("quayside")); // 4 or 5
        System.out.println(yr.rank("merchantman")); //2 or 3
        System.out.println("Size is " + yr.size()); // 5
        System.out.println(yr.rank("harro"));       // 4 or 5
        System.out.println(yr.rank("my name is"));  //2 or 3
        Collection<String> words = yr.words();

        /* The code below should print: 
          
          quayside appeared 95 times.
          merchantman appeared 181 times.
          surrogate appeared 340 times.
        */
        for (String word : words) {
            System.out.println(word + " appeared " + yr.count(word) + " times.");
        }

        /* The code below should print the counts in ascending order:
          
          95
          181
          340
        */

     Collection<Number> counts = yr.counts();
     for (Number count : counts) {
           System.out.println(count);
       }
        HashMap<String, Integer> rawData = new HashMap<String, Integer>();
        rawData.put("berry", 1290);
        rawData.put("auscultating", 6);
        rawData.put("temporariness", 20);
        rawData.put("puppetry", 191);
        YearlyRecord yr2 = new YearlyRecord(rawData);
        System.out.println(yr2.rank("auscultating")); // should print 4
        System.out.println(yr2.rank("berry"));        // should print 1
        System.out.println(yr2.rank("puppetry"));     // should print 2
        System.out.println(yr2.rank("temporariness"));// should print 3   
        Collection<String> wordsTwo = yr2.words();
        for (String word : wordsTwo) {
            System.out.println(word + " appeared " + yr2.count(word) + " times.");
        }
        
        Collection<Number> counts2 = yr2.counts();
        for (Number count : counts2) {
            System.out.println(count);
        } 
    }
} 
