package executor.scheduler.core;

import executor.scheduler.core.contracts.TaskScheduler;
import executor.scheduler.core.contracts.TaskStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class DefaultTaskScheduler implements TaskScheduler {

    private final TaskStorage storage;
    private final ScheduledExecutorService scheduler;

    private final static Logger logger = LoggerFactory.getLogger(DefaultTaskScheduler.class);

    public DefaultTaskScheduler(TaskStorage storage, int numThreads) {
        this.storage = storage;
        this.scheduler = Executors.newScheduledThreadPool(numThreads);
        this.onInit();
    }


    public boolean scheduleTask(TaskAction task) {
        BaseRunnable taskToBeScheduled;
        if (task.getTaskType() == TaskExecutionType.SCRIPT) {
            taskToBeScheduled = new ScriptExecutor(task, this.storage);
        } else if (task.getTaskType() == TaskExecutionType.RUNNABLE) {
            taskToBeScheduled = new BaseRunnable(task, this.storage, task.getRunnable());
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
            if (task.shouldPersist()) {
                this.storage.put(task);
            }
        } catch (Exception ex) {
            logger.error("Falha ao agendar tarefa {} {}", taskToBeScheduled, ex);
            return false;
        }
        return true;
    }

    private void onInit() {
        try {
            List<TaskAction> storedTasks = this.storage.findAllTasks();
            storedTasks.forEach(this::scheduleTask);
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Não foi possível agendar as tarefas existentes", e);
        }
    }

    public void shutdown() {
        this.scheduler.shutdown();
    }

    public void printStoredSchedules() {
        try {
            var tasks = this.storage.findAllTasks();
            System.out.println("Número de tarefas agendadas: " + tasks.size());
            tasks.forEach(task -> {
                System.out.println("Tarefa#" + task.getId() + " | " + task.getTaskType() + " | " + task.getTime() + " | " + task.getUnit());
            });
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Não foi possível carregar as tarefas agendadas", e);
        }
    }
}
