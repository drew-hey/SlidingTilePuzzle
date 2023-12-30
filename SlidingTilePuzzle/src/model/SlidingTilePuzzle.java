package model;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

/**
 * Recreation of a sliding tile puzzle game in which the player must move tiles around
 * a 3x3 grid so that each tile is in the correct position. In other words, the top
 * row would read "1, 2, 3", the middle row would read "4, 5, 6", and the bottom row
 * would read "7, 8" (the bottom right corner should be empty).
 * 
 * @author Drew Hey
 */
public class SlidingTilePuzzle {

    /** The number of rows for the grid. */
    private int rows;
    /** The number of columns for the grid. */
    private int cols;
    /** Tracks the position of the tiles in the grid. */
    private int[][] grid;
    /** Tracks the row of the empty tile. */
    private int emptyTileRow;
    /** Tracks the column of the empty tile. */
    private int emptyTileCol;
    /** Tracks the player's number of moves. */
    private int moveCount;
    
    /** The default number of rows for the grid. */
    public static final int ROWS = 3;
    /** The default number of columns for the grid. */
    public static final int COLS = 3;
    /** The value for the empty tile. */
    private static final int EMPTY_TILE = 0;
    
    /**
     * Default constructor if no parameters are passed in.
     * This will generate a 3x3 sliding tile puzzle.
     */
    public SlidingTilePuzzle() {
        this(ROWS, COLS);
    }
    
