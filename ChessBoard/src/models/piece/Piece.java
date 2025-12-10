//package models.piece;
//
//import models.Board;
//import models.moves.Move;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Piece {
//    private char color;
//    private char type;
//    List<Move> moves;
//
//    Piece(char color, char type) {
//        this.color = color;
//        this.type = type;
//    }
//
//    Piece(char color, char type, List<Move> moves) {
//        this.color = color;
//        this.type = type;
//        this.moves = new ArrayList<>(moves);
//    }
//
//    public boolean canMove(Board board, int startRow, int startCol, int endRow, int endCol) {
//        for(Move move : moves) {
//            if(move.canMove(board, startRow, startCol, endRow, endCol)) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    public char getColor() {
//        return color;
//    }
//
//    public void setColor(char color) {
//        this.color = color;
//    }
//
//    public char getType() {
//        return type;
//    }
//
//    public void setType(char type) {
//        this.type = type;
//    }
//
//    public List<Move> getMoves() {
//        return moves;
//    }
//
//    public void setMoves(List<Move> moves) {
//        this.moves = moves;
//    }
//}
