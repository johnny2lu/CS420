import java.util.Scanner;

public class Game {

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

    public static void play(int timeSecondsAllowed, GamePlayer firstPlayer) {
        Board board = new Board(firstPlayer);
        ABPruning abPruning = new ABPruning(timeSecondsAllowed);
        // let the games begin!
        while (true) {

        }
    }
}