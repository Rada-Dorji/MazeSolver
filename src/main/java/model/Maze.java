package model;

import model.Cell.CellVisitState;

/**
 * This class represents the maze and its properties. A maze is a 2D array of cells.
 */
public class Maze {
    private int numRows;
    private int numCols;
    public Cell[][] maze;
    public Cell startingCell, endingCell;
    private Cell goal;
    private boolean[][][] wallStructure; 
    private int userRow;
    private int userCol;
    

    public Maze() {
        this(MazeConstants.DEFAULT_NUM_ROWS, MazeConstants.DEFAULT_NUM_COLS);
    }

    public Maze(int numRows, int numCols) {
        initMaze(numRows, numCols);
    }

    /**
     * Initializes the maze with the specified number of rows and columns.
     *
     * @param numRows The number of rows in the maze.
     * @param numCols The number of columns in the maze.
     */
    public void initMaze(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;

        maze = new Cell[numRows][numCols];
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                maze[r][c] = new Cell(r, c);
            }
        }
    }

    public Cell getStartingCell() {
        return startingCell;
    }

        /**
     * Sets the starting cell of the maze and updates the user position accordingly.
     *
     * @param cell The new starting cell.
     */
    public void setStartingCell(Cell cell) {
        if (startingCell != null) {
            startingCell.setStart(false); // Mark the previous starting cell as non-starting
        }
        startingCell = cell;
        
        if (startingCell != null) {
            startingCell.setStart(true); // Mark the new starting cell as starting

            // Update user position to the new starting cell's coordinates
            userRow = startingCell.row();
            userCol = startingCell.col();
            startingCell = maze[startingCell.row()][startingCell.col()];
        }
    }


    public Cell getEndingCell() {
        return endingCell;
    }

    /**
     * Sets the ending cell of the maze.
     *
     * @param cell The ending cell.
     */
    public void setEndingCell(Cell cell) {
        if (endingCell != null) {
            endingCell.setEnd(false);
        }
        endingCell = cell;
        if (endingCell != null) {
            endingCell.setEnd(true);
         
        }
    }

    

    public int numRows() {
        return numRows;
    }

    public int numCols() {
        return numCols;
    }

    public Cell getGoal() {
        return goal;
    }

    public void setGoal(Cell goal) {
        this.goal = goal;
    }

    /**
     * Gets the cell at the specified row and column.
     *
     * @param r The row index.
     * @param c The column index.
     * @return The cell at the specified location or null if out of bounds.
     */
    public Cell mazeCell(int r, int c) {
        return inBounds(r, c) ? maze[r][c] : null;
    }

    /**
     * Determines if a cell at the current row and column is in the bounds of the maze.
     *
     * @param row A row.
     * @param col A column.
     * @return A boolean indicating if a cell at that row and column is in the bounds of the maze.
     */
    public boolean inBounds(int row, int col) {
        return row >= 0 && col >= 0 && row < numRows && col < numCols;
    }

    /**
     * Sets all properties of the cell related to it being visited to false. This function is called after the generator
     * has generated the maze so that the solver can visit any cell in the maze.
     */
    public void voidVisits() {
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                Cell current = maze[r][c];
                current.setCurrent(false);
                current.setVisitState(CellVisitState.UNVISITED);
            }
        }
    }

   

    /**
     * Defaults the start and end points for the maze. This function is called at the end of generation so that the user
     * isn't required to pick start and end points. The start point is defaulted to the top left cell, and the end point
     * is defaulted to the bottom right cell.
     */
    public void defaultWaypoints() {
        startingCell = maze[0][0];
        endingCell = maze[numRows - 1][numCols - 1];
    }

    public void clearSolutionPath() {
        
        // Reset the cell's solution state
        startingCell = endingCell = goal = null;
                 
    }

    public void saveWallStructure() {
        wallStructure = new boolean[numRows][numCols][4]; // Assuming 4 directions
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                Cell cell = maze[r][c];
                for (Direction dir : Direction.values()) {
                    wallStructure[r][c][dir.ordinal()] = cell.hasWall(dir);
                }
            }
        }
    }

    /**
     * Restores the saved wall structure to each cell in the maze.
     */
    public void restoreWallStructure() {
        if (wallStructure == null) {
            throw new IllegalStateException("No wall structure saved to restore.");
        }
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                Cell cell = maze[r][c];
                for (Direction dir : Direction.values()) {
                    if (wallStructure[r][c][dir.ordinal()]) {
                        cell.addWall(dir);
                    } else {
                        cell.removeWall(dir);
                    }
                }
            }
        }
    }

    /**
     * Resets the visitation state of each cell in the maze without modifying wall structure.
     */
    public void resetVisitsOnly() {
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                Cell cell = maze[r][c];
                cell.setVisitState(CellVisitState.UNVISITED); // Reset visit state only
                cell.setCurrent(false);                        // Reset any current marking
                cell.setSolution(false);                       // Clear any solution path
            }
        }
    }

    // Inside the Maze class
    public void resetMaze() {
        for (int row = 0; row < numRows(); row++) {
            for (int col = 0; col < numCols(); col++) {
                Cell cell = mazeCell(row, col);

                // Reset cell visit state and any temporary markings
                cell.setVisitState(Cell.CellVisitState.UNVISITED);
                cell.setCurrent(false);
                cell.setStart(false);
                cell.setEnd(false);
                cell.setSolution(false);
                cell.setParent(null);

                // Restore all walls in each direction
                for (Direction direction : Direction.values()) {
                    cell.addWall(direction);
                }
            }
        }
    }

    // In Maze class, check if user has reached the end cell
    public boolean isUserAtEnd() {
        return userRow == endingCell.row() && userCol == endingCell.col();
    }

    // Getters and Setters for the user's position
    public int getUserRow() {
        return userRow;
    }

    public int getUserCol() {
        return userCol;
    }

    public void setUserPosition(int row, int col) {
        this.userRow = row;
        this.userCol = col;
    }
    
    // Method to move the user up
    public void moveUp() {
        if (userRow > 0 && !maze[userRow][userCol].hasWall(Direction.UP)) {
            userRow--; // Move user position up
            setStartingCell(maze[userRow][userCol]); // Update starting cell properly
        }
    }

    // Method to move the user down
    public void moveDown() {
        if (userRow < numRows - 1 && !maze[userRow][userCol].hasWall(Direction.DOWN)) {
            userRow++; // Move user position down
            setStartingCell(maze[userRow][userCol]); // Update starting cell properly
        }
    }

    // Method to move the user left
    public void moveLeft() {
        if (userCol > 0 && !maze[userRow][userCol].hasWall(Direction.LEFT)) {
            userCol--; // Move user position left
            setStartingCell(maze[userRow][userCol]); // Update starting cell properly
        }
    }

    // Method to move the user right
    public void moveRight() {
        if (userCol < numCols - 1 && !maze[userRow][userCol].hasWall(Direction.RIGHT)) {
            userCol++; // Move user position right
            setStartingCell(maze[userRow][userCol]); // Update starting cell properly
        }
    }

}
