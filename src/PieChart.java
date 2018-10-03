import processing.core.PApplet;

public class PieChart {
    public static void draw(Integer[] numbers, int[] colors, int radius, int centerX, int centerY, PApplet pAppletObj) {
        Integer sum = 0;
        for (Integer num: numbers) {
            sum += num;
        }
        pAppletObj.noStroke();
        int pieLength = radius * 2;
        float previous = 0;
        for (int i = 0; i < numbers.length; i++) {
            float factor = (float) numbers[i] / sum;
            int fillColor = colors[i];
            pAppletObj.fill(fillColor);
            pAppletObj.strokeWeight(1);
            pAppletObj.arc(centerX, centerY, pieLength, pieLength, previous, previous + factor*pAppletObj.TWO_PI, pAppletObj.PIE);
            previous += factor * pAppletObj.TWO_PI;
        }
    }

    public static void drawOutline(int centerX, int centerY, int radius, int color, int strokeWeight, PApplet pAppletObj) {
        pAppletObj.strokeWeight(strokeWeight);
        pAppletObj.stroke(color);
        pAppletObj.noFill();
        pAppletObj.ellipse(centerX, centerY, radius * 2, radius * 2);
        pAppletObj.noStroke();
    }

    public static void label(Integer[] numbers, String[] labels, int centerX, int centerY, int radius, int lineColor, int textColor, int strokeWeight, int textSize, PApplet pAppletObj) {
        if (numbers.length != labels.length) {
            System.out.println("Number of labels and numbers don't match");
            return;
        }
        pAppletObj.stroke(lineColor);
        pAppletObj.strokeWeight(strokeWeight);
        Integer sum = 0;
        for (Integer num: numbers) {
            sum += num;
        }
        float previous = 0;
        float theta;
        float current;
        Integer num;
        for (int i = 0; i < numbers.length; i++) {
            num = numbers[i];
            if (num != 0) {
                current = ((float) num / sum) * pAppletObj.TWO_PI;
                theta = previous + (current / 2);
                displayLabel(labels[i], theta, centerX, centerY, radius, lineColor, textColor, textSize, pAppletObj);
                previous += current;
            }
        }
        pAppletObj.noStroke();
    }

    private static void displayLabel(String label, float theta, int centerX, int centerY, int radius, int lineColor, int textColor, int textSize, PApplet pAppletObj) {
        double lineLength = radius * 1.25;
        double textDistance = radius * 1.45;
        int halfRadius = radius / 2;

        int lineStartX = centerX + PApplet.floor((float) (halfRadius * Math.cos(theta)));
        int lineStartY = centerY + PApplet.floor((float) (halfRadius * Math.sin(theta)));
        int lineEndX = centerX + PApplet.floor((float) (lineLength * Math.cos(theta)));
        int lineEndY = centerY + PApplet.floor((float) (lineLength * Math.sin(theta)));

        pAppletObj.stroke(lineColor);
        pAppletObj.fill(textColor);
        pAppletObj.textSize(textSize);
        pAppletObj.line(lineStartX, lineStartY, lineEndX, lineEndY);
        int showTextAtX = centerX + PApplet.floor((float) (textDistance * Math.cos(theta)));
        int showTextAtY = centerY + PApplet.floor((float) (textDistance * Math.sin(theta)));

        pAppletObj.text(label, showTextAtX, showTextAtY);
    }


    public static boolean isInside(int centerX, int centerY, int radius, int pointX, int pointY) {
        int distanceSquared = (int) (Math.pow(centerX - pointX, 2) + Math.pow(centerY - pointY, 2));
        return distanceSquared < radius * radius;
    }
}
