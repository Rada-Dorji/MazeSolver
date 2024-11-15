package model.generators;

import controller.MazeController;
import model.Cell;
import model.Cell.CellVisitState;
import model.Direction;
import model.Maze;
import model.MazeGeneratorWorker;
import java.util.EmptyStackException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CancellationException;

/**
 * A SwingWorker class (extending MazeGeneratorWorker) that implements the recursive backtracker maze generation
 * algorithm. This algorithm is a DFS implementation which randomly picks an unvisited neighboring cell at each
 * iteration to visit to construct the maze.
 * <p>
 * https://en.wikipedia.org/wiki/Maze_generation_algorithm#Recursive_backtracker
 */

 class CustomList<T> {
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

    public CustomList() {
        head = null;
        tail = null;
        size = 0;
    }

    // Adds an item to the end of the list
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

    // Gets the item at the given index
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    // Checks if the list is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Returns the size of the list
    public int size() {
        return size;
    }

    // Removes the item at the end of the list and returns it
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }

        Node<T> current = head;
        if (size == 1) {
            T data = head.data;
            head = null;
            tail = null;
            size--;
            return data;
        }

        while (current.next != tail) {
            current = current.next;
        }

        T data = tail.data;
        tail = current;
        tail.next = null;
        size--;
        return data;
    }
}

class CustomStack<T> {
    private final CustomList<T> elements;

    public CustomStack() {
        elements = new CustomList<>();
    }

    // Pushes an item onto the stack
    public void push(T item) {
        elements.add(item);
    }

    // Pops the item from the top of the stack and returns it
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return elements.removeLast();
    }

    // Peeks at the top item of the stack without removing it
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return elements.get(elements.size() - 1);
    }

    // Checks if the stack is empty
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    // Returns the size of the stack
    public int size() {
        return elements.size();
    }
}

public class RecursiveBacktracker extends MazeGeneratorWorker {
    private final Random rand = new Random();  // Class-level Random instance

    public RecursiveBacktracker(Maze maze, MazeController mazeController) {
        super(maze, mazeController);
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        CustomStack<Cell> searchStack = new CustomStack<>();  // Using CustomStack instead of ArrayDeque
        Cell root, current, unvisitedNeighbor;

        int startRow = rand.nextInt(maze.numRows());
        int startCol = rand.nextInt(maze.numCols());

        root = maze.mazeCell(startRow, startCol);
        searchStack.push(root);
        root.setVisitState(CellVisitState.VISITING);

        while (!searchStack.isEmpty()) {
            current = searchStack.peek();
            current.setCurrent(true);
            unvisitedNeighbor = getUnvisitedNeighbor(current);  // Renamed method

            if (unvisitedNeighbor != null) {
                searchStack.push(unvisitedNeighbor);
                unvisitedNeighbor.setVisitState(CellVisitState.VISITING);
                removeWalls(current, unvisitedNeighbor);
            } else {
                current.setVisitState(CellVisitState.VISITED);
                searchStack.pop();
            }

            publish(maze);
            Thread.sleep(mazeController.getAnimationSpeed());
            current.setCurrent(false);
        }

        maze.voidVisits();
        maze.saveWallStructure();
        return true;
    }

   @Override
    protected void process(List<Maze> chunks) {  // Use List<Maze> instead of CustomList<Maze>
        for (Maze maze : chunks) {
            mazeController.repaintMaze(maze);
        }
    }


    @Override
    protected void done() {
        try {
            if (get()) {
                mazeController.generateMazeSuccess();
            } else {
                mazeController.reset();
            }
        } catch (CancellationException ignore) {
        } catch (Exception e) {
            e.printStackTrace(); // Log exception for debugging
            mazeController.reset();
        }
    }

    private Cell getUnvisitedNeighbor(Cell current) {  // Renamed method
        CustomList<Cell> unvisitedNeighbors = new CustomList<>();  // Replacing ArrayList with CustomList
        int currRow = current.row();
        int currCol = current.col();

        for (Direction dir : Direction.values()) {
            int newRow = currRow + dir.dy;
            int newCol = currCol + dir.dx;

            if (maze.inBounds(newRow, newCol)) {
                Cell neighbor = maze.mazeCell(newRow, newCol);
                if (neighbor.unvisited()) {
                    unvisitedNeighbors.add(neighbor);
                }
            }
        }

        return unvisitedNeighbors.isEmpty() ? null : unvisitedNeighbors.get(rand.nextInt(unvisitedNeighbors.size()));
    }

    private void removeWalls(Cell current, Cell neighbor) {
        // Use Optional to handle potential absence of direction
        Optional<Direction> directionOpt = current.directionToCell(neighbor);
        directionOpt.ifPresent(direction -> {
            current.removeWall(direction);
            neighbor.removeWall(direction.oppositeDirection());
        });
    }
}
