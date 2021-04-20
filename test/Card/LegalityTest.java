package Card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LegalityTest {

    @Test
    void fromInt() {
        assertEquals(Legality.LEGAL, Legality.fromInt(0));
        assertEquals(Legality.NOT_LEGAL, Legality.fromInt(1));
        assertEquals(Legality.RESTRICTED, Legality.fromInt(2));
        assertEquals(Legality.BANNED, Legality.fromInt(3));
        assertNull(Legality.fromInt(-1));
        assertNull(Legality.fromInt(4));
    }

    @Test
    void toInt() {
        assertEquals(0, Legality.LEGAL.toInt());
        assertEquals(1, Legality.NOT_LEGAL.toInt());
        assertEquals(2, Legality.RESTRICTED.toInt());
        assertEquals(3, Legality.BANNED.toInt());
    }

    @Test
    void fromString() {
        assertEquals(Legality.LEGAL, Legality.fromString("legal"));
        assertEquals(Legality.NOT_LEGAL, Legality.fromString("not_legal"));
        assertEquals(Legality.RESTRICTED, Legality.fromString("restricted"));
        assertEquals(Legality.BANNED, Legality.fromString("banned"));
        assertNull(Legality.fromString("test"));
        assertNull(Legality.fromString(""));
    }

    @Test
    void testToString() {
        assertEquals("legal", Legality.LEGAL.toString());
        assertEquals("not_legal", Legality.NOT_LEGAL.toString());
        assertEquals("restricted", Legality.RESTRICTED.toString());
        assertEquals("banned", Legality.BANNED.toString());
    }
}