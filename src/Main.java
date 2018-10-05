import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

public class Main extends PApplet {

    final int NUM_CLASSES = 5;
    Table table;
    ArrayList<Integer[]> allStats;
    int sum = 0;

    ArrayList<Table> allFiles;
    ArrayList<String[]> allActivities;
    Day seventeenth;
    boolean drawPie = true;
    MyRoutine my;
    Day day;
    Month m;

    boolean pro = false;
    boolean unpro = false;
    boolean soc = false;
    boolean necc = false;

    public void settings() {
        size(700, 600);

        //seventeenth = new Day("17-09-2018", this);

        String folder = "E:\\Programs\\Java\\Random Programs\\ActivityTracker\\data\\";
        my = new MyRoutine(folder, this);
//        m = new Month(7, my.getAllDates(), folder, this);
//        day = new Day("16-09-2018", folder, this);

//        myRoutine = new MyRoutine(folder, this);

        /*
        populateFiles(folder);
        populateActivities();
        populateStats();*/

        //selectFolder("Select a folder to process:", "folderSelected");
        //selectInput("Select a file to process:", "fileSelected");

//        Iterable<TableRow> iterator = table.matchRows("sleep", "activity");

        /*for (TableRow row : iterator) {
            println(row.getString("time") + ": " + row.getString("activity"));
        }*/

        // But I am doing it this way


        // Recommended way of traversing the file structure
        /*try (Stream<Path> paths = Files.walk(Paths.get("D:\\Data"))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public void draw() {
        background(50);
        my.makePieChart();
//        day.makePieChart();
//        m.makePieChart();
        //PieChart.drawOutline(width / 2, height / 2, 200, color(255), 5, this);
        /*strokeWeight(10);
        stroke(255);
        point(400, 350);
        float radius = (float) ((width / 1.5) / 2);
        line(350, 300, 350 + radius * (float) Math.cos(0), 300 + radius * (float)Math.sin(0));
        line(350, 300, 350 + radius * (float) Math.cos(HALF_PI), 300 + radius * (float)Math.sin(HALF_PI));*/


        /*ArrayList<String> classes = new ArrayList<>();


        if (unpro) {
            classes.add("unproductive");
        }
        if (pro) {
            classes.add("productive");
        }
        if (soc) {
            classes.add("social");
        }
        if (necc) {
            classes.add("necessities");
        }



        String[] arr = new String[classes.size()];
        for (int i = 0; i < classes.size(); i++) {
            arr[i] = classes.get(i);
        }
//        day.makePieChart(arr);
        m.makePieChart(arr);*/
        //        day.makePieChart("low");
        /*if (drawPie)
            day.makePieChart("high");
        else
            day.makePieChart("low");*/
        //myRoutine.drawPieChartForMonth("August");
        /*if (drawPie)
            myRoutine.drawPieChartForDate("16-09-2018", "high");
        else*/
//        myRoutine.drawPieChartForDate("16-09-2018", "low");
        /*background(0);
//        ellipse(mouseX, mouseY, 20, 20);
        stroke(255);
        //fill(255, 0, 0);
        //arc(100, 100, 100, 100, 0, PI, PIE);
        float previous = 0;
        for (int i = 0; i < stats.length; i++) {
            float factor = (float) stats[i] / sum;
            int fillColor = getColor(i);
            fill(fillColor);
            arc(100, 100, 100, 100, previous, previous + factor*TWO_PI, PIE);
            previous += factor * TWO_PI;
        }*/
        //seventeenth.makePieChart();
        /*if (drawPie) {
            seventeenth.makePieChart();
        }*/
    }

    public void mousePressed() {
        my.handleClick(mouseX, mouseY);
    }






    public void fileSelected(File selection) {
        if (selection == null) {
            println("Window was closed or the user hit cancel.");
        } else {
            println("User selected " + selection.getAbsolutePath());
        }
    }






    public void keyPressed() {
//        if (key == 'a' || key == 'A') {
//
//        }
        if (key == 'd' || key == 'D') {
            my.next();
        }
    }





    public static void main(String[] args) {

//        NOTE: If your class is part of a package other than the default package,
//        you must call PApplet's main using the package name as well, like this:
//        PApplet.main("packageName.ClassName");
        System.out.println(Main.class.getCanonicalName());
        //PApplet.main(Main.class.getCanonicalName());
        PApplet.main("Main");
    }
}

