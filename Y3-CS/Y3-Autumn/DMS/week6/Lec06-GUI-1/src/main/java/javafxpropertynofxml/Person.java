package javafxpropertynofxml;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Person {
    private StringProperty aliasName;
    private StringProperty firstName;
    private StringProperty lastName;
    private final ObservableList<Person> employees = FXCollections.observableArrayList();

    public Person(String alias, String firstName, String lastName) {
        setAliasName(alias);
        setFirstName(firstName);
        setLastName(lastName);
    }

    public final String getAliasName() {
        return aliasNameProperty().get();
    }

    public final void setAliasName(String value) {
        aliasNameProperty().set(value);
    }

    public StringProperty aliasNameProperty() {
        if (aliasName == null) {
            aliasName = new SimpleStringProperty();
        }
        return aliasName;
    }

    public final String getFirstName() {
        return firstNameProperty().get();
    }

    public final void setFirstName(String value) {
        firstNameProperty().set(value);
    }

    public StringProperty firstNameProperty() {
        if (firstName == null) {
            firstName = new SimpleStringProperty();
        }
        return firstName;
    }

    public final String getLastName() {
        return lastNameProperty().get();
    }

    public final void setLastName(String value) {
        lastNameProperty().set(value);
    }

    public StringProperty lastNameProperty() {
        if (lastName == null) {
            lastName = new SimpleStringProperty();
        }
        return lastName;
    }

    public ObservableList<Person> employeesProperty() {
        return employees;
    }
}