package game;

import java.util.Scanner;
import java.lang.Thread;

import action.Action;
import state.State;


public class Game {
	private State mainState = new State();
	
	private int player = 1;
	
	public boolean setTable(Character player, int row, int col) {
        if(row >= 0 && row < 3 && col >= 0 && col < 3) {
            int i = row;
            int j = col;
//             System.out.println("row = " + i);
//             System.out.println("col = " + j);
//             System.out.println("state = " + this.mainState.getState()[i][j]);
            if(this.mainState.getState()[i][j] == 0) {
                if(player == 'O') {
                	this.mainState.setValueState(2, i, j);
//                	System.out.println("set success : " + this.mainState.getState()[i][j]);
                } else {
                	this.mainState.setValueState(1, i, j);
//                	System.out.println("set success : " + this.mainState.getState()[i][j]);
                }
                return true;
            } else {
                System.out.println("\n\nVeuillez choisir un autre case car celui là est déjà rempli!!\n");
                return false;
            }
        }
        System.out.println("\n\nWarning : Veuillez entrez les bonnes positions 0 < i,j < 3\n");
        return false;
    }
	
	
	public boolean checkGameStatus(State state, int player) {   
        if(state.getState()[0][0] == player && state.getState()[0][0] == state.getState()[0][1] && state.getState()[0][0] == state.getState()[0][2]) {
        	return true;
        } else if(state.getState()[1][0] == player && state.getState()[1][0] == state.getState()[1][1] && state.getState()[1][2] == state.getState()[1][0]) {
        	return true;
        } else if(state.getState()[2][0] == player && state.getState()[2][0] == state.getState()[2][1] && state.getState()[2][0] == state.getState()[2][2]) {
        	return true;
        } else if(state.getState()[0][0] == player && state.getState()[0][0] == state.getState()[1][0] && state.getState()[0][0] == state.getState()[2][0]) {
        	return true;
        } else if(state.getState()[0][1] == player && state.getState()[0][1] == state.getState()[1][1] && state.getState()[0][1] == state.getState()[2][1]) {
        	return true;
        } else if(state.getState()[0][2] == player && state.getState()[0][2] == state.getState()[1][2] && state.getState()[0][2] == state.getState()[2][2]) {
        	return true;
        } else if(state.getState()[0][0] == player && state.getState()[0][0] == state.getState()[1][1] && state.getState()[0][0] == state.getState()[2][2]) {
        	return true;
        } else if(state.getState()[0][2] == player && state.getState()[0][2] == state.getState()[1][1] && state.getState()[0][2] == state.getState()[2][0]) {
        	return true;
        }
        
        
        
//        System.out.println("Not OKK");
        return false;
    }

    public boolean checkGameDraw(State state) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(state.getState()[i][j] == 0) {
                    count++;
                }
            }
        }
        if(count == 0) {
            return true;
        } else {
            return false;
        }
    }	
