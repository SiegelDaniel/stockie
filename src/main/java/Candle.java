import java.util.GregorianCalendar;

public class Candle {

    public enum UNIT {SECOND, MINUTE, HOUR, DAY, WEEK, MONTH, YEAR}

    protected double open;
    protected double close;
    protected double low;
    protected double high;

    protected GregorianCalendar date;

    /*Standard constructor*/
    public Candle(){

    }

    /**
     * Parametrized constructor
     */
    public Candle(double open, double close, double low, double high, GregorianCalendar time){
        this.open = open;
        this.close = close;
        this.low = low;
        this.high = high;
        this.date = time;
    }

    /**
     * Updates the Candle with an additional price.
     * If open is not set yet, the price is set as open.
     * The price is always set as close as it may be the last one.
     * High and low are updated accordingly.
     */
    public void update(double price){
        this.close = price;
        if(this.open == 0.0){
            this.open = price;
        }
        if(this.high < price){
            this.high = price;
        }else if(this.low > price) this.low = price;
    }



    /*GETTER & SETTER*/

    public double getClose() {
        return close;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }



    @Override
    public boolean equals(Object obj){
        //TODO
        return false;
    }
}
