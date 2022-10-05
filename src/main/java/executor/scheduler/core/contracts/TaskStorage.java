package executor.scheduler.core.contracts;

import executor.scheduler.core.TaskAction;

import java.util.List;
import java.util.Optional;

public interface TaskStorage {

    boolean put(TaskAction task);
    List<TaskAction> findAllTasks();
    Optional<TaskAction> findTask(String id);

}
