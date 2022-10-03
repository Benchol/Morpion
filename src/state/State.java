package state;

import java.util.ArrayList;

import action.Action;

public class State {
	private int[][] state = {{0,0,0}, {0,0,0}, {0,0,0}};
	private Character[][] state_to_show = {{'!','!','!'}, {'!','!','!'}, {'!','!','!'}};
	Action currentAction = new Action();
	
	
	public void setState(int[][] state) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.setValueState(state[i][j], i, j);
			}
		}
	}
	
	public int[][] getState() {
        return this.state;
    }
	
	public int getValueInState(int i, int j) {
		return this.state[i][j];
	}
	
	public Action getCurrentAction() {
		return this.currentAction;
	}
	
	public void setCurrentAction(int row, int col) {
		this.currentAction.setAction(row, col);
	}
	
	public void setValueState(int value, int i, int j) {
    	this.state[i][j] = value;
    	
    	if (value == 2) {
    		this.state_to_show[i][j] = 'O';
//    		System.out.println("Value O => " + this.state_to_show[i][j]);
    	} else if(value == 1) {
    		this.state_to_show[i][j] = 'X';
//    		System.out.println("Value X => " + this.state_to_show[i][j]);
    	} else {
    		this.state_to_show[i][j] = ' ';
//    		System.out.println("Value vide => " + this.state_to_show[i][j]);
    	}
    }
	
	public ArrayList<Action> getAction() {
    	ArrayList<Action> actionAvailable = new ArrayList<Action>();
//    	this.actionAvailable.removeAll();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(this.state[i][j] == 0) {
                    Action action = new Action();
//                    System.out.println("ele => " + i + " : " + j + " = " + this.state[i][j]);
                    action.setAction(i, j);
                    actionAvailable.add(action);
                }
            }
        }

        return actionAvailable;
    }
	
	public ArrayList<State> getChild(boolean minimizingPlayer) {
		ArrayList<State> stateAvailable = new ArrayList<State>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if(this.state[i][j] == 0) {
					this.setValueState(minimizingPlayer ? 1 : 2, i, j);
					State tempState = new State();
					tempState.setCurrentAction(i, j);
					tempState.setState(this.state);
					stateAvailable.add(tempState);
					this.setValueState(0, i, j);
				}
			}
		}
		
		
		return stateAvailable;
	}
	
	public void showSS() {
		System.out.print("--------------------------------------------------\n");
        for(int i = 0; i < 3; i++) {
            // System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                // System.out.print(this.boards[i][j].getClass().getSimpleName());
                System.out.print("| " + "\t" + " " + "\t");
            }
            System.out.print(" |" + "\n");
            for (int j = 0; j < 3; j++) {
//                if(this.state[i][j] == 0) {
//                    System.out.print("| " + "\t" + ' ' + "\t");
//                } else {
                    System.out.print("| " + "\t" + this.state[i][j] + "\t");
//                }
            }
            System.out.print(" |\n");
            for (int j = 0; j < 3; j++) {
                System.out.print("| " + "\t" + " " + "\t");
            }
            System.out.print(" |" + "\n");
            System.out.print("--------------------------------------------------\n");
        }
	}
	public void showState() {
    	System.out.print("--------------------------------------------------\n");
        for(int i = 0; i < 3; i++) {
            // System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                // System.out.print(this.boards[i][j].getClass().getSimpleName());
                System.out.print("| " + "\t" + " " + "\t");
            }
            System.out.print(" |" + "\n");
            for (int j = 0; j < 3; j++) {
                if(this.state_to_show[i][j] == '!') {
                    System.out.print("| " + "\t" + ' ' + "\t");
                } else {
                    System.out.print("| " + "\t" + this.state_to_show[i][j] + "\t");
                }
            }
            System.out.print(" |\n");
            for (int j = 0; j < 3; j++) {
                System.out.print("| " + "\t" + " " + "\t");
            }
            System.out.print(" |" + "\n");
            System.out.print("--------------------------------------------------\n");
        }
    }
}
