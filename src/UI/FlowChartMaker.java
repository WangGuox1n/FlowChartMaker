package UI;

import Memento.*;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class FlowChartMaker {
    JFrame frame;
    MyPanel myPanel;

    JLabel statusBar;
    ButtonGroup buttonGroup = new ButtonGroup();  //按钮集合

    Figure selectedFigure;
    Figure copyFigure;
    FigureType figureType = FigureType.NONE;     //选中图形的类型
    BufferedImage image = null;                  //用于拖拽过程中的缓冲图像
    ArrayList<Shape> bufImage = null;

    Caretaker caretaker = new Caretaker();
    int xStart, yStart;

    Figure bufferedShape = null;
    FigureList figureList = new FigureList();
    Image bkgImage;

    public FlowChartMaker() {
        construct();
        MyTextArea.getInstance().setMain(this);
    }

    private void construct() {
        frame = new JFrame("简易流程图绘制");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //添加菜单栏、工具栏
        frame.setJMenuBar(new MenuBar(this));
        frame.getContentPane().add(new ToolBar_Horizontal(this), BorderLayout.NORTH);
        frame.getContentPane().add(new ToolBar_Vertical(this), BorderLayout.WEST);

        statusBar = new JLabel();
        frame.getContentPane().add(statusBar, BorderLayout.SOUTH);

        setMyPanel();
        frame.setSize(400, 700);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void setMyPanel() {
        myPanel = new MyPanel(this);
        myPanel.setBackground(Color.white);
        myPanel.add(MyTextArea.getInstance().getjTextArea());
        MyMouseAdapter mouseAdapter = new MyMouseAdapter(this);
        myPanel.addMouseListener(mouseAdapter);
        myPanel.addMouseMotionListener(mouseAdapter);
        myPanel.setPreferredSize(new Dimension(1500, 4000));
        JScrollPane scrollPane = new JScrollPane(myPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.pack();
    }

    public MyPanel getMyPanel() {
        return myPanel;
    }

    public Caretaker getCaretaker() {
        return caretaker;
    }

    public FigureList getFigureList() {
        return figureList;
    }

    public Figure getSelectedFigure() {
        return selectedFigure;
    }

    public void setBkgImage() {
        FileDialog fd = new FileDialog(frame, "选择回放文件", FileDialog.LOAD);
        fd.setLocationRelativeTo(null);
        fd.setVisible(true);
        if (fd.getDirectory() == null || fd.getFile() == null) {
            return;
        }
        String filename = fd.getDirectory() + fd.getFile();
        bkgImage = new ImageIcon(filename).getImage();
        myPanel.repaint();
    }

    public void clearBkgImage() {
        bkgImage = null;
        myPanel.repaint();
    }

    public void copy() {
        if (null == selectedFigure) {
            return;
        }
        copyFigure = selectedFigure.clone();
        copyFigure.setX(copyFigure.getX() + 30);
        copyFigure.setY(copyFigure.getY() + 30);
        //myPanel.repaint();
    }

    public void paste() {
        if (null == copyFigure)
            return;
        if (selectedFigure == copyFigure) {   //重复复制
            copyFigure = selectedFigure.clone();
            copyFigure.setX(copyFigure.getX() + 30);
            copyFigure.setY(copyFigure.getY() + 30);
        }
        figureList.add(copyFigure);
        selectedFigure.setColor(Color.black);
        copyFigure.setColor(Color.red);
        selectedFigure = copyFigure;
        myPanel.repaint();
    }

    public void undo() {
        figureList = caretaker.Undo().clone();
        selectedFigure = null;
        myPanel.repaint();
    }

    public void redo() {
        figureList = caretaker.Redo();
        selectedFigure = null;
        myPanel.repaint();
    }

    public void delete() {
        ArrayList<Figure> list = figureList.getFigureList();
        for (Figure figure : list)
            if (selectedFigure == figure) {
                caretaker.add(new Memento(figureList.clone()));   //保存删除前的状态
                ArrayList<Figure> del_arrows = new ArrayList<>(); //同时删除与之相连的所有线
                for(Figure arrow:list){
                    if(arrow instanceof Arrow){
                        if(((Arrow) arrow).getFigure_from().equals(selectedFigure) ||
                                ((Arrow) arrow).getFigure_goto().equals(selectedFigure)) {
                            del_arrows.add(arrow);
                        }
                    }
                }
                figureList.remove(selectedFigure);
                for(Figure arrow:del_arrows){
                    figureList.remove(arrow);
                }
                caretaker.add(new Memento(figureList.clone())); //保存删除后的状态
                break;
            }
        myPanel.repaint();
    }

    public void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showSaveDialog(frame);
        if (result == JFileChooser.CANCEL_OPTION) {
            return;
        }
        File fileName = fileChooser.getSelectedFile();

        if (fileName == null || fileName.getName().equals("")) {
            JOptionPane.showMessageDialog(fileChooser, "无效的文件名",
                    "无效的文件名", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                FileOutputStream fos = new FileOutputStream(fileName);
                ObjectOutputStream output = new ObjectOutputStream(fos);
                output.writeInt(figureList.getFigureList().size());
                ArrayList<Figure> list = figureList.getFigureList();
                for (Figure figure : list) {
                    output.writeObject(figure);
                    output.flush();
                }
                output.close();
                fos.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.CANCEL_OPTION) {
            return;
        }
        File fileName = fileChooser.getSelectedFile();
        if (fileName == null || fileName.getName().equals("")) {
            JOptionPane.showMessageDialog(fileChooser, "无效的文件名",
                    "无效的文件名", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                FileInputStream fis = new FileInputStream(fileName);
                ObjectInputStream input = new ObjectInputStream(fis);
                int count = input.readInt();
                figureList.clear();
                while (count > 0) {
                    Figure figure = (Figure) input.readObject();
                    figureList.add(figure);
                    count --;
                }
                input.close();
                myPanel.repaint();
            } catch (EOFException endOfFileException) {
                JOptionPane.showMessageDialog(frame, "文件无内容",
                        "提示信息", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException classNotFoundException) {
                JOptionPane.showMessageDialog(frame, "文件内容错误",
                        "提示信息", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(frame, "文件读取失败",
                        "提示信息", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
