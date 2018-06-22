package Model;

import java.awt.geom.Rectangle2D;

public class MyRectangle extends Figure {

    public MyRectangle(int x, int y, int width, int height) {
        super(x, y, width, height);
        resetShape();
    }

    public void resetShape() {
        shape = new Rectangle2D.Float(x, y, width, height);
        inputTextX = x + 10;
        inputTextY = y + 10;
        setStringLocation();
    }

    public MyRectangle clone() {
        MyRectangle copy = new MyRectangle(x, y, width, height);
        copy.setText(this.text);
        copy.resetShape();
        return copy;
    }
}
