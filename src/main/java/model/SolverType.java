package model;

/**
 * All of the different maze solver types.
 */
public enum SolverType {
    BFS("BFS"),
    DFS("DFS"),
    AStar("A*");

    private final String name;

    /**
     * Constructor for SolverType.
     *
     * @param name The name of the solver type.
     */
    SolverType(String name) {
        this.name = name;
    }

    /**
     * Returns the enum value corresponding to the input string (if it exists).
     *
     * @param name A string representing the name of the solver type.
     * @return The SolverType corresponding to the input string, or null if no match is found.
     */
    public static SolverType fromString(String name) {
        for (SolverType solverType : SolverType.values()) {
            if (solverType.name.equalsIgnoreCase(name)) {
                return solverType;
            }
        }
        return null; // Explicitly returning null if no match is found
    }

    /**
     * Returns the name of the solver type.
     *
     * @return The name string of the solver type.
     */
    public String getName() {
        return name;
    }
}
