package Memento;

import Model.FigureList;

import java.util.ArrayList;

public class Caretaker {
    public ArrayList<Memento> states;
    public int currentState = 0;

    public Caretaker() {
        states = new ArrayList<Memento>(5);
        states.add(new Memento(new FigureList()));
    }

    public void add(Memento state) {
        currentState++;
        for(int i = states.size()-1;i>=currentState;i--)
            states.remove(i);
        states.add(state);

    }

    public FigureList Undo() {
        states.get(0).getState().clear();
        if (currentState > 0) {
            currentState--;
            return states.get(currentState).getState();
        } else
            return states.get(0).getState();

    }
    public FigureList Redo() {
        if (currentState < states.size()-1) {
            currentState++;
            return states.get(currentState).getState();
        } else {
            return states.get(states.size()-1).getState();
        }
    }
}
