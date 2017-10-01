package com.almundo.callcenter;

import com.almundo.callcenter.model.Director;
import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.model.Operator;
import com.almundo.callcenter.model.Supervisor;
import com.almundo.callcenter.services.Dispatcher;
import com.almundo.callcenter.services.IncomingCallCallable;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class App {

    private static final Logger log = Logger.getLogger(App.class);
    
    public static void main(String[] args) throws Exception {
        new App().process();    
    }
    
    public void process() throws ExecutionException, InterruptedException {
        PriorityBlockingQueue<Employee> atendees = new PriorityBlockingQueue<>();
        
        for(int i = 0; i < 5; i++) {
            atendees.add(new Operator("Operator #" + i));
        }
        
        for(int i = 0; i < 1; i++) {
            atendees.add(new Supervisor("Supervisor #" + i));
        }
        
        for(int i = 0; i < 1; i++) {
            atendees.add(new Director("Director #" + i));
        }

        Dispatcher d = new Dispatcher(atendees);

        ExecutorService threadPool = Executors.newFixedThreadPool(10); 
        
        List<Callable<Employee>> callablesList = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            callablesList.add(new IncomingCallCallable(d, i));
        }
        List<Future<Employee>> futureList = threadPool.invokeAll(callablesList);
        
        threadPool.shutdown();
    }
}
