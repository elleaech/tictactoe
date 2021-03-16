//package tictactoe;

import java.util.Scanner;

// RETURN INT 1 -> X
// RETURN INT 2 -> O
// RETURN INT 0 -> NOT FINISHED
// RETURN INT -1 -> DRAW



public class Main {
    private char pattern[][] = new char[3][3];
    private int Orows = 0, Xrows = 0;
    private int Olines = 0, Xlines = 0;

    private Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        Main tictactoe = new Main();


        // CREATE MATRIX & PRINT MATRIX
        tictactoe.create_matrix();
        tictactoe.print_matrix();

        int player = 1;
        int game_result = 0;

        while (game_result == 0){

            // ENTER COORDINATES
            boolean permission = false;
            while (!permission){
                System.out.print("Enter the coordinates: ");
                permission = tictactoe.play(player);
            }
            if (player == 2){
                player = 1;
            }
            else{
                player++;
            }

            // PRINT MATRIX
            tictactoe.print_matrix();

            // ANALYZE GAME STATE
            game_result = tictactoe.check_win();
            switch (game_result){
                case -1:
                    System.out.println("Draw");
                    break;
                case 1:
                    System.out.println("X wins");
                    break;
                case 2:
                    System.out.println("O wins");
                    break;
                default:
                    break;
            }
        }
    }



    public boolean play(int player){
        int coordinate_X, coordinate_Y;

        try{
            coordinate_X = this.scanner.nextInt();
            coordinate_Y = this.scanner.nextInt();
            if ((coordinate_X > 3 || coordinate_X < 1) || (coordinate_Y > 3 || coordinate_Y < 1)){
                System.out.println("Coordinates should be from 1 to 3!");
                return false;
            }
        }
        catch (java.util.InputMismatchException err){
            this.scanner.nextLine();
            System.out.println("You should enter numbers!");
            return false;
        }

        if (this.pattern[coordinate_X-1][coordinate_Y-1] != ' '){
            System.out.println("This cell is occupied! Choose another one!");
            return false;
        }
        else {
            if (player == 1){
                this.pattern[coordinate_X-1][coordinate_Y-1] = 'X';
            }
            else if (player == 2){
                this.pattern[coordinate_X-1][coordinate_Y-1] = 'O';
            }
            return true;
        }
    }

    public char[][] create_matrix(){
        char matrix[][] = new char[3][3];

        for (int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++){
                matrix[x][y] = ' ';
            }
        }

        this.pattern = matrix;
        return matrix;
    }

    public void print_matrix(){
        System.out.println("-----------");
        System.out.printf("| %s %s %s |\n", this.pattern[0][0],this.pattern[0][1], this.pattern[0][2]);
        System.out.printf("| %s %s %s |\n", this.pattern[1][0],this.pattern[1][1], this.pattern[1][2]);
        System.out.printf("| %s %s %s |\n", this.pattern[2][0],this.pattern[2][1], this.pattern[2][2]);
        System.out.println("-----------");
    }

    public int check_win(){
        get_rows();
        get_lines();

        if (this.Orows == 0 && this.Olines == 0 && this.Xrows == 0 && this.Xlines == 0){
            if (get_cross() == 0 && (is_finished())){
                return -1;
            }
            else if (get_cross() == 1){
                return 1;
            }
            else if (get_cross() == 2){
                return 2;
            }
        }
        else {
            if ((this.Xrows == 1 || this.Xlines == 1) && (this.Orows == 0 && this.Olines == 0)){
                return 1;
            }
            else if ((this.Orows == 1 || this.Olines == 1) && (this.Xrows == 0 && this.Xlines == 0)){
                return 2;
            }
        }
        return 0;
    }



    private void get_rows(){
        int Orows = 0, Xrows = 0;
        this.Orows = 0;
        this.Xrows = 0;

        for (int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++){
                if (this.pattern[x][y] == 'O'){
                    Orows++;
                }
                else if (this.pattern[x][y] == 'X'){
                    Xrows++;
                }
            }

            if (Orows == 3){ this.Orows++; }
            if (Xrows == 3){ this.Xrows++; }
            Orows = 0;
            Xrows = 0;
        }
    }

    private void get_lines(){
        int Orows = 0, Xrows = 0;
        this.Olines = 0;
        this.Xlines = 0;

        for (int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++){
                if (this.pattern[y][x] == 'O'){
                    Orows++;
                }
                else if (this.pattern[y][x] == 'X'){
                    Xrows++;
                }
            }

            if (Orows == 3){ this.Olines++; }
            if (Xrows == 3){ this.Xlines++; }
            Orows = 0;
            Xrows = 0;
        }
    }

    private int get_cross(){
        if (this.pattern[1][1] == 'X' && ((this.pattern[0][0] == 'X' && this.pattern[2][2] == 'X') || this.pattern[0][2] == 'X' && this.pattern[2][0] == 'X')){
            return 1;
        }
        else if (this.pattern[1][1] == 'O' && ((this.pattern[0][0] == 'O' && this.pattern[2][2] == 'O') || this.pattern[0][2] == 'O' && this.pattern[2][0] == 'O')){
            return 2;
        }
        return 0;
    }

    private boolean is_finished(){
        for (int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++){
                if(this.pattern[x][y] == ' '){
                    return false;
                }
            }
        }
        return true;
    }
}