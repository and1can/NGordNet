package ngordnet;
import java.util.ArrayList;

public class NGramMapDemo {
    public static void main(String[] args) {
        NGramMap ngm = new NGramMap("p1data/ngrams/words_that_start_with_q.csv", 
        "p1data/ngrams/total_counts.csv");
        // System.out.println(ngm.countInYear("airport", 2007));
        // System.out.println(ngm.countInYear("airport", 2008));
        //  System.out.println(ngm.countInYear("airport", 2009)); // should print 0
        //  System.out.println(ngm.countInYear("request", 2005));   
        //  System.out.println(ngm.countInYear("request", 2008));
        //  System.out.println(ngm.countInYear("request", 5));
        //  System.out.println(ngm.countInYear("wandered", 2005));
        //  System.out.println(ngm.countInYear("wandered", 2006));
        //  System.out.println(ngm.countInYear("wandered", 2015));
        System.out.println(ngm.countInYear("quantity", 2000)); // should print 139
        System.out.println("year 3000 should be 0, is " + ngm.countInYear("quantity", 3000));
        System.out.println("roly 1505 should be 0, is " + ngm.countInYear("roly", 1505));
        YearlyRecord yr0 = ngm.getRecord(1736);
        System.out.println(yr0.count("quantity")); // should print 139 
        TimeSeries<Integer> countHistory = ngm.countHistory("quantity");
        System.out.println(countHistory.get(1736)); // should print 139        
     
        TimeSeries<Long> totalCountHistory = ngm.totalCountHistory();
        /* In 1736, there were 8049773 recorded words. Note we are not counting
         * distinct words, but rather the total number of words printed that year. */
        System.out.println(totalCountHistory.get(1736)); // should print 8049773

        TimeSeries<Double> weightHistory = ngm.weightHistory("quantity", 1736, 1737);
        System.out.println("range for quantity 1736 " + weightHistory.get(1736));
        System.out.println("range for quantity 1737 " + weightHistory.get(1737));
        TimeSeries<Double> weightHistory2 = ngm.weightHistory("quantity");
        System.out.println(weightHistory2.get(1736));  // should print roughly 1.7267E-5
        //  YearlyRecord yr = ngm.getRecord(2008);
        //  System.out.println(yr.count("wandered")); // should print 139
        //  TimeSeries<Integer> countHistory = ngm.countHistory("airport");
        //  System.out.println(countHistory.get(1736)); // should print 139
        //  TimeSeries<Long> totalCountHistory = ngm.totalCountHistory();
        //  System.out.println(totalCountHistory.get(2005)); // should print 8049773
        //  System.out.println("total words for 2008 " + totalCountHistory.get(2008));
        //  TimeSeries<Double> weightHistory = ngm.weightHistory("quantity");
        //  System.out.println("WeightHistory of 2008" + weightHistory.get(2008));  // should print roughly 1.7267E-5
        //  System.out.println("WeightHistory of 2005" + weightHistory.get(2005));    
        //  System.out.println((double) countHistory.get(1736) 
        //  (double) totalCountHistory.get(1736)); 
        ArrayList<String> words = new ArrayList<String>();
        words.add("quantity");
        words.add("quality");        
        TimeSeries<Double> sum = ngm.summedWeightHistory(words);
        System.out.println("The summedWeightHistory is " + sum.get(1736)); // should print roughly 3.875E-5
        System.out.println((double) countHistory.get(1736) 
                           / (double) totalCountHistory.get(1736)); 
    }
}
