package Card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormatTest {

    @Test
    void fromInt() {
        assertEquals(Format.STANDARD, Format.fromInt(0));
        assertEquals(Format.BRAWL, Format.fromInt(1));
        assertEquals(Format.PIONEER, Format.fromInt(2));
        assertEquals(Format.HISTORIC, Format.fromInt(3));
        assertEquals(Format.PAUPER, Format.fromInt(5));
        assertEquals(Format.MODERN, Format.fromInt(4));
        assertEquals(Format.LEGACY, Format.fromInt(6));
        assertEquals(Format.PENNY, Format.fromInt(7));
        assertEquals(Format.VINTAGE, Format.fromInt(8));
        assertEquals(Format.COMMANDER, Format.fromInt(9));
        assertNull(Format.fromInt(-1));
        assertNull(Format.fromInt(10));
    }

    @Test
    void toInt() {
        assertEquals(0, Format.STANDARD.toInt());
        assertEquals(1, Format.BRAWL.toInt());
        assertEquals(2, Format.PIONEER.toInt());
        assertEquals(3, Format.HISTORIC.toInt());
        assertEquals(5, Format.PAUPER.toInt());
        assertEquals(4, Format.MODERN.toInt());
        assertEquals(6, Format.LEGACY.toInt());
        assertEquals(7, Format.PENNY.toInt());
        assertEquals(8, Format.VINTAGE.toInt());
        assertEquals(9, Format.COMMANDER.toInt());
    }

    @Test
    void fromString() {
        assertEquals(Format.STANDARD, Format.fromString("standard"));
        assertEquals(Format.BRAWL, Format.fromString("brawl"));
        assertEquals(Format.PIONEER, Format.fromString("pioneer"));
        assertEquals(Format.HISTORIC, Format.fromString("historic"));
        assertEquals(Format.PAUPER, Format.fromString("pauper"));
        assertEquals(Format.MODERN, Format.fromString("modern"));
        assertEquals(Format.LEGACY, Format.fromString("legacy"));
        assertEquals(Format.PENNY, Format.fromString("penny"));
        assertEquals(Format.VINTAGE, Format.fromString("vintage"));
        assertEquals(Format.COMMANDER, Format.fromString("commander"));
        assertNull(Format.fromString("paupr"));
        assertNull(Format.fromString(""));
    }

    @Test
    void testToString() {
        assertEquals("standard", Format.STANDARD.toString());
        assertEquals("brawl", Format.BRAWL.toString());
        assertEquals("pioneer", Format.PIONEER.toString());
        assertEquals("historic", Format.HISTORIC.toString());
        assertEquals("pauper", Format.PAUPER.toString());
        assertEquals("modern", Format.MODERN.toString());
        assertEquals("legacy", Format.LEGACY.toString());
        assertEquals("penny", Format.PENNY.toString());
        assertEquals("vintage", Format.VINTAGE.toString());
        assertEquals("commander", Format.COMMANDER.toString());
    }
}