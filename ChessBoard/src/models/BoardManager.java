//package models;
//
//import models.piece.Piece;
//import models.piece.PieceFactory;
//import models.utils.Utils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class BoardManager {
//    private Board board;
//    private PieceFactory pieceFactory;
//    private Integer gameStatus;
//    private Integer turn;
//    private BoardManager() {
//        pieceFactory = PieceFactory.getInstance();
//        this.gameStatus = 0;
//        this.turn = 0;
//    }
//
//    private static class Singleton {
//        private static BoardManager INSTANCE = new BoardManager();
//    }
//
//    public static BoardManager getInstance() {
//        return Singleton.INSTANCE;
//    }
//
//    public void setBoard(String[][] board) {
//        List<List<Piece>> currBoard = new ArrayList<>();
//        for(String[] row : board) {
//            ArrayList<Piece> tempArray = new ArrayList<>();
//            for(String val : row) {
//                if(val.length() == 2) {
//                    char color = val.charAt(0);
//                    char type = val.charAt(1);
//                    tempArray.add(pieceFactory.createPiece(color, type));
//                }
//                else {
//                    tempArray.add(null);
//                }
//            }
//            currBoard.add(tempArray);
//        }
//        this.board = new Board(currBoard);
//
//        for(List<Piece> row : this.board.getBoard()) {
//            for(Piece val : row) {
//                if(val != null) {
//                    System.out.print("" + val.getColor() + val.getType() + " ");
//                }
//            }
//            System.out.println();
//        }
//    }
//
//    public String move(int startRow, int startCol, int endRow, int endCol) {
//        if(!Utils.isValid(startRow, startCol) ||
//                !Utils.isValid(endRow, endCol) ||
//                board.getPiece(startRow, startCol) == null) {
//
//            return "invalid";
//        }
//
//        Piece initialPiece = board.getPiece(startRow, startCol);
//        Piece finalPiece = board.getPiece(endRow, endCol);
//        if(!initialPiece.canMove(board, startRow, startCol, endRow, endCol)) {
//            return "invalid";
//        }
//
//        board.setPiece(startRow, startCol, null);
//        if(finalPiece == null) {
//            board.setPiece(endRow, endCol, initialPiece);
//        }
//        else {
//            if(initialPiece.getColor() != finalPiece.getColor()) {
//                if(finalPiece.getType() == 'K') {
//                    int status = 0;
//                    if(getTurn() == 0) {
//                        status = 1;
//                    }
//                    else if(getTurn() == 1) {
//                        status = 2;
//                    }
//                    setTurn(-1);
//                    setGameStatus(status);
//
//                    return "" + finalPiece.getColor() + finalPiece.getType();
//                }
//                else {
//                    board.setPiece(endRow, endCol, initialPiece);
//                }
//            }
//            else {
//                return "invalid";
//            }
//        }
//        int turn = 1 - getTurn();
//        setTurn(turn);
//
//        if(finalPiece == null) {
//            return "";
//        }
//        else {
//            return "" + finalPiece.getColor() + finalPiece.getType();
//        }
//    }
//
//    private void setGameStatus(int status) {
//        this.gameStatus = status;
//    }
//
//    public int getTurn() {
//        return this.turn;
//    }
//
//    public int getGameStatus() {
//        return this.gameStatus;
//    }
//
//    private void setTurn(int turn) {
//        this.turn = turn;
//    }
//}