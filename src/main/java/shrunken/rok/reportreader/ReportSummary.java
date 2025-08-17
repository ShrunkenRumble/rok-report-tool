package shrunken.rok.reportreader;

import java.time.Instant;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ReportSummary {
    private SimpleStringProperty myCmdrPair, oppCmdrPair, formattedDate;
    private SimpleIntegerProperty myDeadAndSevs, myKP, oppDeadAndSevs, oppKP;
    private SimpleDoubleProperty ratio, KPRatio;
    private Instant date;

    public ReportSummary() {
        this.myCmdrPair = new SimpleStringProperty(" ");
        this.oppCmdrPair = new SimpleStringProperty(" ");
        this.formattedDate = new SimpleStringProperty(" ");
        this.myDeadAndSevs = new SimpleIntegerProperty(0);
        this.myKP = new SimpleIntegerProperty(0);
        this.oppDeadAndSevs = new SimpleIntegerProperty(0);
        this.oppKP = new SimpleIntegerProperty(0);
        this.ratio = new SimpleDoubleProperty(0);
        this.KPRatio = new SimpleDoubleProperty(0);
    }

    public ReportSummary(String myCmdrPair, int myDeadAndSevs, int myKP, String oppCmdrPair, int oppDeadAndSevs, int oppKP, double ratio, double KPRatio, Instant date) {
        this.myCmdrPair = new SimpleStringProperty(myCmdrPair);
        this.myDeadAndSevs = new SimpleIntegerProperty(myDeadAndSevs);
        this.myKP = new SimpleIntegerProperty(myKP);
        this.oppCmdrPair = new SimpleStringProperty(oppCmdrPair);
        this.oppDeadAndSevs = new SimpleIntegerProperty(oppDeadAndSevs);
        this.oppKP = new SimpleIntegerProperty(oppKP);
        this.ratio = new SimpleDoubleProperty(ratio);
        this.KPRatio = new SimpleDoubleProperty(KPRatio);
        this.formattedDate = new SimpleStringProperty(date.toString());
        this.date = date;
    }

    // Getters
    public String getFormattedDate() {
        return this.formattedDate.get();
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
    public Instant getDate() {
        return this.date;
    }

    // Setters
    public void setDate(Instant date) {
        this.date = date;
        this.formattedDate.set(date.toString());
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

