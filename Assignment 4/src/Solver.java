import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    // find a solution to the initial board (using the A* algorithm)
    private MinPQ<SearchNode> pq;
    private int moves;
    private Stack<Board> SolutionBoard = new Stack<>();
    private boolean isSolved;


    public Solver(Board initial) {
        if (initial == null) throw new java.lang.IllegalArgumentException();
        SearchNode initialNode = new SearchNode(initial, null);
        SearchNode twinNode = new SearchNode(initial.twin(), null);
        pq = new MinPQ<>();
        pq.insert(initialNode);
        pq.insert(twinNode);
        initialNode = pq.delMin();
        while (!initialNode.isGoal()) {
            for (Board neighbor : initialNode.board().neighbors()) {
                if (initialNode.prev == null || !neighbor.equals(initialNode.prev.board())) {
                    SearchNode neighborNode = new SearchNode(neighbor,initialNode);
                    pq.insert(neighborNode);
                }
            }
            initialNode = pq.delMin();
        }

        SearchNode first = initialNode;
        SolutionBoard.push(first.board());
        while (first.prev != null) {
            first = first.prev;
            SolutionBoard.push(first.board());
        }

        if (first.board().equals(twinNode.board())) {
            isSolved = false;
        }
        else {
            isSolved = true;
            moves = initialNode.move();
        }
    }

    private class SearchNode implements Comparable<SearchNode> {
        private SearchNode prev;
        private int priority;
        private int move;
        private Board board;

        public SearchNode(Board board,SearchNode prev) {
            this.prev = prev;
            this.board = board;
            if (prev == null)
                this.move = 0;
            else
                this.move = prev.move() + 1;
            this.priority = board.manhattan() + this.move;
        }

        public int move() {
            return this.move;
        }
        public boolean isGoal() {
            return board.isGoal();

        }

        public Board board() {
            return board;
        }

        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolved;
    }

    // min number of moves to solve initial board
    public int moves() {
        if (!isSolvable())
            return -1;
        return moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (this.isSolvable()) return SolutionBoard;
        return null;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file

        In in = new In(args[0]);
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
            for (Board board : solver.solution())
                StdOut.println(board);
        }
//        int[][] tiles = {{0, 1, 2},
//                         {3, 4, 5},
//                         {6, 7, 8}};
//        //int[][] tiles = {{0, 2}, {1, 3}};
//        Board board = new Board(tiles);
//        System.out.println("Initial state: " + board.toString());
//        Solver solver = new Solver(board);
//        int i = 1;
//        System.out.println("Is this solvsionable? " + solver.isSolvable());
//        System.out.println("Min moves number: " + solver.moves());
//        if (solver.isSolvable()) {
//            for (Board b : solver.solution()) {
//                System.out.println("Step " + i + ": " + b.toString());
//                i++;
//            }
//        }
    }
}
