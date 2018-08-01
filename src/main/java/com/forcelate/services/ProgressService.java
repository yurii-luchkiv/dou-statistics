package com.forcelate.services;

import com.forcelate.domain.Progress;
import com.forcelate.domain.ProgressState;
import com.forcelate.logger.Logger;

import static com.forcelate.logger.Logger.debug;

public class ProgressService {
    private static Progress progress = new Progress();

    public static void updateProgress(ProgressState stage) {
        progress.setStage(stage);
        consoleProgress();
    }

    public static void addMessage(String message) {
        progress.getMessages().add(message);
    }

    // ------------------------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------------------------
    public static void consoleProgress() {
        debug("========================================");
        debug("Progress: " + progress.getStage());
        if (!progress.getMessages().isEmpty()) {
            progress.getMessages().forEach(Logger::debug);
            progress.getMessages().clear();
        }
        debug("========================================");
    }
}
