import spark.Request;
import spark.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static spark.Spark.get;

public class AssetAPI {

  private final Connection DBConnection;
  private final Gson gson ;
  private ArrayList<Candle> Candles = new ArrayList<>();


  public AssetAPI(Connection DBConnection, Gson pGson) {
    this.DBConnection = DBConnection;
    this.gson = pGson;
  }
  ;

  public void init() {
    get("/assets/:asset-id/descr", (this::getDescription));
    get("/assets/:asset-id/price/days/:days", (this::getPriceByDays));
    get("/assets/:asset-id/price/all", (this::getAllPrices));
    get("/assets/:asset-id/price/last", (this::getLastPrice));
  }
  ;

  /**
   * Nimmt als Anfrageparameter eine Asset-ID (z.B. AAPL) und gibt einen Informationsstring zurueck.
   *
   * @author <a href="mailto:daniel.siegel@stud.th-owl.de">Daniel Siegel</a>
   * @param req HTTP-Request Informationen wie von Spark Java uebergeben.
   * @param res HTTP-Response Informationen wie sie an den USer übergeben werden sollen nach Spark
   *     Java.
   * @return JSON-Objekt mit Informationsstring, 404 wenn nicht vorhanden, 500 sonst.
   */
  public String getDescription(Request req, Response res) {

    PreparedStatement ps = null;
    try {
      ps =
          DBConnection.prepareStatement(
              "SELECT Name,Description,Country,Sector,Industry,Currency,Logo,Website FROM assetInformation JOIN assets ON assetInformation.Asset_id = assets.Asset_ID WHERE assets.Symbol=?;");
      try {
        // Asset-Symbol (z.B. AAPL) aus der URL parsen
        ps.setString(1, req.params(":asset-id"));
        ResultSet rs = ps.executeQuery();
        try {
          if (rs.next()) { // pruefe ob RS leer ist oder nicht
            String name = rs.getString("Name");
            String description = rs.getString("Description");
            String sector = rs.getString("Sector");
            String country = rs.getString("Country");
            String industry = rs.getString("Industry");
            String currency = rs.getString("Currency");
            Blob logo = rs.getBlob("Logo");
            String website = rs.getString("Website");

            rs.close();
            ps.close();

            Information temporary = new Information(name,description,country,sector,industry,currency,website,logo);


            res.type("application/json");
            description = gson.toJson(temporary);

            return description;

          } else {
            // Symbol wurde nicht gefunden, ResultSet war leer
            res.status(404);
          }
        } catch (SQLException throwables) {
          rs.close();
          ps.close();
          res.status(500);
          throwables.printStackTrace();
        }
      } catch (SQLException throwables) {
        // ResultSet konnte nicht geholt werden, executeQuery fehlgeschlagen
        res.status(500);
        throwables.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return "NOT FOUND";
  }
  ;

  /**
   * Bedient den Endpunkt /assets/<asset-id>/price/days/<days> Asset-ID beschreibt das Tickersymbol,
   * z.B. AAPL Laenge ist die Laenge des Zeitraums in Tagen
   *
   * @author <a href="mailto:daniel.siegel@stud.th-owl.de">Daniel Siegel</a>
   * @param req HTTP-Request Informationen wie von Spark Java uebergeben.
   * @param res HTTP-Response Informationen wie sie an den USer übergeben werden sollen nach Spark
   *     Java.
   * @return JSON Objekt
   */
  public String getPriceByDays(Request req, Response res) {
    /*TODO: ggf. Plausibilitaetspruefung, ob Datenpunkte fehlen*/
    PreparedStatement ps = null;
    String timeframe = req.params(":days"); // wieviele Tage wollen wir
    timeframe = Integer.toString(Integer.parseInt(timeframe) * 24);
    String symbol = req.params(":asset-id"); // von welcher Aktie wollen wir die
    ResultSet rs = null;
    try {
      ps =
          DBConnection.prepareStatement(
              "SELECT Price_date,open,high,low,close FROM assetPrices INNER JOIN assets ON assetPrices.Asset_id = assets.Asset_id WHERE assets.symbol LIKE ? AND assetPrices.Price_date  >= DATE(NOW()) - INTERVAL ? HOUR ORDER BY assetPrices.Price_date ");
      ps.setString(1, symbol);
      ps.setString(2, timeframe);
      rs = ps.executeQuery();
    } catch (SQLException queryNotExecutable) {
      // prepareStatement laeuft immer durch, Fehler koennen nur in executeQuery auftreten
      queryNotExecutable.printStackTrace();
      res.status(500);
      return "Error retrieving dataset";
    }

    try {
      while (rs.next()) {
        Timestamp pricedate = rs.getTimestamp("Price_date");
        double open = rs.getDouble("open");
        double high = rs.getDouble("high");
        double low = rs.getDouble("low");
        double close = rs.getDouble("close");


        Candle temp = new Candle(open, close, low, high, pricedate);
        Candles.add(temp);
      }
    } catch (SQLException resultSetEmpty) {
      // ResultSet ist leer
      resultSetEmpty.printStackTrace();
      res.status(500);
      return "No entries for asset";
    }

    res.type("application/json");
    String ret = gson.toJson(Candles);
    Candles.clear();
    return ret;
  }


  public String getAllPrices(Request req, Response res){
    String symbol = req.params(":asset-id");
    ResultSet rs = null;
    PreparedStatement ps = null;
    try {
      ps =
              DBConnection.prepareStatement(
                      "SELECT Price_date,open,high,low,close FROM assetHistoryPrices INNER JOIN assets ON assetHistoryPrices.Asset_id = assets.Asset_id WHERE assets.symbol LIKE ?  ORDER BY assetHistoryPrices.Price_date ");
      ps.setString(1, symbol);
      rs = ps.executeQuery();
    } catch (SQLException queryNotExecutable) {
      queryNotExecutable.printStackTrace();
      res.status(500);
      return "Error retrieving dataset";
    }

    try {
      while (rs.next()) {
        Timestamp pricedate = rs.getTimestamp("Price_date");
        double open = rs.getDouble("open");
        double high = rs.getDouble("high");
        double low = rs.getDouble("low");
        double close = rs.getDouble("close");

        Candle temp = new Candle(open, close, low, high, pricedate);
        Candles.add(temp);
      }
    } catch (SQLException resultSetEmpty) {
      resultSetEmpty.printStackTrace();
      res.status(500);
      return "No entries for asset";
    }

    res.type("application/json");
    String ret = gson.toJson(Candles);
    Candles.clear();
    return ret;

  }

  public String getLastPrice(Request req, Response res){
    String symbol = req.params(":asset-id");
    ResultSet rs = null;
    PreparedStatement ps = null;
    double close = 0;
    try {
      ps =
              DBConnection.prepareStatement(
                      "SELECT Price_date,open,high,low,close FROM assetPrices INNER JOIN assets ON assetPrices.Asset_id = assets.Asset_id WHERE assets.symbol LIKE ?  ORDER BY assetPrices.Price_date LIMIT 1 ");
      ps.setString(1, symbol);
      rs = ps.executeQuery();
    } catch (SQLException queryNotExecutable) {
      queryNotExecutable.printStackTrace();
      res.status(500);
      return "Error retrieving dataset";
    }

    try {
      while (rs.next()) {
        close = rs.getDouble("close");


      }
    } catch (SQLException resultSetEmpty) {
      resultSetEmpty.printStackTrace();
      res.status(500);
      return "No entries for asset";
    }

    res.type("application/json");
    String ret = gson.toJson(close);

    return ret;
  }
}
