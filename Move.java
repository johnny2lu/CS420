public class Move {


    private int row;
    private int col;
    private int utility;

    public Move(int x, int y) {
        row = x;
        col = y;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getUtility() {
        return utility;
    }

    public void setUtility(int utility) {
        this.utility = utility;
    }
}