    /** 
     * Initializes the fields of the sliding tile puzzle.
     * 
     * @param rows the number of rows for the puzzle
     * @param cols the number of columns for the puzzle
     * @throws IllegalArgumentException if the number of rows or columns is less than 3
     */
    public SlidingTilePuzzle(int rows, int cols) {
        //error handling
        if (rows < 3 || cols < 3) {
            throw new IllegalArgumentException("Invalid grid size.");
        }
        
        //initialize rows and cols fields
        this.rows = rows;
        this.cols = cols;
        //sets the grid size
        grid = new int[rows][cols];
        //set up the tiles for the grid
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = 1 + i * rows + j;
            }
        }
        //the "empty tile" will have 0 as its value.
        emptyTileRow = rows - 1;
        emptyTileCol = cols - 1;
        grid[emptyTileRow][emptyTileCol] = EMPTY_TILE;
        
        //now shuffle the tiles around!
        shuffleTiles();
        
        //starting with 0 moves
        moveCount = 0;
    }
    
    /**
     * Randomizes the positions of each tile to create a "new" game board.
     */
    private void shuffleTiles() {
        //for random number generation
        Random random = new Random();
        //move the tiles randomly
        int n = 1000 * rows;
        for (int i = 0; i < n; i++) {
            switch (random.nextInt(4)) {
                case 0:
                    //north of empty tile
                    if (emptyTileRow > 0) {
                        moveTile(emptyTileRow - 1, emptyTileCol);
                    };
                    break;
                case 1:
                    //south of empty tile
                    if (emptyTileRow < rows - 1) {
                        moveTile(emptyTileRow + 1, emptyTileCol);
                    };
                    break;
                case 2:
                    //west of empty tile
                    if (emptyTileCol > 0) {
                        moveTile(emptyTileRow, emptyTileCol - 1);
                    };
                    break;
                case 3:
                    //east of empty tile
                    if (emptyTileCol < cols - 1) {
                        moveTile(emptyTileRow, emptyTileCol + 1);
                    };
                    break;
            }
        }
    }
    
    /**
     * Moves the tile at the specified row and column, if possible.
     * A tile can only be moved if it is adjacent to the empty tile.
     * @param row the row of the tile to move
     * @param col the column of the tile to move
     */
    public void moveTile(int row, int col) {
        //error-checking
        checkRow(row);
        checkCol(col);
        
        /*
         * A tile can only move if it is adjacent to the empty tile.
         * In other words, moveTile only works when either
         * 1. the row matches the empty tile's row and the columns differ by 1, or
         * 2. the col matches the empty tile's col and the rows differ by 1
         */
        if ((row == emptyTileRow && Math.abs(col - emptyTileCol) == 1) || 
            (col == emptyTileCol && Math.abs(row - emptyTileRow) == 1)) {
            //empty tile is swapped with the specified tile
            grid[emptyTileRow][emptyTileCol] = grid[row][col];
            grid[row][col] = EMPTY_TILE;
            emptyTileRow = row;
            emptyTileCol = col;
            //increase the moveCount
            moveCount++;
        }
        
    }
    
    /**
     * Checks the validity of the row parameter.
     * 
     * @param row the row to check
     * @throws IllegalArgumentException if row < 0 or row >= ROWS
     */
    protected void checkRow(int row) {
        if (row < 0 || row >= rows) {
            throw new IllegalArgumentException("Invalid row.");
        }
    }
    
    /**
     * Checks the validity of the col parameter.
     * 
     * @param col the column to check
     * @throws IllegalArgumentException if col < 0 or col >= COLS
     */
    protected void checkCol(int col) {
        if (col < 0 || col >= cols) {
            throw new IllegalArgumentException("Invalid column.");
        }
    }
    
    /**
     * Prints the grid to the console for testing purposes.
     */
    public void printGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }
    
    /**
     * Determines whether the puzzle has been solved or not.
     * The puzzle is solved when all the tiles are in ascending order
     * (increasing by columns first, then by rows).
     * 
     * @return true if the puzzle has been solved, false otherwise
     */
    public boolean solved() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                //excluding the last tile, if this tile does not match
                //the expected tile, then the puzzle is not solved.
                if (i * j != (rows - 1) * (cols - 1) && grid[i][j] != 1 + i * rows + j) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Returns the puzzle grid as a 2D array of ints.
     * 
     * @return the puzzle grid
     */
    public int[][] getGrid() {
        return grid;
    }
    
    /**
     * Returns the number of rows for the puzzle.
     * 
     * @return the number of rows for the puzzle
     */
    public int getRows() {
        return rows;
    }
    
    /**
     * Returns the number of columns for the puzzle.
     * 
     * @return the number of columns for the puzzle
     */
    public int getCols() {
        return cols;
    }
    
    /**
     * Returns the player's number of moves.
     * 
     * @return the player's number of moves
     */
    public int moveCount() {
        return moveCount;
    }
    
    /**
     * Tests the SlidingTilePuzzle class.
     * 
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        SlidingTilePuzzle puzzle = new SlidingTilePuzzle();
        puzzle.printGrid();
        Scanner scanner = new Scanner(System.in);
        
        while (!puzzle.solved()) {
            int inputRow = -1;
            int inputCol = -1;
            
            while (inputRow < 0) {
                System.out.print("Choose a row between 0 and " + (puzzle.getGrid().length - 1) + ": ");
                try {
                    inputRow = scanner.nextInt();
                    
                    try {
                        puzzle.checkRow(inputRow);
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println("Invalid row, try again.");
                        inputRow = -1;
                    }
                }
                catch (NoSuchElementException e) {
                    System.out.println("Not an integer, try again.");
                    scanner.next();
                }
                
            }
            
            while (inputCol < 0) {
                System.out.print("Choose a col between 0 and " + (puzzle.getGrid()[0].length - 1) + ": ");
                try {
                    inputCol = scanner.nextInt();
                    
                    try {
                        puzzle.checkCol(inputCol);
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println("Invalid col, try again.");
                        inputCol = -1;
                    }
                }
                catch (NoSuchElementException e) {
                    System.out.println("Not an integer, try again.");
                    scanner.next();
                }
                
            }
            
            puzzle.moveTile(inputRow, inputCol);
            puzzle.printGrid();
        }
        
        System.out.println("You solved the puzzle!");
        scanner.close();
    }
}
