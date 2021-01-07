package checkers;

class IllegalMoveException extends Exception {
    IllegalMoveException(String errorMessage) {
        super(errorMessage);
    }
}