package Database;
import Card.*;

import java.sql.*;
import java.util.UUID;

public class DBDriver {

    public static Connection conn = null;

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
        try {
            String url = "jdbc:sqlite:Database/card_database.db";

            //  Open connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static Card[] search_cards(String query) {
        Statement stmt = null;
        Card[] results = null;

        try {
            // Execute query
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSet count = stmt.executeQuery(query.replace("*", "COUNT(*)"));


            results = new Card[count.getInt(1)];

            // Parse results
            int i = 0;
            while(rs.next()) {
                final UUID id = UUID.fromString(rs.getString("id"));
                final Language lang = Language.fromInt(rs.getInt("lang"));
                final Double cmc = rs.getDouble("cmc");
                final boolean w_c = rs.getBoolean("w_c");
                final boolean u_c = rs.getBoolean("u_c");
                final boolean b_c = rs.getBoolean("b_c");
                final boolean r_c = rs.getBoolean("r_c");
                final boolean g_c = rs.getBoolean("g_c");
                final boolean w_ci = rs.getBoolean("w_ci");
                final boolean u_ci = rs.getBoolean("u_ci");
                final boolean b_ci = rs.getBoolean("b_ci");
                final boolean r_ci = rs.getBoolean("r_ci");
                final boolean g_ci = rs.getBoolean("g_ci");
                final Layout layout = Layout.fromInt(rs.getInt("layout"));
                final Legality standard = Legality.fromInt(rs.getInt("standard"));
                final Legality brawl = Legality.fromInt(rs.getInt("brawl"));
                final Legality pioneer = Legality.fromInt(rs.getInt("pioneer"));
                final Legality historic = Legality.fromInt(rs.getInt("historic"));
                final Legality modern = Legality.fromInt(rs.getInt("modern"));
                final Legality pauper = Legality.fromInt(rs.getInt("pauper"));
                final Legality legacy = Legality.fromInt(rs.getInt("legacy"));
                final Legality penny = Legality.fromInt(rs.getInt("penny"));
                final Legality vintage = Legality.fromInt(rs.getInt("vintage"));
                final Legality commander = Legality.fromInt(rs.getInt("commander"));
                final String mana_cost = rs.getString("mana_cost");
                final String name = rs.getString("name");
                final String power = rs.getString("power");
                final String toughness = rs.getString("toughness");
                final String type_line = rs.getString("type_line");

                results[i] = new Card(id, lang, cmc, w_c, u_c, b_c, r_c, g_c, w_ci, u_ci, b_ci, r_ci, g_ci, layout, standard, brawl, pioneer, historic, modern, pauper, legacy, penny, vintage, commander, mana_cost, name, power, toughness, type_line);
                i += 1;
            }

            // Clean up environment
            rs.close();
            stmt.close();

        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return results;
    }

    public static void disconnect() {
        try {
            if (conn != null)
                conn.close();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
