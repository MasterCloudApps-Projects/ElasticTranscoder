package es.videotranscoding.transcoder.config;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShutdownEndpointCustom extends ShutdownEndpoint {
    
    private static final Map<String, String> NO_CONTEXT_MESSAGE = Collections
            .unmodifiableMap(Collections.singletonMap("message", "No context to shutdown."));

    private static final Map<String, String> SHUTDOWN_MESSAGE = Collections
            .unmodifiableMap(Collections.singletonMap("message", "Shutting down, bye..."));

    @Autowired
    private ExecutorService executorService;

    private ConfigurableApplicationContext context;

    @WriteOperation
    @Override
    public Map<String, String> shutdown() {
        if (this.context == null) {
            return NO_CONTEXT_MESSAGE;
        }
        try {
            executorService.shutdown();
            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return SHUTDOWN_MESSAGE;
        } finally {
            Thread thread = new Thread(this::performShutdown);
            thread.setContextClassLoader(getClass().getClassLoader());
            thread.start();
        }
    }

    private void performShutdown() {
        try {
            Thread.sleep(500L);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        this.context.close();
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        if (context instanceof ConfigurableApplicationContext) {
            this.context = (ConfigurableApplicationContext) context;
        }
    }
}
