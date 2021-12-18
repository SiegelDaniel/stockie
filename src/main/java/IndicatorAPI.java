import spark.Request;
import spark.Response;

import static spark.Spark.get;

public class IndicatorAPI {
  /*
   * Klasse welche statische Methoden zur Berechnung von Indikatoren haelt.
   * */
  public IndicatorAPI() {
    // implement singleton here
  }

  public void init() {
    get("/assets/:asset-id/indicators/ma/days/:days", (this::getMovingAverage));
  }

  public String getMovingAverage(Request request, Response response) {
    // Nimmt Asset-ID, Zeitraum als Parameter, berechnet Moving Average Datenpunkte
    return "";
  }
}
