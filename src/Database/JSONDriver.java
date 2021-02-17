package Database;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import Card.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import Card.Language;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONDriver {
    private static void parseCard(JSONObject card) {
        // CARD TABLE
        // name
        final String name = (String) card.get("name");

        // id - unique Scryfall ID
        final UUID id = UUID.fromString((String) card.get("id"));

        // layout
        final Layout layout = Layout.fromString((String) card.get("layout"));

        // cmc
        final Integer cmc =  (Integer)(int)(double) card.get("cmc");

        // type_line
        final String type_line = (String) card.get("type_line");

        // rarity
        final Rarity rarity = Rarity.fromString((String) card.get("rarity"));

        // Color Identities
        JSONArray color_identities = (JSONArray) card.get("color_identity");
        boolean w_ci = false;
        boolean u_ci = false;
        boolean b_ci = false;
        boolean r_ci = false;
        boolean g_ci = false;
        for (int i = 0; i < color_identities.size(); i++) {
            switch((String) color_identities.get(i)) {
                case "W" -> w_ci = true;
                case "U" -> u_ci = true;
                case "B" -> b_ci = true;
                case "R" -> r_ci = true;
                case "G" -> g_ci = true;
                default -> System.out.println("unhandled color");
            }
        }

        // Legalities
        JSONObject legalities = (JSONObject) card.get("legalities");
        Legality standard = Legality.fromString((String) legalities.get("standard"));
        Legality brawl = Legality.fromString((String) legalities.get("brawl"));
        Legality pioneer = Legality.fromString((String) legalities.get("pioneer"));
        Legality historic = Legality.fromString((String) legalities.get("historic"));
        Legality modern = Legality.fromString((String) legalities.get("modern"));
        Legality pauper = Legality.fromString((String) legalities.get("pauper"));
        Legality legacy = Legality.fromString((String) legalities.get("legacy"));
        Legality penny = Legality.fromString((String) legalities.get("penny"));
        Legality vintage = Legality.fromString((String) legalities.get("vintage"));
        Legality commander = Legality.fromString((String) legalities.get("commander"));

        // CARD FACES TABLE
        // Single faced Cards
        if (layout == Layout.NORMAL || layout == Layout.MELD ||
            layout == Layout.LEVELER || layout == Layout.SAGA) {
            JSONObject image_uris = (JSONObject) card.get("image_uris");
            final String image_uri = (String) image_uris.get("normal");

            final String mana_cost = (String) card.get("mana_cost");

            final String oracle_text = (String) card.get("oracle_text");

            final String power = (String) card.get("power");

            final String toughness = (String) card.get("toughness");

            // Save to db
            DBDriver.insertToCardsTable(id, name, layout, cmc, type_line, w_ci, u_ci, b_ci, r_ci, g_ci, standard, brawl, pioneer, historic, modern, pauper, legacy, penny, vintage, commander, rarity, image_uri);
        }
        // Multifaced Cards
        else if (layout == Layout.SPLIT || layout == Layout.FLIP ||
                 layout == Layout.TRANSFORM || layout == Layout.MODAL_DFC ||
                 layout == Layout.ADVENTURE) {

            JSONArray card_faces = (JSONArray) card.get("card_faces");
            JSONObject front = (JSONObject) card_faces.get(0);
            JSONObject back = (JSONObject) card_faces.get(1);

            String front_image, back_image = null;
            String front_oracle_text = (String) front.get("oracle_text");
            String back_oracle_text = (String) back.get("oracle_text");
            String front_power = (String) front.get("power");
            String back_power = (String) back.get("power");
            String front_toughness = (String) front.get("toughness");
            String back_toughness = (String) back.get("toughness");
            String mana_cost = switch(layout) {
                case SPLIT -> calculateSplitCost((String) card.get("mana_cost"));
                case FLIP, ADVENTURE, TRANSFORM, MODAL_DFC -> (String) front.get("mana_cost");
                default -> null;
            };

            // Two faces, one side
            if (layout == Layout.SPLIT || layout == Layout.FLIP ||
                layout == Layout.ADVENTURE) {
                // front_image
                JSONObject images = (JSONObject) card.get("image_uris");
                front_image = (String) images.get("normal");
            }
            // Two faces, two sides
            else {
                // front_image
                JSONObject front_images = (JSONObject) front.get("image_uris");
                front_image = (String) front_images.get("normal");
                // back_image
                JSONObject back_images = (JSONObject) back.get("image_uris");
                back_image = (String) back_images.get("normal");
            }

            // Save to db
            DBDriver.insertToCardsTable(id, name, layout, cmc, type_line, w_ci, u_ci, b_ci, r_ci, g_ci, standard, brawl, pioneer, historic, modern, pauper, legacy, penny, vintage, commander, rarity, front_image);

        }
        // New/Unhandled Cards
        else {
            System.out.println("Unhandled type: " + layout);
        }

        System.out.println();

    }

    private static String calculateSplitCost(String cost) {
        String[] costs = cost.replace(" // ", "").replace("{", "").split("}");
        Arrays.sort(costs);
        Integer gen_cost = 0;
        StringBuilder hyb_cost = new StringBuilder();
        StringBuilder x_cost = new StringBuilder();
        StringBuilder col_cost = new StringBuilder();
        for (int i = 0; i < costs.length; i++) {
            // put X costs first
            if (costs[i].equals("X")) {
                x_cost.append("{X}");
            } else

                // hybrid mana
                if (costs[i].contains("/")) {
                    hyb_cost.append("{").append(costs[i]).append("}");
                } else

                    // Non-generic mana (WUBRG, Colorless, Snow and Phyrexian colorless)
                    if (costs[i].matches("W|U|B|R|G|C|S|P")) {
                        col_cost.append("{").append(costs[i]).append("}");
                    }

                    //
                    else {
                        gen_cost += Integer.parseInt(costs[i]);
                    }
        }

        String gen_cost_str = "";
        if (gen_cost > 0) {
            gen_cost_str = "{" + gen_cost + "}";
        }
        return x_cost + gen_cost_str + hyb_cost + col_cost;
    }


    public static void readUrl() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("/Users/zachharoian/Downloads/b4d3b6a8-3e4a-4e2b-900a-9a21fa0ced4c.json")) {
            Object obj = jsonParser.parse(reader);

            JSONArray list = (JSONArray) obj;
            list.forEach(card -> parseCard( (JSONObject) card));

        } catch (Exception e) {
            System.out.println(e);
        }



//        BufferedReader reader = null;
//        try {
//            URL url = new URL(urlString);
//            reader = new BufferedReader(new InputStreamReader(url.openStream()));
//            StringBuffer buffer = new StringBuffer();
//            int read;
//            char[] chars = new char[1024];
//            while ((read = reader.read(chars)) != -1)
//                buffer.append(chars, 0, read);
//            return buffer.toString();
//        } catch (Exception e) {
//            System.out.println(e);
//        } finally {
//            try {
//                if (reader != null)
//                    reader.close();
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//        }
//        return null;
    }


}
