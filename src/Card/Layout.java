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
    EMBLEM,             // Emblem cards
}
