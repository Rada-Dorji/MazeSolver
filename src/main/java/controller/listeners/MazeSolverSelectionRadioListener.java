package controller.listeners;

import controller.MazeActionListener;
import controller.MazeController;
import model.MazeState;
import model.SolverType;

import java.awt.event.ActionEvent;

/**
 * An ActionListener (extending MazeActionListener) that listens for changes in the maze solve method
 * radio button selection, updates the maze solve selection accordingly, and re-runs the solver on the
 * current maze if it has been generated.
 */
public class MazeSolverSelectionRadioListener extends MazeActionListener {

    public MazeSolverSelectionRadioListener(MazeController mazeController) {
        super(mazeController);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final var command = e.getActionCommand();
        final var solverTypeChoice = SolverType.fromString(command); // Determines which solver type has been selected.

        if (solverTypeChoice == null) {
            return;
        }

        // Update the solver type in the MazeController
        mazeController.setSolverType(solverTypeChoice);

        // Reuse the maze structure if the maze has been generated
        if (mazeController.getState() == MazeState.GENERATED || mazeController.getState() == MazeState.SOLVED) {
            mazeController.reuseMaze(); // Clear any previous visits without regenerating the maze structure
            mazeController.solve();     // Start solving with the newly selected solver type
        }
    }
}
