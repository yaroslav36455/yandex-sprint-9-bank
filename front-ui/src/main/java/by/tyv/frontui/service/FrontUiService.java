package by.tyv.frontui.service;

import by.tyv.frontui.enums.CurrencyCode;
import by.tyv.frontui.model.bo.SignUpForm;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

public interface FrontUiService {
    Mono<Rendering> buildMainPage();
    Mono<Rendering> signUp(SignUpForm signUpForm);
    Mono<Rendering> updatePassword(String login, String password, String confirmPassword);
    Mono<Rendering> updateAccounts(String login, String name, LocalDate birthdate, List<CurrencyCode> accounts);
}
