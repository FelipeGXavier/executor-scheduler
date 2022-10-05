package executor.scheduler.core.contracts;

import executor.scheduler.core.TaskAction;

public interface TaskScheduler {

    boolean scheduleTask(TaskAction task);

    void shutdown();

    void printStoredSchedules();

}
