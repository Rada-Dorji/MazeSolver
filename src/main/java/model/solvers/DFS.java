package model.solvers;

import controller.MazeController;
import model.Cell;
import model.Cell.CellVisitState;
import model.Direction;
import model.Maze;
import model.MazeSolverWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CancellationException;
import java.util.EmptyStackException;

class CustomStack<T> {
    private Node<T> head;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    public CustomStack() {
        head = null;
        size = 0;
    }

    public void push(T item) {
        Node<T> newNode = new Node<>(item);
        newNode.next = head;
        head = newNode;
        size++;
    }

    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T data = head.data;
        head = head.next;
        size--;
        return data;
    }

    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return head.data;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}

public class DFS extends MazeSolverWorker {
    private int cellsChecked = 0;  // Track the number of checked cells
    private int pathCells = 0;     // Track the number of cells in the solution path

    public DFS(Maze maze, MazeController mazeController) {
        super(maze, mazeController);
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        CustomStack<Cell> searchStack = new CustomStack<>();
        Cell root = maze.getStartingCell();
        Cell end = maze.getEndingCell();

        searchStack.push(root);
        root.setVisitState(CellVisitState.VISITING);

        while (!searchStack.isEmpty()) {
            Cell current = searchStack.peek();
            cellsChecked++;  // Increment cells checked count
            current.setCurrent(true);

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

            Cell unvisitedNeighbor = findUnvisitedNeighbor(current);

            if (unvisitedNeighbor != null) { // There are unvisited neighboring cells to visit
                searchStack.push(unvisitedNeighbor);
                unvisitedNeighbor.setVisitState(CellVisitState.VISITING);
                unvisitedNeighbor.setParent(current);
            } else {
                // Backtrack since there are no unvisited neighboring cells
                current.setVisitState(CellVisitState.VISITED);
                searchStack.pop();
            }

            publish(maze); // Publish the maze state for repainting
            mazeController.updateCellsChecked(cellsChecked); // Update cells checked on GUI
            mazeController.updatePathCells(pathCells);       // Update path cells on GUI if path is found

            Thread.sleep(mazeController.getAnimationSpeed());
            current.setCurrent(false);
        }

        return false; // No path found
    }

    private Cell findUnvisitedNeighbor(Cell current) {
        List<Cell> unvisitedNeighbors = new ArrayList<>();
        int currRow = current.row();
        int currCol = current.col();
        Random rand = new Random();

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

        if (unvisitedNeighbors.isEmpty()) {
            return null; // No unvisited neighbors found
        }

        return unvisitedNeighbors.get(rand.nextInt(unvisitedNeighbors.size())); // Randomly select one unvisited neighbor
    }

    @Override
    protected void process(List<Maze> chunks) {
        for (Maze maze : chunks) {
            mazeController.repaintMaze(maze);  // Trigger maze repaint
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
}
