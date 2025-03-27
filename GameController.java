import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class GameController {
    private GameState gameState;

    GameController() {
        this.gameState = new GameState(Constants.X_SIZE, Constants.Y_SIZE);
    }

    void playGame() {
        while (gameState.maxScore < Constants.WINNING_SCORE && gameState.minScore < Constants.WINNING_SCORE) {
            gameState.printBoard();
            if (gameState.turn == Turn.MAX) {
                maxMove();
            } else {
                minMove();
            }
        }
        System.out.println("Game Over");
    }

    void maxMove() {
        System.out.println("Calculating best move for MAX!");
        MinMax minMax = new MinMax(gameState);
        int maxScore = Integer.MIN_VALUE;
        List<Integer> bestMove = new ArrayList<>();
        for (MinMax.Node node : minMax.tree.root.children) {
            if (node.score > maxScore) {
                bestMove = node.position;
                maxScore = node.score;
                gameState.minTip = "";
                int minScore = Integer.MAX_VALUE;
                for (MinMax.Node child : node.children) {
                    if (child.score < minScore) {
                        minScore = child.score;
                        gameState.minTip = child.position.get(0) + " " + child.position.get(1);
                    }
                }
            }
        }
        try {
            gameState.setMove(bestMove.get(0), bestMove.get(1));
        } catch (Exception e) {
            System.out.println("Invalid move");
        }
    }

    void minMove() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the x and y coordinates of the cell you want to pick:");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        try {
            gameState.setMove(x, y);
        } catch (Exception e) {
            System.out.println("Invalid move");

        }
    }

}
