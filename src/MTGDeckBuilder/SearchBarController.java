package MTGDeckBuilder;

import Card.*;
import Card.Color;
import Database.DBDriver;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.*;

public class SearchBarController {

    public TextField searchTextField;
    public Button searchButton;
    public Button reloadSearchButton;
    public TextField textTextField;
    public TextField typeTextField;
    public RadioButton colorRadioButton;
    public RadioButton identityRadioButton;
    public ToggleGroup color_identity;
    public ToggleButton whiteButton;
    public ToggleButton blueButton;
    public ToggleButton blackButton;
    public ToggleButton redButton;
    public ToggleButton greenButton;
    public ToggleButton colorlessButton;
    public ToggleButton commonButton;
    public ToggleButton uncommonButton;
    public ToggleButton rareButton;
    public ToggleButton mythicButton;
    public TextField manaCostTextField;
    public TextField statsTextField;
    public ComboBox<String> formatComboBox;
    public Button orderDesc;
    public ListView<Card> listView;
    public TitledPane titlePane;
    public Label numResults;
    public ImageView orderDescImageView;
    public ComboBox<String> sortComboBox;
    public ImageView sortComboBoxImageView;
    public ImageView orderTextImageView;
    public ListView<Card> deckView;

    private boolean textEmpty = true;
    private boolean typeEmpty = true;
    private boolean manaEmpty = true;
    private boolean statsEmpty = true;
    private boolean whiteEmpty = true;
    private boolean blueEmpty = true;
    private boolean blackEmpty = true;
    private boolean redEmpty = true;
    private boolean greenEmpty = true;
    private boolean colorlessEmpty = true;
    private boolean commonEmpty = true;
    private boolean uncommonEmpty = true;
    private boolean rareEmpty = true;
    private boolean mythicEmpty = true;
    private boolean formatEmpty = true;
    private boolean searchEmpty = true;

    private boolean desc = false;
    private boolean text = false;


    public static ObservableList<Card> data = FXCollections.observableArrayList();
    public static ObservableList<Card> deck = FXCollections.observableArrayList();
    public static UUID[] full_data;
    private static int step = 10;
    private static int start = 0;
    private boolean scrollListenerEnabled = false;

