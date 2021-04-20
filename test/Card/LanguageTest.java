package Card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LanguageTest {

    @Test
    void fromInt() {
        assertEquals(Language.EN, Language.fromInt(0));
        assertEquals(Language.ES, Language.fromInt(1));
        assertEquals(Language.FR, Language.fromInt(2));
        assertEquals(Language.DE, Language.fromInt(3));
        assertEquals(Language.IT, Language.fromInt(4));
        assertEquals(Language.PT, Language.fromInt(5));
        assertEquals(Language.JA, Language.fromInt(6));
        assertEquals(Language.KR, Language.fromInt(7));
        assertEquals(Language.RU, Language.fromInt(8));
        assertEquals(Language.ZHS, Language.fromInt(9));
        assertEquals(Language.ZHT, Language.fromInt(10));
        assertEquals(Language.HE, Language.fromInt(11));
        assertEquals(Language.LA, Language.fromInt(12));
        assertEquals(Language.GRC, Language.fromInt(13));
        assertEquals(Language.AR, Language.fromInt(14));
        assertEquals(Language.SA, Language.fromInt(15));
        assertEquals(Language.PH, Language.fromInt(16));
        assertNull(Language.fromInt(-1));
        assertNull(Language.fromInt(17));
    }

    @Test
    void toInt() {
        assertEquals(0, Language.EN.toInt());
        assertEquals(1, Language.ES.toInt());
        assertEquals(2, Language.FR.toInt());
        assertEquals(3, Language.DE.toInt());
        assertEquals(4, Language.IT.toInt());
        assertEquals(5, Language.PT.toInt());
        assertEquals(6, Language.JA.toInt());
        assertEquals(7, Language.KR.toInt());
        assertEquals(8, Language.RU.toInt());
        assertEquals(9, Language.ZHS.toInt());
        assertEquals(10, Language.ZHT.toInt());
        assertEquals(11, Language.HE.toInt());
        assertEquals(12, Language.LA.toInt());
        assertEquals(13, Language.GRC.toInt());
        assertEquals(14, Language.AR.toInt());
        assertEquals(15, Language.SA.toInt());
        assertEquals(16, Language.PH.toInt());
    }

    @Test
    void fromString() {
        assertEquals(Language.EN, Language.fromString("en"));
        assertEquals(Language.ES, Language.fromString("es"));
        assertEquals(Language.FR, Language.fromString("fr"));
        assertEquals(Language.DE, Language.fromString("de"));
        assertEquals(Language.IT, Language.fromString("it"));
        assertEquals(Language.PT, Language.fromString("pt"));
        assertEquals(Language.JA, Language.fromString("ja"));
        assertEquals(Language.KR, Language.fromString("kr"));
        assertEquals(Language.RU, Language.fromString("ru"));
        assertEquals(Language.ZHS, Language.fromString("zhs"));
        assertEquals(Language.ZHT, Language.fromString("zht"));
        assertEquals(Language.HE, Language.fromString("he"));
        assertEquals(Language.LA, Language.fromString("la"));
        assertEquals(Language.GRC, Language.fromString("grc"));
        assertEquals(Language.AR, Language.fromString("ar"));
        assertEquals(Language.SA, Language.fromString("sa"));
        assertEquals(Language.PH, Language.fromString("ph"));
        assertNull(Format.fromString("po"));
        assertNull(Format.fromString(""));
    }

    @Test
    void testToString() {
        assertEquals("en", Language.EN.toString());
        assertEquals("es", Language.ES.toString());
        assertEquals("fr", Language.FR.toString());
        assertEquals("de", Language.DE.toString());
        assertEquals("it", Language.IT.toString());
        assertEquals("pt", Language.PT.toString());
        assertEquals("ja", Language.JA.toString());
        assertEquals("kr", Language.KR.toString());
        assertEquals("ru", Language.RU.toString());
        assertEquals("zhs", Language.ZHS.toString());
        assertEquals("zht", Language.ZHT.toString());
        assertEquals("he", Language.HE.toString());
        assertEquals("la", Language.LA.toString());
        assertEquals("grc", Language.GRC.toString());
        assertEquals("ar", Language.AR.toString());
        assertEquals("sa", Language.SA.toString());
        assertEquals("ph", Language.PH.toString());
    }
}