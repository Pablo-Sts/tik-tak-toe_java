import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;

public class Game {
    private final Dictionary<String, Character> cells;
    private char currentPlayer;
    private final ArrayList<String> xChoices, oChoices;
    private final String[][] victoriesCases;
    private boolean endGame, draw;

    public Game() {
        this.cells = new Hashtable<>();
        this.currentPlayer = 'X';
        this.xChoices = new ArrayList<>();
        this.oChoices = new ArrayList<>();
        this.victoriesCases = new String[][]{{"A1", "A2", "A3"}, {"A1", "B1", "C1"},
                {"A1", "B2", "C3"}, {"A2", "B2", "C2"},
                {"A3", "B3", "C3"}, {"B1", "B2", "B3"},
                {"C1", "C2", "C3"}, {"A3", "B2", "C1"}};
        this.endGame = false;
        this.draw = false;
    }


    private void setCells() {
        for (int i = 0; i < 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (i == 1) {
                    this.cells.put("B" + j, '-');
                } else if (i > 1) {
                    this.cells.put("C" + j, '-');
                }else {
                    this.cells.put("A" + j, '-');
                }
            }
        }
    }


    private boolean setCell(String cell) {
        try {
            if (this.cells.get(cell) == '-') {
                this.cells.put(cell, this.currentPlayer);
                return true;
            }
        } catch (NullPointerException e) {
            System.out.println("Invalid cell!");
        }
        return false;
    }

    private void changePlayer() {
        this.currentPlayer = this.currentPlayer == 'X' ? 'O' : 'X';
    }


    private void generateBoard() {
        System.out.printf("  A B C\n1 %c %c %c\n2 %c %c %c\n3 %c %c %c\n",
                this.cells.get("A1"), this.cells.get("B1"), this.cells.get("C1"),
                this.cells.get("A2"), this.cells.get("B2"), this.cells.get("C2"),
                this.cells.get("A3"), this.cells.get("B3"), this.cells.get("C3"));
    }

    private void checkVictory(ArrayList<String> playerChoices) {
        int cont;
        for (String[] victoryCase : this.victoriesCases) {
            cont = 0;
            for (String cell : victoryCase) {
                if (playerChoices.contains(cell)) {
                    cont++;
                }
            }
            if (cont == 3) {
                this.endGame = true;
                break;
            }
        }
        if (this.xChoices.size() + this.oChoices.size() == 9 && !endGame) {
            endGame = true;
            draw = true;
        }
    }


    public void start () {
        this.setCells();
        Scanner sc = new Scanner(System.in);
        String choice;
        while (!endGame) {
            System.out.printf("Player %c turn\n", this.currentPlayer);
            this.generateBoard();
            System.out.print("Select a empty cell(letter + number. exemplo: a1): ");
            choice = sc.next().toUpperCase();

           if (this.setCell(choice)) {
               if (this.currentPlayer == 'X') {
                   this.xChoices.add(choice);
                   if (xChoices.size() + oChoices.size() > 4) {
                       this.checkVictory(this.xChoices);
                   }
               } else {
                   this.oChoices.add(choice);
                   if (xChoices.size() + oChoices.size() > 4) {
                       this.checkVictory(this.oChoices);
                   }
               }
               if (!this.endGame)this.changePlayer();
           } else {
               System.out.println("Invalid move! Play again.");
           }
        }
        if (draw) {
            System.out.println("Draw game!");
        } else {
        System.out.printf("%c Win!\n", this.currentPlayer);
        }
        sc.close();
    }
}