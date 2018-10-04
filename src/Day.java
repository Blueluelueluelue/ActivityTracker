import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

import java.util.*;

public class Day {

    private String date;
    private String[] activities;
    private ArrayList<Integer[]> stats;
    private ArrayList<String[]> classes;
    private int radius;
    private PApplet pAppletObj;
    private String filePath;
    private PieChart pieChart;

    public String getDate() {
        return date;
    }

    public ArrayList<Integer[]> getStats() {
        return stats;
    }

    public int getMonthNumber() {
        return Integer.parseInt(date.substring(3, 5));
    }

    public Day(String date, String filePath, PApplet pApplet) {
        pAppletObj = pApplet;
        this.filePath = filePath;
        this.date = date;
        initializeActivities();
        initializeClasses();
        initializeStats();

        int centerX = pAppletObj.width / 2;
        int centerY = pAppletObj.height / 2;
        ArrayList<Integer[]> colors = getColors();
        radius = PApplet.floor((float) ((pAppletObj.width < pAppletObj.height ? pAppletObj.width : pAppletObj.height) / 1.5)) / 2;

        pieChart = new PieChart(stats, classes, centerX ,centerY, radius, colors, pAppletObj);

    }

    private void initializeActivities() {
        Table today;
        if (date.length() == 0) {
            System.out.println("Date is empty");
            return;
        }
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


    private ArrayList<Integer[]> getColors() {
        ArrayList<Integer[]> colors = new ArrayList<>();
        ColorGenerator cg = new ColorGenerator(pAppletObj);
        for (int i = 0; i < stats.size(); i++) {
            Integer[] row = new Integer[stats.get(i).length];
            for (int j = 0; j < stats.get(i).length; j++) {
                row[j] = cg.nextColor();
            }
            colors.add(row);
        }
        return colors;
    }


    public void makePieChart(String...lowLevelFor) {
        pieChart.draw(lowLevelFor);
    }

}
