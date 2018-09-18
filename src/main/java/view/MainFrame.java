package view;

import model.Bank;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Main JFrame, containing the buttons used to open person and account windows.
 *
 * @author Vasile Lup
 * @see AccountPanel
 * @see PersonPanel
 */
public class MainFrame extends JFrame {
    private JButton personButton;
    private JButton accountButton;

    private PersonPanel personPanel;
    private AccountPanel accountPanel;
    private JFrame personFrame, accountFrame;

    public MainFrame(Bank model) {
        increaseUIFont(3);
        UIManager.put("Table.font", new Font("Dialog", Font.PLAIN, 13));

        setTitle("Bank");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(400, 300);
        //setMinimumSize(new Dimension(600, 500));

        personButton = new JButton("Manage persons");
        accountButton = new JButton("Manage accounts");

        setLayout(new FlowLayout());
        add(personButton);
        add(accountButton);

        personPanel = new PersonPanel(model);
        accountPanel = new AccountPanel(model);

        personFrame = new FramePanel(personPanel, "Person manager", 200, 400, true);
        accountFrame = new FramePanel(accountPanel, "Account manager", 700, 400, true);

        addButtonListeners();

        pack();
    }

    /**
     * Increases the font for all the UI components with the given parameter.
     *
     * @param increase the size with which all the component font size is increased
     */
    public static void increaseUIFont(int increase) {
        java.util.Enumeration keys = UIManager.getDefaults().keys();//get all standard font keys
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();//get the key ex: Table.font
            Object value = UIManager.get(key);//get current font for key
            if (value instanceof javax.swing.plaf.FontUIResource)//check if key is a font resource
            {
                //increase the font
                UIManager.put(key, new javax.swing.plaf.FontUIResource(((FontUIResource) value).getFontName(), Font.PLAIN, ((FontUIResource) value).getSize() + increase));
            }
        }
    }

    private void addButtonListeners() {
        personButton.addActionListener(x -> personFrame.setVisible(true));
        accountButton.addActionListener(x -> accountFrame.setVisible(true));
    }

    public void addPersonListeners(ActionListener addButtonListener, ActionListener updateButtonListener, ActionListener deleteButtonListener) {
        personPanel.addListeners(addButtonListener, updateButtonListener, deleteButtonListener);
    }

    public void addAccountListeners(ActionListener addButtonListener, ActionListener updateButtonListener, ActionListener deleteButtonListener, ActionListener depositButtonListener, ActionListener withdrawButtonListener, ActionListener interestButtonListener) {
        accountPanel.addListeners(addButtonListener, updateButtonListener, deleteButtonListener, depositButtonListener, withdrawButtonListener, interestButtonListener);
    }

    public void showError(String errorMsg) {
        JOptionPane.showMessageDialog(this, errorMsg, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    public PersonPanel getPersonPanel() {
        return personPanel;
    }

    public AccountPanel getAccountPanel() {
        return accountPanel;
    }
}
