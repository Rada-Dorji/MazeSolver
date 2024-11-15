package view.drawable;

/**
 * Constants for cell drawing in the maze visualization.
 */
class CellDrawableConstants {
    // Basic dimensions and offsets
    static final int CELL_SIZE = 20; // Size of each cell
    static final int MARGIN = 10; // Margin around the cells
    static final int WALL_STROKE_SIZE = 2; // Stroke size for walls
    static final int SOLUTION_PATH_STROKE_SIZE = 1; // Stroke size for solution path

    // Scaling factors for drawing solution route points
    static final int SOLUTION_ROUTE_POINT_SCALING_FACTOR = 4; // Scaling factor for solution route points
    
    // Size of the solution route points
    static final double SOLUTION_ROUTE_POINT_SIZE = 
        (CELL_SIZE - (WALL_STROKE_SIZE / 2.0)) / SOLUTION_ROUTE_POINT_SCALING_FACTOR;
    
    // Offset for solution route points
    static final double SOLUTION_ROUTE_POINT_OFFSET = 
        (WALL_STROKE_SIZE / 2.0) + (CELL_SIZE / 2.0) - (CELL_SIZE / (2.0 * SOLUTION_ROUTE_POINT_SCALING_FACTOR));
    
    // Offset for the solution route path
    static final double SOLUTION_ROUTE_PATH_OFFSET = (WALL_STROKE_SIZE / 2.0) + (CELL_SIZE / 2.0); 
    
    // General cell offset
    static final double CELL_OFFSET = WALL_STROKE_SIZE / 2.0; 
}
