package by.tyv.accounts.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.result.view.RedirectView;
import reactor.core.publisher.Mono;

@RestController
public class AccountController {

    @PostMapping("/account/{login}/editPassword")
    public Mono<RedirectView> postEditPassword(@PathVariable("login") String login) {
        return Mono.just(new RedirectView("/main"));
    }
}
