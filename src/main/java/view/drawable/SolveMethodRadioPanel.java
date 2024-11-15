package view.drawable;

import controller.listeners.MazeSolverSelectionRadioListener;
import model.SolverType;

import javax.swing.*;
import java.awt.*;

import static view.drawable.DrawableHelper.addComponent;

/**
 * A JPanel of maze solver options radio.
 */
class SolveMethodRadioPanel extends JPanel {
    public SolveMethodRadioPanel(MazeSolverSelectionRadioListener solverSelectionRadioListener, SolverType mazeSolverType) {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Solve Method"));

        // Create a vertical box for radio buttons
        Box solveMethodRadioBox = Box.createVerticalBox();
        ButtonGroup solveMethodRadioButtonGroup = new ButtonGroup();

        // Iterate through all the solver types and create a corresponding radio option
        for (SolverType solverType : SolverType.values()) {
            JRadioButton solverTypeOption = new JRadioButton(solverType.getName());
            solverTypeOption.addActionListener(solverSelectionRadioListener);

            // Set the default solver type based on the controller's initial setting
            if (solverType == mazeSolverType) {
                solverTypeOption.setSelected(true);
            }

            solveMethodRadioBox.add(solverTypeOption);
            solveMethodRadioButtonGroup.add(solverTypeOption);
        }

        // Insets for layout
        Insets insets = new Insets(2, 2, 2, 2);
        addComponent(this, solveMethodRadioBox, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets);

        // Set minimum and preferred dimensions for the panel
        Dimension guiDimension = new Dimension(125, 100);
        setMinimumSize(guiDimension);
        setPreferredSize(guiDimension);
    }
}
