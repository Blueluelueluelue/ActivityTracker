import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashSet;

public class Month {
    private ArrayList<Day> daysInThisMonth;
    private String monthName;
    private int monthNumber;
    private PApplet pAppletObj;
    private ArrayList<Integer[]> monthStats;
    private ArrayList<String[]> monthClasses;
    private PieChart pieChart;

    public Month(int monthNumber, ArrayList<String> allDates, String filePath, PApplet pAppletObj) {
        this.pAppletObj = pAppletObj;
        this.monthNumber = monthNumber;
        this.monthName = getMonthName(monthNumber);

        populateDays(allDates, filePath);

        monthClasses = new ArrayList<>();
        populateClasses();

        monthStats = new ArrayList<>();
        populateStats();

        int centerX = pAppletObj.width / 2;
        int centerY = pAppletObj.height / 2;
        int radius = PApplet.floor((float) ((pAppletObj.width < pAppletObj.height ? pAppletObj.width : pAppletObj.height) / 1.5)) / 2;

        ColorGenerator cg = new ColorGenerator(pAppletObj);
        ArrayList<Integer[]> colors = cg.getColors(monthStats);
        pieChart = new PieChart(monthStats, monthClasses, centerX, centerY, radius, colors, pAppletObj);

    }

    public int getMonthNumber() {
        return monthNumber;
    }

    private void populateDays(ArrayList<String> allDates, String filePath) {
        daysInThisMonth = new ArrayList<>();
        for (String date: allDates) {
            int monthInDate = Integer.parseInt(date.split("-")[1]);
            if (monthInDate == monthNumber) {
                Day day = new Day(date, filePath, pAppletObj);
                daysInThisMonth.add(day);
            }
        }
    }

    private void populateClasses() {
        // using a HashSet because we want a list with only unique values for our classes list
        HashSet<String> highLevelClasses = new HashSet<>();

        // get all the high level classes from all the days in this month and add them to the HashSet
        for (Day day: daysInThisMonth) {
            ArrayList<String> high = day.getHighLevelClasses();
            highLevelClasses.addAll(high);
        }

        // same reason for using HashSet but since we want a different list for each high level class,
        // we use a list to store the individual HashSets
        ArrayList<HashSet<String>> allLowLevelClasses = new ArrayList<>();
        for (String highLevelClass : highLevelClasses) {
            HashSet<String> set = new HashSet<>();
            // get all the low level classes corresponding to each high level class and add them to the corresponding HashSet
            // so repeat values get discarded
            for (Day day : daysInThisMonth) {
                ArrayList<String> lowLevelClasses = day.getLowLevelClasses(highLevelClass);
                set.addAll(lowLevelClasses);
            }
            // then add them to the list
            allLowLevelClasses.add(set);
        }

        // now that we have the list of unique classNames sorted according to their level and which
        // class they are under we can populate the monthClasses list.
        int i = 0;
        for (String highLevelClass: highLevelClasses) {
            HashSet<String> set = allLowLevelClasses.get(i++);
            String[] row = new String[set.size() + 1];
            row[0] = highLevelClass;
            int j = 1;
            for (String lowLevelClass: set) {
                row[j++] = lowLevelClass;
            }
            monthClasses.add(row);
        }
    }

    private void populateStats() {
        // go through all the classes
        for (String[] monthClass : monthClasses) {
            Integer[] row = new Integer[monthClass.length];
            for (int j = 0; j < monthClass.length; j++) {
                Integer sum = 0;
                // get the number of occurrences of each class
                for (Day day : daysInThisMonth) {
                    sum += day.getStatsOf(monthClass[j]);
                }
                // add that to the row
                row[j] = sum;
            }
            // add the row
            monthStats.add(row);
        }
    }

    private String getMonthName(int monthNumber) {
        switch (monthNumber) {
            case 1:
                return  "January";
            case 3:
                return "March";
            case 5:
                return "May";
            case 7:
                return "July";
            case 8:
                return "August";
            case 10:
                return "October";
            case 12:
                return "December";
            case 2:
                return "February";
            case 4:
                return "April";
            case 6:
                return "June";
            case 9:
                return "September";
            case 11:
                return "November";
            default:
                System.out.println("Bad month number");
                return "Wrong Month";
        }
    }

    public void makePieChart() {
        pieChart.draw();
        displayMonthName();
    }

    private void displayMonthName() {
        pAppletObj.pushMatrix();
        pAppletObj.textSize(15);
        pAppletObj.text(monthName, 50, 50);
        pAppletObj.popMatrix();
    }

    public void handleClick(int mouseX, int mouseY) {
        String theClass = pieChart.clickedInsideClass(mouseX, mouseY);
        pieChart.includeLowLevelClass(theClass);
    }

    public boolean hasData() {
        return daysInThisMonth.size() > 0;
    }

    public String getMonthName() {
        return monthName;
    }

    public Day getDay(int index) {
        return daysInThisMonth.get(index);
    }

    public boolean isLastDayOfMonth(int index) {
        return index == daysInThisMonth.size() - 1;
    }
}
