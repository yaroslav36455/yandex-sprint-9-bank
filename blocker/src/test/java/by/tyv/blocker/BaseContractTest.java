package by.tyv.blocker;

import by.tyv.blocker.contoroller.BlockerController;
import by.tyv.blocker.model.dto.OperationCashRequestDto;
import by.tyv.blocker.service.BlockerService;
import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = BlockerController.class)
@AutoConfigureWebTestClient
public class BaseContractTest {
    @MockitoBean
    private BlockerService blockerService;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void beforeEach() {
        Mockito.doReturn(Mono.just(true))
                .when(blockerService)
                .isAvailable(Mockito.any(OperationCashRequestDto.class));

        RestAssuredWebTestClient.webTestClient(webTestClient);
    }
}
