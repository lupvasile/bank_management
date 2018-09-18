package model;

import java.io.Serializable;
import java.util.Observable;

/**
 * Class representing an account.
 *
 * @author Vasile Lup
 */
public abstract class Account extends Observable implements Serializable {
    protected int id;
    protected float balance;

    public Account(int id, float balance) {
        this.id = id;
        this.balance = balance;
    }

    public Account(int id) {
        this(id, 0);
    }

    public Account() {
        this(0, 0);
    }


    public float getBalance() {
        return balance;
    }

    /**
     * poate forta din interfata
     *
     * @param balance the new balance
     */
    public void setBalance(float balance) {
        this.balance = balance;
        setChanged();
        notifyObservers(balance);
    }

    public abstract boolean addMoney(float sum);

    public abstract boolean withdrawMoney(float sum);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Account)) return false;

        Account account = (Account) o;

        return id == account.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Account #" + id;
    }
}
