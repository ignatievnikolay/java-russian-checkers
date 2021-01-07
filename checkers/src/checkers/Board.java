package checkers;

import lombok.Getter;

import java.util.*;

class Board {
    @Getter private final int rankNum = 8;
    @Getter private final int fileNum = 8;
    private ArrayList<ArrayList<Cell>> board = new ArrayList<>(rankNum);
    @Getter HashMap<PieceColor, HashSet<PiecePosition>> piecesPositions = new HashMap<>();

    Board(ArrayList<String> whitePosition, ArrayList<String> blackPosition) {
        createBoard();
        piecesPositions.put(PieceColor.WHITE, new HashSet<>());
        piecesPositions.put(PieceColor.BLACK, new HashSet<>());
        initPosition(whitePosition, PieceColor.WHITE);
        initPosition(blackPosition, PieceColor.BLACK);
    }

    boolean cellIsEmpty(Coordinates coords) {
        return board.get(coords.getRank()).get(coords.getFile()).isEmpty();
    }

    private Piece getPiece(int rank, int file) {
        return board.get(rank).get(file).getPiece();
    }

    Piece getPieceByCoords(Coordinates coords) {
        return board.get(coords.getRank()).get(coords.getFile()).getPiece();
    }

    private void createBoard() {
        for(int rank = 0; rank < rankNum; ++rank) {
            board.add(new ArrayList<>(8));
            for(int file = 0; file < fileNum; ++file) {
                board.get(rank).add(new Cell(rank, file));
            }
        }
    }

    private void initPosition(ArrayList<String> position, PieceColor color) {
        position.forEach(coordsNotation -> placePiece(coordsNotation, color));
    }

    private void placePiece(String coordsNotation, PieceColor color) {
        Coordinates coords = new Coordinates(coordsNotation, color);
        board.get(coords.getRank()).get(coords.getFile()).placePiece(color, coords.isDame());
        PiecePosition piecePosition = new PiecePosition(coords.getRank(), coords.getFile());
        piecesPositions.get(color).add(piecePosition);
    }

    private Cell getCell(int rank, int file) {
        return board.get(rank).get(file);
    }

    void movePiece(Coordinates from, Coordinates to) {
        Cell toCell = getCell(to.getRank(), to.getFile());
        toCell.placePiece(getPiece(from.getRank(), from.getFile()));
        piecesPositions.get(toCell.getPiece().getColor()).add(new PiecePosition(to.getRank(), to.getFile()));
        deletePiece(from);
    }

    void deletePiece(Coordinates coords) {
        Cell cell = board.get(coords.getRank()).get(coords.getFile());
        piecesPositions.get(cell.getPiece().getColor()).removeIf(piecePosition ->
                coords.getRank() == piecePosition.getRank() && coords.getFile() == piecePosition.getFile()
        );
        cell.deletePiece();
    }

    ArrayList<ArrayList<String>> showPosition() {
        ArrayList<ArrayList<String>> positionNotation = new ArrayList<>();
        positionNotation.add(new ArrayList<>());
        positionNotation.add(new ArrayList<>());
        for (int rank = 0; rank < rankNum; ++rank) {
            for (int file = 0; file < fileNum; ++file) {
                if (getPiece(rank, file).getColor() == PieceColor.WHITE) {
                    positionNotation.get(0).add(getNotation(rank, file));
                } else if (getPiece(rank, file).getColor() == PieceColor.BLACK) {
                    positionNotation.get(1).add(getNotation(rank, file));
                }
            }
        }
        Collections.sort(positionNotation.get(0));
        Collections.sort(positionNotation.get(1));
        return positionNotation;
    }

    private String getNotation(int rank, int file) {
        String fileLetter = Character.toString((char) (getPiece(rank, file).isDame() ? 'A' + file : 'a' + file));
        return fileLetter + (rank + 1);
    }
}
