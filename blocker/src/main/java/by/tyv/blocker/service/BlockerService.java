package by.tyv.blocker.service;

import by.tyv.blocker.model.dto.OperationCashRequestDto;
import reactor.core.publisher.Mono;

public interface BlockerService {
    Mono<Boolean> isAvailable(OperationCashRequestDto operationCashRequestDto);
}
