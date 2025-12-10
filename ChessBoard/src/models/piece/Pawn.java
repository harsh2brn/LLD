//package models.piece;
//
//import models.Board;
//
//public class Pawn extends Piece {
//    public Pawn(char color, char type) {
//        super(color, type);
//    }
//
//    @Override
//    public boolean canMove(Board board, int startRow, int startCol, int endRow, int endCol) {
//        Piece piece = board.getPiece(startRow, startCol);
//
//        if(board.getPiece(endRow, endCol) == null) {
//            if(startCol != endCol) {
//                return false;
//            }
//            else {
//                return (piece.getColor() == 'W' && endRow - startRow == 1) ||
//                        (piece.getColor() == 'B' && endRow - startRow == -1);
//            }
//        }
//        else {
//            if(startCol == endCol) {
//                return false;
//            }
//            else {
//                return (piece.getColor() == 'W' && endRow - startRow == 1 && Math.abs(endCol - startCol) == 1) ||
//                        (piece.getColor() == 'B' && endRow - startRow == 1 && Math.abs(endCol - startCol) == 1);
//            }
//        }
//    }
//}
