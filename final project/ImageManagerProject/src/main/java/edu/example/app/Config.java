package edu.example.app;

import java.nio.file.Paths;

/**
 * Config - simple Singleton configuration holder.
 */
public class Config {
    private static Config instance;
    private String tempDir;

    private Config() {
        this.tempDir = System.getProperty("java.io.tmpdir");
    }

    public static Config getInstance() {
        if (instance == null) instance = new Config();
        return instance;
    }

    public String getTempDir() { return tempDir; }
}
