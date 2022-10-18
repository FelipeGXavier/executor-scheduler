package executor.scheduler.runnables;

import executor.scheduler.core.DatabaseService;
import executor.scheduler.core.RunnableSerializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class ExampleRunnable extends RunnableSerializable {

    private final static Logger logger = LoggerFactory.getLogger(ExampleRunnable.class);
    private final DatabaseService service;

    public ExampleRunnable(DatabaseService service) {
        this.service = service;
    }

    @Override
    public void run() {
        logger.info("Starting ExampleRunnable schedule");
        this.service.cleanOldRows("customers", LocalDateTime.now());
    }
}
