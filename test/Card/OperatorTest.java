package Card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperatorTest {

    @Test
    void toInt() {
        assertEquals(0, Operator.EQUAL.toInt());
        assertEquals(1, Operator.NOT_EQUAL.toInt());
        assertEquals(2, Operator.GREATER_THAN.toInt());
        assertEquals(3, Operator.LESS_THAN.toInt());
        assertEquals(4, Operator.GREATER_THAN_EQUAL.toInt());
        assertEquals(5, Operator.LESS_THAN_EQUAL.toInt());
    }

    @Test
    void fromInt() {
        assertEquals(Operator.EQUAL, Operator.fromInt(0));
        assertEquals(Operator.NOT_EQUAL, Operator.fromInt(1));
        assertEquals(Operator.GREATER_THAN, Operator.fromInt(2));
        assertEquals(Operator.LESS_THAN, Operator.fromInt(3));
        assertEquals(Operator.GREATER_THAN_EQUAL, Operator.fromInt(4));
        assertEquals(Operator.LESS_THAN_EQUAL, Operator.fromInt(5));
        assertNull(Operator.fromInt(-1));
        assertNull(Operator.fromInt(6));
    }

    @Test
    void testToString() {
        assertEquals("=", Operator.EQUAL.toString());
        assertEquals("!=", Operator.NOT_EQUAL.toString());
        assertEquals(">", Operator.GREATER_THAN.toString());
        assertEquals("<", Operator.LESS_THAN.toString());
        assertEquals(">=", Operator.GREATER_THAN_EQUAL.toString());
        assertEquals("<=", Operator.LESS_THAN_EQUAL.toString());
    }

    @Test
    void fromString() {
        assertEquals(Operator.EQUAL, Operator.fromString("="));
        assertEquals(Operator.NOT_EQUAL, Operator.fromString("!="));
        assertEquals(Operator.GREATER_THAN, Operator.fromString(">"));
        assertEquals(Operator.LESS_THAN, Operator.fromString("<"));
        assertEquals(Operator.GREATER_THAN_EQUAL, Operator.fromString(">="));
        assertEquals(Operator.LESS_THAN_EQUAL, Operator.fromString("<="));
        assertNull(Operator.fromString("!"));
        assertNull(Operator.fromString(""));

    }
}