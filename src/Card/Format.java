package Card;

public enum Format {
    STANDARD,
    BRAWL,
    PIONEER,
    HISTORIC,
    MODERN,
    PAUPER,
    LEGACY,
    PENNY,
    VINTAGE,
    COMMANDER;

    public static Format fromInt(int val) {
        return switch (val) {
            case 0 -> STANDARD;
            case 1 -> BRAWL;
            case 2 -> PIONEER;
            case 3 -> HISTORIC;
            case 4 -> MODERN;
            case 5 -> PAUPER;
            case 6 -> LEGACY;
            case 7 -> PENNY;
            case 8 -> VINTAGE;
            case 9 -> COMMANDER;
            default -> null;
        };
    }

    public int toInt() {
        return switch (this) {
            case STANDARD -> 0;
            case BRAWL -> 1;
            case PIONEER -> 2;
            case HISTORIC -> 3;
            case MODERN -> 4;
            case PAUPER -> 5;
            case LEGACY -> 6;
            case PENNY -> 7;
            case VINTAGE -> 8;
            case COMMANDER -> 9;
        };
    }

    public static Format fromString(String val) {
        if (val == null) return null;
        return switch (val.toLowerCase()) {
            case "standard" -> STANDARD;
            case "brawl" -> BRAWL;
            case "pioneer" -> PIONEER;
            case "historic" -> HISTORIC;
            case "modern" -> MODERN;
            case "pauper" -> PAUPER;
            case "legacy" -> LEGACY;
            case "penny" -> PENNY;
            case "vintage" -> VINTAGE;
            case "commander" -> COMMANDER;
            default -> null;
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case STANDARD -> "standard";
            case BRAWL -> "brawl";
            case PIONEER -> "pioneer";
            case HISTORIC -> "historic";
            case MODERN -> "modern";
            case PAUPER -> "pauper";
            case LEGACY -> "legacy";
            case PENNY -> "penny";
            case VINTAGE -> "vintage";
            case COMMANDER -> "commander";
        };
    }
}
