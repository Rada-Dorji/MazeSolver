package view.drawable;

import model.Cell;
import model.Maze;
import model.MazeState;

import java.awt.*;

/**
 * Class responsible for the drawing of the maze. The maze is drawn by iterating through each cell, and drawing each
 * cell.
 */
class MazeDrawable {
    /**
     * Iterates through and draws individual cells of the maze to draw the entire maze.
     *
     * @param maze      A maze instance
     * @param graphics  A Graphics instance
     * @param mazeState The current state of the maze
     * @param xOffset   The x offset
     * @param yOffset   The y offset
     */
    public void drawMaze(Maze maze, Graphics graphics, MazeState mazeState, int xOffset, int yOffset) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        for (int r = 0; r < maze.numRows(); r++) {
            for (int c = 0; c < maze.numCols(); c++) {
                Cell cell = maze.mazeCell(r, c);
                CellDrawable.drawCell(cell, graphics2D, mazeState, xOffset, yOffset);
            }
        }
    }
}
