package model;

/**
 * Constants for maze animation.
 */
public class MazeAnimationConstants {
    /** The default sleep time for animations in milliseconds. */
    public static final int DEFAULT_ANIMATION_SLEEP = 50;
    
    /** The minimum sleep time for animations in milliseconds. */
    public static final int MINIMUM_ANIMATION_SLEEP = 5;
    
    /** The maximum sleep time for animations in milliseconds. */
    public static final int MAXIMUM_ANIMATION_SLEEP = 100;
    
    /** Multiplier for sleep time during maze generation. */
    public static final double GENERATION_SLEEP_TIME_MULTIPLIER = 1.0;
    
    /** Multiplier for sleep time during maze solving. */
    public static final double SOLVE_SLEEP_TIME_MULTIPLIER = 3.0;
    
    /** Multiplier for sleep time when displaying the solution. */
    public static final double SOLUTION_SLEEP_TIME_MULTIPLIER = 2.0;
}
