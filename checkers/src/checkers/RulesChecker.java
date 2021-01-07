package checkers;

import java.util.ArrayList;
import java.util.HashSet;

class RulesChecker {

    /**
     * Осуществляет проверки, актуальные для любого типа хода.
     * @param move ход, который нужно проверить.
     * @param board актуальное состояние доски.
     * @throws IllegalMoveException если ход некорректен.
     */
    void commonChecks(Move move, Board board) throws IllegalMoveException {
        noWhiteCells(move);
        pieceColorMatches(move, board);
        isDameMatches(move, board);
    }

    /**
     * Проверяет, содержит ли ход клетки белого цвета.
     * @param move ход, который нужно проверить.
     * @throws IllegalMoveException если ход содержит клетки белого цвета.
     */
    private void noWhiteCells(Move move) throws IllegalMoveException {
        if (move.getCoordsSequence().stream().anyMatch(Coordinates::isWhite)) {
            throw new IllegalMoveException("white cell");
        }
    }

    /**
     * Проверяет, свопадает ли цвет шашки с цветом шашки на доске.
     * @param move ход, который ужно проверить.
     * @param board доска, с которой нужно свериться.
     * @throws IllegalMoveException если цвет шашки, указанный в ходе не совпадает с цветом шашки на доске.
     */
    private void pieceColorMatches(Move move, Board board) throws IllegalMoveException {
        if (!(move.getPieceColor() == board.getPieceByCoords(move.getCoordsSequence().get(0)).getColor())) {
            throw new IllegalMoveException("error");
        }
    }

    /**
     * Проверяет, совпадает ли статус  шашки со статусом шашки на доске.
     * @param move ход, который нужно проверить
     * @param board доска, с которой нужно свериться.
     * @throws IllegalMoveException если статус шашки, указанный в ходе не совпадает со статусом шашки на доске.
     */
    private void isDameMatches(Move move, Board board) throws IllegalMoveException {
        if (!(move.isDame() == board.getPieceByCoords(move.getCoordsSequence().get(0)).isDame())) {
            throw new IllegalMoveException("error");
        }
    }

    /**
     * Осуществляет проверки, актуальные для хода без взятия.
     * @param move ход, который нужно проверить.
     * @param board актуальное состояние доски.
     * @throws IllegalMoveException если ходе некорректен.
     */
    void stepMoveCheck(Move move, Board board) throws IllegalMoveException {
        targetCellEmpty(move, board);
        ArrayList<Coordinates> coordsPair = move.getCoordsSequence();
        if (!(new Path(coordsPair.get(0), coordsPair.get(1))).canStep(board)) {
            throw new IllegalMoveException("error");
        }
        noneCanTake(move, board);
    }


    /**
     * Проверяет, сводна ли клетку, в которую нужно прийти.
     * @param move ход, который нужно проверить.
     * @param board актуальное состояние доски.
     * @throws IllegalMoveException если целевая клетка занята.
     */
    private void targetCellEmpty(Move move, Board board) throws IllegalMoveException {
        if (!board.cellIsEmpty(move.getCoordsSequence().get(1))) {
            throw new IllegalMoveException("busy cell");
        }
    }

    private void noneCanTake(Move move, Board board) throws IllegalMoveException {
        for (PiecePosition piecePosition : board.getPiecesPositions().get(move.getPieceColor())) {
            canNotTake(
                    new Coordinates(piecePosition.getRank(), piecePosition.getFile(), move.getPieceColor()),
                    board,
                    new HashSet<>()
            );
        }
    }

    private void canNotTake(Coordinates coords, Board board, HashSet<Coordinates> takenPieces)
            throws IllegalMoveException {
        if (canTakeDirection(coords, board, -1, -1, takenPieces)) {
            throw new IllegalMoveException("invalid move");
        } else if (canTakeDirection(coords, board, -1, 1, takenPieces)) {
            throw new IllegalMoveException("invalid move");
        } else if (canTakeDirection(coords, board, 1, -1, takenPieces)) {
            throw new IllegalMoveException("invalid move");
        } else if (canTakeDirection(coords, board, 1, 1, takenPieces)) {
            throw new IllegalMoveException("invalid move");
        }
    }

