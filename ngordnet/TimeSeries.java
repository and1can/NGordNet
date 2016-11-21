package ngordnet;
import java.util.TreeMap;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
public class TimeSeries<T extends Number> extends TreeMap<Integer, T> {
    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
        super();
    }
    /** Returns the years in which this time series is valid. Doesn't really
      * need to be a NavigableSet. This is a private method and you don't have
      * to implement it if you don't want to. */
    private NavigableSet<Integer> validYears(int startYear, int endYear) {
        return this.subMap(startYear, true, endYear, true).navigableKeySet();  
    }
    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR.
     * inclusive of both end points. */
    public TimeSeries(TimeSeries<T> ts, int startYear, int endYear) {
        /** come back to it later */
        for (Integer key : ts.validYears(startYear, endYear)) {
            this.put(key, ts.get(key)); 
        } 
    }
    /** Creates a copy of TS. */
    public TimeSeries(TimeSeries<T> ts) {
        for (Integer keyS: ts.navigableKeySet()) {
            this.put(keyS, ts.get(keyS));
        } 
    }
    /** Returns the quotient of this time series divided by the relevant value in ts.
      * If ts is missing a key in this time series, return an IllegalArgumentException. */
    public TimeSeries<Double> dividedBy(TimeSeries<? extends Number> ts) {
        TimeSeries<Double> divided = new TimeSeries<Double>();
        //System.out.println("We have matching keySets " + ts.keySet().equals(this.keySet()));
        if ((!ts.keySet().equals(this.keySet()))) { 
            //System.out.println(ts.keySet());
            //System.out.println(this.keySet()); 
            //System.out.println("Time Series is throwing error");
            throw new IllegalArgumentException(); 
        } else {
            for (Integer keyS: ts.navigableKeySet()) {              
                if (ts.get(keyS).doubleValue() == 0) {
                    System.out.println("Has a 0");
                    throw new IllegalArgumentException();
                }
                divided.put(keyS, this.get(keyS).doubleValue() / ts.get(keyS).doubleValue());
            }
            return divided;	    
        } 		 
    } 
    /** Returns the sum of this time series with the given ts. The result is a
      * a Double time series (for simplicity). */
    public TimeSeries<Double> plus(TimeSeries<? extends Number> ts) {
        TimeSeries<Double> added = new TimeSeries<Double>(); 
        for (Integer keyS: ts.navigableKeySet()) {
            if (added.containsKey(keyS)) {
                added.put(keyS, ts.get(keyS).doubleValue() + added.get(keyS).doubleValue());
            } else {
                added.put(keyS, ts.get(keyS).doubleValue());
            }
        } 
        for (Integer keyS: this.navigableKeySet()) {
            if (added.containsKey(keyS)) {
                added.put(keyS, this.get(keyS).doubleValue() + added.get(keyS).doubleValue());
            } else {
                added.put(keyS, this.get(keyS).doubleValue());
            }
        }     
        return added;   
    }
    /** Returns all years for this time series (in any order). */
    public Collection<Number> years() {
        List<Integer> valueT = new ArrayList<Integer>(this.keySet());
        Collection<Number> collectValue = new TreeSet<Number>();
        collectValue.addAll(valueT);
        return collectValue;
    }
    /** Returns all data for this time series (in any order). */
    public Collection<Number> data() {
        Collection<Number> collectKey = new ArrayList<Number>(this.size());
        for (Integer intKey : this.keySet()) {
            collectKey.add(this.get(intKey)); // take out the doubleValue()
        }
        return collectKey;
    }
}

