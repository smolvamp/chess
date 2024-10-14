package main;

import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class Board extends JPanel {

    public int tileSize= 85;

    int cols = 8;
    int rows = 8;

    ArrayList<Pieces> pieceList = new ArrayList<>();

    public Pieces selectedPiece;

    Input input =  new Input(this);

    public int enPassantTile = -1;

    private boolean isWhiteToMove = true;
    private boolean isGameOver = false;


    public CheckScanner checkScanner = new CheckScanner(this);


    public Board(){
        this.setPreferredSize(new Dimension(cols*tileSize , rows*tileSize));
        this.addMouseListener(input);
        this.addMouseMotionListener(input);

        addPieces();
    }

    public Pieces getPieces(int col, int row){

        for (Pieces pieces : pieceList){
            if ((pieces.col == col )&& (pieces.row == row)){
                return pieces;
            }
        }
        return null;
    }

    public void makeMove(Move move) {

        if (move.pieces.name.equals("Pawn")) {

            movePawn(move);
        }
        else if (move.pieces.name.equals("King")) {

            moveKing(move);

        }


            move.pieces.col = move.newCol;
            move.pieces.row = move.newRow;
            move.pieces.xPos = move.newCol * tileSize;
            move.pieces.yPos = move.newRow * tileSize;
            move.pieces.isFirstMove = false;

            capture(move.capture);
            isWhiteToMove = !isWhiteToMove;

            updateGameState();

    }

    private void moveKing(Move move){

        if (Math.abs(move.pieces.col- move.newCol)==2){
            Pieces rook;
            if (move.pieces.col < move.newCol){
                rook = getPieces(7,move.pieces.row);
                rook.col = 5;
            }
            else{
                rook = getPieces(0,move.pieces.row);
                rook.col = 3;
            }
            rook.xPos= rook.col*tileSize;
        }

//        move.pieces.col = move.newCol;
//        move.pieces.row = move.newRow;
//        move.pieces.xPos = move.newCol * tileSize;
//        move.pieces.yPos = move.newRow * tileSize;
//        move.pieces.isFirstMove = false;
//
//        capture(move.capture);

    }

    private void movePawn(Move move){

        int colorIndex = move.pieces.isWhite ? 1:-1;

        if (getTileNum(move.newCol, move.newRow)== enPassantTile) {

            move.capture = getPieces(move.newCol,move.newRow + colorIndex);

        }
        if (Math.abs(move.pieces.row - move.newRow)==2){
            enPassantTile = getTileNum(move.newCol, move.newRow + colorIndex);

        }
        else{
            enPassantTile= -1;
        }

        //promotions
        colorIndex = move.pieces.isWhite? 0 : 7;
        if (move.newRow == colorIndex){
            promotePawn(move);
        }

//        move.pieces.col = move.newCol;
//        move.pieces.row = move.newRow;
//        move.pieces.xPos = move.newCol * tileSize;
//        move.pieces.yPos = move.newRow * tileSize;
//
//        move.pieces.isFirstMove = false;
//
//        capture(move.capture);

    }

    private void promotePawn(Move move){
        pieceList.add(new Queen(this, move.newCol, move.newRow, move.pieces.isWhite));
        capture(move.pieces);
    }

    public void capture(Pieces pieces){
        pieceList.remove(pieces);
    }

    public boolean isValidMove(Move move){

        if(move.pieces.isWhite !=  isWhiteToMove){
            return false;
        }

        if (sameTeam(move.pieces, move.capture)){
            return false;
        }
        if (!move.pieces.isValidMovement(move.newCol,move.newRow)) {
            return false;
        }

        if (move.pieces.moveCollideWithPiece(move.newCol,move.newRow)) {
            return false;
        }

       if (checkScanner.isKingChecked(move)){
            return false;
        }

        return true;
    }


    public boolean sameTeam(Pieces p1, Pieces p2){
        if (p1==null|| p2==null){
            return false;
        }
        return p1.isWhite == p2.isWhite;
    }


    public int getTileNum(int col, int row){
        return (row*rows) +col;
    }

    Pieces findKing(boolean isWhite){
        for (Pieces pieces : pieceList){
            if (isWhite == pieces.isWhite && pieces.name.equals("King")){
                return pieces;
            }
        }
        return null;
    }


    public void addPieces() {
        pieceList.add(new Rook(this, 0, 0, false));
        pieceList.add(new Knight(this, 1, 0, false));
        pieceList.add(new Bishop(this, 2, 0, false));
        pieceList.add(new Queen(this, 3, 0, false));
        pieceList.add(new King(this, 4, 0, false));
        pieceList.add(new Bishop(this, 5, 0, false));
        pieceList.add(new Knight(this, 6, 0, false));
        pieceList.add(new Rook(this, 7, 0, false));

        pieceList.add(new Pawn(this, 0, 1, false));
        pieceList.add(new Pawn(this, 1, 1, false));
        pieceList.add(new Pawn(this, 2, 1, false));
        pieceList.add(new Pawn(this, 3, 1, false));
        pieceList.add(new Pawn(this, 4, 1, false));
        pieceList.add(new Pawn(this, 5, 1, false));
        pieceList.add(new Pawn(this, 6, 1, false));
        pieceList.add(new Pawn(this, 7, 1, false));

        pieceList.add(new Rook(this, 0, 7, true));
        pieceList.add(new Knight(this, 1, 7, true));
        pieceList.add(new Bishop(this, 2, 7, true));
        pieceList.add(new Queen(this, 3, 7, true));
        pieceList.add(new King(this, 4, 7, true));
        pieceList.add(new Bishop(this, 5, 7, true));
        pieceList.add(new Knight(this, 6, 7, true));
        pieceList.add(new Rook(this, 7, 7, true));

        pieceList.add(new Pawn(this, 0, 6, true));
        pieceList.add(new Pawn(this, 1, 6, true));
        pieceList.add(new Pawn(this, 2, 6, true));
        pieceList.add(new Pawn(this, 3, 6, true));
        pieceList.add(new Pawn(this, 4, 6, true));
        pieceList.add(new Pawn(this, 5, 6, true));
        pieceList.add(new Pawn(this, 6, 6, true));
        pieceList.add(new Pawn(this, 7, 6, true));

    }

    private void updateGameState(){
        Pieces king = findKing(isWhiteToMove);

        if(checkScanner.isGameOver(king)){
            if (checkScanner.isKingChecked(new Move(this, king, king.col, king.row))){
                System.out.println(isWhiteToMove ? "Black won!!": "White WIns!!");

            }
            else{
                System.out.println("Stalemate!!!");
            }
        isGameOver = true;
        }
          else if (inSufficientMaterial(true) && inSufficientMaterial(false)) {
            System.out.println("Insufficient Material!");

            isGameOver = true;
        }
    }
    private boolean inSufficientMaterial(boolean isWhite){
                ArrayList<String> names = pieceList.stream().filter(p->p.isWhite).map(p->p.name).collect(Collectors.toCollection(ArrayList::new));

                if (names.contains("Queen") || names.contains("Rook")|| names.contains("Pawn")){
                    return false;
                }

                return names.size() < 3;
    }

    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
//board ki tiles paint krne k liye
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) {
                g2d.setColor((c + r) % 2 == 0 ? new Color(188, 148, 87) : new Color(95, 60, 32));
                g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);

            }
        //  for highlights
        if (selectedPiece!=null)

         for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) {

                if (isValidMove(new Move(this, selectedPiece,c,r))){
                    g2d.setColor(new Color(8, 233, 14));
                    g2d.fillRect(c*tileSize, r*tileSize, tileSize,tileSize);
                }
            }


    // to paint pieces on board
    for(Pieces pieces : pieceList){
        pieces.paint(g2d);
        }

    }

}


