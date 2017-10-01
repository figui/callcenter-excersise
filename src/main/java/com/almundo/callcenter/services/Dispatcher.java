package com.almundo.callcenter.services;

import com.almundo.callcenter.model.Employee;
import org.apache.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public class Dispatcher {
    
    private PriorityBlockingQueue<Employee> atendees;
    private ExecutorService callPool;
    
    public static final Integer MAX_POOL_SIZE = 10;
    
    private static final Logger log = Logger.getLogger(Dispatcher.class);
    
    public Dispatcher(PriorityBlockingQueue<Employee> atendees, Integer poolSize) {
        this.callPool = Executors.newFixedThreadPool(poolSize != null ? poolSize : MAX_POOL_SIZE);
        this.atendees = atendees;
    }

    public synchronized void dispatchCall(Integer call) {
        try {
            Employee atendee = atendees.take();
            log.info("Incoming call #" + call + " Assign atendee " + atendee);
            callPool.execute(new ProcessCall(atendee, call, this));
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
    
    void finishCall(Integer call, Double duration, Employee atendee) {
        log.info("Finish Call#" + call + " with Atendee " + atendee + " was finished with duration " + duration);
        atendees.add(atendee);
    }

    public void shutdown() {
        callPool.shutdown();
    }
}
