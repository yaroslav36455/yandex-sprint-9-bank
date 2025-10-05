package by.tyv.account;

import by.tyv.account.controller.AccountController;
import by.tyv.account.mapper.AccountMapperImpl;
import by.tyv.account.model.bo.OperationCash;
import by.tyv.account.model.bo.OperationTransfer;
import by.tyv.account.service.AccountService;
import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = AccountController.class)
@Import(AccountMapperImpl.class)
@AutoConfigureWebTestClient
public class BaseContractTest {
    @MockitoBean
    private AccountService accountService;

    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    public void beforeEach() {
        Mockito.doReturn(Mono.empty())
                .when(accountService)
                .cashOperation(Mockito.any(OperationCash.class));

        Mockito.doReturn(Mono.empty())
                .when(accountService)
                .transferOperation(Mockito.any(OperationTransfer.class));

        RestAssuredWebTestClient.webTestClient(webTestClient);
    }
}
