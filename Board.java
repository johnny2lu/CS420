import java.util.*;

public class Board {

    private final int BOARD_SIZE = 8;
    private GamePiece[][] tiles;
    // maps moves to utility cost values
    private Set<Move> moves;
    private GamePlayer playerTurn;

    public Board(GamePlayer playerTurn) {
        // initialize empty board
        tiles = new GamePiece[BOARD_SIZE][BOARD_SIZE];
        moves = new HashSet<>();
        this.playerTurn = playerTurn;
        initializeBoard();
    }

    public Board(Board board) {
        this.tiles = board.tiles;
    }

    // initialize board with negative values
    public void initializeBoard() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = GamePiece._;
            }
        }
    }

    // places X or O on board depending on whose turn
    public boolean move(Move action) {
        if (!checkValidMove(action)) {
            System.out.println("Invalid move");
            return false;
        }
        if (isGameOver(action)) {
            System.out.println("Game over");
            return false;
        }

        // whose turn is it anyways?
        if (playerTurn == GamePlayer.OPPONENT) {
            tiles[action.getRow()][action.getCol()] = GamePiece.O;
        }
        else {
            tiles[action.getRow()][action.getCol()] = GamePiece.X;
        }
        return true;
    }

    /*
    // test different move permutations to find best path
    public Board testMove(Move action) {
        this.move(action);
    }
    */

    public boolean checkValidMove(Move action) {
        int x = action.getRow();
        int y = action.getCol();
        // within boundaries
        if (x < BOARD_SIZE && y < BOARD_SIZE) {
            // valid move
            if (tiles[x][y] == GamePiece._) {
                return true;
            }
        }
        return false;
    }

    public boolean isGameOver(Move action) {
        // TODO
        // check for winner or if game is a draw
        int x = action.getRow();
        int y = action.getCol();

        return false;
    }

    public Set<Move> getPossibleMoves() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (tiles[i][j] != GamePiece._) {
                    moves.add(new Move(i, j));
                }
            }
        }
        return moves;
    }

    @Override
    public String toString() {
        char c = 'A';
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        for (int i = 1; i <= BOARD_SIZE; i++) {
            sb.append(i + " ");
        }
        sb.append("\n");
        for (int i = 0; i < BOARD_SIZE; i++) {
            sb.append((char) c + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                sb.append(tiles[i][j] + " ");
            }
            sb.append("\n");
            c++;
        }
        return sb.toString();
    }
}