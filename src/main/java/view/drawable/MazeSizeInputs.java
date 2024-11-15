package view.drawable;

import controller.MazeChangeListener;
import controller.listeners.MazeCustomNumColsListener;
import controller.listeners.MazeCustomNumRowsListener;
import model.MazeConstants;

import javax.swing.*;
import java.awt.*;

import static view.drawable.DrawableHelper.addComponent;

/**
 * A JPanel of the two custom maze dimension spinners, the number of rows spinner and the number of columns spinner.
 */
class MazeSizeInputs extends JPanel {
    public MazeSizeInputs(MazeCustomNumRowsListener mazeCustomNumRowsListener, MazeCustomNumColsListener mazeCustomNumColsListener) {
        setLayout(new GridBagLayout());

        // Panel for number of rows
        JPanel numRowsPanel = new JPanel(new GridLayout(1, 0));
        String numRowsSpinnerLabel = String.format("Num Rows (%d-%d):", MazeConstants.MIN_NUM_ROWS, MazeConstants.MAX_NUM_ROWS);
        SpinnerModel numRowsSpinner = new SpinnerNumberModel(MazeConstants.DEFAULT_NUM_ROWS, MazeConstants.MIN_NUM_ROWS, MazeConstants.MAX_NUM_ROWS, 1);

        // Panel for number of columns
        JPanel numColsPanel = new JPanel(new GridLayout(1, 0));
        String numColsSpinnerLabel = String.format("Num Cols (%d-%d):", MazeConstants.MIN_NUM_COLS, MazeConstants.MAX_NUM_COLS);
        SpinnerModel numColsSpinner = new SpinnerNumberModel(MazeConstants.DEFAULT_NUM_COLS, MazeConstants.MIN_NUM_COLS, MazeConstants.MAX_NUM_COLS, 1);

        // Add labeled spinners to panels
        addLabeledSpinner(numRowsPanel, numRowsSpinner, numRowsSpinnerLabel, mazeCustomNumRowsListener);
        addLabeledSpinner(numColsPanel, numColsSpinner, numColsSpinnerLabel, mazeCustomNumColsListener);

        // Insets for grid layout
        Insets insets = new Insets(0, 0, 0, 0);
        addComponent(this, numRowsPanel, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets);
        addComponent(this, numColsPanel, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets);
    }

    /**
     * Attaches a SpinnerModel to a container.
     *
     * @param container          The container to which to add the spinner
     * @param model              The SpinnerModel to add
     * @param label              The SpinnerModel label
     * @param mazeChangeListener The listener to attach to the spinner
     */
    private static void addLabeledSpinner(Container container, SpinnerModel model, String label, MazeChangeListener mazeChangeListener) {
        JLabel spinnerLabel = new JLabel(label);
        container.add(spinnerLabel);

        JSpinner spinner = new JSpinner(model);
        spinner.addChangeListener(mazeChangeListener);
        spinnerLabel.setLabelFor(spinner);
        container.add(spinner);
    }
}
