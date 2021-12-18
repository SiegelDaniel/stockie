import java.sql.Timestamp;

public class Candle {

  protected double open;
  protected double high;
  protected double low;
  protected double close;
  protected double volume;

  protected long date; /*UNIX time*/

  /*Standard constructor*/
  public Candle() {}

  /** Parametrized constructor */
  public Candle(double open, double close, double low, double high, Timestamp time) {
    this.open = open;
    this.high = high;
    this.low = low;
    this.close = close;
    this.volume = 420.00; /*dummy*/

    this.date = time.getTime();
  }

  /**
   * Updates the Candle with an additional price. If open is not set yet, the price is set as open.
   * The price is always set as close as it may be the last one. High and low are updated
   * accordingly.
   */
  public void update(double price) {
    this.close = price;
    if (this.open == 0.0) {
      this.open = price;
    }
    if (this.high < price) {
      this.high = price;
    } else if (this.low > price) this.low = price;
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

  public long getDate() {
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

  public void setDate(Timestamp date) {
    this.date = date.getTime();
  }

  @Override
  public boolean equals(Object obj) {
    return this == obj;
  }
}
