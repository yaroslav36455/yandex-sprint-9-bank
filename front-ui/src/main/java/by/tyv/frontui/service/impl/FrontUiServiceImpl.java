package by.tyv.frontui.service.impl;

import by.tyv.frontui.enums.CurrencyCode;
import by.tyv.frontui.exception.ServiceException;
import by.tyv.frontui.model.bo.SignUpForm;
import by.tyv.frontui.service.AccountService;
import by.tyv.frontui.service.FrontUiService;
import by.tyv.frontui.service.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FrontUiServiceImpl implements FrontUiService {
    private final AccountService accountService;
    private final TokenProvider tokenProvider;

    @Override
    public Mono<Rendering> buildMainPage() {
        return tokenProvider.getCurrentUsername().flatMap(username -> Mono.zip(
                        Mono.from(accountService.getUserAccounts(username).collectList()),
                        Mono.from(accountService.getUsers().collectList()))
                .map(tuple -> Rendering.view("main")
                        .modelAttribute("accounts", tuple.getT1())
                        .modelAttribute("currency", List.of(CurrencyCode.values()))
                        .modelAttribute("users", tuple.getT2()
                                .stream().filter(user -> !Objects.equals(user.getLogin(), username)).toList())
                        .modelAttribute("currentUser", tuple.getT2()
                                .stream().filter(user -> Objects.equals(user.getLogin(), username)).findFirst()
                                .orElseThrow(() -> new ServiceException("Не найден текущий пользователь")))
                        .build()));
    }

    @Override
    public Mono<Rendering> signUp(SignUpForm signUpForm) {
        return accountService.createUser(signUpForm)
                .thenReturn(Rendering.redirectTo("/oauth2/authorization/user-token-ac").build())
                .onErrorResume(ServiceException.class, e ->
                        Mono.just(Rendering.view("signup")
                                .modelAttribute("name", signUpForm.getName())
                                .modelAttribute("login", signUpForm.getLogin())
                                .modelAttribute("birthDate", signUpForm.getBirthDate())
                                .modelAttribute("errors", List.of(e.getMessage()))
                                .build()
                        )
                );
    }

    @Override
    public Mono<Rendering> updatePassword(String login, String password, String confirmPassword) {
        return accountService.updatePassword(login, password, confirmPassword)
                .thenReturn(Rendering.redirectTo("/main").build())
                .onErrorResume(ServiceException.class, e ->
                        tokenProvider.getCurrentUsername().flatMap(username -> Mono.zip(
                                        Mono.from(accountService.getUserAccounts(username).collectList()),
                                        Mono.from(accountService.getUsers().collectList()))
                                .map(tuple -> Rendering.view("main")
                                        .modelAttribute("passwordErrors", e.getMessage())
                                        .modelAttribute("accounts", tuple.getT1())
                                        .modelAttribute("currency", List.of(CurrencyCode.values()))
                                        .modelAttribute("users", tuple.getT2()
                                                .stream().filter(user -> !Objects.equals(user.getLogin(), username)).toList())
                                        .modelAttribute("currentUser", tuple.getT2()
                                                .stream().filter(user -> Objects.equals(user.getLogin(), username)).findFirst()
                                                .orElseThrow(() -> new ServiceException("Не найден текущий пользователь")))
                                        .build()))
                );
    }

    @Override
    public Mono<Rendering> updateAccounts(String login, String name, LocalDate birthdate, List<CurrencyCode> accounts) {
        return accountService.updateAccounts(login, name, birthdate, accounts)
                .thenReturn(Rendering.redirectTo("/main").build());
    }
}
