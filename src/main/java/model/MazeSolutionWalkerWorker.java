package model;

import controller.MazeController;

import javax.swing.*;
import java.util.List;

/**
 * A SwingWorker that draws the solution path of the maze from the end cell to the start cell.
 */
public class MazeSolutionWalkerWorker extends SwingWorker<Void, Maze> {
    private final Maze maze;
    private final MazeController mazeController;

    public MazeSolutionWalkerWorker(Maze maze, MazeController mazeController) {
        this.maze = maze;
        this.mazeController = mazeController;
    }

    /**
     * Override of the SwingWorker doInBackground method, executing the solution path walking in a background thread.
     * This method publishes Maze instances at each iteration of the solution path walking to be repainted in the view
     * asynchronously.
     * <p>
     * This function begins at the ending cell of the maze and iterates up through its parent cells until a parent no
     * longer exists, at which point the beginning of the maze has been reached.
     *
     * @return null
     * @throws InterruptedException If the thread is interrupted during sleep
     */
    @Override
    protected Void doInBackground() throws InterruptedException {
        Cell goal = maze.getGoal();
        

        while (goal != null) {
            goal.setSolution(true);
            goal = goal.parent();

            publish(maze);
            Thread.sleep(mazeController.getAnimationSpeed());
        }

        return null;
    }

    /**
     * Override of the SwingWorker process function for the recursive solution path walking SwingWorker, which repaints
     * the maze at every iteration of the solution path walking asynchronously on the event dispatch thread.
     *
     * @param chunks The list of Maze instances to be repainted
     */
    @Override
    protected void process(List<Maze> chunks) {
        for (Maze maze : chunks) {
            mazeController.repaintMaze(maze);
        }
    }
}
