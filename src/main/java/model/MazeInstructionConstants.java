package model;

/**
 * Constants for maze instructions displayed to the user.
 */
public class MazeInstructionConstants {

    // Instructions for the maze generation and solving process
    static final String INIT = "Set the desired number of rows and columns for the maze, and then click " +
            "\"Generate\" to create a maze.";
    static final String GENERATING = "Generating a new maze! Adjust the animation slider to speed up or slow down " +
            "the animation. To interrupt the maze generation, click the \"Reset\" button.";
    static final String GENERATED = "Maze successfully generated! Set the start and end points for the maze by " +
            "clicking a cell in the maze (the start and end points will be defaulted if you don't), choose the " +
            "maze solution algorithm, and then click the \"Solve\" button to solve the maze.";
    static final String SOLVING = "Solving the maze! Adjust the animation slider to speed up or slow down the " +
            "animation. To interrupt the maze solving, click the \"Reset\" button.";
    static final String SOLVED = "Maze solved! Click the \"Reset\" button to restart.";
    
    static final String USER_SOLVING = "Manual solve mode: Use arrow keys to navigate through the maze.";
}
