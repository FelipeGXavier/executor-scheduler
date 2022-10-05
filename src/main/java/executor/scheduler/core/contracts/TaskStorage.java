package executor.scheduler.core.contracts;

import executor.scheduler.core.TaskAction;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface TaskStorage {

    boolean put(TaskAction task) throws IOException;
    List<TaskAction> findAllTasks() throws IOException, ClassNotFoundException;
    Optional<TaskAction> findTask(String id);

    void deleteTask(String id);

}
