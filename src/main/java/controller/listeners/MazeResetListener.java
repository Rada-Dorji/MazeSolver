package controller.listeners;

import controller.MazeActionListener;
import controller.MazeController;

import java.awt.event.ActionEvent;

/**
 * An ActionListener (extending MazeActionListener) that is registered and listens for clicks from the 'Reset'
 * button and triggers maze resetting.
 */
public class MazeResetListener extends MazeActionListener {

    public MazeResetListener(MazeController mazeController) {
        super(mazeController);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        mazeController.reset();
    }
}
