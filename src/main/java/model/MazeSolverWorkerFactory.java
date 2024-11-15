package model;

import controller.MazeController;
import model.solvers.AStar;
import model.solvers.BFS;
import model.solvers.DFS;

/**
 * The MazeSolverWorker factory that provides instances of different maze solvers based on the solver type.
 * Each call to initMazeSolver creates a new instance of the solver, allowing the maze to be solved again on the same layout
 * with a different algorithm when needed.
 */
public class MazeSolverWorkerFactory {

    /**
     * Initializes a MazeSolverWorker based on the specified solver type.
     *
     * @param solverType The type of solver to initialize.
     * @param maze       The maze to be solved.
     * @param mazeController The controller for the maze.
     * @return A MazeSolverWorker instance corresponding to the specified solver type, or null if the maze is null.
     */
    public static MazeSolverWorker initMazeSolver(SolverType solverType, Maze maze, MazeController mazeController) {
        if (maze == null) {
            return null;
        }

        MazeSolverWorker mazeSolverWorker;

        switch (solverType) {
            case BFS:
                mazeSolverWorker = new BFS(maze, mazeController);
                break;

            case DFS:
                mazeSolverWorker = new DFS(maze, mazeController);
                break;

            case AStar:
                mazeSolverWorker = new AStar(maze, mazeController);
                break;

            default:
                mazeSolverWorker = new AStar(maze, mazeController);
                break;
        }

        return mazeSolverWorker;
    }
}
