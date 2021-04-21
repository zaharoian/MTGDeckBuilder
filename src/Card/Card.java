package Card;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// See https://scryfall.com/docs/api/cards
public class Card {

    // Card Fields
    private final UUID id;
    private final String name;
    private final String type_line;
    private final int cmc;
    private final String mana_cost;
    private final boolean w_ci; // White Color Identity
    private final boolean u_ci; // Blue Color Identity
    private final boolean b_ci; // Black Color Identity
    private final boolean r_ci; // Red Color Identity
    private final boolean g_ci; // Green Color Identity
    private final boolean double_faced;
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
    private final String front_download_link;
    private final String back_download_link;
    private final String front_image_path;
    private final String back_image_path;
    private ListenableBoolean cardsDownloaded = new ListenableBoolean();

    public class ListenableBoolean {
        protected PropertyChangeSupport propertyChangeSupport;
        private boolean bool;

        public ListenableBoolean () {
            propertyChangeSupport = new PropertyChangeSupport(this);
        }

        public void setBool(boolean bool) {
            boolean oldValue = this.bool;
            this.bool = bool;
            System.out.println(name + " downloaded?: " + bool);
            propertyChangeSupport.firePropertyChange("ListenableBoolean", oldValue, bool);
        }

        public void addPropertyChangeListener(PropertyChangeListener listener) {
            PropertyChangeListener[] currentListeners = propertyChangeSupport.getPropertyChangeListeners();
            for (PropertyChangeListener lis : currentListeners) {
                if (lis.equals(listener)) {
                    return;
                }
            }
            propertyChangeSupport.addPropertyChangeListener(listener);
        }

        public void removePropertyChangeListener(PropertyChangeListener listener) {
            propertyChangeSupport.removePropertyChangeListener(listener);
        }
    }


    public Card(UUID id, int cmc, boolean w_ci, boolean u_ci, boolean b_ci, boolean r_ci, boolean g_ci, boolean double_faced, Legality standard_legality, Legality brawl_legality, Legality pioneer_legality, Legality historic_legality, Legality modern_legality, Legality pauper_legality, Legality legacy_legality, Legality penny_legality, Legality vintage_legality, Legality commander_legality, String mana_cost, String name, String type_line, String front_download_link, String back_download_link) {
        this.id = id;
        this.cmc = cmc;
        this.w_ci = w_ci;
        this.u_ci = u_ci;
        this.b_ci = b_ci;
        this.r_ci = r_ci;
        this.g_ci = g_ci;
        this.double_faced = double_faced;
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
        this.type_line = type_line;
        this.front_download_link = front_download_link;
        this.back_download_link = back_download_link;
        front_image_path = "images/" + id + ".jpg";
        back_image_path = "images/" + id + "_b.jpg";
        cardsDownloaded.setBool(checkImagePath(true));
    }

    public void downloadImages() {
        System.out.println("downloading " + name + " images");
        System.out.println("path: " + front_image_path);
        if (!checkImagePath(true)) downloadImage(front_download_link, id.toString());
        if (double_faced) if (!checkImagePath(false)) downloadImage(back_download_link, id + "_b");
        cardsDownloaded.setBool(true);
    }

    public boolean getCardsDownloaded() {
        return cardsDownloaded.bool;
    }

    public void addCardsDownloadedListener(PropertyChangeListener listener) {
        cardsDownloaded.addPropertyChangeListener(listener);
    }

    public void removeCardsDownloadedListener(PropertyChangeListener listener) {
        cardsDownloaded.removePropertyChangeListener(listener);
    }



    private boolean checkImagePath(boolean front) {
        String path = "images/" + id + (front ? "" : "_b") + ".jpg";
        File file = new File(path);
        return file.exists();
    }

    public String getFront_image_path() {
        return front_image_path;
    }

    public String getBack_image_path() {
        return back_image_path;
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
        return "\nCard{" +
                "\nd=" + id +
                "\nname='" + name + '\'' +
                "\ntype_line='" + type_line + '\'' +
                "\nmana_cost='" + mana_cost + '\'' +
                "\ncmc=" + cmc +
                "\nci:" +
                "\n  W: " + w_ci +
                "\n  U: " + u_ci +
                "\n  B: " + b_ci +
                "\n  R: " + r_ci +
                "\n  G: " + g_ci +
                "\ndouble-faced: " + double_faced +
                "\nfront: " + front_download_link +
                "\nback: " + back_download_link +
                "\nfront image saved: " + checkImagePath(true) +
                "\nback image saved: " + (double_faced ? checkImagePath(false) : "null") +
                "\n}";
    }



    private static void downloadImage(String image_path, String name) {
        try {
            URL url = new URL(image_path);
            String fileName = name + ".jpg";
            String destName = "images/" + fileName;
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(destName);

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static String simplifyManaCost(String cost) {
        if (cost == null || cost.isBlank()) return null;
        cost = cost.replaceAll("[^{}0-9WUBRGXSC/]", "");
        if (cost.isBlank()) return null;
        StringBuilder total_cost = new StringBuilder();
        String[] precedence =
               {"\\{X}", //{*},                                        // X, generic
                "\\{W/U}", "\\{W/B}", "\\{B/R}", "\\{B/G}", "\\{U/B}", // Hybrid
                "\\{U/R}", "\\{R/G}", "\\{R/W}", "\\{G/W}", "\\{G/U}", // Hybrid
                "\\{2/W}", "\\{2/U}", "\\{2/B}", "\\{2/R}", "\\{2/G}", // Twobrid
                "\\{W/P}", "\\{U/P}", "\\{B/P}", "\\{R/P}", "\\{G/P}", // Phyrexian
                "\\{W}",   "\\{U}",   "\\{B}",   "\\{R}",   "\\{G}",   // WUBRG
                "\\{C}",   "\\{S}" // Colorless + snow
        };
        Integer gen_cost = null;
        for (int i = 0; i < precedence.length; i++) {
            while (cost.contains(precedence[i].substring(1))) {
                cost = cost.replaceFirst(precedence[i], "");
                total_cost.append(precedence[i].substring(1));
            }
            if (i == 0) {
                // add placeholder for generic
                total_cost.append("{*}");
            }
        }
        Pattern p = Pattern.compile("\\{\\d}");
        Matcher m = p.matcher(cost);
        // get generic values
        while (m.find()) {
            if (gen_cost == null) {
                gen_cost = Integer.parseInt(cost.substring(m.start() + 1, m.end() - 1));
            } else {
                gen_cost += Integer.parseInt(cost.substring(m.start() + 1, m.end() - 1));
            }
        }

        cost = total_cost.toString();
        if (gen_cost != null) {
            cost = cost.replace("*", gen_cost.toString());
        } else {
            cost = cost.replace("{*}", "");
        }
        return cost;
    }


    // Accessors
    public UUID getId() {
        return id;
    }

    public Integer getCmc() {
        return cmc;
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

    public String getType_line() {
        return type_line;
    }

    public boolean isDouble_faced() {
        return double_faced;
    }

    public String getFront_download_link() {
        return front_download_link;
    }

    public String getBack_download_link() {
        return back_download_link;
    }
}
