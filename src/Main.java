import tictactoe.TicTacToe;

public class Main {
    public static void main(String[] args) {
        TicTacToe tictactoe = new TicTacToe();

        // CREATE MATRIX & PRINT MATRIX
        tictactoe.createMatrix();
        tictactoe.printMatrix();

        // START GAME
        tictactoe.start();
    }
}