package model;

/**
 * Directions available for maze generation and solving.
 */
public enum Direction {
    UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);

    /**
     * The delta x and y coordinates (in the Cartesian coordinate system) for each direction.
     */
    public final int dx, dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Calculates and returns the opposite direction of a direction (i.e., if UP, returns DOWN).
     *
     * @return The opposite direction of a direction.
     */
    public Direction oppositeDirection() {
        int opposite_dx = -this.dx;
        int opposite_dy = -this.dy;

        return switch (opposite_dx) {
            case 0 -> (opposite_dy == -1 ? UP : DOWN);
            case -1 -> LEFT;
            case 1 -> RIGHT;
            default -> throw new IllegalArgumentException("Invalid direction");
        };
    }
}
