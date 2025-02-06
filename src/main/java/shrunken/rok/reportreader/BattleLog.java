package shrunken.rok.reportreader;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

public class BattleLog {
    private Table battleLog;
    private ObservableList<Battle> battles;

    public BattleLog() {
        battles = FXCollections.observableArrayList();
        battleLog = Table.create("Reports",
                StringColumn.create("MyPrimCmdr"), StringColumn.create("MySecCmdr"), IntColumn.create("MyUnits"),
                IntColumn.create("MyHeals"), IntColumn.create("MyDead"), IntColumn.create("MySev"),
                IntColumn.create("MySlight"), IntColumn.create("MyRemaining"),
                IntColumn.create("MyKP"), IntColumn.create("MyPowerLoss"),
                StringColumn.create("OppPrimCmdr"), StringColumn.create("OppSecCmdr"),
                IntColumn.create("OppUnits"), IntColumn.create("OppHeals"),
                IntColumn.create("OppDead"), IntColumn.create("OppSev"),
                IntColumn.create("OppSlight"), IntColumn.create("OppRemaining"),
                IntColumn.create("OppKP"), IntColumn.create("OppPowerLoss"), StringColumn.create("ID"));
    }

    public void addReport(Report report) {
        ArrayList<Battle> battles = report.getBattles();
        for (int i = 0; i < battles.size(); i++) {
            this.battles.add(battles.get(i));

            battleLog.stringColumn("MyPrimCmdr").append(battles.get(i).getMyPrimCmdr());
            battleLog.stringColumn("MySecCmdr").append(battles.get(i).getMySecCmdr());
            battleLog.intColumn("MyUnits").append(battles.get(i).getMyUnits());
            battleLog.intColumn("MyHeals").append(battles.get(i).getMyHeals());
            battleLog.intColumn("MyDead").append(battles.get(i).getMyDead());
            battleLog.intColumn("MySev").append(battles.get(i).getMySev());
            battleLog.intColumn("MySlight").append(battles.get(i).getMySlight());
            battleLog.intColumn("MyRemaining").append(battles.get(i).getMyRemaining());
            battleLog.intColumn("MyKP").append(battles.get(i).getMyKP());
            battleLog.intColumn("MyPowerLoss").append(battles.get(i).getMyPowerLoss());
            battleLog.stringColumn("OppPrimCmdr").append(battles.get(i).getOppPrimCmdr());
            battleLog.stringColumn("OppSecCmdr").append(battles.get(i).getOppSecCmdr());
            battleLog.intColumn("OppUnits").append(battles.get(i).getOppUnits());
            battleLog.intColumn("OppHeals").append(battles.get(i).getOppHeals());
            battleLog.intColumn("OppDead").append(battles.get(i).getOppDead());
            battleLog.intColumn("OppSev").append(battles.get(i).getOppSev());
            battleLog.intColumn("OppSlight").append(battles.get(i).getOppSlight());
            battleLog.intColumn("OppRemaining").append(battles.get(i).getOppRemaining());
            battleLog.intColumn("OppKP").append(battles.get(i).getOppKP());
            battleLog.intColumn("OppPowerLoss").append(battles.get(i).getOppPowerLoss());
            battleLog.stringColumn("ID").append(battles.get(i).getId());
        }
    }

    public Set<String> getFilesRead() {
        Set<String> filesRead = new HashSet<String>();
        for (int i = 0; i < battles.size(); i++) {
            filesRead.add(battles.get(i).getId().split("-")[0]);
        }
        return filesRead;
    }

    public Table getLog() {
        return this.battleLog;
    }

    public ObservableList<Battle> getBattles() {
        return this.battles;
    }
}
