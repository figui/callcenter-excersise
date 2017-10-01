package com.almundo.callcenter.model;

public class Director extends Employee {

    public Director(String name) {
        super(name);
        setPriority(Priority.DIRECTOR);
    }
}
