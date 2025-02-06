package shrunken.rok.reportreader;

import java.util.ArrayList;

public class Report {
    private ArrayList<Battle> battles;

    public Report() {
        battles = new ArrayList<Battle>();
    }

    public Report(ArrayList<Battle> battles) {
        this.battles = battles;
    }

    public void addBattle(Battle battle) {
        battles.add(battle);
    }

    public ArrayList<Battle> getBattles() {
        return battles;
    }

    public ReportSummary getSummary() {
        int myDeadAndSevs = 0;
        int myKP = 0;
        int oppDeadAndSevs = 0;
        int oppKP = 0;

        for (int i = 0; i < battles.size(); i++) {
            myDeadAndSevs += battles.get(i).getMyDead() + battles.get(i).getMySev();
            myKP += battles.get(i).getMyKP();
            oppDeadAndSevs += battles.get(i).getOppDead() + battles.get(i).getOppSev();
            oppKP += battles.get(i).getOppKP();
        }

        return new ReportSummary(battles.get(0).getDate(), battles.get(0).getMyCmdrPair(), myDeadAndSevs, myKP,
                battles.get(0).getOppCmdrPair(), oppDeadAndSevs, oppKP, oppDeadAndSevs / myDeadAndSevs, myKP / oppKP);
    }
}
