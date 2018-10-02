import processing.core.PApplet;
import processing.data.Table;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class MyRoutine {
    private PApplet pAppletObj;
    private ArrayList<String> allDayDates;
    private ArrayList<Month> allMonths;
    private ArrayList<Day> allDays;

    /*public MyRoutine(String folderName, PApplet pAppletObj) {
        this.pAppletObj = pAppletObj;
        final File folder = new File(folderName);
        allDayDates = new ArrayList<>();
        getFileNames(folder);
        allDays = new ArrayList<>();
        populateDays();
        allMonths = new ArrayList<>();
        populateMonths();
    }*/

    /*private void populateMonths() {
        for (int i = 1; i <= 12; i++) {
            Month m = new Month(i, allDays, pAppletObj);
            if (m.hasData()) {
                allMonths.add(m);
            }
        }
    }*/

    public void drawPieChartForMonth(String monthName) {
        for (Month month: allMonths) {
            if (monthName.equals(month.getMonthName())) {
                month.makePieChart();
            }
        }
    }

    /*public void drawPieChartForDate(String date, String level) {
        int monthNumber = Integer.parseInt(date.substring(3, 5));
        for (Month month: allMonths) {
            if (month.getMonthNumber() == monthNumber) {
                month.makePieChartForDate(date, level);
                break;
            }
        }
    }*/

    private void populateDays() {
        for (String dayDate: allDayDates) {
            allDays.add(new Day(dayDate, pAppletObj));
        }
    }

    private void getFileNames(final File folder) {
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                getFileNames(fileEntry);
            } else {
                /*Table t = pAppletObj.loadTable(fileEntry.getName(), "header");
                t.trim();*/
                String fileName = fileEntry.getName();
                fileName = fileName.substring(0, fileName.indexOf('.'));
                allDayDates.add(fileName);
            }
        }
    }
    /*
    public void populateActivities() {
        allActivities = new ArrayList<>();
        for (int i = 0; i < allFiles.size(); i++) {
            Table file = allFiles.get(i);
            String[] activities = new String[file.getRowCount()];
            int actNum = 0;
            for (TableRow row : file.rows()) {
                activities[actNum] = row.getString("activity");
                actNum++;
            }
            allActivities.add(activities);
        }
    }

    public void populateStats() {
        allStats = new ArrayList<>();
        for (int i = 0; i < allActivities.size(); i++) {
            Integer[] stats = getStats(allActivities.get(i));
            allStats.add(stats);
        }
    }
    */
}
