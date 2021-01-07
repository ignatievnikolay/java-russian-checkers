package checkers;

import lombok.Getter;

class Cell {
    private int rank;
    private int file;
    @Getter private Piece piece;
    private CellColor color;

    Cell(int _rank, int _file) {
        rank = _rank;
        file = _file;
        color = (rank + file) % 2 == 0 ? CellColor.BLACK : CellColor.WHITE;
        piece = new Piece(PieceColor.NONE);
    }

    void deletePiece() {
        piece = new Piece(PieceColor.NONE);
    }

    boolean isEmpty() {
        return piece.isNone();
    }

    boolean isWhite() {
        return color == CellColor.WHITE;
    }

    void placePiece(PieceColor color, boolean dame) {
        piece = new Piece(color, dame);
    }

    void placePiece(Piece piece) {
        this.piece = piece;
    }
}