    private boolean canTakeDirection(Coordinates coords, Board board, int rankDelta, int fileDelta,
                                     HashSet<Coordinates> takenPieces) {
        if (!coords.isDame()) {
            return !takenPieces.contains(coords) && canTakeDirectionNotDame(coords, board, rankDelta, fileDelta);
        } else {
            return !takenPieces.contains(coords) && canTakeDirectionDame(coords, board, rankDelta, fileDelta);
        }
    }

    private boolean canTakeDirectionNotDame(Coordinates coords, Board board, int rankDelta, int fileDelta) {
        Coordinates takeCoords = new Coordinates(
                coords.getRank() + rankDelta,
                coords.getFile() + fileDelta,
                board.getPieceByCoords(coords).getColor().oppositeColor()
        );
        Coordinates landCoords = new Coordinates(
                coords.getRank() + 2 * rankDelta,
                coords.getFile() + 2 * fileDelta,
                PieceColor.NONE
        );
        if (takeCoords.outOfBounds(board) || landCoords.outOfBounds(board)) {
            return false;
        }
        return takeCoords.getPieceColor() == board.getPieceByCoords(takeCoords).getColor()
                && landCoords.getPieceColor() == board.getPieceByCoords(landCoords).getColor();
    }

    private boolean canTakeDirectionDame(Coordinates pieceCoords, Board board, int rankDelta, int fileDelta) {
        PieceColor pieceColor = board.getPieceByCoords(pieceCoords).getColor();
        Coordinates curCoords = new Coordinates(
                pieceCoords.getRank() + rankDelta,
                pieceCoords.getFile() + fileDelta,
                pieceColor.oppositeColor()
        );
        boolean legal = true;
        int counter = 0;
        if (curCoords.outOfBounds(board) || board.getPieceByCoords(curCoords).getColor() == pieceColor) {
            legal = false;
        }
        while (legal && board.getPieceByCoords(curCoords).getColor() != pieceColor.oppositeColor()) {
            ++counter;
            curCoords = new Coordinates (
                    pieceCoords.getRank() + counter * rankDelta,
                    pieceCoords.getFile() + counter * fileDelta,
                    pieceColor.oppositeColor()
            );
            if (curCoords.outOfBounds(board) || board.getPieceByCoords(curCoords).getColor() == pieceColor) {
                legal = false;
                break;
            }
        }
        ++counter;
        Coordinates landingCell = new Coordinates(
                pieceCoords.getRank() + counter * rankDelta,
                pieceCoords.getFile() + counter * fileDelta,
                PieceColor.NONE
        );
        if (landingCell.outOfBounds(board)) {
            legal = false;
        }
        return  legal && board.getPieceByCoords(landingCell).getColor() == PieceColor.NONE;
    }

    /**
     * Осуществляет проверки, актуальные для входа со взятием.
     * @param move ход, который нужно проверить.
     * @param board актуальное состояние доски.
     * @return HashSet Coordinates шашек, схеденных в результате данного хода.
     * @throws IllegalMoveException если ход некореектен.
     */
    HashSet<Coordinates> takeMoveCheck(Move move, Board board) throws IllegalMoveException {
        ArrayList<Coordinates> coordsSequence = move.getCoordsSequence();
        HashSet<Coordinates> takenPieces = new HashSet<>();
        for (int i = 0; i < coordsSequence.size() - 1; ++i) {
            Path path = new Path(coordsSequence.get(i), coordsSequence.get(i + 1));
            takenPieces.add(path.canTake(board, takenPieces));
        }

        return takenPieces;
    }
}