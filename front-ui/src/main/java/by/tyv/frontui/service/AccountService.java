package by.tyv.frontui.service;

import by.tyv.frontui.model.Account;
import by.tyv.frontui.model.User;
import reactor.core.publisher.Flux;

public interface AccountService {
    Flux<Account> getAccounts();
    Flux<User> getUsers();
}
