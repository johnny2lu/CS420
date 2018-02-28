public class Board {

    public final int BOARD_SIZE = 8;
    public int[][] tiles;

    public Board() {
        // initialize empty board
        tiles = new int[BOARD_SIZE][BOARD_SIZE];
    }

    public Board(int[][] tiles) {
        this.tiles = tiles;
    }

    public String toString() {
        return super.toString();
    }
}