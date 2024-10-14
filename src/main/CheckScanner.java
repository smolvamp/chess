package main;

import pieces.Pieces;

public class CheckScanner {

    Board board;

    public CheckScanner(Board board) {

        this.board = board;
    }

    public boolean isKingChecked(Move move) {

        Pieces king = board.findKing(move.pieces.isWhite);
        assert king != null;

        int kingCol = king.col;
        int kingRow = king.row;

        if (board.selectedPiece != null && board.selectedPiece.name.equals("King")) {
            kingCol = move.newCol;
            kingRow = move.newRow;
        }
        return  hitByRook(move.newCol, move.newRow, king, kingCol, kingRow, 0, 1) ||//up
                hitByRook(move.newCol, move.newRow, king, kingCol, kingRow, 1, 0) ||//right
                hitByRook(move.newCol, move.newRow, king, kingCol, kingRow, 0, -1) ||//down
                hitByRook(move.newCol, move.newRow, king, kingCol, kingRow, -1, 0) ||//left

                hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, -1, -1) ||//up left
                hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, 1, -1) || // up right
                hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, 1, 1) || // down right
                hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, -1, 1) ||// down left

                hitByKnight(move.newCol, move.newRow, king, kingCol, kingRow) ||

                hitByPawn(move.newCol, move.newRow, king, kingCol, kingRow) ||

                hitByKing(king, kingCol, kingRow);


    }

    private boolean hitByRook(int col, int row, Pieces King, int kingCol, int kingRow, int colVal, int rowVal) {
        for (int i = 1; i < 8; i++) {
            if (kingCol + (i * colVal) == col && kingRow + (i * rowVal) == row) {
                break;
            }
            Pieces pieces = board.getPieces(kingCol + (i * colVal), kingRow + (i * rowVal));
            if (pieces != null && pieces != board.selectedPiece) {
                if (!board.sameTeam(pieces, King) && (pieces.name.equals("Rook") || pieces.name.equals("Queen"))) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean hitByBishop(int col, int row, Pieces King, int kingCol, int kingRow, int colVal, int rowVal) {
        for (int i = 1; i < 8; i++) {
            if (kingCol - (i * colVal) == col && kingRow - (i * rowVal) == row) {
                break;
            }
            Pieces pieces = board.getPieces(kingCol - (i * colVal), kingRow - (i * rowVal));
            if (pieces != null && pieces != board.selectedPiece) {
                if (!board.sameTeam(pieces, King) && (pieces.name.equals("Bishop") || pieces.name.equals("Queen"))) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean hitByKnight(int col, int row, Pieces king, int kingCol, int kingRow) {
        return  checkKnight(board.getPieces(kingCol - 1, kingRow - 2), king, col, row) ||
                checkKnight(board.getPieces(kingCol - 1, kingRow + 2), king, col, row) ||
                checkKnight(board.getPieces(kingCol + 1, kingRow - 2), king, col, row) ||
                checkKnight(board.getPieces(kingCol + 1, kingRow + 2), king, col, row) ||
                checkKnight(board.getPieces(kingCol - 2, kingRow + 1), king, col, row) ||
                checkKnight(board.getPieces(kingCol - 2, kingRow - 1), king, col, row) ||
                checkKnight(board.getPieces(kingCol + 2, kingRow + 1), king, col, row) ||
                checkKnight(board.getPieces(kingCol + 2, kingRow - 1), king, col, row);
    }

    private boolean checkKnight(Pieces p, Pieces k, int col, int row) {
        return p != null && !board.sameTeam(p, k) && p.name.equals("Knight") && !(p.col == col && p.row == row);
    }

    private boolean hitByKing(Pieces king, int kingCol, int kingRow) {
        return  checkKing(board.getPieces(kingCol - 1, kingRow - 1), king) ||
                checkKing(board.getPieces(kingCol + 1, kingRow - 1), king) ||
                checkKing(board.getPieces(kingCol, kingRow - 1), king) ||
                checkKing(board.getPieces(kingCol - 1, kingRow), king) ||
                checkKing(board.getPieces(kingCol + 1, kingRow), king) ||
                checkKing(board.getPieces(kingCol - 1, kingRow + 1), king) ||
                checkKing(board.getPieces(kingCol + 1, kingRow + 1), king) ||
                checkKing(board.getPieces(kingCol, kingRow + 1), king);
    }

    private boolean checkKing(Pieces p, Pieces k) {
        return p != null && !board.sameTeam(p, k) && p.name.equals("King");
    }

    private boolean hitByPawn(int col, int row, Pieces king, int kingCol, int kingRow) {
        int colVal = king.isWhite ? -1 : 1;
        return  checkPawn(board.getPieces(kingCol + 1, kingRow + colVal), king, col, row) ||
                checkPawn(board.getPieces(kingCol - 1, kingRow + colVal), king, col, row);
    }

    private boolean checkPawn(Pieces p, Pieces k, int col, int row) {
        return p != null && !board.sameTeam(p, k) && p.name.equals("Pawn") && !(p.col == col && p.row == row);
    }

    public boolean isGameOver(Pieces king){
        for (Pieces pieces : board.pieceList){
            if (board.sameTeam(pieces, king)){
                board.selectedPiece = pieces == king ? king : null;
                 for (int row = 0; row< board.rows ; row++){
                     for(int col = 0; col< board.cols; col++){
                         Move move = new Move(board, pieces,col,row);
                         if (board.isValidMove(move)){
                             return false;
                         }
                     }
                 }
            }
        }
        return true;
    }
}