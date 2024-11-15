package model;

import controller.MazeController;

import javax.swing.*;

/**
 * An abstract class that extends the SwingWorker class. This class is extended by all maze solvers. All
 * SwingWorker classes extending this class will return a Boolean class type indicating if the maze solving was a
 * success and publish Maze instances at each iteration of the solving to be repainted in the view.
 */
public abstract class MazeSolverWorker extends SwingWorker<Boolean, Maze> {
    protected final Maze maze;
    protected final MazeController mazeController;

    protected MazeSolverWorker(Maze maze, MazeController mazeController) {
        this.maze = maze;
        this.mazeController = mazeController;
    }
}
