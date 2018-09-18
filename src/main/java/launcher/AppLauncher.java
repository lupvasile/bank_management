package launcher;

import controller.MainController;
import model.*;
import view.MainFrame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Class containing the main method of the app.
 *
 * @author Vasile Lup
 * @see MainFrame
 * @see MainController
 * @see Bank
 */
public class AppLauncher {

    private static Bank model;

    /**
     * Entry point in the app.
     * Creates a view and controller and links them together.
     */
    public static void main(String[] args) {
        loadApp();
        MainFrame view = new MainFrame(model);
        MainController controller = new MainController(view, model);

        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosed(e);
                saveApp();//write the model in a file
            }
        });
    }

    private static void populateModel(Bank bank) {
        int nrPersons = 5;
        int nrAccounts = 1;
        int cnt = 0;

        for (int i = 1; i <= nrPersons; ++i) {
            Person p = new Person(i, "Person #" + i, 20 + i, "mail@mail.com", "0712345678");
            bank.addPerson(p);

            for (int j = 1; j <= nrAccounts; ++j) {
                Account account1 = new SpendingAccount(++cnt);
                Account account2 = new SavingAccount(++cnt);

                account1.setBalance(200);
                account2.setBalance(300);

                bank.addAccount(p, account1);
                bank.addAccount(p, account2);
            }

        }
    }

    /**
     * Checks if there is an existing savefile and loads the
     * model with existing data from the savefile.
     * If there is no savefile, it initializes the model to
     * a predefined state.
     */
    private static void loadApp() {
        try {

            ObjectInputStream in = new ObjectInputStream(new FileInputStream("bankSave.dat"));
            model = (Bank) in.readObject();
            model.updateObserverLists();
            System.out.println("Reading object");
            in.close();
        } catch (Exception e) {
            System.out.println("Making new object...");
            model = new Bank();
            populateModel(model);
        }
    }

    /**
     * Saves the curent configuration of the model
     * in a savefile so it can be read again when the app is restarting.
     */
    private static void saveApp() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("bankSave.dat"));
            out.writeObject(model);
            System.out.println("Writing object");
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

