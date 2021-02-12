package Card;

import java.util.Objects;
import java.util.UUID;

// See https://scryfall.com/docs/api/cards
public class Card {

    // Core Card Fields
    private final UUID id;
    private final String lang;
    private final Integer tcgplayer_id;
    private final UUID oracle_id;

    // Gameplay Fields
    private final Double cmc;
    private final Color[] color_identity;
    private final Color[] color_indicator;
    private final Color[] colors;
    private final boolean foil;
    private final String[] keywords;
    private final Layout layout;
    private final Legality[] legalities;
    private final String loyalty;
    private final String mana_cost;
    private final String name;
    private final boolean nonfoil;
    private final String oracle_text;
    private final String power;
    private final String toughness;
    private final Color[] produced_mana;
    private final String type_line;

    // Print Fields
    private final String artist;
    private final BorderColor border_color;
    private final UUID card_back_id;
    private final String collector_number;
    private final boolean content_warning;
    private final boolean digital;
    private final String flavor_name;
    private final String flavor_text;
    private final Games[] games;
    private final String printed_name;
    private final String printed_text;
    private final String printed_type_line;
    private final boolean promo;
    private final String[] promo_types;
    private final Rarity rarity;
    private final String release_date;
    private final boolean reprint;
    private final String set_name;
    private final String set_type;
    private final String set;

    // TODO: Add Card Face Objects and Related Card Objects

    // Constructors

    public Card(UUID id, String lang, Integer tcgplayer_id, UUID oracle_id, Double cmc, Color[] color_identity, Color[] color_indicator, Color[] colors, boolean foil, String[] keywords, Layout layout, Legality[] legalities, String loyalty, String mana_cost, String name, boolean nonfoil, String oracle_text, String power, String toughness, Color[] produced_mana, String type_line, String artist, BorderColor border_color, UUID card_back_id, String collector_number, boolean content_warning, boolean digital, String flavor_name, String flavor_text, Games[] games, String printed_name, String printed_text, String printed_type_line, boolean promo, String[] promo_types, Rarity rarity, String release_date, boolean reprint, String set_name, String set_type, String set) {
        this.id = id;
        this.lang = lang;
        this.tcgplayer_id = tcgplayer_id;
        this.oracle_id = oracle_id;
        this.cmc = cmc;
        this.color_identity = color_identity;
        this.color_indicator = color_indicator;
        this.colors = colors;
        this.foil = foil;
        this.keywords = keywords;
        this.layout = layout;
        this.legalities = legalities;
        this.loyalty = loyalty;
        this.mana_cost = mana_cost;
        this.name = name;
        this.nonfoil = nonfoil;
        this.oracle_text = oracle_text;
        this.power = power;
        this.toughness = toughness;
        this.produced_mana = produced_mana;
        this.type_line = type_line;
        this.artist = artist;
        this.border_color = border_color;
        this.card_back_id = card_back_id;
        this.collector_number = collector_number;
        this.content_warning = content_warning;
        this.digital = digital;
        this.flavor_name = flavor_name;
        this.flavor_text = flavor_text;
        this.games = games;
        this.printed_name = printed_name;
        this.printed_text = printed_text;
        this.printed_type_line = printed_type_line;
        this.promo = promo;
        this.promo_types = promo_types;
        this.rarity = rarity;
        this.release_date = release_date;
        this.reprint = reprint;
        this.set_name = set_name;
        this.set_type = set_type;
        this.set = set;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return id.equals(card.id) && name.equals(card.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    // Accessors

    public UUID getId() {
        return id;
    }

    public String getLang() {
        return lang;
    }

    public Integer getTcgplayer_id() {
        return tcgplayer_id;
    }

    public UUID getOracle_id() {
        return oracle_id;
    }

    public Double getCmc() {
        return cmc;
    }

    public Color[] getColor_identity() {
        return color_identity;
    }

    public Color[] getColor_indicator() {
        return color_indicator;
    }

    public Color[] getColors() {
        return colors;
    }

    public boolean isFoil() {
        return foil;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public Layout getLayout() {
        return layout;
    }

    public Legality[] getLegalities() {
        return legalities;
    }

    public String getLoyalty() {
        return loyalty;
    }

    public String getMana_cost() {
        return mana_cost;
    }

    public String getName() {
        return name;
    }

    public boolean isNonfoil() {
        return nonfoil;
    }

    public String getOracle_text() {
        return oracle_text;
    }

    public String getPower() {
        return power;
    }

    public String getToughness() {
        return toughness;
    }

    public Color[] getProduced_mana() {
        return produced_mana;
    }

    public String getType_line() {
        return type_line;
    }

    public String getArtist() {
        return artist;
    }

    public BorderColor getBorder_color() {
        return border_color;
    }

    public UUID getCard_back_id() {
        return card_back_id;
    }

    public String getCollector_number() {
        return collector_number;
    }

    public boolean isContent_warning() {
        return content_warning;
    }

    public boolean isDigital() {
        return digital;
    }

    public String getFlavor_name() {
        return flavor_name;
    }

    public String getFlavor_text() {
        return flavor_text;
    }

    public Games[] getGames() {
        return games;
    }

    public String getPrinted_name() {
        return printed_name;
    }

    public String getPrinted_text() {
        return printed_text;
    }

    public String getPrinted_type_line() {
        return printed_type_line;
    }

    public boolean isPromo() {
        return promo;
    }

    public String[] getPromo_types() {
        return promo_types;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public String getRelease_date() {
        return release_date;
    }

    public boolean isReprint() {
        return reprint;
    }

    public String getSet_name() {
        return set_name;
    }

    public String getSet_type() {
        return set_type;
    }

    public String getSet() {
        return set;
    }
}
