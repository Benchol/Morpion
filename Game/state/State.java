package state;
import action.Action;

import java.util.ArrayList;


public class State {
    private int[][] state = {{0,0,0}, {0,0,0}, {0,0,0}};
    public ArrayList<Action> actionAvailable;

    public State() {
        this.actionAvailable = new ArrayList<Action>();
    }

    public void setState(int player, Action action) {
        this.state[action.positionx][action.positiony] = player;
    }

    public State getState() {
        return this.state;
    }

    public Action getAction() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(this.state[i][j] != 0) {
                    Action action = null;
                    action.setAction(i, j);
                    this.actionAvailable.add(action);
                }
            }
        }

        return this.actionAvailable;
    }
}