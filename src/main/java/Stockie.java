import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static spark.Spark.get;

public class Stockie {
    /*
    * Entry-Point und Main der Application.
    * */
    public Connection dbc;

    public Stockie(){

    }

    public static void main(String[] args){
        Stockie app = new Stockie();
        try {
            //Das hier kann man nochmal sauberer machen...
            app.dbc = DriverManager.getConnection("jdbc:mysql://185.188.250.67:3306/Se2Projekt", "root", "StefanWolf2k21");
        } catch (SQLException connectionNotEstablished) {
            connectionNotEstablished.printStackTrace();
        }
        //TODO: fix this
        AssetAPI asset_ey_pi_ei = new AssetAPI(app.dbc);
        asset_ey_pi_ei.init();
        get("/hello", (req,res)->"Hello World");

    }
}
