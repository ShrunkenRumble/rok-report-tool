package shrunken.rok.reportreader;


import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tech.tablesaw.api.*;

public class ReportLog {
    private Table reportLog;
    private ObservableList<Report> reports;

    public ReportLog() {
        reports = FXCollections.observableArrayList();
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

    public void addReport(ArrayList<Report> report) {
        for (int i = 0; i < report.size(); i++) {
            this.reports.add(report.get(i));

            reportLog.stringColumn("MyPrimCmdr").append(report.get(i).getMyPrimCmdr());
            reportLog.stringColumn("MySecCmdr").append(report.get(i).getMySecCmdr());
            reportLog.intColumn("MyUnits").append(report.get(i).getMyUnits());
            reportLog.intColumn("MyHeals").append(report.get(i).getMyHeals());
            reportLog.intColumn("MyDead").append(report.get(i).getMyDead());
            reportLog.intColumn("MySev").append(report.get(i).getMySev());
            reportLog.intColumn("MySlight").append(report.get(i).getMySlight());
            reportLog.intColumn("MyRemaining").append(report.get(i).getMyRemaining());
            reportLog.intColumn("MyKP").append(report.get(i).getMyKP());
            reportLog.intColumn("MyPowerLoss").append(report.get(i).getMyPowerLoss());
            reportLog.stringColumn("OppPrimCmdr").append(report.get(i).getOppPrimCmdr());
            reportLog.stringColumn("OppSecCmdr").append(report.get(i).getOppSecCmdr());
            reportLog.intColumn("OppUnits").append(report.get(i).getOppUnits());
            reportLog.intColumn("OppHeals").append(report.get(i).getOppHeals());
            reportLog.intColumn("OppDead").append(report.get(i).getOppDead());
            reportLog.intColumn("OppSev").append(report.get(i).getOppSev());
            reportLog.intColumn("OppSlight").append(report.get(i).getOppSlight());
            reportLog.intColumn("OppRemaining").append(report.get(i).getOppRemaining());
            reportLog.intColumn("OppKP").append(report.get(i).getOppKP());
            reportLog.intColumn("OppPowerLoss").append(report.get(i).getOppPowerLoss());
            reportLog.stringColumn("ID").append(report.get(i).getId());
        }
    }

    public ArrayList<String> getFilesRead() {
        ArrayList<String> filesRead = new ArrayList<String>();
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
