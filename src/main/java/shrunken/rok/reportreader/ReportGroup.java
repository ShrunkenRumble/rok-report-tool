package shrunken.rok.reportreader;

import java.util.ArrayList;
import java.util.List;

public class ReportGroup {
    private List<Report> reports;

    public ReportGroup() {
        reports = new ArrayList<Report>();
    }

    public void addReport(Report report) {
        this.reports.add(report);
    }

}
