package Card;

// See https://scryfall.com/docs/api/languages
public enum Language {
    EN, // English
    ES, // Spanish
    FR, // French
    DE, // German
    IT, // Italian
    PT, // Portuguese
    JA, // Japanese
    KR, // Korean
    RU, // Russian
    ZHS,// Simplified Chinese
    ZHT,// Traditional Chinese
    HE, // Hebrew
    LA, // Latin
    GRC,// Ancient Greek
    AR, // Arabic
    SA, // Sanskrit
    PH; // Phyrexian

    public static Language fromInt(int val) {
        return switch(val) {
            case 0 -> EN;
            case 1 -> ES;
            case 2 -> FR;
            case 3 -> DE;
            case 4 -> IT;
            case 5 -> PT;
            case 6 -> JA;
            case 7 -> KR;
            case 8 -> RU;
            case 9 -> ZHS;
            case 10 -> ZHT;
            case 11 -> HE;
            case 12 -> LA;
            case 13 -> GRC;
            case 14 -> AR;
            case 15 -> SA;
            case 16 -> PH;
            default -> null;
        };
    }

    public static int toInt(Language val) {
        return switch (val) {
            case EN -> 0;
            case ES -> 1;
            case FR -> 2;
            case DE -> 3;
            case IT -> 4;
            case PT -> 5;
            case JA -> 6;
            case KR -> 7;
            case RU -> 8;
            case ZHS -> 9;
            case ZHT -> 10;
            case HE -> 11;
            case LA -> 12;
            case GRC -> 13;
            case AR -> 14;
            case SA -> 15;
            case PH -> 16;
        };
    }
}
