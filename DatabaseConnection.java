import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/IngreGenius";
    private static final String USER = "root";
    private static final String PASSWORD = "Heba-5252";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
            
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }


    public static void testConnection() {
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            System.out.println("✅ Connection successful!");
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println("⚠️ Error closing connection: " + e.getMessage());
            }
        } else {
            System.out.println("❌ Failed to connect to the database.");
        }
    }

  
}
    