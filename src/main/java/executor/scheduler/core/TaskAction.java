package executor.scheduler.core;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TaskAction implements Serializable {

    private final String id = UUID.randomUUID().toString();
    private final TaskExecutionType taskType;
    private final String executionPath;
    private long time;
    private TimeUnit unit;
    private final boolean runOnce;
    private final boolean shouldPersist;
    private final RunnableSerializable runnable;

    protected TaskAction(TaskExecutionType taskType, String executionPath, String executionTime, boolean runOnce, RunnableSerializable runnable, boolean shouldPersist) {
        try {
            String path = new File(".").getCanonicalPath();
            this.taskType = taskType;
            this.executionPath = path + "/scripts/" + executionPath;
            this.runOnce = runOnce;
            this.runnable = runnable;
            this.shouldPersist = shouldPersist;
            this.parseExecutionTime(executionTime);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static TaskAction ofRunnable(RunnableSerializable runnable, String executionTime, boolean runOnce, boolean shouldPersist) {
        return new TaskAction(TaskExecutionType.RUNNABLE, null, executionTime, runOnce, runnable, shouldPersist);
    }

    public static TaskAction ofScript(String executionPath, String executionTime, boolean runOnce, boolean shouldPersist) {
        return new TaskAction(TaskExecutionType.SCRIPT, executionPath, executionTime, runOnce, null, shouldPersist);
    }

    private void parseExecutionTime(String executionTime) {
        if (executionTime == null || executionTime.isEmpty()) {
            throw new RuntimeException("Tempo de agendamento inválido");
        }
        var parts = executionTime.split("");
        if (parts.length < 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new RuntimeException("Tempo de agendamento inválido");
        }
        StringBuilder numberPart = new StringBuilder();
        var currentPart = parts[0];
        int index = 0;
        while (isNumeric(currentPart)) {
            numberPart.append(currentPart);
            currentPart = parts[++index];
        }
        long time = Long.parseLong(numberPart.toString());
        TimeUnit unit;
        switch (parts[index].toLowerCase().trim()) {
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

    private boolean isNumeric(String str) {
        return str.chars().allMatch(Character::isDigit);
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

    public long getTime() {
        return time;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public boolean isRunOnce() {
        return runOnce;
    }

    public RunnableSerializable getRunnable() {
        return runnable;
    }

    public boolean shouldPersist() {
        return shouldPersist;
    }

    @Override
    public String toString() {
        return "TaskAction{" +
                "id='" + id + '\'' +
                ", taskType=" + taskType +
                ", executionPath='" + executionPath + '\'' +
                ", time=" + time +
                ", unit=" + unit +
                ", runOnce=" + runOnce +
                '}';
    }
}
