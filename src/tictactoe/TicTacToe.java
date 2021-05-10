package tictactoe;

import java.util.*;

public class TicTacToe {
    private char board[][] = new char[3][3];
    private String[] gameOptions = new String[4];
    private final Scanner scanner = new Scanner(System.in);

    private final char playerX1 = 'X';
    private final char playerO2 = 'O';
    private String[] typePlayers = new String[2];

    private int[] currentPlayCoordinate = new int[2];
    private int turn = 0;

    /*-----------------------------------------------------------------------------*/
    public TicTacToe() {
        this.gameOptions[0] = "easy";
        this.gameOptions[1] = "medium";
        this.gameOptions[2] = "hard";
        this.gameOptions[3] = "user";
    }

    public void start() {
        createMatrix();
        printMatrix();

        if (menu()) {
            while (true) {
                callTurn();
                drawPlay();
                printMatrix();

                if (winner()) {
                    return;
                }
            }
        }

        return;
    }

    /*-----------------------------------------------------------------------------*/
    private boolean menu() {
        String[] startInput;

        while (true) {
            System.out.print("Input command: ");
            startInput = scanner.nextLine().split(" ");

            if (startInput[0].equals("start") && startInput.length == 3) {
                if (isGameOption(startInput[1]) && isGameOption(startInput[2])) {
                    typePlayers[0] = startInput[1];
                    typePlayers[1] = startInput[2];
                    return true;
                }
            }

            else if (startInput[0].equals("exit")) {
                return false;
            }

            else {
                System.out.println("Bad parameters!");
            }
        }
    }

    private void callTurn() {
        if (typePlayers[turn].equals("user")) {
            getUsersPlay();
        }

        else if (typePlayers[turn].equals("easy")) {
            System.out.printf("Making move level " + "easy\n");
            analyseEasyPlay();
        }

        else if (typePlayers[turn].equals("medium")) {
            System.out.printf("Making move level " + "medium\n");
            analyseMediumPlay();
        }

        else if (typePlayers[turn].equals("hard")) {
            System.out.printf("Making move level " + "hard\n");
            analyseHardPlay();
        }
    }

    private void drawPlay() {
        if (turn == 0) {
            board[currentPlayCoordinate[0]][currentPlayCoordinate[1]] = playerX1;
            return;
        }

        board[currentPlayCoordinate[0]][currentPlayCoordinate[1]] = playerO2;
    }

    private boolean winner() {
        if (turn == 1) {
            turn = 0;
        } else if (turn == 0) {
            turn = 1;
        }

        for (int coordX = 0; coordX < 3; coordX++) {
            if (board[coordX][0] == playerX1 && board[coordX][1] == playerX1 && board[coordX][2] == playerX1) {
                System.out.println("X wins");
                return true;
            } else if (board[coordX][0] == playerO2 && board[coordX][1] == playerO2 && board[coordX][2] == playerO2) {
                System.out.println("O wins");
                return true;
            }

            else if (board[0][coordX] == playerX1 && board[1][coordX] == playerX1 && board[2][coordX] == playerX1) {
                System.out.println("X wins");
                return true;
            } else if (board[0][coordX] == playerO2 && board[1][coordX] == playerO2 && board[2][coordX] == playerO2) {
                System.out.println("O wins");
                return true;
            }
        }

        if (board[0][0] == playerX1 && board[1][1] == playerX1 && board[2][2] == playerX1) {
            System.out.println("X wins");
            return true;
        } else if (board[0][2] == playerX1 && board[1][1] == playerX1 && board[2][0] == playerX1) {
            System.out.println("X wins");
            return true;
        } else if (board[0][0] == playerO2 && board[1][1] == playerO2 && board[2][2] == playerO2) {
            System.out.println("O wins");
            return true;
        } else if (board[0][2] == playerO2 && board[1][1] == playerO2 && board[2][0] == playerO2) {
            System.out.println("O wins");
            return true;
        }

        return false;
    }

