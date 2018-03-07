import java.util.*;

public class Board {

    private final int BOARD_SIZE = 8;
    private final int MOVES_TO_WIN = 4;
    private GamePiece[][] tiles;
    // maps moves to utility cost values
    private List<Move> moves;
    private GamePlayer playerTurn;
    private boolean gameOver;
    private int turnNumber;

    public Board() {
        // initialize empty board
        tiles = new GamePiece[BOARD_SIZE][BOARD_SIZE];
        gameOver = false;
        turnNumber = 0;
        initializeBoard();
    }

    public Board deepCopy(Board board) {
        Board copy = new Board();
        for (int i = 0; i < tiles.length; i++) {
            copy.getGamePieces()[i] = Arrays.copyOf(board.getGamePieces()[i], board.getGamePieces()[i].length);
        }
        return copy;
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

        // whose turn is it anyways?
        if (playerTurn == GamePlayer.OPPONENT) {
            tiles[action.getRow()][action.getCol()] = GamePiece.O;
        }
        else {
            tiles[action.getRow()][action.getCol()] = GamePiece.X;
        }
        turnNumber++;

        if (isGameOver(action)) {
            gameOver = true;
            return false;
        }
        return true;
    }

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
        // check for winner or if game is a draw
        int x = action.getRow();
        int y = action.getCol();

        GamePiece currentPlayer = null;
        if (playerTurn == GamePlayer.PLAYER) {
            currentPlayer = GamePiece.X;
        }
        else if (playerTurn == GamePlayer.OPPONENT) {
            currentPlayer = GamePiece.O;
        }

