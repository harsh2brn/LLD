//package models.piece;
//
//import models.moves.DiagonalMove;
//import models.moves.StraightMove;
//
//import java.util.List;
//
//public class PieceFactory {
//
//    private StraightMove straightMove;
//    private DiagonalMove diagonalMove;
//    private PieceFactory() {
//        this.straightMove = new StraightMove();
//        this.diagonalMove = new DiagonalMove();
//    }
//
//    public static PieceFactory getInstance() {
//        return Singleton.INSTANCE;
//    }
//
//    private class Singleton {
//        private static final PieceFactory INSTANCE = new PieceFactory();
//    }
//
//    public Piece createPiece(char color, char type) {
//        switch(type) {
//            case 'K':
//                return new King(color, type);
//            case 'Q':
//                return new Queen(color, type, List.of(straightMove, diagonalMove));
//            case 'B':
//                return new Bishop(color, type, List.of(diagonalMove));
//            case 'H':
//                return new Knight(color, type);
//            case 'R':
//                return new Rook(color, type, List.of(straightMove));
//            case 'P':
//                return new Pawn(color, type);
//        }
//        return null;
//    }
//}
