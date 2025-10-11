package by.tyv.exchangegenerator;

import by.tyv.exchangegenerator.service.ExchangeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringBootIntegrationTest {

    protected static WireMockServer wireMockServer = new WireMockServer(
            WireMockConfiguration.options().dynamicPort()
    );

    static {
        wireMockServer.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            wireMockServer.stop();
        }));
    }

    @AfterEach
    public void cleanUp() {
        wireMockServer.resetAll();
    }

    @Autowired
    protected ExchangeService exchangeService;
    @Autowired
    protected ObjectMapper objectMapper;

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry registry) {
        registry.add("clients.exchange-service.url", () -> wireMockServer.baseUrl());
    }
}
