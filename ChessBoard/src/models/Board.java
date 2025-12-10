//package models;
//
//import models.piece.Piece;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Board {
//    private List<List<Piece>> board;
//
//    Board(List<List<Piece>> board) {
//        this.board = board;
//    }
//
//    public List<List<Piece>> getBoard() {
//        return board;
//    }
//
//    public Piece getPiece(int row, int col) {
//        if(this.board.get(row).get(col) == null) {
//            return null;
//        }
//        return this.board.get(row).get(col);
//    }
//
//    public void setPiece(int row, int col, Piece piece) {
//        this.board.get(row).set(col, piece);
//    }
//}
