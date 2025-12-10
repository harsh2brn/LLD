public interface Q08ChessBoardInterface {
    void init(Helper08 helper, String[][] chessboard);

    String move(int startRow, int endRow, int startCol, int endCol);

    int getGameStatus();

    int getNextTurn();
}
