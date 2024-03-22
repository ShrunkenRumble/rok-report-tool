package shrunken.rok.reportreader;

import shrunken.util.Pair;

public class Report {
    private Pair<Integer, Integer> initUnits;
    private Pair<Integer, Integer> healing;
    private Pair<Integer, Integer> dead;
    private Pair<Integer, Integer> sevWound;
    private Pair<Integer, Integer> slightWound;
    private Pair<Integer, Integer> remaining;
    private Pair<Integer, Integer> power;
    private Pair<Integer, Integer> kp;

    private Pair<String, String> selfCmdrs;
    private Pair<String, String> oppCmdrs;

    public Report() {
        this.initUnits = new Pair<Integer, Integer> (0, 0);
        this.healing = new Pair<Integer, Integer>(0, 0);
        this.dead = new Pair<Integer, Integer>(0, 0);
        this.sevWound = new Pair<Integer, Integer>(0, 0);
        this.slightWound = new Pair<Integer, Integer>(0, 0);
        this.remaining = new Pair<Integer, Integer>(0, 0);
        this.power = new Pair<Integer, Integer>(0, 0);
        this.kp = new Pair<Integer, Integer>(0, 0);

        this.selfCmdrs = new Pair<String,String>(null, null);
        this.oppCmdrs = new Pair<String, String>(null, null);
    }

    public Report(
            String selfCmdr1, String selfCmdr2,
            int selfInitUnits, int selfHealing,
            int selfDead, int selfSevWound,
            int selfSlightWound, int selfRemaining,
            int selfPower, int selfKP,
            String oppCmdr1, String oppCmdr2,
            int oppInitUnits, int oppHealing,
            int oppDead, int oppSevWound,
            int oppSlightWound, int oppRemaining,
            int oppPower, int oppKP) {
        this.selfCmdrs = new Pair<String, String>(selfCmdr1, selfCmdr2);
        this.oppCmdrs = new Pair<String, String>(oppCmdr1, oppCmdr2);
        this.initUnits = new Pair<Integer, Integer>(selfInitUnits, oppInitUnits);
        this.healing = new Pair<Integer, Integer>(selfHealing, oppHealing);
        this.dead = new Pair<Integer, Integer>(selfDead, oppDead);
        this.sevWound = new Pair<Integer, Integer>(selfSevWound, oppSevWound);
        this.slightWound = new Pair<Integer, Integer>(selfSlightWound, oppSlightWound);
        this.remaining = new Pair<Integer, Integer>(selfRemaining, oppRemaining);
        this.power = new Pair<Integer, Integer>(selfPower, oppPower);;
        this.kp = new Pair<Integer, Integer>(selfKP, oppKP);;
    }

    public void setSelfCmdrs(String cmdr1, String cmdr2) {
        this.selfCmdrs.setFirst(cmdr1);
        this.selfCmdrs.setSecond(cmdr2);
    }

    public void setOppCmdrs(String cmdr1, String cmdr2) {
        this.oppCmdrs.setFirst(cmdr1);
        this.oppCmdrs.setSecond(cmdr2);
    }

    public void setInitUnits(int selfInitUnits, int oppInitUnits) {
        this.initUnits.setFirst(selfInitUnits);
        this.initUnits.setSecond(oppInitUnits);
    }

    public void setHealing(int selfHealing, int oppHealing) {
        this.healing.setFirst(selfHealing);
        this.healing.setSecond(oppHealing);
    }

    public void setDead(int selfDead, int oppDead) {
        this.dead.setFirst(selfDead);
        this.dead.setSecond(oppDead);
    }

    public void setSevWound(int selfSevWound, int oppSevWound) {
        this.sevWound.setFirst(selfSevWound);
        this.sevWound.setSecond(oppSevWound);
    }

    public void setSlightWound(int selfSlightWound, int oppSlightWound) {
        this.slightWound.setFirst(selfSlightWound);
        this.slightWound.setSecond(oppSlightWound);
    }

    public void setRemaining(int selfRemaining, int oppRemaining) {
        this.remaining.setFirst(selfRemaining);
        this.remaining.setSecond(oppRemaining);
    }

    public void setPower(int selfPower, int oppPower) {
        this.power.setFirst(selfPower);
        this.power.setSecond(oppPower);
    }

    public void setKP(int selfKP, int oppKP) {
        this.kp.setFirst(selfKP);
        this.kp.setSecond(oppKP);
    }

}
