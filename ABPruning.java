import java.util.*;

public class ABPruning {

    private long MAX_TIME;
    private final Integer POSITIVE_INFINITY = Integer.MAX_VALUE;
    private final Integer NEGATIVE_INFINITY = Integer.MIN_VALUE;
    private final int MAX_DEPTH = 5;
    private long startTime;
    private PriorityQueue<Move> utilityMoves;

    public ABPruning(int timeSecondsAllowed) {
        // time to abort in milliseconds
        MAX_TIME = (long) timeSecondsAllowed * 1000;
    }

    public Move ABSearch(Board board) {
        startTime = System.currentTimeMillis();
        // heap based priority queue of moves to utility values
        utilityMoves = new PriorityQueue<>(10, new Comparator<Move>() {
            @Override
            public int compare(Move o1, Move o2) {
                return o2.getUtility() - o1.getUtility();
            }
        });
        int v = maxValue(board, NEGATIVE_INFINITY, POSITIVE_INFINITY, 0);
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
                //System.out.println(m.getUtility());
                if (m.getUtility() == v) {
                    System.out.println(v);
                    return m;
                }
            }
            // return best utility within time limit
            return utilityMoves.poll();
        //}
    }

    public int maxValue(Board board, int alpha, int beta, int depth) {
        //System.out.println(depth);
        if (!terminalState(board) && !cutOffTime() && depth <= MAX_DEPTH) {
            System.out.println("MAX LOOP");
            int v = NEGATIVE_INFINITY;
            // loop through each possible move on board
            for (Move action : board.getPossibleMoves()) {
                //System.out.println(action.getUtility());
                // create separate board to calculate utility
                Board testBoard = board.deepCopy(board);
                testBoard.move(action);
                System.out.println(testBoard);
                v = max(v, minValue(testBoard, alpha, beta, depth + 1));
                System.out.println("V: " + v);
                action.setUtility(v);
                utilityMoves.add(action);
                if (v >= beta) {
                    System.out.println("GREATER THAN BETA");
                    return v;
                }
                alpha = max(alpha, v);
            }
            return v;
        }
        // reached time limit
        return alpha;
    }

    public int minValue(Board board, int alpha, int beta, int depth) {
        if (!terminalState(board) && !cutOffTime() && depth <= MAX_DEPTH) {
            System.out.println("MIN LOOP");
            int v = POSITIVE_INFINITY;
            // loop through each possible move on board
            for (Move action : board.getPossibleMoves()) {
                Board testBoard = board.deepCopy(board);
                testBoard.setPlayerTurn(GamePlayer.OPPONENT);
                testBoard.move(action);
                System.out.println(testBoard);
                v = min(v, maxValue(testBoard, alpha, beta, depth + 1));
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
        return beta;
    }

    // check if game has a winner
    public boolean terminalState(Board board) {
        return board.getGameStatus();
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