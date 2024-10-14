package main;

import pieces.Pieces;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Input extends MouseAdapter {

    Board board;

    public Input(Board board) {
        this.board = board;

    }


    @Override
    public void mousePressed(MouseEvent mouseEvent) {

        int col = mouseEvent.getX() / board.tileSize;
        int row = mouseEvent.getY() / board.tileSize;

        Pieces PieceXY = board.getPieces(col,row);
        if (PieceXY!=null){
            board.selectedPiece = PieceXY;

        }

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

        int col = mouseEvent.getX() / board.tileSize;
        int row = mouseEvent.getY() / board.tileSize;

        if (board.selectedPiece != null){
            Move move = new Move(board,board.selectedPiece,col,row);

            if (board.isValidMove(move)){
                board.makeMove(move);
            }
            else{
                board.selectedPiece.xPos = board.selectedPiece.col * board.tileSize;
                board.selectedPiece.yPos = board.selectedPiece.row * board.tileSize;

            }

        }

        board.selectedPiece = null;
        board.repaint();

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

        if (board.selectedPiece != null){
            board.selectedPiece.xPos = mouseEvent.getX() - board.tileSize/2;
            board.selectedPiece.yPos = mouseEvent.getY() - board.tileSize/2;

            board.repaint();
        }

    }

}
