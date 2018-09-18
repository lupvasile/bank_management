package view;

import javax.swing.*;

/**
 * Helper class used to wrap a JPanel in a new JFrame.
 * It makes the JFrame automatically not visible.
 *
 * @author Vasile Lup
 */
public class FramePanel extends JFrame {

    private JPanel panel;

    public FramePanel(JPanel panel, String name, int locX, int locY, boolean visibility) {
        this.panel = panel;
        setTitle(name);
        setContentPane(panel);
        setVisible(visibility);
        setLocation(locX, locY);
        pack();
    }

    public FramePanel(JPanel panel, String name) {
        this(panel, name, 400, 400, false);
    }

    public JPanel getPanel() {
        return panel;
    }
}