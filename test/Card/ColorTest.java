package Card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorTest {

    @Test
    void toInt() {
        assertEquals(0, Color.WHITE.toInt());
        assertEquals(1, Color.BLUE.toInt());
        assertEquals(2, Color.BLACK.toInt());
        assertEquals(3, Color.RED.toInt());
        assertEquals(4, Color.GREEN.toInt());
    }

    @Test
    void fromInt() {
        assertEquals(Color.WHITE, Color.fromInt(0));
        assertEquals(Color.BLUE, Color.fromInt(1));
        assertEquals(Color.BLACK, Color.fromInt(2));
        assertEquals(Color.RED, Color.fromInt(3));
        assertEquals(Color.GREEN, Color.fromInt(4));
        assertNull(Color.fromInt(5));
        assertNull(Color.fromInt(-1));
    }

    @Test
    void testToString() {
        assertEquals("W", Color.WHITE.toString());
        assertEquals("U", Color.BLUE.toString());
        assertEquals("B", Color.BLACK.toString());
        assertEquals("R", Color.RED.toString());
        assertEquals("G", Color.GREEN.toString());
    }

    @Test
    void fromString() {
        assertEquals(Color.WHITE, Color.fromString("W"));
        assertEquals(Color.BLUE, Color.fromString("U"));
        assertEquals(Color.BLACK, Color.fromString("B"));
        assertEquals(Color.RED, Color.fromString("R"));
        assertEquals(Color.GREEN, Color.fromString("G"));
        assertNull(Color.fromString(""));
        assertNull(Color.fromString("S"));
    }
}