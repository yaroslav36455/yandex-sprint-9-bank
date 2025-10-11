package by.tyv.account;

import by.tyv.account.config.TestcontainersConfiguration;
import by.tyv.account.repository.AccountRepository;
import by.tyv.account.repository.DeferredNotificationRepository;
import by.tyv.account.scheduler.NotificationScheduler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Import(TestcontainersConfiguration.class)
public abstract class SpringBootIntegrationTest {
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected WebTestClient webClient;
    @Autowired
    protected DeferredNotificationRepository deferredNotificationRepository;
    @Autowired
    protected AccountRepository accountRepository;
    @Autowired
    protected NotificationScheduler notificationScheduler;

    protected static WireMockServer wireMockServerNotification = new WireMockServer(
            WireMockConfiguration.options().dynamicPort()
    );

    static {
        wireMockServerNotification.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            wireMockServerNotification.stop();
        }));
    }

    @AfterEach
    public void cleanUp() {
        wireMockServerNotification.resetAll();
    }

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry registry) {
        registry.add("clients.notification-service.url", () -> wireMockServerNotification.baseUrl());
    }
}
