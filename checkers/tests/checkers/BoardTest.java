package checkers;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

class BoardTest {
    private String whitePositionNotation = "a1 a3 b2 c1 c3 d2 e1 e3 f2 g1 g3 h2";
    private String blackPositionNotation = "a7 b6 b8 c7 d6 d8 e7 f6 f8 g7 h6 h8";
    private ArrayList<String> whitePosition = new ArrayList<>(Arrays.asList(whitePositionNotation.split(" ")));
    private ArrayList<String> blackPosition = new ArrayList<>(Arrays.asList(blackPositionNotation.split(" ")));

    @Test
    void ConstructorTest() {
        Board board = new Board(whitePosition, blackPosition);
        assert board.showPosition().equals(
                new ArrayList<>(Arrays.asList(whitePosition, blackPosition))
        );
    }
}
