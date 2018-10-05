import processing.core.PApplet;

public class Button {
    private PApplet pAppletObj;
    private int topLeftX;
    private int topLeftY;
    private int bottomRightX;
    private int bottomRightY;
    private int fillColor;
    private int borderColor;
    private int curve;

    public Button(int x1, int y1, int x2, int y2, PApplet pAppletObj) {
        this.pAppletObj = pAppletObj;
        topLeftX = x1;
        topLeftY = y1;
        bottomRightX = x2;
        bottomRightY = y2;
        fillColor = pAppletObj.color(230);
        borderColor = pAppletObj.color(60);
        curve = 10;
    }

    public Button(int x1, int y1, int x2, int y2, int fillColor, int borderColor, PApplet pAppletObj) {
        this(x1, y1, x2, y2, pAppletObj);
        this.fillColor = fillColor;
        this.borderColor = borderColor;
    }

    public void draw() {
        pAppletObj.pushMatrix();
        pAppletObj.fill(fillColor);
        pAppletObj.stroke(borderColor);
        pAppletObj.rect(topLeftX, topLeftY, bottomRightX, bottomRightY, curve);
        pAppletObj.popMatrix();
    }
}
