package com.almundo.callcenter.services;

import com.almundo.callcenter.model.Employee;
import org.apache.log4j.Logger;

import java.util.Random;

public class ProcessCall implements Runnable {

    private Employee atendee;
    private Integer call;
    private Dispatcher dispatcher;
    
    private static final Integer MAX_CALL_DURATION = 10000;
    private static final Integer MIN_CALL_DURATION = 5000;
    
    private static final Logger log = Logger.getLogger(ProcessCall.class);
    
    public ProcessCall(Employee atendee, Integer call, Dispatcher dispatcher) {
        this.atendee = atendee;
        this.call = call;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        try {
            Integer duration = new Random().nextInt((MAX_CALL_DURATION - MIN_CALL_DURATION) + 1) + MIN_CALL_DURATION;
            Thread.sleep(duration);
            dispatcher.finishCall(call, duration.doubleValue() / 1000, atendee);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
