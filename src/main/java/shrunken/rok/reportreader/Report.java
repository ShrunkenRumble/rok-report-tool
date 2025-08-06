package shrunken.rok.reportreader;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Report {
    private SimpleStringProperty myCmdrPair, myPrimCmdr, mySecCmdr, oppCmdrPair, oppPrimCmdr, oppSecCmdr, id, date;
    private SimpleIntegerProperty myUnits, myHeals, myDead, mySev, mySlight, myRemaining, myKP, myPowerLoss;
    private SimpleIntegerProperty oppUnits, oppHeals, oppDead, oppSev, oppSlight, oppRemaining, oppKP, oppPowerLoss;
    private int dateSort;

    public Report() {
        this.myCmdrPair = new SimpleStringProperty(" ");
        this.myPrimCmdr = new SimpleStringProperty(" ");
        this.mySecCmdr = new SimpleStringProperty(" ");
        this.oppCmdrPair = new SimpleStringProperty(" ");
        this.oppPrimCmdr = new SimpleStringProperty(" ");
        this.oppSecCmdr = new SimpleStringProperty(" ");
        this.id = new SimpleStringProperty(" ");
        this.date = new SimpleStringProperty(" ");
    }

    public Report(String date, String myPrimCmdr, String mySecCmdr, String oppPrimCmdr, String oppSecCmdr, int myUnits,
                    int myHeals, int myDead, int mySev, int mySlight, int myRemaining, int myKP, int myPowerLoss, int oppUnits, int oppHeals, int oppDead, int oppSev, int oppSlight, int oppRemaining, int oppKP, int oppPowerLoss, String id, int dateSort) {
        this.date = new SimpleStringProperty(date);
        this.myPrimCmdr = new SimpleStringProperty(myPrimCmdr);
        this.mySecCmdr = new SimpleStringProperty(mySecCmdr);
        this.oppPrimCmdr = new SimpleStringProperty(oppPrimCmdr);
        this.oppSecCmdr = new SimpleStringProperty(oppSecCmdr);
        this.myUnits = new SimpleIntegerProperty(myUnits);
        this.myHeals = new SimpleIntegerProperty(myHeals);
        this.myDead = new SimpleIntegerProperty(myDead);
        this.mySev = new SimpleIntegerProperty(mySev);
        this.mySlight = new SimpleIntegerProperty(mySlight);
        this.myRemaining = new SimpleIntegerProperty(myRemaining);
        this.myKP = new SimpleIntegerProperty(myKP);
        this.myPowerLoss = new SimpleIntegerProperty(myPowerLoss);
        this.oppUnits = new SimpleIntegerProperty(oppUnits);
        this.oppHeals = new SimpleIntegerProperty(oppHeals);
        this.oppDead = new SimpleIntegerProperty(oppDead);
        this.oppSev = new SimpleIntegerProperty(oppSev);
        this.oppSlight = new SimpleIntegerProperty(oppSlight);
        this.oppRemaining = new SimpleIntegerProperty(oppRemaining);
        this.oppKP = new SimpleIntegerProperty(oppKP);
        this.oppPowerLoss = new SimpleIntegerProperty(oppPowerLoss);
        this.id = new SimpleStringProperty(id);
        this.myCmdrPair = new SimpleStringProperty(myPrimCmdr + " / " + mySecCmdr);
        this.oppCmdrPair = new SimpleStringProperty(oppPrimCmdr + " / " + oppSecCmdr);
        this.dateSort = dateSort;
    }

    // Getters
    public String getDate() {
        return this.date.get();
    }
    public String getMyCmdrPair() {
        return this.myCmdrPair.get();
    }
    public String getMyPrimCmdr() {
        return this.myPrimCmdr.get();
    }
    public String getMySecCmdr() {
        return this.mySecCmdr.get();
    }
    public int getMyUnits() {
        return this.myUnits.get();
    }
    public int getMyHeals() {
        return this.myHeals.get();
    }
    public int getMyDead() {
        return this.myDead.get();
    }
    public int getMySev() {
        return this.mySev.get();
    }
    public int getMySlight() {
        return this.mySlight.get();
    }
    public int getMyRemaining() {
        return this.myRemaining.get();
    }
    public int getMyKP() {
        return this.myKP.get();
    }
    public int getMyPowerLoss() {
        return this.myPowerLoss.get();
    }
    public String getOppCmdrPair() {
        return this.oppCmdrPair.get();
    }
    public String getOppPrimCmdr() {
        return this.oppPrimCmdr.get();
    }
    public String getOppSecCmdr() {
        return this.oppSecCmdr.get();
    }
    public int getOppUnits() {
        return this.oppUnits.get();
    }
    public int getOppHeals() {
        return this.oppHeals.get();
    }
    public int getOppDead() {
        return this.oppDead.get();
    }
    public int getOppSev() {
        return this.oppSev.get();
    }
    public int getOppSlight() {
        return this.oppSlight.get();
    }
    public int getOppRemaining() {
        return this.oppRemaining.get();
    }
    public int getOppKP() {
        return this.oppKP.get();
    }
    public int getOppPowerLoss() {
        return this.oppPowerLoss.get();
    }
    public String getId() {
        return this.id.get();
    }
    public int getDateSort() {
        return this.dateSort;
    }

    // Setters
    public void setDate(String date) {
        this.date = new SimpleStringProperty(date);
    }
    public void setMyPrimCmdr(String myPrimCmdr) {
        this.myPrimCmdr = new SimpleStringProperty(myPrimCmdr);
    }
    public void setMySecCmdr(String mySecCmdr) {
        this.mySecCmdr = new SimpleStringProperty(mySecCmdr);
    }
    public void setMyUnits(int myUnits) {
        this.myUnits = new SimpleIntegerProperty(myUnits);
    }
    public void setMyHeals(int myHeals) {
        this.myHeals = new SimpleIntegerProperty(myHeals);
    }
    public void setMyDead(int myDead) {
        this.myDead = new SimpleIntegerProperty(myDead);
    }
    public void setMySev(int mySev) {
        this.mySev = new SimpleIntegerProperty(mySev);
    }
    public void setMySlight(int mySlight) {
        this.mySlight = new SimpleIntegerProperty(mySlight);
    }
    public void setMyRemaining(int myRemaining) {
        this.myRemaining = new SimpleIntegerProperty(myRemaining);
    }
    public void setMyKP(int myKP) {
        this.myKP = new SimpleIntegerProperty(myKP);
    }
    public void setMyPowerLoss(int myPowerLoss) {
        this.myPowerLoss = new SimpleIntegerProperty(myPowerLoss);
    }
    public void setOppPrimCmdr(String oppPrimCmdr) {
        this.oppPrimCmdr = new SimpleStringProperty(oppPrimCmdr);
    }
    public void setOppSecCmdr(String oppSecCmdr) {
        this.oppSecCmdr = new SimpleStringProperty(oppSecCmdr);
    }
    public void setOppUnits(int oppUnits) {
        this.oppUnits = new SimpleIntegerProperty(oppUnits);
    }
    public void setOppHeals(int oppHeals) {
        this.oppHeals = new SimpleIntegerProperty(oppHeals);
    }
    public void setOppDead(int oppDead) {
        this.oppDead = new SimpleIntegerProperty(oppDead);
    }
    public void setOppSev(int oppSev) {
        this.oppSev = new SimpleIntegerProperty(oppSev);
    }
    public void setOppSlight(int oppSlight) {
        this.oppSlight = new SimpleIntegerProperty(oppSlight);
    }
    public void setOppRemaining(int oppRemaining) {
        this.oppRemaining = new SimpleIntegerProperty(oppRemaining);
    }
    public void setOppKP(int oppKP) {
        this.oppKP = new SimpleIntegerProperty(oppKP);
    }
    public void setOppPowerLoss(int oppPowerLoss) {
        this.oppPowerLoss = new SimpleIntegerProperty(oppPowerLoss);
    }
    public void setId(String id) {
        this.id = new SimpleStringProperty(id);
    }
}
