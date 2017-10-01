package com.almundo.callcenter.services;

import com.almundo.callcenter.model.Employee;
import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

public class Dispatcher {
    
    private PriorityBlockingQueue<Employee> atendees;
    
    private static final Integer MAX_CALL_DURATION = 10000;
    private static final Integer MIN_CALL_DURATION = 5000;
    
    private static final Logger log = Logger.getLogger(Dispatcher.class);

    /**
     * Constructor
     * @param atendees
     */
    public Dispatcher(PriorityBlockingQueue<Employee> atendees) {
        this.atendees = atendees;
    }

    /**
     * Take an available atendee and perform the call, then put it back in the atendees queue. 
     * If no atendee are available, then the take() method lock all new incoming calls till an atendee is available again
     * @param call
     * @return
     */
    public Employee dispatchCall(Integer call) {
        Employee atendee = null;
        try {
            atendee = atendees.take();
            log.info("Incoming call #" + call + " Assign atendee " + atendee);
            doCall(call, atendee);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        return atendee;
    }

    /**
     * Simulate call execution and when call is finish, the atendee goes back to the queue
     * @param call
     * @param atendee
     */
    private void doCall(Integer call, Employee atendee) {
        try {
            Integer duration = new Random().nextInt((MAX_CALL_DURATION - MIN_CALL_DURATION) + 1) + MIN_CALL_DURATION;
            Thread.sleep(duration);
            log.info("Finish Call#" + call + " with Atendee " + atendee + " was finished with duration " + duration);
            atendees.add(atendee);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
