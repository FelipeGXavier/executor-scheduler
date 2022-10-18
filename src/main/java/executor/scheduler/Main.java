package executor.scheduler;

import executor.scheduler.core.DatabaseService;
import executor.scheduler.core.RunnableSerializable;
import executor.scheduler.core.TaskAction;
import executor.scheduler.core.DefaultTaskScheduler;
import executor.scheduler.core.contracts.TaskScheduler;
import executor.scheduler.core.contracts.TaskStorage;
import executor.scheduler.core.storage.DefaultTaskStorage;
import executor.scheduler.runnables.ExampleRunnable;

public class Main {
    public static void main(String[] args) {
        // Armazenamento das tarefas
        TaskStorage taskStorage = new DefaultTaskStorage("ts");
        TaskScheduler scheduler = new DefaultTaskScheduler(taskStorage, 10);
        // Tarefa execução de script
        TaskAction task = TaskAction.ofScript("hello.js", "1m", false, true);
        TaskAction task2 = TaskAction.ofRunnable(new RunnableSerializable() {
            @Override
            public void run() {
                System.out.println("Executando");
            }
        }, "10s", true, false);

        TaskAction task3 = TaskAction.ofRunnable(new ExampleRunnable(new DatabaseService()),
                "30s",
                false,
                true);

        scheduler.scheduleTask(task);
        scheduler.scheduleTask(task2);
        scheduler.scheduleTask(task3);

        scheduler.printStoredSchedules();
    }
}