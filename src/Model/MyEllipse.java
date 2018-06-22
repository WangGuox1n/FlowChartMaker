package Model;

import java.awt.geom.Ellipse2D;

public class MyEllipse extends Figure{

    public MyEllipse(int x, int y, int width, int height) {
        super(x,y,width,height);
        resetShape();
    }

    public void resetShape() {
        shape = new Ellipse2D.Float(x, y, width, height);
        inputTextX = x + 10;
        inputTextY = y + height/2 - 5;
        setStringLocation();
    }

    public MyEllipse clone() {
        MyEllipse copy = new MyEllipse(x, y, width, height);
        copy.setText(this.text);
        copy.resetShape();
        return copy;
    }
}
