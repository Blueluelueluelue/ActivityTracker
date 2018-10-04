import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Arrays;

public class PieChart {

    private PApplet pAppletObj;
    private ArrayList<Integer[]> numbers;
    private ArrayList<String[]> labels;
    private ArrayList<Integer[]> colors;
    private int radius;
    private int centerX;
    private int centerY;

    public PieChart(ArrayList<Integer[]> numbers,
                    ArrayList<String[]> labels,
                    int centerX,
                    int centerY,
                    int radius,
                    ArrayList<Integer[]> colors,
                    PApplet pAppletObj) {
        this.numbers = numbers;
        this.labels = labels;
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.colors = colors;
        this.pAppletObj = pAppletObj;
    }

    private void print1(ArrayList<Integer[]> numbers) {
        for (Integer[] num: numbers) {
            System.out.println(Arrays.toString(num));
        }
    }

    private void print2(ArrayList<String[]> numbers) {
        for (String[] num: numbers) {
            System.out.println(Arrays.toString(num));
        }
    }

    private ArrayList<Integer> getLowLevelClassIndices(String...lowLevelFor) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (String theClass: lowLevelFor) {
            for (int i = 0; i < labels.size(); i++) {
                if (theClass.equals(labels.get(i)[0])) {
                    indices.add(i);
                    break;
                }
            }
        }
        return indices;
    }

    private void drawArc(float start, float stop, int fillColor) {
        pAppletObj.fill(fillColor);
        pAppletObj.strokeWeight(1);
        pAppletObj.arc(centerX, centerY, radius * 2, radius * 2, start, stop, pAppletObj.PIE);
    }

    public void draw(String...lowLevelFor) {

        // get the location of the classes, for which we have to display a low level view, from the labels
        ArrayList<Integer> indices = getLowLevelClassIndices(lowLevelFor);

        // calculate the high level total
        Integer sum = 0;
        for (Integer[] num: numbers) {
            sum += num[0];
        }

        pAppletObj.noStroke();
        float previous = 0;
        for (int i = 0; i < numbers.size(); i++) {
            // get the high level percentage of the current stat
            float factor = (float) numbers.get(i)[0] / sum;

            // calculate the slice the high level class occupies in the Pie
            float slice = factor * pAppletObj.TWO_PI;

            int fillColor;

            // if the current class's index is inside the list of indices for which we have to display a low level view
            // then access that index's numbers and display the low level view for that class
            // else continue on with the high level view
            if (indices.contains(i)) {

                // since the high level class's numbers, by definition, are the sum of the low level classes' numbers
                Integer lowSum = numbers.get(i)[0];
                float lowFactor;

                // start drawing from where the high level left off
                float lowPrevious = previous;



                for (int j = 1; j < numbers.get(i).length; j++) {

                    // get the low level percentage of the low level class
                    lowFactor = (float) numbers.get(i)[j] / lowSum;

                    fillColor = colors.get(i)[j];
                    drawArc(lowPrevious, lowPrevious + lowFactor * slice, fillColor);

                    // update the low level previous so that the next iteration can start from where we left off.
                    lowPrevious += lowFactor * slice;
                }

                // update the high level previous so that the next iteration can start from where we left off.
                previous = lowPrevious;
            } else {
                fillColor = colors.get(i)[0];
                drawArc(previous, previous + factor * pAppletObj.TWO_PI, fillColor);
                // update previous so that the next iteration can start from where we left off.
                previous += slice;
            }
        }

        drawOutline(pAppletObj.color(200), 4);

        int lineColor = pAppletObj.color(0);
        int textColor = pAppletObj.color(230);
        label(lineColor, 2, textColor, 18, indices);
    }

    private void drawOutline(int color, int strokeWeight) {
        pAppletObj.strokeWeight(strokeWeight);
        pAppletObj.stroke(color);
        pAppletObj.noFill();
        pAppletObj.ellipse(centerX, centerY, radius * 2, radius * 2);
        pAppletObj.noStroke();
    }

    private void label(int lineColor,
                       int strokeWeight,
                       int textColor,
                       int textSize,
                       ArrayList<Integer> indices) {
        // calculate the total of the high level classes' numbers
        Integer sum = 0;
        for (Integer[] num: numbers) {
            sum += num[0];
        }

        float previous = 0;
        float theta;
        for (int i = 0; i < numbers.size(); i++) {
            // get the high level percentage of the current stat
            float factor = (float) numbers.get(i)[0] / sum;

            // calculate the slice the high level class occupies in the Pie
            float slice = factor * pAppletObj.TWO_PI;

            // if the current class's index is inside the list of indices for which we have to label the low level
            // then access that index's numbers and label the low level view for that class
            // else continue on with the high level view
            if (indices.contains(i)) {

                // since the high level class's numbers, by definition, are the sum of the low level classes' numbers
                Integer lowSum = numbers.get(i)[0];
                float lowFactor;
                float lowSlice;

                // start drawing from where the high level left off
                float lowPrevious = previous;

                for (int j = 1; j < numbers.get(i).length; j++) {

                    // get the low level percentage of the low level class
                    lowFactor = (float) numbers.get(i)[j] / lowSum;

                    // calculate the low level slice of the Pie
                    lowSlice = lowFactor * slice;

                    // calculate the angle at which the line gets displayed, which is in the middle of the slice
                    theta = lowPrevious + lowSlice / 2;

                    // display the label at theta
                    displayLabel(labels.get(i)[j], theta, lineColor, strokeWeight, textSize, textColor);

                    // update the low level previous so that the next iteration can start from where we left off.
                    lowPrevious += lowSlice;
                }

                // update the high level previous so that the next iteration can start from where we left off.
                previous = lowPrevious;
            } else {
                // calculate the angle at which the line gets displayed, which is in the middle of the slice
                theta = previous + slice / 2;

                // display the label at theta
                displayLabel(labels.get(i)[0], theta, lineColor, strokeWeight, textSize, textColor);

                // update previous so that the next iteration can start from where we left off.
                previous += slice;
            }
        }
    }

    private void displayLabel(String label, float theta, int lineColor, int strokeWeight, int textSize,  int textColor) {
        double lineLength = radius * 1.25;

        // the text must be displayed a bit beyond the end of the line
        double textDistance = radius * 1.45;

        int halfRadius = radius / 2;

        // converting Polar to Cartesian
        int lineStartX = centerX + PApplet.floor((float) (halfRadius * Math.cos(theta)));
        int lineStartY = centerY + PApplet.floor((float) (halfRadius * Math.sin(theta)));
        int lineEndX = centerX + PApplet.floor((float) (lineLength * Math.cos(theta)));
        int lineEndY = centerY + PApplet.floor((float) (lineLength * Math.sin(theta)));

        pAppletObj.stroke(lineColor);
        pAppletObj.strokeWeight(strokeWeight);
        pAppletObj.fill(textColor);
        pAppletObj.textSize(textSize);

        int showTextAtX = centerX + PApplet.floor((float) (textDistance * Math.cos(theta)));
        int showTextAtY = centerY + PApplet.floor((float) (textDistance * Math.sin(theta)));

        pAppletObj.line(lineStartX, lineStartY, lineEndX, lineEndY);
        pAppletObj.text(label, showTextAtX, showTextAtY);

        pAppletObj.noStroke();
        pAppletObj.noFill();
    }


    public static boolean isInside(int centerX, int centerY, int radius, int pointX, int pointY) {
        int distanceSquared = (int) (Math.pow(centerX - pointX, 2) + Math.pow(centerY - pointY, 2));
        return distanceSquared < radius * radius;
    }
}
