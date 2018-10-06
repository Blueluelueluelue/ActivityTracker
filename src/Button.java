import processing.core.PApplet;
import processing.core.PImage;


public class Button {
    private PApplet pAppletObj;
    private int topLeftX;
    private int topLeftY;
    private int width;
    private int height;
    private int fillColor;
    private int borderColor;
    private int cornerRadius;
    private PImage buttonSprite;
    private int[] imagePixels;
    private int oppositeFillColor;

    public Button(int x1, int y1, int w, int h, PApplet pAppletObj) {
        this.pAppletObj = pAppletObj;
        topLeftX = x1;
        topLeftY = y1;
        width = w;
        height = h;
        fillColor = pAppletObj.color(220);
        oppositeFillColor = pAppletObj.color(190);
        borderColor = pAppletObj.color(0);
        cornerRadius = PApplet.floor(0.2f * (w > h ? w : h));
    }

    public void setImage(String imagePath) {
        buttonSprite = pAppletObj.loadImage(imagePath);
        buttonSprite.resize(width, height);
    }

    public Button(int x1, int y1, int x2, int y2, int fillColor, int borderColor, PApplet pAppletObj) {
        this(x1, y1, x2, y2, pAppletObj);
        this.fillColor = fillColor;
        this.borderColor = borderColor;
    }

    public void press() {
        if (buttonSprite != null && imagePixels == null) {
            imagePixels = new int[buttonSprite.pixels.length];
            buttonSprite.loadPixels();
            int first = buttonSprite.pixels[0];
            for (int i = 0; i < buttonSprite.pixels.length; i++) {
                imagePixels[i] = buttonSprite.pixels[i];
                if (buttonSprite.pixels[i] == first) {
                    int color = buttonSprite.pixels[i];
                    if (color < pAppletObj.color(128)) {
                        color = pAppletObj.color(120);
                    } else {
                        color = pAppletObj.color(180);
                    }
                    buttonSprite.pixels[i] = color;
                }
            }
            buttonSprite.updatePixels();
        } else {
            toggleButtonFill();
        }
    }

    private void swapArrays(int[] a, int[] b) {
        int temp;
        for (int i = 0; i < a.length; i++) {
            temp = a[i];
            a[i] = b[i];
            b[i] = temp;
        }
    }

    private void toggleButtonFill() {
        if (buttonSprite != null) {
            buttonSprite.loadPixels();
            swapArrays(buttonSprite.pixels, imagePixels);
            buttonSprite.updatePixels();
        } else {
            int temp = fillColor;
            fillColor = oppositeFillColor;
            oppositeFillColor = temp;
        }
    }

    public void release() {
        toggleButtonFill();
    }

    public void draw() {
        pAppletObj.pushMatrix();
        if (buttonSprite != null) {
            pAppletObj.image(buttonSprite, topLeftX, topLeftY);
        } else {
            drawRect(fillColor);
        }

        pAppletObj.popMatrix();
    }

    private void drawRect(int fill) {
        pAppletObj.pushMatrix();
        pAppletObj.fill(fill);
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
