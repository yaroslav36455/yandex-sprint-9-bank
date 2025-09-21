package by.tyv.account.service;

import by.tyv.account.model.bo.OperationCash;
import reactor.core.publisher.Mono;

public interface AccountService {
    Mono<Void> cashOperation(OperationCash operationCash);
}
