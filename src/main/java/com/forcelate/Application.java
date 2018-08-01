package com.forcelate;

import com.forcelate.configuration.Configurations;
import com.forcelate.services.FlowService;

import static com.forcelate.logger.Logger.debug;

public class Application {

    public static void main(String[] args) {
        debug("Started...");
        executeOne();
        debug("Completed...");
    }

    private static void executeOne() {
        debug("Execute One...");
        FlowService.executeAnalysis(Configurations.ONE);
    }

    private static void executeAll() {
        debug("Execute All...");
        Configurations.getAll().forEach(FlowService::executeAnalysis);
    }
}
