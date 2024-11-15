package view.drawable;

import controller.MazeController;
import controller.listeners.MazeWaypointClickListener;
import model.Cell;
import model.Maze;
import model.MazeState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * A JPanel of the maze. This panel is where the maze is drawn during maze generation and solving, and where the user
 * can pick the start and end points.
 */
public class MazePanel extends JPanel {
    private static final Color BACKGROUND = new Color(55, 50, 55);
    private final MazeController mazeController;
    private final MazeDrawable mazeDrawable;
    private Maze maze;
    private WaypointState waypointState;
    private int yOffset, xOffset;
    private Dimension mazeDimension;
    private Cell currentCell; // Add a field to track the current cell


    public MazePanel(Maze maze, MazeController mazeController) {
        this.maze = maze;
        this.mazeController = mazeController;
        this.mazeDrawable = new MazeDrawable();
        this.waypointState = WaypointState.START;
        this.yOffset = this.xOffset = 0;
        this.mazeDimension = new Dimension();
        this.currentCell = maze.getStartingCell();  // Initialize currentCell
        // addKeyListener(new MazeKeyListener()); // Add the KeyListener
        setFocusable(true);

        initMazePanel();
        setDefaultWaypoints();
    }

    public Maze getMaze() {
        return maze;
    }
    
    public void setCurrentCell(Cell cell) {
        this.currentCell = cell;
    }
    

    private void initMazePanel() {
        int mazeWidth = maze.numCols() * CellDrawableConstants.CELL_SIZE + CellDrawableConstants.MARGIN * 2;
        int mazeHeight = maze.numRows() * CellDrawableConstants.CELL_SIZE + CellDrawableConstants.MARGIN * 2;

        mazeDimension = new Dimension(mazeWidth, mazeHeight);
        setMinimumSize(mazeDimension);
        setPreferredSize(mazeDimension);
        setBackground(BACKGROUND);

        addMouseListener(new MazeWaypointClickListener(this, mazeController));

        repaint();
    }

       /**
     * Automatically sets the default start and end points in the maze.
     */
    public void setDefaultWaypoints() {
        if (maze.numRows() > 0 && maze.numCols() > 0) {
            // Set the start point in the top-left corner and end point in the bottom-right corner
            Cell startCell = maze.mazeCell(0, 0);
            Cell endCell = maze.mazeCell(maze.numRows() - 1, maze.numCols() - 1);

            maze.setStartingCell(startCell);
            maze.setEndingCell(endCell);

            repaint(); // Repaint to show the updated waypoints
        }
    }

    /**
     * Calculate the x and y offsets to account for a change in the number of rows and columns.
     *
     * @param panelWidth  The maze panel width
     * @param panelHeight The maze panel height
     */
    public void setOffset(int panelWidth, int panelHeight) {
        int widthDifference = (int) (panelWidth - mazeDimension.getWidth());
        int heightDifference = (int) (panelHeight - mazeDimension.getHeight());

        xOffset = Math.max(0, widthDifference / 2);
        yOffset = Math.max(0, heightDifference / 2);
    }

    public void repaintMaze(Maze maze) {
        this.maze = maze;
        setDefaultWaypoints(); // Set default waypoints again if a new maze is generated
        repaint();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        MazeState mazeState = mazeController.getState();
        mazeDrawable.drawMaze(maze, graphics, mazeState, xOffset, yOffset);

        // Highlight the current cell (example – adapt as needed for your MazeDrawable)
        if (currentCell != null) {
            graphics.setColor(Color.YELLOW); // Or any other highlight color
            int x = currentCell.col() * CellDrawableConstants.CELL_SIZE + CellDrawableConstants.MARGIN + xOffset;
            int y = currentCell.row() * CellDrawableConstants.CELL_SIZE + CellDrawableConstants.MARGIN + yOffset;
            graphics.fillRect(x, y, CellDrawableConstants.CELL_SIZE, CellDrawableConstants.CELL_SIZE);
        }
    }
    
    public void resize() {
        int mazeWidth = maze.numCols() * CellDrawableConstants.CELL_SIZE + CellDrawableConstants.MARGIN * 2;
        int mazeHeight = maze.numRows() * CellDrawableConstants.CELL_SIZE + CellDrawableConstants.MARGIN * 2;

        mazeDimension = new Dimension(mazeWidth, mazeHeight);
        setMinimumSize(mazeDimension);
        setPreferredSize(mazeDimension);
    }

    /**
     * Sets a waypoint in the maze (based on the current state of the waypoint setting), and repaints the maze to show
     * the new waypoint. As setting one waypoint enables the setting of another, the user can change their choice for
     * the start and end points before solving the maze.
     *
     * @param mouseClickX The x coordinate of a mouse click
     * @param mouseClickY The y coordinate of a mouse click
     */
    public void setWaypoint(int mouseClickX, int mouseClickY) {
        for (int r = 0; r < maze.numRows(); r++) {
            for (int c = 0; c < maze.numCols(); c++) {
                Cell cell = maze.mazeCell(r, c);
                if (cell.pointInside(mouseClickX, mouseClickY, CellDrawableConstants.CELL_SIZE,
                        CellDrawableConstants.MARGIN, xOffset, yOffset)) {
    
                    if (waypointState == WaypointState.START && !cell.getEnd()) {
                        maze.setStartingCell(cell);
                        waypointState = WaypointState.END;
                        requestFocusInWindow(); // Ensure panel is focused
                        repaint();
                        return;
                    } else if (waypointState == WaypointState.END && !cell.getStart()) {
                        maze.setEndingCell(cell);
                        waypointState = WaypointState.START;
                        requestFocusInWindow(); // Ensure panel is focused
                        repaint();
                        return;
                    }
                }
            }
        }
    }
    

    /**
     * Resets the waypoint state to the initial start state.
     */
    public void resetWaypointSetterState() {
        waypointState = WaypointState.START;
    }

    /**
     * The state of setting waypoints. The two waypoints that are set are the start and end points.
     */
    private enum WaypointState {
        START, END
    }
}
