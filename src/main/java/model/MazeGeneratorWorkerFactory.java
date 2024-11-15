package model;

import controller.MazeController;
import model.generators.RecursiveBacktracker;

/**
 * The factory class for creating MazeGeneratorWorker instances.
 */
public class MazeGeneratorWorkerFactory {

    /**
     * Initializes a MazeGeneratorWorker based on the specified generator type.
     *
     * @param generatorType The type of maze generator to create.
     * @param maze The maze instance to be generated.
     * @param mazeController The controller managing maze operations.
     * @return A MazeGeneratorWorker instance or null if the maze or mazeController is null.
     */
    public static MazeGeneratorWorker initMazeGenerator(GeneratorType generatorType, Maze maze,
                                                        MazeController mazeController) {
        if (maze == null || mazeController == null) {
            return null; // Return null if inputs are invalid
        }

        MazeGeneratorWorker mazeGeneratorWorker;

        // Create the appropriate MazeGeneratorWorker based on the generator type
        switch (generatorType) {
            case RECURSIVE_BACKTRACKER:
                mazeGeneratorWorker = new RecursiveBacktracker(maze, mazeController);
                break;

            // Default to recursive backtracker
            default:
                mazeGeneratorWorker = new RecursiveBacktracker(maze, mazeController);
                break;
        }

        return mazeGeneratorWorker; // Return the created worker
    }
}
