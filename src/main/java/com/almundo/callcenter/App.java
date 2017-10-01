package com.almundo.callcenter;

import com.almundo.callcenter.model.Director;
import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.model.Operator;
import com.almundo.callcenter.model.Supervisor;
import com.almundo.callcenter.services.Dispatcher;
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
        
        for(int i = 0; i < 7; i++) {
            atendees.add(new Operator("Operator #" + i));
        }
        
        for(int i = 0; i < 2; i++) {
            atendees.add(new Supervisor("Supervisor #" + i));
        }
        
        for(int i = 0; i < 1; i++) {
            atendees.add(new Director("Director #" + i));
        }

        Dispatcher d = new Dispatcher(atendees, Dispatcher.MAX_POOL_SIZE);

        ExecutorService threadPool = Executors.newFixedThreadPool(Dispatcher.MAX_POOL_SIZE); 
        
        List<Callable<Void>> callablesList = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            callablesList.add(new ProcessCall(d, i));
        }
        threadPool.invokeAll(callablesList);
        
        d.shutdown();
        threadPool.shutdown();
    }
    
    
    class ProcessCall implements Callable<Void> {
        private Integer call;
        private Dispatcher dispatcher;
        
        public ProcessCall(Dispatcher dispatcher, Integer call) {
            this.dispatcher = dispatcher;
            this.call = call;
        }

        @Override
        public Void call() throws Exception {
            dispatcher.dispatchCall(call);
            return null;
        }
    }

}
