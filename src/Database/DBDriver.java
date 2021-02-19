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
            String url = "jdbc:sqlite:src/Database/card_database.db";

            //  Open connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

            System.out.println("Checking for card table...");
            if (!tableExists("cards")) {
                System.out.println("cards does not exist");
                createCardsTable();
                System.out.println("Created cards Table");
            } else {
                System.out.println("cards exists");
            }

            System.out.println("Checking for faces table...");
            if(!tableExists("faces")) {
                System.out.println("faces does not exist");
                createFacesTable();
                System.out.println("Created faces Table");
            } else {
                System.out.println("faces exists");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean tableExists(String tableName) {
        PreparedStatement preparedStmt = null;
        boolean result = false;

        try {
            preparedStmt = conn.prepareStatement("SELECT COUNT(name) FROM sqlite_master WHERE type='table' AND name=?");
            preparedStmt.setString(1, tableName);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                result = rs.getBoolean(1);
            }
            rs.close();
            preparedStmt.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            try {
                if (preparedStmt != null)
                    preparedStmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return result;
    }

    private static void createFacesTable() {
        createTable("faces",
                "id TEXT not null," +
                        "front INTEGER not null," +
                        "image_url TEXT not null," +
                        "oracle_text TEXT," +
                        "power TEXT," +
                        "toughness TEXT");
    }

    private static void createCardsTable() {
        createTable("cards",
                        "id TEXT PRIMARY KEY," +
                        "name TEXT not null," +
                        "layout INTEGER not null," +
                        "cmc INTEGER not null," +
                        "type_line TEXT not null," +
                        "w_ci INTEGER not null," +
                        "u_ci INTEGER not null," +
                        "b_ci INTEGER not null," +
                        "r_ci INTEGER not null," +
                        "g_ci INTEGER not null," +
                        "standard INTEGER not null," +
                        "brawl INTEGER not null," +
                        "pioneer INTEGER not null," +
                        "historic INTEGER not null," +
                        "modern INTEGER not null," +
                        "pauper INTEGER not null," +
                        "legacy INTEGER not null," +
                        "penny INTEGER not null," +
                        "vintage INTEGER not null," +
                        "commander INTEGER not null," +
                        "rarity INTEGER not null," +
                        "image_url TEXT not null," +
                        "mana_cost TEXT," +
                        "power TEXT," +
                        "toughness TEXT");
    }

    public static void insertToFacesTable(UUID id, boolean front, String image_url, String oracle_text, String power, String toughness) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("REPLACE INTO faces VALUES(?, ?, ?, ?, ?, ?)");

            // Set values
            stmt.setString(1, id.toString());
            stmt.setBoolean(2, front);
            stmt.setString(3, image_url);
            stmt.setString(4, oracle_text);
            stmt.setString(5, power);
            stmt.setString(6, toughness);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void insertToCardsTable(UUID id, String name, Layout layout, int cmc, String type_line, boolean w_ci, boolean u_ci, boolean b_ci, boolean r_ci, boolean g_ci,
                                          Legality standard, Legality brawl, Legality pioneer, Legality historic, Legality modern, Legality pauper, Legality legacy, Legality penny, Legality vintage, Legality commander,
                                          Rarity rarity, String image_url, String mana_cost, String power, String toughness) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("REPLACE INTO cards VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            // Set values
            stmt.setString(1, id.toString());
            stmt.setString(2, name);
            stmt.setInt(3, Layout.toInt(layout));
            stmt.setInt(4, cmc);
            stmt.setString(5, type_line);
            stmt.setBoolean(6, w_ci);
            stmt.setBoolean(7, u_ci);
            stmt.setBoolean(8, b_ci);
            stmt.setBoolean(9, r_ci);
            stmt.setBoolean(10, g_ci);
            stmt.setInt(11, Legality.toInt(standard));
            stmt.setInt(12, Legality.toInt(brawl));
            stmt.setInt(13, Legality.toInt(pioneer));
            stmt.setInt(14, Legality.toInt(historic));
            stmt.setInt(15, Legality.toInt(modern));
            stmt.setInt(16, Legality.toInt(pauper));
            stmt.setInt(17, Legality.toInt(legacy));
            stmt.setInt(18, Legality.toInt(penny));
            stmt.setInt(19, Legality.toInt(vintage));
            stmt.setInt(20, Legality.toInt(commander));
            stmt.setInt(21, Rarity.toInt(rarity));
            stmt.setString(22, image_url);
            stmt.setString(23, mana_cost);
            stmt.setString(24, power);
            stmt.setString(25, toughness);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private static void createTable(String tableName, String columns) {
        Statement stmt = null;
        String query = "CREATE TABLE " + tableName + "(" + columns + ")";

        try {
            stmt = conn.createStatement();
            stmt.execute(query);

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
    }

    public static Card[] search_cards(String query) {
        Statement stmt = null;
        Statement count = null;
        Card[] results = null;

        try {
            // Execute query
            stmt = conn.createStatement();
            count = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSet rs_count = count.executeQuery(query.replace("*", "COUNT(*)"));

            results = new Card[rs_count.getInt(1)];

            // Parse results
            int i = 0;
            while(rs.next()) {
                final UUID id = UUID.fromString(rs.getString("id"));
                final Integer cmc = rs.getInt("cmc");
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

                results[i] = new Card(id, cmc, w_ci, u_ci, b_ci, r_ci, g_ci, layout, standard, brawl, pioneer, historic, modern, pauper, legacy, penny, vintage, commander, mana_cost, name, power, toughness, type_line);
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
