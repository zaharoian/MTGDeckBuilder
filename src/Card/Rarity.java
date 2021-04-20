package Card;

public enum Rarity {
    COMMON,
    UNCOMMON,
    RARE,
    MYTHIC;

    public static Rarity fromInt(int val) {
        return switch(val) {
            case 0 -> COMMON;
            case 1 -> UNCOMMON;
            case 2 -> RARE;
            case 3 -> MYTHIC;
            default -> null;
        };
    }

    public int toInt() {
        return switch(this) {
            case COMMON -> 0;
            case UNCOMMON -> 1;
            case RARE -> 2;
            case MYTHIC -> 3;
        };
    }

    public static Rarity fromString(String val) {
        return switch(val) {
            case "common" -> COMMON;
            case "uncommon" -> UNCOMMON;
            case "rare" -> RARE;
            case "mythic" -> MYTHIC;
            default -> null;
        };
    }

    @Override
    public String toString() {
        return switch(this) {
            case COMMON -> "common";
            case UNCOMMON -> "uncommon";
            case RARE -> "rare";
            case MYTHIC -> "mythic";
        };
    }
}
