package org.sammancoaching;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sammancoaching.dependencies.*;
import static org.mockito.Mockito.*;

class PipelineTest {
    private Config config;
    private Emailer emailer;
    private CapturingLogger log;
    private Pipeline pipeline;

    @BeforeEach
    void setup() {
        config = mock(Config.class);
        emailer = mock(Emailer.class);
        log = new CapturingLogger();
        pipeline = new Pipeline(config, emailer, log);
    }

    @Test
    void deveLogarSucessoQuandoNaoHaTestesEDeployOk() {
        Project project = Project.builder()
                .setTestStatus(TestStatus.NO_TESTS)
                .setDeploysSuccessfully(true)
                .build();

        pipeline.run(project);

        assert(log.getLoggedLines().contains("INFO: No tests"));
        assert(log.getLoggedLines().contains("INFO: Deployment successful"));
    }

    @Test
    void deveEnviarEmailDeErroQuandoTestesFalham() {
        when(config.sendEmailSummary()).thenReturn(true);
        Project project = Project.builder()
                .setTestStatus(TestStatus.FAILING_TESTS)
                .build();

        pipeline.run(project);

        verify(emailer).send("Tests failed");
    }
}