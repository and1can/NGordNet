package ngordnet;
import java.util.Collection;
import edu.princeton.cs.introcs.In;
import java.util.TreeMap;
import java.util.Map;
import java.util.HashMap;
public class NGramMap {
    private Map<Integer, YearlyRecord> yrRecord;
    private Map<String, TimeSeries<Integer>> wSeries;
    private TimeSeries<Long> totalCountHistory;
    private Map<Integer, TimeSeries<Long>> mapTotalCount;
    private TreeMap<Integer, HashMap<String, Integer>> yrHashRecord;
    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    public NGramMap(String wordsFilename, String countsFilename) {
        In words = new In(wordsFilename);
        In counts = new In(countsFilename);
        yrRecord = new TreeMap<Integer, YearlyRecord>();
        wSeries = new HashMap<String, TimeSeries<Integer>>();
        yrHashRecord = new TreeMap<Integer, HashMap<String, Integer>>();
        int wordsCount = 0;
	    int countsCount = 0;
        while (words.readLine() != null) {
            wordsCount += 1;
        }
        while (counts.readLine() != null) {
	        countsCount += 1;
        }
        In readWords = new In(wordsFilename);
        In readCounts = new In(countsFilename);
        wSeries = new TreeMap<String, TimeSeries<Integer>>();
        for (int i = 0; i < wordsCount; i++) {
	        String curr = readWords.readLine();
            Object[] scanWords = curr.split("\\s+");
            String word = (String) scanWords[0];
            int year = Integer.parseInt((String) scanWords[1]);
            int value = Integer.parseInt((String) scanWords[2]);
            if (!yrRecord.containsKey(year)) {
                HashMap<String, Integer> wordValue = new HashMap<String, Integer>();
                wordValue.put(word, value);
                YearlyRecord newYrRec = new YearlyRecord(wordValue);
                yrRecord.put(year, newYrRec);
            } else {
                HashMap<String, Integer> wordValue = new HashMap<String, Integer>();
                wordValue.put(word, value);
                YearlyRecord newYrRec = new YearlyRecord(wordValue); 
                yrRecord.get(year).put(word, value);
            }
            if (!wSeries.containsKey(word)) {
                TimeSeries<Integer> newTs = new TimeSeries<Integer>();
                newTs.put(year, value);
                wSeries.put(word, newTs);
	        } else {
                TimeSeries<Integer> newTs = new TimeSeries<Integer>();
                newTs.put(year, value);
                TimeSeries<Double> added = wSeries.get(word).plus(newTs);
                TimeSeries<Integer> addedInt = new TimeSeries<Integer>();
                for (Integer key : added.keySet()) {
                    addedInt.put(key, (int) ((double)  added.get(key)));
                } 
                wSeries.put(word, addedInt);
            }
        }
        totalCountHistory = new TimeSeries<Long>();
        mapTotalCount = new TreeMap<Integer, TimeSeries<Long>>();
        for (int i = 0; i < countsCount; i++) {
            String curr = readCounts.readLine();
  	        Object[] scanWords = curr.split(",");
            Integer year = Integer.parseInt((String) scanWords[0]);
            long numWords = Long.parseLong((String) scanWords[1]);
            totalCountHistory.put(year, numWords);
            mapTotalCount.put(year, totalCountHistory);
        }
    }

    /** Returns the absolute count of WORD in the given YEAR. If the word
      * did not appear in the given year, return 0. */
    public int countInYear(String word, int year) {
        if (yrRecord.containsKey(year)) {
            return yrRecord.get(year).count(word);
        } else {
            return 0;
        }
    }

    /** Returns a defensive copy of the YearlyRecord of WORD. */
    public YearlyRecord getRecord(int year) {
        HashMap<String, Integer> hashYr = new HashMap<String, Integer>();
        for (String word : yrRecord.get(year).words()) {
            hashYr.put(word, yrRecord.get(year).count(word));
        }
        return new YearlyRecord(hashYr);
    }

    /** Returns the total number of words recorded in all volumes. */
    public TimeSeries<Long> totalCountHistory() {
        return totalCountHistory;
    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Integer> countHistory(String word, int startYear, int endYear) {
        TimeSeries<Integer> time = new TimeSeries(wSeries.get(word), startYear, endYear);
        return time;
    }

    /** Provides a defensive copy of the history of WORD. */
    public TimeSeries<Integer> countHistory(String word) {
        TimeSeries<Integer> time = new TimeSeries(wSeries.get(word));
        return time;
    }

    /** Provides the relative frequency of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Double> weightHistory(String word, int startYear, int endYear) {
        TimeSeries<Integer> interval = new TimeSeries(wSeries.get(word), startYear, endYear);
        TimeSeries<Double> total = new TimeSeries();
        for (int i = startYear; i <= endYear; i++) {
            total = total.plus(new TimeSeries(mapTotalCount.get(i), i, i));
        } 
        return interval.dividedBy(total);
    }

    /** Provides the relative frequency of WORD. */
    public TimeSeries<Double> weightHistory(String word) {
        TimeSeries<Integer> interval = new TimeSeries(wSeries.get(word));
        TimeSeries<Double> total = new TimeSeries();
        for (Integer year : interval.keySet()) {
            total = total.plus(new TimeSeries(mapTotalCount.get(year), year, year));
        }
        return interval.dividedBy(total);
    }

    /** Provides the summed relative frequency of all WORDS between
      * STARTYEAR and ENDYEAR. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words, 
        int startYear, int endYear) {
        TimeSeries<Double> total = new TimeSeries<Double>();
        for (String word : words) {
            total = total.plus(weightHistory(word));
        }
        return new TimeSeries(total, startYear, endYear);
    }

    /** Returns the summed relative frequency of all WORDS. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words) {
        TimeSeries<Double> total = new TimeSeries<Double>();
        for (String word : words) {
            total = total.plus(weightHistory(word));
        }
        return total;
    }
    /** Provides processed history of all words between STARTYEAR and ENDYEAR as processed
    * by YRP. */
    public TimeSeries<Double> processedHistory(int startYear, int endYear,
                                               YearlyRecordProcessor yrp) {
        TimeSeries<Double> processed = new TimeSeries<Double>();
        for (Integer key : yrRecord.keySet()) {
            processed.put(key, yrp.process(yrRecord.get(key)));
        }
        return new TimeSeries(processed, startYear, endYear);
    }

    /** Provides processed history of all words ever as processed by YRP. */
    public TimeSeries<Double> processedHistory(YearlyRecordProcessor yrp) {
        TimeSeries<Double> processed = new TimeSeries<Double>();
        for (Integer key : yrRecord.keySet()) {
            processed.put(key, yrp.process(yrRecord.get(key)));
        } 
        return processed;
    }
}
