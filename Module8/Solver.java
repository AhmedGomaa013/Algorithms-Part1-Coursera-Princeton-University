import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private int moves = 0;
    private boolean isSolvable = false;
    private BoardNode goalBoard;
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        moves = 0;
        solvePuzzleUsingAStar(initial);
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable() ? moves : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable()) {
        Stack<Board> solution = new Stack<>();
        while (goalBoard != null) {
            solution.push(goalBoard.board);
            goalBoard = goalBoard.prev;
        }
        return solution;
        }
        return null;
    }

    private void solvePuzzleUsingAStar(Board initial) {
        MinPQ<BoardNode> open = new MinPQ<>();
        MinPQ<BoardNode> twinOpen = new MinPQ<>();
        
        var initialNode = new BoardNode(initial, moves, null);
        
        open.insert(initialNode);
        twinOpen.insert(new BoardNode(initial.twin(), moves, null));
        
        BoardNode current = open.delMin();
        BoardNode twinCurrent = twinOpen.delMin();
        while (!current.board.isGoal() && !twinCurrent.board.isGoal()) {
            Iterable<Board> neighbors = current.board.neighbors();
            for (Board b : neighbors) {
                if (current.prev == null || !b.equals(current.prev.board)) {
                    open.insert(new BoardNode(b, current.moves + 1, current));
                }
            }

            neighbors = twinCurrent.board.neighbors();
            for (Board b : neighbors) {
                if (twinCurrent.prev == null || !b.equals(twinCurrent.prev.board)) {
                    twinOpen.insert(new BoardNode(b, twinCurrent.moves + 1, twinCurrent));
                }
            }
            if (open.isEmpty() || twinOpen.isEmpty()) break;
            current = open.delMin();
            twinCurrent = twinOpen.delMin();
    }

        if(current.board.isGoal()) {
            goalBoard = current;
            isSolvable = true;
        }
        else {
            goalBoard = null;
            isSolvable = false;
        }
        moves = current.moves;
    }
    // test client (see below) 
    public static void main(String[] args) {

        In in = new In("test.txt");
    int n = in.readInt();
    int[][] tiles = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            tiles[i][j] = in.readInt();
    Board initial = new Board(tiles);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        // for (Board board : solver.solution())
           //  StdOut.println(board);
    }
    }

    private class BoardNode implements Comparable<BoardNode>{
        private Board board;
        private int moves;
        private BoardNode prev;
        public BoardNode(Board b, int moves, BoardNode prev) {
            this.board = b;
            this.moves = moves;
            this.prev = prev;
        }

        public int getPriority() {
            return this.moves + this.board.manhattan();
        }

        public int compareTo(BoardNode that) {
            return Integer.compare(this.getPriority(), that.getPriority());
        }
    
        
    }
}