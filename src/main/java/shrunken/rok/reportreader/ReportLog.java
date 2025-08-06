package shrunken.rok.reportreader;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

public class ReportLog {
    private Table reportLog;
    private ObservableList<Report> reports;
    private ArrayList<Report> summarizedReports;

    public ReportLog() {
        reports = FXCollections.observableArrayList();
        summarizedReports = new ArrayList<Report>();
        reportLog = Table.create("Reports",
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

    public void addReport(ArrayList<Report> reports) {
        this.summarizedReports.add(reports.get(reports.size() - 1));
        for (int i = 0; i < reports.size() - 1; i++) {
            this.reports.add(reports.get(i));

            reportLog.stringColumn("MyPrimCmdr").append(reports.get(i).getMyPrimCmdr());
            reportLog.stringColumn("MySecCmdr").append(reports.get(i).getMySecCmdr());
            reportLog.intColumn("MyUnits").append(reports.get(i).getMyUnits());
            reportLog.intColumn("MyHeals").append(reports.get(i).getMyHeals());
            reportLog.intColumn("MyDead").append(reports.get(i).getMyDead());
            reportLog.intColumn("MySev").append(reports.get(i).getMySev());
            reportLog.intColumn("MySlight").append(reports.get(i).getMySlight());
            reportLog.intColumn("MyRemaining").append(reports.get(i).getMyRemaining());
            reportLog.intColumn("MyKP").append(reports.get(i).getMyKP());
            reportLog.intColumn("MyPowerLoss").append(reports.get(i).getMyPowerLoss());
            reportLog.stringColumn("OppPrimCmdr").append(reports.get(i).getOppPrimCmdr());
            reportLog.stringColumn("OppSecCmdr").append(reports.get(i).getOppSecCmdr());
            reportLog.intColumn("OppUnits").append(reports.get(i).getOppUnits());
            reportLog.intColumn("OppHeals").append(reports.get(i).getOppHeals());
            reportLog.intColumn("OppDead").append(reports.get(i).getOppDead());
            reportLog.intColumn("OppSev").append(reports.get(i).getOppSev());
            reportLog.intColumn("OppSlight").append(reports.get(i).getOppSlight());
            reportLog.intColumn("OppRemaining").append(reports.get(i).getOppRemaining());
            reportLog.intColumn("OppKP").append(reports.get(i).getOppKP());
            reportLog.intColumn("OppPowerLoss").append(reports.get(i).getOppPowerLoss());
            reportLog.stringColumn("ID").append(reports.get(i).getId());
        }
    }

    public Set<String> getFilesRead() {
        Set<String> filesRead = new HashSet<String>();
        for (int i = 0; i < reports.size(); i++) {
            filesRead.add(reports.get(i).getId().split("-")[0]);
        }
        return filesRead;
    }

    public Table getLog() {
        return this.reportLog;
    }

    public ObservableList<Report> getReports() {
        return this.reports;
    }
}