        // no moves left
        if (!checkDraw()) {
            // check horizontal, vertical for winning game
            int vertical = 0;
            int horizontal = 0;
            for (int i = 0; i < BOARD_SIZE; i++) {
                // vertical check
                if (tiles[x][i] == currentPlayer) {
                    vertical++;
                }
                else {
                    vertical = 0;
                }

                // horizontal check
                if (tiles[i][y] == currentPlayer) {
                    horizontal++;
                }
                else {
                    horizontal = 0;
                }

                if (horizontal >= MOVES_TO_WIN || vertical >= MOVES_TO_WIN) {
                    // we have a winner
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkDraw() {
        if (turnNumber == (BOARD_SIZE * BOARD_SIZE)) {
            return true;
        }
        return false;
    }

    public List<Move> getPossibleMoves() {
        moves = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE ; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (tiles[i][j] == GamePiece._) {
                    Move possibleMove = new Move(i, j);
                    //System.out.println(evaluateUtility(possibleMove));
                    possibleMove.setUtility(evaluateUtility(possibleMove));
                    moves.add(possibleMove);
                }
            }
        }
        return moves;
    }

    public void setPlayerTurn(GamePlayer player) {
        this.playerTurn = player;
    }

    public GamePlayer getPlayerTurn() {
        return playerTurn;
    }

    public boolean getGameStatus() {
        return gameOver;
    }

    public GamePiece[][] getGamePieces() {
        return tiles;
    }

    public int evaluateUtility(Move move) {
        // greater than zero: better for player
        // equals zero: neither better nor worse
        // less than zero move: better for opponent
        int playerUtility = 0;
        int opponentUtility = 0;
        int row = move.getRow();
        int col = move.getCol();
        // check player moves
        // check four in a row horizontally
        if (row + 3 < BOARD_SIZE) {
            if (tiles[row][col] == GamePiece.X && tiles[row + 1][col] == GamePiece.X &&
                    tiles[row + 2][col] == GamePiece.X && tiles[row + 3][col] == GamePiece.X) {
                playerUtility += 10000;
            }
        }
        if (row - 3 < BOARD_SIZE) {
            if (tiles[row][col] == GamePiece.X && tiles[row - 1][col] == GamePiece.X &&
                    tiles[row - 2][col] == GamePiece.X && tiles[row - 3][col] == GamePiece.X) {
                playerUtility += 10000;
            }
        }
        // check three in a row horizontally
        if (row + 2 < BOARD_SIZE) {
            if (tiles[row][col] == GamePiece.X && tiles[row + 1][col] == GamePiece.X &&
                    tiles[row + 2][col] == GamePiece.X) {
                playerUtility += 75;
            }
        }
        if (row - 2 < BOARD_SIZE) {
            if (tiles[row][col] == GamePiece.X && tiles[row - 1][col] == GamePiece.X &&
                    tiles[row - 2][col] == GamePiece.X) {
                playerUtility += 75;
            }
        }
        // check two in a row horizontally
        if (row + 1 < BOARD_SIZE) {
            if (tiles[row][col] == GamePiece.X && tiles[row + 1][col] == GamePiece.X) {
                playerUtility += 10;
            }
        }
        if (row - 1 < BOARD_SIZE) {
            if (tiles[row][col] == GamePiece.X && tiles[row - 1][col] == GamePiece.X) {
                playerUtility += 10;
            }
        }

        // check opponent moves horizontally
        if (row + 3 < BOARD_SIZE) {
            if (tiles[row][col] == GamePiece.O && tiles[row + 1][col] == GamePiece.O &&
                    tiles[row + 2][col] == GamePiece.O && tiles[row + 3][col] == GamePiece.O) {
                opponentUtility -= 10000;
            }
        }
        if (row - 3 < BOARD_SIZE) {
            if (tiles[row][col] == GamePiece.O && tiles[row - 1][col] == GamePiece.O &&
                    tiles[row - 2][col] == GamePiece.O && tiles[row - 3][col] == GamePiece.O) {
                opponentUtility -= 10000;
            }
        }
        // check three in a row horizontally
        if (row + 2 < BOARD_SIZE) {
            if (tiles[row][col] == GamePiece.O && tiles[row + 1][col] == GamePiece.O &&
                    tiles[row + 2][col] == GamePiece.O) {
                opponentUtility -= 75;
            }
        }
        if (row - 2 < BOARD_SIZE) {
            if (tiles[row][col] == GamePiece.O && tiles[row - 1][col] == GamePiece.O &&
                    tiles[row - 2][col] == GamePiece.O) {
                opponentUtility -= 75;
            }
        }
        // check two in a row horizontally
        if (row + 1 < BOARD_SIZE) {
            if (tiles[row][col] == GamePiece.O && tiles[row + 1][col] == GamePiece.O) {
                opponentUtility -= 10;
            }
        }
        if (row - 1 < BOARD_SIZE) {
            if (tiles[row][col] == GamePiece.O && tiles[row - 1][col] == GamePiece.O) {
                opponentUtility -= 10;
            }
        }

        // check player moves
        // check four in a row vertically
        if (col + 3 < BOARD_SIZE) {
            if (tiles[row][col] == GamePiece.X && tiles[row][col + 1] == GamePiece.X &&
                    tiles[row][col + 2] == GamePiece.X && tiles[row][col + 3] == GamePiece.X) {
                playerUtility += 10000;
            }
        }
        if (col - 3 < BOARD_SIZE) {
            if (tiles[row][col] == GamePiece.X && tiles[row][col - 1] == GamePiece.X &&
                    tiles[row][col - 2] == GamePiece.X && tiles[row][col - 3] == GamePiece.X) {
                playerUtility += 10000;
            }
        }
        // check three in a row vertically
        if (col + 2 < BOARD_SIZE) {
            if (tiles[row][col] == GamePiece.X && tiles[row][col + 1] == GamePiece.X &&
                    tiles[row][col + 2] == GamePiece.X) {
                playerUtility += 75;
            }
        }
        if (col - 2 < BOARD_SIZE) {
            if (tiles[row][col] == GamePiece.X && tiles[row][col - 1] == GamePiece.X &&
                    tiles[row][col - 2] == GamePiece.X) {
                playerUtility += 75;
            }
        }
        // check two in a row vertically
        if (col + 1 < BOARD_SIZE) {
            if (tiles[row][col] == GamePiece.X && tiles[row][col + 1] == GamePiece.X) {
                playerUtility += 10;
            }
        }
        if (col - 1 < BOARD_SIZE) {
            if (tiles[row][col] == GamePiece.X && tiles[row][col - 1] == GamePiece.X) {
                playerUtility += 10;
            }
        }

        // check opponent moves vertically
        if (col + 3 < BOARD_SIZE) {
            if (tiles[row][col] == GamePiece.O && tiles[row][col + 1] == GamePiece.O &&
                    tiles[row][col + 2] == GamePiece.O && tiles[row][col + 3] == GamePiece.O) {
                opponentUtility += 10000;
            }
        }
        if (col - 3 < BOARD_SIZE) {
            if (tiles[row][col] == GamePiece.O && tiles[row][col - 1] == GamePiece.O &&
                    tiles[row][col - 2] == GamePiece.O && tiles[row][col - 3] == GamePiece.O) {
                opponentUtility += 10000;
            }
        }
        // check three in a row vertically
        if (col + 2 < BOARD_SIZE) {
            if (tiles[row][col] == GamePiece.O && tiles[row][col + 1] == GamePiece.O &&
                    tiles[row][col + 2] == GamePiece.O) {
                opponentUtility += 75;
            }
        }
        if (col - 2 < BOARD_SIZE) {
            if (tiles[row][col] == GamePiece.O && tiles[row][col - 1] == GamePiece.O &&
                    tiles[row][col - 2] == GamePiece.O) {
                opponentUtility += 75;
            }
        }
        // check two in a row vertically
        if (col + 1 < BOARD_SIZE) {
            if (tiles[row][col] == GamePiece.O && tiles[row][col + 1] == GamePiece.O) {
                opponentUtility += 10;
            }
        }
        if (col - 1 < BOARD_SIZE) {
            if (tiles[row][col] == GamePiece.O && tiles[row][col - 1] == GamePiece.O) {
                opponentUtility += 10;
            }
        }
        return playerUtility - opponentUtility;
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