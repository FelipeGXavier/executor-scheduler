package executor.scheduler.core.storage;

import executor.scheduler.core.TaskAction;
import executor.scheduler.core.contracts.TaskStorage;

import java.util.List;
import java.util.Optional;

public class DefaultTaskStorage implements TaskStorage {

    @Override
    public boolean put(TaskAction task) {
        return false;
    }

    @Override
    public List<TaskAction> findAllTasks() {
        return null;
    }

    @Override
    public Optional<TaskAction> findTask(String id) {
        return Optional.empty();
    }
}
