import spark.Request;
import spark.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static spark.Spark.get;

public class AssetAPI {
    private Connection DBConnection;

    public AssetAPI(Connection DBConnection){
        this.DBConnection = DBConnection;
    };

    public void init(){
        get("/assets/:asset-id/descr", ((request, response) -> getDescription(request, response)));
        get("assets/:asset-id/price/days/:days", ((request, response) -> getPriceByDays(request,response)));
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
                    "SELECT Description FROM asset_information WHERE symbol=?;"
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

        return "";
    };

    /**
     * Bedient den Endpunkt /assets/<asset-id>/price/<zeitraum>/<laenge>
     * Asset-ID beschreibt das Tickersymbol, z.B. AAPL
     * Zeitraum ist aus Days,Weeks,Months,Years
     * Laenge ist die Laenge des Zeitraums
     * @author <a href="mailto:daniel.siegel@stud.th-owl.de">Daniel Siegel</a>
     * @param req HTTP-Request Informationen wie von Spark Java uebergeben.
     * @param res HTTP-Response Informationen wie sie an den USer übergeben werden sollen nach Spark Java.
     * @return JSON Objekt
     */
    public String getPriceByDays(Request req, Response res){
        //Nimmt Asset-ID und Anzahl der Tage als Parameter, gibt Preisdaten dieser Tage aus
        return "";
    }



}
