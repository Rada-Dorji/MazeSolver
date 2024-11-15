package model.solvers;

import controller.MazeController;
import model.Cell;
import model.Cell.CellVisitState;
import model.Direction;
import model.Maze;
import model.MazeSolverWorker;

import java.util.concurrent.CancellationException;
import java.util.NoSuchElementException;

class CustomList<T> {
    private Node<T> head;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    public CustomList() {
        head = null;
        size = 0;
    }

    public void add(T item) {
        Node<T> newNode = new Node<>(item);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> current = head;
        if (index == 0) {
            head = head.next;
        } else {
            Node<T> prev = null;
            for (int i = 0; i < index; i++) {
                prev = current;
                current = current.next;
            }
            if (prev != null) {
                prev.next = current.next;
            }
        }
        size--;
        return current.data;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void remove(T item) {
        Node<T> current = head;
        Node<T> prev = null;
        while (current != null) {
            if (current.data.equals(item)) {
                if (prev == null) {
                    head = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return;
            }
            prev = current;
            current = current.next;
        }
        throw new NoSuchElementException("Item not found");
    }
}

public class AStar extends MazeSolverWorker {
    private int cellsChecked = 0;  // Track the number of checked cells
    private int pathCells = 0;     // Track the number of cells in the solution path

    public AStar(Maze maze, MazeController mazeController) {
        super(maze, mazeController);
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        CustomList<Cell> openSet = new CustomList<>();
        Cell start, end, current;
        int tentativeG;

        start = maze.getStartingCell();
        end = maze.getEndingCell();

        start.setGCost(0);
        start.setHCost(manhattan_distance(start, end));
        start.setFCost(start.getGCost() + start.getHCost());
        openSet.add(start);

        while (!openSet.isEmpty()) {
            current = lowestFScoreCell(openSet);
            cellsChecked++;  // Increment cells checked count
            current.setCurrent(true);
            current.setVisitState(CellVisitState.VISITED);

            if (current == end) { // Check if the current cell is the goal cell (i.e., maze has been solved)
                maze.setGoal(current);
                // Trace back from the end to count path cells
                while (current != null) {
                    pathCells++;
                    current = current.parent();
                }
                return true;
            }

            CustomList<Cell> unvisitedNeighbors = unvisitedNeighbors(current);

            for (int i = 0; i < unvisitedNeighbors.size(); i++) {
                Cell neighbor = unvisitedNeighbors.get(i);
                openSet.add(neighbor);
                neighbor.setVisitState(CellVisitState.VISITING);
                neighbor.setParent(current);

                tentativeG = current.getGCost() + 1;

                // If the cost to the neighbor via this path is greater than a previously traversed path, skip
                if (tentativeG >= neighbor.getGCost()) {
                    continue;
                }

                neighbor.setGCost(tentativeG);
                neighbor.setHCost(manhattan_distance(neighbor, end));
                neighbor.setFCost(neighbor.getGCost() + neighbor.getHCost());
            }

            publish(maze);  // Publish the current maze state to be repainted on the event dispatch thread
            mazeController.updateCellsChecked(cellsChecked);  // Update cells checked on GUI
            mazeController.updatePathCells(pathCells);        // Update path cells on GUI if path is found

            Thread.sleep(mazeController.getAnimationSpeed());

            current.setCurrent(false);
        }

        return false;
    }

    @Override
    protected void process(java.util.List<Maze> chunks) {
        for (Maze maze : chunks) {
            mazeController.repaintMaze(maze);  // Trigger maze repaint
        }
    }

    @Override
    protected void done() {
        try {
            if (get()) {
                mazeController.solveMazeSuccess();
                mazeController.updatePathCells(pathCells);  // Final update for path cells
            } else {
                mazeController.reset();
            }
        } catch (CancellationException ignore) {
        } catch (Exception e) {
            mazeController.reset();
        }
    }

    private CustomList<Cell> unvisitedNeighbors(Cell current) {
        CustomList<Cell> unvisitedNeighbors = new CustomList<>();
        int currRow = current.row();
        int currCol = current.col();

        for (Direction direction : Direction.values()) {
            int newRow = currRow + direction.dy;
            int newCol = currCol + direction.dx;

            if (!maze.inBounds(newRow, newCol)) {
                continue;
            }

            Cell neighbor = maze.mazeCell(newRow, newCol);

            if (current.wallMissing(direction) && neighbor.unvisited()) {
                unvisitedNeighbors.add(neighbor);
            }
        }

        return unvisitedNeighbors;
    }

    private Cell lowestFScoreCell(CustomList<Cell> openSet) {
        Cell lowestFScoreCell = openSet.get(0);
        int lowestFScore = lowestFScoreCell.getFCost();

        for (int i = 1; i < openSet.size(); i++) {
            Cell cell = openSet.get(i);
            int cellFScore = cell.getFCost();

            if (cellFScore < lowestFScore) {
                lowestFScoreCell = cell;
                lowestFScore = cellFScore;
            }
        }

        openSet.remove(lowestFScoreCell);
        return lowestFScoreCell;
    }

    private int manhattan_distance(Cell cell, Cell end) {
        return Math.abs(cell.row() - end.row()) + Math.abs(cell.col() - end.col());
    }
}
