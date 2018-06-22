package UI;

import Model.Arrow;
import Model.Figure;
import Model.MyRectangle;
import Model.MyTextArea;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class MyPanel extends JPanel {
    private FlowChartMaker main;

    public MyPanel(FlowChartMaker main) {
        super();
        this.main = main;
        setLayout(null);
        setPreferredSize(new Dimension(main.frame.getWidth(), main.frame.getHeight()));
    }


    public void paintComponent(Graphics g) {
        Graphics2D d2 = null;
        super.paintComponent(g);
        if (main.image != null) {
            d2 = (Graphics2D) main.image.createGraphics();
            d2.setColor(Color.gray);
            d2.drawRect(0, 0, main.image.getWidth() - 1, main.image.getHeight() - 1);
            d2.drawString("FlowChartEditor", 2, 15);
            d2.setColor(Color.black);
            drawShapes(d2, g);
            d2.drawImage(main.image, 0, 0, this);
        }

        d2 = (Graphics2D) g;
        //绘制网格
        drawMesh(d2);
        d2.setColor(Color.BLACK);
        if(main.bkgImage != null)
            g.drawImage(main.bkgImage,0,0,this.getWidth(),900,this);
        //设置线条宽度
        d2.setStroke(new BasicStroke(3));
        drawResize(d2, g);
        drawShapes(d2, g);
    }

    public void drawMesh(Graphics2D d2) {
        d2.setColor(Color.gray);
        for (int j = 20; j < this.getWidth(); j += 20)
            for (int k = 20; k < this.getHeight(); k += 20)
                d2.draw(new Ellipse2D.Float(j, k, 1, 1));
    }

    public void drawResize(Graphics2D d2, Graphics g) {
        /*if (main.bufImage != null) {
            for (Shape im : main.bufImage) {
                d2.setColor(Color.white);
                d2.fill(im);
                d2.setColor(Color.red);
                d2.draw(im);
            }
        }*/
        if (main.bufferedShape != null) {
            d2.setColor(Color.white);
            d2.fill(main.bufferedShape.getShape());
            d2.setColor(Color.red);
            d2.draw(main.bufferedShape.getShape());
        }
        main.bufferedShape = null;
    }

    public void drawShapes(Graphics2D d2, Graphics g) {
        for (Figure i : main.figureList.getFigureList()) {
            i.resetShape();
            if (i instanceof Arrow) {
                d2.setColor(i.getColor());
                d2.draw(((Arrow) i).getPath());
                d2.fillPolygon(((Arrow) i).getArrow());
            } else {
                d2.setColor(Color.WHITE);
                d2.fill(i.getShape());
                d2.setColor(i.getColor());
                d2.draw(i.getShape());
                d2.setFont(MyTextArea.getInstance().getFont());
                ArrayList<String> strings = i.getStrings();
                int y = i.getStringY();
                for (String str : strings) {
                    d2.drawString(str, i.getStringX(), y);
                    y += MyTextArea.getInstance().getTextSize();
                }
            }
        }
    }
}
