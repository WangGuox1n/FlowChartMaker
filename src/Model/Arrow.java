package Model;

import java.awt.*;

public class Arrow extends Figure {
    private Figure figure_from;
    private Figure figure_goto;
    private Polygon path;
    private Polygon arrow;


    public Arrow() {
        super();
        path = new Polygon();
        text = "";
    }

/*    @Override
    public Shape getShape() {
        path = new Polygon();
        path.addPoint(figure_from.getX() + figure_from.getWidth() / 2, figure_from.getY() + figure_from.getHeight());
        path.addPoint(figure_goto.getX() + figure_goto.getWidth() / 2, figure_goto.getY());
        return path;
        // return super.getShape();
    }*/

    public Shape getPath() {
        path = new Polygon();
        path.addPoint(figure_from.getX() + figure_from.getWidth() / 2, figure_from.getY() + figure_from.getHeight());
        path.addPoint(figure_goto.getX() + figure_goto.getWidth() / 2, figure_goto.getY());
        return path;
    }

    public Polygon getArrow() {
        arrow = new Polygon();
        Point point_from = new Point(figure_from.getX() + figure_from.getWidth() / 2, figure_from.getY() + figure_from.getHeight());
        Point point_goto = new Point(figure_goto.getX() + figure_goto.getWidth() / 2, figure_goto.getY());

        int arrowH = 16, arrowW = 8;
        double dx = point_goto.getX() - point_from.getX();
        double dy = point_goto.getY() - point_from.getY();
        double length = Math.sqrt(dx * dx + dy * dy);
        double sin = dy / length, cos = dx / length;

        //center 是三角形与连线相交的点
        double centerX = point_from.getX() + (length - arrowH) * cos;
        double centerY = point_from.getY() + (length - arrowH) * sin;

        //dx,xy 是三角形的两个底角与中心点的坐标差
        dx = arrowW * sin;
        dy = arrowW * cos;

        arrow.addPoint((int) (point_goto.getX()), (int) (point_goto.getY()));
        arrow.addPoint((int) (centerX + dx), (int) (centerY - dy));
        arrow.addPoint((int) (centerX - dx), (int) (centerY + dy));

        return arrow;
    }

    public void setFigure_from(Figure figure_from) {
        this.figure_from = figure_from;
    }

    public void setFigure_goto(Figure figure_goto) {
        this.figure_goto = figure_goto;
    }

    public Figure getFigure_from() {
        return figure_from;
    }

    public Figure getFigure_goto() {
        return figure_goto;
    }

    public boolean contains(Point p) {
        return false;
    }

    public Arrow clone(){
        Arrow cloneArrow = new Arrow();
        cloneArrow.figure_from = this.figure_from.clone();
        cloneArrow.figure_goto = this.figure_goto.clone();
        return cloneArrow;
    }
}
