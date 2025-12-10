//package models.piece;
//
//import models.Board;
//
//public class King extends Piece {
//    King(char color, char type) {
//        super(color, type);
//    }
//
//    @Override
//    public boolean canMove(Board board, int startRow, int startCol, int endRow, int endCol) {
//        int rowDelta = Math.abs(endRow - startRow);
//        int colDelta = Math.abs(endCol - startCol);
//
//        return rowDelta <= 1 && colDelta <= 1;
//    }
//}
