package model.solvers;

import controller.MazeController;
import model.Cell;
import model.Cell.CellVisitState;
import model.Direction;
import model.Maze;
import model.MazeSolverWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;

import java.util.NoSuchElementException;

class CustomQueue<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    public CustomQueue() {
        head = null;
        tail = null;
        size = 0;
    }

    // Adds an item to the end of the queue
    public void add(T item) {
        Node<T> newNode = new Node<>(item);
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        if (head == null) {
            head = newNode;
        }
        size++;
    }

    // Removes an item from the front of the queue
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        T data = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return data;
    }

    // Checks if the queue is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Returns the size of the queue
    public int size() {
        return size;
    }
}

public class BFS extends MazeSolverWorker {
    private int cellsChecked = 0; // Track the number of checked cells
    private int pathCells = 0;    // Track the number of cells in the solution path

    public BFS(Maze maze, MazeController mazeController) {
        super(maze, mazeController);
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        Cell current;
        Cell end = maze.getEndingCell();
        CustomQueue<Cell> searchQueue = new CustomQueue<>();
        searchQueue.add(maze.getStartingCell());

        while (!searchQueue.isEmpty()) {
            current = searchQueue.remove();
            cellsChecked++;  // Increment cells checked count
            current.setCurrent(true);
            current.setVisitState(CellVisitState.VISITED);

            // Check if the current cell is the goal cell
            if (current.equals(end)) { 
                maze.setGoal(current);
                // Trace back from the end to count path cells
                while (current != null) {
                    pathCells++;
                    current = current.parent();
                }
                return true;
            }

            List<Cell> unvisitedNeighbors = unvisitedNeighbors(current);
            enqueueUnvisitedNeighbors(searchQueue, unvisitedNeighbors, current);

            publish(maze);  // Publish the maze state to repaint the GUI
            mazeController.updateCellsChecked(cellsChecked);  // Update cells checked on GUI
            mazeController.updatePathCells(pathCells);        // Update path cells on GUI if path is found

            Thread.sleep(mazeController.getAnimationSpeed());
            current.setCurrent(false);
        }

        return false; // No path found
    }

    private void enqueueUnvisitedNeighbors(CustomQueue<Cell> searchQueue, List<Cell> unvisitedNeighbors, Cell current) {
        for (Cell neighbor : unvisitedNeighbors) {
            searchQueue.add(neighbor);
            neighbor.setVisitState(CellVisitState.VISITING);
            neighbor.setParent(current);
        }
    }

    @Override
    protected void process(List<Maze> chunks) {
        for (Maze maze : chunks) {
            mazeController.repaintMaze(maze);
        }
    }

    @Override
    protected void done() {
        try {
            if (get()) { // If maze-solving was successful
                mazeController.solveMazeSuccess();
                mazeController.updatePathCells(pathCells);  // Final update for path cells
            } else {
                mazeController.reset();
            }
        } catch (CancellationException ignore) {
            // Task was cancelled, no action needed
        } catch (Exception e) {
            mazeController.reset(); // Handle other exceptions
        }
    }

    private List<Cell> unvisitedNeighbors(Cell current) {
        List<Cell> unvisitedNeighbors = new ArrayList<>();
        int currRow = current.row();
        int currCol = current.col();

        for (Direction direction : Direction.values()) {
            int newRow = currRow + direction.dy;
            int newCol = currCol + direction.dx;

            if (!maze.inBounds(newRow, newCol)) {
                continue; // Skip out-of-bounds cells
            }

            Cell neighbor = maze.mazeCell(newRow, newCol);
            if (current.wallMissing(direction) && neighbor.unvisited()) {
                unvisitedNeighbors.add(neighbor);
            }
        }

        return unvisitedNeighbors;
    }
}