    public void initialize() {
        listView.setCellFactory(new Callback<ListView<Card>, ListCell<Card>>() {
            @Override
            public ListCell<Card> call(ListView<Card> cardListView) {
                return new ImageCell();
            }
        });
        deckView.setCellFactory(new Callback<ListView<Card>, ListCell<Card>>() {
            @Override
            public ListCell<Card> call(ListView<Card> cardListView) {
                return new CardCell();
            }
        });
        deckView.setFixedCellSize(345);
        listView.setFixedCellSize(345);
        deckView.setSelectionModel(new NoSelectionModel<Card>());
        listView.setSelectionModel(new NoSelectionModel<Card>());
        deckView.setFocusTraversable(false);
        listView.setFocusTraversable(false);
        listView.setItems(data);
        deckView.setItems(deck);

        ObservableList<String> sortNames = FXCollections.observableArrayList();
        sortNames.addAll("Name", "CMC", "Power", "Toughness", "Rarity");
        sortComboBox.setItems(sortNames);
        sortComboBox.setValue("Name");

        ChangeListener<String> resetTextListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String id = ((TextField) ((StringProperty) observableValue).getBean()).getId();
                switch (id) {
                    case "textTextField": textEmpty = t1.isBlank(); break;
                    case "typeTextField": typeEmpty = t1.isBlank(); break;
                    case "manaCostTextField": manaEmpty = t1.isBlank(); break;
                    case "statsTextField": statsEmpty = t1.isBlank(); break;
                    case "searchTextField": searchEmpty = t1.isBlank(); break;
                    default: break;
                }
                checkSearchButton();
                checkResetButton();
            }
        };

        textTextField.textProperty().addListener(resetTextListener);
        typeTextField.textProperty().addListener(resetTextListener);
        manaCostTextField.textProperty().addListener(resetTextListener);
        statsTextField.textProperty().addListener(resetTextListener);
        searchTextField.textProperty().addListener(resetTextListener);

        ChangeListener<Boolean> resetBooleanListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                String id = ((ToggleButton) ((BooleanProperty) observableValue).getBean()).getId();
                switch (id) {
                    case "whiteButton": whiteEmpty = !t1; break;
                    case "blueButton": blueEmpty = !t1; break;
                    case "blackButton": blackEmpty = !t1; break;
                    case "redButton": redEmpty = !t1; break;
                    case "greenButton": greenEmpty = !t1; break;
                    case "colorlessButton": colorlessEmpty = !t1; break;
                    case "commonButton": commonEmpty = !t1; break;
                    case "uncommonButton": uncommonEmpty = !t1; break;
                    case "rareButton": rareEmpty = !t1; break;
                    case "mythicButton": mythicEmpty = !t1; break;
                    default: break;
                }
                checkSearchButton();
                checkResetButton();
            }
        };

        whiteButton.selectedProperty().addListener(resetBooleanListener);
        blueButton.selectedProperty().addListener(resetBooleanListener);
        blackButton.selectedProperty().addListener(resetBooleanListener);
        redButton.selectedProperty().addListener(resetBooleanListener);
        greenButton.selectedProperty().addListener(resetBooleanListener);
        colorlessButton.selectedProperty().addListener(resetBooleanListener);
        commonButton.selectedProperty().addListener(resetBooleanListener);
        uncommonButton.selectedProperty().addListener(resetBooleanListener);
        rareButton.selectedProperty().addListener(resetBooleanListener);
        mythicButton.selectedProperty().addListener(resetBooleanListener);



        formatComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                formatEmpty = t1 == null || t1.isBlank();
                checkSearchButton();
                checkResetButton();
            }
        });

        loadDeckList();

    }



    private ScrollBar getListViewScrollBar(ListView<?> listView) {
        ScrollBar scrollBar = null;
        for (Node node : listView.lookupAll(".scroll-bar")) {
            if (node instanceof ScrollBar) {
                ScrollBar bar = (ScrollBar) node;
                if (bar.getOrientation().equals(Orientation.VERTICAL)) {
                    scrollBar = bar;
                }
            }
        }
        return scrollBar;
    }


    private void checkResetButton() {
        if (!textEmpty || !typeEmpty || !manaEmpty || !statsEmpty ||
            !whiteEmpty || !blueEmpty || !blackEmpty || !redEmpty || !greenEmpty || !colorlessEmpty ||
            !commonEmpty || !uncommonEmpty || !rareEmpty || !mythicEmpty ||
            !formatEmpty) {
            reloadSearchButton.setDisable(false);
            titlePane.setText("Advanced (Filters modified)");
        } else {
            reloadSearchButton.setDisable(true);
            titlePane.setText("Advanced");
        }
    }

    private void checkSearchButton() {
        if (!textEmpty || !typeEmpty || !manaEmpty || !statsEmpty ||
                !whiteEmpty || !blueEmpty || !blackEmpty || !redEmpty || !greenEmpty || !colorlessEmpty ||
                !commonEmpty || !uncommonEmpty || !rareEmpty || !mythicEmpty ||
                !formatEmpty || !searchEmpty) {
            searchButton.setDisable(false);
        } else {
            searchButton.setDisable(true);
        }
    }

    private void loadDeckList() {
        UUID[] ids = DBDriver.getDeckItems();
        Task<Card> task = new Task<Card>() {
            @Override
            protected Card call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        deckView.getItems().clear();
                        for (UUID id : ids) {
                            Card card = DBDriver.getCard(id);
                            deck.add(card);

                        }
                    }
                });
                return null;
            }
        };

        Thread th = new Thread(task);
        th.start();
    }

    private void loadResults(boolean newResults) {
        System.out.println("load results: " + newResults);
        System.out.println("start: " + start);
        System.out.println("full_data.length: " + full_data.length);
        // Do nothing if there are no more results to return
        if (start >= full_data.length) return;

        int end = Math.min(start + step, full_data.length);

        System.out.println("end: " +end);
        UUID[] page = new UUID[end - start];
        System.out.println("page length: " + page.length);

        for (int i = start; i < end; i++ ) {
            page[i - start] = full_data[i];
        }
        start = end;

        ArrayList<Card> cards = new ArrayList<Card>();
        for (UUID id : page) {
            Card card = DBDriver.getCard(id);
            cards.add(card);
        }

        // Create a task to retrieve cards
        Task<Card> task = new Task<Card>() {
            @Override
            protected Card call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (newResults) {
                            // Clear results
                            listView.getItems().clear();
                        }
                        int oldSize = data.size();

                        data.addAll(cards);

                        ScrollBar bar = getListViewScrollBar(listView);
                        double targetValue = bar.getValue() * oldSize;
                        bar.setValue(targetValue / data.size());
                    }
                });
                return null;
            }
        };

        Task<Card> imageDownload = new Task<Card>() {
            @Override
            protected Card call() throws Exception {
                for (Card card : cards) {
                    card.downloadImages();
                }
                return null;
            }
        };

        Thread th1 = new Thread(task);
        th1.start();
        Thread th2 = new Thread(imageDownload);
        th2.start();

    }

    public void searchOnAction(ActionEvent actionEvent) {
        start = 0;
        if (searchButton.isDisable()) {
            numResults.setText("Total results: 0");
            return;
        }

        if (titlePane.isExpanded()) {
            titlePane.setExpanded(false);
        }

        if (!scrollListenerEnabled) {
            ScrollBar listViewScrollBar = getListViewScrollBar(listView);
            listViewScrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {
                double position = newValue.doubleValue();
                ScrollBar scrollBar = getListViewScrollBar(listView);
                if (position == scrollBar.getMax()) {
                    loadResults(false);
                }
            });
            scrollListenerEnabled = true;
        }

        ArrayList<Color> colors = new ArrayList<Color>();
        if (whiteButton.isSelected()) colors.add(Color.WHITE);
        if (blueButton.isSelected()) colors.add(Color.BLUE);
        if (blackButton.isSelected()) colors.add(Color.BLACK);
        if (redButton.isSelected()) colors.add(Color.RED);
        if (greenButton.isSelected()) colors.add(Color.GREEN);


        ArrayList<Rarity> rarities = new ArrayList<Rarity>();
        if (commonButton.isSelected()) rarities.add(Rarity.COMMON);
        if (uncommonButton.isSelected()) rarities.add(Rarity.UNCOMMON);
        if (rareButton.isSelected()) rarities.add(Rarity.RARE);
        if (mythicButton.isSelected()) rarities.add(Rarity.MYTHIC);

        String query = DBDriver.constructSQLSearch(
                searchTextField.getText(),
                textTextField.getText(),
                Format.fromString((String) formatComboBox.getValue()),
                colors.isEmpty() ? null : colors.toArray(new Color[0]),
                typeTextField.getText(),
                null,
                manaCostTextField.getText(),
                null,
                null,
                null,
                rarities.toArray(new Rarity[0]),
                identityRadioButton.isSelected(),
                sortComboBox.getValue().toLowerCase(),
                desc);

        System.out.println(query);
        full_data = DBDriver.searchIDs(query);
        numResults.setText("Total results: " + full_data.length);

        loadResults(true);
    }

    public void colorOnAction(ActionEvent actionEvent) {
        ToggleButton button = (ToggleButton) actionEvent.getSource();
        String id = button.getId();
        boolean selected = button.isSelected();
        button.setOpacity(selected ? 1 : 0.4);

        if (id.equals("colorlessButton")) {
            if (selected) {
                whiteButton.setSelected(true);
                whiteButton.fire();
                blueButton.setSelected(true);
                blueButton.fire();
                blackButton.setSelected(true);
                blackButton.fire();
                redButton.setSelected(true);
                redButton.fire();
                greenButton.setSelected(true);
                greenButton.fire();
            }
        } else if (id.equals("whiteButton") || id.equals("blueButton") || id.equals("blackButton") ||
                   id.equals("redButton") || id.equals("greenButton")) {
            if (selected) {
                colorlessButton.setSelected(true);
                colorlessButton.fire();
            }
        }
    }

    public void onSortMouseClicked(MouseEvent mouseEvent) {
        sortComboBox.show();
    }



    public class ImageCell extends ListCell<Card> implements PropertyChangeListener {
        private final ImageView cardImageView = new ImageView();
        private final HBox invalidHighlightBorder = new HBox(new Rectangle(236, 340, null));
        private final Label numCopiesLabel = new Label("10");
        private final Button toggleCardFlag = new Button();
        private final BorderPane cardTop = new BorderPane();
        private final ProgressIndicator loadingIndicator = new ProgressIndicator();
        private final StackPane imageStackPane = new StackPane(cardImageView, invalidHighlightBorder, cardTop, loadingIndicator);
        private final Button addButton = new Button();
        private final Button moreButton = new Button();
        private final Spinner<Integer> addSpinner = new Spinner<Integer>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 200));
        private final StackPane addButtonPane = new StackPane(addButton, addSpinner);
        private final VBox buttonVBox = new VBox(addButtonPane, moreButton);
        private final HBox root = new HBox(imageStackPane, buttonVBox);

        private String path = null;
        private String path2 = null;
        private boolean flipped = false;
        private boolean imageLoaded = false;
        private Card card = null;
        private int num_copies = 0;

        public ImageCell() {
            root.setAlignment(Pos.CENTER);

            //  Image setup
            cardImageView.setPreserveRatio(true);
            Group clip = new Group();
            Rectangle rect1 = new Rectangle(0, 0, 236, 340);
            Rectangle rect2 = new Rectangle(118, 0, 118, 340);
            rect1.setArcWidth(25);
            rect1.setArcHeight(25);
            clip.getChildren().addAll(rect1, rect2);
            cardImageView.setClip(clip);
            cardImageView.setViewport(new Rectangle2D(0, 0, 236, 340));
//            imageStackPane.setOnDragDetected(new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent mouseEvent) {
//                    if (imageLoaded) {
//                        Dragboard db = imageStackPane.startDragAndDrop(TransferMode.COPY);
//
//                        ClipboardContent content = new ClipboardContent();
//                        content.putImage(new Image(path, 122, 170, true, true));
//                        db.setContent(content);
//
//                        mouseEvent.consume();
//                    }
//                }
//            });

            // Toggle flag
            toggleCardFlag.setVisible(false);
            toggleCardFlag.setDisable(true);
            toggleCardFlag.getStyleClass().add("toggle-flag");
            toggleCardFlag.setText(null);
            toggleCardFlag.setAlignment(Pos.TOP_RIGHT);
            toggleCardFlag.setFocusTraversable(false);
            toggleCardFlag.setOnAction(new EventHandler<ActionEvent>() {
                                           @Override
                                           public void handle(ActionEvent actionEvent) {
                                               if (imageLoaded) {
                                                   flipped = !flipped;
                                                   cardImageView.setImage(new Image(flipped ? path2 : path, 244, 340, true, true));
                                               }
                                           }
                                       }
            );
            // Icon
            Region toggleIcon = new Region();
            toggleIcon.getStyleClass().add("toggle-icon");
            toggleCardFlag.setGraphic(toggleIcon);

            // Flag spacer
            Pane spacer = new Pane();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            // Number of copies flag
            numCopiesLabel.setVisible(false);
            numCopiesLabel.getStyleClass().add("num-copies-flag");
            numCopiesLabel.setAlignment(Pos.CENTER);

            // Flag container
            HBox hboxFlags = new HBox(numCopiesLabel, spacer, toggleCardFlag);
            cardTop.topProperty().set(hboxFlags);

            // Invalid Highlight Border
            invalidHighlightBorder.getStyleClass().add("image-view-wrapper");
            invalidHighlightBorder.setFocusTraversable(false);
            invalidHighlightBorder.setVisible(false);

            // Add Button
            addButton.setMaxHeight(340);
            addButton.setMaxWidth(32);
            addButton.getStyleClass().add("card-button");
            addButton.setDisable(true);
            VBox.setVgrow(addButton, Priority.ALWAYS);
            addButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    num_copies = 1;
                    changeCopies();
                    enableAddSpinner();
                    DBDriver.setCopyNumber(card.getId(), 1);
                    loadDeckList();
                }
            });
            // Icon
            Region icon = new Region();
            icon.getStyleClass().add("plus-icon");
            addButton.setGraphic(icon);

            // Add Spinner
            addSpinner.getStyleClass().add("add-spinner");
            addSpinner.setDisable(true);
            VBox.setVgrow(addSpinner, Priority.ALWAYS);
            addSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
            addSpinner.setMaxHeight(340);
            addSpinner.setMaxWidth(32);
            addSpinner.setEditable(false);
            addSpinner.setVisible(false);
            addSpinner.setFocusTraversable(false);
            addSpinner.editorProperty().get().setAlignment(Pos.CENTER);
            addSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                if (newValue == 0) {
                    enableAddButton();
                }
                num_copies = newValue;
                changeCopies();
                DBDriver.setCopyNumber(card.getId(), num_copies);
                loadDeckList();
            });

            moreButton.setMaxHeight(340);
            moreButton.setPrefHeight(169);
            moreButton.setMaxWidth(32);

            Region arrow_icon = new Region();
            arrow_icon.getStyleClass().add("arrow-icon");
            moreButton.setGraphic(arrow_icon);
            moreButton.getStyleClass().add("card-button-blue");
            VBox.setVgrow(moreButton, Priority.ALWAYS);


            addButtonPane.setMaxHeight(340);
            addButtonPane.setPrefHeight(170);
            addButtonPane.setMaxWidth(32);
        }

        @Override
        public void updateItem(Card newCard, boolean empty) {
            super.updateItem(newCard, empty);
            if (!empty || newCard != null) {

                card = newCard;

                resetCard();
                path = card.getFront_image_path();
                num_copies = DBDriver.getCopyNumber(card.getId());
                changeCopies();
                if (num_copies > 0) {
                    enableAddSpinner();
                } else {
                    enableAddButton();
                }

                if (card.isDouble_faced()) {
                    toggleCardFlag.setVisible(true);
                    toggleCardFlag.setDisable(false);
                }

                if (card.getCardsDownloaded()) {
                    loadImage();
                } else {
                    card.addCardsDownloadedListener(this);
                }
            } else {
                setGraphic(null);
            }
        }

        public void loadImage() {
            File file = new File(card.getFront_image_path());
            System.out.println(file);
            path = file.toURI().toString(); // 236
            System.out.println(path);
            Image image = new Image(path, 244, 340, true, true);
            System.out.println(image);
            cardImageView.setImage(image);
            imageLoaded = true;
            loadingIndicator.setVisible(false);
            addButton.setDisable(false);
            if (card.isDouble_faced()) {
                file = new File(card.getBack_image_path());
                path2 = file.toURI().toString();
            }
        }

        private void changeCopies() {
            numCopiesLabel.setText(String.valueOf(num_copies));
            if (num_copies > 0) {
                numCopiesLabel.setVisible(true);
            } else {
                numCopiesLabel.setVisible(false);
            }
        }

        private void enableAddButton() {
            addButton.setVisible(true);
            addButton.setDisable(false);
            addSpinner.setVisible(false);
            addSpinner.setDisable(true);
        }

        private void enableAddSpinner() {
            addButton.setVisible(false);
            addButton.setDisable(true);
            addSpinner.setVisible(true);
            addSpinner.setDisable(false);
            addSpinner.getValueFactory().setValue(num_copies);
        }

        private void resetCard() {
            setGraphic(root);
            num_copies = 0;
            cardImageView.setImage(null);
            enableAddButton();
            invalidHighlightBorder.setVisible(false);
            toggleCardFlag.setVisible(false);
            toggleCardFlag.setDisable(true);
            numCopiesLabel.setVisible(false);
            loadingIndicator.setVisible(true);
        }


        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("ListenableBoolean") && (boolean) evt.getNewValue()) {
                loadImage();
            }
        }
    }

    public class CardCell extends ListCell<Card> implements PropertyChangeListener {
        private final ImageView cardImageView = new ImageView();
        private final HBox invalidHighlightBorder = new HBox(new Rectangle(236, 340, null));
        private final Label numCopiesLabel = new Label();
        private final Button toggleCardFlag = new Button();
        private final BorderPane cardTop = new BorderPane();
        private final ProgressIndicator loadingIndicator = new ProgressIndicator();
        private final StackPane imageStackPane = new StackPane(cardImageView, invalidHighlightBorder, cardTop, loadingIndicator);
        private final HBox root = new HBox(imageStackPane);

        private String path = null;
        private String path2 = null;
        private boolean flipped = false;
        private boolean imageLoaded = false;
        private Card card = null;
        private int num_copies = 0;

        public CardCell() {
            root.setAlignment(Pos.CENTER);

            //  Image setup
            Rectangle rect1 = new Rectangle(0, 0, 244, 340);
            rect1.setArcWidth(25);
            rect1.setArcHeight(25);
            cardImageView.setClip(rect1);
            cardImageView.setPreserveRatio(true);

            // Toggle flag
            toggleCardFlag.setVisible(false);
            toggleCardFlag.setDisable(true);
            toggleCardFlag.getStyleClass().add("toggle-flag");
            toggleCardFlag.setText(null);
            toggleCardFlag.setAlignment(Pos.TOP_RIGHT);
            toggleCardFlag.setFocusTraversable(false);
            toggleCardFlag.setOnAction(new EventHandler<ActionEvent>() {
                                           @Override
                                           public void handle(ActionEvent actionEvent) {
                                               if (imageLoaded) {
                                                   flipped = !flipped;
                                                   cardImageView.setImage(new Image(flipped ? path2 : path, 244, 340, true, true));
                                               }
                                           }
                                       }
            );
            // Icon
            Region toggleIcon = new Region();
            toggleIcon.getStyleClass().add("toggle-icon");
            toggleCardFlag.setGraphic(toggleIcon);

            // Flag spacer
            Pane spacer = new Pane();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            // Number of copies flag
            numCopiesLabel.setVisible(false);
            numCopiesLabel.getStyleClass().add("num-copies-flag");
            numCopiesLabel.setAlignment(Pos.CENTER);

            // Flag container
            HBox hboxFlags = new HBox(numCopiesLabel, spacer, toggleCardFlag);
            cardTop.topProperty().set(hboxFlags);

            // Invalid Highlight Border
            invalidHighlightBorder.getStyleClass().add("image-view-wrapper");
            invalidHighlightBorder.setFocusTraversable(false);
            invalidHighlightBorder.setVisible(false);
        }

        @Override
        public void updateItem(Card newCard, boolean empty) {
            super.updateItem(newCard, empty);
            if (!empty || newCard != null) {
                // If it is a new cell
                if (card != null) card.removeCardsDownloadedListener(this);

                card = newCard;

                resetCard();
                path = card.getFront_image_path();
                num_copies = DBDriver.getCopyNumber(card.getId());
                changeCopies();

                if (card.isDouble_faced()) {
                    toggleCardFlag.setVisible(true);
                    toggleCardFlag.setDisable(false);
                }
                if (card.getCardsDownloaded()) {
                    loadImage();
                } else {
                    card.addCardsDownloadedListener(this);
                }
            } else {
                setGraphic(null);
                card = null;
            }
        }

        public void loadImage() {
            File file = new File(card.getFront_image_path());
            path = file.toURI().toString(); // 236
            Image image = new Image(path, 244, 340, true, true);
            cardImageView.setImage(image);
            imageLoaded = true;
            loadingIndicator.setVisible(false);
            if (card.isDouble_faced()) {
                file = new File(card.getBack_image_path());
                path2 = file.toURI().toString();
            }
        }

        private void changeCopies() {
            numCopiesLabel.setText(String.valueOf(num_copies));
            if (num_copies > 0) {
                numCopiesLabel.setVisible(true);
            } else {
                numCopiesLabel.setVisible(false);
            }
        }

        private void resetCard() {
            setGraphic(root);
            num_copies = 0;
            cardImageView.setImage(null);
            invalidHighlightBorder.setVisible(false);
            toggleCardFlag.setVisible(false);
            toggleCardFlag.setDisable(true);
            numCopiesLabel.setVisible(false);
            loadingIndicator.setVisible(true);
        }


        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("ListenableBoolean") && (boolean) evt.getNewValue()) {
                loadImage();
            }
        }
    }

    public void reloadSearchOnAction(ActionEvent actionEvent) {
        reloadSearchButton.setDisable(true);
        textTextField.clear();
        typeTextField.clear();
        manaCostTextField.clear();
        statsTextField.clear();
        whiteButton.setSelected(true);
        whiteButton.fire();
        blueButton.setSelected(true);
        blueButton.fire();
        blackButton.setSelected(true);
        blackButton.fire();
        redButton.setSelected(true);
        redButton.fire();
        greenButton.setSelected(true);
        greenButton.fire();
        colorlessButton.setSelected(true);
        colorlessButton.fire();
        commonButton.setSelected(true);
        commonButton.fire();
        uncommonButton.setSelected(true);
        uncommonButton.fire();
        rareButton.setSelected(true);
        rareButton.fire();
        mythicButton.setSelected(true);
        mythicButton.fire();
        formatComboBox.setValue(null);
        colorRadioButton.setSelected(true);
        titlePane.setText("Advanced");
    }

    public void orderNameOnAction(ActionEvent actionEvent) {
        if (sortComboBox.getValue().toLowerCase().equals("name")) {
            sortComboBoxImageView.setImage(new Image("MTGDeckBuilder/svgtopng/alphabetical-order.png"));
        } else {
            sortComboBoxImageView.setImage(new Image("MTGDeckBuilder/svgtopng/numeric-ascending-sort.png"));
        }
        if (!data.isEmpty()) {
           searchButton.fire();
        }


    }

    public void orderDescOnAction(ActionEvent actionEvent) {
        desc = !desc;
        if (desc) {
            orderDesc.setText("Descending");
            orderDescImageView.setImage(new Image("MTGDeckBuilder/svgtopng/sort-descending.png"));
        } else {
            orderDesc.setText("Ascending");
            orderDescImageView.setImage(new Image("MTGDeckBuilder/svgtopng/sort-down.png"));
        }
        if (!data.isEmpty()) {
            searchButton.fire();
        }
    }

