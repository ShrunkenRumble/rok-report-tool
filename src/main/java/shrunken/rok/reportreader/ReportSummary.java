package shrunken.rok.reportreader;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ReportSummary {
    private SimpleStringProperty myCmdrPair, oppCmdrPair, date;
    private SimpleIntegerProperty myDeadAndSevs, myKP, oppDeadAndSevs, oppKP;
    private SimpleDoubleProperty ratio, KPRatio;

    public ReportSummary() {
        this.myCmdrPair = new SimpleStringProperty(" ");
        this.oppCmdrPair = new SimpleStringProperty(" ");
        this.date = new SimpleStringProperty(" ");
        this.myDeadAndSevs = new SimpleIntegerProperty(0);
        this.myKP = new SimpleIntegerProperty(0);
        this.oppDeadAndSevs = new SimpleIntegerProperty(0);
        this.oppKP = new SimpleIntegerProperty(0);
        this.ratio = new SimpleDoubleProperty(0);
        this.KPRatio = new SimpleDoubleProperty(0);
    }

    public ReportSummary(String date, String myCmdrPair, int myDeadAndSevs, int myKP, String oppCmdrPair, int oppDeadAndSevs, int oppKP, double ratio, double KPRatio) {
        this.date = new SimpleStringProperty(date);
        this.myCmdrPair = new SimpleStringProperty(myCmdrPair);
        this.myDeadAndSevs = new SimpleIntegerProperty(myDeadAndSevs);
        this.myKP = new SimpleIntegerProperty(myKP);
        this.oppCmdrPair = new SimpleStringProperty(oppCmdrPair);
        this.oppDeadAndSevs = new SimpleIntegerProperty(oppDeadAndSevs);
        this.oppKP = new SimpleIntegerProperty(oppKP);
        this.ratio = new SimpleDoubleProperty(ratio);
        this.KPRatio = new SimpleDoubleProperty(KPRatio);
    }

    // Getters
    public String getDate() {
        return this.date.get();
    }
    public String getMyCmdrPair() {
        return this.myCmdrPair.get();
    }
    public int getMyDeadAndSevs() {
        return this.myDeadAndSevs.get();
    }
    public int getMyKP() {
        return this.myKP.get();
    }
    public String getOppCmdrPair() {
        return this.oppCmdrPair.get();
    }
    public int getOppDeadAndSevs() {
        return this.oppDeadAndSevs.get();
    }
    public int getOppKP() {
        return this.oppKP.get();
    }
    public double getRatio() {
        return this.ratio.get();
    }
    public double getKPRatio() {
        return this.KPRatio.get();
    }

    // Setters
    public void setDate(String date) {
        this.date.set(date);
    }
    public void setMyCmdrPair(String myCmdrPair) {
        this.myCmdrPair.set(myCmdrPair);
    }
    public void setMyDeadAndSevs(int myDeadAndSevs) {
        this.myDeadAndSevs.set(myDeadAndSevs);
    }
    public void setMyKP(int myKP) {
        this.myKP.set(myKP);
    }
    public void setOppCmdrPair(String oppCmdrPair) {
        this.oppCmdrPair.set(oppCmdrPair);
    }
    public void setOppDeadAndSevs(int oppDeadAndSevs) {
        this.oppDeadAndSevs.set(oppDeadAndSevs);
    }
    public void setOppKP(int oppKP) {
        this.oppKP.set(oppKP);
    }
    public void setRatio(double ratio) {
        this.ratio.set(ratio);
    }
    public void setKPRatio(double KPRatio) {
        this.KPRatio.set(KPRatio);
    }
}

