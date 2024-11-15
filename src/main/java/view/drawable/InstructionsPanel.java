package view.drawable;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;

/**
 * A JPanel of the instructions for the maze at each stage to give the user guidance on what to do and what features
 * are available to them.
 */
public class InstructionsPanel extends JPanel {
    private final JTextArea instructions;

    public InstructionsPanel() {
        setLayout(new BorderLayout());
        var margin = new EmptyBorder(5, 5, 5, 5);  // Use var for margin
        setBorder(new CompoundBorder(new EtchedBorder(), margin));

        this.instructions = new JTextArea();
        initInstructionTextArea();
        add(instructions);

        setVisible(true);
    }

    private void initInstructionTextArea() {
        instructions.setBackground(new Color(238, 238, 238));
        instructions.setEditable(false);
        instructions.setCursor(null);
        instructions.setOpaque(false);
        instructions.setFocusable(false);
        instructions.setWrapStyleWord(true);
        instructions.setLineWrap(true);
    }

    public void setInstructions(String instruction) {
        instructions.setText(instruction);
    }
}
