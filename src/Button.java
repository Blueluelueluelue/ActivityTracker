import processing.core.PApplet;

public class Button {
    private PApplet pAppletObj;
    private int topLeftX;
    private int topLeftY;
    private int width;
    private int height;
    private int fillColor;
    private int borderColor;
    private int cornerRadius;

    public Button(int x1, int y1, int w, int h, PApplet pAppletObj) {
        this.pAppletObj = pAppletObj;
        topLeftX = x1;
        topLeftY = y1;
        width = w;
        height = h;
        fillColor = pAppletObj.color(220);
        borderColor = pAppletObj.color(0);
        cornerRadius = PApplet.floor(0.2f * (w > h ? w : h));
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
        pAppletObj.rect(topLeftX, topLeftY, width, height, cornerRadius);
        pAppletObj.popMatrix();
    }

    public boolean isInside(int pointX, int pointY) {
        boolean xIsInside = pointX > topLeftX && pointX < topLeftX + width;
        boolean yIsInside = pointY > topLeftY && pointY < topLeftY + height;
        return xIsInside && yIsInside;
    }
}
