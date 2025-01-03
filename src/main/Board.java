package main;

import pieces.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class Board extends JPanel {

  //  public String fenStartingPosition = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

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

    public void makeMove(Move move) throws IOException, InterruptedException {

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

//        pieceList.clear();
//        String[] parts = fenString.split(" ");
//
//        String position = parts[0];
//        int row = 0;
//        int col = 0;
//        for(int i = 0; i< position.length(); i++){
//            char ch = position.charAt(i);
//            if (ch == '/'){
//                row++;
//                col = 0;
//            }
//            else if (Character.isDigit(ch)){
//                col += Character.getNumericValue(ch);
//
//            }
//            else{
//                boolean isWhite = Character.isUpperCase(ch);
//                char pieceChar  = Character.toLowerCase(ch);
//
//                switch (pieceChar){
//                    case 'r':
//                        pieceList.add(new Rook(this, col, row, isWhite));
//                        break;
//                    case 'n':
//                        pieceList.add(new Knight(this, col, row, isWhite));
//                        break;
//                    case 'b':
//                        pieceList.add(new Bishop(this, col, row, isWhite));
//                        break;
//                    case 'q':
//                        pieceList.add(new Queen(this, col, row, isWhite));
//                        break;
//                    case 'k':
//                        pieceList.add(new King(this, col, row, isWhite));
//                        break;
//                    case 'p':
//                        pieceList.add(new Pawn(this, col, row, isWhite));
//                        break;
//                }
//                col++;
//            }
//        }
//        isWhiteToMove = parts[1].equals("w");
//
//        Pieces bqr = getPieces(0,0);
//        if (bqr instanceof Rook){
//            bqr.isFirstMove = parts[2].contains("q");
//        }
//        Pieces bkr = getPieces(7,0);
//        if (bkr instanceof Rook){
//            bkr.isFirstMove = parts[2].contains("k");
//        }
//        Pieces wqr = getPieces(0,7);
//        if (wqr instanceof Rook){
//            wqr.isFirstMove = parts[2].contains("Q");
//        }
//        Pieces wkr = getPieces(7,7);
//        if (wkr instanceof Rook){
//            wkr.isFirstMove = parts[2].contains("Q");
//        }
//
//        if (parts[3].equals("-")){
//            enPassantTile = -1;
//        }
//        else{
//             enPassantTile = (7 - (parts[3].charAt(1)-'1')) * 8 + (parts[3].charAt(0) - 'a');
//        }

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

    private void updateGameState() throws IOException, InterruptedException {
        Pieces king = findKing(isWhiteToMove);

        if(checkScanner.isGameOver(king)){

            if (checkScanner.isKingChecked(new Move(this, king, king.col, king.row))){

                if (isWhiteToMove){
                    new gameOver("Black won!");
//                    JFrame frame = new JFrame("GAME OVER!!!");
//
//                    // Set default behavior on close
//                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                    frame.setSize(500, 400);
//
//                    // Show an Information dialog (pop-up)
//                    String result = "Black won";
//                    JOptionPane.showMessageDialog(frame, result,
//                            "GAME OVER!!!", JOptionPane.INFORMATION_MESSAGE);

                }
                else
                {
                    new gameOver("White won!");
                }

            }
            else{
                new gameOver("Stalemate!");
            }
        isGameOver = true;
        }
          else if (inSufficientMaterial(true) && inSufficientMaterial(false)) {
            new gameOver("Insufficient Material!");

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