    private void createMatrix() {
        char matrix[][] = new char[3][3];

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                matrix[x][y] = ' ';
            }
        }

        this.board = matrix;
    }

    private void printMatrix() {
        System.out.println("-----------");
        System.out.printf("| %s %s %s |\n", this.board[0][0], this.board[0][1], this.board[0][2]);
        System.out.printf("| %s %s %s |\n", this.board[1][0], this.board[1][1], this.board[1][2]);
        System.out.printf("| %s %s %s |\n", this.board[2][0], this.board[2][1], this.board[2][2]);
        System.out.println("-----------");
    }

    /*-----------------------------------------------------------------------------*/
    private void getUsersPlay() {
        int tempCoordX;
        int tempCoordY;

        while (true) {
            System.out.print("Enter the coordinates: ");
            try {
                tempCoordX = scanner.nextInt();
                tempCoordY = scanner.nextInt();
            } catch (InputMismatchException er) {
                System.out.println("You should enter numbers!");
                continue;
            }

            if (coordinateIsNotAvailable((tempCoordX - 1), (tempCoordY - 1), true)) {
                continue;
            }

            break;
        }

        currentPlayCoordinate[0] = (tempCoordX - 1);
        currentPlayCoordinate[1] = (tempCoordY - 1);
    }

    private void analyseEasyPlay() {
        int tempCoordX;
        int tempCoordY;

        Random randomPlay = new Random();

        while (true) {
            tempCoordX = randomPlay.nextInt(3 - 1 + 1) + 1;
            tempCoordY = randomPlay.nextInt(3 - 1 + 1) + 1;

            if (coordinateIsNotAvailable(tempCoordX, tempCoordY, false)) {
                continue;
            }

            break;
        }

        currentPlayCoordinate[0] = tempCoordX;
        currentPlayCoordinate[1] = tempCoordY;
    }

    private void analyseMediumPlay() {
        int[] winningPlay = new int[2];
        winningPlay[0] = -1;
        winningPlay[1] = -1;

        int[] blockWinPlay = new int[2];
        blockWinPlay[0] = -1;
        blockWinPlay[1] = -1;

        char player;
        if (turn == 0) {
            player = playerX1;
        } else {
            player = playerO2;
        }

        checkLines(winningPlay, blockWinPlay, player);
        checkColumns(winningPlay, blockWinPlay, player);
        checkCrosses(winningPlay, blockWinPlay, player);

        if (winningPlay[0] != -1 && winningPlay[1] != -1) {
            currentPlayCoordinate[0] = winningPlay[0];
            currentPlayCoordinate[1] = winningPlay[1];
            return;
        }
        if (blockWinPlay[0] != -1 && blockWinPlay[1] != -1) {
            currentPlayCoordinate[0] = blockWinPlay[0];
            currentPlayCoordinate[1] = blockWinPlay[1];
            return;
        }

        analyseEasyPlay();
    }

    /*-----------------------------------------------------------------------------*/
    private void analyseHardPlay() {
        int[] tempCoordinates = new int[2];

        while (true) {
            tempCoordinates = findBestMove();
            if (coordinateIsNotAvailable(tempCoordinates[0], tempCoordinates[1], false)) {
                continue;
            }
            break;
        }

        currentPlayCoordinate = tempCoordinates;
    }

    private Boolean isMovesLeft() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '_')
                    return true;
        return false;
    }

    private int evaluate() {
        // Checking for Rows for X or O victory.
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
                if (board[row][0] == playerX1)
                    return +10;
                else if (board[row][0] == playerO2)
                    return -10;
            }
        }

        // Checking for Columns for X or O victory.
        for (int col = 0; col < 3; col++) {
            if (board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                if (board[0][col] == playerX1)
                    return +10;

                else if (board[0][col] == playerO2)
                    return -10;
            }
        }

        // Checking for Diagonals for X or O victory.
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == playerX1)
                return +10;
            else if (board[0][0] == playerO2)
                return -10;
        }

        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == playerX1)
                return +10;
            else if (board[0][2] == playerO2)
                return -10;
        }

        // Else if none of them have won then return 0
        return 0;
    }

    private int minimax(int depth, Boolean isMax) {
        int score = evaluate();

        // If Maximizer has won the game
        // return his/her evaluated score
        if (score == 10)
            return score;

        // If Minimizer has won the game
        // return his/her evaluated score
        if (score == -10)
            return score;

        // If there are no more moves and
        // no winner then it is a tie
        if (isMovesLeft() == false)
            return 0;

        // If this maximizer's move
        if (isMax) {
            int best = -1000;

            // Traverse all cells
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (board[i][j] == '_') {
                        // Make the move
                        board[i][j] = playerX1;

                        // Call minimax recursively and choose
                        // the maximum value
                        best = Math.max(best, minimax(depth + 1, !isMax));

                        // Undo the move
                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }

        // If this minimizer's move
        else {
            int best = 1000;

            // Traverse all cells
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (board[i][j] == '_') {
                        // Make the move
                        board[i][j] = playerO2;

                        // Call minimax recursively and choose
                        // the minimum value
                        best = Math.min(best, minimax(depth + 1, !isMax));

                        // Undo the move
                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }
    }

    private int[] findBestMove() {
        int bestVal = -1000;
        int[] bestMove = new int[2];

        // Traverse all cells, evaluate minimax function
        // for all empty cells. And return the cell
        // with optimal value.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Check if cell is empty
                if (board[i][j] == '_') {
                    // Make the move
                    board[i][j] = playerX1;

                    int moveVal = minimax(0, false);

                    // Undo the move
                    board[i][j] = '_';

                    // If the value of the current move is
                    // more than the best value, then update
                    // best/
                    if (moveVal > bestVal) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        return bestMove;
    }

    /*-----------------------------------------------------------------------------*/
    private void checkLines(int[] winningPlay, int[] blockWinPlay, char player) {
        for (int coordX = 0; coordX < 3; coordX++) {
            for (int coordY = 0; coordY < 2; coordY++) {
                if (coordY == 0) {
                    if ((board[coordX][coordY] == board[coordX][coordY + 1]) && board[coordX][coordY] == player
                            && board[coordX][coordY] != ' ' && !(coordinateIsNotAvailable(coordX, coordY + 2, false))) {
                        winningPlay[0] = coordX;
                        winningPlay[1] = coordY + 2;
                    }
                    if ((board[coordX][coordY] == board[coordX][coordY + 2]) && board[coordX][coordY] == player
                            && board[coordX][coordY] != ' ' && !(coordinateIsNotAvailable(coordX, coordY + 1, false))) {
                        winningPlay[0] = coordX;
                        winningPlay[1] = coordY + 1;
                    }

                    if ((board[coordX][coordY] == board[coordX][coordY + 1]) && board[coordX][coordY] != player
                            && board[coordX][coordY] != ' ' && !(coordinateIsNotAvailable(coordX, coordY + 2, false))) {
                        blockWinPlay[0] = coordX;
                        blockWinPlay[1] = coordY + 2;
                    }
                    if ((board[coordX][coordY] == board[coordX][coordY + 2]) && board[coordX][coordY] != player
                            && board[coordX][coordY] != ' ' && !(coordinateIsNotAvailable(coordX, coordY + 1, false))) {
                        blockWinPlay[0] = coordX;
                        blockWinPlay[1] = coordY + 1;
                    }
                }

                else {
                    if ((board[coordX][coordY] == board[coordX][coordY + 1]) && board[coordX][coordY] == player
                            && board[coordX][coordY] != ' ' && !(coordinateIsNotAvailable(coordX, coordY - 1, false))) {
                        winningPlay[0] = coordX;
                        winningPlay[1] = coordY - 1;
                    }

                    if ((board[coordX][coordY] == board[coordX][coordY + 1]) && board[coordX][coordY] != player
                            && board[coordX][coordY] != ' ' && !(coordinateIsNotAvailable(coordX, coordY - 1, false))) {
                        blockWinPlay[0] = coordX;
                        blockWinPlay[1] = coordY - 1;
                    }
                }
            }
        }
    }

    private void checkColumns(int[] winningPlay, int[] blockWinPlay, char player) {
        for (int coordX = 0; coordX < 2; coordX++) {
            for (int coordY = 0; coordY < 3; coordY++) {
                if (coordX == 0) {
                    if ((board[coordX][coordY] == board[coordX + 1][coordY]) && board[coordX][coordY] == player
                            && board[coordX][coordY] != ' ' && !(coordinateIsNotAvailable(coordX + 2, coordY, false))) {
                        winningPlay[0] = coordX + 2;
                        winningPlay[1] = coordY;
                    }
                    if ((board[coordX][coordY] == board[coordX + 2][coordY]) && board[coordX][coordY] == player
                            && board[coordX][coordY] != ' ' && !(coordinateIsNotAvailable(coordX + 1, coordY, false))) {
                        winningPlay[0] = coordX + 1;
                        winningPlay[1] = coordY;
                    }

                    if ((board[coordX][coordY] == board[coordX + 1][coordY]) && board[coordX][coordY] != player
                            && board[coordX][coordY] != ' ' && !(coordinateIsNotAvailable(coordX + 2, coordY, false))) {
                        blockWinPlay[0] = coordX + 2;
                        blockWinPlay[1] = coordY;
                    }
                    if ((board[coordX][coordY] == board[coordX + 2][coordY]) && board[coordX][coordY] != player
                            && board[coordX][coordY] != ' ' && !(coordinateIsNotAvailable(coordX + 1, coordY, false))) {
                        blockWinPlay[0] = coordX + 1;
                        blockWinPlay[1] = coordY;
                    }
                }

                else {
                    if ((board[coordX][coordY] == board[coordX + 1][coordY]) && board[coordX][coordY] == player
                            && board[coordX][coordY] != ' ' && !(coordinateIsNotAvailable(coordX - 1, coordY, false))) {
                        winningPlay[0] = coordX - 1;
                        winningPlay[1] = coordY;
                    }

                    if ((board[coordX][coordY] == board[coordX + 1][coordY]) && board[coordX][coordY] != player
                            && board[coordX][coordY] != ' ' && !(coordinateIsNotAvailable(coordX - 1, coordY, false))) {
                        blockWinPlay[0] = coordX - 1;
                        blockWinPlay[1] = coordY;
                    }
                }
            }
        }
    }

    private void checkCrosses(int[] winningPlay, int[] blockWinPlay, char player) {
        if ((board[0][0] == board[1][1]) && board[0][0] == player && board[0][0] != ' '
                && !(coordinateIsNotAvailable(2, 2, false))) {
            winningPlay[0] = 2;
            winningPlay[1] = 2;
        }
        if ((board[0][0] == board[1][1]) && board[0][0] != player && board[0][0] != ' '
                && !(coordinateIsNotAvailable(2, 2, false))) {
            blockWinPlay[0] = 2;
            blockWinPlay[1] = 2;
        }

        if ((board[0][0] == board[2][2]) && board[0][0] == player && board[0][0] != ' '
                && !(coordinateIsNotAvailable(1, 1, false))) {
            winningPlay[0] = 1;
            winningPlay[1] = 1;
        }
        if ((board[0][0] == board[2][2]) && board[0][0] != player && board[0][0] != ' '
                && !(coordinateIsNotAvailable(1, 1, false))) {
            blockWinPlay[0] = 1;
            blockWinPlay[1] = 1;
        }

        if ((board[1][1] == board[2][2]) && board[1][1] == player && board[1][1] != ' '
                && !(coordinateIsNotAvailable(0, 0, false))) {
            winningPlay[0] = 0;
            winningPlay[1] = 0;
        }
        if ((board[1][1] == board[2][2]) && board[1][1] != player && board[1][1] != ' '
                && !(coordinateIsNotAvailable(0, 0, false))) {
            blockWinPlay[0] = 0;
            blockWinPlay[1] = 0;
        }

        if ((board[0][2] == board[1][1]) && board[0][2] == player && board[0][2] != ' '
                && !(coordinateIsNotAvailable(2, 0, false))) {
            winningPlay[0] = 2;
            winningPlay[1] = 0;
        }
        if ((board[0][2] == board[1][1]) && board[0][2] != player && board[0][2] != ' '
                && !(coordinateIsNotAvailable(2, 0, false))) {
            blockWinPlay[0] = 2;
            blockWinPlay[1] = 0;
        }

        if ((board[0][2] == board[2][0]) && board[0][2] == player && board[0][2] != ' '
                && !(coordinateIsNotAvailable(1, 1, false))) {
            winningPlay[0] = 1;
            winningPlay[1] = 1;
        }
        if ((board[1][1] == board[2][0]) && board[1][1] != player && board[1][1] != ' '
                && !(coordinateIsNotAvailable(1, 1, false))) {
            blockWinPlay[0] = 0;
            blockWinPlay[1] = 2;
        }

        if ((board[1][1] == board[2][0]) && board[1][1] == player && board[1][1] != ' '
                && !(coordinateIsNotAvailable(0, 2, false))) {
            winningPlay[0] = 0;
            winningPlay[1] = 2;
        }
        if ((board[1][1] == board[2][0]) && board[1][1] != player && board[1][1] != ' '
                && !(coordinateIsNotAvailable(0, 2, false))) {
            blockWinPlay[0] = 0;
            blockWinPlay[1] = 2;
        }
    }

    private boolean coordinateIsNotAvailable(int tempCoordX, int tempCoordY, boolean userInput) {
        if ((tempCoordX > 2 || tempCoordX < 0) || (tempCoordY > 2 || tempCoordY < 0)) {
            if (userInput) {
                System.out.println("Coordinates should be from 1 to 3!");
            }
            return true;
        }

        if (board[tempCoordX][tempCoordY] != ' ') {
            if (userInput) {
                System.out.println("This cell is occupied! Choose another one!");
            }
            return true;
        }

        return false;
    }

    private boolean isGameOption(String input) {
        for (String obj : this.gameOptions) {
            if (obj.equals(input)) {
                return true;
            }
        }

        return false;
    }
}
