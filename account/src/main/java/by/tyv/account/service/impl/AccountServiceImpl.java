package by.tyv.account.service.impl;

import by.tyv.account.model.bo.OperationCash;
import by.tyv.account.service.AccountService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {
    @Override
    public Mono<Void> cashOperation(OperationCash operationCash) {
        return null;
    }
}
