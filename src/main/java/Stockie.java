import com.mysql.cj.xdevapi.Statement;

import java.io.IOException;
import java.sql.ResultSet;

public class Stockie {
    /*
    * Entry-Point und Main der gesamten Applikation.
    * */

   // public Stockie(){}

    public static void main(String[] args) throws IOException {



        System.out.println("test");
        Candle mycandle = new Candle();
        DatabaseConnector db = new DatabaseConnector();

        // TEST ABFRAGE!
        URLModel url = new URLModel();
        API api = new API();
        url.setKey("1A79MCHMT69G16RE");
        url.setAsset("AAPL");
        url.setInterval("5min");
        url.setFunction("TIME_SERIES_INTRADAY");
        String alphaVantageUrl = url.getUrl();
        api.getWebPage(alphaVantageUrl);
    }
}
