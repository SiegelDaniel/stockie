import static spark.Spark.*;

public class Stockie {
    /*
    * Entry-Point und Main der Application.
    * */

    public Stockie(){}

    public static void main(String[] args){
        System.out.println("test");
        Candle mycandle = new Candle();
        DatabaseConnector db = new DatabaseConnector();
        get("/hello", (req,res)->"Hello World");

    }
}
