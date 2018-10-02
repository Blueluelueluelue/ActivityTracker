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
            } else {
                subTasks.add(task[0]);
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
        for (Integer[] s: stats) {
            if (s[1] == 0) {
                s[1] = s[0];
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

    private Integer[] getStats(String option) {
        Integer[] statsArray;
        switch (option) {
            case "high":
                statsArray = new Integer[stats.size()];
                for (int i = 0; i < statsArray.length; i++) {
                    statsArray[i] = stats.get(i)[0];
                }
                return statsArray;

            case "low":
                int size = 0;
                for (Integer[] s : stats) {
                    size += s.length - 1;
                }
                statsArray = new Integer[size];
                int k = 0;
                for (Integer[] s : stats) {
                    for (int i = 1; i < s.length; i++, k++) {
                        statsArray[k] = s[i];
                    }
                }
                return statsArray;

            default:
                System.out.println("Bad option for the level");
                return null;
        }
    }

    private String[] getLabels(String option) {
        String[] labels;
        switch (option) {
            case "high":
                labels = new String[classes.size()];
                int i = 0;
                for (String[] s: classes) {
                    labels[i++] = s[0];
                }
                return labels;

            case "low":
                int size = 0;
                for (String[] s : classes) {
                    size += s.length - 1;
                }
                labels = new String[size];
                int k = 0;
                for (String[] s : classes) {
                    for (int j = 1; j < s.length; j++, k++) {
                        labels[k] = s[j];
                    }
                }
                return labels;

            default:
                System.out.println("Bad option for the level");
                return null;
        }
    }

    public void makePieChart(String option) {
        int centerX = pAppletObj.width / 2;
        int centerY = pAppletObj.height / 2;

        int[] colors = getColors(option);
        Integer[] statArray = getStats(option);
        String[] labels = getLabels(option);
        if (statArray != null && colors != null && labels != null) {
            PieChart.draw(statArray, colors, radius, centerX, centerY, pAppletObj);
            PieChart.drawOutline(centerX, centerY, radius, pAppletObj.color(230, 230, 230), 4, pAppletObj);
            PieChart.label(statArray, labels, centerX, centerY, radius, pAppletObj.color(255, 0, 0), pAppletObj.color(210, 210, 210),3, 16, pAppletObj);
        }
    }

}
