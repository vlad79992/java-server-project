package com.example.restservice.user;

public class User {
    private String firstName, secondName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    
    @Override
    public String toString() {
        return this.firstName + " " + this.secondName;
    }
}
