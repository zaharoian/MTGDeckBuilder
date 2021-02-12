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

    public static  int toInt(Legality val) {
        return switch (val) {
            case LEGAL -> 0;
            case NOT_LEGAL -> 1;
            case RESTRICTED -> 2;
            case BANNED -> 3;
        };
    }
}
