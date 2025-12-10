//package models.piece;
//
//import models.Board;
//import models.moves.Move;
//
//import java.util.List;
//
//public class Knight extends Piece {
//    public Knight(char color, char type) {
//        super(color, type);
//    }
//
//    @Override
//    public boolean canMove(Board board, int startRow, int startCol, int endRow, int endCol) {
//        int rowDelta = Math.abs(endRow - startRow);
//        int colDelta = Math.abs(endCol - startCol);
//
//        return (rowDelta == 1 && colDelta == 2) ||
//                (rowDelta == 2 && colDelta == 1);
//    }
//}
