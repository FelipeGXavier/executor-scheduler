package executor.scheduler.core;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TaskAction {

    private final String id = UUID.randomUUID().toString();
    private final TaskExecutionType taskType;
    private final String executionPath;
    private long time;
    private TimeUnit unit;
    private final boolean runOnce;


    public TaskAction(TaskExecutionType taskType, String executionPath, String executionTime, boolean runOnce) {
        this.taskType = taskType;
        this.executionPath = executionPath;
        this.runOnce = runOnce;
        this.parseExecutionTime(executionTime);
    }

    private void parseExecutionTime(String executionTime) {
        if (executionTime == null || executionTime.isEmpty()) {
            throw new RuntimeException("Tempo de agendamento inválido");
        }
        var parts = executionTime.split("");
        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new RuntimeException("Tempo de agendamento inválido");
        }
        long time = Long.parseLong(parts[0]);
        TimeUnit unit;
        switch (parts[1].toLowerCase().trim()) {
            case "mili":
                unit = TimeUnit.MILLISECONDS;
                break;
            case "s":
                unit = TimeUnit.SECONDS;
                break;
            case "m":
                unit = TimeUnit.MINUTES;
                break;
            case "d":
                unit = TimeUnit.DAYS;
                break;
            default:
                throw new RuntimeException("Unidade de tempo inválida " + parts[1]);
        }
        this.time = time;
        this.unit = unit;
    }

    public TaskExecutionType getTaskType() {
        return taskType;
    }

    public String getExecutionPath() {
        return executionPath;
    }

    public String getId() {
        return id;
    }


}
