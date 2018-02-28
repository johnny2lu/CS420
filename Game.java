import java.util.Scanner;

public class Game {

    public static void main(String[] args) {
        Board board = new Board();
        ABPruning abPruning = new ABPruning();
        int timeSecondsAllowed;
        int firstPlayer;

        System.out.println("Enter maximum time allowed to generate answer: ");
        Scanner kb = new Scanner(System.in);
        timeSecondsAllowed = kb.nextInt();

        System.out.println("Who is moving first, Player or Opponent? [1/2]: ");
        firstPlayer = kb.nextInt();
    }

    public static void play() {

    }
}