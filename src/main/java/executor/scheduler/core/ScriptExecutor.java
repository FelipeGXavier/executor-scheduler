package executor.scheduler.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ScriptExecutor implements Runnable {

    private final String scriptPath;

    public ScriptExecutor(String scriptPath) {
        this.scriptPath = scriptPath;
    }

    @Override
    public void run() {
        try {
            Process process = Runtime.getRuntime().exec(this.scriptPath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String s;
            while (true) {
                if ((s = reader.readLine()) == null) break;
                System.out.println("Script output: " + s);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
