package by.tyv.cash.service;

import reactor.core.publisher.Mono;

public interface NotificationService {
    Mono<Void> sendNotification(String login, String message);
}
