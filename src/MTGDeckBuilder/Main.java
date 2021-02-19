package MTGDeckBuilder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import Database.DBDriver;
import Card.*;
import Database.JSONDriver;

import java.util.Arrays;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        DBDriver.connect();
        JSONDriver.readUrl("https://c2.scryfall.com/file/scryfall-bulk/oracle-cards/oracle-cards-20210218220417.json");
        Card[] cards = DBDriver.search_cards("SELECT * FROM cards");
        for (int i = 0; i < cards.length; i++) {
            System.out.println(cards[i]);
        }
        DBDriver.disconnect();
        launch(args);
    }

}


