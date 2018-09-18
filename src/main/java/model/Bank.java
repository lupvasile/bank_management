package model;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @invariant isWellFormed()
 * @invariant hashTable != null
 * @invariant forall person: hastTable.keys() @hashTable(person).value() != null
 * @invariant forall account: hashTable.values() @exists unique person: hashTable.keys() => hashTable(person).value().contains(account)
 */
public class Bank implements BankProc, Serializable {
    private Map<Person, Set<Account>> hashTable;

    public Bank() {
        this.hashTable = new HashMap<>();
    }

    /**
     * Adds a new person to the bank.
     * @param person the person to be added
     * @return true if the person does not yet exist, false otherwise
     *
     * @pre person != null
     * @post !getAllPersons().contains(person)@pre => getAllPersons().getSize() = getAllPersons().getSize()@pre + 1 && @result==true
     * @post getAllPersons().contains(person)@pre => getAllPersons().getSize() = getAllPersons().getSize()@pre && @result == false
     */
    @Override
    public boolean addPerson(Person person) {
        assert person != null;
        assert isWellFormed();

        boolean containsPre = getAllPersons().contains(person);
        int sizePre = getAllPersons().size();

        boolean result;

        if (hashTable.containsKey(person)) {
            result = false;
        } else {
            hashTable.put(person, new HashSet<>());
            result = true;
        }

        if(!containsPre) {
            assert getAllPersons().size() == sizePre + 1;
            assert result == true;
        }
        else{
            assert getAllPersons().size() == sizePre;
            assert result == false;
        }
        assert isWellFormed();

        return result;
    }

    /**
     * Remove a person from the bank.
     * @param person the person to be removed
     *
     * @pre person != null
     * @post getAllPersons().contains(person)@pre => getAllPersons().getSize() = getAllPersons().getSize()@pre - 1
     * @post !getAllPersons().contains(person)@pre => getAllPersons().getSize() = getAllPersons().getSize()@pre
     */
    @Override
    public void removePerson(Person person) {
        assert person != null;
        assert isWellFormed();

        boolean containsPre = getAllPersons().contains(person);
        int sizePre = getAllPersons().size();

        hashTable.remove(person);

        if(containsPre) assert getAllPersons().size() == sizePre - 1;
        else assert getAllPersons().size() == sizePre;
        assert isWellFormed();
    }

    /**
     * Updates fields of and existingPerson to fields of a new person.
     * Should not update id and name.
     *
     * @param existingPerson the existing person
     * @param newPersonDetails person object from which new details are taken
     *
     * @pre existingPerson != null
     * @pre newPersonDetails != null
     * @post existingPerson.getName() == existingPerson.getName()@pre
     * @post existingPerson.getId() == existingPerson.getName()@pre
     * @post existingPerson.getAge() == newPersonDetails.getAge()
     * @post existingPerson.getEmail() == newPersonDetails.getEmail()
     * @post existingPerson.getPhone() == newPersonDetails.getPhone()
     */
    @Override
    public void updatePerson(Person existingPerson, Person newPersonDetails) {
        assert existingPerson != null;
        assert newPersonDetails != null;
        assert isWellFormed();

        String namePre = existingPerson.getName(); int idPre = existingPerson.getId();

        existingPerson.setAge(newPersonDetails.getAge());
        existingPerson.setEmail(newPersonDetails.getEmail());
        existingPerson.setPhone(newPersonDetails.getPhone());

        assert existingPerson.getId() == idPre;
        assert existingPerson.getName().equals(namePre);
        assert existingPerson.getAge() == newPersonDetails.getAge();
        assert existingPerson.getPhone().equals(newPersonDetails.getPhone());
        assert existingPerson.getEmail().equals(newPersonDetails.getEmail());
        assert isWellFormed();
    }

    /**
     * Returns all the persons in the bank.
     * @return the list of all persons
     *
     * @pre true
     * @post @nochange
     */
    @Override
    public List<Person> getAllPersons() {
        assert isWellFormed();
        return hashTable.keySet().stream().sorted(Comparator.comparing(Person::getName)).collect(Collectors.toList());
    }

