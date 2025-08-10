package by.tyv.exchange.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.result.view.RedirectView;
import reactor.core.publisher.Mono;

@RestController
public class ExchangeController {
    /*
    и) GET "/api/rates" - эндпоинт получения курсов валют
    Возвращает JSON со списком курсов валют:
                title - название валюты
                name - код валюты
                value - курс валюты по отношению к рублю (для рубля 1)
    */
    @PostMapping("/api/rates")
    public Mono<RedirectView> postMainPage() {
        return Mono.just(new RedirectView("/main"));
    }
}
