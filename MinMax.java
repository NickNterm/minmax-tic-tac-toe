import java.util.ArrayList;
import java.util.List;

class MinMax {
    public class Node {
        GameState gameState;
        List<Integer> position;
        boolean isMax;
        List<Node> children;
        int score;

        Node(boolean isMax, GameState gameState, List<Integer> position) {
            this.gameState = gameState;
            this.isMax = isMax;
            this.position = position;
            this.children = new ArrayList<>();
        }
    }

    public class Tree {
        Node root;

        Tree(Node root) {
            this.root = root;
            generateTree(root);
            scoreTree(root, 0);
        }

    }

    public Tree tree;

    MinMax(GameState gameState) {
        tree = new Tree(
                new Node(true, gameState, new ArrayList<>()));
    }

    void generateTree(Node parentNode) {
        List<List<Integer>> positonList = parentNode.gameState.getPossibleMoves();
        boolean isChildMaxPlayer = !parentNode.isMax;
        for (List<Integer> position : positonList) {
            GameState newGameState = new GameState(parentNode.gameState);

            newGameState.board.get(position.get(0)).set(position.get(1), newGameState.turn.symbol());
            newGameState.turn = newGameState.turn.next();
            Node newNode = new Node(isChildMaxPlayer, newGameState, position);
            parentNode.children.add(newNode);
            if (newGameState.isBoardWin() == null) {
                if (!newGameState.isBoardFull()) {
                    generateTree(newNode);
                }
            } else {
                if (newGameState.isBoardWin() == Turn.MAX) {
                    // GameController.printGame(newGameState);
                }
            }
        }

    }

    void scoreTree(Node parentNode, int depth) {
        if (parentNode.children.size() == 0) {
            if (parentNode.gameState.isBoardWin() == Turn.MAX) {
                parentNode.score = 100 - depth;
            } else if (parentNode.gameState.isBoardWin() == Turn.MIN) {
                parentNode.score = depth - 100;
            } else {
                parentNode.score = 0;
            }
        } else {
            if (parentNode.isMax) {
                int maxScore = Integer.MIN_VALUE;
                for (Node child : parentNode.children) {
                    scoreTree(child, depth + 1);
                    maxScore = Math.max(maxScore, child.score);
                }
                parentNode.score = maxScore;
            } else {
                int minScore = Integer.MAX_VALUE;
                for (Node child : parentNode.children) {
                    scoreTree(child, depth + 1);
                    minScore = Math.min(minScore, child.score);
                }
                parentNode.score = minScore;
            }
        }
    }

}
