import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Date;

public class DatabaseConnector {

     /**
        Loginparameter for the MariaDB.
     */

    private String url = "jdbc:mysql://185.188.250.67:3306/Se2Projekt";
    private String user = "root";
    private String pass = "StefanWolf2k21";

     /**
        Constructor
     */
    public DatabaseConnector() {}

     /**
        Method which shows all entries from the database.
     */

    public void viewData()  {

        try {
                // Connection to the MariaDB
                 Connection con = DriverManager.getConnection(url, user, pass);
                 System.out.println("Verbindung erfolgreich hergestellt");

                 // Create a query
                 Statement stm = con.createStatement();
                 ResultSet rs = stm.executeQuery("SELECT * FROM dailyprices;");

                 // While loop to go through all entries
                 while (rs.next()) {
                     int id = rs.getInt("Asset_ID");
                     String price = rs.getString("Price_Date");
                     double open = rs.getDouble("open");
                     double high = rs.getDouble("high");
                     double low = rs.getDouble("low");
                     double close = rs.getDouble("close");
                     double volume= rs.getDouble("volume");

                     System.out.println("date"+"                | "+      "open"+"   | "+"high"+"   | "+"low"+ "     | "+"close"+" | "+ "volume");
                    System.out.println(price+" | "+open+" | "+high+" | "+low+ " | "+close+" | "+ volume);
                }
             } catch (SQLException ex) {
                ex.printStackTrace();
             }



    }


    /**
        Method to write the data from the API call into the MariaDB
    */

    public void uploadData(ArrayList<Map<String, String>> data){

        // ArrayList with the data from the API call.
        ArrayList<Map<String, String>> stockValues = data;

        /*
            loop to write all entries from the list into the MariaDB (row for row!)
        */
        int index = 1;
        for (Map<String,String> map : stockValues) {
            try {
                String date = map.get("date");

                /*
                    Parse the value into an double.
                 */
                double open = Double.parseDouble(map.get("open"));
                double high = Double.parseDouble(map.get("high"));
                double low = Double.parseDouble(map.get("low"));
                double close = Double.parseDouble(map.get("close"));
                double volume = Double.parseDouble(map.get("volume"));

                // Connection to the MariaDB
                Connection conn = DriverManager.getConnection(url, user, pass);

                // Create an INSERT INTO query
                String query = "insert into dailyprices (Asset_ID, Price_date, open, high, low, close, volume) values (?, ?, ?, ?, ?, ?, ?)";

                /*
                    Prepared Statements for an safety upload into the MariaDB
                 */

                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setInt(1, 0);
                preparedStmt.setString(2, date);
                preparedStmt.setDouble(3, open);
                preparedStmt.setDouble(4, high);
                preparedStmt.setDouble(5, low);
                preparedStmt.setDouble(6, close);
                preparedStmt.setDouble(7, volume);
                preparedStmt.execute();
                System.out.println(index + "/" + stockValues.size() + " uploaded");
                index++;

                // Disconnect
                conn.close();
            } catch (Exception e) {
                //TODO: handle exception
                System.out.println(e.getLocalizedMessage());
            }
        }

        System.out.println("Die Verbindung zur Datenbank wurde aufgehoben. Alle Daten wurden erfolgreich hochgeladen.");




    }
}



