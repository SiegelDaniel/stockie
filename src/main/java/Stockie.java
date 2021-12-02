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
        URLModel url = new URLModel("1A79MCHMT69G16RE","AAPL","5min","TIME_SERIES_INTRADAY");
        API api = new API();
        String alphaVantageUrl = url.getUrl();
        api.getWebPage(alphaVantageUrl, "3. low");
        api.getWebPage(alphaVantageUrl, "1. open");

    }
}
