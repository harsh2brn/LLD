import java.util.ArrayList;
import java.util.List;

public class Solution implements Q08ChessBoardInterface {
    private Helper08 helper;
    private BoardManager boardManager;
    public Solution(){}

    public void init(Helper08 helper, String[][] chessboard) {
        this.helper=helper;
        this.boardManager = BoardManager.getInstance();
        boardManager.setBoard(chessboard);
    }

    @Override
    public String move(int startRow, int startCol, int endRow, int endCol) {
        return boardManager.move(startRow, startCol, endRow, endCol);
    }

    // return 0 for game in progress, 1 for white has won, 2 for black has won
    public int getGameStatus() {
        return boardManager.getGameStatus();
    }

    // return 0 for white, 1 for black, -1 for game already finished
    public int getNextTurn() {
        return boardManager.getTurn();
    }
}

class BoardManager {
    private Board board;
    private PieceFactory pieceFactory;
    private Integer gameStatus;
    private Integer turn;
    private BoardManager() {
        pieceFactory = PieceFactory.getInstance();
        this.gameStatus = 0;
        this.turn = 0;
    }

    private static class Singleton {
        private static BoardManager INSTANCE = new BoardManager();
    }

    public static BoardManager getInstance() {
        return BoardManager.Singleton.INSTANCE;
    }

    public void setBoard(String[][] board) {
        List<List<Piece>> currBoard = new ArrayList<>();
        for(String[] row : board) {
            ArrayList<Piece> tempArray = new ArrayList<>();
            for(String val : row) {
                if(val.length() == 2) {
                    char color = val.charAt(0);
                    char type = val.charAt(1);
                    tempArray.add(pieceFactory.createPiece(color, type));
                }
                else {
                    tempArray.add(null);
                }
            }
            currBoard.add(tempArray);
        }
        this.board = new Board(currBoard);

        for(List<Piece> row : this.board.getBoard()) {
            for(Piece val : row) {
                if(val != null) {
                    System.out.print("" + val.getColor() + val.getType() + " ");
                }
            }
            System.out.println();
        }
    }

    public String move(int startRow, int startCol, int endRow, int endCol) {
        if(!Utils.isValid(startRow, startCol) ||
                !Utils.isValid(endRow, endCol) ||
                board.getPiece(startRow, startCol) == null) {

            return "invalid";
        }

        Piece initialPiece = board.getPiece(startRow, startCol);
        Piece finalPiece = board.getPiece(endRow, endCol);
        if(!initialPiece.canMove(board, startRow, startCol, endRow, endCol)) {
            return "invalid";
        }

        board.setPiece(startRow, startCol, null);
        if(finalPiece == null) {
            board.setPiece(endRow, endCol, initialPiece);
        }
        else {
            if(initialPiece.getColor() != finalPiece.getColor()) {
                if(finalPiece.getType() == 'K') {
                    int status = 0;
                    if(getTurn() == 0) {
                        status = 1;
                    }
                    else if(getTurn() == 1) {
                        status = 2;
                    }
                    setTurn(-1);
                    setGameStatus(status);

                    return "" + finalPiece.getColor() + finalPiece.getType();
                }
                else {
                    board.setPiece(endRow, endCol, initialPiece);
                }
            }
            else {
                return "invalid";
            }
        }
        int turn = 1 - getTurn();
        setTurn(turn);

        if(finalPiece == null) {
            return "";
        }
        else {
            return "" + finalPiece.getColor() + finalPiece.getType();
        }
    }

    private void setGameStatus(int status) {
        this.gameStatus = status;
    }

    public int getTurn() {
        return this.turn;
    }

    public int getGameStatus() {
        return this.gameStatus;
    }

    private void setTurn(int turn) {
        this.turn = turn;
    }
}

class Board {
    private List<List<Piece>> board;

    Board(List<List<Piece>> board) {
        this.board = board;
    }

    public List<List<Piece>> getBoard() {
        return board;
    }

    public Piece getPiece(int row, int col) {
        if(this.board.get(row).get(col) == null) {
            return null;
        }
        return this.board.get(row).get(col);
    }

    public void setPiece(int row, int col, Piece piece) {
        this.board.get(row).set(col, piece);
    }
}

interface Move {
    public boolean canMove(Board board, int startRow, int startCol, int endRow, int endCol);
}
class DiagonalMove implements Move {
    @Override
    public boolean canMove(Board board, int startRow, int startCol, int endRow, int endCol) {
        int rowDelta = endRow - startRow;
        int colDelta = endCol - startCol;

        if(Math.abs(rowDelta) != Math.abs(colDelta)) {
            return false;
        }

        rowDelta = rowDelta > 0 ? 1 : -1;
        colDelta = colDelta > 0 ? 1 : -1;

        while(startRow != endRow && startCol != endCol) {
            if(board.getPiece(startRow, startCol) != null) {
                return false;
            }
            startRow += rowDelta;
            startCol += colDelta;
        }

        return true;
    }
}

