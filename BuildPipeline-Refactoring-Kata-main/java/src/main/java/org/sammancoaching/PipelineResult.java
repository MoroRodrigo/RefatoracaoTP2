package org.sammancoaching;

/**
 * Classe auxiliar para agrupar o estado da execução do pipeline.
 * Ajuda a reduzir o acoplamento e melhora a legibilidade.
 */
public class PipelineResult {
    private final boolean testsPassed;
    private final boolean deploySuccessful;

    public PipelineResult(boolean testsPassed, boolean deploySuccessful) {
        this.testsPassed = testsPassed;
        this.deploySuccessful = deploySuccessful;
    }

    public boolean areTestsPassed() {
        return testsPassed;
    }

    public boolean isDeploySuccessful() {
        return deploySuccessful;
    }

    public boolean isFullySuccessful() {
        return testsPassed && deploySuccessful;
    }
}