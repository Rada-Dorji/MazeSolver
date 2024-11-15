package controller.listeners;

import controller.MazeController;
import model.Cell;
import model.Direction;
import model.Maze;
import model.MazeState;
import view.drawable.MazePanel;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A MouseAdapter that listens for clicks on a generated maze to set the start and end points for the maze solver.
 * It also listens for key inputs to navigate the maze.
 */
public class MazeWaypointClickListener extends MouseAdapter {
    private final MazePanel mazePanel;
    private final MazeController mazeController;
    private final Maze maze;
    private Cell currentCell;
    private Cell[][] mazeCell;

    public MazeWaypointClickListener(final MazePanel mazePanel, final MazeController mazeController) {
        super();
        this.mazePanel = mazePanel;
        this.mazeController = mazeController;
        this.maze = mazePanel.getMaze();
        this.currentCell = maze.getStartingCell(); // Initialize current cell to starting cell
        
        // Adding the key listener
        mazePanel.addKeyListener(new MazeKeyListener());
        mazePanel.setFocusable(true);

    }

    @Override
    public void mouseReleased(final MouseEvent mouseEvent) {
        if (canSet()) {
            // mazePanel.setWaypoint(mouseEvent.getX(), mouseEvent.getY());
            mazePanel.requestFocusInWindow(); // Request focus back to the panel for key events
        }
    }

    /**
     * KeyAdapter to handle keyboard inputs for navigating the maze.
     */
    private class MazeKeyListener extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        if (mazeController.isUserMode) {
           
             // Get current user position from maze
            int row = maze.getUserRow();
            int col = maze.getUserCol();

            // Handle key presses for navigation
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    if (row > 0 && !maze.mazeCell(row, col).hasWall(Direction.UP)) {
                        maze.moveUp(); // Move up in the maze
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    if (row < maze.numRows() - 1 && !maze.mazeCell(row, col).hasWall(Direction.DOWN)) {
                        maze.moveDown(); // Move down in the maze
                    }
                    break;

                case KeyEvent.VK_LEFT:
                    if (col > 0 && !maze.mazeCell(row, col).hasWall(Direction.LEFT)) {
                        maze.moveLeft(); // Move left in the maze
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if (col < maze.numCols() - 1 && !maze.mazeCell(row, col).hasWall(Direction.RIGHT)) {
                        maze.moveRight(); // Move right in the maze
                    }
                    break;
            }

            // // Update the starting cell to reflect the new user position
            // maze.setStartingCell(maze.mazeCell(maze.getUserRow(), maze.getUserCol()));

            // Print current position for debugging
            System.out.println("Current Position: Row " + maze.getUserRow() + ", Col " + maze.getUserCol());

            // Repaint the maze panel to reflect changes
            mazePanel.repaint();

            // Check if the user reached the ending cell
            if (maze.isUserAtEnd()) {
                mazeController.setState(MazeState.SOLVED);
                JOptionPane.showMessageDialog(mazePanel, "Maze Solved!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        return; // Prevent navigation if maze is not in the GENERATED state
       
    }
}

    /**
     * Determines if setting the start and end points can be set. The waypoints can only be set if the maze is in the
     * GENERATED state (after a maze has been generated but not being solved or already solved).
     */
    private boolean canSet() {
        final var mazeState = mazeController.getState();
        final var mazeState2 = mazeController.getState();
        final boolean state = mazeState == MazeState.GENERATED || mazeState2==MazeState.USER_SOLVING;
        System.out.println(" state" + state);

        return state;
    }
}