//    public void orderTextOnAction(ActionEvent actionEvent) {
//        text = !text;
//        if (text) {
//            orderText.setText("Text");
//            orderTextImageView.setImage(new Image("MTGDeckBuilder/svgtopng/text-letter.png"));
//        } else {
//            orderText.setText("Image");
//            orderTextImageView.setImage(new Image("MTGDeckBuilder/svgtopng/image-filled-square-button.png"));
//        }
//        if (!data.isEmpty()) {
//            searchButton.fire();
//        }
//    }
    public class NoSelectionModel<T> extends MultipleSelectionModel<T> {

        @Override
        public ObservableList<Integer> getSelectedIndices() {
            return FXCollections.emptyObservableList();
        }

        @Override
        public ObservableList<T> getSelectedItems() {
            return FXCollections.emptyObservableList();
        }

        @Override
        public void selectIndices(int index, int... indices) {
        }

        @Override
        public void selectAll() {
        }

        @Override
        public void selectFirst() {
        }

        @Override
        public void selectLast() {
        }

        @Override
        public void clearAndSelect(int index) {
        }

        @Override
        public void select(int index) {
        }

        @Override
        public void select(T obj) {
        }

        @Override
        public void clearSelection(int index) {
        }

        @Override
        public void clearSelection() {
        }

        @Override
        public boolean isSelected(int index) {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public void selectPrevious() {
        }

        @Override
        public void selectNext() {
        }
    }
}

