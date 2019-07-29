
import java.util.LinkedList;
import java.util.Queue;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private int[][] tiles;
    private int dimension;
    public Board(int[][] tiles) {
        dimension = tiles.length;
        this.tiles = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                String format = "%" + (dimension * dimension / 10 + 2) + "d";
                s.append(String.format(format, tiles[i][j]));  // 2d format
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension(){
        return dimension;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j=0; j < dimension; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != i*dimension + j + 1) count++;
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int count = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] != 0) {
                    count += Math.abs((tiles[i][j] - 1) / dimension - i) + Math.abs((tiles[i][j] - 1) % dimension - j);
                }
            }
        }
        return count;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i == dimension - 1 && j == dimension - 1) {
                    if (tiles[i][j] != 0)
                        return false;
                }
                else if (tiles[i][j] != i*dimension + j + 1)
                    return false;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {

        if (y.getClass() != this.getClass()) return false;
        if (y == this) return true;
        if (y == null) return false;
        Board that = (Board) y;
        if (this.dimension != that.dimension) return false;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> q = new LinkedList<>();
        int[] dirX = {0, 1, 0, -1};
        int[] dirY = {1, 0, -1, 0};
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] == 0) {
                    for (int d = 0; d < 4; d++) {
                        int toSwapX = i + dirX[d];
                        int toSwapY = j + dirY[d];
                        if (toSwapX >= 0 && toSwapX < dimension && toSwapY >= 0 && toSwapY < dimension) {
                            Board neighbor = swap(toSwapX, toSwapY, i, j);
                            q.add(neighbor);
                        }
                    }
                }
            }
        }
        return q;
    }

    private Board swap(int toSwapX, int toSwapY, int origX, int origY) {
        int[][] clTiles = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                clTiles[i][j] = tiles[i][j];
            }
        }

        int temp = clTiles[toSwapX][toSwapY];
        clTiles[toSwapX][toSwapY] = clTiles[origX][origY];
        clTiles[origX][origY] = temp;
        Board swap = new Board(clTiles);
        return swap;

    }


    //iterator?
    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinTiles = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                twinTiles[i][j] = tiles[i][j];
            }
        }
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i + 1 < dimension && twinTiles[i][j] != 0 && twinTiles[i + 1][j] != 0) {
                    int temp = twinTiles[i][j];
                    twinTiles[i][j] = twinTiles[i + 1][j];
                    twinTiles[i + 1][j] = temp;
                    return new Board(twinTiles);
                }
            }
        }
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // create initial board from file
        /*
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        */

        int[][] tiles = {{0, 2, 5}, {7, 8, 6}, {4, 1, 3}};
        Board board = new Board(tiles);
        System.out.println(board);
        System.out.println(board.isGoal());
        System.out.println(board.manhattan());
        System.out.println(board.twin());
    }
}
