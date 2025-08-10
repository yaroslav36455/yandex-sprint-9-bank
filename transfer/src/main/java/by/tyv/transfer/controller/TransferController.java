package by.tyv.transfer.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.result.view.RedirectView;
import reactor.core.publisher.Mono;

@RestController
public class TransferController {
    /*
    е) POST "/user/{login}/transfer" - эндпоинт перевода денег между своими счетами и перевода денег на счёт другого пользователя (один эндпоинт для того и другого, записывает список ошибок, если есть, в transferErrors или в transferOtherErrors)
        Параметры:
            login - логин пользователя
            from_currency - строка с валютой счета, с которого переводятся деньги
            to_currency - строка с валютой счета, на который переводятся деньги
            value - сумма внесения/снятия
            to_login - логин пользователя, которому переводятся деньги
        Возвращает:
            редирект на "/main"
    */
    @PostMapping("/user/{login}/transfer")
    public Mono<RedirectView> postTransferMoney(@PathVariable("login") String login) {
        return Mono.just( new RedirectView("/main"));
    }
}
