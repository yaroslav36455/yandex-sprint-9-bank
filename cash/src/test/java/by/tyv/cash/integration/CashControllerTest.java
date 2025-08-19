package by.tyv.cash.integration;

import by.tyv.cash.SpringBootIntegrationTest;
import by.tyv.cash.enums.Action;
import by.tyv.cash.enums.CurrencyCode;
import by.tyv.cash.model.dto.BlockerResponseDto;
import by.tyv.cash.model.dto.CashRequestDto;
import by.tyv.cash.model.dto.ErrorResponseDto;
import by.tyv.cash.model.dto.OperationCashRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.springframework.web.util.UriComponentsBuilder.fromPath;

public class CashControllerTest extends SpringBootIntegrationTest {

    @Test
    @DisplayName("POST /cash/{login} - успешное внесение денег на счёт")
    public void postCashPutSuccess() throws JsonProcessingException {
        String login = "username";
        CashRequestDto cashRequestDto = new CashRequestDto(CurrencyCode.BYN, new BigDecimal("10.00"), Action.PUT);

        OperationCashRequestDto operationCashRequestDto = new OperationCashRequestDto()
                .setCurrency(CurrencyCode.BYN)
                .setAmount(new BigDecimal("10.00"))
                .setAction(Action.PUT);
        BlockerResponseDto blockerResponseDto = new BlockerResponseDto(true);
        wireMockServerBlocker.stubFor(WireMock.post(WireMock.urlPathEqualTo("/operations/available"))
                .withHeader(HttpHeaders.CONTENT_TYPE, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withRequestBody(WireMock.equalToJson(objectMapper.writeValueAsString(operationCashRequestDto)))
                .willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(blockerResponseDto))));

        wireMockServerAccount.stubFor(WireMock.post(WireMock.urlPathEqualTo(fromPath("/account/{login}/operation/cash").buildAndExpand(login).toUriString()))
                .withHeader(HttpHeaders.CONTENT_TYPE, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withRequestBody(WireMock.equalToJson(objectMapper.writeValueAsString(operationCashRequestDto)))
                .willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

        wireMockServerNotification.stubFor(WireMock.post(WireMock.urlPathEqualTo(fromPath("/notifications/{login}/message").buildAndExpand(login).toUriString()))
                .withRequestBody(WireMock.equalTo("Операция выполнена успешно"))
                .willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())));


        webClient.post()
                .uri(fromPath("/cash/{login}").buildAndExpand(login).toUriString())
                .body(Mono.just(cashRequestDto), CashRequestDto.class)
                .exchange()
                .expectStatus().isOk();

