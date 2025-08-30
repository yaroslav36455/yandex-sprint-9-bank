package by.tyv.cash;

import by.tyv.cash.controller.CashController;
import by.tyv.cash.service.CashService;
import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(controllers = CashController.class)
@AutoConfigureWebTestClient
public abstract class BaseContractTest {
    @MockitoBean
    CashService cashService;

    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    void setupRestAssured() {
        RestAssuredWebTestClient.webTestClient(webTestClient);
    }
}

