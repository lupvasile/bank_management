package model;

/**
 * Class representing a Saving account type.
 *
 * @author Vasile Lup
 */
public class SavingAccount extends Account {
    private boolean firstAddMoney;
    private boolean firstWithdrawMoney;

    public SavingAccount(int id) {
        super(id);
        firstAddMoney = true;
        firstWithdrawMoney = true;
    }

    public SavingAccount() {
        this(0);
    }

    @Override
    public boolean addMoney(float sum) {
        if (firstAddMoney == false) return false;

        balance += sum;
        firstAddMoney = false;
        setChanged();
        notifyObservers(balance);
        return true;
    }

    @Override
    public boolean withdrawMoney(float sum) {
        if (firstWithdrawMoney == false) return false;

        balance -= sum;
        firstWithdrawMoney = false;
        setChanged();
        notifyObservers(balance);
        return true;
    }

    public boolean canAddMoney() {
        return firstAddMoney;
    }

    public boolean canWithdrawMoney() {
        return firstWithdrawMoney;
    }

    public void addInterest() {
        balance += balance * 0.02;
        setChanged();
        notifyObservers(balance);
    }
}
