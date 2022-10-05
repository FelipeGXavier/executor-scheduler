package executor.scheduler.core;

import executor.scheduler.core.contracts.TaskStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class ScriptExecutor extends BaseRunnable {

    private final Logger logger = LoggerFactory.getLogger(ScriptExecutor.class);

    public ScriptExecutor(TaskAction task, TaskStorage taskStorage) {
        super(task, taskStorage);
    }

    @Override
    public void run() {
        try {
            File f = new File(this.taskAction.getExecutionPath());
            if (!f.exists() || f.isDirectory()) {
                throw new RuntimeException("Caminho do script não encontrado " + this.taskAction.getExecutionPath());
            }
            Process process = Runtime.getRuntime().exec("node " + this.taskAction.getExecutionPath());
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String s;
            while (true) {
                if ((s = reader.readLine()) == null) break;
                System.out.println("Script output: " + s);
            }
            this.cleanTask();
        } catch (Exception e) {
            logger.error("Erro ao executar script", e);
        }
    }
}
