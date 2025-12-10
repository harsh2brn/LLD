//package models.moves;
//
//import models.Board;
//import models.moves.Move;
//
//public class StraightMove implements Move {
//    @Override
//    public boolean canMove(Board board, int startRow, int startCol, int endRow, int endCol) {
//        if(startRow != endRow && startCol != endCol) {
//            return false;
//        }
//
//        int rowDelta = 0;
//        int colDelta = 0;
//
//        if(startRow != endRow) {
//            rowDelta = (endRow - startRow) > 0 ? 1 : -1;
//        }
//        else if(startCol != endCol) {
//            colDelta = (endCol - startCol) > 0 ? 1 : -1;
//        }
//
//        while(startRow != endRow || startCol != endCol) {
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
