package UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MenuListener;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class MenuBar extends JMenuBar {

    private FlowChartMaker main;

    public MenuBar(FlowChartMaker main) {
        super();
        this.main = main;
        construct();
    }

    public void construct() {
        this.setBounds(0, 0, main.frame.getWidth(), 30);

        JMenu fileMenu = new JMenu("文件");

        Action newFile = new AbstractAction("新建") {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.getFigureList().clear();
                main.myPanel.repaint();
            }
        };
        newFile.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));

        Action openFile = new AbstractAction("打开") {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.openFile();
            }
        };
        openFile.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK));

        Action saveFile = new AbstractAction("保存") {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.saveFile();
            }
        };
        saveFile.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));

        Action exit = new AbstractAction("退出"){
            public void actionPerformed(ActionEvent event) {
                main.frame.dispatchEvent(new WindowEvent(main.frame, WindowEvent.WINDOW_CLOSING));
            }
        };
        exit.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));

        fileMenu.add(new JMenuItem(newFile));
        fileMenu.add(new JMenuItem(openFile));
        fileMenu.add(new JMenuItem(saveFile));
        fileMenu.add(new JMenuItem(exit));


        JMenu editMenu = new JMenu("操作");
        Action undo = new AbstractAction("撤销") {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.undo();
            }
        };
        undo.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));

        Action redo = new AbstractAction("恢复撤销") {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.redo();
            }
        };
        redo.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK));

        Action delete = new AbstractAction("删除") {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.delete();
            }
        };
        delete.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0));

        Action copy = new AbstractAction("复制") {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.copy();
            }
        };
        copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));

        Action paste = new AbstractAction("粘贴") {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.paste();
            }
        };
        paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));

        editMenu.add(new JMenuItem(undo));
        editMenu.add(new JMenuItem(redo));
        editMenu.add(new JMenuItem(delete));
        editMenu.add(new JMenuItem(copy));
        editMenu.add(new JMenuItem(paste));

        JMenu configureMenu = new JMenu("设置");
        Action setBkg = new AbstractAction("设置背景图片") {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.setBkgImage();
            }
        };
        Action clearBkg = new AbstractAction("清除背景图片") {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.clearBkgImage();
            }
        };

        configureMenu.add(new JMenuItem(setBkg));
        configureMenu.add(new JMenuItem(clearBkg));

        JMenu helpMenu = new JMenu("帮助");
        Action help = new AbstractAction("操作帮助") {
            @Override
            public void actionPerformed(ActionEvent e) {
                UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("宋体", Font.PLAIN, 20)));
                JOptionPane.showMessageDialog(null,
                        "1. 点击左侧图形，然后在绘图区中左键点击向右下方拖拽，即可绘制图形；\n" +
                                "2. 点击左侧的连接线，然后依次单击两个图形，即可连接；\n" +
                                "3. 单击选中某个图形，然后可以进行复制、删除操作；\n" +
                                "4. 双击某个图形，会出现文字输入框，可以添加文字描述；\n" +
                                "5. 选择字体类型、字体大小、粗/斜体后，在绘图区中点击一下，可以看到字体的变化。\n\n" +
                                "copyright@王国新  958804019@qq.com",
                        "操作帮助",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        };
        helpMenu.add(new JMenuItem(help));


        this.add(fileMenu);
        this.add(configureMenu);
        this.add(editMenu);
        this.add(helpMenu);
    }
}
