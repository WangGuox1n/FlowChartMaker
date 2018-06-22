package UI;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import Memento.Memento;

public class MyMouseAdapter extends MouseAdapter {
    FlowChartMaker main;
    //图形坐标
    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;
    private int xMove;
    private int yMove;

    private boolean pressed = false;   //用于判断按下的是 "正在新建图形" or "拖动图形"
    private boolean dragged = false;

    private boolean selectFlag = false;
    private Arrow arrow;


    public MyMouseAdapter(FlowChartMaker main) {
        this.main = main;
    }

    public void mousePressed(MouseEvent e) {
        main.statusBar.setText("     Mouse Pressed @:[" + e.getX() + ", " + e.getY() + "]");//设置状态提示
        main.buttonGroup.clearSelection();
        main.xStart = e.getX();
        main.yStart = e.getY();
        xStart = e.getX();
        yStart = e.getY();

        main.selectedFigure = getClickedFigure(e.getPoint());
        if (main.selectedFigure != null) {
            xMove = main.selectedFigure.getX() - xStart;
            yMove = main.selectedFigure.getY() - yStart;
            return;
        }
        pressed = true;
    }

    public void mouseDragged(MouseEvent e) {
        System.out.println("in mouseDragged");

        //main.flowChart.setColor(Color.black);
        dragged = true;

        MyTextArea.getInstance().setVisible(false);

        //拖动已存在图形
        if (main.selectedFigure != null) {
            main.selectedFigure.setX(xMove + e.getX());
            main.selectedFigure.setY(yMove + e.getY());
            main.selectedFigure.setColor(Color.black);
            main.selectedFigure.resetShape();
            pressed = false;
            main.myPanel.repaint();
            return;
        }

        //新建图形
        switch (main.figureType) {
            case RECTANGLE: //矩形
                MyRectangle myRectangle = new MyRectangle(xStart, yStart, Math.abs(xStart - e.getX()), Math.abs(yStart - e.getY()));
                main.bufferedShape = myRectangle;
                //main.bufImage = ob.draw();
                break;
            case PARALLELOGRAM://平行四边形
                MyParallelogram myParallelogram = new MyParallelogram(xStart, yStart, Math.abs(xStart - e.getX()), Math.abs(yStart - e.getY()));
                main.bufferedShape = myParallelogram;
                break;
            case ELLIPSE:  //椭圆
                MyEllipse myEllipse = new MyEllipse(xStart, yStart, Math.abs(xStart - e.getX()), Math.abs(yStart - e.getY()));
                main.bufferedShape = myEllipse;
                break;
            case RHOMBUS:  //棱形 Rhombus
                main.bufferedShape = new MyRhombus(xStart, yStart, Math.abs(xStart - e.getX()), Math.abs(yStart - e.getY()));
                break;
            default:
                break;
        }
        main.myPanel.repaint();
    }

    public void mouseReleased(MouseEvent e) {
        main.statusBar.setText("     Mouse Released @:[" + e.getX() + ", " + e.getY() + "]");
        //main.clickedElement = null;
        if (pressed == true) {

            xEnd = e.getX();
            yEnd = e.getY();
            if (Math.abs(xStart - xEnd) == 0 || Math.abs(yStart - yEnd) == 0)
                return;
            addFigure(xStart, yStart, Math.abs(xStart - xEnd), Math.abs(yStart - yEnd));

            pressed = false;
            main.figureType = FigureType.NONE;
            main.myPanel.repaint();
        }

        main.bufImage = null;
        xEnd = 0;
        yEnd = 0;
        if (dragged) {     //拖动已存在图形结束，保存新的状态
            System.out.println("in drag end");
            createMemento();
            dragged = false;
        }
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            onRightMouseClick(e);
            return;
        }
        main.statusBar.setText("     Mouse clicked @:[" + e.getX() + ", " + e.getY() + "]");
        xStart = e.getX();
        yStart = e.getY();
        dragged = false;

        Figure clickedFigure = getClickedFigure(new Point(xStart, yStart));
        //选中时变为红色
        if (clickedFigure != null) {
            clickedFigure.setColor(Color.red);
        }
        main.myPanel.repaint();

