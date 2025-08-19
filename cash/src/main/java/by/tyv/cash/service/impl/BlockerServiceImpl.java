package by.tyv.cash.service.impl;

import by.tyv.cash.model.dto.BlockerResponseDto;
import by.tyv.cash.model.dto.OperationCashRequestDto;
import by.tyv.cash.service.BlockerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class BlockerServiceImpl implements BlockerService {
    private final WebClient webClient;

    public BlockerServiceImpl(@Value("${clients.blocker-service.url}") String blockerServiceUrl,
                              WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(blockerServiceUrl).build();
    }

    @Override
    public Mono<Boolean> isAvailableOperation(String login, OperationCashRequestDto operationCashRequestDto) {
        return webClient.post().uri(uriBuilder -> uriBuilder.path("/operations/available").build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(operationCashRequestDto), OperationCashRequestDto.class)
                .retrieve()
                .bodyToMono(BlockerResponseDto.class)
                .map(BlockerResponseDto::isAvailable)
                .doOnError(throwable -> log.error("Ошибка проверки блокировки операции, login:{}, request:{}",
                        login, operationCashRequestDto, throwable));
    }
}
