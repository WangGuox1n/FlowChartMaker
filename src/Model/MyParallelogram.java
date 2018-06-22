package Model;

import java.awt.*;

public class MyParallelogram extends Figure{

    public MyParallelogram(int x, int y, int width, int height) {
        super(x,y,width,height);
        resetShape();
    }

    public void resetShape() {
        Polygon parallelogram = new Polygon();
        parallelogram.addPoint(x + height / 4, y);
        parallelogram.addPoint(x + height / 4 + width, y);
        parallelogram.addPoint(x + width, y + height);
        parallelogram.addPoint(x, y + height);
        shape = parallelogram;

        inputTextX = x + height / 4 + 5;
        inputTextY = y + 10;
        setStringLocation();
    }

    public MyParallelogram clone() {
        MyParallelogram copy = new MyParallelogram(x, y, width, height);
        copy.setText(this.text);
        copy.resetShape();
        return copy;
    }
}
