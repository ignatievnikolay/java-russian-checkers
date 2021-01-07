package checkers;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;

class Move {
    @Getter private PieceColor pieceColor;
    private boolean dame;
    @Getter private MoveType type;
    @Getter private ArrayList<Coordinates> coordsSequence = new ArrayList<>();

    Move(String moveNotation, PieceColor pieceColor) {
        this.pieceColor = pieceColor;
        ArrayList<String> coordsSequenceNotation = new ArrayList<>(Arrays.asList(moveNotation.split("-")));
        if (coordsSequenceNotation.size() > 1) {
            type = MoveType.STEP;

        } else {
            coordsSequenceNotation = new ArrayList<>(Arrays.asList(moveNotation.split(":")));
            type = MoveType.TAKE;
        }
        coordsSequenceNotation.forEach(
                coordsNotation -> coordsSequence.add(new Coordinates(coordsNotation, this.pieceColor))
        );
        dame = coordsSequence.get(0).isDame();
        coordsSequence.forEach(coords -> coords.setDame(coordsSequence.get(0).isDame()));
    }

    boolean isDame() {
        return dame;
    }
}
