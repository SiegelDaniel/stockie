import spark.Request;
import spark.Response;

import static spark.Spark.get;

public class AssetAPI {

    public AssetAPI(){

    };

    public void init(){
        get("/assets/:asset-id/descr", ((request, response) -> getDescription(request, response)));
        get("assets/:asset-id/price/days/:days", ((request, response) -> getPriceByDays(request,response)));

    };


    public String getDescription(Request req, Response res){
        //Nimmt Asset-ID und gibt entsprechende Beschreibung aus der Datenbank aus
        return "";
    };

    public String getPriceByDays(Request req, Response res){
        //Nimmt Asset-ID und Anzahl der Tage als Parameter, gibt Preisdaten dieser Tage aus
        return "";
    }



}
