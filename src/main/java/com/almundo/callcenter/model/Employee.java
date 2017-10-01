package com.almundo.callcenter.model;

public class Employee implements Comparable<Employee> {
    private String name;
    private Priority priority;
    
    public Employee(String name) {
        this.name = name;
    }

    public Priority getPriority() {
        return priority;
    }
    
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Employee o) {
        return this.priority.getValue().compareTo(o.getPriority().getValue());
    }

    @Override
    public String toString() {
        return name;
    }

    enum Priority {
        
        OPERATOR(1),
        SUPERVISOR(2),
        DIRECTOR(3);

        private Integer value;

        public Integer getValue() {
            return value;
        }
        
        Priority(Integer i) {
            this.value = i;
        }
    }
}
