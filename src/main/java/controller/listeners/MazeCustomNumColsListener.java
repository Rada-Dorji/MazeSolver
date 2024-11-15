package controller.listeners;

import controller.MazeChangeListener;
import controller.MazeController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;

/**
 * A ChangeListener (extending MazeChangeListener) that listens for changes in the number of columns spinner and sets
 * the number of columns to be used in the next maze generation accordingly.
 */
public class MazeCustomNumColsListener extends MazeChangeListener {

    public MazeCustomNumColsListener(MazeController mazeController) {
        super(mazeController);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        final var colsSpinner = (JSpinner) e.getSource();
        final var colsSpinnerModel = (SpinnerNumberModel) colsSpinner.getModel();
        final var numCols = (int) colsSpinnerModel.getNumber();
        mazeController.setMazeNumCols(numCols);
    }
}
