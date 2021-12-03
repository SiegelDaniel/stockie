import com.mysql.cj.xdevapi.Statement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Stockie {
    /*
    * Entry-Point und Main der gesamten Applikation.
    * */

   // public Stockie(){}

    public static void main(String[] args) throws IOException {

        System.out.println("test");
        //Candle mycandle = new Candle();

        // TEST ABFRAGE!
        URLModel url = new URLModel("1A79MCHMT69G16RE","AAPL","5min","TIME_SERIES_INTRADAY");
        API api = new API();
        String alphaVantageUrl = url.getUrl();
        String response = api.getWebPage(alphaVantageUrl);
        ArrayList<Map<String, String>> data = api.getJson(response);

        // DB Conector SELECT ABFRAGE UND UPLOAD API CALL
        DatabaseConnector db = new DatabaseConnector();
        db.viewData();

        /** DIE UPLOAD METHODE ERSTMAL NICHT AUSFÜHREN !
                MUSS NOCH ANGEPASST WERDEN
           DATEN IN DER DB WÜRDEN SONST DOPPELT VORHANDEN SEIN.
        */
        //db.uploadData(data);


    }
}