    /**
     * Adds a new account to the bank.
     * @param person the person which is owner of the account
     * @param account the account to be added
     * @return true if the person does exist and account does not exist in bank, false otherwise
     *
     * @pre person != null
     * @pre account != null
     * @post !getAllAccounts().contains(account)@pre && getAllPersons().contains(person) => getAllAccounts().getSize() = getAllAccounts().getSize()@pre + 1 && @result==true && findPerson(account) == person
     * @post getAllAccounts().contains(account)@pre || !getAllPersons().contains(person)=> getAllAccounts().getSize() = getAllAccounts().getSize()@pre && @result == false
     */
    @Override
    public boolean addAccount(Person person, Account account) {
        assert person != null;
        assert account != null;
        assert isWellFormed();

        boolean containsPreAccount = getAllAccounts().contains(account);
        boolean containsPrePerson = getAllPersons().contains(person);
        int sizePre = getAllAccounts().size();

        boolean result;

        if(getAllAccounts().contains(account)){
            result = false;
        }

        else if (!hashTable.containsKey(person)){
            result = false;
        }
        else {
            hashTable.get(person).add(account);
            account.addObserver(person);

            result = true;
        }

        if(!containsPreAccount && containsPrePerson){
            assert getAllAccounts().size() == sizePre + 1;
            assert findPerson(account).equals(person);
            assert result == true;
        }
        else {
            assert getAllAccounts().size() == sizePre;
            assert result == false;
        }
        assert isWellFormed();

        return result;
    }

    /**
     * Remove an account from the bank.
     * @param account the account to be removed
     *
     * @pre account != null
     * @post getAllAccounts().contains(account)@pre => getAllAccounts().getSize() = getAllAccounts().getSize()@pre - 1
     * @post !getAllAccounts().contains(account)@pre => getAllAccounts().getSize() = getAllAccounts().getSize()@pre
     */
    @Override
    public void removeAccount(Account account) {
        assert account != null;
        assert isWellFormed();

        boolean containsPre = getAllAccounts().contains(account);
        int sizePre = getAllAccounts().size();

        Person person = findPerson(account);
        if (person == null) return;

        hashTable.get(person).remove(account);
        account.deleteObserver(person);

        if(containsPre) assert getAllAccounts().size() == sizePre - 1;
        else assert getAllAccounts().size() == sizePre;
        assert isWellFormed();
    }

    /**
     * Updates fields of and existingAccount to fields of a new account.
     * Should not update id.
     *
     * @param existingAccount the existing account
     * @param newAccountDetails account object from which new details are taken
     *
     * @pre existingAccount != null
     * @pre newAccountDetails != null
     * @post existingAccount.getId() == existingAccount.getId()@pre
     * @post existingAccount.getBalance() == newAccountDetails.getBalance()
     */
    @Override
    public void updateAccount(Account existingAccount, Account newAccountDetails) {
        assert existingAccount != null;
        assert newAccountDetails != null;

        int idPre = existingAccount.getId();

        existingAccount.setBalance(newAccountDetails.getBalance());

        assert existingAccount.getId() == idPre;
        assert existingAccount.getBalance() == newAccountDetails.getBalance();
    }

    /**
     * Returns all the accounts in the bank.
     *
     * @return the list of all accounts
     * @pre true
     * @post @nochange
     */
    @Override
    public List<Account> getAllAccounts() {
        assert isWellFormed();
        return hashTable.values().stream().flatMap(Set::stream).collect(Collectors.toList());
    }

    /**
     * Search for the owner of the account.
     * @param account the account whose owner is searched
     * @return the owner of the account if the account exists, null otherwise
     *
     * @pre account != null
     * @post @nochange
     */
    @Override
    public Person findPerson(Account account) {
        assert account != null;
        assert isWellFormed();

        for (Map.Entry<Person, Set<Account>> e : hashTable.entrySet())
            for (Account currAccount : e.getValue())
                if (currAccount.equals(account)) {
                    assert isWellFormed();//trebe aici?
                    return e.getKey();
                }

        return null;
    }

    public void updateObserverLists() {
        for (Map.Entry<Person, Set<Account>> e : hashTable.entrySet())
            for (Account currAccount : e.getValue()) {
                currAccount.deleteObservers();
                currAccount.addObserver(e.getKey());
            }
    }

    private boolean isWellFormed() {
        if (hashTable == null) return false;

        for (Map.Entry<Person, Set<Account>> e : hashTable.entrySet())
            if (e.getValue() == null) return false;//no person with null account list

        for (Map.Entry<Person, Set<Account>> e : hashTable.entrySet())
            for (Account currAccount : e.getValue()) {

                for (Map.Entry<Person, Set<Account>> e2 : hashTable.entrySet()) {
                    if (e2.getKey().equals(e.getKey())) continue;
                    for (Account dupAccount : e2.getValue()) {
                        if (currAccount.equals(dupAccount)) return false;//no account appears twice
                    }
                }
            }

        return true;
    }
}
