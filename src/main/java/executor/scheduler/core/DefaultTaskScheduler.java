package executor.scheduler.core;

import executor.scheduler.core.contracts.TaskStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class TaskScheduler {

    private final TaskStorage storage;
    private final ScheduledExecutorService scheduler;

    private final static Logger logger = LoggerFactory.getLogger(TaskScheduler.class);

    public TaskScheduler(TaskStorage storage, int numThreads) {
        this.storage = storage;
        this.scheduler = Executors.newScheduledThreadPool(numThreads);
    }


    public boolean scheduleTask(TaskAction task) {
        Runnable taskToBeScheduled;
        if (task.getTaskType() == TaskExecutionType.SCRIPT) {
            taskToBeScheduled = new ScriptExecutor(task.getExecutionPath());
        } else if (task.getTaskType() == TaskExecutionType.RUNNABLE) {
            taskToBeScheduled = task.getRunnable();
        } else {
            logger.error("Erro ao instanciar tarefa para executada {}", task);
            return false;
        }
        logger.info("Agendando tarefa {}", task);
        try {
            if (task.isRunOnce()) {
                this.scheduler.schedule(taskToBeScheduled, task.getTime(), task.getUnit());
            } else {
                this.scheduler.scheduleAtFixedRate(taskToBeScheduled, 0, task.getTime(), task.getUnit());
            }
        } catch (Exception ex) {
            logger.error("Falha ao agendar tarefa {} {}", taskToBeScheduled, ex);
            return false;
        }
        return true;
    }

    public void shutdown() {
        this.scheduler.shutdown();
    }

    public void pintCurrentSchedules() {

    }
}
