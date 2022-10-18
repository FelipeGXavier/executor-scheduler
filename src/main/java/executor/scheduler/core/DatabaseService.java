package executor.scheduler.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DatabaseService implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseService.class);

    public void cleanOldRows(String table, LocalDateTime lessThan) {
        logger.info("Cleaning rows from table {} where date is less than {}", table, lessThan);
    }
}
