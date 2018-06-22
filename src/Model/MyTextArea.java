package Model;

import Memento.Memento;
import UI.FlowChartMaker;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyTextArea {
    static MyTextArea myTextArea = null;
    Figure figure;
    JTextArea jTextArea;
    FlowChartMaker main;
    boolean inputTextBegin = false;
    Font font;
    boolean bold, italic;
    String textStyle = "宋体";
    int textSize = 20;

    public static MyTextArea getInstance() {
        if (null == myTextArea) {
            myTextArea = new MyTextArea();
        }
        return myTextArea;
    }

    private MyTextArea() {
        jTextArea = new JTextArea();
        jTextArea.setVisible(true);
        jTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    jTextArea.setVisible(false);
                    figure.setText(jTextArea.getText());
                    inputTextBegin = false;
                    main.getMyPanel().repaint();
                    main.getCaretaker().add(new Memento(main.getFigureList().clone()));
                }
            }
        });

        font = new Font("宋体", Font.PLAIN, 20);
    }

    public void setMain(FlowChartMaker main) {
        this.main = main;
    }

    public void inputText() {
        //在图形中才能添加文字
        if (null == figure)
            return;
        inputTextBegin = true;
        jTextArea.setOpaque(true);
        jTextArea.setText(figure.getText());
        figure.setText("");
        jTextArea.setEditable(true);
        jTextArea.setBounds(figure.getInputTextX(), figure.getInputTextY(),
                figure.getWidth() - 20, 40);
        Border border = BorderFactory.createDashedBorder(Color.gray, 1, 1);
        jTextArea.setBorder(border);

        jTextArea.setLineWrap(true); //自动换行
        //jTextArea.setWrapStyleWord(true);

        jTextArea.setVisible(true);
        if (main == null) {
            System.out.println("main =null");
        }
        if (main.getMyPanel() == null) {
            System.out.println("getMyPanel =null");
        }
        main.getMyPanel().repaint();
    }

    public String getText() {
        return jTextArea.getText();
    }

    public JTextArea getjTextArea() {
        return jTextArea;
    }

    public boolean isInputTextBegin() {
        return inputTextBegin;
    }

    public void setInputTextBegin(boolean inputTextBegin) {
        this.inputTextBegin = inputTextBegin;
        jTextArea.setVisible(false);
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public Figure getFigure() {
        return figure;
    }

    public void setVisible(boolean aFlag) {
        jTextArea.setVisible(aFlag);
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Font getFont() {
        return font;
    }

    public int getTextSize() {
        return font.getSize() * 4 / 3;
/*        int px[] = {8, 9, 10, 12, 14, 16, 18, 20, 21, 22, 24, 26, 28, 36, 48};
        int pt[] = {6, 7, 8, 9, 10, 12, 13, 14, 15, 16, 18, 20, 22, 27, 36};
        int size = font.getSize();
        for (int i = 0; i < pt.length; i++) {
            if (size == pt[i])
                return px[i];
        }
        return 14;*/
    }

    public void resetFont() {
        if (bold && italic)
            font = new Font(textStyle, Font.BOLD + Font.ITALIC, textSize);
        else if (bold)
            font = new Font(textStyle, Font.BOLD, textSize);
        else if (italic)
            font = new Font(textStyle, Font.ITALIC, textSize);
        else
            font = new Font(textStyle, Font.PLAIN, textSize);
    }

    public boolean getBold(){
        return bold;
    }

    public boolean getItalic(){
        return italic;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
        resetFont();
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
        resetFont();
    }

    public void setTextStyle(String textStyle) {
        this.textStyle = textStyle;
        resetFont();
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        resetFont();
    }
}
