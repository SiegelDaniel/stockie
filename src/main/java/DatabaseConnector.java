import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
  public DatabaseConnector() {
    String url = "jdbc:mysql://185.188.250.67:3306";
    String user = "root";
    String pass = "StefanWolf2k21";
    // Verbindung aufbauen
    try {
      Connection con = DriverManager.getConnection(url, user, pass);
      System.out.println("Verbindung erfolgreich hergestellt");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
}