class StraightMove implements Move {
    @Override
    public boolean canMove(Board board, int startRow, int startCol, int endRow, int endCol) {
        if(startRow != endRow && startCol != endCol) {
            return false;
        }

        int rowDelta = 0;
        int colDelta = 0;

        if(startRow != endRow) {
            rowDelta = (endRow - startRow) > 0 ? 1 : -1;
        }
        else if(startCol != endCol) {
            colDelta = (endCol - startCol) > 0 ? 1 : -1;
        }

        while(startRow != endRow || startCol != endCol) {
            if(board.getPiece(startRow, startCol) != null) {
                return false;
            }
            startRow += rowDelta;
            startCol += colDelta;
        }

        return true;
    }
}

class Piece {
    private char color;
    private char type;
    List<Move> moves;

    Piece(char color, char type) {
        this.color = color;
        this.type = type;
    }

    Piece(char color, char type, List<Move> moves) {
        this.color = color;
        this.type = type;
        this.moves = new ArrayList<>(moves);
    }

    public boolean canMove(Board board, int startRow, int startCol, int endRow, int endCol) {
        for(Move move : moves) {
            if(move.canMove(board, startRow, startCol, endRow, endCol)) {
                return true;
            }
        }

        return false;
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }
}

class Bishop extends Piece {
    public Bishop(char color, char type, List<Move> moves) {
        super(color, type, moves);
    }
}

class PieceFactory {

    private StraightMove straightMove;
    private DiagonalMove diagonalMove;
    private PieceFactory() {
        this.straightMove = new StraightMove();
        this.diagonalMove = new DiagonalMove();
    }

    public static PieceFactory getInstance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final PieceFactory INSTANCE = new PieceFactory();
    }

    public Piece createPiece(char color, char type) {
        switch(type) {
            case 'K':
                return new King(color, type);
            case 'Q':
                return new Queen(color, type, List.of(straightMove, diagonalMove));
            case 'B':
                return new Bishop(color, type, List.of(diagonalMove));
            case 'H':
                return new Knight(color, type);
            case 'R':
                return new Rook(color, type, List.of(straightMove));
            case 'P':
                return new Pawn(color, type);
        }
        return null;
    }
}

class Pawn extends Piece {
    public Pawn(char color, char type) {
        super(color, type);
    }

    @Override
    public boolean canMove(Board board, int startRow, int startCol, int endRow, int endCol) {
        Piece piece = board.getPiece(startRow, startCol);

        if(board.getPiece(endRow, endCol) == null) {
            if(startCol != endCol) {
                return false;
            }
            else {
                return (piece.getColor() == 'W' && endRow - startRow == 1) ||
                        (piece.getColor() == 'B' && endRow - startRow == -1);
            }
        }
        else {
            if(startCol == endCol) {
                return false;
            }
            else {
                return (piece.getColor() == 'W' && endRow - startRow == 1 && Math.abs(endCol - startCol) == 1) ||
                        (piece.getColor() == 'B' && endRow - startRow == 1 && Math.abs(endCol - startCol) == 1);
            }
        }
    }
}

class Queen extends Piece {
    public Queen(char color, char type, List<Move> moves) {
        super(color, type, moves);
    }

}

class King extends Piece {
    King(char color, char type) {
        super(color, type);
    }

    @Override
    public boolean canMove(Board board, int startRow, int startCol, int endRow, int endCol) {
        int rowDelta = Math.abs(endRow - startRow);
        int colDelta = Math.abs(endCol - startCol);

        return rowDelta <= 1 && colDelta <= 1;
    }
}

class Knight extends Piece {
    public Knight(char color, char type) {
        super(color, type);
    }

    @Override
    public boolean canMove(Board board, int startRow, int startCol, int endRow, int endCol) {
        int rowDelta = Math.abs(endRow - startRow);
        int colDelta = Math.abs(endCol - startCol);

        return (rowDelta == 1 && colDelta == 2) ||
                (rowDelta == 2 && colDelta == 1);
    }
}

class Rook extends Piece {
    public Rook(char color, char type, List<Move> moves) {
        super(color, type, moves);
    }
}

class Utils {
    public static boolean isValid(int row, int col) {
        return row >= 0 && col >= 0 && row < 8 && col < 8;
    }
}

class Helper08{
    void print(String s){System.out.print(s);} void println(String s){print(s+"\n");}
}
