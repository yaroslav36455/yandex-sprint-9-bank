package by.tyv.frontui.integration;

import by.tyv.frontui.controller.FrontUiController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class FrontUiControllerTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    @DisplayName("GET / - редирект на /main")
    public void redirectToMainPage() {
        webClient.get()
                .uri("/")
                .exchange()
                .expectStatus().isSeeOther()
                .expectHeader().location("/main");
    }

    @Test
    @DisplayName("GET /signup - страница регистрации нового пользователя")
    public void redirectToSignupPage() {
        webClient.get()
                .uri("/signup")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody(String.class);
    }

    @Test
    @DisplayName("GET /signup - представление, страница регистрации нового пользователя")
    public void signupView() {
        FrontUiController controller = new FrontUiController();
        String viewName = controller.getSignupPage().block();
        Assertions.assertThat(viewName).isEqualTo("signup");
    }
}
