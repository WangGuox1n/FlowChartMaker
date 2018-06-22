package Model;

import UI.FlowChartMaker;

import javax.swing.*;
import java.awt.*;

public class MyInputDialog extends JDialog {
    JTextField width, height;//定义三个输入框

    public MyInputDialog(FlowChartMaker main){
        setTitle("设置图形大小");
        setModal(true);
        setSize(240, 160);//对话框的大小
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);//关闭后销毁对话框
        setLocationRelativeTo(null);
        JLabel jLabel1 = new JLabel("宽度(横向):",SwingConstants.CENTER);
        JLabel jLabel2 = new JLabel("高度(纵向):",SwingConstants.CENTER);

        jLabel1.setFont(new Font("Dialog",1,20));
        jLabel2.setFont(new Font("Dialog",1,20));
        width = new JTextField(5);
        height = new JTextField(5);
        JPanel jPanel = new JPanel(new GridLayout(2, 2));
        jPanel.add(jLabel1);
        jPanel.add(width);
        jPanel.add(jLabel2);
        jPanel.add(height);
        JButton confirm = new JButton("确认输入");
        confirm.addActionListener(e-> {
            Figure figure = main.getSelectedFigure();
            if(null == figure)
                return;
            String w = width.getText();
            String h = height.getText();
            if(w.length()==0||h.length()==0){
                return;
            }

            if(w.length() >4 || figure.getX() + Integer.parseInt(w) > 1780 || h.length() > 10){
                return;
            }
            figure.setWidth(Integer.parseInt(w));
            figure.setHeight(Integer.parseInt(h));
            main.getMyPanel().repaint();
            setVisible(false);
        });
        add(jPanel);
        add(confirm,BorderLayout.SOUTH);
    }
}