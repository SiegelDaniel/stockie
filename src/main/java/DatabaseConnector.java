import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Date;

public class DatabaseConnector {

    public DatabaseConnector(ArrayList<Map<String, String>> data) {
        /*
        ArrayList<Map<String, String>> stockValues = data;
        String url = "jdbc:mysql://185.188.250.67:3306/Se2Projekt";
        String user = "root";
        String pass = "StefanWolf2k21";

        int index = 1;
        for (Map<String,String> map : stockValues) {
            try {
                String date = map.get("date");
                System.out.println(date);
                double open = Double.parseDouble(map.get("open"));
                double high = Double.parseDouble(map.get("high"));
                double low = Double.parseDouble(map.get("low"));
                double close = Double.parseDouble(map.get("close"));
                double volume = Double.parseDouble(map.get("volume"));
    
                Connection conn = DriverManager.getConnection(url, user, pass);
                String query = "insert into dailyprices (Asset_ID, Price_date, open, high, low, close, volume) values (?, ?, ?, ?, ?, ?, ?)";
    
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
                conn.close();
            } catch (Exception e) {
                //TODO: handle exception
                System.out.println(e.getLocalizedMessage());
            }



        }

        System.out.println("Die Verbindung zur Datenbank wurde aufgehoben. Alle Daten wurden erfolgreich hochgeladen.");


       
        // Verbindung aufbauen
        //String db = "Se2Projekt";

        // try {
        //     Connection conn = DriverManager.getConnection(url, user, pass);
        //     System.out.println("Verbindung erfolgreich hergestellt");
        //     String query = "insert into dailyprices (Asset_ID, Price_date, open, high, low, close, volume)";

        //     PreparedStatement preparedStmt = conn.prepareStatement(query);
        //     preparedStmt.setInt(1, 0);
        //     preparedStmt.setDate(2, date);
        //     preparedStmt.setDouble(3, open);
        //     preparedStmt.setDouble(4, high);
        //     preparedStmt.setDouble(5, low);
        //     preparedStmt.setDouble(6, close);
        //     preparedStmt.setDouble(7, volume);
        //     preparedStmt.execute();
        //     conn.close();

        // } catch (Exception e) {
        //     //TODO: handle exception
        // }


        // try {
        //     Connection con = DriverManager.getConnection(url, user, pass);
        //     System.out.println("Verbindung erfolgreich hergestellt");
        //     Statement stm = con.createStatement();
        //     ResultSet rs = stm.executeQuery("SELECT * FROM assets;");
        //     while (rs.next()) {
        //         int id = rs.getInt("ID");
        //         String Name = rs.getString("Name");
        //         System.out.format(" %s\n",  Name);
        //     }
        // } catch (SQLException ex) {
        //     ex.printStackTrace();
        // }

        */
    }
    public void viewData()  {

        String url = "jdbc:mysql://185.188.250.67:3306/Se2Projekt";
        String user = "root";
        String pass = "StefanWolf2k21";


        try {
                 Connection con = DriverManager.getConnection(url, user, pass);
                 System.out.println("Verbindung erfolgreich hergestellt");
                 Statement stm = con.createStatement();
                 ResultSet rs = stm.executeQuery("SELECT * FROM dailyprices;");
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
    public void uploadData(ArrayList<Map<String, String>> data){





        ArrayList<Map<String, String>> stockValues = data;
        String url = "jdbc:mysql://185.188.250.67:3306/Se2Projekt";
        String user = "root";
        String pass = "StefanWolf2k21";

        int index = 1;
        for (Map<String,String> map : stockValues) {
            try {
                String date = map.get("date");
                System.out.println(date);
                double open = Double.parseDouble(map.get("open"));
                double high = Double.parseDouble(map.get("high"));
                double low = Double.parseDouble(map.get("low"));
                double close = Double.parseDouble(map.get("close"));
                double volume = Double.parseDouble(map.get("volume"));

                Connection conn = DriverManager.getConnection(url, user, pass);
                String query = "insert into dailyprices (Asset_ID, Price_date, open, high, low, close, volume) values (?, ?, ?, ?, ?, ?, ?)";

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
                conn.close();
            } catch (Exception e) {
                //TODO: handle exception
                System.out.println(e.getLocalizedMessage());
            }
        }

        System.out.println("Die Verbindung zur Datenbank wurde aufgehoben. Alle Daten wurden erfolgreich hochgeladen.");




    }
}



