package com.almundo.callcenter.model;

public class Supervisor extends Employee{

    public Supervisor(String name) {
        super(name);
        setPriority(Priority.SUPERVISOR);
    }
}