        //当前有输入文字的动作
        if (MyTextArea.getInstance().isInputTextBegin()) {
            if (MyTextArea.getInstance().getFigure() != null) {
                MyTextArea.getInstance().getFigure().setText(MyTextArea.getInstance().getText());
                System.out.println("add TEXT 1");
                createMemento();
            }
            MyTextArea.getInstance().setInputTextBegin(false);
            main.myPanel.repaint();
            return;
        }

        //双击在图形内，开始输入文字
        if (e.getClickCount() == 2) {
            Figure doubleClickFigure = getClickedFigure(new Point(xStart, yStart));
            if (doubleClickFigure != null) {
                MyTextArea.getInstance().setFigure(doubleClickFigure);
                MyTextArea.getInstance().inputText();
            }
            return;
        }

        if (main.figureType == FigureType.TEXT) {
            if (clickedFigure != null) {
                MyTextArea.getInstance().setFigure(clickedFigure);
                MyTextArea.getInstance().inputText();
                System.out.println("add TEXT 3");
                createMemento();
            }
        }
        //在两个图形间画线
        if (main.figureType == FigureType.ARROW) {
            //Figure f = getClickedFigure(new Point(xStart, yStart));
            if (null == clickedFigure)  //需要选中图形才能连线
                return;
            if (selectFlag == false) {  //还未选择图形
                arrow = new Arrow();
                arrow.setFigure_from(clickedFigure);
                selectFlag = true;
            } else {  //已经选择了一个图形
                //如果没有选择第二个图形，或者第二个图形与第一个图形为同一个图形，则不连线
                if (clickedFigure == null || clickedFigure == arrow.getFigure_from()) {
                    arrow.setFigure_from(null);
                    selectFlag = false;
                    main.figureType = FigureType.NONE;
                    return;
                }
                arrow.setFigure_goto(clickedFigure);
                main.figureList.add(arrow);
                selectFlag = false;
                main.myPanel.repaint();
                main.figureType = FigureType.NONE;
                System.out.println("add ARROW");
                createMemento();
            }
        }
    }

    public void onRightMouseClick(MouseEvent e) {
        JMenuItem mCopy, mPaste, mDel, mSetSize;
        JPopupMenu menu = new JPopupMenu();
        mCopy = new JMenuItem("复制");
        mPaste = new JMenuItem("粘贴");
        mDel = new JMenuItem("删除");
        mSetSize = new JMenuItem("设置图形大小");

        if (main.selectedFigure != null) {
            //menu.add(mCopy);
            //menu.add(mPaste);
            menu.add(mDel);
            menu.add(mSetSize);
            menu.show(main.myPanel, e.getX(), e.getY());
        }
        //menu.show(main.myPanel, e.getX(), e.getY());

        //mCopy.addActionListener(a -> main.copy());
        //mPaste.addActionListener(a -> main.paste());
        mDel.addActionListener(a -> main.delete());
        mSetSize.addActionListener(a -> {
            MyInputDialog inputDialog = new MyInputDialog(main);
            int lx = main.selectedFigure.getX() + main.selectedFigure.getWidth();
            int ly = main.selectedFigure.getY() + main.selectedFigure.getHeight();
            inputDialog.setLocation(lx, ly);
            inputDialog.setVisible(true);
        });
    }

    public void addFigure(int x1, int y1, int width, int height) {
        System.out.println("in addFigure");
        switch (main.figureType) {
            case RECTANGLE:
                main.figureList.add(new MyRectangle(x1, y1, width, height));
                break;
            case PARALLELOGRAM:
                main.figureList.add(new MyParallelogram(x1, y1, width, height));
                break;
            case ELLIPSE:
                main.figureList.add(new MyEllipse(x1, y1, width, height));
                break;
            case RHOMBUS:
                main.figureList.add(new MyRhombus(x1, y1, width, height));
                break;
            default:
                break;
        }
    }

    //返回鼠标单击选中的图形
    public Figure getClickedFigure(Point p) {
        System.out.println("in getClickedFigure");
        //从后向前遍历是为了两个图形有重叠时，先选中最外层那个 (外层的通常是后建立的)
        ArrayList<Figure> list = main.figureList.getFigureList();
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).contains(p))
                return list.get(i);
            else
                list.get(i).setColor(Color.black);
        }
        return null;
    }

    public void createMemento() {
        System.out.println("in createMemento");
        main.caretaker.add(new Memento(main.figureList.clone()));
    }
}
