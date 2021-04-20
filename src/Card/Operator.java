package Card;

public enum Operator {
    EQUAL,
    NOT_EQUAL,
    GREATER_THAN,
    LESS_THAN,
    GREATER_THAN_EQUAL,
    LESS_THAN_EQUAL;

    public int toInt() {
        return switch(this) {
            case EQUAL -> 0;
            case NOT_EQUAL -> 1;
            case GREATER_THAN -> 2;
            case LESS_THAN -> 3;
            case GREATER_THAN_EQUAL -> 4;
            case LESS_THAN_EQUAL -> 5;
        };
    }

    public static Operator fromInt(int val) {
        return switch(val) {
            case 0 -> EQUAL;
            case 1 -> NOT_EQUAL;
            case 2 -> GREATER_THAN;
            case 3 -> LESS_THAN;
            case 4 -> GREATER_THAN_EQUAL;
            case 5 -> LESS_THAN_EQUAL;
            default -> null;
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case EQUAL -> "=";
            case NOT_EQUAL -> "!=";
            case GREATER_THAN -> ">";
            case LESS_THAN -> "<";
            case GREATER_THAN_EQUAL -> ">=";
            case LESS_THAN_EQUAL -> "<=";
        };
    }

    public static Operator fromString(String val) {
        return switch (val) {
            case "=" -> EQUAL;
            case "!=" -> NOT_EQUAL;
            case ">" -> GREATER_THAN;
            case "<" -> LESS_THAN;
            case ">=" -> GREATER_THAN_EQUAL;
            case "<=" -> LESS_THAN_EQUAL;
            default -> null;
        };
    }
}
