package model;

/**
 * The potential states for the maze to be in.
 */
public enum MazeState {
    INIT(MazeInstructionConstants.INIT),
    GENERATING(MazeInstructionConstants.GENERATING),
    GENERATED(MazeInstructionConstants.GENERATED),
    SOLVING(MazeInstructionConstants.SOLVING),
    SOLVED(MazeInstructionConstants.SOLVED),
    USER_SOLVING(MazeInstructionConstants.USER_SOLVING);  

    public final String instruction;

    /**
     * Constructor for MazeState.
     *
     * @param instruction The instruction associated with the maze state.
     */
    MazeState(String instruction) {
        this.instruction = instruction;
    }

    /**
     * Returns the instruction associated with the maze state.
     *
     * @return The instruction string.
     */
    public String getInstruction() {
        return instruction;
    }
}
