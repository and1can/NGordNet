package ngordnet;
import java.util.Collection;
public class WordLengthProcessor implements YearlyRecordProcessor {
    public double process(YearlyRecord yearlyRecord) {
        double denom = 0;
        double numer = 0;
        Collection<String> words = yearlyRecord.words();
        //Collection<Number> numberWords = new ArrayList(words.size);
        for (String word : words) {
            denom += yearlyRecord.count(word);    
            numer += (yearlyRecord.count(word) * word.length());
        }
        return numer / denom;
    }     
}

