import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

import java.util.*;

public class Day {
    private String date;
    private String[] activities;
    private ArrayList<Integer[]> stats;
    private PApplet pAppletObj;
    private ArrayList<String[]> classes;
    private final int NUM_TIMESLOTS = 48;
    private final int IS_A_CLASS = -1;
    private int radius;

    public String getDate() {
        return date;
    }

    public ArrayList<Integer[]> getStats() {
        return stats;
    }

    public int getMonthNumber() {
        return Integer.parseInt(date.substring(3, 5));
    }

    public Day(String date, PApplet pApplet) {
        pAppletObj = pApplet;
        this.date = date;
        initializeActivities();
        initializeClasses();
        initializeStats();
        radius = PApplet.floor((float) ((pAppletObj.width < pAppletObj.height ? pAppletObj.width : pAppletObj.height) / 1.5)) / 2;
    }

    private void initializeActivities() {
        Table today;
        if (date.length() == 0) {
            System.out.println("Date is empty");
            return;
        }
        String filePath = "E:\\Programs\\Java\\Random Programs\\ActivityTracker\\data\\";
        today = pAppletObj.loadTable(filePath + date + ".csv", "header");
        if (today == null) {
            System.out.println("The file of date " + date + " is not present in the data folder");
            return;
        }
        today.trim();
        activities = new String[today.getRowCount()];
        int actNum = 0;
        for (TableRow row : today.rows()) {
            activities[actNum] = row.getString("activity");
            actNum++;
        }
    }


    private void initializeClasses() {
        Map<String, HashSet<String>> classMap = new HashMap<>();
        classes = new ArrayList<>();
        String[] task;
        for (String activity: activities) {
            task = activity.split(" *\\| *");
            HashSet<String> subTasks = new HashSet<>();

            if (classMap.containsKey(task[0])) {
                subTasks = classMap.get(task[0]);
            }
            if (task.length > 1) {
                subTasks.add(task[1]);
            }
            classMap.put(task[0], subTasks);
        }
        for (String theClass: classMap.keySet()) {
            HashSet<String> subClasses = classMap.get(theClass);
            int length = subClasses.size() + 1;
            String[] fullClass = new String[length];
            fullClass[0] = theClass;
            Iterator<String> iterator = subClasses.iterator();
            int i = 1;
            while (iterator.hasNext() && i < fullClass.length) {
                fullClass[i] = iterator.next();
                i++;
            }
            classes.add(fullClass);
        }
    }

    private void initializeStats() {
        if (activities == null) {
            System.out.println("The activities array hasn't been initialized");
            return;
        }
        stats = new ArrayList<>();
        for (String[] aClass : classes) {
            Integer[] freq = new Integer[aClass.length];
            populateWith(freq, 0);
            stats.add(freq);
        }
        for (String activity: activities) {
            String[] task = activity.split(" *\\| *");
            for (String aClass: task) {
                int indices[] = getClassNumber(aClass);
                if (indices[0] == -1 || indices[1] == -1) {
                    System.out.println("There is a problem with the data in " + date);
                }
                stats.get(indices[0])[indices[1]]++;
            }
        }

    }


    private void populateWith(Integer[] arr, int num) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = num;
        }
    }

    private int[] getClassNumber(String className) {
        int[] indices = new int[2];
        for (int i = 0; i < classes.size(); i++) {
            for (int j = 0; j < classes.get(i).length; j++) {
                if (className.equals(classes.get(i)[j])) {
                    indices[0] = i;
                    indices[1] = j;
                    return indices;
                }
            }
        }
        return new int[] {-1, -1};
    }


    private int[] getColors(String option) {
        ColorGenerator cg = new ColorGenerator(pAppletObj);
        int[] colors;
        switch (option) {
            case "high":
                colors = new int[stats.size()];
                break;
            case "low":
                int size = 0;
                for (Integer[] s : stats) {
                    size += s.length - 1;
                }
                colors = new int[size];
                break;
            default:
                System.out.println("Bad option for the level");
                return null;
        }

        for (int i = 0; i < colors.length; i++) {
            colors[i] = cg.nextColor();
        }
        return colors;
    }

    private int[] getStats(String option) {
        int[] statsArray;
        switch (option) {
            case "high":
                statsArray = new int[stats.size()];
                for (int i = 0; i < statsArray.length; i++) {
                    statsArray[i] = stats.get(i)[0];
                }
                return statsArray;
            case "low":
                int size = 0;
                for (Integer[] s : stats) {
                    size += s.length - 1;
                }
                statsArray = new int[size];
                int k = 0;
                for (Integer[] s : stats) {
                    for (int i = 0; i < s.length; i++, k++) {
                        statsArray[k] = s[i];
                    }
                }
                return statsArray;
            default:
                System.out.println("Bad option for the level");
                return null;
        }
    }

    public void makePieChart(String option) {
        int centerX = pAppletObj.width / 2;
        int centerY = pAppletObj.height / 2;

        int[] colors = getColors(option);
        int[] statArray = getStats(option);

/*
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
        PieChart.label(stats, classLabels, centerX, centerY, radius, pAppletObj.color(255, 0, 0), pAppletObj.color(0, 0, 255),6, 20, pAppletObj);*/
    }


    /*public void makeLowLevelPieChart() {
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
    }*/


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
