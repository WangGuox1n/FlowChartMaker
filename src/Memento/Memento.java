package Memento;

import Model.FigureList;

public class Memento {
    final FigureList  state; //状态即所绘制图形的集合
    public Memento(FigureList state) {
        this.state = state;
    }
    public FigureList getState(){
        return state;
    }
}
