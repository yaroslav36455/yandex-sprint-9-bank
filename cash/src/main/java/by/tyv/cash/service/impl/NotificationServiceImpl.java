package by.tyv.cash.service.impl;

import by.tyv.cash.exception.NotificationException;
import by.tyv.cash.model.dto.ErrorResponseDto;
import by.tyv.cash.service.NotificationService;
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
public class NotificationServiceImpl implements NotificationService {
    private final WebClient webClient;

    public NotificationServiceImpl(@Value("${clients.notification-service.url}") String notificationServiceUrl,
                                   WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(notificationServiceUrl).build();
    }

    @Override
    public Mono<Void> sendNotification(String login, String message) {
        return this.webClient.post()
                .uri(uriBuilder -> uriBuilder.pathSegment("notifications", login, "message").build())
                .contentType(MediaType.TEXT_PLAIN)
                .body(Mono.just(message), String.class)
                .retrieve()
                .onStatus(status -> !Objects.equals(status, HttpStatus.OK),
                        resp -> Mono.error(new NotificationException("Неизвестная ошибка")))
                .toBodilessEntity()
                .then()
                .doOnError(throwable -> log.error("Ошибка отправки нотификации", throwable));
    }
}
