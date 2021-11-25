import com.mysql.cj.xdevapi.Statement;

import java.io.IOException;
import java.sql.ResultSet;

public class Stockie {
    /*
    * Entry-Point und Main der gesamten Applikation.
    * */

   // public Stockie(){}

    public static void main(String[] args) throws IOException {

    URLModel url = new URLModel();

        System.out.println("test");
        Candle mycandle = new Candle();
        DatabaseConnector db = new DatabaseConnector();
        API api = new API();
        String alphaVantageUrl = url.getUrl();
        api.getWebPage(alphaVantageUrl);
    }
}
