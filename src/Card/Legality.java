package Card;

// Describes the legality of the card across play formats
public enum Legality {
    LEGAL,      // Legal
    NOT_LEGAL,  // Not legal - Cards not in standard
    RESTRICTED, // Restricted - Cannot use more than one copy
    BANNED;     // Banned - Cannot be included in deck or sideboard

    public static Legality fromInt(int val) {
        return switch (val) {
            case 0 -> LEGAL;
            case 1 -> NOT_LEGAL;
            case 2 -> RESTRICTED;
            case 3 -> BANNED;
            default -> null;
        };
    }

    public int toInt() {
        return switch (this) {
            case LEGAL -> 0;
            case NOT_LEGAL -> 1;
            case RESTRICTED -> 2;
            case BANNED -> 3;
        };
    }

    public static Legality fromString(String val) {
        return switch (val) {
            case "legal" -> LEGAL;
            case "not_legal" -> NOT_LEGAL;
            case "restricted" -> RESTRICTED;
            case "banned" -> BANNED;
            default -> null;
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case LEGAL -> "legal";
            case NOT_LEGAL -> "not_legal";
            case RESTRICTED -> "restricted";
            case BANNED -> "banned";
        };
    }
}
