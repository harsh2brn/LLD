//package models.moves;
//
//import models.Board;
//import models.moves.Move;
//
//public class DiagonalMove implements Move {
//    @Override
//    public boolean canMove(Board board, int startRow, int startCol, int endRow, int endCol) {
//        int rowDelta = endRow - startRow;
//        int colDelta = endCol - startCol;
//
//        if(Math.abs(rowDelta) != Math.abs(colDelta)) {
//            return false;
//        }
//
//        rowDelta = rowDelta > 0 ? 1 : -1;
//        colDelta = colDelta > 0 ? 1 : -1;
//
//        while(startRow != endRow && startCol != endCol) {
//            if(board.getPiece(startRow, startCol) != null) {
//                return false;
//            }
//            startRow += rowDelta;
//            startCol += colDelta;
//        }
//
//        return true;
//    }
//}
