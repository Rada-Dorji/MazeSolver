package view.drawable;

import controller.MazeController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static view.drawable.DrawableHelper.addComponent;

/**
 * A JPanel of the GUI to the right of the maze. This panel stores all the UI components that the user can use
 * to interact with the maze including the maze dimension spinners, the solution method radio, the animation speed
 * slider, and the buttons.
 */
public class GUIPanel extends JPanel implements ActionListener {
    private final MazeController mazeController;
    public final JLabel cellsCheckedLabel;
    public final JLabel pathCellsLabel;

    public GUIPanel(MazeController mazeController) {
        this.mazeController = mazeController;
        this.cellsCheckedLabel = new JLabel("Cells Checked: 0");
        this.pathCellsLabel = new JLabel("Path Cells: 0");
        initGUIPanel();
    }

    private void initGUIPanel() {
        setLayout(new GridBagLayout());

        var insets = new Insets(5, 0, 0, 0);  // Use var for insets

        // Maze Size Inputs
        var customMazeSizeInputs = new MazeSizeInputs(mazeController.getMazeCustomNumRowsListener(),
                mazeController.getMazeCustomNumColsListener());
        addComponent(this, customMazeSizeInputs, 0, 0, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets);

        // Generate Maze Button
        initButton(new JButton("Generate"), "generate", 1,
                mazeController.getMazeGeneratorListener(), insets);

        // Solve Method Radio
        var solveMethodRadio = new SolveMethodRadioPanel(mazeController.getMazeSolverSelectionRadioListener(),
                mazeController.getSolverType());
        addComponent(this, solveMethodRadio, 0, 2, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets);

        // Solve Maze Button
        initButton(new JButton("Solve"), "solve", 3,
                mazeController.getMazeSolverListener(), insets);

        // User Solve Button
        initButton(new JButton("User Solve"), "userSolve", 4,
                this, insets);

        // Speed Slider
        var mazeAnimationSpeedSliderPanel = new AnimationSliderPanel(mazeController.getMazeAnimationSpeedSliderListener());
        addComponent(this, mazeAnimationSpeedSliderPanel, 0, 5, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets);

        // Reset Maze Button
        initButton(new JButton("Reset"), "reset", 8,
                mazeController.getMazeResetListener(), insets);

        // Check Label
        addComponent(this, cellsCheckedLabel, 0, 6, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets);

        // Path Label
        addComponent(this, pathCellsLabel, 0, 7, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets);

        // Exit Button
        initButton(new JButton("Exit"), "exit", 9, this, insets);

        
    }

    /**
     * Initializes a button and adds it to the GUI panel.
     *
     * @param button              New button to add to the GUI panel
     * @param buttonActionCommand The new button's ActionCommand
     * @param gridy               The button's y coordinate in the GridBagLayout
     * @param actionListener      The actionListener to attach to the button
     * @param insets              The button's insets
     */
    private void initButton(JButton button,
                            String buttonActionCommand,
                            int gridy,
                            ActionListener actionListener,
                            Insets insets) {
        button.setActionCommand(buttonActionCommand);
        button.addActionListener(actionListener);
        addComponent(this, button, 0, gridy, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets);
    }

    /**
     * Override of the actionPerformed action. The only button that is listened to and handled directly by the GUI panel
     * is the 'Exit' button, which terminates the application.
     *
     * @param mazeGuiButtonClick A button click ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent mazeGuiButtonClick) {
        var buttonIntention = mazeGuiButtonClick.getActionCommand();  // Use var for button intention

        if (buttonIntention.equals("exit")) {
            System.exit(0);
        }

        // Handle "User Solve" action
        if (buttonIntention.equals("userSolve")) {
            // Trigger manual user solve
            mazeController.startUserSolve();
            
        }
    }
}
