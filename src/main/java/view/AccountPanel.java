package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * JPanel for managing accounts.
 *
 * @author Vasile Lup
 * @see Account
 */
public class AccountPanel extends JPanel {

    private Bank model;

    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton addInterestButton;
    private JButton withdrawButton;
    private JButton depositButton;
    private JComboBox<Person> personComboBox;
    private JComboBox<String> accountTypeComboBox;
    private JTextField sumTextField;

    private JTable table;
    private List<Account> accountList;

    public AccountPanel(Bank bank) {
        this.model = bank;

        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        addInterestButton = new JButton("Add interest");
        withdrawButton = new JButton("Withdraw");
        depositButton = new JButton("Deposit");
        accountTypeComboBox = new JComboBox<>(new String[]{"Saving", "Spending"});
        sumTextField = new JTextField(10);


        updatePanel();
    }

    public void addListeners(ActionListener addButtonListener, ActionListener updateButtonListener, ActionListener deleteButtonListener, ActionListener depositButtonListener, ActionListener withdrawButtonListener, ActionListener interestButtonListener) {
        deleteButton.addActionListener(deleteButtonListener);
        addButton.addActionListener(addButtonListener);
        updateButton.addActionListener(updateButtonListener);
        depositButton.addActionListener(depositButtonListener);
        withdrawButton.addActionListener(withdrawButtonListener);
        addInterestButton.addActionListener(interestButtonListener);
    }

    public Account getAccountOnRow(int rowNumber, boolean withId) {
        try {
            Account account;

            if (rowNumber >= accountList.size()) {
                if (accountTypeComboBox.getSelectedItem().equals("Saving"))
                    account = new SavingAccount();
                else account = new SpendingAccount();
            } else {
                if (table.getModel().getValueAt(rowNumber, 2).equals("Saving"))
                    account = new SavingAccount();
                else account = new SpendingAccount();
            }

            if (withId) account.setId(Integer.parseInt((String) table.getModel().getValueAt(rowNumber, 0)));
            if (rowNumber >= accountList.size()) account.setBalance(Float.parseFloat(sumTextField.getText()));
            else account.setBalance(Float.parseFloat((String) table.getModel().getValueAt(rowNumber, 1)));

            return account;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Bad input", "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    ;

    public void updatePanel() {
        removeAll();
        addInterestButton.setEnabled(false);
        withdrawButton.setEnabled(false);
        depositButton.setEnabled(false);
        setLayout(new BorderLayout());

        JPanel p = new JPanel(new BorderLayout());

        personComboBox = new JComboBox<>(model.getAllPersons().toArray(new Person[0]));

        JPanel p1 = new JPanel(new FlowLayout());
        p1.add(personComboBox);
        p1.add(accountTypeComboBox);
        p1.add(addButton);
        p1.add(updateButton);
        p1.add(deleteButton);
        p.add(p1, BorderLayout.NORTH);

        JPanel p2 = new JPanel(new FlowLayout());
        p2.add(new JLabel("Sum:"));
        p2.add(sumTextField);
        p2.add(depositButton);
        p2.add(withdrawButton);
        p.add(p2, BorderLayout.CENTER);
        p.add(addInterestButton, BorderLayout.EAST);

        add(p, BorderLayout.SOUTH);
        accountList = model.getAllAccounts();
        table = createTable(accountList);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableClickListener();
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private void tableClickListener() {
        if (table.getSelectedRow() < 0 || table.getSelectedRow() >= accountList.size()) {
            //in the empty last row
            addInterestButton.setEnabled(false);
            withdrawButton.setEnabled(false);
            depositButton.setEnabled(false);
            return;
        }


        if (accountList.get(table.getSelectedRow()) instanceof SavingAccount) {
            //selected saving account
            addInterestButton.setEnabled(true);
            withdrawButton.setEnabled(((SavingAccount) getAccountList().get(table.getSelectedRow())).canWithdrawMoney());
            depositButton.setEnabled(((SavingAccount) getAccountList().get(table.getSelectedRow())).canAddMoney());
        } else {
            //selected spending account
            addInterestButton.setEnabled(false);
            withdrawButton.setEnabled(true);
            depositButton.setEnabled(true);
        }
    }

    protected JTable createTable(List<Account> accounts) {
        if (accounts == null || accounts.size() == 0) return new JTable();//empty list

        String[] columnNamesArr = {"id", "balance", "type", "Person id", "Person name"};
        String[][] tableContentsArr = new String[accounts.size() + 1][columnNamesArr.length];

        for (int i = 0; i < tableContentsArr.length - 1; ++i) {
            Account account = accounts.get(i);
            Person person = model.findPerson(account);

            tableContentsArr[i][0] = String.valueOf(account.getId());
            tableContentsArr[i][1] = String.valueOf(account.getBalance());
            if (account instanceof SavingAccount) tableContentsArr[i][2] = "Saving";
            else tableContentsArr[i][2] = "Spending";

            tableContentsArr[i][3] = String.valueOf(person.getId());
            tableContentsArr[i][4] = person.getName();
        }

        int i = tableContentsArr.length - 1;
        for (int j = 0; j < tableContentsArr[i].length; ++j)
            tableContentsArr[i][j] = "";
        return new JTable(tableContentsArr, columnNamesArr);//return the JTable
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public Person getSelectedPerson() {
        return (Person) personComboBox.getSelectedItem();
    }

    public JTable getTable() {
        return table;
    }

    public JTextField getSumTextField() {
        return sumTextField;
    }
}
