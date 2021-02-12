package Database;

import java.sql.*;

public class DBDriver {

    public static void createNewDatabase(String fileName) {
        String url = "jdbc:sqlite:Database" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());

                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void connect() {
        Connection conn = null;
        Statement stmt = null;
        try {
            String url = "jdbc:sqlite:Database/chinook.db";

            //  Open connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

            // Execute query
            String query = "SELECT * FROM albums";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Parse results
            while(rs.next()) {
                String name = rs.getString("title");
                System.out.println(name);
            }

            // Clean up environment
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e2) {
                System.out.println(e2.getMessage());
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e3) {
                System.out.println(e3.getMessage());
            }
        }
    }
}
