import processing.core.PApplet;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class MyRoutine {
    private final int DAY_MODE = 0;
    private final int MONTH_MODE = 1;
    private PApplet pAppletObj;
    private ArrayList<String> allDates;
    private Month[] allMonths;
    private String folderName;
    private int mode;
    private int dayIndex;
    private int monthIndex;

    public MyRoutine(String folderName, PApplet pAppletObj) {
        this.folderName = folderName;
        this.pAppletObj = pAppletObj;
        final File folder = new File(folderName);
        allDates = new ArrayList<>();
        getAllDates(folder);
        allMonths = new Month[12];
        populateMonths();
        mode = DAY_MODE;
        dayIndex = 0;
        monthIndex = 0;
        while (!allMonths[monthIndex].hasData()) {
            monthIndex = (monthIndex + 1) % allMonths.length;
        }
    }

    private void populateMonths() {
        for (int i = 0; i < allMonths.length; i++) {
            allMonths[i] = new Month(i+1, allDates, folderName, pAppletObj);
        }
    }

    public void next() {
        if (mode == DAY_MODE) {
            if (allMonths[monthIndex].isLastDayOfMonth(dayIndex)) {
                monthIndex = (monthIndex + 1) % allMonths.length;
                while (!allMonths[monthIndex].hasData()) {
                    monthIndex = (monthIndex + 1) % allMonths.length;
                }
                dayIndex = 0;
            } else {
                dayIndex++;
            }
        } else if (mode == MONTH_MODE) {
            dayIndex = 0;
            monthIndex = (monthIndex + 1) % allMonths.length;
            while (!allMonths[monthIndex].hasData()) {
                monthIndex = (monthIndex + 1) % allMonths.length;
            }
        }
    }

    public void changeMode() {
        if (mode == DAY_MODE) {
            mode = MONTH_MODE;
        } else if (mode == MONTH_MODE) {
            mode = DAY_MODE;
        }
    }

    public void handleClick(int mouseX, int mouseY) {
        switch (mode) {
            case DAY_MODE:
                allMonths[monthIndex].getDay(dayIndex).handleClick(mouseX, mouseY);
                break;
            case MONTH_MODE:
                allMonths[monthIndex].handleClick(mouseX, mouseY);
                break;
        }
    }

    public void makePieChart() {
        if (mode == DAY_MODE) {
            if (allMonths[monthIndex].hasData()) {
                allMonths[monthIndex].getDay(dayIndex).makePieChart();
            }
        } else if (mode == MONTH_MODE) {
            if (allMonths[monthIndex].hasData()) {
                allMonths[monthIndex].makePieChart();
            }
        }
    }

    private void getAllDates(final File folder) {
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                getAllDates(fileEntry);
            } else {
                /*Table t = pAppletObj.loadTable(fileEntry.getName(), "header");
                t.trim();*/
                String fileName = fileEntry.getName();
                fileName = fileName.split("\\.")[0];
                allDates.add(fileName);
            }
        }
    }
}
