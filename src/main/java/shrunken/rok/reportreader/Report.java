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
    private Pair<Integer, Integer> killPts;

    private Pair<String, String> selfCmdrs;
    private Pair<String, String> oppCmdrs;

    public Report() {}

    public Report(
            Pair<String, String> selfCmdrs, Pair<String, String> oppCmdrs,
            Pair<Integer, Integer> initUnits, Pair<Integer, Integer> healing, Pair<Integer, Integer> dead,
            Pair<Integer, Integer> sevWound, Pair<Integer, Integer> slightWound, Pair<Integer, Integer> remaining,
            Pair<Integer, Integer> power, Pair<Integer, Integer> killPts) {
        this.selfCmdrs = selfCmdrs;
        this.oppCmdrs = oppCmdrs;
        this.initUnits = initUnits;
        this.healing = healing;
        this.dead = dead;
        this.sevWound = sevWound;
        this.slightWound = slightWound;
        this.remaining = remaining;
        this.power = power;
        this.killPts = killPts;
    }

}
