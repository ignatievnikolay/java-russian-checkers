package checkers;

import lombok.Getter;

class PiecePosition {
    @Getter private int rank;
    @Getter private int file;

    PiecePosition(int rank, int file) {
        this.rank = rank;
        this.file = file;
    }
}
