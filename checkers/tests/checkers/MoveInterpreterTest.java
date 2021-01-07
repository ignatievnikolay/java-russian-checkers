package checkers;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Native;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class MoveInterpreterTest {

    @Test
    void StepMoveTest() throws IllegalMoveException {
        ArrayList<String> whitePosition = new ArrayList<>(Collections.singletonList("a1"));
        ArrayList<String> blackPosition = new ArrayList<>();
        Board board = new Board(whitePosition, blackPosition);
        MoveInterpreter interpreter = new MoveInterpreter(board);
        interpreter.playMove(new Move("a1-b2", PieceColor.WHITE));
        assert board.showPosition().equals(new ArrayList<>(Arrays.asList(
                new ArrayList<>(Collections.singletonList("b2")), new ArrayList<>()
        )));
    }

    @Test
    void DoubleTakeBecomesDameTest() throws IllegalMoveException {
        ArrayList<String> whitePosition = new ArrayList<>(Arrays.asList("b2", "d2"));
        ArrayList<String> blackPosition = new ArrayList<>(Collections.singletonList("a3"));
        Board board = new Board(whitePosition, blackPosition);
        MoveInterpreter interpreter = new MoveInterpreter(board);
        interpreter.playMove(new Move("a3:c1:e3", PieceColor.BLACK));
        assert board.showPosition().equals(new ArrayList<>(Arrays.asList(
                new ArrayList<>(), new ArrayList<>(Collections.singletonList("E3"))
        )));
    }

    @Test
    void DameDoubleTakesTest() throws IllegalMoveException {
        ArrayList<String> whitePosition = new ArrayList<>(Collections.singletonList("A3"));
        ArrayList<String> blackPosition = new ArrayList<>(Arrays.asList("B4", "f4"));
        Board board = new Board(whitePosition, blackPosition);
        MoveInterpreter interpreter = new MoveInterpreter(board);
        interpreter.playMove(new Move("A3:d6:h2", PieceColor.WHITE));
        assert board.showPosition().equals(new ArrayList<>(Arrays.asList(
                new ArrayList<>(Collections.singleton("H2")), new ArrayList<>()
        )));
    }

    @Test
    void DameTurkishTakeTest() {
        ArrayList<String> whitePosition = new ArrayList<>(Collections.singletonList("C3"));
        ArrayList<String> blackPosition = new ArrayList<>(Arrays.asList("b2", "F6"));
        Board board = new Board(whitePosition, blackPosition);
        MoveInterpreter interpreter = new MoveInterpreter(board);
        try {
            interpreter.playMove(new Move("C3:a1:g7", PieceColor.WHITE));
        } catch (IllegalMoveException e) {
            assert true;
            return;
        }
        assert false;
    }
}
