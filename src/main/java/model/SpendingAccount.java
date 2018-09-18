package model;

/**
 * Class representing a spending account type.
 *
 * @author Vasile Lup
 */
public class SpendingAccount extends Account {

    public SpendingAccount(int id) {
        super(id);
    }

    public SpendingAccount() {
        this(0);
    }

    @Override
    public boolean addMoney(float sum) {
        balance += sum;
        setChanged();//the state of the account has changed
        notifyObservers(balance);//notify all observers about new balance
        return true;
    }

    @Override
    public boolean withdrawMoney(float sum) {
        balance -= sum;
        setChanged();
        notifyObservers(balance);
        return true;
    }
}
