import com.mysql.cj.xdevapi.Statement;

import java.sql.ResultSet;

public class Stockie {
    /*
    * Entry-Point und Main der gesamten Applikation.
    * */

    public Stockie(){}

    public static void main(String[] args){
        System.out.println("test");
        Candle mycandle = new Candle();
        DatabaseConnector db = new DatabaseConnector();

    }
}
