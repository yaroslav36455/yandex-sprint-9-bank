package by.tyv.frontui.service.impl;

import by.tyv.frontui.model.Account;
import by.tyv.frontui.model.User;
import by.tyv.frontui.service.AccountService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class AccountServiceImpl implements AccountService {
    @Override
    public Flux<Account> getAccounts() {
        return null;
    }

    @Override
    public Flux<User> getUsers() {
        return null;
    }
}
