package by.tyv.account.service;

import by.tyv.account.model.bo.OperationCash;
import by.tyv.account.model.bo.OperationTransfer;
import reactor.core.publisher.Mono;

public interface AccountService {
    Mono<Void> cashOperation(OperationCash operationCash);
    Mono<Void> transferOperation(OperationTransfer operationTransfer);
}
