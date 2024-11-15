package controller.listeners;

import controller.MazeActionListener;
import controller.MazeController;
import model.MazeState;

import java.awt.event.ActionEvent;

/**
 * An ActionListener (extending MazeActionListener) that is registered and listens for clicks from the 'Solve'
 * button and triggers the maze solve. Checks that the state of the maze is viable for maze solving.
 */
public class MazeSolverListener extends MazeActionListener {

    public MazeSolverListener(MazeController mazeController) {
        super(mazeController);
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        if (canSolve()) {
            mazeController.solve();
        }
    }

    /**
     * Determines if maze solving can be triggered. A maze can only be generated if in the GENERATED state (after a maze
     * has been generated but not being solved or already solved).
     */
    private boolean canSolve() {
        final var mazeState = mazeController.getState();
        return mazeState == MazeState.GENERATED;
    }
}
