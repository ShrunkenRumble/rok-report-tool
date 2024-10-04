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

            reportLog.stringColumn("MyPrimCmdr").append(report.get(i).getmyPrimCmdr());
            reportLog.stringColumn("MySecCmdr").append(report.get(i).getmySecCmdr());
            reportLog.stringColumn("OppPrimCmdr").append(report.get(i).getoppPrimCmdr());
            reportLog.stringColumn("OppSecCmdr").append(report.get(i).getoppSecCmdr());
            reportLog.intColumn("MyUnits").append(report.get(i).getmyUnits());
            reportLog.intColumn("MyHeals").append(report.get(i).getmyHeals());
            reportLog.intColumn("MyDead").append(report.get(i).getmyDead());
            reportLog.intColumn("MySev").append(report.get(i).getmySev());
            reportLog.intColumn("MySlight").append(report.get(i).getmySlight());
            reportLog.intColumn("MyRemaining").append(report.get(i).getmyRemaining());
            reportLog.intColumn("MyKP").append(report.get(i).getmyKP());
            reportLog.intColumn("MyPowerLoss").append(report.get(i).getmyPowerLoss());
            reportLog.intColumn("OppUnits").append(report.get(i).getoppUnits());
            reportLog.intColumn("OppHeals").append(report.get(i).getoppHeals());
            reportLog.intColumn("OppDead").append(report.get(i).getoppDead());
            reportLog.intColumn("OppSev").append(report.get(i).getoppSev());
            reportLog.intColumn("OppSlight").append(report.get(i).getoppSlight());
            reportLog.intColumn("OppRemaining").append(report.get(i).getoppRemaining());
            reportLog.intColumn("OppKP").append(report.get(i).getoppKP());
            reportLog.intColumn("OppPowerLoss").append(report.get(i).getoppPowerLoss());
            reportLog.stringColumn("ID").append(report.get(i).getid());
        }
    }

    public ArrayList<String> getFilesRead() {
        ArrayList<String> filesRead = new ArrayList<String>();
        for (int i = 0; i < reports.size(); i++) {
            filesRead.add(reports.get(i).getid().split("-")[0]);
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
