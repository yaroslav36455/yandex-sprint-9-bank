package by.tyv.exchange.integration;


import by.tyv.exchange.SpringBootIntegrationTest;
import by.tyv.exchange.enums.CurrencyCode;
import by.tyv.exchange.model.dto.ExchangeRateRequestDto;
import by.tyv.exchange.model.dto.ExchangeRateResponseDto;
import by.tyv.exchange.model.entity.ExchangeRateEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.web.util.UriComponentsBuilder.fromPath;

public class ExchangeControllerTest extends SpringBootIntegrationTest {

    @Test
    @DisplayName("GET /api/rates - эндпоинт получения курсов валют")
    @Sql(scripts = {"/sql/clean.sql", "/sql/insert_exchange_rates.sql"})
    public void getApiRates() {
        List<ExchangeRateResponseDto> expectedList = List.of(
                new ExchangeRateResponseDto("Российский Рубль", new BigDecimal("1.22"), CurrencyCode.RUB),
                new ExchangeRateResponseDto("Белорусский Рубль", new BigDecimal("55.50"), CurrencyCode.BYN),
                new ExchangeRateResponseDto("Иранский Риал", new BigDecimal("99.10"), CurrencyCode.IRR),
                new ExchangeRateResponseDto("Китайский Юань", new BigDecimal("77.40"), CurrencyCode.CNY),
                new ExchangeRateResponseDto("Индийская Рупия", new BigDecimal("11.78"), CurrencyCode.INR)
        );

        webClient.get()
                .uri(fromPath("/api/rates").toUriString())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ExchangeRateResponseDto.class)
                .isEqualTo(expectedList);
    }

    @Test
    @DisplayName("POST /api/update - вставка новых курсов валют")
    @Sql(scripts = {"/sql/clean.sql"})
    public void postRatesInsert() {
        List<ExchangeRateRequestDto> requestDto = List.of(
                new ExchangeRateRequestDto(CurrencyCode.RUB, new BigDecimal("1.11")),
                new ExchangeRateRequestDto(CurrencyCode.BYN, new BigDecimal("2.22")),
                new ExchangeRateRequestDto(CurrencyCode.IRR, new BigDecimal("3.33")),
                new ExchangeRateRequestDto(CurrencyCode.CNY, new BigDecimal("4.44")),
                new ExchangeRateRequestDto(CurrencyCode.INR, new BigDecimal("5.55"))
        );
        List<ExchangeRateEntity> expectedEntities = List.of(
                new ExchangeRateEntity(){{setCode(CurrencyCode.RUB.toString()); setRate(new BigDecimal("1.11"));}},
                new ExchangeRateEntity(){{setCode(CurrencyCode.BYN.toString()); setRate(new BigDecimal("2.22"));}},
                new ExchangeRateEntity(){{setCode(CurrencyCode.IRR.toString()); setRate(new BigDecimal("3.33"));}},
                new ExchangeRateEntity(){{setCode(CurrencyCode.CNY.toString()); setRate(new BigDecimal("4.44"));}},
                new ExchangeRateEntity(){{setCode(CurrencyCode.INR.toString()); setRate(new BigDecimal("5.55"));}}
        );

        webClient.post()
                .uri(fromPath("/api/update").toUriString())
                .body(Mono.just(requestDto), ExchangeRateRequestDto.class)
                .exchange()
                .expectStatus().isOk();

        StepVerifier.create(exchangeRateRepository.findAll().collectList())
                .assertNext(exchangeRateEntities -> Assertions.assertThat(exchangeRateEntities)
                        .usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(expectedEntities))
                .expectNextCount(requestDto.size());
    }

    @Test
    @DisplayName("POST /api/update - обновление курсов валют")
    @Sql(scripts = {"/sql/clean.sql", "/sql/insert_exchange_rates.sql"})
    public void postRatesUpdate() {
        List<ExchangeRateRequestDto> requestDto = List.of(
                new ExchangeRateRequestDto(CurrencyCode.RUB, new BigDecimal("1.11")),
                new ExchangeRateRequestDto(CurrencyCode.BYN, new BigDecimal("2.22")),
                new ExchangeRateRequestDto(CurrencyCode.IRR, new BigDecimal("3.33")),
                new ExchangeRateRequestDto(CurrencyCode.CNY, new BigDecimal("4.44")),
                new ExchangeRateRequestDto(CurrencyCode.INR, new BigDecimal("5.55"))
        );
        List<ExchangeRateEntity> expectedEntities = List.of(
                new ExchangeRateEntity(){{setCode(CurrencyCode.RUB.toString()); setRate(new BigDecimal("1.11"));}},
                new ExchangeRateEntity(){{setCode(CurrencyCode.BYN.toString()); setRate(new BigDecimal("2.22"));}},
                new ExchangeRateEntity(){{setCode(CurrencyCode.IRR.toString()); setRate(new BigDecimal("3.33"));}},
                new ExchangeRateEntity(){{setCode(CurrencyCode.CNY.toString()); setRate(new BigDecimal("4.44"));}},
                new ExchangeRateEntity(){{setCode(CurrencyCode.INR.toString()); setRate(new BigDecimal("5.55"));}}
        );

        webClient.post()
                .uri(fromPath("/api/update").toUriString())
                .body(Mono.just(requestDto), ExchangeRateRequestDto.class)
                .exchange()
                .expectStatus().isOk();

        StepVerifier.create(exchangeRateRepository.findAll().collectList())
                .assertNext(exchangeRateEntities -> Assertions.assertThat(exchangeRateEntities)
                        .usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(expectedEntities))
                .expectNextCount(requestDto.size());
    }
}
