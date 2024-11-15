package controller.listeners;

import controller.MazeChangeListener;
import controller.MazeController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;

/**
 * A ChangeListener (extending MazeChangeListener) that listens for changes in the number of rows spinner and sets
 * the number of rows to be used in the next maze generation accordingly.
 */
public class MazeCustomNumRowsListener extends MazeChangeListener {

    public MazeCustomNumRowsListener(MazeController mazeController) {
        super(mazeController);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        final var rowsSpinner = (JSpinner) e.getSource();
        final var rowsSpinnerModel = (SpinnerNumberModel) rowsSpinner.getModel();
        final var numRows = (int) rowsSpinnerModel.getNumber();
        mazeController.setMazeNumRows(numRows);
    }
}
