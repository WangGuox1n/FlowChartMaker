package Model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Figure implements Serializable {
    protected Shape shape;
    protected Color color = Color.black;
    protected String text;
    protected int x, y, width, height;
    protected int inputTextX, inputTextY;    //输入框的位置
    protected int stringX, stringY;          //文字展示的位置
    protected ArrayList<String> strings = new ArrayList<>(); //

    public Figure() {
    }

    public Figure(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        text = "";
    }

    public void setStringLocation(){
        //文字显示坐标计算、 文字换行
        strings.clear();
        int textSize = MyTextArea.getInstance().getFont().getSize();
        int textLength = text.length() * textSize;

        if (textLength > width) {
            int charCount = (width - 20) / textSize;
            int index = 0, lineCount = 0;

            while (index + charCount < text.length()) {
                System.out.println(index+" "+charCount+" "+text.substring(index, index + charCount));
                strings.add(text.substring(index, index + charCount));
                index = index + charCount;
                lineCount++;
            }
            if (index < text.length()) {
                strings.add(text.substring(index));
                lineCount++;
            }
            stringX = x + 10;
            stringY = y + height / 2  - lineCount * textSize / 2;
        }
        else{
            strings.add(text);
            if(this instanceof MyRhombus){
                stringX = x + width / 2 - textLength / 2 + 10;
                stringY = y + height / 2 - textSize / 2;
            }else{
                stringX = x + width / 2 - textLength / 2;
                stringY = y + height / 2 - textSize / 2;
            }
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Shape getShape() {
        return shape;
    }

    public void resetShape() {
    }

    public boolean contains(Point p) {
        if (shape.contains(p)) {
            color = Color.red;
            return true;
        } else
            return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setInputTextX(int inputTextX) {
        this.inputTextX = inputTextX;
    }

    public void setInputTextY(int inputTextY) {
        this.inputTextY = inputTextY;
    }

    public void setStringX(int stringX) {
        this.stringX = stringX;
    }

    public void setStringY(int stringY) {
        this.stringY = stringY;
    }

    public int getInputTextX() {
        return inputTextX;
    }

    public int getInputTextY() {
        return inputTextY;
    }

    public int getStringX() {
        return stringX;
    }

    public int getStringY() {
        return stringY;
    }

    public String getText() {
        return text;
    }

    public ArrayList<String> getStrings() {
        return strings;
    }

    public abstract Figure clone();
}
