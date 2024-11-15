package view.drawable;

import controller.listeners.MazeAnimationSpeedSliderListener;
import model.MazeAnimationConstants;

import javax.swing.*;
import java.awt.*;

import static view.drawable.DrawableHelper.addComponent;

/**
 * A JPanel that contains a slider for controlling the animation speed of the maze solver.
 */
class AnimationSliderPanel extends JPanel {
    
    public AnimationSliderPanel(MazeAnimationSpeedSliderListener mazeAnimationSpeedSliderListener) {
        setLayout(new GridBagLayout());

        // Create the animation speed slider
        JSlider mazeAnimationSpeedSlider = new JSlider(JSlider.HORIZONTAL, 
                MazeAnimationConstants.MINIMUM_ANIMATION_SLEEP,
                MazeAnimationConstants.MAXIMUM_ANIMATION_SLEEP, 
                MazeAnimationConstants.DEFAULT_ANIMATION_SLEEP);
        
        // Invert the slider to have faster speeds on the right
        mazeAnimationSpeedSlider.setInverted(true);
        
        // Add a change listener to the slider
        mazeAnimationSpeedSlider.addChangeListener(mazeAnimationSpeedSliderListener);

        // Create a label for the slider
        JLabel mazeAnimationSpeedSliderLabel = new JLabel("Animation Speed");
        mazeAnimationSpeedSliderLabel.setLabelFor(mazeAnimationSpeedSlider); // Associate label with slider

        // Define insets for layout
        Insets insets = new Insets(0, 0, 0, 0);

        // Add label and slider to the panel
        addComponent(this, mazeAnimationSpeedSliderLabel, 0, 0, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets);
        addComponent(this, mazeAnimationSpeedSlider, 0, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets);
    }
}
