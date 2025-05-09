import java.util.ArrayList;
import java.util.List;

class GameState {
    private int xSize;
    private int ySize;
    public int minScore;
    public int maxScore;
    public String minTip;

    public Turn turn;

    // board for the game
    public List<List<Character>> board;

    GameState(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        int xRandom = (int) (Math.random() * xSize);
        int yRandom = (int) (Math.random() * ySize);
        int xRandom2 = xRandom;
        int yRandom2 = yRandom;
        // while the 2nd random position is too close to the first one,
        // generate a new one

        while (Math.abs(xRandom - xRandom2) < 2 && Math.abs(yRandom - yRandom2) < 2) {
            xRandom2 = (int) (Math.random() * xSize);
            yRandom2 = (int) (Math.random() * xSize);
        }
        this.board = initBoard();
        this.turn = Constants.STARTING_TURN;
        this.minScore = 0;
        this.maxScore = 0;
    }

    // Deep copy
    GameState(GameState gameState) {
        this.xSize = gameState.xSize;
        this.ySize = gameState.ySize;
        this.board = new ArrayList<>();
        for (int i = 0; i < xSize; i++) {
            List<Character> row = new ArrayList<>();
            for (int j = 0; j < ySize; j++) {
                row.add(gameState.board.get(i).get(j));
            }
            this.board.add(row);
        }
        this.turn = gameState.turn;
        this.minScore = gameState.minScore;
        this.maxScore = gameState.maxScore;
    }

    private List<List<Character>> initBoard() {
        int xRandom = (int) (Math.random() * xSize);
        int yRandom = (int) (Math.random() * ySize);
        int xRandom2 = xRandom;
        int yRandom2 = yRandom;
        // while the 2nd random position is too close to the first one,
        // generate a new one
        while (Math.abs(xRandom - xRandom2) < 2 && Math.abs(yRandom - yRandom2) < 2) {
            xRandom2 = (int) (Math.random() * xSize - 1);
            yRandom2 = (int) (Math.random() * xSize - 1);
        }
        List<List<Character>> board = new ArrayList<>();
        for (int i = 0; i < xSize; i++) {
            List<Character> row = new ArrayList<>();
            for (int j = 0; j < ySize; j++) {
                if (i == xRandom && j == yRandom) {
                    row.add('X');
                } else if (i == xRandom2 && j == yRandom2) {
                    row.add('O');
                } else {
                    row.add('-');
                }
            }
            board.add(row);
        }
        return board;
    }

    void setMove(int x, int y) {
        if (x < 0 || x >= xSize || y < 0 || y >= ySize) {
            throw new IllegalArgumentException("Invalid move");
        }
        if (board.get(x).get(y) != '-') {
            throw new IllegalArgumentException("Invalid move");
        }
        board.get(x).set(y, turn.symbol());
        turn = turn.next();
        Turn win = isBoardWin();
        if (win != null) {
            printBoard();
            if (win == Turn.MAX) {
                maxWin();
            } else {
                minWin();
            }
        } else if (isBoardFull()) {
            board = initBoard();
            turn = Turn.MAX;
        }
    }

    char getCell(int x, int y) {
        if (x < 0 || x >= xSize || y < 0 || y >= ySize) {
            return '-';
        }
        return board.get(x).get(y);
    }

    Turn isBoardWin() {
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                if (getCell(i, j) == 'X') {
                    if (getCell(i - 1, j) == 'O' && getCell(i + 1, j) == 'O') {
                        return Turn.MIN;
                    }
                    if (getCell(i, j - 1) == 'O' && getCell(i, j + 1) == 'O') {
                        return Turn.MIN;
                    }
                    if (getCell(i - 1, j - 1) == 'O' && getCell(i + 1, j + 1) == 'O') {
                        return Turn.MIN;
                    }
                    if (getCell(i - 1, j + 1) == 'O' && getCell(i + 1, j - 1) == 'O') {
                        return Turn.MIN;
                    }
                }
                if (getCell(i, j) == 'O') {
                    if (getCell(i - 1, j) == 'X' && getCell(i + 1, j) == 'X') {
                        return Turn.MAX;
                    }
                    if (getCell(i, j - 1) == 'X' && getCell(i, j + 1) == 'X') {
                        return Turn.MAX;
                    }
                    if (getCell(i - 1, j - 1) == 'X' && getCell(i + 1, j + 1) == 'X') {
                        return Turn.MAX;
                    }
                    if (getCell(i - 1, j + 1) == 'X' && getCell(i + 1, j - 1) == 'X') {
                        return Turn.MAX;
                    }
                }
            }
        }
        return null;
    }

    boolean isBoardFull() {
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                if (board.get(i).get(j) == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    void minWin() {
        System.out.println("MIN wins");
        board = initBoard();
        turn = Constants.STARTING_TURN;
        minScore++;
    }

    void maxWin() {
        System.out.println("MAX wins");
        board = initBoard();
        turn = Constants.STARTING_TURN;
        maxScore++;
    }

    List<List<Integer>> getPossibleMoves() {
        List<List<Integer>> possibleMoves = new ArrayList<>();
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                if (board.get(i).get(j) == '-') {
                    List<Integer> move = new ArrayList<>();
                    move.add(i);
                    move.add(j);
                    possibleMoves.add(move);
                }
            }
        }
        return possibleMoves;
    }

    void printBoard() {
        System.out.println("-------------------- Score " + maxScore + ":" + minScore
                + " (MAX : MIN) --------------------");
        for (int i = 0; i < Constants.X_SIZE; i++) {
            System.out.print("|");
            for (int j = 0; j < Constants.Y_SIZE; j++) {
                System.out.print(board.get(i).get(j));
                System.out.print("|");
            }
            if (i == 0) {
                System.out.println("\t\tTurn: " + turn.name());
            } else if (i == 1 && minTip != "" && turn == Turn.MIN) {
                System.out.println("\t\tSuggest Move is: " + minTip);
            } else {

                System.out.println();
            }
        }
    }
}
