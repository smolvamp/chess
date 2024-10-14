package pieces;

import main.Board;

import java.awt.image.BufferedImage;

public class Rook extends Pieces{

    public Rook (Board board, int col, int row, boolean isWhite)
    {
        super(board);

        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;



        this.isWhite = isWhite;
        this.name = "Rook";

        this.sprite = sheet.getSubimage(4*sheetScale , isWhite ? 0: sheetScale,sheetScale,sheetScale).getScaledInstance(board.tileSize,board.tileSize, BufferedImage.SCALE_SMOOTH);

    }

    public boolean isValidMovement(int col, int row){
        return this.col== col || this.row == row;
    }

    public boolean moveCollideWithPiece(int col, int row) {
//left collision check
        if(this.col>col)
            for (int c= this.col-1;c>col; c--)
                if( board.getPieces(c, this.row)!=null)
                    return true;
//right
        if(this.col<col)
            for (int c= this.col+1;c<col; c++)
                if( board.getPieces(c, this.row)!=null)
                    return true;
//up
        if(this.row>row)
            for (int r= this.row-1;r>row; r--)
                if( board.getPieces( this.col,r)!=null)
                    return true;
//down
        if(this.row<row)
            for (int r= this.row+1;r<row; r++)
                if( board.getPieces(this.col,r)!=null)
                    return true;



        return false;
    }

//    @Override
//    public void paint(Graphics2D g2d) {
//        g2d.drawImage(sprite, col * board.tileSize, row * board.tileSize, null);
//    }

}
