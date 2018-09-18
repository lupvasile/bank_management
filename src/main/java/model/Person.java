package model;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

/**
 * Class representing a person.
 *
 * @author Vasile Lup
 */
public class Person implements Observer, Serializable {
    private int id;
    private String name;
    private int age;
    private String email;
    private String phone;

    public Person(int id, String name, int age, String email, String phone) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Short constructor of the class Customer.
     * Age is set as 0, phone number is null.
     *
     * @param id    the id of the customer
     * @param name  name of the customer
     * @param email the customer's email
     */
    public Person(int id, String name, String email) {
        this(id, name, 0, email, null);
    }

    public Person() {
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Customer " + name +
                " is notified that account " + o +
                " has changed its ballance to " + arg
        );
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "id=" + id +
                "\n name='" + name + '\'' +
                "\n age=" + age +
                "\n email='" + email + '\'' +
                "\n phone='" + phone + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != person.id) return false;
        return name.equals(person.name);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }
}
