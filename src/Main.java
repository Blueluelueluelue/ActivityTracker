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
    int sum;

    ArrayList<Table> allFiles;
    ArrayList<String[]> allActivities;
    Day seventeenth;
    boolean drawPie = true;
    MyRoutine myRoutine;

    public void settings() {
        size(700, 600);

        //seventeenth = new Day("17-09-2018", this);

        String folder = "E:\\Programs\\Java\\Random Programs\\ActivityTracker\\data";
        Day day = new Day("16-09-2018", this);
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





    public Integer[] getStats(String[] activities) {
        Integer[] stats = new Integer[NUM_CLASSES];
        for (String activity : activities) {
            stats[getClassNumber(activity)]++;
        }
        return stats;
    }




    public void fileSelected(File selection) {
        if (selection == null) {
            println("Window was closed or the user hit cancel.");
        } else {
            println("User selected " + selection.getAbsolutePath());
        }
    }

    private int getClassNumber(String className) {
        switch (className) {
            case "sleep" :
                return 0;
            case "eat" :
                return 1;
            case "not productive" :
                return 2;
            case "practice programming" :
                return 3;
            case "project" :
                return 4;
            default:
                return -1;
        }
    }

    public void draw() {
        background(0);
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


    public void keyPressed() {
        if (key == 'a' || key == 'A') {
            drawPie = false;
            //myRoutine.drawPieChartForDate("16-09-2018", "low");
        }
        if (key == 'b' || key == 'B') {
            //myRoutine.drawPieChartForDate("16-09-2018", "high");
            drawPie = true;
        }
    }



    /*private int getColor(int index) {

     *//*
        *   #c5cc14
            197, 204, 20
            #20299e
            32, 41, 158
            #942f83
            148, 47, 131
            #570052
            87, 0, 82
            #c8a9b1
            200, 169, 177
        * *//*

        switch(index) {
            case 0:
                return color(197, 204, 20);
            case 1:
                return color(32, 41, 158);
            case 2:
                return color(148, 47, 131);
            case 3:
                return color(87, 0, 82);
            case 4:
                return color(200, 169, 177);
                default:
                    return color(0, 0, 0);
        }
    }*/

    public static void main(String[] args) {

//        NOTE: If your class is part of a package other than the default package,
//        you must call PApplet's main using the package name as well, like this:
//        PApplet.main("packageName.ClassName");
        System.out.println(Main.class.getCanonicalName());
        //PApplet.main(Main.class.getCanonicalName());
        PApplet.main("Main");
    }
}

/*
// The following short CSV file called "mammals.csv" is parsed
// in the code below. It must be in the project's "data" folder.
//
// id,species,name
// 0,Capra hircus,Goat
// 1,Panthera pardus,Leopard
// 2,Equus zebra,Zebra

    Table table;

    void setup() {

        table = loadTable("mammals.csv", "header");

        println(table.getRowCount() + " total rows in table");

        for (TableRow row : table.rows()) {

            int id = row.getInt("id");
            String species = row.getString("species");
            String name = row.getString("name");

            println(name + " (" + species + ") has an ID of " + id);
        }

    }

// Sketch prints:
// 3 total rows in table
// Goat (Capra hircus) has an ID of 0
// Leopard (Panthera pardus) has an ID of 1
// Zebra (Equus zebra) has an ID of 2*/
