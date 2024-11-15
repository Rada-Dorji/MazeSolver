package model;

import controller.MazeController;

import javax.swing.*;

/**
 * An abstract class that extends the SwingWorker class. This class is extended by all maze generators. 
 * All SwingWorker classes extending this class will return a Boolean class type indicating if the maze 
 * generation was a success and publish Maze instances at each iteration of the generation to be 
 * repainted in the view.
 */
public abstract class MazeGeneratorWorker extends SwingWorker<Boolean, Maze> {
    
    /** The maze instance being generated. */
    protected final Maze maze;

    /** The controller responsible for managing maze-related operations. */
    protected final MazeController mazeController;

    /**
     * Constructs a MazeGeneratorWorker with the specified maze and maze controller.
     *
     * @param maze The maze instance to be generated.
     * @param mazeController The controller managing the maze operations.
     */
    protected MazeGeneratorWorker(Maze maze, MazeController mazeController) {
        this.maze = maze;
        this.mazeController = mazeController;
    }
}





