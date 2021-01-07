package checkers;

import java.util.ArrayList;
import java.util.Arrays;

class Game {
    private Board board;
    private ArrayList<String> gameNotation;
    private MoveInterpreter interpreter;

    Game(ArrayList<String> gameNotation) {
        this.gameNotation = gameNotation;
        ArrayList<String> whitePosition = new ArrayList<>(Arrays.asList(this.gameNotation.get(0).split(" ")));
        ArrayList<String> blackPosition = new ArrayList<>(Arrays.asList(this.gameNotation.get(1).split(" ")));
        board = new Board(whitePosition, blackPosition);
        interpreter = new MoveInterpreter(board);
    }

    void run() throws IllegalMoveException {
        for (int i = 2; i < gameNotation.size(); i++) {
            ArrayList<String> doubleMove = new ArrayList<>(Arrays.asList(gameNotation.get(i).split(" ")));
            Move whiteMove = new Move(doubleMove.get(0), PieceColor.WHITE);
            Move blackMove = new Move(doubleMove.get(1), PieceColor.BLACK);
            interpreter.playMove(whiteMove);
            interpreter.playMove(blackMove);
        }
    }

    ArrayList<ArrayList<String>> getPosition() {
        return board.showPosition();
    }
}
