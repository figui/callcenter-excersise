package com.almundo.callcenter.services;

import com.almundo.callcenter.model.Employee;

import java.util.concurrent.Callable;

public class IncomingCallCallable implements Callable<Employee>{

    private Integer call;
    private Dispatcher dispatcher;
        
    public IncomingCallCallable(Dispatcher dispatcher, Integer call) {
        this.dispatcher = dispatcher;
        this.call = call;
    }

    @Override
    public Employee call() throws Exception {
        return dispatcher.dispatchCall(call);
    }
}
