package Database;

import Card.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DBDriverTest {

    @Test
    void constructSQLSearch() {
        // All null
        assertNull(DBDriver.constructSQLSearch(null, null, null, null, null, null, null, null, null, null, null, true, "name", false));

        // Name field
        // Null
        assertNull(DBDriver.constructSQLSearch("", null, null, null, null, null,null,null,null,null, null, true, "name", false));
        assertNull(DBDriver.constructSQLSearch(" ", null, null, null, null, null,null,null,null,null, null, true, "name", false));
        assertNull(DBDriver.constructSQLSearch("\n", null, null, null, null, null,null,null,null,null, null, true, "name", false));
        assertNull(DBDriver.constructSQLSearch("|", null, null, null, null, null,null,null,null,null, null, true, "name", false));
        // Args
        assertEquals("SELECT id FROM cards WHERE name LIKE '%arg%';", DBDriver.constructSQLSearch("arg", null, null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards WHERE name NOT LIKE '%arg%';", DBDriver.constructSQLSearch("-arg", null, null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards WHERE name LIKE '%-%';", DBDriver.constructSQLSearch("-", null, null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards WHERE name NOT LIKE '%-%';", DBDriver.constructSQLSearch("--", null, null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards WHERE name LIKE '%i-%';", DBDriver.constructSQLSearch("i-", null, null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards WHERE name LIKE '%i-i%';", DBDriver.constructSQLSearch("i-i", null, null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards WHERE name LIKE '%i%';", DBDriver.constructSQLSearch(" i", null, null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards WHERE name LIKE '%i%';", DBDriver.constructSQLSearch("i ", null, null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards WHERE name LIKE '%i%';", DBDriver.constructSQLSearch(" i ", null, null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards WHERE name LIKE '%i%' AND name LIKE '%i%';", DBDriver.constructSQLSearch("i i", null, null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards WHERE name LIKE '%i%' AND name NOT LIKE '%i%';", DBDriver.constructSQLSearch("i -i", null, null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards WHERE name LIKE '%i%';", DBDriver.constructSQLSearch("\"i\"", null, null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards WHERE name LIKE '%i i%';", DBDriver.constructSQLSearch("\"i i\"", null, null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards WHERE name LIKE '%i -i%';", DBDriver.constructSQLSearch("\"i -i\"", null, null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards WHERE name LIKE '%é%';", DBDriver.constructSQLSearch("é", null, null, null, null, null,null,null,null,null, null, true, "name", false));



        // Oracle Text field
        // Null
        assertNull(DBDriver.constructSQLSearch(null, null, null, null, null, null, null, null, null, null, null, true, "name", false));
        assertNull(DBDriver.constructSQLSearch(null, "", null, null, null, null,null,null,null,null, null, true, "name", false));
        assertNull(DBDriver.constructSQLSearch(null, " ", null, null, null, null,null,null,null,null, null, true, "name", false));
        assertNull(DBDriver.constructSQLSearch(null, "\n", null, null, null, null,null,null,null,null, null, true, "name", false));
        assertNull(DBDriver.constructSQLSearch(null, "|", null, null, null, null,null,null,null,null, null, true, "name", false));
        // Args
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE oracle_text LIKE '%arg%';", DBDriver.constructSQLSearch(null, "arg", null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE oracle_text NOT LIKE '%arg%';", DBDriver.constructSQLSearch(null, "-arg", null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE oracle_text LIKE '%-%';", DBDriver.constructSQLSearch(null, "-", null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE oracle_text NOT LIKE '%-%';", DBDriver.constructSQLSearch(null, "--", null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE oracle_text LIKE '%i-%';", DBDriver.constructSQLSearch(null, "i-", null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE oracle_text LIKE '%i-i%';", DBDriver.constructSQLSearch(null, "i-i", null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE oracle_text LIKE '%i%';", DBDriver.constructSQLSearch(null, " i", null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE oracle_text LIKE '%i%';", DBDriver.constructSQLSearch(null, "i ", null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE oracle_text LIKE '%i%';", DBDriver.constructSQLSearch(null, " i ", null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE oracle_text LIKE '%i%' AND oracle_text LIKE '%i%';", DBDriver.constructSQLSearch(null, "i i", null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE oracle_text LIKE '%i%' AND oracle_text NOT LIKE '%i%';", DBDriver.constructSQLSearch(null, "i -i", null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE oracle_text LIKE '%i%';", DBDriver.constructSQLSearch(null, "\"i\"", null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE oracle_text LIKE '%i i%';", DBDriver.constructSQLSearch(null, "\"i i\"", null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE oracle_text LIKE '%i -i%';", DBDriver.constructSQLSearch(null, "\"i -i\"", null, null, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE oracle_text LIKE '%:%';", DBDriver.constructSQLSearch(null, ":", null, null, null, null,null,null,null,null, null, true, "name", false));


        // Format field
        // Args
        assertEquals("SELECT id FROM cards WHERE commander = " + Legality.LEGAL.toInt() + ";", DBDriver.constructSQLSearch(null, null, Format.COMMANDER, null, null, null, null, null, null, null, null, true, "name", false));
        assertEquals("SELECT id FROM cards WHERE pauper = " + Legality.LEGAL.toInt() + ";", DBDriver.constructSQLSearch(null, null, Format.PAUPER, null, null, null, null, null, null, null, null, true, "name", false));


        // Color Identity field
        // Args
        assertEquals("SELECT id FROM cards WHERE ( w_ci = 0 AND u_ci = 0 AND b_ci = 0 AND r_ci = 0 AND g_ci = 0 );", DBDriver.constructSQLSearch(null, null, null, new Color[0], null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards WHERE ( w_ci = 0 AND u_ci = 0 AND b_ci = 0 AND r_ci = 0 AND g_ci = 0 );", DBDriver.constructSQLSearch(null, null, null, new Color[]{}, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards WHERE ( ( w_ci = 0 AND u_ci = 0 AND b_ci = 0 AND r_ci = 0 AND g_ci = 0 ) OR ( w_ci = 1 AND u_ci = 0 AND b_ci = 0 AND r_ci = 0 AND g_ci = 0 ) );", DBDriver.constructSQLSearch(null, null, null, new Color[]{Color.WHITE}, null, null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards WHERE ( ( w_ci = 0 AND u_ci = 0 AND b_ci = 0 AND r_ci = 0 AND g_ci = 0 ) OR ( w_ci = 1 AND u_ci = 1 AND b_ci = 1 AND r_ci = 1 AND g_ci = 1 ) );", DBDriver.constructSQLSearch(null, null, null, new Color[]{Color.BLACK, Color.BLUE, Color.GREEN, Color.RED, Color.WHITE}, null, null, null,null,null,null,null, true, "name", false));
        // Colors
        // Args
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE ( w = 0 AND u = 0 AND b = 0 AND r = 0 AND g = 0 );", DBDriver.constructSQLSearch(null, null, null, new Color[0], null, null,null,null,null,null, null, false, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE ( w = 0 AND u = 0 AND b = 0 AND r = 0 AND g = 0 );", DBDriver.constructSQLSearch(null, null, null, new Color[]{}, null, null,null,null,null,null, null, false, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE ( w = 1 AND u = 0 AND b = 0 AND r = 0 AND g = 0 );", DBDriver.constructSQLSearch(null, null, null, new Color[]{Color.WHITE}, null, null,null,null,null,null, null, false, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE ( w = 1 AND u = 1 AND b = 1 AND r = 1 AND g = 1 );", DBDriver.constructSQLSearch(null, null, null, new Color[]{Color.BLACK, Color.BLUE, Color.GREEN, Color.RED, Color.WHITE}, null, null, null,null,null,null,null, false, "name", false));



        // Type Line field
        // Null
        assertNull(DBDriver.constructSQLSearch(null, null, null, null, "", null,null,null,null,null, null, true, "name", false));
        assertNull(DBDriver.constructSQLSearch(null, null, null, null, " ", null,null,null,null,null, null, true, "name", false));
        assertNull(DBDriver.constructSQLSearch(null, null, null, null, "\n", null,null,null,null,null, null, true, "name", false));
        assertNull(DBDriver.constructSQLSearch(null, null, null, null, "|", null,null,null,null,null, null, true, "name", false));
        // Args
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE type_line LIKE '%arg%';", DBDriver.constructSQLSearch(null, null, null, null, "arg", null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE type_line NOT LIKE '%arg%';", DBDriver.constructSQLSearch(null, null, null, null, "-arg", null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE type_line LIKE '%-%';", DBDriver.constructSQLSearch(null, null, null, null, "-", null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE type_line NOT LIKE '%-%';", DBDriver.constructSQLSearch(null, null, null, null, "--", null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE type_line LIKE '%i-%';", DBDriver.constructSQLSearch(null, null, null, null, "i-", null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE type_line LIKE '%i-i%';", DBDriver.constructSQLSearch(null, null, null, null, "i-i", null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE type_line LIKE '%i%';", DBDriver.constructSQLSearch(null, null, null, null, " i", null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE type_line LIKE '%i%';", DBDriver.constructSQLSearch(null, null, null, null, "i ", null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE type_line LIKE '%i%';", DBDriver.constructSQLSearch(null, null, null, null, " i ", null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE type_line LIKE '%i%' AND type_line LIKE '%i%';", DBDriver.constructSQLSearch(null, null, null, null, "i i", null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE type_line LIKE '%i%' AND type_line NOT LIKE '%i%';", DBDriver.constructSQLSearch(null, null, null, null, "i -i", null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE type_line LIKE '%i%';", DBDriver.constructSQLSearch(null, null, null, null, "\"i\"", null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE type_line LIKE '%i i%';", DBDriver.constructSQLSearch(null, null, null, null, "\"i i\"", null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE type_line LIKE '%i -i%';", DBDriver.constructSQLSearch(null, null, null, null, "\"i -i\"", null,null,null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE type_line LIKE '%'%';", DBDriver.constructSQLSearch(null, null, null, null, "'", null,null,null,null,null, null, true, "name", false));


        // Converted Mana Cost
        // Null
        assertNull(DBDriver.constructSQLSearch(null, null, null, null, null, new NumericArg[0],null,null,null,null, null, true, "name", false));
        assertNull(DBDriver.constructSQLSearch(null, null, null, null, null, new NumericArg[]{},null,null,null,null, null, true, "name", false));
        assertNull(DBDriver.constructSQLSearch(null, null, null, null, null, new NumericArg[]{new NumericArg()},null,null,null,null, null, true, "name", false));
        // A, truergs
        assertEquals("SELECT id FROM cards WHERE cmc = 2;", DBDriver.constructSQLSearch(null, null, null, null, null, new NumericArg[]{ new NumericArg(2, Operator.EQUAL)},null,null,null, null,null, true, "name", false));
        assertEquals("SELECT id FROM cards WHERE cmc > 2 AND cmc < 5;", DBDriver.constructSQLSearch(null, null, null, null, null, new NumericArg[]{ new NumericArg(2, Operator.GREATER_THAN), new NumericArg(5, Operator.LESS_THAN)},null, null,null,null,null, true, "name", false));
        assertEquals("SELECT id FROM cards WHERE cmc >= 2 AND cmc <= 5;", DBDriver.constructSQLSearch(null, null, null, null, null, new NumericArg[]{ new NumericArg(2, Operator.GREATER_THAN_EQUAL), new NumericArg(5, Operator.LESS_THAN_EQUAL)},null, null,null,null,null, true, "name", false));
        assertEquals("SELECT id FROM cards WHERE cmc != 0;", DBDriver.constructSQLSearch(null, null, null, null, null, new NumericArg[]{ new NumericArg(0, Operator.NOT_EQUAL)},null,null,null, null,null, true, "name", false));



        // Mana Cost field
        // Null
        assertNull(DBDriver.constructSQLSearch(null, null, null, null, null, null,"",null,null,null, null, true, "name", false));
        assertNull(DBDriver.constructSQLSearch(null, null, null, null, null, null," ",null,null,null, null, true, "name", false));
        assertNull(DBDriver.constructSQLSearch(null, null, null, null, null, null,"\n",null,null,null, null, true, "name", false));
        assertNull(DBDriver.constructSQLSearch(null, null, null, null, null, null,"|",null,null,null, null, true, "name", false));
        // Args
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE mana_cost LIKE '%{G}%';", DBDriver.constructSQLSearch(null, null, null, null, null, null,"{G}",null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE mana_cost LIKE '%{R}%' AND mana_cost LIKE '%{G}%';", DBDriver.constructSQLSearch(null, null, null, null, null, null,"\"{G}{R}\"",null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE mana_cost LIKE '%{G}{G}%';", DBDriver.constructSQLSearch(null, null, null, null, null, null,"{G}{G}",null,null,null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE (mana_cost LIKE '%{3}%' OR mana_cost LIKE '%{4}%' OR mana_cost LIKE '%{5}%' OR mana_cost LIKE '%{6}%' OR mana_cost LIKE '%{7}%' OR mana_cost LIKE '%{8}%' OR mana_cost LIKE '%{9}%' OR mana_cost LIKE '%{10}%' OR mana_cost LIKE '%{11}%' OR mana_cost LIKE '%{12}%' OR mana_cost LIKE '%{13}%' OR mana_cost LIKE '%{14}%' OR mana_cost LIKE '%{15}%' OR mana_cost LIKE '%{16}%' OR mana_cost LIKE '%{17}%' OR mana_cost LIKE '%{18}%' OR mana_cost LIKE '%{19}%') AND mana_cost LIKE '%{G}{G}%';", DBDriver.constructSQLSearch(null, null, null, null, null, null,"{3}{G}{G}",null,null,null, null, true, "name", false));



        // Loyalty
        // Null
        assertNull(DBDriver.constructSQLSearch(null, null, null, null, null, null,null, new NumericArg[0],null,null, null, true, "name", false));
        assertNull(DBDriver.constructSQLSearch(null, null, null, null, null, null,null, new NumericArg[]{},null,null, null, true, "name", false));
        assertNull(DBDriver.constructSQLSearch(null, null, null, null, null, null,null, new NumericArg[]{new NumericArg()},null,null, null, true, "name", false));
        // Args
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE loyalty = 2;", DBDriver.constructSQLSearch(null, null, null, null, null, null,null, new NumericArg[]{ new NumericArg(2, Operator.EQUAL)},null, null,null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE loyalty > 2 AND loyalty < 5;", DBDriver.constructSQLSearch(null, null, null, null, null, null, null, new NumericArg[]{ new NumericArg(2, Operator.GREATER_THAN), new NumericArg(5, Operator.LESS_THAN) }, null,null,null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE loyalty >= 2 AND loyalty <= 5;", DBDriver.constructSQLSearch(null, null, null, null, null, null,null, new NumericArg[]{ new NumericArg(2, Operator.GREATER_THAN_EQUAL), new NumericArg(5, Operator.LESS_THAN_EQUAL) }, null,null,null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE loyalty != 0;", DBDriver.constructSQLSearch(null, null, null, null, null, null,null, new NumericArg[]{ new NumericArg(0, Operator.NOT_EQUAL)},null, null,null, true, "name", false));



        // Power
        // Null
        assertNull(DBDriver.constructSQLSearch(null, null, null, null, null, null,null, null, new NumericArg[0],null,null, true, "name", false));
        assertNull(DBDriver.constructSQLSearch(null, null, null, null, null, null,null, null, new NumericArg[]{},null,null, true, "name", false));
        assertNull(DBDriver.constructSQLSearch(null, null, null, null, null, null,null, null, new NumericArg[]{new NumericArg()},null,null, true, "name", false));
        // Args
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE power = 2;", DBDriver.constructSQLSearch(null, null, null, null, null, null,null, null, new NumericArg[]{ new NumericArg(2, Operator.EQUAL)},null, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE power > 2 AND power < 5;", DBDriver.constructSQLSearch(null, null, null, null, null, null, null, null, new NumericArg[]{ new NumericArg(2, Operator.GREATER_THAN), new NumericArg(5, Operator.LESS_THAN) }, null,null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE power >= 2 AND power <= 5;", DBDriver.constructSQLSearch(null, null, null, null, null, null, null,null, new NumericArg[]{ new NumericArg(2, Operator.GREATER_THAN_EQUAL), new NumericArg(5, Operator.LESS_THAN_EQUAL) }, null,null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE power != 0;", DBDriver.constructSQLSearch(null, null, null, null, null, null,null, null, new NumericArg[]{ new NumericArg(0, Operator.NOT_EQUAL)},null, null, true, "name", false));



        // Toughness
        // Null
        assertNull(DBDriver.constructSQLSearch(null, null, null, null, null, null, null, null, null, new NumericArg[0],null, true, "name", false));
        assertNull(DBDriver.constructSQLSearch(null, null, null, null, null, null, null, null, null, new NumericArg[]{},null, true, "name", false));
        assertNull(DBDriver.constructSQLSearch(null, null, null, null, null, null, null, null, null, new NumericArg[]{new NumericArg()},null, true, "name", false));
        // Args
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE toughness = 2;", DBDriver.constructSQLSearch(null, null, null, null, null, null, null, null,null, new NumericArg[]{ new NumericArg(2, Operator.EQUAL)},null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE toughness > 2 AND toughness < 5;", DBDriver.constructSQLSearch(null, null, null, null, null, null, null, null, null, new NumericArg[]{ new NumericArg(2, Operator.GREATER_THAN), new NumericArg(5, Operator.LESS_THAN) }, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE toughness >= 2 AND toughness <= 5;", DBDriver.constructSQLSearch(null, null, null, null, null, null, null, null,null, new NumericArg[]{ new NumericArg(2, Operator.GREATER_THAN_EQUAL), new NumericArg(5, Operator.LESS_THAN_EQUAL) }, null, true, "name", false));
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE toughness != 0;", DBDriver.constructSQLSearch(null, null, null, null, null, null, null, null,null, new NumericArg[]{ new NumericArg(0, Operator.NOT_EQUAL)},null, true, "name", false));


        // Rarity
        assertNull(DBDriver.constructSQLSearch(null, null, null, null, null, null, null, null, null, null, new Rarity[0], true, "name", false));
        assertNull(DBDriver.constructSQLSearch(null, null, null, null, null, null, null, null, null, null, new Rarity[]{}, true, "name", false));
        // Args
        assertEquals("SELECT id FROM cards WHERE rarity = " + Rarity.COMMON.toInt() + ";", DBDriver.constructSQLSearch(null, null, null, null, null, null, null, null, null, null, new Rarity[]{Rarity.COMMON}, true, "name", false));
        assertEquals("SELECT id FROM cards WHERE (rarity = " + Rarity.COMMON.toInt() + " OR rarity = " + Rarity.UNCOMMON.toInt() + ");", DBDriver.constructSQLSearch(null, null, null, null, null, null, null, null, null, null, new Rarity[]{Rarity.COMMON, Rarity.UNCOMMON}, true, "name", false));

        // All args
        assertEquals("SELECT id FROM cards LEFT JOIN faces USING (id) WHERE name LIKE '%blim%' AND oracle_text LIKE '%player%' AND oracle_text LIKE '%discard%' AND commander = 0 AND ( ( w_ci = 0 AND u_ci = 0 AND b_ci = 0 AND r_ci = 0 AND g_ci = 0 ) OR ( w_ci = 0 AND u_ci = 0 AND b_ci = 1 AND r_ci = 1 AND g_ci = 0 ) ) AND type_line LIKE '%creature%' AND cmc >= 3 AND mana_cost LIKE '%{B}%' AND mana_cost LIKE '%{R}%' AND power <= 5 AND toughness > 1 AND (rarity = 1 OR rarity = 2 OR rarity = 3);", DBDriver.constructSQLSearch("Blim", "player discard", Format.COMMANDER, new Color[]{Color.RED, Color.BLACK}, "Creature", new NumericArg[]{new NumericArg(3, Operator.GREATER_THAN_EQUAL)}, "{R}{B}", null, new NumericArg[]{new NumericArg(5, Operator.LESS_THAN_EQUAL)}, new NumericArg[]{new NumericArg(1, Operator.GREATER_THAN)}, new Rarity[]{Rarity.UNCOMMON, Rarity.RARE, Rarity.MYTHIC}, true, "name", false));

    }
}