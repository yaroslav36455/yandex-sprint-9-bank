package by.tyv.frontui.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class FrontUiControllerTest {

    @Autowired
    WebTestClient webClient;

    @Test
    @DisplayName("GET / - редирект на /main")
    public void redirectToPost() {
        webClient.get()
                .uri("/")
                .exchange()
                .expectStatus().isSeeOther()
                .expectHeader().location("/main");
    }
}
