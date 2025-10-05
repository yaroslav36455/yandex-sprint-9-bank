package by.tyv.transfer;

import by.tyv.transfer.config.TestcontainersConfiguration;
import by.tyv.transfer.repository.DeferredNotificationRepository;
import by.tyv.transfer.scheduler.NotificationScheduler;
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
public class SpringBootIntegrationTest {
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected WebTestClient webClient;
    @Autowired
    protected DeferredNotificationRepository deferredNotificationRepository;
    @Autowired
    protected NotificationScheduler notificationScheduler;

    protected static WireMockServer wireMockServerBlocker = new WireMockServer(
            WireMockConfiguration.options().dynamicPort()
    );
    protected static WireMockServer wireMockServerAccount = new WireMockServer(
            WireMockConfiguration.options().dynamicPort()
    );
    protected static WireMockServer wireMockServerNotification = new WireMockServer(
            WireMockConfiguration.options().dynamicPort()
    );
    protected static WireMockServer wireMockServerExchange = new WireMockServer(
            WireMockConfiguration.options().dynamicPort()
    );

    static {
        wireMockServerBlocker.start();
        wireMockServerAccount.start();
        wireMockServerNotification.start();
        wireMockServerExchange.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            wireMockServerBlocker.stop();
            wireMockServerAccount.stop();
            wireMockServerNotification.stop();
            wireMockServerExchange.stop();
        }));
    }

    @AfterEach
    public void cleanUp() {
        wireMockServerBlocker.resetAll();
        wireMockServerAccount.resetAll();
        wireMockServerNotification.resetAll();
        wireMockServerExchange.resetAll();
    }

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry registry) {
        registry.add("clients.blocker-service.url", () -> wireMockServerBlocker.baseUrl());
        registry.add("clients.account-service.url", () -> wireMockServerAccount.baseUrl());
        registry.add("clients.notification-service.url", () -> wireMockServerNotification.baseUrl());
        registry.add("clients.exchange-service.url", () -> wireMockServerExchange.baseUrl());
    }
}
