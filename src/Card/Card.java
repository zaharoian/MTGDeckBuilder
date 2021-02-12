package Card;

import java.util.Objects;
import java.util.UUID;

// See https://scryfall.com/docs/api/cards
public class Card {

    // Core Card Fields
    private final UUID id;
    private final Language lang;
    private final Double cmc;
    private final boolean w_c; // White Color
    private final boolean u_c; // Blue Color
    private final boolean b_c; // Black Color
    private final boolean r_c; // Red Color
    private final boolean g_c; // Green Color
    private final boolean w_ci; // White Color Identity
    private final boolean u_ci; // Blue Color Identity
    private final boolean b_ci; // Black Color Identity
    private final boolean r_ci; // Red Color Identity
    private final boolean g_ci; // Green Color Identity
    private final Layout layout;
    private final Legality standard;
    private final Legality brawl;
    private final Legality pioneer;
    private final Legality historic;
    private final Legality modern;
    private final Legality pauper;
    private final Legality legacy;
    private final Legality penny;
    private final Legality vintage;
    private final Legality commander;
    private final String mana_cost;
    private final String name;
    private final String power;
    private final String toughness;
    private final String type_line;

    // TODO: Add Card Face Objects and Related Card Objects

    // Constructors
    public Card(UUID id, Language lang, Double cmc, boolean w_c, boolean u_c, boolean b_c, boolean r_c, boolean g_c, boolean w_ci, boolean u_ci, boolean b_ci, boolean r_ci, boolean g_ci, Layout layout, Legality standard_legality, Legality brawl_legality, Legality pioneer_legality, Legality historic_legality, Legality modern_legality, Legality pauper_legality, Legality legacy_legality, Legality penny_legality, Legality vintage_legality, Legality commander_legality, String mana_cost, String name, String power, String toughness, String type_line) {
        this.id = id;
        this.lang = lang;
        this.cmc = cmc;
        this.w_c = w_c;
        this.u_c = u_c;
        this.b_c = b_c;
        this.r_c = r_c;
        this.g_c = g_c;
        this.w_ci = w_ci;
        this.u_ci = u_ci;
        this.b_ci = b_ci;
        this.r_ci = r_ci;
        this.g_ci = g_ci;
        this.layout = layout;
        this.standard = standard_legality;
        this.brawl = brawl_legality;
        this.pioneer = pioneer_legality;
        this.historic = historic_legality;
        this.modern = modern_legality;
        this.pauper = pauper_legality;
        this.legacy = legacy_legality;
        this.penny = penny_legality;
        this.vintage = vintage_legality;
        this.commander = commander_legality;
        this.mana_cost = mana_cost;
        this.name = name;
        this.power = power;
        this.toughness = toughness;
        this.type_line = type_line;
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

    public Language getLang() {
        return lang;
    }

    public Double getCmc() {
        return cmc;
    }

    public boolean isW_c() {
        return w_c;
    }

    public boolean isU_c() {
        return u_c;
    }

    public boolean isB_c() {
        return b_c;
    }

    public boolean isR_c() {
        return r_c;
    }

    public boolean isG_c() {
        return g_c;
    }

    public boolean isW_ci() {
        return w_ci;
    }

    public boolean isU_ci() {
        return u_ci;
    }

    public boolean isB_ci() {
        return b_ci;
    }

    public boolean isR_ci() {
        return r_ci;
    }

    public boolean isG_ci() {
        return g_ci;
    }

    public Layout getLayout() {
        return layout;
    }

    public Legality getStandard() {
        return standard;
    }

    public Legality getBrawl() {
        return brawl;
    }

    public Legality getPioneer() {
        return pioneer;
    }

    public Legality getHistoric() {
        return historic;
    }

    public Legality getModern() {
        return modern;
    }

    public Legality getPauper() {
        return pauper;
    }

    public Legality getLegacy() {
        return legacy;
    }

    public Legality getPenny() {
        return penny;
    }

    public Legality getVintage() {
        return vintage;
    }

    public Legality getCommander() {
        return commander;
    }

    public String getMana_cost() {
        return mana_cost;
    }

    public String getName() {
        return name;
    }

    public String getPower() {
        return power;
    }

    public String getToughness() {
        return toughness;
    }

    public String getType_line() {
        return type_line;
    }

}
