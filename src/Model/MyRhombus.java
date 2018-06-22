package Model;

import java.awt.*;

public class MyRhombus extends Figure{

    public MyRhombus(int x, int y, int width, int height) {
        super(x,y,width,height);
        resetShape();
    }

    public void resetShape() {
        Polygon rhombus = new Polygon();
        rhombus.addPoint(x + width / 2, y);            //上
        rhombus.addPoint(x, y + height / 2);           //左
        rhombus.addPoint(x + width / 2, y + height);//下
        rhombus.addPoint(x + width, y + height / 2);//右
        shape = rhombus;

        inputTextX = x + 10;
        inputTextY = y + height / 2 - 5;
        setStringLocation();
    }

    public MyRhombus clone() {
        MyRhombus copy = new MyRhombus(x, y, width, height);
        copy.setText(this.text);
        copy.resetShape();
        return copy;
    }
}
