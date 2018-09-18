package controller;

import model.Account;
import model.Bank;
import model.Person;
import model.SavingAccount;
import view.AccountPanel;
import view.MainFrame;
import view.PersonPanel;

import javax.swing.*;
import java.util.List;

/**
 * Controller providing listeners for the MainFrame.
 *
 * @author Vasile Lup
 * @see MainFrame
 */
public class MainController {
    private MainFrame view;
    private Bank model;

    public MainController(MainFrame view, Bank model) {
        this.view = view;
        this.model = model;

        view.addPersonListeners(x -> addPersonListener(), x -> updatePersonListener(), x -> deletePersonListener());
        view.addAccountListeners(x -> addAccountListener(), x -> updateAccountListener(), x -> deleteAccountListener(), x -> depositListener(), x -> withdrawListener(), x -> interestListener());
        view.setVisible(true);
    }

    private void addPersonListener() {
        PersonPanel panel = view.getPersonPanel();
        Person newPerson = panel.getPersonOnRow(panel.getPersonList().size(), false);
        if (newPerson == null) return;

        newPerson.setId(panel.getPersonList().stream().mapToInt(Person::getId).max().getAsInt() + 1);
        model.addPerson(newPerson);
        panel.updatePanel();
        view.getAccountPanel().updatePanel();
    }

    private void updatePersonListener() {
        List<Person> pers = view.getPersonPanel().getPersonList();
        int row = 0;
        for (Person oldPerson : pers) {
            Person newPerson = view.getPersonPanel().getPersonOnRow(row, true);
            ++row;

            if (newPerson == null) continue;
            oldPerson.setPhone(newPerson.getPhone());
            oldPerson.setEmail(newPerson.getEmail());
            oldPerson.setAge(newPerson.getAge());
        }

        view.getPersonPanel().updatePanel();
        view.getAccountPanel().updatePanel();
    }

    private void deletePersonListener() {
        JTable table = view.getPersonPanel().getTable();
        List<Person> personList = view.getPersonPanel().getPersonList();

        if (table.getSelectedRow() < 0 || table.getSelectedRow() >= personList.size()) return;
        model.removePerson(personList.get(table.getSelectedRow()));
        view.getPersonPanel().updatePanel();
        view.getAccountPanel().updatePanel();
    }

    private void addAccountListener() {
        AccountPanel panel = view.getAccountPanel();
        Account newAccount = panel.getAccountOnRow(panel.getAccountList().size(), false);
        if (newAccount == null) return;

        newAccount.setId(panel.getAccountList().stream().mapToInt(Account::getId).max().getAsInt() + 1);
        model.addAccount(panel.getSelectedPerson(), newAccount);
        panel.updatePanel();
    }

    private void updateAccountListener() {
        List<Account> accounts = view.getAccountPanel().getAccountList();
        int row = 0;
        for (Account oldAccount : accounts) {
            Account newAccount = view.getAccountPanel().getAccountOnRow(row, true);
            ++row;

            if (newAccount == null) continue;
            if (oldAccount.getBalance() != newAccount.getBalance())
                oldAccount.setBalance(newAccount.getBalance());
        }

        view.getAccountPanel().updatePanel();
    }

    private void deleteAccountListener() {
        JTable table = view.getAccountPanel().getTable();
        List<Account> accountList = view.getAccountPanel().getAccountList();

        if (table.getSelectedRow() < 0 || table.getSelectedRow() >= accountList.size()) return;
        model.removeAccount(accountList.get(table.getSelectedRow()));
        view.getAccountPanel().updatePanel();
    }

    private void depositListener() {
        AccountPanel panel = view.getAccountPanel();
        Account account = panel.getAccountList().get(panel.getTable().getSelectedRow());
        String sumText = panel.getSumTextField().getText();

        try {
            float sum = Float.parseFloat(sumText);
            account.addMoney(sum);
            panel.updatePanel();
        } catch (NumberFormatException e) {
            view.showError("Bad sum");
        }
    }

    private void withdrawListener() {
        AccountPanel panel = view.getAccountPanel();
        Account account = panel.getAccountList().get(panel.getTable().getSelectedRow());
        String sumText = panel.getSumTextField().getText();

        try {
            float sum = Float.parseFloat(sumText);
            account.withdrawMoney(sum);
            panel.updatePanel();
        } catch (NumberFormatException e) {
            view.showError("Bad sum");
        }
    }

    private void interestListener() {
        try {
            AccountPanel panel = view.getAccountPanel();
            SavingAccount account = (SavingAccount) panel.getAccountList().get(panel.getTable().getSelectedRow());
            account.addInterest();
            panel.updatePanel();
        } catch (Exception e) {
            view.showError("not a savings account");
        }
    }


}
