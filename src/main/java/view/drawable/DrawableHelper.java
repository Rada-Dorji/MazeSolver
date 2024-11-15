package view.drawable;

import java.awt.*;

/**
 * A helper class available to all classes in the view.
 */
class DrawableHelper {
    /**
     * Add a component to a container. This function assumes that the container has a GridBagLayout layout.
     *
     * @param container  The container
     * @param component  The component to add to the container
     * @param gridx      The component's x coordinate in the GridBagLayout
     * @param gridy      The component's y coordinate in the GridBagLayout
     * @param gridwidth  The GridBagLayout grid width
     * @param gridheight The GridBagLayout grid height
     * @param anchor     The component's anchor in the GridBagLayout
     * @param fill       The component's fill in the GridBagLayout
     * @param insets     The component's insets
     */
    public static void addComponent(Container container, Component component, int gridx, int gridy, int gridwidth,
                                     int gridheight, int anchor, int fill, Insets insets) {
        var gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
                1.0, 1.0, anchor, fill, insets, 0, 0);
        container.add(component, gbc);
    }
}
