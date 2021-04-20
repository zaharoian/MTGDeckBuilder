package Card;

public enum Color{
    WHITE,
    BLUE,
    BLACK,
    RED,
    GREEN;

    public int toInt() {
        return switch (this) {
            case WHITE -> 0;
            case BLUE -> 1;
            case BLACK -> 2;
            case RED -> 3;
            case GREEN -> 4;
        };
    }

    public static Color fromInt(int val) {
        return switch (val) {
            case 0 -> WHITE;
            case 1 -> BLUE;
            case 2 -> BLACK;
            case 3 -> RED;
            case 4 -> GREEN;
            default -> null;
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case WHITE -> "W";
            case BLUE -> "U";
            case BLACK -> "B";
            case RED -> "R";
            case GREEN -> "G";
        };
    }

    public static Color fromString(String val) {
        return switch (val) {
            case "W" -> WHITE;
            case "U" -> BLUE;
            case "B" -> BLACK;
            case "R" -> RED;
            case "G" -> GREEN;
            default -> null;
        };
    }
}
