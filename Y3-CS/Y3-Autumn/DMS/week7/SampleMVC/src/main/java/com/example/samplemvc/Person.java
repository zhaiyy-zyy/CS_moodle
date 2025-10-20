package com.example.samplemvc;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Note: This sample code is used for the purpose of MVC demonstration.
 * It doesn't mean the code has been properly refactored
 * or other design patterns are well applied.
 */

public class Person {
    private IntegerProperty age;

    public Person(int age){
        if (this.age == null)
            this.age = new SimpleIntegerProperty();
        this.age.set(age);
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public int getAge() {
        return age.get();
    }
    public void setAge(int age) {
        this.age.set(age);
    }

    public void incrementAge() {
        this.age.set(getAge()+1);
    }
    public void decrementAge() {
        this.age.set(getAge()-1);
    }
}
