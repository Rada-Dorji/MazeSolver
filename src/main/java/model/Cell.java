package model;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Optional;

/**
 * This class represents the cells in a maze. A maze consists of a 2D array of cells.
 */
public class Cell {
    private final int row, col;
    private final EnumMap<Direction, Wall> walls;
    private CellVisitState visitState;
    private int fCost, gCost, hCost;
    private boolean current, start, end, solution;
    private Cell parent;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        walls = new EnumMap<>(Direction.class) {{
            put(Direction.UP, new Wall(0, 0, 1, 0));
            put(Direction.DOWN, new Wall(0, 1, 1, 1));
            put(Direction.LEFT, new Wall(0, 0, 0, 1));
            put(Direction.RIGHT, new Wall(1, 0, 1, 1));
        }};
        visitState = CellVisitState.UNVISITED;
        current = end = solution = false;
        parent = null;
        fCost = gCost = hCost = Integer.MAX_VALUE;
    }

    public int col() {
        return col;
    }

    public int row() {
        return row;
    }

    public Collection<Wall> getWalls() {
        return walls.values();
    }

    // New methods added to get the x and y coordinates based on cell size and margin
    public int getCellX(int margin, int cellSize) {
        return col * cellSize + margin; // Calculate x position
    }

    public int getCellY(int margin, int cellSize) {
        return row * cellSize + margin; // Calculate y position
    }

    public boolean wallMissing(Direction direction) {
        return Optional.ofNullable(walls.get(direction))
                .map(wall -> !wall.present)
                .orElse(false);
    }

    public void removeWall(Direction direction) {
        Optional.ofNullable(walls.get(direction)).ifPresent(wall -> wall.present = false);
    }

    public boolean current() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public void setVisitState(CellVisitState visitState) {
        this.visitState = visitState;
    }

    public boolean unvisited() {
        return visitState == CellVisitState.UNVISITED;
    }

    public boolean visiting() {
        return visitState == CellVisitState.VISITING;
    }

    public boolean visited() {
        return visitState == CellVisitState.VISITED;
    }

    public boolean getStart() {
        return start;
    }

    public void setStart(boolean state) {
        this.start = state;
    }

    public boolean getEnd() {
        return end;
    }

    public void setEnd(boolean state) {
        this.end = state;
    }

    public Cell parent() {
        return parent;
    }

    public void setParent(Cell parent) {
        this.parent = parent;
    }

    public boolean getSolution() {
        return solution;
    }

    public void setSolution(boolean solution) {
        this.solution = solution;
    }

    public int getFCost() {
        return fCost;
    }

    public void setFCost(int fCost) {
        this.fCost = fCost;
    }

    public int getGCost() {
        return gCost;
    }

    public void setGCost(int gCost) {
        this.gCost = gCost;
    }

    public int getHCost() {
        return hCost;
    }

    public void setHCost(int hCost) {
        this.hCost = hCost;
    }

    /**
     * Returns a boolean indicating whether a mouse click is inside the cell.
     *
     * @param mouseClickX The x coordinate of a mouse click
     * @param mouseClickY The y coordinate of a mouse click
     * @param scale       The maze scale constant
     * @param margin      The maze margin constant
     * @param xOffset     The x offset
     * @param yOffset     The y offset
     * @return Boolean indicating whether a mouse click is inside the cell.
     */
    public boolean pointInside(int mouseClickX, int mouseClickY, int scale, int margin, int xOffset, int yOffset) {
        int cellXStart = (col * scale) + margin + xOffset;
        int cellYStart = (row * scale) + margin + yOffset;
        int cellXEnd = cellXStart + scale;
        int cellYEnd = cellYStart + scale;

        return (mouseClickX >= cellXStart && mouseClickX <= cellXEnd) &&
                (mouseClickY >= cellYStart && mouseClickY <= cellYEnd);
    }

    /**
     * Calculates the direction from a starting cell to a neighboring cell.
     *
     * @param neighbour The neighboring cell
     * @return The direction from the cell to the neighboring cell
     */
    public Optional<Direction> directionToCell(Cell neighbour) {
        int x_diff = neighbour.col() - this.col;

        if (x_diff > 0) {
            return Optional.of(Direction.RIGHT);
        } else if (x_diff < 0) {
            return Optional.of(Direction.LEFT);
        }

        int y_diff = neighbour.row() - this.row;

        if (y_diff > 0) {
            return Optional.of(Direction.DOWN);
        } else if (y_diff < 0) {
            return Optional.of(Direction.UP);
        }

        return Optional.empty();
    }

    public enum CellVisitState {
        UNVISITED, VISITING, VISITED
    }

    /**
     * This class represents the walls for a cell. Each cell has 4 walls, one in each direction (up, down, left, right).
     * A wall instance stores the coordinates of the wall in the maze, and a boolean indicating whether that wall is
     * present or not.
     */
    public static class Wall {
        private final int xStart, yStart, xEnd, yEnd;
        private boolean present;

        public Wall(int xStart, int yStart, int xEnd, int yEnd) {
            this.xStart = xStart;
            this.yStart = yStart;
            this.xEnd = xEnd;
            this.yEnd = yEnd;
            this.present = true;
        }

        public int xStart() {
            return xStart;
        }

        public int yStart() {
            return yStart;
        }

        public int xEnd() {
            return xEnd;
        }

        public int yEnd() {
            return yEnd;
        }

        public boolean isPresent() {
            return present;
        }
    }

    public boolean hasWall(Direction direction) {
        return walls.get(direction) != null && walls.get(direction).isPresent();
    }

    public void addWall(Direction direction) {
        Optional.ofNullable(walls.get(direction)).ifPresent(wall -> wall.present = true);
    }

    public boolean isWall() {
        return hasWall(Direction.UP) || hasWall(Direction.DOWN) ||
               hasWall(Direction.LEFT) || hasWall(Direction.RIGHT);
    }
    
    
    
    
}
