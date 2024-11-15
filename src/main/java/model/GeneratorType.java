package model;

/**
 * All of the different maze generator types.
 */
public enum GeneratorType {
    RECURSIVE_BACKTRACKER;

    /**
     * Returns a user-friendly name for the generator type.
     *
     * @return A string representation of the generator type.
     */
    @Override
    public String toString() {
        return switch (this) {
            case RECURSIVE_BACKTRACKER -> "Recursive Backtracker";
        };
    }
}
