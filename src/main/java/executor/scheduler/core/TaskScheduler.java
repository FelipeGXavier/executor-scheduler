package executor.scheduler.core;

import executor.scheduler.core.contracts.TaskStorage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class TaskScheduler {

    private final TaskStorage storage;
    private final int numThreads;

    private final ScheduledExecutorService scheduler;

    public TaskScheduler(TaskStorage storage, int numThreads) {
        this.storage = storage;
        this.numThreads = numThreads;
        this.scheduler = Executors.newScheduledThreadPool(numThreads);
    }

    public boolean scheduleTask(TaskAction task) {
        if (task.getTaskType() == TaskExecutionType.SCRIPT) {
            this.scheduler.schedule(new ScriptExecutor(task.getExecutionPath()), );
        }
        return true;
    }

    public void shutdown() {
        this.scheduler.shutdown();
    }

    public void pintCurrentSchedules() {

    }
}
