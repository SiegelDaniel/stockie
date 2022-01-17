import java.sql.Timestamp;

public class Indicator_Point implements Comparable<Indicator_Point>{

    private long timestamp;
    private double value;

    public Indicator_Point(Timestamp pTimestamp, double pValue){
        this.timestamp = pTimestamp.getTime();
        this.value = pValue;
    }

    public Indicator_Point(Timestamp pTimestamp, int pValue){
        this.timestamp = pTimestamp.getTime();
        this.value = (double) pValue;
    }

    public long getTimestamp(){
        return this.timestamp;
    }

    @Override
    public int compareTo(Indicator_Point o) {
        return Long.compare(this.timestamp, o.getTimestamp());
    }
}
