package ngordnet;
import java.util.Collections;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import com.google.common.collect.ArrayListMultimap; //http://docs.guava-libraries.googlecode.com/
//git/javadoc/com/google/common/collect/ArrayListMultimap.html 
import com.google.common.collect.Multimap; //http://docs.guava-libraries.googlecode.com/
//git/javadoc/com/google/common/collect/Multimap.html
    
public class YearlyRecord {
    private Multimap<Integer, String>  yrRec;
    private Map<String, Integer> revRec;
    private List<String> listWords;
    private Collection<Number> collVal;
    private List<Integer> listV;
    private int rankCount;
    private Map<String, Integer> rankMap;
    private int putCounter;
    private boolean sorted;
    private Collection<String> words;
    private ArrayList<Word> wordRank;
    private Map<Integer, String> hashMap;
    private Map<String, Number> hashCount;
    /** Creates a new empty YearlyRecord. */
    private void sort() {
        sorted = true;
        wordRank = new ArrayList<Word>(yrRec.size()); 
        rankCount = yrRec.size();
        words = new LinkedList<String>();
        hashMap = new LinkedHashMap<Integer, String>();
        hashCount = new LinkedHashMap<String, Number>();
        for (Integer key : yrRec.keySet()) {
           for (String keyWord : yrRec.get(key)) {
                Word wordSort = new Word(keyWord, key);
  		        wordRank.add(wordSort);
   		        Collections.sort(wordRank);
            }		
        }
        for (Word word : wordRank) {
            rankMap.put(word.word, rankCount);
            rankCount -= 1;
      	    hashMap.put(word.value, word.word);
            hashCount.put(word.word, word.value);
        }
    } 
    public YearlyRecord() {
        yrRec = ArrayListMultimap.create();
        rankMap = new TreeMap<String, Integer>();
        revRec = new TreeMap<String, Integer>();
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        yrRec = ArrayListMultimap.create();
        rankMap = new TreeMap<String, Integer>();
        revRec = new TreeMap<String, Integer>();   
        for (String word : otherCountMap.keySet()) {
            yrRec.put(otherCountMap.get(word), word);
            revRec.put(word, otherCountMap.get(word));
        }
    }

    /** Returns the number of times WORD appeared in this year. */
    public int count(String word) {
        if (revRec.containsKey(word)) {
            return (int) revRec.get(word).doubleValue();
        } else {
            return 0;
        } 
    }

    /** Records that WORD occurred COUNT times in this year. */
    public void put(String word, int count) {
        sorted = false;
        yrRec.put(count, word);
        revRec.put(word, count);
    }

    /** Returns the number of words recorded this year. */
    public int size() {
        return yrRec.size();
    }

    /** Returns all words in ascending order of count. */
    public Collection<String> words() {
        if (sorted) {
            return hashMap.values();
        } else {
            sort();
            return hashMap.values();
        }
    }
    /** Returns all counts in ascending order of count. */
    public Collection<Number> counts() {
        if (sorted) {
            return hashCount.values();
        } else {
            sort();
	    return hashCount.values(); 
        }
    }
    /** Returns rank of WORD. Most common word is rank 1.
      * If two words have the same rank, break ties arbitrarily.
      * No two words should have the same rank.
      */
    public int rank(String word) {
        if (sorted) {
            return rankMap.get(word);
        } else {
    	    sort();
            return rankMap.get(word);
        }        
    }

    public class Word implements Comparable<Word> {
        private String word;
        private int value;

        private Word(String w, Integer v) {
            this.word = w;
            this.value = v;
        }
        @Override 
        public int compareTo(Word compareWord) {
            int compareValue = ((Word) compareWord).value;
            return this.value - compareValue;
        }
    } 
}
