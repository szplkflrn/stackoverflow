package com.codecool.stackoverflowtw.logger;

import java.time.LocalDateTime;

public class ConsoleLogger implements Logger {
    @Override
    public void logError(String message) {
        logMessage(message, "INFO");
    }

    @Override
    public void logInfo(String message) {
        logMessage(message, "ERROR");
    }

    private void logMessage(String message, String type) {
        String entry = "[" + LocalDateTime.now() + "] " + type + ": " + message;
        System.out.println(entry);
    }
}

