package shrunken.rok.reportreader;

import java.util.ArrayList;
import java.util.List;

public class ReportGroup {
    private List<Report> reports;

    public ReportGroup() {
        reports = new ArrayList<Report>();
    }

    public void printReportGroup() {
        for (int i = 0; i < reports.size(); i++) {
            reports.get(i).printReport();
            System.out.println("---------------------------------------------------");
        }
    }

    public void addReport(Report report) {
        this.reports.add(report);
    }

}
