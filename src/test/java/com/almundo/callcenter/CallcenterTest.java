package com.almundo.callcenter;

import com.almundo.callcenter.model.Director;
import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.model.Operator;
import com.almundo.callcenter.model.Supervisor;
import com.almundo.callcenter.services.Dispatcher;
import com.almundo.callcenter.services.IncomingCallCallable;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class CallcenterTest {
    
    PriorityBlockingQueue<Employee> atendees;
    ExecutorService incomingCallExecutor;
    Dispatcher dispatcher;
    
    @Test
    public void testConcurrentCalls() throws InterruptedException, ExecutionException {
        setup(7,2,1,10);

        List<Callable<Employee>> callableList = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            callableList.add(new IncomingCallCallable(dispatcher, i));
        }
        
        List<Future<Employee>> futureList = incomingCallExecutor.invokeAll(callableList);
        
        int counCalls = 0;
        for(Future<Employee> future : futureList) {
            assert future.get() != null;
            counCalls++;
        }
        
        assert counCalls == 10;
    }
    
    @Test
    /**
     * Add 7 operators, 2 supervisors and 1 director and then dispatch 7 call, all atendees that answer the call must be Operators
     */
    public void testPriorityCallAntedee() throws InterruptedException, ExecutionException {
        setup(7,2,1,10);

        List<Callable<Employee>> callableList = new ArrayList<>();
        for(int i = 0; i < 7; i++) {
            callableList.add(new IncomingCallCallable(dispatcher, i));
        }
        
        List<Future<Employee>> futureList = incomingCallExecutor.invokeAll(callableList);
        
        for(Future<Employee> future : futureList) {
            assert future.get() instanceof Operator;
        }
    }
    
    @Test
    /**
     * Add 7 operators, 2 supervisors and 1 director and then dispatch 8 call, all atendees that answer the call must be Operators and the last must be Supervisor
     */
    public void testPriorityCallWithSupervisor() throws InterruptedException, ExecutionException {
        setup(7,2,1,10);

        List<Callable<Employee>> callableList = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            callableList.add(new IncomingCallCallable(dispatcher, i));
        }
        
        List<Future<Employee>> futureList = incomingCallExecutor.invokeAll(callableList);
        
        for(Future<Employee> future : futureList) {
            Employee e = future.get();
            assert e != null;
            assert e instanceof Operator || e instanceof Supervisor;
        }
        
        
    }

    private void setup(Integer operators, Integer supervisors, Integer directors, Integer callPoolSize) {
        atendees = new PriorityBlockingQueue<>();
        for(int i = 0; i < operators; i++) {
            atendees.add(new Operator("Operator #" + i));
        }
        
        for(int i = 0; i < supervisors; i++) {
            atendees.add(new Supervisor("Supervisor #" + i));
        }
        
        for(int i = 0; i < directors; i++) {
            atendees.add(new Director("Director #" + i));
        }
        
        incomingCallExecutor = Executors.newFixedThreadPool(callPoolSize);
        dispatcher = new Dispatcher(atendees);
    }
    
    @After
    public void tearDown() {
        atendees = null;
        dispatcher = null;
        incomingCallExecutor.shutdown();
        if(incomingCallExecutor.isShutdown()) {
            incomingCallExecutor = null;
        }
    }
}
