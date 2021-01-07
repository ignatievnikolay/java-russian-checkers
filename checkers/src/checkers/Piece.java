package checkers;

import lombok.Getter;

class Piece {
    @Getter private PieceColor color;
    private boolean dame;

    Piece(PieceColor _color) {
        color = _color;
        dame = false;
    }

    Piece(PieceColor _color, boolean _dame) {
        color = _color;
        dame = _dame;
    }

    boolean isDame() {
        return dame;
    }

    boolean isNone() {
        return color == PieceColor.NONE;
    }

    void makeDame() {
        dame = true;
    }
}
