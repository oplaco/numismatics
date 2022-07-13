package com.numismatics_gae;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class PrimaryController extends ActionEvent{

    @FXML
    Button addCoinButton, removeCoinButton, statisticsButton,andorraButton,austriaButton, belgiumButton, cyprusButton, estoniaButton,
            finlandButton, franceButton, germanyButton, greeceButton, irelandButton, italyButton, latviaButton,
            lithuaniaButton, luxembourgButton, maltaButton, monacoButton, netherlandsButton, portugalButton,
            sanMarinoButton, slovakiaButton, sloveniaButton, spainButton, vaticanCityButton;

    final ArrayList<Button> buttonList = new ArrayList<Button>(Arrays.asList(andorraButton, austriaButton, belgiumButton, cyprusButton, estoniaButton,
            finlandButton, franceButton, germanyButton, greeceButton, irelandButton, italyButton, latviaButton,
            lithuaniaButton, luxembourgButton, maltaButton, monacoButton, netherlandsButton, portugalButton,
            sanMarinoButton, slovakiaButton, sloveniaButton, spainButton, vaticanCityButton));

    @FXML
    TableView serieTableView;

    @FXML
    TableColumn yearTableColumn,oneCentTableColumn,twoCentTableColumn,fiveCentTableColumn,tenCentTableColumn,twentyCentTableColumn,fiftyCentTableColumn,oneEuroTableColumn,twoEuroTableColumn; 

    @FXML
    Label currentCollectionLabel,countryData1Label,countryData2Label,countryData3Label,countryData4Label;

    @FXML
    ImageView flagImageView;

    @FXML
    AnchorPane leftAnchorPane, rightAnchorPane;

    static ArrayList<Collection> collectionArrayList = new ArrayList<>();
    static int calIndex = 0; // CollectionArrayListIndex
    static String currentCountry = "Andorra"; // By default
    static String[][] countriesData = new String[23][5];
    File imagePath = new File(App.projectRootPath + "\\src\\main\\resources\\Images\\" + currentCountry + ".png");

    int currentCollectionIndex;
    static Collection currentCollection;
    
    static void programBoot(){
        File folder = new File(App.projectRootPath + "\\src\\main\\resources\\collections\\");
        for (File fileEntry : folder.listFiles()) {
                // adds already existing collections to collectionArrayList
            collectionArrayList.add(new Collection(fileEntry.getName(),true));
            collectionArrayList.get(collectionArrayList.size() - 1).setCollectionName(fileEntry.getName());

        }
        System.out.println("Existing collections:");
        for (int i = 0; i < collectionArrayList.size(); i++) {
            System.out.println(collectionArrayList.get(i).getCollectionName());
        }

        // Reads countries aditional info csv
        String filepath = "src\\main\\resources\\data\\countriesData.csv";
        BufferedReader reader = null;
        String line = "";
        try {
            reader = new BufferedReader(new FileReader(filepath));
            int j = 0;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                for (int i = 0; i < values.length; i++) {
                    countriesData[j][i] = values[i];
                }
                j++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void returnBoot(Collection previuosCollection){
        currentCollection=SecondaryController.currentCollection;
    }

    public void printData(){
        currentCollectionLabel.setText("Current collection: " + currentCollection.getCollectionName());
        currentCollectionLabel.setAlignment(Pos.CENTER);
         ObservableList<Serie> coinObservableList = FXCollections.observableArrayList();
         for (int i=0; i<Country.seriesYear.length;i++){
            Serie serie = currentCollection.getCountryArray()[getCurrentCountryIndex(currentCountry)].getSerie()[i];
            coinObservableList.add(serie);
         }
         // ObservableList<Integer> yearObservableList = FXCollections.observableArrayList(Country.seriesYear);

        /* // https://stackoverflow.com/questions/45968324/how-to-use-setcellvaluefactory-and-setcellfactory
        oneCentTableColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn param) {
                return new TableCell<Serie, Double>() {
    
                    @Override
                    public void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            setStyle(arg0);
                            // Get fancy and change color based on data
                            if(item.toString().contains("j")) 
                                this.setTextFill(Color.BLUEVIOLET);
                            setText(item.toString());
                        }
                    }
                };
            }
        });
        */
        yearTableColumn.setCellValueFactory(new PropertyValueFactory<Serie,Integer>("serieYear")); 
        oneCentTableColumn.setCellValueFactory(new PropertyValueFactory<Serie, Boolean>("oneCent"));
         // It will look in the Serie class for the Serie.oneCentProperty() method and if it doesnt exist getOneCent()
         oneCentTableColumn.setCellFactory(getMyFactoryCallback("0.01"));
         twoCentTableColumn.setCellValueFactory(new PropertyValueFactory<Serie, Boolean>("twoCent"));
         twoCentTableColumn.setCellFactory(getMyFactoryCallback("0.02"));
         fiveCentTableColumn.setCellValueFactory(new PropertyValueFactory<Serie, Boolean>("fiveCent"));
         fiveCentTableColumn.setCellFactory(getMyFactoryCallback("0.05"));
         tenCentTableColumn.setCellValueFactory(new PropertyValueFactory<Serie, Boolean>("tenCent"));
         tenCentTableColumn.setCellFactory(getMyFactoryCallback("0.10"));
         twentyCentTableColumn.setCellValueFactory(new PropertyValueFactory<Serie, Boolean>("twentyCent"));
         twentyCentTableColumn.setCellFactory(getMyFactoryCallback("0.20"));
         fiftyCentTableColumn.setCellValueFactory(new PropertyValueFactory<Serie, Boolean>("fiftyCent"));
         fiftyCentTableColumn.setCellFactory(getMyFactoryCallback("0.50"));
         oneEuroTableColumn.setCellValueFactory(new PropertyValueFactory<Serie, Boolean>("oneEuro"));
         oneEuroTableColumn.setCellFactory(getMyFactoryCallback("1.0"));
         twoEuroTableColumn.setCellValueFactory(new PropertyValueFactory<Serie, Boolean>("twoEuro"));
         twoEuroTableColumn.setCellFactory(getMyFactoryCallback("2.0"));

         serieTableView.setItems(coinObservableList);
    }

    public Callback<TableColumn<Serie,Boolean>,TableCell<Serie,Boolean>> getMyFactoryCallback(String text){
        Callback factory = new Callback<TableColumn<Serie,Boolean>,TableCell<Serie,Boolean>>() {
            @Override
            public TableCell<Serie, Boolean> call(TableColumn<Serie, Boolean> arg0) {
                // TODO Auto-generated method stub
                return new TableCell<Serie, Boolean>() {
                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            if (item==true) {
                                setText(text);
                                setStyle("-fx-background-color: green");
                            } else {
                                setStyle("-fx-background-color: red");
                                setText(null);
                            }
                        }
                    }
                };
            }
        };
        return factory;
    }

    @FXML
    private void newCollection() {
        Stage newFolderStage = new Stage();
        newFolderStage.initModality(Modality.APPLICATION_MODAL);
        newFolderStage.setMinWidth(250);

        Label label1 = new Label("New Collection Name:");
        TextField newCollectionNameTextField = new TextField();
        Button button = new Button("Ok");
        button.setOnAction(e -> {
            String newCollectionName = newCollectionNameTextField.getText();
            new Collection(newCollectionName);
            System.out.println("New Collection clicked");
            updateCurrentCollection(newCollectionName);
            leftAnchorPane.setVisible(true);
            rightAnchorPane.setVisible(true);
            printData();
            newFolderStage.close();
        });
        VBox layout = new VBox();
        layout.getChildren().addAll(label1, newCollectionNameTextField, button);
        layout.setAlignment(Pos.CENTER);
        Scene addCoinScene = new Scene(layout);
        newFolderStage.setScene(addCoinScene);
        newFolderStage.showAndWait();
    }

    @FXML
    private void openCollection() {
    Stage openCollectionStage = new Stage();
    openCollectionStage.initModality(Modality.APPLICATION_MODAL);
    openCollectionStage.setMinWidth(250);

    Label openCollectionLabel1 = new Label("Existing Collection Name:");
    Label warningLabel = new Label();
    TextField openCollectionTextField = new TextField();
    Button openCollectionButton = new Button("Ok");

    openCollectionButton.setOnAction(e -> {
        boolean collectionExists = false;
        for (int i = 0; i < collectionArrayList.size(); i++) {
            if (collectionArrayList.get(i).getCollectionName().equals(openCollectionTextField.getText())) {
                collectionExists = true;
            }
        }
        if (collectionExists == true) {
            updateCurrentCollection(openCollectionTextField.getText());
            leftAnchorPane.setVisible(true);
            rightAnchorPane.setVisible(true);
            printData();
            // collectionOpened = true;
            openCollectionStage.close();
        } else {
            warningLabel.setText(openCollectionTextField.getText() + " collection does not exist");
        }
    });

    VBox openCollectionLayout = new VBox();
    openCollectionLayout.getChildren().addAll(openCollectionLabel1, openCollectionTextField, warningLabel,
            openCollectionButton);
    openCollectionLayout.setAlignment(Pos.CENTER);
    Scene addCoinScene = new Scene(openCollectionLayout);
    openCollectionStage.setScene(addCoinScene);
    openCollectionStage.showAndWait();
    setCountryDataLabels(currentCountry);
    }

    @FXML
    private void saveFile() {
        currentCollection.createCSV(false);
        System.out.println("File Saved");
    }

    @FXML
    private void editCoin(ActionEvent event){ //Check out this eception
        String title="";
        if(event.getSource() == addCoinButton){
            title = "Add Coin";
        }else if(event.getSource() == removeCoinButton){
            title = "Remove Coin";
        }
        Stage editCoinStage = new Stage();

        editCoinStage.initModality(Modality.APPLICATION_MODAL);
        editCoinStage.setTitle(title);
        editCoinStage.setMinWidth(250);

        Label label1 = new Label("Coin Year");
        TextField coinYearTextField = new TextField();
        Label label2 = new Label("Coin Face Value");
        TextField coinFaceValueTextField = new TextField();
        Label label3 = new Label("Fill out the fields correctly");
        label3.setVisible(false);
        Button addCoinButton2 = new Button(title);
        addCoinButton2.setOnAction(e -> {
            Boolean newOwned=false;
            if(event.getSource() == addCoinButton){
                newOwned = true;
            }else if(event.getSource() == removeCoinButton){
                newOwned = false;
            }
            if (isValid(coinFaceValueTextField, "faceValue") & isValid(coinYearTextField,"seriesYear")){
                label3.setVisible(false);
                currentCollection.getCountryArray()[getCurrentCountryIndex(currentCountry)]
                .getSerie()[getCurrentYearIndex(coinYearTextField.getText())]
                .getCoinArray()[getCurrentCoinIndex(coinFaceValueTextField.getText())].setOwned(newOwned);
            }else{
                label3.setVisible(true);
            }

            
            printData();
        });

        VBox layout = new VBox();
        layout.getChildren().addAll(label1, coinYearTextField, label2, coinFaceValueTextField, label3, addCoinButton2);
        layout.setAlignment(Pos.CENTER);

        Scene addCoinScene = new Scene(layout);
        editCoinStage.setScene(addCoinScene);
        editCoinStage.showAndWait();
        System.out.println("Coin Added");
    }

    private boolean isValid(TextField textfield, String validate){
        Boolean isvalid=false;
        try {
            if (validate == "faceValue"){
                for(int i = 0; i<Serie.coins.length;i++){
                    if(Serie.coins[i]==Double.parseDouble(textfield.getText())){
                        isvalid = true;
                    }
                }
            }
            if (validate == "seriesYear"){
                for(int i = 0; i<Country.seriesYear.length;i++){
                    if(Country.seriesYear[i]==Integer.parseInt(textfield.getText())){
                        isvalid = true;
                    }
                }
            }
        } catch (Exception e) {

        }


        return isvalid;
    }

    @FXML
    private void changeCountry(ActionEvent event) throws FileNotFoundException{
        /*
        for (int i=0;i<buttonList.size();i++){
            if (event.getSource()==buttonList.get(i)){
                currentCountry = Collection.countries[i];
                break;
            }
        }
        */
        if (event.getSource() == andorraButton) {
            currentCountry = "Andorra";
        } else if (event.getSource() == austriaButton) {
            currentCountry = "Austria";
        } else if (event.getSource() == belgiumButton) {
            currentCountry = "Belgium";
        } else if (event.getSource() == cyprusButton) {
            currentCountry = "Cyprus";
        } else if (event.getSource() == estoniaButton) {
            currentCountry = "Estonia";
        } else if (event.getSource() == finlandButton) {
            currentCountry = "Finland";
        } else if (event.getSource() == franceButton) {
            currentCountry = "France";
        } else if (event.getSource() == germanyButton) {
            currentCountry = "Germany";
        } else if (event.getSource() == greeceButton) {
            currentCountry = "Greece";
        } else if (event.getSource() == irelandButton) {
            currentCountry = "Ireland";
        } else if (event.getSource() == italyButton) {
            currentCountry = "Italy";
        } else if (event.getSource() == latviaButton) {
            currentCountry = "Latvia";
        } else if (event.getSource() == lithuaniaButton) {
            currentCountry = "Lithuania";
        } else if (event.getSource() == luxembourgButton) {
            currentCountry = "Luxembourg";
        } else if (event.getSource() == maltaButton) {
            currentCountry = "Malta";
        } else if (event.getSource() == monacoButton) {
            currentCountry = "Monaco";
        } else if (event.getSource() == netherlandsButton) {
            currentCountry = "Netherlands";
        } else if (event.getSource() == portugalButton) {
            currentCountry = "Portugal";
        } else if (event.getSource() == sanMarinoButton) {
            currentCountry = "San Marino";
        } else if (event.getSource() == slovakiaButton) {
            currentCountry = "Slovakia";
        } else if (event.getSource() == sloveniaButton) {
            currentCountry = "Slovenia";
        } else if (event.getSource() == spainButton) {
            currentCountry = "Spain";
        } else if (event.getSource() == vaticanCityButton) {
            currentCountry = "Vatican City";
        }

        imagePath = new File(App.projectRootPath + "/src/main/resources/Images/" + currentCountry + ".png");
        Image flagImage = new Image(new FileInputStream(imagePath)); // Investigar lo de throwexception
        flagImageView.setImage(flagImage);

        setCountryDataLabels(currentCountry);

        printData();
    }

    @FXML
    private void switchToSecondary() throws IOException {
        SecondaryController.currentCollection=currentCollection;
        App.setRoot("secondary");
    }


    private int getCurrentCountryIndex(String countryName){
        int index=0;
        for(int i=0;i<Collection.countries.length;i++){
            if(Collection.countries[i].equals(countryName)){
                index=i;
                break;
            }
        }
        return index;
    }

    private int getCurrentYearIndex(String serieYear){
        int index=0;
        for(int i=0;i<Country.seriesYear.length;i++){
            if(Country.seriesYear[i].equals(Integer.parseInt(serieYear))){
                index=i;
                break;
            }
        }
        return index;
    }

    private int getCurrentCoinIndex(String faceValue){
        int index=0;
        for(int i=0;i<Serie.coins.length;i++){
            if(Serie.coins[i].equals(Double.parseDouble(faceValue))){
                index=i;
                break;
            }
        }
        return index;
    }

    //Updates the String currentCollection to let the pogramm know with which Collection is working
    private void updateCurrentCollection(String collectionName){
        System.out.println("Starting updating");
        for(int i=0;i<collectionArrayList.size();i++){
            if(collectionArrayList.get(i).getCollectionName().equals(collectionName)){
                currentCollectionIndex=i;
                break;
            }
        }
        currentCollection = collectionArrayList.get(currentCollectionIndex);
    }

    //Updates the information in the countryDataLabels
    public void setCountryDataLabels(String currentCountry) {

        for (int i = 0; i < countriesData.length; i++) {
            if (countriesData[i][0].equals(currentCountry)) {
                countryData1Label.setText("Adopted Euro in : " + countriesData[i][1]);
                countryData2Label.setText("Pre-Euro Currency : " + countriesData[i][2]);
                countryData3Label.setText("Population (2019) : " + countriesData[i][3]);
                countryData4Label.setText("ISO Code : " + countriesData[i][4]);
                break;
            }
        }
    }

}
