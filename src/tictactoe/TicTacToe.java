package tictactoe;

import java.util.*;

public class TicTacToe {
    private char pattern[][] = new char[3][3];

    private final String[] gameOptions = new String[4];

    public TicTacToe() {
        this.gameOptions[0] = "easy";
        this.gameOptions[1] = "medium";
        this.gameOptions[2] = "hard";
        this.gameOptions[3] = "user";
    }

    public void start() {
        String[] playerOption = new String[2];
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Input command: ");
            String[] command = scanner.nextLine().split(" ");

            if (command[0].equals("start") && command.length == 3) {
                playerOption[0] = command[1];
                playerOption[1] = command[2];

                if (this.isGameOption(playerOption[0]) && this.isGameOption(playerOption[0])) {
                    int player = 1;
                    int game_result = 0;

                    this.printMatrix();

                    while (game_result == 0) {
                        int user = 0;
                        int index = (player - 1);
                        boolean permission = false;

                        if (playerOption[index].equals("user")) {
                            System.out.print("Enter the coordinates: ");
                            user = 0;
                        } else {
                            System.out.printf("Making move level \"%s\"\n", playerOption[index]);

                            switch (playerOption[index]) {
                            case "easy":
                                user = 1;
                                break;
                            }
                        }

                        do {
                            permission = this.play(player, user);
                        } while (!permission);

                        if (player == 2) {
                            player = 1;
                        } else {
                            player++;
                        }

                        // ANALYZE GAME STATE
                        this.printMatrix();
                        game_result = this.checkWin();
                    }

                    break;
                }

                else {
                    // END IF
                }
            }

            else if (command[0].equals("exit")) {
                break;
            }

            else {
                System.out.println("Bad parameters!");
            }
        }

    }

    public char[][] createMatrix() {
        char matrix[][] = new char[3][3];

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                matrix[x][y] = ' ';
            }
        }

        this.pattern = matrix;
        return matrix;
    }

    public char[][] createMatrix(char[] pattern) {
        char matrix[][] = new char[3][3];

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                matrix[x][y] = pattern[(x * 3) + y];
            }
        }

        this.pattern = matrix;
        return matrix;
    }

    public void printMatrix() {
        System.out.println("-----------");
        System.out.printf("| %s %s %s |\n", this.pattern[0][0], this.pattern[0][1], this.pattern[0][2]);
        System.out.printf("| %s %s %s |\n", this.pattern[1][0], this.pattern[1][1], this.pattern[1][2]);
        System.out.printf("| %s %s %s |\n", this.pattern[2][0], this.pattern[2][1], this.pattern[2][2]);
        System.out.println("-----------");
    }

    // USER 0 -> Human
    // USER 1 -> Easy
    // USER 2 -> Medium
    // USER 3 -> Hard

    private boolean play(int player, int user) {
        int coordinate_X = 0, coordinate_Y = 0;

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        if (user == 0) {
            try {
                coordinate_X = scanner.nextInt();
                coordinate_Y = scanner.nextInt();
            } catch (java.util.InputMismatchException err) {
                scanner.nextLine();
                return false;
            }
        } else if (user == 1) {
            coordinate_X = random.nextInt(3 - 1 + 1) + 1;
            coordinate_Y = random.nextInt(3 - 1 + 1) + 1;
        }

        if ((coordinate_X > 3 || coordinate_X < 1) || (coordinate_Y > 3 || coordinate_Y < 1)) {
            return false;
        } else if (this.pattern[coordinate_X - 1][coordinate_Y - 1] != ' ') {
            return false;
        } else {
            if (player == 1) {
                this.pattern[coordinate_X - 1][coordinate_Y - 1] = 'X';
            } else if (player == 2) {
                this.pattern[coordinate_X - 1][coordinate_Y - 1] = 'O';
            }
        }

        return true;
    }

    private boolean isGameOption(String input) {
        for (String obj : this.gameOptions) {
            if (obj.equals(input)) {
                return true;
            }
        }

        return false;
    }

    // RETURN INT 1 -> X
    // RETURN INT 2 -> O
    // RETURN INT 0 -> NOT FINISHED
    // RETURN INT -1 -> DRAW

    // MATRIX INDEX 0 -> O (player2)
    // MATRIX INDEX 1 -> X (player1)

    private int checkWin() {
        int[] rRows = getRows();
        int[] rLines = getLines();
        int[] rCross = getCross();

        if (rRows[0] == 0 && rLines[0] == 0 && rRows[1] == 0 && rLines[1] == 0) {
            if (rCross[1] == 0 && rCross[0] == 0 && (isFinished())) {
                System.out.println("Draw");
                return -1;
            } else if (rCross[1] != 0 && rCross[0] == 0) {
                System.out.println("X wins");
                return 1;
            } else if (rCross[1] == 0 && rCross[0] != 0) {
                System.out.println("O wins");
                return 2;
            }
        } else {
            if ((rRows[1] == 1 || rLines[1] == 1) && (rRows[0] == 0 && rLines[0] == 0)) {
                System.out.println("X wins");
                return 1;
            } else if ((rRows[0] == 1 || rLines[0] == 1) && (rRows[1] == 0 && rLines[1] == 0)) {
                System.out.println("O wins");
                return 2;
            }
        }
        return 0;
    }

    private boolean isFinished() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (this.pattern[x][y] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private int[] getRows() {
        int Orows = 0, Xrows = 0;
        int[] rRows = new int[2];

        rRows[0] = 0;
        rRows[1] = 0;

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (this.pattern[x][y] == 'O') {
                    Orows++;
                } else if (this.pattern[x][y] == 'X') {
                    Xrows++;
                }
            }

            if (Orows == 3) {
                rRows[0]++;
            }
            if (Xrows == 3) {
                rRows[1]++;
            }
            Orows = 0;
            Xrows = 0;
        }

        return rRows;
    }

    private int[] getLines() {
        int Olines = 0, Xlines = 0;
        int[] rLines = new int[2];

        rLines[0] = 0;
        rLines[1] = 0;

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (this.pattern[y][x] == 'O') {
                    Olines++;
                } else if (this.pattern[y][x] == 'X') {
                    Xlines++;
                }
            }

            if (Olines == 3) {
                rLines[0]++;
            }
            if (Xlines == 3) {
                rLines[1]++;
            }
            Olines = 0;
            Xlines = 0;
        }

        return rLines;
    }

    private int[] getCross() {
        int[] rCross = new int[2];
        rCross[0] = 0;
        rCross[1] = 0;

        if (this.pattern[1][1] == 'X' && ((this.pattern[0][0] == 'X' && this.pattern[2][2] == 'X')
                || this.pattern[0][2] == 'X' && this.pattern[2][0] == 'X')) {
            rCross[1]++;
        } else if (this.pattern[1][1] == 'O' && ((this.pattern[0][0] == 'O' && this.pattern[2][2] == 'O')
                || this.pattern[0][2] == 'O' && this.pattern[2][0] == 'O')) {
            rCross[0]++;
        }

        return rCross;
    }
}
