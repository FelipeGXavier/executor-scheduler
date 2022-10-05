package executor.scheduler.core.storage;

import executor.scheduler.core.TaskAction;
import executor.scheduler.core.TaskExecutionType;
import executor.scheduler.core.contracts.TaskStorage;
import executor.scheduler.util.IOUtil;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DefaultTaskStorage implements TaskStorage {

    private final String storagePath;

    private final static Logger logger = LoggerFactory.getLogger(DefaultTaskStorage.class);

    public DefaultTaskStorage(String name) {
        try {
            String basePath = new File(".").getCanonicalPath() + "/temp";
            this.storagePath = basePath + "/" + name + "__storage";
            var storageDir = new File(storagePath);
            if (!storageDir.exists()) {
                storageDir.mkdir();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean put(TaskAction task) throws IOException {
        logger.info("Adicionando tarefa {} ao storage {}", task, this.storagePath);
        String objectSerializationPath = this.storagePath + "/" + task.getId() + ".ser";
        FileOutputStream file = new FileOutputStream(objectSerializationPath);
        ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(task);
        out.close();
        file.close();
        return true;
    }

    @Override
    public List<TaskAction> findAllTasks() throws IOException, ClassNotFoundException {
        var filenames = IOUtil.listFiles(this.storagePath);
        List<TaskAction> tasks = new ArrayList<>();
        for (var filename : filenames) {
            FileInputStream file = new FileInputStream(this.storagePath + "/" + filename);
            ObjectInputStream in = new ObjectInputStream(file);
            TaskAction task = (TaskAction) in.readObject();
            tasks.add(task);
            in.close();
            file.close();
        }
        return tasks;
    }

    @Override
    public Optional<TaskAction> findTask(String id) {
        return Optional.empty();
    }

    @Override
    public void deleteTask(String id) {
        File file = new File(this.storagePath + "/" + id + ".ser");
        if(file.delete()) {
            logger.info("Deletando tarefa com id {} do storage {}", id, this.storagePath);
        } else {
            logger.error("Erro ao excluir tarefa com id {} do storage {}", id, this.storagePath);
        }
    }
}
