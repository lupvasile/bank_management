package model;

import java.util.List;

/**
 * Interface class for a bank.
 *
 * @author Vasile Lup
 */
public interface BankProc {

    /**
     * Adds a new person to the bank.
     *
     * @param person the person to be added
     * @return true if the person does not yet exist, false otherwise
     * @pre person != null
     * @post !getAllPersons().contains(person)@pre => getAllPersons().getSize() = getAllPersons().getSize()@pre + 1 && @result==true
     * @post getAllPersons().contains(person)@pre => getAllPersons().getSize() = getAllPersons().getSize()@pre && @result == false
     */
    boolean addPerson(Person person);

    /**
     * Remove a person from the bank.
     *
     * @param person the person to be removed
     * @pre person != null
     * @post getAllPersons().contains(person)@pre => getAllPersons().getSize() = getAllPersons().getSize()@pre - 1
     * @post !getAllPersons().contains(person)@pre => getAllPersons().getSize() = getAllPersons().getSize()@pre
     */
    void removePerson(Person person);

    /**
     * Updates fields of and existingPerson to fields of a new person.
     * Should not update id and name.
     *
     * @param existingPerson   the existing person
     * @param newPersonDetails person object from which new details are taken
     * @pre existingPerson != null
     * @pre newPersonDetails != null
     * @post existingPerson.getName() == existingPerson.getName()@pre
     * @post existingPerson.getId() == existingPerson.getName()@pre
     * @post existingPerson.getAge() == newPersonDetails.getAge()
     * @post existingPerson.getEmail() == newPersonDetails.getEmail()
     * @post existingPerson.getPhone() == newPersonDetails.getPhone()
     */
    void updatePerson(Person existingPerson, Person newPersonDetails);

    /**
     * Returns all the persons in the bank.
     *
     * @return the list of all persons
     * @pre true
     * @post @nochange
     */
    List<Person> getAllPersons();


    /**
     * Adds a new account to the bank.
     *
     * @param person  the person which is owner of the account
     * @param account the account to be added
     * @return true if the person does exist in bank, false otherwise
     * @pre person != null
     * @pre account != null
     * @post !getAllAccounts().contains(account)@pre => getAllAccounts().getSize() = getAllAccounts().getSize()@pre + 1 && @result==true && findPerson(account) == person
     * @post getAllAccounts().contains(account)@pre => getAllAccounts().getSize() = getAllAccounts().getSize()@pre && @result == false
     */
    boolean addAccount(Person person, Account account);

    /**
     * Remove an account from the bank.
     *
     * @param account the account to be removed
     * @pre account != null
     * @post getAllAccounts().contains(account)@pre => getAllAccounts().getSize() = getAllAccounts().getSize()@pre - 1
     * @post !getAllAccounts().contains(account)@pre => getAllAccounts().getSize() = getAllAccounts().getSize()@pre
     */
    void removeAccount(Account account);

    /**
     * Updates fields of and existingAccount to fields of a new account.
     * Should not update id.
     *
     * @param existingAccount   the existing account
     * @param newAccountDetails account object from which new details are taken
     * @pre existingAccount != null
     * @pre newAccountDetails != null
     * @post existingAccount.getId() == existingAccount.getId()@pre
     * @post existingAccount.getBalance() == newAccountDetails.getBalance()
     */
    void updateAccount(Account existingAccount, Account newAccountDetails);

    /**
     * Returns all the accounts in the bank.
     *
     * @return the list of all accounts
     * @pre true
     * @post @nochange
     */
    List<Account> getAllAccounts();

    /**
     * Search for the owner of the account.
     *
     * @param account the account whose owner is searched
     * @return the owner of the account if the account exists, null otherwise
     * @pre account != null
     * @post @nochange
     */
    Person findPerson(Account account);
}
