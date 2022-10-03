package action;

public class Action {
	private int positionx = 0;
    private int positiony = 0;

    // public Action getAction() {
    //     return i, j;
    // }


    public void setAction(int i, int j) {
        this.positionx = i;
        this.positiony = j;
    }

    public int getX() {
        return this.positionx;
    }

    public int getY() {
        return this.positiony;
    }
}
