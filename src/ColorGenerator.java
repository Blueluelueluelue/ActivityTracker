import processing.core.PApplet;

import java.util.ArrayList;

public class ColorGenerator {
    private PApplet pAppletObj;
    private ArrayList<Integer> colorList;
    private int currentColor;

    public ColorGenerator(PApplet pAppletObj) {
        this.pAppletObj = pAppletObj;
        colorList = new ArrayList<>();
        fillColors();
        currentColor = -1;
    }

    private void fillColors() {
        colorList.add(pAppletObj.color(197, 204, 20));
        colorList.add(pAppletObj.color(32, 41, 158));
        colorList.add(pAppletObj.color(148, 47, 131));
        colorList.add(pAppletObj.color(87, 0, 82));
        colorList.add(pAppletObj.color(200, 169, 177));
        colorList.add(pAppletObj.color(59, 88, 161));
        colorList.add(pAppletObj.color(197, 70, 142));
        colorList.add(pAppletObj.color(204, 203, 117));
        colorList.add(pAppletObj.color(119, 76, 123));
        colorList.add(pAppletObj.color(156, 71, 198));
        colorList.add(pAppletObj.color(72,109,180));
        colorList.add(pAppletObj.color(83,249,219));
        colorList.add(pAppletObj.color(87,122,214));
        colorList.add(pAppletObj.color(249,22,136));
        colorList.add(pAppletObj.color(8,172,200));
        colorList.add(pAppletObj.color(102,64,139));
        colorList.add(pAppletObj.color(75,20,7));
    }

    public int nextColor() {
        currentColor = (currentColor + 1) % colorList.size();
        return colorList.get(currentColor);
    }

    public ArrayList<Integer[]> getColors(ArrayList<Integer[]> stats) {
        ArrayList<Integer[]> colors = new ArrayList<>();
        ColorGenerator cg = new ColorGenerator(pAppletObj);
        for (Integer[] stat : stats) {
            Integer[] row = new Integer[stat.length];
            for (int j = 0; j < stat.length; j++) {
                row[j] = cg.nextColor();
            }
            colors.add(row);
        }
        return colors;
    }


}
