package by.tyv.cash.service.impl;

import by.tyv.cash.exception.ServiceException;
import by.tyv.cash.model.dto.ErrorResponseDto;
import by.tyv.cash.model.dto.OperationCashRequestDto;
import by.tyv.cash.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final WebClient webClient;

    public AccountServiceImpl(@Value("${clients.account-service.url}") String accountServiceUrl,
                              WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(accountServiceUrl).build();
    }

    @Override
    public Mono<Void> doOperation(String login, OperationCashRequestDto operationCashRequestDto) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.pathSegment("account", login, "operation", "cash").build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(operationCashRequestDto)
                .retrieve()
                .onStatus(status -> Objects.equals(status, HttpStatus.BAD_REQUEST),
                        resp -> resp.bodyToMono(ErrorResponseDto.class)
                                .defaultIfEmpty(new ErrorResponseDto("Неизвестная ошибка"))
                                .flatMap(err -> Mono.error(new ServiceException(err.getErrorMessage()))))
                .toBodilessEntity()
                .then()
                .doOnError(throwable -> log.error("Ошибка операции списания", throwable));
    }
}
