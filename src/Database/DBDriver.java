package Database;
import Card.*;
import org.sqlite.Function;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBDriver {

    public static Connection conn = null;

    public static void connect() {
        try {
            String url = "jdbc:sqlite:src/Database/card_database.db";

            //  Open connection to the database
            conn = DriverManager.getConnection(url);

            if (!tableExists("cards")) {
                createCardsTable();
            }

            if(!tableExists("faces")) {
                createFacesTable();
            }

            if(!tableExists("deck")) {
                createDeckTable();
            }

            Function.create(conn, "REGEXP", new Function() {
                @Override
                protected void xFunc() throws SQLException {
                    String expression = value_text(0);
                    String value = value_text(1);
                    if (value == null)
                        value = "";

                    Pattern pattern=Pattern.compile(expression);
                    result(pattern.matcher(value).find() ? 1 : 0);
                }
            });

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

    private static void createDeckTable() {
        createTable("deck",
                "id TEXT NOT NULL PRIMARY KEY," +
                        "copies INTEGER not null");
    }

    private static void createFacesTable() {
        createTable("faces",
                "id TEXT not null," +
                        "front INTEGER not null," +
                        "image_url TEXT," +
                        "oracle_text TEXT," +
                        "power TEXT," +
                        "toughness TEXT," +
                        "mana_cost TEXT," +
                        "loyalty TEXT," +
                        "type_line TEXT not null," +
                        "w INTEGER," +
                        "u INTEGER," +
                        "b INTEGER," +
                        "r INTEGER," +
                        "g INTEGER");
    }

    private static void createCardsTable() {
        createTable("cards",
                        "id TEXT NOT NULL PRIMARY KEY," +
                        "name TEXT not null," +
                        "layout INTEGER not null," +
                        "cmc INTEGER not null," +
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
                        "rarity INTEGER not null");
    }

    public static UUID[] getDeckItems() {
        Statement stmt = null;
        Statement count = null;
        UUID[] results = null;
        try {
            stmt = conn.createStatement();
            count = conn.createStatement();
            ResultSet rs_count = count.executeQuery("SELECT count(id) FROM deck");

            if (!rs_count.next()) {
                return null;
            }

            results = new UUID[rs_count.getInt(1)];

            ResultSet rs = stmt.executeQuery("SELECT id FROM deck");

            // Parse results
            int i = 0;
            while(rs.next()) {
                results[i] = UUID.fromString(rs.getString("id"));
                i += 1;
            }

            rs.close();
            rs_count.close();
            count.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return results;
    }

    public static void setCopyNumber(UUID id, int copies) {
        PreparedStatement stmt = null;
        PreparedStatement stmt_delete = null;
        try {
            if (copies > 0) {
                stmt = conn.prepareStatement("REPLACE INTO deck VALUES(?, ?)");

                stmt.setString(1, id.toString());
                stmt.setInt(2, copies);

                System.out.println("Updated " + id + " to " + copies);
                stmt.executeUpdate();
            } else {
                stmt_delete = conn.prepareStatement("DELETE FROM deck WHERE id = ?");

                stmt_delete.setString(1, id.toString());

                stmt_delete.execute();
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static int getCopyNumber(UUID id) {
        Statement stmt = null;
        int result = 0;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT copies FROM deck WHERE id = '" + id.toString() + "';");
            if (rs.next()) {
                result = rs.getInt(1);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }

    public static void insertToFacesTable(UUID id, boolean front, String image_url, String oracle_text, String power, String toughness, String mana_cost, String loyalty, String type_line, boolean w, boolean u, boolean b, boolean r, boolean g) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("REPLACE INTO faces VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            // Set values
            stmt.setString(1, id.toString());
            stmt.setBoolean(2, front);
            stmt.setString(3, image_url);
            stmt.setString(4, oracle_text);
            stmt.setString(5, power);
            stmt.setString(6, toughness);
            stmt.setString(7, mana_cost);
            stmt.setString(8, loyalty);
            stmt.setString(9, type_line);
            stmt.setBoolean(10, w);
            stmt.setBoolean(11, u);
            stmt.setBoolean(12, b);
            stmt.setBoolean(13, r);
            stmt.setBoolean(14, g);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void insertToCardsTable(UUID id, String name, Layout layout, int cmc, boolean w_ci, boolean u_ci, boolean b_ci, boolean r_ci, boolean g_ci,
                                          Legality standard, Legality brawl, Legality pioneer, Legality historic, Legality modern, Legality pauper, Legality legacy, Legality penny, Legality vintage, Legality commander,
                                          Rarity rarity) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("REPLACE INTO cards VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            // Set values
            stmt.setString(1, id.toString());
            stmt.setString(2, name);
            stmt.setInt(3, layout.toInt());
            stmt.setInt(4, cmc);
            stmt.setBoolean(5, w_ci);
            stmt.setBoolean(6, u_ci);
            stmt.setBoolean(7, b_ci);
            stmt.setBoolean(8, r_ci);
            stmt.setBoolean(9, g_ci);
            stmt.setInt(10, standard.toInt());
            stmt.setInt(11, brawl.toInt());
            stmt.setInt(12, pioneer.toInt());
            stmt.setInt(13, historic.toInt());
            stmt.setInt(14, modern.toInt());
            stmt.setInt(15, pauper.toInt());
            stmt.setInt(16, legacy.toInt());
            stmt.setInt(17, penny.toInt());
            stmt.setInt(18, vintage.toInt());
            stmt.setInt(19, commander.toInt());
            stmt.setInt(20, rarity.toInt());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }



    public static String constructSQLSearch(String name, String oracle_text, Format format, Color[] colors, String type_line, NumericArg[] cmc, String mana_cost, NumericArg[] loyalty, NumericArg[] power, NumericArg[] toughness, Rarity[] rarity, boolean color_id_true, String sortCol, boolean desc) {
        // Mask unused fields
        boolean name_b = !(name == null || name.isBlank());
        boolean oracle_text_b = !(oracle_text == null || oracle_text.isBlank());
        boolean format_b = format != null;
        boolean colors_b = colors != null;
        boolean type_line_b = !(type_line == null || type_line.isBlank());
        boolean cmc_b = cmc != null;
        boolean mana_cost_b = !(mana_cost == null || mana_cost.isBlank());
        boolean loyalty_b = loyalty != null;
        boolean power_b = power != null;
        boolean toughness_b = toughness != null;
        boolean rarity_b = rarity != null;

        // Where filter
        StringBuilder where = new StringBuilder("WHERE ");

        // If all search parameters are null, return null;
        if (!name_b && !oracle_text_b && !format_b && !colors_b && !type_line_b && !cmc_b && !mana_cost_b && !loyalty_b && !power_b && !toughness_b && !rarity_b)  return null;

        // If there are parameters that require the faces table, join
        boolean join = oracle_text_b || power_b || toughness_b || mana_cost_b || loyalty_b || type_line_b || !color_id_true;

        // Format
        if (format_b) {
            // Append "AND" if other args have been added
            if (where.length() > 6) {
                where.append("AND ");
            }
            where.append(format).append(" = ").append(Legality.LEGAL.toInt()).append(' ');
        }

        // Converted Mana Cost
        if (cmc_b) {
            if (cmc.length > 0) {
                for (NumericArg arg:cmc) {
                    if (arg == null || arg.getOp() == null) continue;
                    // Append "AND" if other args have been added
                    if (where.length() > 6) {
                        where.append("AND ");
                    }

                    int val = arg.getVal();
                    Operator op = arg.getOp();

                    where.append("cmc ").append(op).append(" ").append(val).append(" ");
                }
            }
        }

        // Color / Identity
        if (colors_b) {
            boolean[] colors_boolean_array = new boolean[5];

            // Only look for colors if the array is not empty
            if (colors.length > 0) {
                for (Color c:colors) {
                    colors_boolean_array[c.toInt()] = true;
                }
            }

            // Append "AND" if other args have been added
            if (where.length() > 6) {
                where.append("AND ");
            }

            // Color Identity
            if (color_id_true) {
                // Check if it is searching for colorless - if any of the values are true, we aren't.
                boolean colorless = true;
                for (boolean b : colors_boolean_array) if (b) { colorless = false; break; }

                // If it isn't colorless, include in the query. Otherwise, the query will just be this, handled below.
                if (!colorless) {
                    // Append "AND" if other args have been added
                    where.append("( ( ");

                    int i = 0;
                    for (boolean color:colors_boolean_array) {
                        // Append "AND" if other args have been added
                        if (where.length() > 6 && i > 0) {
                            where.append("AND ");
                        }
                        where.append(Color.fromInt(i).toString().toLowerCase()).append("_ci = 0 ");
                        i += 1;
                    }
                    where.append(") OR ");
                }

                where.append("( ");
                int i = 0;
                for (boolean color:colors_boolean_array) {
                    // Append "AND" if other args have been added
                    if (where.length() > 6 && i > 0) {
                        where.append("AND ");
                    }
                    where.append(Color.fromInt(i).toString().toLowerCase()).append("_ci = ").append(color ? 1:0).append(" ");
                    i += 1;
                }
                where.append(") ");

                if (!colorless) where.append(") ");
                // Colors
            } else {
                where.append("( ");
                int i = 0;
                for (boolean color:colors_boolean_array) {
                    // Append "AND" if other args have been added
                    if (where.length() > 6 && i > 0) {
                        where.append("AND ");
                    }
                    where.append(Color.fromInt(i).toString().toLowerCase()).append(" = ").append(color ? 1:0).append(" ");
                    i += 1;
                }
                where.append(") ");
            }
        }

        // Loyalty
        if (loyalty_b) {
            if (loyalty.length > 0) {
                for (NumericArg arg:loyalty) {
                    if (arg == null || arg.getOp() == null) continue;
                    // Append "AND" if other args have been added
                    if (where.length() > 6) {
                        where.append("AND ");
                    }

                    int val = arg.getVal();
                    Operator op = arg.getOp();

                    where.append("loyalty ").append(op).append(" ").append(val).append(" ");
                }
            }
        }

        // Power
        if (power_b) {
            if (power.length > 0) {
                for (NumericArg arg:power) {
                    if (arg == null || arg.getOp() == null) continue;
                    // Append "AND" if other args have been added
                    if (where.length() > 6) {
                        where.append("AND ");
                    }

                    int val = arg.getVal();
                    Operator op = arg.getOp();

                    where.append("power ").append(op).append(" ").append(val).append(" ");
                }
            }
        }

        // Toughness
        if (toughness_b) {
            if (toughness.length > 0) {
                for (NumericArg arg:toughness) {
                    if (arg == null || arg.getOp() == null) continue;
                    // Append "AND" if other args have been added
                    if (where.length() > 6) {
                        where.append("AND ");
                    }

                    int val = arg.getVal();
                    Operator op = arg.getOp();

                    where.append("toughness ").append(op).append(" ").append(val).append(" ");
                }
            }
        }

        // Rarity
        if (rarity_b) {
            if (rarity.length == 1) {
                // Only one selected - simply add

                // Append "AND" if other args have been added
                if (where.length() > 6) {
                    where.append("AND ");
                }
                where.append("rarity = ").append(rarity[0].toInt()).append(" ");
            } else if (rarity.length > 1) {
                // More than one selected - use (_ OR _)

                // Append "AND" if other args have been added
                if (where.length() > 6) {
                    where.append("AND ");
                }
                where.append("(");
                for (Rarity val:rarity) {
                    if (!val.equals(rarity[0])) {
                        where.append(" OR ");
                    }
                    where.append("rarity = ").append(val.toInt());
                }
                where.append(")");
            }
        }



        // Name
        if (name_b) {
            String[] args = parseStringArgs(name,"A-z\\s\\/,.'-:!áàâãéíöúüûŠ?\"&");
            if (!(args == null || args.length == 0)) {
                for (String arg : args) {
                    // Append "AND" if other args have been added
                    if (where.length() > 6) {
                        where.append("AND ");
                    }
                    where.append("name ");

                    // if it begins with -, negate it (as long as it's not just "-")
                    if (arg.charAt(0) == '-' & arg.length() > 1) {
                        where.append("NOT ");
                        // trim dash
                        arg = arg.substring(1);
                    }
                    // add arg
                    where.append("LIKE '%").append(arg).append("%' ");
                }
            }
        }

        // Oracle Text: \w\s\/,'-.:!"{};+—•
        if (oracle_text_b) {
            String [] args = parseStringArgs(oracle_text,"\\w\\s\\/,'-.:!\"{};+—•");
            if (!(args == null || args.length == 0)) {
                for (String arg : args) {
                    // Append "AND" if other args have been added
                    if (where.length() > 6) {
                        where.append("AND ");
                    }
                    where.append("oracle_text ");

                    // if it begins with -, negate it (as long as it's not just "-")
                    if (arg.charAt(0) == '-' & arg.length() > 1) {
                        where.append("NOT ");
                        // trim dash
                        arg = arg.substring(1);
                    }
                    // add arg
                    where.append("LIKE '%").append(arg).append("%' ");
                }
            }
        }



        // Type Line: A-z\s—\/-'
        if (type_line_b) {
            String[] args = parseStringArgs(type_line, "A-z\\s\"/'-");
            if (!(args == null || args.length == 0)) {
                for (String arg : args) {
                    // Append "AND" if other args have been added
                    if (where.length() > 6) {
                        where.append("AND ");
                    }
                    where.append("type_line ");

                    // if it begins with -, negate it (as long as it's not just "-")
                    if (arg.charAt(0) == '-' & arg.length() > 1) {
                        where.append("NOT ");
                        // trim dash
                        arg = arg.substring(1);
                    }
                    // add arg
                    where.append("LIKE '%").append(arg).append("%' ");
                }
            }
        }



        // Mana Cost: {}0-9WUBRGXS"-
        if (mana_cost_b) {
            // Combine any terms and order in terms of precedence
            mana_cost = Card.simplifyManaCost(mana_cost);

            // Continue only if non-blank/null
            if (!(mana_cost == null || mana_cost.isBlank())) {
                String[] parts = mana_cost.replaceAll("}\\{", "} {").split(" ");
                ArrayList<String> args = new ArrayList<>();
                if (parts.length > 1) {
                    String temp = "";
                    for (int i = 1; i < parts.length; i++) {
                        String lastArg = parts[i - 1];
                        String currentArg = parts[i];

                        // If they are the same, add them together
                        if (currentArg.equals(lastArg)) {
                            if (temp.equals("")) {
                                temp = currentArg + currentArg;
                            } else {
                                temp += currentArg;
                            }
                        }
                        // Different terms - clear buffer and add
                        else {
                            if (temp.equals("")) {
                                args.add(lastArg);
                            } else {
                                args.add(temp);
                                temp = "";
                            }
                        }
                        if (currentArg == parts[parts.length - 1]) {
                            if (temp.equals("")) {
                                args.add(currentArg);
                            } else {
                                args.add(temp);
                                temp = "";
                            }
                        }

                    }
                } else {
                    // Only one term - add to args
                    args.add(parts[0]);
                }

                Pattern p = Pattern.compile("\\{\\d}");
                Matcher m = null;
                for (String arg:args) {
                    // Append "AND" if other args have been added
                    if (where.length() > 6) {
                        where.append("AND ");
                    }

                    m = p.matcher(arg);
                    // Check if it's the generic cost
                    if (m.find()) {
                        int gen_cost = Integer.parseInt(arg.substring(m.start() + 1, m.end() - 1));
                        where.append("(");
                        // Only return equal or higher values (No card has higher than 16 generic mana)
                        for (int i = gen_cost; i < 20; i++) {
                            if (i > gen_cost) where.append(" OR ");
                            where.append("mana_cost LIKE ").append("'%{").append(i).append("}%'");
                        }
                        where.append(") ");
                    } else {
                        where.append("mana_cost LIKE '%").append(arg).append("%' ");
                    }
                }
            }
        }


        if (where.length() <= 6) {
            // No terms entered, return null
            return null;
        }

        String removeNulls = sortCol.equals("power") || sortCol.equals("toughness") ? " NULLS LAST" : "";
        if (join) {
            return "SELECT DISTINCT(id) FROM cards LEFT JOIN faces USING (id) " + where.toString().trim() + " ORDER BY " + sortCol + (desc ? " DESC" : "") + removeNulls + ", name;";
        } else {
            return "SELECT DISTINCT(id) FROM cards " + where.toString().trim() + " ORDER BY " + sortCol + (desc ? " DESC" : "") + removeNulls + ", name;";
        }
    }

    private static String[] parseStringArgs(String args, String acceptableChars) {
        if (args == null || args.isBlank()) return null;
        args = args.replaceAll("[^" + acceptableChars + "]", "").toLowerCase().trim();
        args = args.replaceAll("'", "''");
        if (args.isBlank()) return null;
        StringBuilder nameBuilder = new StringBuilder();
        ArrayList<String> stringArgs = new ArrayList<>();
        // Split by space, unless it is within quotes
        boolean inQuotes = false;
        for (char c:args.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ' ') {
                if (!inQuotes) {
                    // Add arg
                    stringArgs.add(nameBuilder.toString());
                    // Clear buffer
                    nameBuilder.setLength(0);
                } else {
                    // Otherwise, add the space
                    nameBuilder.append(c);
                }
            } else {
                nameBuilder.append(c);
            }
        }
        // Finish last word
        stringArgs.add(nameBuilder.toString());

        return stringArgs.toArray(new String[0]);
    }

    public static UUID[] searchIDs (String query) {
        Statement stmt = null;
        Statement count = null;
        UUID[] results = null;

        try {
            // Execute query
            stmt = conn.createStatement();
            count = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSet rs_count = count.executeQuery(query.toLowerCase().replace("select distinct(id)", "SELECT COUNT(DISTINCT(id))"));
            results = new UUID[rs_count.getInt(1)];

            // Parse results
            int i = 0;
            while(rs.next()) {
                results[i] = UUID.fromString(rs.getString("id"));
                i += 1;
            }

            // Clean up environment
            rs.close();
            stmt.close();
            count.close();
            rs_count.close();

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

    public static Card getCard(UUID id) {
        Statement stmt = null;
        Card result = null;
        try {
            // Execute query
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, cmc, w_ci, u_ci, b_ci, r_ci, g_ci, layout, front, standard, brawl, pioneer, historic, modern, pauper, legacy, penny, vintage, commander, mana_cost, name, image_url, type_line FROM cards LEFT JOIN faces USING (id) WHERE id = '" + id + "' ORDER BY front");

            String back_image = null;
            while (rs.next()) {
                if (rs.getBoolean("front")) {
                    Layout layout = Layout.fromInt(rs.getInt("layout"));

                    result = new Card(id,
                            rs.getInt("cmc"),
                            rs.getBoolean("w_ci"),
                            rs.getBoolean("u_ci"),
                            rs.getBoolean("b_ci"),
                            rs.getBoolean("r_ci"),
                            rs.getBoolean("g_ci"),
                            layout == Layout.MODAL_DFC || layout == Layout.TRANSFORM,
                            Legality.fromInt(rs.getInt("standard")),
                            Legality.fromInt(rs.getInt("brawl")),
                            Legality.fromInt(rs.getInt("pioneer")),
                            Legality.fromInt(rs.getInt("historic")),
                            Legality.fromInt(rs.getInt("modern")),
                            Legality.fromInt(rs.getInt("pauper")),
                            Legality.fromInt(rs.getInt("legacy")),
                            Legality.fromInt(rs.getInt("penny")),
                            Legality.fromInt(rs.getInt("vintage")),
                            Legality.fromInt(rs.getInt("commander")),
                            rs.getString("mana_cost"),
                            rs.getString("name"),
                            rs.getString("type_line"),
                            rs.getString("image_url"),
                            back_image);
                } else {
                    back_image = rs.getString("image_url");
                }
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return result;
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
