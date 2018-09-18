package view;

import model.Bank;
import model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * JPanel for managing persons.
 *
 * @author Vasile Lup
 * @see Person
 */
public class PersonPanel extends JPanel {
    private Bank model;

    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable table;
    private List<Person> personList;

    public PersonPanel(Bank bank) {
        this.model = bank;

        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        updatePanel();
    }

    public void addListeners(ActionListener addButtonListener, ActionListener updateButtonListener, ActionListener deleteButtonListener) {
        deleteButton.addActionListener(deleteButtonListener);
        addButton.addActionListener(addButtonListener);
        updateButton.addActionListener(updateButtonListener);
    }

    public Person getPersonOnRow(int rowNumber, boolean withId) {
        try {
            Person person = new Person();

            if (withId) person.setId(Integer.parseInt((String) table.getModel().getValueAt(rowNumber, 0)));
            person.setName((String) table.getModel().getValueAt(rowNumber, 1));
            person.setEmail((String) table.getModel().getValueAt(rowNumber, 3));
            person.setPhone((String) table.getModel().getValueAt(rowNumber, 4));
            person.setAge(Integer.parseInt((String) table.getModel().getValueAt(rowNumber, 2)));

            return person;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Bad integer input", "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    ;

    public void updatePanel() {
        removeAll();

        setLayout(new BorderLayout());
        JPanel p = new JPanel(new FlowLayout());
        p.add(addButton);
        p.add(updateButton);
        p.add(deleteButton);
        add(p, BorderLayout.SOUTH);

        personList = model.getAllPersons();
        table = createTable(personList);

        add(new JScrollPane(table), BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    protected <T> JTable createTable(java.util.List<T> objects) {
        if (objects == null || objects.size() == 0) return new JTable();//empty list

        java.util.List<String> columNames = new ArrayList<>();
        java.util.List<java.util.List<String>> tableContents = new ArrayList<>();

        for (Field field : objects.get(0).getClass().getDeclaredFields()) {
            columNames.add(field.getName());//make the table header
        }

        for (Object obj : objects) {
            java.util.List<String> curr = new ArrayList<>();

            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);//read private fields also
                try {
                    curr.add(field.get(obj).toString());//get field content
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            tableContents.add(curr);
        }

        String[][] tableContentsArr = new String[tableContents.size() + 1][columNames.size()];
        String[] columnNamesArr = new String[columNames.size()];

        for (int i = 0; i < columnNamesArr.length; ++i) columnNamesArr[i] = columNames.get(i);
        for (int i = 0; i < tableContentsArr.length - 1; ++i)
            for (int j = 0; j < tableContentsArr[i].length; ++j)
                tableContentsArr[i][j] = tableContents.get(i).get(j);//transform tableContents to an array

        int i = tableContentsArr.length - 1;
        for (int j = 0; j < tableContentsArr[i].length; ++j)
            tableContentsArr[i][j] = "";
        return new JTable(tableContentsArr, columnNamesArr);//return the JTable
    }

    public JTable getTable() {
        return table;
    }

    public List<Person> getPersonList() {
        return personList;
    }
}
