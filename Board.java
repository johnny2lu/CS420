import java.util.*;

public class Board {

    private final int BOARD_SIZE = 8;
    private int[][] tiles;
    // maps moves to utility cost values
    private Set<Move> moves;

    public Board() {
        // initialize empty board
        tiles = new int[BOARD_SIZE][BOARD_SIZE];
        moves = new HashSet<>();
        initializeBoard();
    }

    public Board(Board board) {
        this.tiles = board.tiles;
    }

    // initialize board with negative values
    public void initializeBoard() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = -1;
            }
        }
    }

    /*
    public int[][] getTiles() {
        return tiles;
    }
    */

    // places X or O on board depending on whose turn
    public void move(Move action) {
        if (!checkValidMove()) {
            System.out.println("Invalid move");
        }
        if (isGameOver()) {
            System.out.println("Game over");
        }
    }

    /*
    // test different move permutations to find best path
    public Board testMove(Move action) {
        this.move(action);
    }
    */

    public boolean checkValidMove() {
        return true;
    }

    public boolean isGameOver() {
        return false;
    }

    public Set<Move> getPossibleMoves() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (tiles[i][j] != -1) {
                    moves.add(new Move(i, j));
                }
            }
        }
        return moves;
    }

    public String toString() {
        return super.toString();
    }
}