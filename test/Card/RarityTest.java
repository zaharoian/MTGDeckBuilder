package Card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RarityTest {

    @Test
    void fromInt() {
        assertEquals(Rarity.COMMON, Rarity.fromInt(0));
        assertEquals(Rarity.UNCOMMON, Rarity.fromInt(1));
        assertEquals(Rarity.RARE, Rarity.fromInt(2));
        assertEquals(Rarity.MYTHIC, Rarity.fromInt(3));
        assertNull(Rarity.fromInt(-1));
        assertNull(Rarity.fromInt(4));
    }

    @Test
    void toInt() {
        assertEquals(0, Rarity.COMMON.toInt());
        assertEquals(1, Rarity.UNCOMMON.toInt());
        assertEquals(2, Rarity.RARE.toInt());
        assertEquals(3, Rarity.MYTHIC.toInt());
    }

    @Test
    void fromString() {
        assertEquals(Rarity.COMMON, Rarity.fromString("common"));
        assertEquals(Rarity.UNCOMMON, Rarity.fromString("uncommon"));
        assertEquals(Rarity.RARE, Rarity.fromString("rare"));
        assertEquals(Rarity.MYTHIC, Rarity.fromString("mythic"));
        assertNull(Rarity.fromString("test"));
        assertNull(Rarity.fromString(""));
    }

    @Test
    void testToString() {
        assertEquals("common", Rarity.COMMON.toString());
        assertEquals("uncommon", Rarity.UNCOMMON.toString());
        assertEquals("rare", Rarity.RARE.toString());
        assertEquals("mythic", Rarity.MYTHIC.toString());
    }
}