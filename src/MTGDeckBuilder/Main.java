package MTGDeckBuilder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import Database.*;
import Card.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.UUID;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Deckbuilder");
        Scene scene = new Scene(root, 600, 800);
        scene.getStylesheets().add("MTGDeckBuilder/cellStyle.css");

        primaryStage.setScene(scene);

        primaryStage.show();
    }


    public static void main(String[] args) {

        DBDriver.connect();
//        try (FileReader reader = new FileReader("/Users/zachharoian/Downloads/9e8c0009-787f-480b-84b6-bf297f1fb466.json")) {
//            JSONParser jsonParser = new JSONParser();
//            Object obj = jsonParser.parse(reader);
//
//            JSONDriver.parseCard( (JSONObject) obj);
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        JSONDriver.readUrl("https://c2.scryfall.com/file/scryfall-bulk/oracle-cards/oracle-cards-20210218220417.json");
//        DBDriver.getCard(UUID.fromString("5d131784-c1a3-463e-a37b-b720af67ab62"));
//        DBDriver.getCard(UUID.fromString("18c0b3b3-bb62-42c5-9869-386af0540a9b"));

        launch(args);
        DBDriver.disconnect();
    }

}


