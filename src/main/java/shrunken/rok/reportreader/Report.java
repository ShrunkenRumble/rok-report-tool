package shrunken.rok.reportreader;

public class Report {
    private String myPrimCmdr, mySecCmdr, oppPrimCmdr, oppSecCmdr;
    private int myUnits, myHeals, myDead, mySev, mySlight, myRemaining, myKP, myPowerLoss;
    private int oppUnits, oppHeals, oppDead, oppSev, oppSlight, oppRemaining, oppKP, oppPowerLoss;
    private String id;

    public Report() {
        this.myPrimCmdr = "";
        this.mySecCmdr = "";
        this.oppPrimCmdr = "";
        this.oppSecCmdr = "";
        this.id = "";
    }

    public Report(String myPrimCmdr, String mySecCmdr, String oppPrimCmdr, String oppSecCmdr, int myUnits,
                    int myHeals, int myDead, int mySev, int mySlight, int myRemaining, int myKP, int myPowerLoss, int oppUnits, int oppHeals, int oppDead, int oppSev, int oppSlight, int oppRemaining, int oppKP, int oppPowerLoss, String id){
        this.myPrimCmdr = myPrimCmdr;
        this.mySecCmdr = mySecCmdr;
        this.oppPrimCmdr = oppPrimCmdr;
        this.oppSecCmdr = oppSecCmdr;
        this.myUnits = myUnits;
        this.myHeals = myHeals;
        this.myDead = myDead;
        this.mySev = mySev;
        this.mySlight = mySlight;
        this.myRemaining = myRemaining;
        this.myKP = myKP;
        this.myPowerLoss = myPowerLoss;
        this.oppUnits = oppUnits;
        this.oppHeals = oppHeals;
        this.oppDead = oppDead;
        this.oppSev = oppSev;
        this.oppSlight = oppSlight;
        this.oppRemaining = oppRemaining;
        this.oppKP = oppKP;
        this.oppPowerLoss = oppPowerLoss;
        this.id = id;
    }

    public String getmyPrimCmdr() {
        return this.myPrimCmdr;
    }
    public String getmySecCmdr() {
        return this.mySecCmdr;
    }
    public String getoppPrimCmdr() {
        return this.oppPrimCmdr;
    }
    public String getoppSecCmdr() {
        return this.oppSecCmdr;
    }
    public int getmyPowerLoss() {
        return this.myPowerLoss;
    }
    public int getmyUnits() {
        return this.myUnits;
    }
    public int getmyHeals() {
        return this.myHeals;
    }
    public int getmyDead() {
        return this.myDead;
    }
    public int getmySev() {
        return this.mySev;
    }
    public int getmySlight() {
        return this.mySlight;
    }
    public int getmyRemaining() {
        return this.myRemaining;
    }
    public int getmyKP() {
        return this.myKP;
    }
    public int getoppPowerLoss() {
        return this.oppPowerLoss;
    }
    public int getoppUnits() {
        return this.oppUnits;
    }
    public int getoppHeals() {
        return this.oppHeals;
    }
    public int getoppDead() {
        return this.oppDead;
    }
    public int getoppSev() {
        return this.oppSev;
    }
    public int getoppSlight() {
        return this.oppSlight;
    }
    public int getoppRemaining() {
        return this.oppRemaining;
    }
    public int getoppKP() {
        return this.oppKP;
    }
    public String getid() {
        return this.id;
    }

    public void setmyPrimCmdr(String myPrimCmdr) {
        this.myPrimCmdr = myPrimCmdr;
    }
    public void setmySecCmdr(String mySecCmdr) {
        this.mySecCmdr = mySecCmdr;
    }
    public void setoppPrimCmdr(String oppPrimCmdr) {
        this.oppPrimCmdr = oppPrimCmdr;
    }
    public void setoppSecCmdr(String oppSecCmdr) {
        this.oppSecCmdr = oppSecCmdr;
    }
    public void setmyPowerLoss(int myPowerLoss) {
        this.myPowerLoss = myPowerLoss;
    }
    public void setmyUnits(int myUnits) {
        this.myUnits = myUnits;
    }
    public void setmyHeals(int myHeals) {
        this.myHeals = myHeals;
    }
    public void setmyDead(int myDead) {
        this.myDead = myDead;
    }
    public void setmySev(int mySev) {
        this.mySev = mySev;
    }
    public void setmySlight(int mySlight) {
        this.mySlight = mySlight;
    }
    public void setmyRemaining(int myRemaining) {
        this.myRemaining = myRemaining;
    }
    public void setmyKP(int myKP) {
        this.myKP = myKP;
    }
    public void setoppPowerLoss(int oppPowerLoss) {
        this.oppPowerLoss = oppPowerLoss;
    }
    public void setoppUnits(int oppUnits) {
        this.oppUnits = oppUnits;
    }
    public void setoppHeals(int oppHeals) {
        this.oppHeals = oppHeals;
    }
    public void setoppDead(int oppDead) {
        this.oppDead = oppDead;
    }
    public void setoppSev(int oppSev) {
        this.oppSev = oppSev;
    }
    public void setoppSlight(int oppSlight) {
        this.oppSlight = oppSlight;
    }
    public void setoppRemaining(int oppRemaining) {
        this.oppRemaining = oppRemaining;
    }
    public void setoppKP(int oppKP) {
        this.oppKP = oppKP;
    }
    public void setid(String id) {
        this.id = id;
    }
}
