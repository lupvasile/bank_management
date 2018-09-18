import model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BankTest {

    private  Bank bank;
    private  List<Person> persons;
    private  List<SpendingAccount> spendingAccounts;
    private  List<SavingAccount> savingAccounts;
    private  int nrPersons = 5;

    @Before
    public void initialize() {
        bank = new Bank();
        persons = new ArrayList<>();
        spendingAccounts = new ArrayList<>();
        savingAccounts = new ArrayList<>();

        int cnt = 0;

        for(int i = 1; i <= nrPersons; ++i){
            Person p = new Person(i,"Person #"+i,20+i,"mail@mail.com","0712345678");
            persons.add(p);

            SpendingAccount account1 = new SpendingAccount(++cnt);
            SavingAccount account2 = new SavingAccount(++cnt);

            account1.setBalance(200);
            account2.setBalance(300);

            savingAccounts.add(account2);
            spendingAccounts.add(account1);
            }
    }

    @Test
    public void testAddPerson() {
        for (Person p : persons)
            assertEquals(bank.addPerson(p), true);

        for (Person p : persons)
            assertEquals(bank.addPerson(p), false);

        for (Person p : persons)
            assertTrue(bank.getAllPersons().contains(p));
    }

    @Test
    public void testAddAccount(){
        Person p = persons.get(0);
        bank.addPerson(p);

        for(Account a:savingAccounts)
            assertEquals(bank.addAccount(p,a),true);

        for(Account a:savingAccounts)
            assertEquals(bank.addAccount(p,a),false);

        for(Account a:savingAccounts)
            assertTrue(bank.getAllAccounts().contains(a));
    }

    @Test
    public void testRemovePerson(){
        Person p = persons.get(0);
        bank.addPerson(p);
        bank.removePerson(p);
        assertFalse(bank.getAllPersons().contains(p));
    }

    @Test
    public void testRemoveAccount(){
        Person p = persons.get(0);
        Account a = savingAccounts.get(0);

        bank.addPerson(p);

        assertTrue(bank.addAccount(p,a));
        bank.removeAccount(a);
        assertFalse(bank.getAllAccounts().contains(a));
    }

    @Test
    public void updatePerson(){
        Person p1 = persons.get(0);
        Person p2 = persons.get(1);

        bank.updatePerson(p1,p2);
        assertTrue(p1.getPhone().equals(p2.getPhone()));
        assertTrue(p1.getEmail().equals(p2.getEmail()));
        assertEquals(p1.getAge(),p2.getAge());
    }

    @Test
    public void updateAccount(){
        Account a1 = savingAccounts.get(0);
        Account a2 = savingAccounts.get(1);

        bank.updateAccount(a1,a2);
        assertTrue(a1.getBalance()==a2.getBalance());
    }

    @Test
    public void getAllPersons(){
        for(Person p:persons)
            bank.addPerson(p);

        for (Person p:persons)
            assertTrue(bank.getAllPersons().contains(p));
    }

    @Test
    public void getAllAccounts(){
        Person p = persons.get(0);
        bank.addPerson(p);

        for(Account a:savingAccounts)
            bank.addAccount(p,a);

        for(Account a:savingAccounts)
            assertTrue(bank.getAllAccounts().contains(a));
    }

    @Test
    public void findPerson(){
        Person p = persons.get(0);
        Account a = savingAccounts.get(0);

        assertNull(bank.findPerson(a));
        bank.addPerson(p);
        bank.addAccount(p,a);
        assertTrue(p.equals(bank.findPerson(a)));
    }
}
