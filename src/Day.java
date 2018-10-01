import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

import java.util.ArrayList;

public class Day {
    private Table today;
    private String date;
    private String[] activities;
    private Integer[] stats;
    private ArrayList<Integer[]> lowLevelStats;
    private PApplet pAppletObj;
    private ArrayList<String[]> classes;
    private final int NUM_TIMESLOTS = 48;
    private final int IS_A_CLASS = -1;
    private int radius;

    public String getDate() {
        return date;
    }

    public Integer[] getStats() {
        return stats;
    }

    public Day(String date, PApplet pApplet) {
        pAppletObj = pApplet;
        this.date = date;
        initializeTable();
        initializeClasses();
        initializeActivities();
        initializeStats();
        radius = PApplet.floor((float) ((pAppletObj.width < pAppletObj.height ? pAppletObj.width : pAppletObj.height) / 1.5)) / 2;
    }

    public int getMonthNumber() {
        return Integer.parseInt(date.substring(3, 5));
    }

    private void initializeClasses() {
        classes = new ArrayList<>();
        classes.add(new String[]{"sleep"});
        classes.add(new String[]{"productive", "study", "exercise", "project", "practice"});
        classes.add(new String[]{"unproductive", "games", "videos", "sports"});
        classes.add(new String[]{"social", "meet friends", "family stuff"});
        classes.add(new String[]{"necessities", "eat", "hygiene", "chores"});
    }

    private void initializeStats() {
        if (activities == null) {
            System.out.println("The activities array hasn't been initialized");
            return;
        }
        stats = new Integer[classes.size()];
        lowLevelStats = new ArrayList<>();
        for (String[] category: classes) {
            Integer[] low = new Integer[category.length];
            populateWith(low, 0);
            lowLevelStats.add(low);
        }
        populateWith(stats, 0);

        for (String activity: activities) {
            String[] task = activity.split("\\|");
            String theClass = task[0];
            int classIndex = getClassNumber(theClass);
            if (classIndex != -1) {
                stats[classIndex]++;
                if (task.length == 1) {
                    lowLevelStats.get(classIndex)[0]++;
                }
                for (int i = 1; i < task.length; i++) {
                    String theSubClass = task[i];
                    int subClassIndex = getSubClassNumber(theSubClass);
                    if (subClassIndex != -1) {
                        lowLevelStats.get(classIndex)[subClassIndex]++;
                    } else {
                        System.out.println("There's some problem in some of the subclasses of date " + date);
                    }
                }
            } else {
                System.out.println("There's some problem in the data of date " + date);
            }
        }
    }

    private int getSubClassNumber(String theSubClass) {
        for (String[] category: classes) {
            for (int j = 1; j < category.length; j++) {
                if (category[j].equals(theSubClass)) {
                    return j;
                }
            }
        }
        return  -1;
    }

    private void populateWith(Integer[] arr, int num) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = num;
        }
    }

    private int getClassNumber(String className) {
        for (int i = 0; i < classes.size(); i++) {
            if (className.equals(classes.get(i)[0])) {
                return i;
            }
        }
        return -1;
    }

    private void initializeActivities() {
        if (today == null) {
            System.out.println("The table for the csv file is not initialized");
            return;
        }
        activities = new String[today.getRowCount()];
        int actNum = 0;
        for (TableRow row : today.rows()) {
            activities[actNum] = row.getString("activity");
            actNum++;
        }

    }

    private void initializeTable() {
        if (date.length() == 0) {
            System.out.println("Date is empty");
            return;
        }
        String filePath = "E:\\Programs\\Java\\Random Programs\\ActivityTracker\\data\\";
        today = pAppletObj.loadTable(filePath + date + ".csv", "header");
        today.trim();
        if (today == null) {
            System.out.println("The file of date " + date + " is not present in the data folder");
        }
    }

    public void makePieChart() {
        int centerX = pAppletObj.width / 2;
        int centerY = pAppletObj.height / 2;

        int[] colors = new int[]{
                 pAppletObj.color(197, 204, 20),
                 pAppletObj.color(32, 41, 158),
                 pAppletObj.color(148, 47, 131),
                 pAppletObj.color(87, 0, 82),
                 pAppletObj.color(200, 169, 177)
        };
        PieChart.draw(stats, colors, radius, centerX, centerY, pAppletObj);
        PieChart.drawOutline(centerX, centerY, radius, pAppletObj.color(255,255,255), 4, pAppletObj);
        String[] classLabels = new String[] {
                "Sleep",
                "Productive",
                "Unproductive",
                "Social",
                "Necessities"
        };
        PieChart.label(stats, classLabels, centerX, centerY, radius, pAppletObj.color(255, 0, 0), pAppletObj.color(0, 0, 255),6, 20, pAppletObj);
    }


    public void makeLowLevelPieChart() {
        pAppletObj.background(200, 200, 200);
        int centerX = pAppletObj.width / 2;
        int centerY = pAppletObj.height / 2;

        int sum = 0;
        for (Integer[] low: lowLevelStats) {
            sum += low.length;
            if (low.length != 1) {
                sum--;
            }
        }
        Integer[] lowLevelStats = new Integer[sum];
        int k = 0;
        for (Integer[] low: this.lowLevelStats) {
            for (int i = (low.length == 1) ? 0 : 1; i < low.length; i++, k++) {
                lowLevelStats[k] = low[i];
            }
        }

        int[] colors = new int[] {
                 pAppletObj.color(197, 204, 20),
                 pAppletObj.color(59, 88, 161),
                 pAppletObj.color(197, 70, 142),
                 pAppletObj.color(204, 203, 117),
                 pAppletObj.color(119, 76, 123),
                 pAppletObj.color(156, 71, 198),
                 pAppletObj.color(72,109,180),
                 pAppletObj.color(83,249,219),
                 pAppletObj.color(87,122,214),
                 pAppletObj.color(249,22,136),
                 pAppletObj.color(8,172,200),
                 pAppletObj.color(102,64,139),
                 pAppletObj.color(75,20,7)
        };
        PieChart.draw(lowLevelStats, colors, radius, centerX, centerY, pAppletObj);
        PieChart.drawOutline(centerX, centerY, radius, pAppletObj.color(255, 255, 255), 4, pAppletObj);
        String[] subClassLabels = new String[] {
                "Sleep",
                "Study",
                "Exercise",
                "Project",
                "Practice",
                "Games",
                "Videos",
                "Sports",
                "Meet friends",
                "Family stuff",
                "Eat",
                "Hygiene",
                "Chores"
        };
        PieChart.label(lowLevelStats, subClassLabels, centerX, centerY, radius, pAppletObj.color(0, 0, 255), pAppletObj.color(255, 0, 0), 4, 20, pAppletObj);
    }


    private int getSubClassColor(int index) {
        switch(index) {
            case 2:
                return pAppletObj.color(59, 88, 161);
            case 3:
                return pAppletObj.color(197, 70, 142);
            case 4:
                return pAppletObj.color(204, 203, 117);
            case 5:
                return pAppletObj.color(119, 76, 123);
            case 7:
                return pAppletObj.color(156, 71, 198);
            case 8:
                return pAppletObj.color(72,109,180);
            case 9:
                return pAppletObj.color(83,249,219);
            case 11:
                return pAppletObj.color(87,122,214);
            case 12:
                return pAppletObj.color(249,22,136);
            case 14:
                return pAppletObj.color(8,172,200);
            case 15:
                return pAppletObj.color(102,64,139);
            case 16:
                return pAppletObj.color(75,20,7);
            default:
                return pAppletObj.color(0, 0, 0);
        }
    }
}
