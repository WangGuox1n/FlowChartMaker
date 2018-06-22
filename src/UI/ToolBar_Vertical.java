package UI;

import Model.FigureType;

import javax.swing.*;

public class ToolBar_Vertical extends JToolBar{
    FlowChartMaker main;

    public ToolBar_Vertical(FlowChartMaker main) {
        super("ToolbarVertical", JToolBar.VERTICAL);
        this.main = main;
        construct();
    }

    public void construct() {
        this.setFloatable(false);

/*		JLabel fc = new JLabel("图形:");

		fc.setSize(100, 20);
		// fc.setEditable(false);
		this.add(fc);*/
        this.addSeparator();
        JToggleButton ellipseButton       = new JToggleButton(new ImageIcon("images/ellipse.png"));
        JToggleButton parallelogramButton = new JToggleButton(new ImageIcon("images/parallelogram.png"));
        JToggleButton rectangleButton     = new JToggleButton(new ImageIcon("images/rectangle.png"));
        JToggleButton rhombusButton       = new JToggleButton(new ImageIcon("images/rhombus.png"));
        JToggleButton arrowButton         = new JToggleButton(new ImageIcon("images/association.png"));

        ellipseButton.addActionListener(e ->  main.figureType = FigureType.ELLIPSE );
        parallelogramButton.addActionListener(e ->  main.figureType = FigureType.PARALLELOGRAM );
        rectangleButton.addActionListener(e ->  main.figureType = FigureType.RECTANGLE );
        rhombusButton.addActionListener(e ->  main.figureType = FigureType.RHOMBUS );
        arrowButton.addActionListener(e ->  main.figureType = FigureType.ARROW );

        main.buttonGroup.add(ellipseButton);
        main.buttonGroup.add(rectangleButton);
        main.buttonGroup.add(rhombusButton);
        main.buttonGroup.add(parallelogramButton);
        main.buttonGroup.add(arrowButton);

        this.add(ellipseButton);
        this.add(parallelogramButton);
        this.add(rectangleButton);
        this.add(rhombusButton);
        this.add(arrowButton);
    }
}
