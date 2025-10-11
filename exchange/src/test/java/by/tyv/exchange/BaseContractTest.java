package by.tyv.exchange;

import by.tyv.exchange.controller.ExchangeController;
import by.tyv.exchange.enums.CurrencyCode;
import by.tyv.exchange.model.dto.ExchangeRateResponseDto;
import by.tyv.exchange.service.ExchangeService;
import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@WebFluxTest(controllers = ExchangeController.class)
@AutoConfigureWebTestClient
public abstract class BaseContractTest {
    @MockitoBean
    ExchangeService exchangeService;

    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    void setupRestAssured() {
        RestAssuredWebTestClient.webTestClient(webTestClient);

        Mockito.doReturn(Flux.just(
                new ExchangeRateResponseDto("Белорусский Рубль", CurrencyCode.BYN, new BigDecimal("123.45")),
                new ExchangeRateResponseDto("Российский Рубль",  CurrencyCode.RUB, new BigDecimal("98.76"))
        )).when(exchangeService).getRates();

        Mockito.doReturn(Mono.empty())
                .when(exchangeService)
                .update(Mockito.anyList());
    }
}

