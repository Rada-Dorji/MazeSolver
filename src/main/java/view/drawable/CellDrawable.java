package view.drawable;

import model.Cell;
import model.Cell.Wall;
import model.MazeState;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * Class responsible for drawing an individual cell in the maze.
 */
class CellDrawable {
	private static final Color WALL = Color.white;
	private static final Color CURRENT = Color.green;
	private static final Color START = Color.cyan;
	private static final Color END = Color.magenta;
	private static final Color SOLUTION = Color.green;
	private static final Color VISITING = Color.red;
	private static final Color VISITED = Color.blue;

	private static final int WALL_STROKE_SIZE = CellDrawableConstants.WALL_STROKE_SIZE;
	private static final Stroke WALL_STROKE = new BasicStroke(WALL_STROKE_SIZE);
	private static final int SOLUTION_PATH_STROKE_SIZE = CellDrawableConstants.SOLUTION_PATH_STROKE_SIZE;
	private static final Stroke SOLUTION_PATH_STROKE = new BasicStroke(SOLUTION_PATH_STROKE_SIZE);

	private static final int CELL_SIZE = CellDrawableConstants.CELL_SIZE;
	private static final int MARGIN = CellDrawableConstants.MARGIN;
	private static final double SOLUTION_ROUTE_POINT_SIZE = CellDrawableConstants.SOLUTION_ROUTE_POINT_SIZE;
	private static final double SOLUTION_ROUTE_POINT_OFFSET = CellDrawableConstants.SOLUTION_ROUTE_POINT_OFFSET;
	private static final double SOLUTION_ROUTE_PATH_OFFSET = CellDrawableConstants.SOLUTION_ROUTE_PATH_OFFSET;
	private static final double CELL_OFFSET = CellDrawableConstants.CELL_OFFSET;

	/**
	 * Draws the current cells walls, and fills it with the appropriate fill based on the cell properties and the
	 * current state of the maze.
	 *
	 * @param cell       The cell to be drawn
	 * @param graphics2D A Graphics2D instance
	 * @param mazeState  The current state of the maze
	 * @param xOffset    The x offset
	 * @param yOffset    The y offset
	 */
	public static void drawCell(Cell cell, Graphics2D graphics2D, MazeState mazeState, int xOffset, int yOffset) {
		int cellX = cell.getCellX(MARGIN, CELL_SIZE);
		int cellY = cell.getCellY(MARGIN, CELL_SIZE);

		drawCellWalls(graphics2D, cell, cellX, cellY, xOffset, yOffset); // Draw the cell walls

		// The fill of the cell is determined by the current state of the maze
		switch (mazeState) {
			case GENERATED:
				generatedCellDraw(graphics2D, cell, cellX, cellY, xOffset, yOffset);
				break;
			case SOLVING:
				solvingCellDraw(graphics2D, cell, cellX, cellY, xOffset, yOffset);
				break;
			case SOLVED:
				solvedCellDraw(graphics2D, cell, cellX, cellY, xOffset, yOffset);
				break;
		}

		if (cell.current()) {
			graphics2D.setColor(CURRENT);
			graphics2D.fill(fillCell(cellX, cellY, xOffset, yOffset));
		}
	}

	/**
	 * Draws each of the current cell's walls if they are present.
	 *
	 * @param graphics2D A Graphics2D instance
	 * @param cell       The current cell to draw
	 * @param cellX      The cells top left x coordinate in the maze panel
	 * @param cellY      The cells top left y coordinate in the maze panel
	 * @param xOffset    The x offset
	 * @param yOffset    The y offset
	 */
	private static void drawCellWalls(Graphics2D graphics2D, Cell cell, int cellX, int cellY, int xOffset,
									  int yOffset) {
		int xStart, yStart, xEnd, yEnd;

		graphics2D.setStroke(WALL_STROKE);
		graphics2D.setColor(WALL);

		for (Wall wall : cell.getWalls()) {
			if (wall.isPresent()) {
				xStart = cellX + (wall.xStart() * CELL_SIZE) + xOffset;
				yStart = cellY + (wall.yStart() * CELL_SIZE) + yOffset;
				xEnd = cellX + (wall.xEnd() * CELL_SIZE) + xOffset;
				yEnd = cellY + (wall.yEnd() * CELL_SIZE) + yOffset;
				graphics2D.draw(new Line2D.Double(xStart, yStart, xEnd, yEnd));
			}
		}
	}

	/**
	 * Fills the cells if the state of the maze is GENERATED.
	 *
	 * @param graphics2D A Graphics2D instance
	 * @param cell       The current cell to draw
	 * @param cellX      The cells top left x coordinate in the maze panel
	 * @param cellY      The cells top left y coordinate in the maze panel
	 * @param xOffset    The x offset
	 * @param yOffset    The y offset
	 */
	private static void generatedCellDraw(Graphics2D graphics2D, Cell cell, int cellX, int cellY, int xOffset,
										  int yOffset) {
		if (cell.getStart()) { // Draws the maze's start cell
			graphics2D.setColor(START);
			graphics2D.fill(fillCell(cellX, cellY, xOffset, yOffset));
		} else if (cell.getEnd()) { // Draws the maze's end cell
			graphics2D.setColor(END);
			graphics2D.fill(fillCell(cellX, cellY, xOffset, yOffset));
		}
	}

