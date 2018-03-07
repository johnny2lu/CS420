import java.util.Scanner;

public class Game {

    public static final int LOWER_ASCII = 97;

    public static void main(String[] args) {
        int timeSecondsAllowed;
        int first;

        System.out.println("Enter maximum time allowed to generate answer: ");
        Scanner kb = new Scanner(System.in);
        timeSecondsAllowed = kb.nextInt();

        System.out.println("Who is moving first, Player or Opponent? [1/2]: ");
        first = kb.nextInt();
        // Player = X, Opponent = O
        GamePlayer firstPlayer = null;
        if (first == 1) {
            firstPlayer = GamePlayer.PLAYER;
        }
        else if (first == 2) {
            firstPlayer = GamePlayer.OPPONENT;
        }

        play(timeSecondsAllowed, firstPlayer);
    }

    public static void play(int timeSecondsAllowed, GamePlayer player) {
        Board board = new Board();
        board.setPlayerTurn(player);
        ABPruning abPruning = new ABPruning(timeSecondsAllowed);
        Scanner kb = new Scanner(System.in);
        // let the games begin!
        while (true) {
            if (board.getPlayerTurn() == GamePlayer.OPPONENT) {
                // enter opponent move
                System.out.print("Choose opponent's next move: ");
                String opponentMove = kb.nextLine();
                Move nextMove = stringToMove(opponentMove);
                board.move(nextMove);
                board.setPlayerTurn(GamePlayer.PLAYER);
            }
            else if (board.getPlayerTurn() == GamePlayer.PLAYER) {
                // make first move
                Move nextMove = abPruning.ABSearch(board);
                board.move(nextMove);
                System.out.print("Player's move is: " + moveToString(nextMove));
                board.setPlayerTurn(GamePlayer.OPPONENT);
            }
            System.out.println();
            System.out.println(board);
            // check for winner
            if (!board.getGameStatus()) {
                System.out.println("Game over");
                break;
            }
        }
    }

    // translate string <char><int> move to board move with x and y coordinates
    // starts at index 0
    // ex: e5 = Move(4, 4)
    public static Move stringToMove(String nextMove) {
        if (nextMove.length() == 2) {
            char r = nextMove.charAt(0);
            int c = Character.getNumericValue(nextMove.charAt(1));
            // within range of lowercase alphabet
            if (r >= LOWER_ASCII && r < LOWER_ASCII + 26)
                return new Move(r - LOWER_ASCII, c - 1);
        }
        return null;
    }

    // translate board move with x and y coordinates to string <char><int> move
    // stats at index 0
    // ex: Move(3, 4) = d5
    public static String moveToString(Move nextMove) {
        int x = nextMove.getRow();
        int y = nextMove.getRow();
        char r = (char) (x + LOWER_ASCII);
        int c = y + 1;
        StringBuilder sb = new StringBuilder();
        sb.append(r);
        sb.append(c);
        return sb.toString();
    }
}