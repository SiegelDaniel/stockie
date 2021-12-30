import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static spark.Spark.after;

public class Stockie {
  /*
   * Entry-Point und Main der Application.
   * */


  public Stockie() {

  }

  public static void main(String[] args) {

    after(
            (request, response) -> {
              response.header("Access-Control-Allow-Origin", "*");
              response.header("Access-Control-Allow-Methods", "GET");
            });
    Connection dbc = null;
    try{
      dbc = DriverManager.getConnection("jdbc:mysql://185.188.250.67:3306/Se2Projekt", "root", "StefanWolf2k21");
    } catch (SQLException ConnectionNotEstablished) {
      ConnectionNotEstablished.printStackTrace();
      System.exit(0);
    }

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    AssetAPI assetAPI = new AssetAPI(dbc, gson);
    assetAPI.init();

    IndicatorAPI indicatorAPI  = new IndicatorAPI(dbc, gson);
    indicatorAPI.init();

    Stockie app;
    app = new Stockie();


  }
}
