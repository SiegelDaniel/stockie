import spark.Request;
import spark.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static spark.Spark.get;

public class AssetAPI {

    private Connection DBConnection;
    private ArrayList<Candle> Candles = new ArrayList<>();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public AssetAPI(Connection DBConnection){
        this.DBConnection = DBConnection;
    };

    public void init(){
        get("/assets/:asset-id/descr", ((request, response) -> getDescription(request, response)));
        get("/assets/:asset-id/price/days/:days", ((request, response) -> getPriceByDays(request,response)));
    };

    /**
     * Nimmt als Anfrageparameter eine Asset-ID (z.B. AAPL) und gibt einen Informationsstring zurueck.
     *
     * @author <a href="mailto:daniel.siegel@stud.th-owl.de">Daniel Siegel</a>
     * @param req HTTP-Request Informationen wie von Spark Java uebergeben.
     * @param res HTTP-Response Informationen wie sie an den USer übergeben werden sollen nach Spark Java.
     * @return JSON-Objekt mit Informationsstring, 404 wenn nicht vorhanden, 500 sonst.
     */
    public String getDescription(Request req, Response res)  {

        PreparedStatement ps = null;
        try {
            ps = DBConnection.prepareStatement(
                    "SELECT Description FROM assetInformation JOIN assets ON assetInformation.Asset_id = assets.Asset_ID WHERE assets.Symbol=?;"
            );
            try{
                //Asset-Symbol (z.B. AAPL) aus der URL parsen
                ps.setString(1, req.params(":asset-id"));
                ResultSet rs = ps.executeQuery();
                try{
                    if(rs.next()){ //pruefe ob RS leer ist oder nicht
                        String description = rs.getString("Description");
                        rs.close();
                        ps.close();
                        /* Alle Query-Instanzen ordnungsgemaess schliessen und zurueckgeben */
                        res.type("application/json");
                        /*muss vorher noch in JSON verpackt werden*/
                        description = gson.toJson(description);
                        return description;
                    }else{
                        //Symbol wurde nicht gefunden, ResultSet war leer
                        res.status(404);
                    }
                } catch (SQLException throwables) {
                    rs.close();
                    ps.close();
                    res.status(500);
                    throwables.printStackTrace();
                }
            } catch (SQLException throwables) {
                //ResultSet konnte nicht geholt werden, executeQuery fehlgeschlagen
                res.status(500);
                throwables.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "NOT FOUND";
    };

    /**
     * Bedient den Endpunkt /assets/<asset-id>/price/days/<days>
     * Asset-ID beschreibt das Tickersymbol, z.B. AAPL
     * Laenge ist die Laenge des Zeitraums in Tagen
     * @author <a href="mailto:daniel.siegel@stud.th-owl.de">Daniel Siegel</a>
     * @param req HTTP-Request Informationen wie von Spark Java uebergeben.
     * @param res HTTP-Response Informationen wie sie an den USer übergeben werden sollen nach Spark Java.
     * @return JSON Objekt
     */
    //TODO: Aktuell werden alle Tage ausgegeben, wir wollen nur die letzten X Tage
    public String getPriceByDays(Request req, Response res){
        PreparedStatement ps = null;
        String timeframe = req.params(":days"); //wieviele Tage wollen wir
        String symbol = req.params(":asset-id"); //von welcher Aktie wollen wir die
        ResultSet rs = null;
        try{
            ps = DBConnection.prepareStatement("SELECT Price_date,open,high,low,close FROM assetPrices INNER JOIN assets ON assetPrices.Asset_id = assets.Asset_id WHERE assets.symbol LIKE ? ORDER BY assetPrices.Price_date ");
            ps.setString(1, symbol);
            rs = ps.executeQuery();
        } catch (SQLException queryNotExecutable) {
            //prepareStatement laeuft immer durch, Fehler koennen nur in executeQuery auftreten
            queryNotExecutable.printStackTrace();
            res.status(500);
            return "Error retrieving dataset";
        }

        try{
            while(rs.next()){
                Timestamp pricedate = rs.getTimestamp("Price_date");
                double open = rs.getDouble("open");
                double high = rs.getDouble("high");
                double low  = rs.getDouble("low");
                double close = rs.getDouble("close");

                Candle temp = new Candle(open,close,low,high,pricedate);
                Candles.add(temp);
            }
        } catch (SQLException resultSetEmpty) {
            //ResultSet ist leer
            resultSetEmpty.printStackTrace();
            res.status(500);
            return "No entries for asset";
        }

        res.type("application/json");
        String ret = gson.toJson(Candles);
        Candles.clear();
        return ret;
    }





}
