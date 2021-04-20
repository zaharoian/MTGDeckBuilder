package Card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LayoutTest {

    @Test
    void fromInt() {
        assertEquals(Layout.NORMAL, Layout.fromInt(0));
        assertEquals(Layout.SPLIT, Layout.fromInt(1));
        assertEquals(Layout.FLIP, Layout.fromInt(2));
        assertEquals(Layout.TRANSFORM, Layout.fromInt(3));
        assertEquals(Layout.MODAL_DFC, Layout.fromInt(4));
        assertEquals(Layout.MELD, Layout.fromInt(5));
        assertEquals(Layout.LEVELER, Layout.fromInt(6));
        assertEquals(Layout.SAGA, Layout.fromInt(7));
        assertEquals(Layout.ADVENTURE, Layout.fromInt(8));
        assertEquals(Layout.TOKEN, Layout.fromInt(9));
        assertEquals(Layout.DOUBLE_FACED_TOKEN, Layout.fromInt(10));
        assertEquals(Layout.EMBLEM, Layout.fromInt(11));
        assertEquals(Layout.PLANAR, Layout.fromInt(12));
        assertEquals(Layout.SCHEME, Layout.fromInt(13));
        assertEquals(Layout.VANGUARD, Layout.fromInt(14));
        assertEquals(Layout.AUGMENT, Layout.fromInt(15));
        assertEquals(Layout.HOST, Layout.fromInt(16));
        assertEquals(Layout.ART_SERIES, Layout.fromInt(17));
        assertNull(Layout.fromInt(-1));
        assertNull(Layout.fromInt(18));
    }

    @Test
    void toInt() {
        assertEquals(0, Layout.NORMAL.toInt());
        assertEquals(1, Layout.SPLIT.toInt());
        assertEquals(2, Layout.FLIP.toInt());
        assertEquals(3, Layout.TRANSFORM.toInt());
        assertEquals(4, Layout.MODAL_DFC.toInt());
        assertEquals(5, Layout.MELD.toInt());
        assertEquals(6, Layout.LEVELER.toInt());
        assertEquals(7, Layout.SAGA.toInt());
        assertEquals(8, Layout.ADVENTURE.toInt());
        assertEquals(9, Layout.TOKEN.toInt());
        assertEquals(10, Layout.DOUBLE_FACED_TOKEN.toInt());
        assertEquals(11, Layout.EMBLEM.toInt());
        assertEquals(12, Layout.PLANAR.toInt());
        assertEquals(13, Layout.SCHEME.toInt());
        assertEquals(14, Layout.VANGUARD.toInt());
        assertEquals(15, Layout.AUGMENT.toInt());
        assertEquals(16, Layout.HOST.toInt());
        assertEquals(17, Layout.ART_SERIES.toInt());
    }

    @Test
    void fromString() {
        assertEquals(Layout.NORMAL, Layout.fromString("normal"));
        assertEquals(Layout.SPLIT, Layout.fromString("split"));
        assertEquals(Layout.FLIP, Layout.fromString("flip"));
        assertEquals(Layout.TRANSFORM, Layout.fromString("transform"));
        assertEquals(Layout.MODAL_DFC, Layout.fromString("modal_dfc"));
        assertEquals(Layout.MELD, Layout.fromString("meld"));
        assertEquals(Layout.LEVELER, Layout.fromString("leveler"));
        assertEquals(Layout.SAGA, Layout.fromString("saga"));
        assertEquals(Layout.ADVENTURE, Layout.fromString("adventure"));
        assertEquals(Layout.TOKEN, Layout.fromString("token"));
        assertEquals(Layout.DOUBLE_FACED_TOKEN, Layout.fromString("double_faced_token"));
        assertEquals(Layout.EMBLEM, Layout.fromString("emblem"));
        assertEquals(Layout.PLANAR, Layout.fromString("planar"));
        assertEquals(Layout.SCHEME, Layout.fromString("scheme"));
        assertEquals(Layout.VANGUARD, Layout.fromString("vanguard"));
        assertEquals(Layout.AUGMENT, Layout.fromString("augment"));
        assertEquals(Layout.HOST, Layout.fromString("host"));
        assertEquals(Layout.ART_SERIES, Layout.fromString("art_series"));
        assertNull(Layout.fromString("test"));
        assertNull(Layout.fromString(""));
    }

    @Test
    void testToString() {
        assertEquals("normal", Layout.NORMAL.toString());
        assertEquals("split", Layout.SPLIT.toString());
        assertEquals("flip", Layout.FLIP.toString());
        assertEquals("transform", Layout.TRANSFORM.toString());
        assertEquals("modal_dfc", Layout.MODAL_DFC.toString());
        assertEquals("meld", Layout.MELD.toString());
        assertEquals("leveler", Layout.LEVELER.toString());
        assertEquals("saga", Layout.SAGA.toString());
        assertEquals("adventure", Layout.ADVENTURE.toString());
        assertEquals("token", Layout.TOKEN.toString());
        assertEquals("double_faced_token", Layout.DOUBLE_FACED_TOKEN.toString());
        assertEquals("emblem", Layout.EMBLEM.toString());
        assertEquals("planar", Layout.PLANAR.toString());
        assertEquals("scheme", Layout.SCHEME.toString());
        assertEquals("vanguard", Layout.VANGUARD.toString());
        assertEquals("augment", Layout.AUGMENT.toString());
        assertEquals("host", Layout.HOST.toString());
        assertEquals("art_series", Layout.ART_SERIES.toString());
    }
}