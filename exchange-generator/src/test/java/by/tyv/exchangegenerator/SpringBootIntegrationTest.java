package by.tyv.exchangegenerator;

import by.tyv.exchangegenerator.service.ExchangeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "scheduler.enable=false")
public class SpringBootIntegrationTest {

    protected static WireMockServer wireMockServer = new WireMockServer(
            WireMockConfiguration.options().dynamicPort()
    );

    static {
        wireMockServer.start();
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    @Autowired
    protected ExchangeService exchangeService;
    @Autowired
    protected ObjectMapper objectMapper;

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry registry) {
        registry.add("clients.exchange-service.url", () -> "http://localhost:" + wireMockServer.port());
    }
}
