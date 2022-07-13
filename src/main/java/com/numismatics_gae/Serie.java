package com.numismatics_gae;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Serie extends Country{
    private Integer serieYear;
    private Coin[] coinArray = new Coin[8];

    public Coin[] getCoinArray() {
        return coinArray;
    }

    public void setCoinArray(Coin[] coinArray) {
        this.coinArray = coinArray;
    }

    static Double[] coins ={0.01,0.02,0.05,0.1,0.2,0.5,1.0,2.0};
    /*
    public Boolean[] serie = new Boolean[8];
    
    public Serie(){
        
    }
    public Serie(Boolean oneCent,Boolean twoCent, Boolean fiveCent, Boolean tenCent, Boolean twentyCent, Boolean fiftyCent, Boolean oneEuro, Boolean twoEuro){
        this.serie[0]=oneCent;
        this.serie[1]=twoCent;
        this.serie[2]=fiveCent;
        this.serie[3]=tenCent;
        this.serie[4]=twentyCent;
        this.serie[5]=fiftyCent;
        this.serie[6]=oneEuro;
        this.serie[7]=twoEuro;
    }
    public void createSerie(){

    }
    */


    // You cand add a listener to a property not to an atrribute. You can bind two properties.

    public Serie(){}

    public Serie(Integer serieYear){
        this.serieYear=serieYear;
        for (int i=0;i<coins.length;i++) {
            coinArray[i]=new Coin(coins[i],this.serieYear);
        }
    }

    public IntegerProperty serieYearProperty(){
        IntegerProperty serieYearIntegerProperty = new SimpleIntegerProperty(this.serieYear);
        return serieYearIntegerProperty;
    }

    public Property<Boolean> oneCentProperty(){
        BooleanProperty oneCentBooleanProperty = new SimpleBooleanProperty(this.coinArray[0].getOwned());
        return oneCentBooleanProperty;
    }

    public Property<Boolean> twoCentProperty(){
        BooleanProperty twoCentBooleanProperty = new SimpleBooleanProperty(this.coinArray[1].getOwned());
        return twoCentBooleanProperty;
    }

    public Property<Boolean> fiveCentProperty(){
        BooleanProperty fiveCentBooleanProperty = new SimpleBooleanProperty(this.coinArray[2].getOwned());
        return fiveCentBooleanProperty;
    }

    public Property<Boolean> tenCentProperty(){
        BooleanProperty tenCentBooleanProperty = new SimpleBooleanProperty(this.coinArray[3].getOwned());
        return tenCentBooleanProperty;
    }

    public Property<Boolean> twentyCentProperty(){
        BooleanProperty twentyCentBooleanProperty = new SimpleBooleanProperty(this.coinArray[4].getOwned());
        return twentyCentBooleanProperty;
    }

    public Property<Boolean> fiftyCentProperty(){
        BooleanProperty fiftyCentBooleanProperty = new SimpleBooleanProperty(this.coinArray[5].getOwned());
        return fiftyCentBooleanProperty;
    }

    public Property<Boolean> oneEuroProperty(){
        BooleanProperty oneEuroBooleanProperty = new SimpleBooleanProperty(this.coinArray[6].getOwned());
        return oneEuroBooleanProperty;
    }

    public Property<Boolean> twoEuroProperty(){
        BooleanProperty twoEuroBooleanProperty = new SimpleBooleanProperty(this.coinArray[7].getOwned());
        return twoEuroBooleanProperty;
    }
    
    public Integer getSerieYear() {
        return serieYear;
    }

    public void setSerieYear(Integer serieYear) {
        this.serieYear = serieYear;
    }

}
