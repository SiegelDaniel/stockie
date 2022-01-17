import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.sql.*;
import java.util.ArrayList;

import static spark.Spark.get;

public class IndicatorAPI {

  private final Connection DBConnection;
  private final Gson gson;
  private ArrayList<Indicator_Point> DataPoints;

  public IndicatorAPI(Connection DBConnection, Gson pGson) {
    this.DBConnection = DBConnection;
    this.gson = pGson;
  }

  public void init() {
    get("/assets/:asset-id/indicators/sma/days/:days", (this::getSimpleMovingAverage));
  }

  public String getSimpleMovingAverage(Request request, Response response) {
    String symbol = request.params(":asset-id");
    String days = request.params(":days");
    ResultSet rs;
    PreparedStatement ps;
    int MAcounter = 0;
    double MAsum = 0;
    DataPoints = new ArrayList<>();

    try {
      ps =
          DBConnection.prepareStatement(
              "SELECT Price_date, high, low FROM assetPrices INNER JOIN assets ON assetPrices.Asset_id = assets.Asset_id WHERE assets.symbol LIKE ? AND assetPrices.Price_date  >= DATE(NOW()) - INTERVAL ? DAY ORDER BY assetPrices.Price_date ");
      ps.setString(1, symbol);
      ps.setString(2, days);
      rs = ps.executeQuery();
    } catch (SQLException queryNotExecutable) {
      queryNotExecutable.printStackTrace();
      response.status(500);
      return "Error retrieving dataset";
    }

    try {
      if(!rs.next()){
        response.status(404);
        return "No result set";
      }
      while (rs.next()) {
        Timestamp pricedate = rs.getTimestamp("Price_date");
        double high = rs.getDouble("high");
        double low = rs.getDouble("low");
        double value = high + low;
        value = value / 2;
        MAsum += value;
        MAcounter += 1;
        Indicator_Point temp = new Indicator_Point(pricedate, MAsum / MAcounter);
        DataPoints.add(temp);
      }
    } catch (SQLException resultSetEmpty) {
      resultSetEmpty.printStackTrace();
      response.status(500);
      return "No entries for asset";
    }

    response.type("application/json");
    return gson.toJson(DataPoints);
  }
}
