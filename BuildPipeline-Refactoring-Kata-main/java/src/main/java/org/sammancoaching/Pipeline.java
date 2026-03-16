package org.sammancoaching;

import org.sammancoaching.dependencies.Config;
import org.sammancoaching.dependencies.Emailer;
import org.sammancoaching.dependencies.Logger;
import org.sammancoaching.dependencies.Project;

public class Pipeline {
    private final Config config;
    private final Emailer emailer;
    private final Logger log;

    public Pipeline(Config config, Emailer emailer, Logger log) {
        this.config = config;
        this.emailer = emailer;
        this.log = log;
    }

    public void run(Project project) {
        // Fase 1: Execução de Testes
        boolean testsPassed = executeTests(project);

        // Fase 2: Deployment (só ocorre se os testes passarem)
        boolean deploySuccessful = false;
        if (testsPassed) {
            deploySuccessful = executeDeployment(project);
        }

        // Fase 3: Notificações
        PipelineResult result = new PipelineResult(testsPassed, deploySuccessful);
        handleNotifications(result);
    }

    private boolean executeTests(Project project) {
        if (!project.hasTests()) {
            log.info("No tests");
            return true;
        }

        // Substituindo a comparação de String por um método mais expressivo
        boolean success = "success".equals(project.runTests());

        if (success) {
            log.info("Tests passed");
        } else {
            log.error("Tests failed");
        }
        return success;
    }

    private boolean executeDeployment(Project project) {
        boolean success = "success".equals(project.deploy());

        if (success) {
            log.info("Deployment successful");
        } else {
            log.error("Deployment failed");
        }
        return success;
    }

    private void handleNotifications(PipelineResult result) {
        if (!config.sendEmailSummary()) {
            log.info("Email disabled");
            return;
        }

        log.info("Sending email");
        sendEmailByResult(result);
    }

    private void sendEmailByResult(PipelineResult result) {
        if (!result.areTestsPassed()) {
            emailer.send("Tests failed");
            return;
        }

        if (result.isDeploySuccessful()) {
            emailer.send("Deployment completed successfully");
        } else {
            emailer.send("Deployment failed");
        }
    }
}