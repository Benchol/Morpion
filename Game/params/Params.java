package params;

import action.Action;


public class Params {
    private Action action;
    private int score;


    public void setParams(Action bestAction, int bestScore) {
        this.action = bestAction;
        this.score = bestScore;
    }

    public Action getAction() {
        return this.action;
    }

    public int getScore() {
        return this.score;
    }
}