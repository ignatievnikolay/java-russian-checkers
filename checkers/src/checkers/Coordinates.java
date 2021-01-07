package checkers;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

class Coordinates {
    @Getter private int rank;
    @Getter private int file;
    @Getter private CellColor cellColor;
    @Getter private PieceColor pieceColor;
    @Setter private boolean dame;
    private final char lowercaseFileStart = 'a';
    private final char uppercaseFileStart = 'A';

    Coordinates(String coordsNotation, PieceColor pieceColor) {
        this.pieceColor = pieceColor;
        rank = Integer.parseInt(Character.toString(coordsNotation.charAt(1))) - 1;
        char fileLetter = coordsNotation.charAt(0);
        if (Character.isLowerCase(fileLetter)) {
            file = fileLetter - lowercaseFileStart;
            dame = false;
        } else {
            file = fileLetter - uppercaseFileStart;
            dame = true;
        }
        cellColor = (rank + file) % 2 == 0 ? CellColor.BLACK : CellColor.WHITE;
    }

    Coordinates(int rank, int file, PieceColor pieceColor) {
        this.rank = rank;
        this.file = file;
        this.pieceColor = pieceColor;
    }

    /**
     * Проверяет, может ли шашка (с учётом того, является она дамкой или нет) в данной координате за один обычный ход
     * дойти до заданного поля или нет.
     * @param other поле, до которого нужно дойти.
     * @return true, если может дойти за один обычный ход и false, если не может.
     */
    boolean isReachable(Coordinates other) {
        return this.isDame() ? sameDiagonal(other) : isNeighbor(other);
    }

    private boolean isNeighbor(Coordinates other) {
        return Math.abs(other.rank - this.rank) == 1 && Math.abs(other.file - this.file) == 1;
    }

    private boolean sameDiagonal(Coordinates other) {
        return Math.abs(other.rank - this.rank) == Math.abs(other.file - this.file);
    }

    /**
     * Возвращает массив координат клеток, образующий путь от клетки this до клетки из аргуменат to (this, to]
     * (включая to и исключая this).
     * Кидает InvalidCoordinatesException в случае, если клетки не лежат на одной диагонали.
     * @param to координаты клетки, до которой нужно вернуть маршрут
     * @return ArrayList Coordinates клеток, образующих путь (this, to]
     * @throws InvalidCoordinatesException, если клетки лежат не на одной диагонали
     */
    ArrayList<Coordinates> getRouteTo(Coordinates to) throws InvalidCoordinatesException {
        if (!this.sameDiagonal(to)) {
            throw new InvalidCoordinatesException("different diagonals");
        } else {
            int rankDelta = (int) Math.signum(to.rank - this.rank);
            int fileDelta = (int) Math.signum(to.file - this.file);
            int curRank = this.rank;
            int curFile = this.file;
            ArrayList<Coordinates> path = new ArrayList<>();
            while (curRank != to.rank || curFile != to.file) {
                curRank += rankDelta;
                curFile += fileDelta;
                path.add(new Coordinates(curRank, curFile, PieceColor.NONE));
            }
            return path;
        }
    }

    boolean isDame() {
        return dame;
    }

    boolean isWhite() {
        return cellColor == CellColor.WHITE;
    }

    boolean outOfBounds(Board board) {
        return rank < 0 || rank >= board.getRankNum() || file < 0 || file >= board.getFileNum();
    }

    /**
     * Проверяет, является ли клетка для данного цвета шашки последним рядом (в случае 8х8: 8 для белых и 1 для чёрных).
     * @param board доска, для которой проверяется условие.
     * @return true, если клетка в последнем ряду и false иначе.
     */
    boolean isLastRank(Board board) {
        return (pieceColor == PieceColor.WHITE && rank == board.getRankNum() - 1
                || pieceColor == PieceColor.BLACK && rank == 0);
    }
}
