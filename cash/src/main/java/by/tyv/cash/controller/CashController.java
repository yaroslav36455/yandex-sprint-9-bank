package by.tyv.cash.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.result.view.RedirectView;
import reactor.core.publisher.Mono;

@RestController
public class CashController {

    /*
    д) POST "/user/{login}/сash" - эндпоинт внесения/снятия денег (записывает список ошибок, если есть, в cashErrors)
        Параметры:
            login - логин пользователя
            currency - строка с валютой
            value - сумма внесения/снятия
            action - действие (enum PUT иди GET)
        Возвращает:
            редирект на "/main"
    */
    @PostMapping("/user/{login}/сash")
    public Mono<RedirectView> postCash(@PathVariable("login") String login) {
        return Mono.just(new RedirectView("/main"));
    }
}
