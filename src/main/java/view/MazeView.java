package view;

import controller.MazeController;
import model.Maze;
import view.drawable.GUIPanel;
import view.drawable.InstructionsPanel;
import view.drawable.MazePanel;

import javax.swing.*;
import java.awt.*;

/**
 * The view of the maze, following the MVC design pattern. 
 * This JFrame contains the maze panel (displaying the maze) 
 * and the GUI panel (containing UI elements). It acts as an 
 * intermediary between the controller and its child panels.
 */
public class MazeView extends JFrame {
    private final MazeController mazeController;
    private final MazePanel mazePanel;
    final GUIPanel guiPanel;
    private final InstructionsPanel instructionsPanel;

    public MazeView(Maze maze, MazeController mazeController) {
        super("Maze Solver");
        this.mazeController = mazeController;
        this.guiPanel = new GUIPanel(mazeController);
        this.mazePanel = new MazePanel(maze, mazeController);
        this.instructionsPanel = new InstructionsPanel();

        initDisplay();
    }

    private void initDisplay() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridBagLayout());

        Insets insets = new Insets(5, 5, 5, 5);

        // Add components to the layout
        addComponent(mazePanel, 0, 0, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets);
        addComponent(guiPanel, 2, 0, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, insets);
        addComponent(instructionsPanel, 0, 1, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets);

        setInstructions();
        setVisible(true);
        pack();
    }

    /**
     * Sets the maze instructions based on the current maze state 
     * and adjusts the view size to account for changes in text.
     */
    public void setInstructions() {
        instructionsPanel.setInstructions(mazeController.getState().getInstruction());
        pack();
    }

    /**
     * Resizes the maze panel to account for changes in the number 
     * of rows and columns.
     */
    public void resize() {
        mazePanel.resize();
        pack();
        mazePanel.setOffset(mazePanel.getWidth(), mazePanel.getHeight());
        mazePanel.repaint();
    }

    /**
     * Resets the view after the reset action is triggered.
     */
    public void resetView() {
        mazePanel.resetWaypointSetterState();
        mazePanel.repaint();
    }

    /**
     * Repaints the maze with the provided maze instance.
     *
     * @param maze The maze instance to repaint
     */
    public void repaintMaze(Maze maze) {
        mazePanel.repaintMaze(maze);
    }

    /**
     * Adds a component to the maze view using GridBagLayout.
     *
     * @param component  The component to add to the view
     * @param gridx      The component's x coordinate in the GridBagLayout
     * @param gridy      The component's y coordinate in the GridBagLayout
     * @param gridwidth  The GridBagLayout grid width
     * @param gridheight The GridBagLayout grid height
     * @param anchor     The component's anchor in the GridBagLayout
     * @param fill       The component's fill in the GridBagLayout
     */
    private void addComponent(Component component, int gridx, int gridy, int gridwidth, int gridheight, 
                              int anchor, int fill, Insets insets) {
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 
                1.0, 1.0, anchor, fill, insets, 0, 0);
        getContentPane().add(component, gbc);
    }
     /**
     * Updates the cells checked count displayed on the panel.
     *
     * @param cellsChecked The number of cells checked.
     */
    public void updateCellsChecked(int cellsChecked) {
        guiPanel.cellsCheckedLabel.setText("Cells Checked: " + cellsChecked);
    }

    /**
     * Updates the path cells count displayed on the panel.
     *
     * @param pathCells The number of path cells.
     */
    public void updatePathCells(int pathCells) {
        guiPanel.pathCellsLabel.setText("Path Cells: " + pathCells);
    }
     /**
     * Displays a congratulatory message when the user successfully solves the maze.
     */
    public void showWinMessage() {
        JOptionPane.showMessageDialog(this, "You solved the maze!", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
    }

    

    
}
