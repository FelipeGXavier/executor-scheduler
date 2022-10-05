package executor.scheduler;

import executor.scheduler.core.RunnableSerializable;
import executor.scheduler.core.TaskAction;
import executor.scheduler.core.DefaultTaskScheduler;
import executor.scheduler.core.contracts.TaskScheduler;
import executor.scheduler.core.contracts.TaskStorage;
import executor.scheduler.core.storage.DefaultTaskStorage;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        TaskStorage taskStorage = new DefaultTaskStorage("ts");
        TaskScheduler scheduler = new DefaultTaskScheduler(taskStorage, 10);

        TaskAction task = TaskAction.ofScript("hello.js", "1m", true, true);

        TaskAction task2 = TaskAction.ofRunnable(new RunnableSerializable() {
            @Override
            public void run() {
                System.out.println("Executando");
            }
        }, "5s", false, true);

        scheduler.scheduleTask(task);
        scheduler.scheduleTask(task2);
    }
}