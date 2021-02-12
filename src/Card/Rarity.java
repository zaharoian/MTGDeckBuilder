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

    public static int toInt(Rarity val) {
        return switch(val) {
            case COMMON -> 0;
            case UNCOMMON -> 1;
            case RARE -> 2;
            case MYTHIC -> 3;
        };
    }
}
