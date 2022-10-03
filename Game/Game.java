package Game;
import java.util.Scanner;

import Action.Action;
import State.State;
import Params.Params;

public class Game {
    public static final int BEST_SCORE = -200;

    private int table_morpion[][] = {{0,0,0}, {0,0,0}, {0,0,0}};
    private Character boards[][] = {{'!','!','!'}, {'!','!','!'}, {'!','!','!'}};
    private int player = 1;


    public Game() {
    }

    public boolean setTable(Character player, int row, int col) {
        if(row >= 0 && row < 3 && col >= 0 && col < 3) {
            int i = row;
            int j = col;
            // System.out.println("i = " + i);
            // System.out.println("i = " + j);
            if(this.table_morpion[i][j] == 0) {
                if(player == 'O') {
                    this.table_morpion[i][j] = 1;
                    this.boards[i][j] = player;
                } else {
                    this.table_morpion[i][j] = 2;
                    this.boards[i][j] = player;
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
                if(this.boards[i][j] == '!') {
                    System.out.print("| " + "\t" + ' ' + "\t");
                } else {
                    System.out.print("| " + "\t" + this.boards[i][j] + "\t");
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

    public boolean checkGameStatus(int player) {
        if(this.table_morpion[0][0] == player) {
            if(this.table_morpion[0][1] == player && this.table_morpion[0][2] == player) {
                return true;
            } else if(this.table_morpion[1][0] == player && this.table_morpion[2][0] == player) {
                return true;
            } else if(this.table_morpion[1][1] == player && this.table_morpion[2][2] == player) {
                return true;
            }
        } else if(this.table_morpion[1][1] == player) {
            if(this.table_morpion[0][1] == player && this.table_morpion[2][1] == player) {
                return true;
            } else if(this.table_morpion[1][0] == player && this.table_morpion[1][2] == player) {
                return true;
            } else if(this.table_morpion[2][0] == player && this.table_morpion[0][2] == player) {
                return true;
            }
        } else if(this.table_morpion[2][2] == player) {
            if(this.table_morpion[1][2] == player && this.table_morpion[0][2] == player) {
                return true;
            } else if(this.table_morpion[2][1] == player && this.table_morpion[2][0] == player) {
                return true;
            }
        } 
        return false;
    }

    public boolean checkGameDraw() {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(this.table_morpion[i][j] == 0) {
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

    public Params minimax(State state, boolean player, Action action) {
        Action bestAction = null;
        Params bestParams = null;
        int bestScore = -200;
        int player_test = 1;

        if (checkGameDraw()) {
            if(player) {
                player_test = 1;
            } else {
                player_test = 2;
            }
            if(checkGameStatus(player_test)) {
                Params bestParams = null;
                bestAction.setAction(action.getX(), action.getY());
                bestParams.setAction(bestAction);
                bestParams.setScore(1);
                return bestParams;
            } else if(!checkGameStatus(player_test)) {
                Params bestParams = null;
                bestAction.setAction(action.getX(), action.getY());
                bestParams.setAction(bestAction);
                bestParams.setScore(-1);
                return bestParams;
            }
            Params bestParams = null;
            bestAction.setAction(action.getX(), action.getY());
            bestParams.setAction(bestAction);
            bestParams.setScore(0);
            return bestParams;
        }


        for (Action action: state.getAction()) {

            setState(action, player);

            bestParams = minimax(state, !player, action);

            if(player) {
                if(bestParams.getScore() > bestScore) {
                    bestScore = bestParams.getScore();
                    bestAction.setAction(bestParams.getAction().getX(), bestParams.getAction().getY());
                } else {
                    bestAction.setAction(bestParams.getAction().getX(), bestParams.getAction().getY());
                }
            } else {
                if(bestParams.getScore() < bestScore) {
                    bestScore = bestParams.getScore();
                    bestAction.setAction(bestParams.getAction().getX(), bestParams.getAction().getY());
                } else {
                    bestAction.setAction(bestParams.getAction().getX(), bestParams.getAction().getY());
                }
            }
        }

        bestParams.setAction(action);
        bestParams.setScore(bestScore);

        return bestParams;
    }

    public void play() {
        boolean status = false;

        while(!status) {
            System.out.println("\n\nTour du joueur numero " + this.player);
            if(this.player == 1) {
                System.out.println("Entrer les positions de votre pion : ");
                Scanner sc1 = new Scanner(System.in);
                System.out.print("i = ");
                int i = sc1.nextInt();
                System.out.print("\n");
                Scanner sc2 = new Scanner(System.in);
                System.out.print("j = ");
                int j = sc2.nextInt();
                if(this.setTable('O', i, j)) {
                    this.showState();
                    if(this.checkGameStatus(this.player)) {
                        System.out.println("\n\nLE JOUEUR 1 A GAGNÉ, FELICITATION...\n\n");
                        status = true;
                    }
                    this.player = 2;
                }
            } else {
                System.out.println("Entrer les positions de votre pion : ");
                Scanner sc1 = new Scanner(System.in);
                System.out.print("i = ");
                int i = sc1.nextInt();
                System.out.print("\n");
                Scanner sc2 = new Scanner(System.in);
                System.out.print("j = ");
                int j = sc2.nextInt();
                if(this.setTable('X', i, j)) {
                    this.showState();
                    if(this.checkGameStatus(this.player)) {
                        System.out.println("\n\nLE JOUEUR 2 A GAGNÉ, FELICITATION...\n\n");
                        status = true;
                    }
                    this.player = 1;
                }
            }

        }
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

    public static void main(String[] args) {
        Game game = new Game();
        game.welcome();
        game.showState();
        game.play();
    }
}