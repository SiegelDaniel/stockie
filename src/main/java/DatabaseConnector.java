import java.sql.*;

public class DatabaseConnector {
    public DatabaseConnector(){
        String url = "jdbc:mysql://185.188.250.67:3306/Se2Projekt";
        String user = "root";
        String pass = "StefanWolf2k21";
        // Verbindung aufbauen
        //String db = "Se2Projekt";
        try {
            Connection con = DriverManager.getConnection(url, user, pass);
            System.out.println("Verbindung erfolgreich hergestellt");
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM assets;");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String Name = rs.getString("Name");
                System.out.format(" %s\n",  Name);
            }





        } catch (SQLException ex) {
            ex.printStackTrace();
        }


    }
    }



