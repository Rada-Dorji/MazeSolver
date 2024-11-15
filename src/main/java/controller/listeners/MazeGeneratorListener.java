package controller.listeners;

import controller.MazeActionListener;
import controller.MazeController;
import model.MazeState;

import java.awt.event.ActionEvent;

/**
 * An ActionListener (extending MazeActionListener) that is registered and listens for clicks from the 'Generate'
 * button and triggers the maze generation. Checks that the state of the maze is viable for maze generation.
 */
public class MazeGeneratorListener extends MazeActionListener {

    public MazeGeneratorListener(MazeController mazeController) {
        super(mazeController);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (canGenerate()) {
            mazeController.generate();
        }
    }

    /**
     * Determines if maze generation can be triggered. A maze can only be generated if in the INIT state (on start up or
     * after reset), or in the GENERATED state (after a maze has been generated but not being solved or already solved).
     */
    private boolean canGenerate() {
        final var mazeState = mazeController.getState();
        return mazeState == MazeState.INIT || mazeState == MazeState.GENERATED || mazeState == MazeState.USER_SOLVING; 
    }
}
