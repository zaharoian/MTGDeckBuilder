package Database;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import Card.*;

import java.util.Arrays;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONDriver {
    public static void parseCard(JSONObject card) {
        // layout
        final Layout layout = Layout.fromString((String) card.get("layout"));

        if (layout == Layout.PLANAR || layout == Layout.SCHEME ||
                layout == Layout.VANGUARD || layout == Layout.AUGMENT ||
                layout == Layout.HOST || layout == Layout.ART_SERIES){
            // Don't process funny/illegal cards
            return;
        }

        // name
        final String name = (String) card.get("name");

        // id - unique Scryfall ID
        final UUID id = UUID.fromString((String) card.get("id"));

        // cmc
        final int cmc = (int)(double) card.get("cmc");

        // rarity
        final Rarity rarity = Rarity.fromString((String) card.get("rarity"));

        // Color Identities
        JSONArray color_identities = (JSONArray) card.get("color_identity");
        boolean w_ci = false;
        boolean u_ci = false;
        boolean b_ci = false;
        boolean r_ci = false;
        boolean g_ci = false;
        for (Object color_identity : color_identities) {
            switch ((String) color_identity) {
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
            layout == Layout.LEVELER || layout == Layout.SAGA ||
            layout == Layout.TOKEN || layout == Layout.EMBLEM) {
            JSONObject image_uris = (JSONObject) card.get("image_uris");
            final String image_uri = (String) image_uris.get("normal");

            final String mana_cost = (String) card.get("mana_cost");

            final String type_line = (String) card.get("type_line");

            final String oracle_text = (String) card.get("oracle_text");

            final String power = (String) card.get("power");

            final String toughness = (String) card.get("toughness");

            final String loyalty = (String) card.get("loyalty");

            // Colors
            JSONArray colors = (JSONArray) card.get("colors");
            boolean w = false;
            boolean u = false;
            boolean b = false;
            boolean r = false;
            boolean g = false;
            for (Object color : colors) {
                switch ((String) color) {
                    case "W" -> w = true;
                    case "U" -> u = true;
                    case "B" -> b = true;
                    case "R" -> r = true;
                    case "G" -> g = true;
                    default -> System.out.println("unhandled color (one side): " + color);
                }
            }

            // Save to db
            DBDriver.insertToCardsTable(id, name, layout, cmc, w_ci, u_ci, b_ci, r_ci, g_ci, standard, brawl, pioneer, historic, modern, pauper, legacy, penny, vintage, commander, rarity);
            DBDriver.insertToFacesTable(id, true, image_uri, oracle_text, power, toughness, mana_cost, loyalty, type_line, w, u, b, r, g);
        }
        // Multifaced Cards
        else if (layout == Layout.SPLIT || layout == Layout.FLIP ||
                 layout == Layout.TRANSFORM || layout == Layout.MODAL_DFC ||
                 layout == Layout.ADVENTURE || layout == Layout.DOUBLE_FACED_TOKEN) {

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
            String front_mana_cost = (String) front.get("mana_cost");
            String back_mana_cost = (String) back.get("mana_cost");
            String front_loyalty = (String) front.get("loyalty");
            String back_loyalty = (String) back.get("loyalty");
            String front_type_line = (String) front.get("type_line");
            String back_type_line = (String) back.get("type_line");

            boolean front_w = false;
            boolean front_u = false;
            boolean front_b = false;
            boolean front_r = false;
            boolean front_g = false;
            boolean back_w = false;
            boolean back_u = false;
            boolean back_b = false;
            boolean back_r = false;
            boolean back_g = false;

            // Two faces, one side
            if (layout == Layout.SPLIT || layout == Layout.FLIP ||
                layout == Layout.ADVENTURE) {
                // front_image
                JSONObject images = (JSONObject) card.get("image_uris");
                front_image = (String) images.get("normal");

                // Colors
                JSONArray colors = (JSONArray) card.get("colors");
                for (Object color : colors) {
                    switch ((String) color) {
                        case "W" -> {
                            front_w = true;
                            back_w = true;
                        }
                        case "U" -> {
                            front_u = true;
                            back_u = true;
                        }
                        case "B" -> {
                            front_b = true;
                            back_b = true;
                        }
                        case "R" -> {
                            front_r = true;
                            back_r = true;
                        }
                        case "G" -> {
                            front_g = true;
                            back_g = true;
                        }
                        default -> System.out.println("unhandled color (two faces, one side): " + color);
                    }
                }
            }
            // Two faces, two sides
            else {
                // front_image
                JSONObject front_images = (JSONObject) front.get("image_uris");
                front_image = (String) front_images.get("normal");
                // back_image
                JSONObject back_images = (JSONObject) back.get("image_uris");
                back_image = (String) back_images.get("normal");

                // Colors
                JSONArray colors_front = (JSONArray) front.get("colors");
                for (Object color : colors_front) {
                    switch ((String) color) {
                        case "W" -> front_w = true;
                        case "U" -> front_u = true;
                        case "B" -> front_b = true;
                        case "R" -> front_r = true;
                        case "G" -> front_g = true;
                        default -> System.out.println("unhandled color (front): " + color);
                    }
                }
                JSONArray colors_back = (JSONArray) back.get("colors");
                for (Object color : colors_back) {
                    switch ((String) color) {
                        case "W" -> back_w = true;
                        case "U" -> back_u = true;
                        case "B" -> back_b = true;
                        case "R" -> back_r = true;
                        case "G" -> back_g = true;
                        default -> System.out.println("unhandled color (back): '" + color);
                    }
                }
            }

            // Save to db
            DBDriver.insertToCardsTable(id, name, layout, cmc, w_ci, u_ci, b_ci, r_ci, g_ci, standard, brawl, pioneer, historic, modern, pauper, legacy, penny, vintage, commander, rarity);
            DBDriver.insertToFacesTable(id, true, front_image, front_oracle_text, front_power, front_toughness, front_mana_cost, front_loyalty, front_type_line, front_w, front_u, front_b, front_r, front_g);
            DBDriver.insertToFacesTable(id, false, back_image, back_oracle_text, back_power, back_toughness, back_mana_cost, back_loyalty, back_type_line, back_w, back_u, back_b, back_r, back_g);
        }
        // New/Unhandled Cards
        else {
            System.out.println("Unhandled type: " + layout);
        }

    }

    public static void readUrl(String urlString) {
        JSONParser jsonParser = new JSONParser();

//        try (FileReader reader = new FileReader("/Users/zachharoian/Downloads/b4d3b6a8-3e4a-4e2b-900a-9a21fa0ced4c.json")) {
//            Object obj = jsonParser.parse(reader);
//
//            JSONArray list = (JSONArray) obj;
//            list.forEach(card -> parseCard( (JSONObject) card));
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }

        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
            String json = buffer.toString();
            Object obj = jsonParser.parse(json);
            if (obj instanceof JSONObject) {
                // Single card
                parseCard((JSONObject) obj);
            } else {
                // Array of cards
                JSONArray list = (JSONArray) obj;
                list.forEach(card -> parseCard((JSONObject) card));
            }


        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }


}