	/**
	 * Fills the cells if the state of the maze is SOLVING.
	 *
	 * @param graphics2D A Graphics2D instance
	 * @param cell       The current cell to draw
	 * @param cellX      The cells top left x coordinate in the maze panel
	 * @param cellY      The cells top left y coordinate in the maze panel
	 * @param xOffset    The x offset
	 * @param yOffset    The y offset
	 */
	private static void solvingCellDraw(Graphics2D graphics2D, Cell cell, int cellX, int cellY, int xOffset,
										int yOffset) {
		if (cell.visiting()) { // Draws the cell that is currently being visited
			graphics2D.setColor(VISITING);
			graphics2D.fill(fillCell(cellX, cellY, xOffset, yOffset));
		} else if (cell.visited()) { // Draws the cell that has being visited
			graphics2D.setColor(VISITED);
			graphics2D.fill(fillCell(cellX, cellY, xOffset, yOffset));
		}

		if (cell.getStart()) { // Draws the maze's start cell
			graphics2D.setColor(START);
			graphics2D.fill(fillCell(cellX, cellY, xOffset, yOffset));
		} else if (cell.getEnd()) { // Draws the maze's end cell
			graphics2D.setColor(END);
			graphics2D.fill(fillCell(cellX, cellY, xOffset, yOffset));
		}
	}

	/**
	 * Fills the cells if the state of the maze is SOLVED.
	 *
	 * @param graphics2D A Graphics2D instance
	 * @param cell       The current cell to draw
	 * @param cellX      The cells top left x coordinate in the maze panel
	 * @param cellY      The cells top left y coordinate in the maze panel
	 * @param xOffset    The x offset
	 * @param yOffset    The y offset
	 */
	private static void solvedCellDraw(Graphics2D graphics2D, Cell cell, int cellX, int cellY, int xOffset,
									   int yOffset) {
		if (cell.getSolution()) { // Draws the cells that are part of the solution path
			graphics2D.setColor(SOLUTION);
			drawSolutionPathComponent(graphics2D, cell, cellX, cellY, xOffset, yOffset);
		}
	}

	/**
	 * Draws a solution path in the current cell
	 *
	 * @param graphics2D A Graphics2D instance
	 * @param cell       The current cell to draw (in the solution path)
	 * @param cellX      The cells top left x coordinate in the maze panel
	 * @param cellY      The cells top left y coordinate in the maze panel
	 * @param xOffset    The x offset
	 * @param yOffset    The y offset
	 */
	private static void drawSolutionPathComponent(Graphics2D graphics2D, Cell cell, int cellX, int cellY, int xOffset,
												  int yOffset) {
		double solutionRoutePointX = cellX + SOLUTION_ROUTE_POINT_OFFSET + xOffset;
		double solutionRoutePointY = cellY + SOLUTION_ROUTE_POINT_OFFSET + yOffset;

		Ellipse2D.Double solutionRoutePoint = new Ellipse2D.Double(solutionRoutePointX, solutionRoutePointY,
				SOLUTION_ROUTE_POINT_SIZE, SOLUTION_ROUTE_POINT_SIZE);
		graphics2D.fill(solutionRoutePoint);

		if (cell.parent() != null) {
			double solutionRoutePathPointStartX = cellX + SOLUTION_ROUTE_PATH_OFFSET + xOffset;
			double solutionRoutePathPointStartY = cellY + SOLUTION_ROUTE_PATH_OFFSET + yOffset;

			double solutionRoutePathPointEndX = cell.parent().getCellX(MARGIN, CELL_SIZE) +
					SOLUTION_ROUTE_PATH_OFFSET + xOffset;
			double solutionRoutePathPointEndY = cell.parent().getCellY(MARGIN, CELL_SIZE) +
					SOLUTION_ROUTE_PATH_OFFSET + yOffset;

			graphics2D.setStroke(SOLUTION_PATH_STROKE);
			graphics2D.draw(new Line2D.Double(solutionRoutePathPointStartX, solutionRoutePathPointStartY,
					solutionRoutePathPointEndX, solutionRoutePathPointEndY));
		}
	}

	/**
	 * Returns a Rectangle2D to fill a cell
	 *
	 * @param cellX   The cells top left x coordinate in the maze panel
	 * @param cellY   The cells top left y coordinate in the maze panel
	 * @param xOffset The x offset
	 * @param yOffset The y offset
	 * @return A Rectangle2D to fill a cell
	 */
	private static Rectangle2D.Double fillCell(int cellX, int cellY, int xOffset, int yOffset) {
		return new Rectangle2D.Double(cellX + CELL_OFFSET + xOffset, cellY + CELL_OFFSET + yOffset,
				CELL_SIZE - WALL_STROKE_SIZE, CELL_SIZE - WALL_STROKE_SIZE);
	}
}
