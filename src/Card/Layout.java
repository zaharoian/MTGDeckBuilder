package Card;

// See https://scryfall.com/docs/api/layouts
public enum Layout {
    NORMAL,             // 1 Face  - A standard Magic card with one face
    SPLIT,              // 2 Faces - A split-faced card
    FLIP,               // 2 Faces - Cards that invert vertically with the flip keyword
    TRANSFORM,          // 2 Faces - Double-sided cards that transform
    MODAL_DFC,          // 2 Faces - Double-sided cards that can be played either-side
    MELD,               // 1 Face  - Cards with meld parts printed on the back
    LEVELER,            // 1 Face  - Cards with Level Up
    SAGA,               // 1 Face  - Saga-type cards
    ADVENTURE,          // 2 Faces - Cards with an Adventure spell part
    TOKEN,              // 1 Face  - Token cards
    DOUBLE_FACED_TOKEN, // 2 Faces - Tokens with another token printed on the back
    EMBLEM;             // 1 Face  - Emblem cards

    public static Layout fromInt(int val) {
        return switch (val) {
            case 0 -> NORMAL;
            case 1 -> SPLIT;
            case 2 -> FLIP;
            case 3 -> TRANSFORM;
            case 4 -> MODAL_DFC;
            case 5 -> MELD;
            case 6 -> LEVELER;
            case 7 -> SAGA;
            case 8 -> ADVENTURE;
            case 9 -> TOKEN;
            case 10 -> DOUBLE_FACED_TOKEN;
            case 11 -> EMBLEM;
            default -> null;
        };
    }

    public static int toInt(Layout val) {
        return switch (val) {
            case NORMAL -> 0;
            case SPLIT -> 1;
            case FLIP -> 2;
            case TRANSFORM -> 3;
            case MODAL_DFC -> 4;
            case MELD -> 5;
            case LEVELER -> 6;
            case SAGA -> 7;
            case ADVENTURE -> 8;
            case TOKEN -> 9;
            case DOUBLE_FACED_TOKEN -> 10;
            case EMBLEM -> 11;
        };
    }

    public static Layout fromString(String val) {
        return switch (val) {
            case "normal" -> NORMAL;
            case "split" -> SPLIT;
            case "flip" -> FLIP;
            case "transform" -> TRANSFORM;
            case "modal_dfc" -> MODAL_DFC;
            case "meld" -> MELD;
            case "leveler" -> LEVELER;
            case "saga" -> SAGA;
            case "adventure" -> ADVENTURE;
            case "token" -> TOKEN;
            case "double_faced_token" -> DOUBLE_FACED_TOKEN;
            case "emblem" -> EMBLEM;
            default -> null;
        };
    }
}
