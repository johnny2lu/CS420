import java.util.*;

public class ABPruning {

    // time to abort in milliseconds
    private final long MAX_TIME = 5000;
    private final Integer POSITIVE_INFINITY = Integer.MAX_VALUE;
    private final Integer NEGATIVE_INFINITY = Integer.MIN_VALUE;
    private long startTime;
    private PriorityQueue<Move> utilityMoves;

    public Move ABSearch(Board board) {
        startTime = System.currentTimeMillis();
        // heap based priority queue of moves to utility values
        utilityMoves = new PriorityQueue<>(10, new Comparator<Move>() {
            @Override
            public int compare(Move o1, Move o2) {
                return o2.getUtility() - o1.getUtility();
            }
        });
        int v = maxValue(board, NEGATIVE_INFINITY, POSITIVE_INFINITY);
        /*
        if (v == NEGATIVE_INFINITY || v == POSITIVE_INFINITY) {
            return utilityMoves.poll();
        }
        else {
        */
            // get the move with utility v by iterating through priority queue
            Iterator iterator = utilityMoves.iterator();
            while (iterator.hasNext()) {
                Move m = (Move) iterator.next();
                if (m.getUtility() == v) {
                    return m;
                }
            }
            // return best utility within time limit
            return utilityMoves.poll();
        //}
    }

    public int maxValue(Board board, int alpha, int beta) {
        if (terminalState(board) && !cutOffTime()) {
            int v = NEGATIVE_INFINITY;
            // loop through each possible move on board
            for (Move action : board.getPossibleMoves()) {
                Board testBoard = new Board(board);
                testBoard.move(action);
                v = max(v, minValue(testBoard, alpha, beta));
                action.setUtility(v);
                utilityMoves.add(action);
                if (v >= beta) {
                    return v;
                }
                alpha = max(alpha, v);
            }
            return v;
        }
        // reached time limit
        return NEGATIVE_INFINITY;
    }

    public int minValue(Board board, int alpha, int beta) {
        if (terminalState(board) && !cutOffTime()) {
            int v = POSITIVE_INFINITY;
            // loop through each possible move on board
            for (Move action : board.getPossibleMoves()) {
                Board testBoard = new Board(board);
                testBoard.move(action);
                v = min(v, minValue(testBoard, alpha, beta));
                action.setUtility(v);
                utilityMoves.add(action);
                if (v <= alpha) {
                    return v;
                }
                beta = min(beta, v);
            }
            return v;
        }
        // reached time limit
        return POSITIVE_INFINITY;
    }

    // check if game has a winner
    public boolean terminalState(Board board) {
        return true;
    }

    // check if max time reached
    public boolean cutOffTime() {
        if (System.currentTimeMillis() - startTime <= MAX_TIME) {
            return false;
        }
        return true;
    }

    public int max(int x, int y) {
        if (x > y) {
            return x;
        }
        return y;
    }

    public int min(int x, int y) {
        if (x < y) {
            return x;
        }
        return y;
    }

}