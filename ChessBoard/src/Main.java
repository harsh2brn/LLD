public class Main {
    public static void main(String[] args) {
        String[][] board = {
                {"WR","WH","WB","WQ","WK","WB","WH","WR"},
                {"WP","WP","WP","WP","WP","WP","WP","WP"},
                {"","","","","","","",""},
                {"","","","","","","",""},
                {"","","","","","","",""},
                {"","","","","","","",""},
                {"BP","BP","BP","BP","BP","BP","BP","BP"},
                {"BR","BH","BB","BQ","BK","BB","BH","BR"}
        };

        Solution solution = new Solution();
        solution.init(new Helper08(), board);

        System.out.print(solution.move(1,6,7,0) + " ");
        System.out.print(solution.getNextTurn() + " ");
        System.out.print(solution.getGameStatus() + " ");
        System.out.println();

        System.out.print(solution.move(6,6,5,6) + " ");
        System.out.print(solution.getNextTurn() + " ");
        System.out.print(solution.getGameStatus() + " ");
        System.out.println();

        System.out.print(solution.move(2,5,3,5) + " ");
        System.out.print(solution.getNextTurn() + " ");
        System.out.print(solution.getGameStatus() + " ");
        System.out.println();

        System.out.print(solution.move(6,2,5,2) + " ");
        System.out.print(solution.getNextTurn() + " ");
        System.out.print(solution.getGameStatus() + " ");
        System.out.println();

        System.out.print(solution.move(0,1,2,2) + " ");
        System.out.print(solution.getNextTurn() + " ");
        System.out.print(solution.getGameStatus() + " ");
        System.out.println();

        System.out.print(solution.move(6,4,5,4) + " ");
        System.out.print(solution.getNextTurn() + " ");
        System.out.print(solution.getGameStatus() + " ");
        System.out.println();

        System.out.print(solution.move(1,7,2,7) + " ");
        System.out.print(solution.getNextTurn() + " ");
        System.out.print(solution.getGameStatus() + " ");
        System.out.println();

        System.out.print(solution.move(7,6,5,7) + " ");
        System.out.print(solution.getNextTurn() + " ");
        System.out.print(solution.getGameStatus() + " ");
        System.out.println();

        System.out.print(solution.move(2,2,3,4) + " ");
        System.out.print(solution.getNextTurn() + " ");
        System.out.print(solution.getGameStatus() + " ");
        System.out.println();

        System.out.print(solution.move(6,5,5,5) + " ");
        System.out.print(solution.getNextTurn() + " ");
        System.out.print(solution.getGameStatus() + " ");
        System.out.println();

        System.out.print(solution.move(3,4,5,5) + " ");
        System.out.print(solution.getNextTurn() + " ");
        System.out.print(solution.getGameStatus() + " ");
        System.out.println();

        System.out.print(solution.move(6,0,5,0) + " ");
        System.out.print(solution.getNextTurn() + " ");
        System.out.print(solution.getGameStatus() + " ");
        System.out.println();

        System.out.print(solution.move(5,5,7,4) + " ");
        System.out.print(solution.getNextTurn() + " ");
        System.out.print(solution.getGameStatus() + " ");
        System.out.println();
    }
}