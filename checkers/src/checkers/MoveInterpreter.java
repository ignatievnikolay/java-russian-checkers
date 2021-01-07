package checkers;

import java.util.ArrayList;
import java.util.HashSet;

class MoveInterpreter {
    private Board board;
    private RulesChecker checker = new RulesChecker();

    MoveInterpreter(Board board) {
        this.board = board;
    }

    /**
     * Проверяет, корректен ли ход и исполняет его
     * @param move ход, который нужно исполнить
     * @throws IllegalMoveException если ход некорректен
     */
    void playMove(Move move) throws IllegalMoveException {
        checker.commonChecks(move, board);
        if (move.getType() == MoveType.STEP) {
            playStepMove(move);
        } else {
            playTakeMove(move);
        }
    }

    /**
     * Проверяет корректен ли ход без взятия и исполняет его.
     * @param move ход, который нужно исполнить.
     * @throws IllegalMoveException если ход некорректен.
     */
    private void playStepMove(Move move) throws IllegalMoveException {
        checker.stepMoveCheck(move, board);
        ArrayList<Coordinates> movePair = move.getCoordsSequence();
        board.movePiece(movePair.get(0), movePair.get(1));
        if (becomesDame(move)) {
            board.getPieceByCoords(movePair.get(1)).makeDame();
        }
    }

    /**
     * Проверяет корректен ли ход со взятием и исполняет его.
     * @param move ход, который нужно исполнить.
     * @throws IllegalMoveException если ход некорректен.
     */
    private void playTakeMove(Move move) throws IllegalMoveException {
        HashSet<Coordinates> takenPieces = checker.takeMoveCheck(move, board);
        ArrayList<Coordinates> moveCoordsSequence = move.getCoordsSequence();
        board.movePiece(moveCoordsSequence.get(0), moveCoordsSequence.get(moveCoordsSequence.size() - 1));
        takenPieces.forEach(coords -> board.deletePiece(coords));
        if (becomesDame(move)) {
            board.getPieceByCoords(moveCoordsSequence.get(moveCoordsSequence.size() - 1)).makeDame();
        }
    }


    /**
     * Проверяет, должна ли шашка стать дамкой после сделанного хода.
     * @param move сделанный ход.
     * @return true если должна и false иначе.
     */
    private boolean becomesDame(Move move) {
        return move.getCoordsSequence().stream().anyMatch(coords -> coords.isLastRank(board));
    }
}
