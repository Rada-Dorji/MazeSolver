package controller;

import javax.swing.event.ChangeListener;

/**
 * An abstract ChangeListener class. All ChangeListeners extend this class to gain access to the maze controller.
 */
public abstract class MazeChangeListener implements ChangeListener {
    protected final MazeController mazeController;

    protected MazeChangeListener(final MazeController mazeController) {
        this.mazeController = mazeController;
    }
}
