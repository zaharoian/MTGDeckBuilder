package Card;

// See https://scryfall.com/docs/api/layouts
public enum Layout {
    NORMAL,             // A standard Magic card with one face
    SPLIT,              // A split-faced card
    FLIP,               // Cards that invert vertically with the flip keyword
    TRANSFORM,          // Double-sided cards that transform
    MODAL_DFC,          // Double-sided cards that can be played either-side
    MELD,               // Cards with meld parts printed on the back
    LEVELER,            // Cards with Level Up
    SAGA,               // Saga-type cards
    ADVENTURE,          // Cards with an Adventure spell part
    TOKEN,              // Token cards
    DOUBLE_FACED_TOKEN, // Tokens with another token printed on the back
    EMBLEM;             // Emblem cards

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
}
