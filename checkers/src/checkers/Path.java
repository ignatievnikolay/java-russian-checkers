package checkers;

import java.util.ArrayList;
import java.util.HashSet;

class Path {
    private Coordinates from;
    private Coordinates to;
    private ArrayList<Coordinates> route;

    Path(Coordinates from, Coordinates to) {
        this.from = from;
        this.to = to;
    }

    boolean canStep(Board board) {
        return from.isReachable(to) && isFree(board);
    }

    private boolean isFree(Board board) {
        ArrayList<Coordinates> route;
        try {
            route = from.getRouteTo(to);
        } catch (InvalidCoordinatesException e) {
            return false;
        }
        return route.stream().allMatch(board::cellIsEmpty);
    }

    Coordinates canTake(Board board, HashSet<Coordinates> takenPieces) throws IllegalMoveException {
        ArrayList<Coordinates> route;
        try {
            route = from.getRouteTo(to);
        } catch (InvalidCoordinatesException e) {
            throw new IllegalMoveException("error");
        }
        // check that last cell is free
        if (!board.getPieceByCoords(route.get(route.size() - 1)).isNone()) {
            throw new IllegalMoveException("busy cell");
        }
        // check that there is exactly one piece of opposite color
        PieceColor pieceColor = from.getPieceColor();
        long routeTakeableCount = route
                .stream()
                .filter(coords -> board.getPieceByCoords(coords).getColor() == pieceColor.oppositeColor())
                .count();
        if (routeTakeableCount > 1) {
            throw new IllegalMoveException("error");
        }
        // check that there are no pieces of the same color
        if (route.stream().anyMatch(coords -> board.getPieceByCoords(coords).getColor() == pieceColor)) {
            throw new IllegalMoveException("error");
        }
        for (Coordinates coords : route) {
            if (board.getPieceByCoords(coords).getColor() == pieceColor.oppositeColor()) {
                return coords;
            }
        }
        throw new IllegalMoveException("error");
    }
}
