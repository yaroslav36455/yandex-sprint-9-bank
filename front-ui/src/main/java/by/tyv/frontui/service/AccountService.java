package by.tyv.frontui.service;

import by.tyv.frontui.model.bo.Account;
import by.tyv.frontui.model.bo.SignUpForm;
import by.tyv.frontui.model.bo.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Flux<Account> getUserAccounts(String username);
    Flux<User> getUsers();
    Mono<Void> createUser(SignUpForm signUpForm);
    Mono<Void> updatePassword(String login, String password, String confirmPassword);
}
