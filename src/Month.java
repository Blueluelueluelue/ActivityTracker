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

    public Month(int monthNumber, ArrayList<String> allDates, String filePath, PApplet pAppletObj) {
        this.pAppletObj = pAppletObj;
        this.monthNumber = monthNumber;
        this.monthName = getMonthName(monthNumber);

        populateDays(allDates, filePath);
        monthClasses = new ArrayList<>();
        populateClasses();

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
        HashSet<String> highLevelClasses = new HashSet<>();

        for (Day day: daysInThisMonth) {
            ArrayList<String> high = day.getHighLevelClasses();
            highLevelClasses.addAll(high);
        }
        ArrayList<HashSet<String>> allLowLevelClasses = new ArrayList<>();
        for (String highLevelClass : highLevelClasses) {
            HashSet<String> set = new HashSet<>();
            for (Day day : daysInThisMonth) {
                ArrayList<String> lowLevelClasses = day.getLowLevelClasses(highLevelClass);
                set.addAll(lowLevelClasses);
            }
            allLowLevelClasses.add(set);
        }

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

    /*private void populateStats() {

        Day day1 = daysInThisMonth.get(0);
        Integer[] day1Stats = day1.getStats();
        int numOfClasses = day1Stats.length;
        monthStats = new Integer[numOfClasses];
        populateWith(monthStats, 0);

        for (Day day : daysInThisMonth) {
            Integer[] dayStats = day.getStats();
            for (int classNum = 0; classNum < dayStats.length; classNum++) {
                monthStats[classNum] += dayStats[classNum];
            }
        }
    }*/

    private void populateWith(Integer[] arr, int num) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = num;
        }
    }

    public boolean hasData() {
        return daysInThisMonth.size() > 0;
    }

    public String getMonthName() {
        return monthName;
    }

   /* public void makePieChart() {
        int[] colors = new int[]{
                pAppletObj.color(197, 204, 20),
                pAppletObj.color(32, 41, 158),
                pAppletObj.color(148, 47, 131),
                pAppletObj.color(87, 0, 82),
                pAppletObj.color(200, 169, 177)
        };
        int radius = PApplet.floor((float) ((pAppletObj.width < pAppletObj.height ? pAppletObj.width : pAppletObj.height) / 1.5)) / 2;
        int centerX = pAppletObj.width / 2;
        int centerY = pAppletObj.height / 2;

        PieChart.draw(monthStats, colors, radius, centerX, centerY, pAppletObj);
        PieChart.drawOutline(centerX, centerY, radius, pAppletObj.color(255, 255, 255), 4, pAppletObj);
        String[] classLabels = new String[]{
                "Sleep",
                "Productive",
                "Unproductive",
                "Social",
                "Necessities"
        };
        PieChart.label(monthStats, classLabels, centerX, centerY, radius, pAppletObj.color(0, 0, 255), pAppletObj.color(0, 0, 255), 6, 20,  pAppletObj);
    }*/

    /*public void makePieChartForDate(String date, String level) {
        for (Day day: daysInThisMonth) {
            if (day.getDate().equals(date)) {
                if (level.equals("high")) {
                    day.makePieChart();
                } else if (level.equals("low")) {
                    day.makeLowLevelPieChart();
                } else {
                    System.out.println("Don't have the specified level");
                }
                break;
            }
        }
    }*/
}
