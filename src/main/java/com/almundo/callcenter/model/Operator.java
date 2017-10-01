package com.almundo.callcenter.model;

public class Operator extends Employee {
    
    public Operator(String name) {
        super(name);
        setPriority(Priority.OPERATOR);
    }
    
}