//    
    public double max(double v1, double v2) {
    	if(v1 >= v2) {
    		return v1;
    	} else {
    		return v2;
    	}
    }
    
    public double min(double v1, double v2) {
    	if(v1 <= v2) {
    		return v1;
    	} else {
    		return v2;
    	}
    }
    
    
    public double minmax(State node, boolean maximizingPlayer) {
//    	node.showState();
//    	node.showSS();
    	double value = 0;
    	if(this.checkGameStatus(node, 2)) {
//    		System.out.println("IA win");
    		return 1;
    	} else if (this.checkGameStatus(node, 1)) {
//    		System.out.println("YOU win");
    		return -1;
    	} else if (this.checkGameDraw(node)) {
//    		System.out.println("Game draw");
    		return 0;
    	}
    	
    	if(maximizingPlayer) {
    		value = Double.NEGATIVE_INFINITY;
    		
    		for (int i = 0; i < 3; i++) {
    			for (int j = 0; j < 3; j++) {
    				if(node.getState()[i][j] == 0) {
    					node.setValueState(2, i, j);
    					value = Math.max(value, minmax(node, false));
//    					value = this.max(value, minmax(node, false));
    					
    					node.setValueState(0, i, j);
    				}
    			}
    		}
    		return value;
    	} else {
    		value = Double.POSITIVE_INFINITY;
    		for (int i = 0; i < 3; i++) {
    			for (int j = 0; j < 3; j++) {
    				if(node.getState()[i][j] == 0) {
    					node.setValueState(1, i, j);
    					value = Math.min(value, minmax(node, true));
//    					value = this.min(value, minmax(node, true));
    					node.setValueState(0, i, j);
    				}
    			}
    		}
    		return value;
    	}
    	
//    	System.out.println("Score in minmax : " + value);
    	
    	
    }
	
    public Action bestAction(State board) {
    	double bestScore = Double.NEGATIVE_INFINITY;
    	Action bestAction = new Action();
    	
    	for (int i = 0; i < 3; i++) {
    		for (int j = 0; j < 3; j++) {
    			if(board.getState()[i][j] == 0) {
    				board.setValueState(2, i, j);
//    				board.showState();
//    				System.out.println("*****");
    				double score = minmax(board, false);
    				board.setValueState(0, i, j);
//    				System.out.println("Score : " + score + " | " + " Action : " + i + " : " + j);
//    				board.showState();
//    				System.out.println("score : " + score);
    				if(score >= bestScore) {
    					bestScore = score;
    					bestAction.setAction(i, j);
    				}
    			}
    		}
    	}
    	
    	return bestAction;
    }

    public void welcome() {
        System.out.println("--------------------------------------------------");
        System.out.println("| " + "\t\t\t\t\t\t " + "|");
        System.out.println("| " + "\t\t" + "JEU DE MORPION" + "\t\t\t " + "|");
        System.out.println("| " + "\t\t\t\t\t\t " + "|");
        System.out.println("| " + "\t\t" + "Bienvenue et bon jeu" + "\t\t " + "|");
        System.out.println("| " + "\t\t\t\t\t\t " + "|");
        System.out.println("| " + "\t\t" + "par Riana Ben Andriarinaivo" + "\t " + "|");
        System.out.println("| " + "\t\t\t\t\t\t " + "|");
        System.out.println("--------------------------------------------------");
        System.out.println("\n\n\n");
        System.out.println("OBJECTIF : Reussir à aligner 3 de vos pions");
        System.out.println("\n\n\n");
    }
    
    
	public void play() {
		boolean status = false;
		Action action = new Action();
		Action action_opposant = new Action();
		action.setAction(0, 0);
		State state = new State();
		
		state.showState();
		while(!status) {
			System.out.println("\n\nTour du joueur numero " + this.player);
//          state.showState();
          if(this.player == 1) {
          	
          	/*PLAYER 1 YOU*/
          	
//          	params = this.minimax(this.mainState, true, action);
              System.out.println("Entrer les positions de votre pion : ");
              Scanner sc1 = new Scanner(System.in);
              System.out.print("i = ");
              int i = sc1.nextInt();
              System.out.print("\n");
              Scanner sc2 = new Scanner(System.in);
              System.out.print("j = ");
              int j = sc2.nextInt();
          	if(this.setTable('X', i, j)) {
//              if(this.setTable('X', params.getAction().getX(), params.getAction().getY())) {
//                  this.mainState.showState();
                  if(this.checkGameStatus(this.mainState, this.player)) {
                      System.out.println("\n\nLE JOUEUR 1 A GAGNÉ, FELICITATION...\n\n");
                      status = true;
                  } else if(this.checkGameDraw(this.mainState)) {
                  	System.out.println("EGALITÉ... bravo aux deux joueurs..");
                  	status = true;
                  }
                  this.player = 2;
              }
          } else {
        	  action = this.bestAction(this.mainState);
//        	  System.out.println("best a : " + action.getX() + " : " + action.getY());
        	  if(this.setTable('O', action.getX(), action.getY())) {
//                if(this.setTable('O', i, j)) {
//                	this.mainState.showState();
                    if(this.checkGameStatus(this.mainState, this.player)) {
                        System.out.println("\n\nL' IA A GAGNÉ, FELICITATION...\n\n");
                        status = true;
                    } else if(this.checkGameDraw(this.mainState)) {
                    	System.out.println("EGALITÉ... bravo à vous!! Vous avez fait égalité avec un IA..");
                    	status = true;
                    }
                    this.player = 1;
                } else {
                	System.out.println("Can't set this state");
                }
          }
          this.mainState.showState();
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		State state = new State();
		Game game = new Game();
//		System.out.println("Final score : " + game.minmax(state, false));
		game.welcome();
		game.play();
	}

}