        wireMockServerBlocker.verify(1, WireMock.postRequestedFor(WireMock.urlPathEqualTo("/operations/available"))
                .withHeader(HttpHeaders.CONTENT_TYPE, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withRequestBody(WireMock.equalToJson(objectMapper.writeValueAsString(operationCashRequestDto))));

        wireMockServerAccount.verify(1, WireMock.postRequestedFor(WireMock.urlPathEqualTo(fromPath("/account/{login}/operation/cash").buildAndExpand(login).toUriString()))
                .withHeader(HttpHeaders.CONTENT_TYPE, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withRequestBody(WireMock.equalToJson(objectMapper.writeValueAsString(operationCashRequestDto))));

        wireMockServerNotification.verify(1, WireMock.postRequestedFor(WireMock.urlPathEqualTo(fromPath("/notifications/{login}/message").buildAndExpand(login).toUriString()))
                .withRequestBody(WireMock.equalTo("Операция выполнена успешно")));
    }

    @Test
    @DisplayName("POST /cash/{login} - успешное снятие денег со счёта")
    public void postCashGetSuccess() throws JsonProcessingException {
        String login = "username";
        CashRequestDto cashRequestDto = new CashRequestDto(CurrencyCode.BYN, new BigDecimal("10.00"), Action.GET);

        OperationCashRequestDto operationCashRequestDto = new OperationCashRequestDto()
                .setCurrency(CurrencyCode.BYN)
                .setAmount(new BigDecimal("10.00"))
                .setAction(Action.GET);
        BlockerResponseDto blockerResponseDto = new BlockerResponseDto(true);
        wireMockServerBlocker.stubFor(WireMock.post(WireMock.urlPathEqualTo("/operations/available"))
                .withHeader(HttpHeaders.CONTENT_TYPE, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withRequestBody(WireMock.equalToJson(objectMapper.writeValueAsString(operationCashRequestDto)))
                .willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(blockerResponseDto))));

        wireMockServerAccount.stubFor(WireMock.post(WireMock.urlPathEqualTo(fromPath("/account/{login}/operation/cash").buildAndExpand(login).toUriString()))
                .withHeader(HttpHeaders.CONTENT_TYPE, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withRequestBody(WireMock.equalToJson(objectMapper.writeValueAsString(operationCashRequestDto)))
                .willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

        wireMockServerNotification.stubFor(WireMock.post(WireMock.urlPathEqualTo(fromPath("/notifications/{login}/message").buildAndExpand(login).toUriString()))
                .withRequestBody(WireMock.equalTo("Операция выполнена успешно"))
                .willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())));


        webClient.post()
                .uri(fromPath("/cash/{login}").buildAndExpand(login).toUriString())
                .body(Mono.just(cashRequestDto), CashRequestDto.class)
                .exchange()
                .expectStatus().isOk();

        wireMockServerBlocker.verify(1, WireMock.postRequestedFor(WireMock.urlPathEqualTo("/operations/available"))
                .withHeader(HttpHeaders.CONTENT_TYPE, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withRequestBody(WireMock.equalToJson(objectMapper.writeValueAsString(operationCashRequestDto))));

        wireMockServerAccount.verify(1, WireMock.postRequestedFor(WireMock.urlPathEqualTo(fromPath("/account/{login}/operation/cash").buildAndExpand(login).toUriString()))
                .withHeader(HttpHeaders.CONTENT_TYPE, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withRequestBody(WireMock.equalToJson(objectMapper.writeValueAsString(operationCashRequestDto))));

        wireMockServerNotification.verify(1, WireMock.postRequestedFor(WireMock.urlPathEqualTo(fromPath("/notifications/{login}/message").buildAndExpand(login).toUriString()))
                .withRequestBody(WireMock.equalTo("Операция выполнена успешно")));
    }

    @Test
    @DisplayName("POST /cash/{login} - блокировщик отклоняет операцию")
    public void postCashPutBlockerDenied() throws JsonProcessingException {
        String login = "username";
        CashRequestDto cashRequestDto = new CashRequestDto(CurrencyCode.BYN, new BigDecimal("10.00"), Action.PUT);

        OperationCashRequestDto operationCashRequestDto = new OperationCashRequestDto()
                .setCurrency(CurrencyCode.BYN)
                .setAmount(new BigDecimal("10.00"))
                .setAction(Action.PUT);
        BlockerResponseDto blockerResponseDto = new BlockerResponseDto(false);
        wireMockServerBlocker.stubFor(WireMock.post(WireMock.urlPathEqualTo("/operations/available"))
                .withHeader(HttpHeaders.CONTENT_TYPE, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withRequestBody(WireMock.equalToJson(objectMapper.writeValueAsString(operationCashRequestDto)))
                .willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(blockerResponseDto))));

        wireMockServerAccount.stubFor(WireMock.post(WireMock.urlPathEqualTo(fromPath("/account/{login}/operation/cash").buildAndExpand(login).toUriString()))
                .withHeader(HttpHeaders.CONTENT_TYPE, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withRequestBody(WireMock.equalToJson(objectMapper.writeValueAsString(operationCashRequestDto)))
                .willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

        wireMockServerNotification.stubFor(WireMock.post(WireMock.urlPathEqualTo(fromPath("/notifications/{login}/message").buildAndExpand(login).toUriString()))
                .withRequestBody(WireMock.equalTo("Операция запрещена"))
                .willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())));


        webClient.post()
                .uri(fromPath("/cash/{login}").buildAndExpand(login).toUriString())
                .body(Mono.just(cashRequestDto), CashRequestDto.class)
                .exchange()
                .expectStatus().isOk();

        wireMockServerBlocker.verify(1, WireMock.postRequestedFor(WireMock.urlPathEqualTo("/operations/available"))
                .withHeader(HttpHeaders.CONTENT_TYPE, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withRequestBody(WireMock.equalToJson(objectMapper.writeValueAsString(operationCashRequestDto))));

        wireMockServerAccount.verify(0, WireMock.postRequestedFor(WireMock.urlPathEqualTo(fromPath("/account/{login}/operation/cash").buildAndExpand(login).toUriString())));

        wireMockServerNotification.verify(1, WireMock.postRequestedFor(WireMock.urlPathEqualTo(fromPath("/notifications/{login}/message").buildAndExpand(login).toUriString()))
                .withRequestBody(WireMock.equalTo("Операция запрещена")));
    }

    @Test
    @DisplayName("POST /cash/{login} - недостаточно денег на счету")
    public void postCashGetNotEnoughMoney() throws JsonProcessingException {
        String login = "username";
        CashRequestDto cashRequestDto = new CashRequestDto(CurrencyCode.BYN, new BigDecimal("10.00"), Action.GET);

        OperationCashRequestDto operationCashRequestDto = new OperationCashRequestDto()
                .setCurrency(CurrencyCode.BYN)
                .setAmount(new BigDecimal("10.00"))
                .setAction(Action.GET);
        BlockerResponseDto blockerResponseDto = new BlockerResponseDto(true);
        ErrorResponseDto errorResponseDto = new ErrorResponseDto("Недостаточно денег на счету");
        wireMockServerBlocker.stubFor(WireMock.post(WireMock.urlPathEqualTo("/operations/available"))
                .withHeader(HttpHeaders.CONTENT_TYPE, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withRequestBody(WireMock.equalToJson(objectMapper.writeValueAsString(operationCashRequestDto)))
                .willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(blockerResponseDto))));

        wireMockServerAccount.stubFor(WireMock.post(WireMock.urlPathEqualTo(fromPath("/account/{login}/operation/cash").buildAndExpand(login).toUriString()))
                .withHeader(HttpHeaders.CONTENT_TYPE, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withRequestBody(WireMock.equalToJson(objectMapper.writeValueAsString(operationCashRequestDto)))
                .willReturn(WireMock.aResponse().withStatus(HttpStatus.BAD_REQUEST.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(errorResponseDto))));

        wireMockServerNotification.stubFor(WireMock.post(WireMock.urlPathEqualTo(fromPath("/notifications/{login}/message").buildAndExpand(login).toUriString()))
                .withRequestBody(WireMock.equalTo("Ошибка операции: Недостаточно денег на счету"))
                .willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())));


        webClient.post()
                .uri(fromPath("/cash/{login}").buildAndExpand(login).toUriString())
                .body(Mono.just(cashRequestDto), CashRequestDto.class)
                .exchange()
                .expectStatus().isBadRequest();

        wireMockServerBlocker.verify(1, WireMock.postRequestedFor(WireMock.urlPathEqualTo("/operations/available"))
                .withHeader(HttpHeaders.CONTENT_TYPE, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withRequestBody(WireMock.equalToJson(objectMapper.writeValueAsString(operationCashRequestDto))));

        wireMockServerAccount.verify(1, WireMock.postRequestedFor(WireMock.urlPathEqualTo(fromPath("/account/{login}/operation/cash").buildAndExpand(login).toUriString()))
                .withHeader(HttpHeaders.CONTENT_TYPE, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withRequestBody(WireMock.equalToJson(objectMapper.writeValueAsString(operationCashRequestDto))));

        wireMockServerNotification.verify(1, WireMock.postRequestedFor(WireMock.urlPathEqualTo(fromPath("/notifications/{login}/message").buildAndExpand(login).toUriString()))
                .withRequestBody(WireMock.equalTo("Ошибка операции: Недостаточно денег на счету")));
    }

    @Test
    @DisplayName("POST /cash/{login} - ошибка отправки нотификации")
    public void postCashPutNotificationError() throws JsonProcessingException {
        String login = "username";
        CashRequestDto cashRequestDto = new CashRequestDto(CurrencyCode.BYN, new BigDecimal("10.00"), Action.PUT);

        OperationCashRequestDto operationCashRequestDto = new OperationCashRequestDto()
                .setCurrency(CurrencyCode.BYN)
                .setAmount(new BigDecimal("10.00"))
                .setAction(Action.PUT);
        BlockerResponseDto blockerResponseDto = new BlockerResponseDto(true);
        wireMockServerBlocker.stubFor(WireMock.post(WireMock.urlPathEqualTo("/operations/available"))
                .withHeader(HttpHeaders.CONTENT_TYPE, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withRequestBody(WireMock.equalToJson(objectMapper.writeValueAsString(operationCashRequestDto)))
                .willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(blockerResponseDto))));

        wireMockServerAccount.stubFor(WireMock.post(WireMock.urlPathEqualTo(fromPath("/account/{login}/operation/cash").buildAndExpand(login).toUriString()))
                .withHeader(HttpHeaders.CONTENT_TYPE, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withRequestBody(WireMock.equalToJson(objectMapper.writeValueAsString(operationCashRequestDto)))
                .willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

        wireMockServerNotification.stubFor(WireMock.post(WireMock.urlPathEqualTo(fromPath("/notifications/{login}/message").buildAndExpand(login).toUriString()))
                .withRequestBody(WireMock.equalTo("Операция выполнена успешно"))
                .willReturn(WireMock.aResponse().withStatus(HttpStatus.BAD_REQUEST.value())));


        webClient.post()
                .uri(fromPath("/cash/{login}").buildAndExpand(login).toUriString())
                .body(Mono.just(cashRequestDto), CashRequestDto.class)
                .exchange()
                .expectStatus().isOk();

        wireMockServerBlocker.verify(1, WireMock.postRequestedFor(WireMock.urlPathEqualTo("/operations/available"))
                .withHeader(HttpHeaders.CONTENT_TYPE, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withRequestBody(WireMock.equalToJson(objectMapper.writeValueAsString(operationCashRequestDto))));

        wireMockServerAccount.verify(1, WireMock.postRequestedFor(WireMock.urlPathEqualTo(fromPath("/account/{login}/operation/cash").buildAndExpand(login).toUriString()))
                .withHeader(HttpHeaders.CONTENT_TYPE, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withRequestBody(WireMock.equalToJson(objectMapper.writeValueAsString(operationCashRequestDto))));

        wireMockServerNotification.verify(1, WireMock.postRequestedFor(WireMock.urlPathEqualTo(fromPath("/notifications/{login}/message").buildAndExpand(login).toUriString()))
                .withRequestBody(WireMock.equalTo("Операция выполнена успешно")));
    }
}
