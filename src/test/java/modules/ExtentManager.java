package modules;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

    static Date date = new Date();
    static Timestamp ts = new Timestamp(date.getTime());
    static SimpleDateFormat timestamp = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
    static String timeStamp = timestamp.format(ts).replace(" ", "_").replace("-", "_");

    public static final String fileSeperator = System.getProperty("file.separator");
    public static final String reportFilepath = System.getProperty("user.dir") + fileSeperator + "Reports" + fileSeperator + "Report_" + timeStamp;
    private static final String reportFileName = "Test_Automation_Report_" + timeStamp + ".html";
    private static final String reportFileLocation = reportFilepath + fileSeperator + reportFileName;

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }

    public static ExtentReports createInstance() {
        String fileName = getReportPath();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(reportFileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(reportFileName);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        return extent;
    }

    private static String getReportPath() {
        File testDirectory = new File(reportFilepath);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdirs()) {
                System.out.println("Directory: " + reportFilepath + " is created!");
            } else {
                System.out.println("Failed to create directory: " + reportFilepath);
                return System.getProperty("user.dir");
            }
        } else {
            System.out.println("Directory already exists: " + reportFilepath);
        }
        return reportFileLocation;
    }
}
