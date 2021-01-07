package checkers;

enum PieceColor {
    WHITE,
    BLACK,
    NONE;

    PieceColor oppositeColor() {
        if (this == WHITE) {
            return BLACK;
        }
        if (this == BLACK) {
            return WHITE;
        }
        return NONE;
    }
}
