package Model;

import java.util.ArrayList;

public class FigureList {
    private ArrayList<Figure> figureList = new ArrayList<>(5);

    public FigureList clone(){
        ArrayList<Integer> from_index = new ArrayList<>();
        ArrayList<Integer> goto_index = new ArrayList<>();

        FigureList cloneList = new FigureList();
        for(Figure figure :figureList){
            if(figure instanceof Arrow){
                 for(int i=0;i<figureList.size();i++){
                         if(((Arrow) figure).getFigure_from().equals(figureList.get(i)))
                             from_index.add(i);
                         else if(((Arrow) figure).getFigure_goto().equals(figureList.get(i)))
                             goto_index.add(i);
                 }
            }
            cloneList.figureList.add(figure.clone());
        }

        //在clone 连接线的时候，要把连接的两个图形重新设置。这样redo/undo操作后，图形的拖动也能带着线的拖动
        int index = 0;
        ArrayList<Figure> list = cloneList.figureList;
        for(Figure cloneArrow : cloneList.figureList){
            if(cloneArrow instanceof Arrow){
                ((Arrow) cloneArrow).setFigure_from(list.get(from_index.get(index)));
                ((Arrow) cloneArrow).setFigure_goto(list.get(goto_index.get(index)));
                index ++;
            }
        }

        return cloneList;
    }

    public ArrayList<Figure> getFigureList() {
        return figureList;
    }

    public void add(Figure figure){
        figureList.add(figure);
    }

    public void remove(Figure figure){
        if(figure != null){
            figureList.remove(figure);
        }
    }

    public void clear() {
        figureList.clear();
    }
}
