import processing.core.PApplet;

public class Main extends PApplet {

    MyRoutine my;
    Button button;

    public void settings() {
        size(700, 600);

        String folder = "E:\\Programs\\Java\\Random Programs\\ActivityTracker\\data\\";
        my = new MyRoutine(folder, this);
        button = new Button(10, 10, 50, 50, this);

    }

    public void draw() {
        background(60);
        my.makePieChart();
        //button.draw();
    }

    public void mousePressed() {
        my.handleClick(mouseX, mouseY);
        System.out.println(button.isInside(mouseX, mouseY));
    }


    public void keyPressed() {
        if (key == 'a' || key == 'A') {
            my.previous();
        }
        if (key == 'm' || key == 'M') {
            my.changeMode();
        }
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

