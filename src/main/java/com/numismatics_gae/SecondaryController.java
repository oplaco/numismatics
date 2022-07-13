package com.numismatics_gae;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SecondaryController {

    static Collection currentCollection;
    static int [] numberOfCoins = new int [Collection.countries.length]; // Number of coins in a particular country
    static int totalNumberOfCoins = 0;
    static int maxNumberOfCoinsCountryIndex = 0;
    static Double [] countryTotalValue = new Double [Collection.countries.length]; // Sum of the face value of all the coins of a particular country
    static Double totalCollectionValue = 0.0;
    static int maxCountryTotalValueIndex = 0;
    @FXML
    Button secondaryButton,loadStatisticsButton;
    @FXML
    Label totalNumberOfCoinsLabel,maxNumberOfCoinsCountryLabel,maxCountryTotalValueLabel,totalCollectionValueLabel,warningLabel;
    @FXML
    PieChart countriesPieChart;

    @FXML
    private void switchToPrimary() throws IOException {
        
        App.setRoot("primary");

    }

    @FXML
    private void loadStatistics(){
        loadPieChartData();
        totalNumberOfCoinsLabel.setText("Total number of coins: "+totalNumberOfCoins);
        maxNumberOfCoinsCountryLabel.setText("The country with the most coins is "+
            Collection.countries[maxNumberOfCoinsCountryIndex]+" with "+numberOfCoins[maxNumberOfCoinsCountryIndex]+" coins.");
        
         maxCountryTotalValueLabel.setText("The country with the most face value is "+ 
            Collection.countries[maxCountryTotalValueIndex]+" with "+String.format("%.2f",countryTotalValue[maxCountryTotalValueIndex])+" €");
        
        totalCollectionValueLabel.setText("The total collection face value is: "+String.format("%.2f",totalCollectionValue)+" €");
        
    }

    // Load Pie Chart Data
    public void loadPieChartData(){
        numberOfCoins();
        ObservableList<PieChart.Data> pieChartDataOL = FXCollections.observableArrayList();
        for (int i=0;i<Collection.countries.length;i++){
            pieChartDataOL.add(new PieChart.Data(Collection.countries[i],numberOfCoins[i]));
        }
        countriesPieChart.setData(pieChartDataOL);
    }

    private void numberOfCoins(){
        totalNumberOfCoins = 0;
        maxNumberOfCoinsCountryIndex = 0;
        totalCollectionValue = 0.0;
        maxCountryTotalValueIndex = 0;
        for(int i=0;i<Collection.countries.length;i++){
            countryTotalValue[i]=0.0; //Initialize the array so its values are not null
            numberOfCoins[i]=0;
            for(int j=0;j<Country.seriesYear.length;j++){
                for(int k=0;k<Serie.coins.length;k++){
                    if(currentCollection.getCountryArray()[i].getSerie()[j].getCoinArray()[k].getOwned()){
                        numberOfCoins[i]=numberOfCoins[i]+1;
                        countryTotalValue[i]=countryTotalValue[i]+currentCollection.getCountryArray()[i].getSerie()[j].getCoinArray()[k].getFaceValue();
                    }
                }
            }
            if (countryTotalValue[i]>countryTotalValue[maxCountryTotalValueIndex]){maxCountryTotalValueIndex=i;}
            totalNumberOfCoins = totalNumberOfCoins + numberOfCoins[i];
            if (numberOfCoins[i]>numberOfCoins[maxNumberOfCoinsCountryIndex]){maxNumberOfCoinsCountryIndex=i;}
            totalCollectionValue=totalCollectionValue+countryTotalValue[i];
        }
    }

    
}