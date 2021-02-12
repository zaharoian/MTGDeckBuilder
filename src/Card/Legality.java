package Card;

// Describes the legality of the card across play formats
public enum Legality {
    LEGAL,      // Legal
    NOT_LEGAL,  // Not legal - Cards not in standard
    RESTRICTED, // Restricted - Cannot use more than one copy
    BANNED      // Banned - Cannot be included in deck or sideboard
}
