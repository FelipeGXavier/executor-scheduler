package executor.scheduler.core;

import executor.scheduler.core.contracts.TaskStorage;

import java.io.Serializable;

public class BaseRunnable implements Runnable, Serializable {

    protected final TaskAction taskAction;
    protected final TaskStorage taskStorage;

    protected Runnable runnable;

    public BaseRunnable(TaskAction task, TaskStorage taskStorage) {
        this.taskAction = task;
        this.taskStorage = taskStorage;
    }

    public BaseRunnable(TaskAction task, TaskStorage taskStorage, Runnable runnable) {
        this(task, taskStorage);
        this.runnable = runnable;
    }

    protected void cleanTask() {
        if (this.taskAction.isRunOnce()) {
            this.taskStorage.deleteTask(this.taskAction.getId());
        }
    }

    @Override
    public void run() {
        if (this.runnable != null) {
            this.runnable.run();
            this.cleanTask();
        }
    }
}
