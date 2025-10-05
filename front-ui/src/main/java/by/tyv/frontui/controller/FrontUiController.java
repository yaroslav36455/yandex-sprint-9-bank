package by.tyv.frontui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.result.view.RedirectView;
import reactor.core.publisher.Mono;

@Controller
public class FrontUiController {

    // а) GET "/" - редирект на "/main"
    @GetMapping("/")
    public Mono<RedirectView> getRedirectMainPage() {
        return Mono.just(new RedirectView("/main"));
    }

    /*
	б) GET "/main" - главная страница
		Возвращает:
            шаблон "main.html"
            используется модель для заполнения шаблона:
                "login" - строка с логином пользователя
                "name" - строка с фамилией и именем пользователя
                "birthdate" - LocalDate с датой рождения пользователя
                "accounts" - список всех зарегистрированных пользователей:
                    "currency" - enum валюта:
                        "title" - название валюты
                        "name()" - код валюты
                    "value" - сумма на счету пользователя в этой валюте
                    "exists" - true, если у пользователя есть счет в этой валюте, false, если нет
                "currency" - список всех доступных валют:
                    "title" - название валюты
                    "name()" - код валюты
                "users" - список всех пользователей:
                    "login" - логин пользователя
                    "name" - фамилия и имя пользователя
                "passwordErrors" - список ошибок при смене пароля (null, если не выполнялась смена пароля)
                "userAccountsErrors" - список ошибок при редактировании настроек аккаунта (null, если не выполнялось редактирование)
                "cashErrors" - список ошибок при внесении/снятии денег (null, если не выполнялось внесение/снятие)
                "transferErrors" - список ошибок при переводе между своими счетами (null, если не выполнялся перевод)
                "transferOtherErrors" - список ошибок при переводе на счет другого пользователя (null, если не выполнялся перевод)
    */
    @GetMapping("/main")
    public Mono<String> getMainPage(Model model) {
        return Mono.just("main");
    }

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
    public Mono<RedirectView> postCash(@PathVariable("login") String login/*,
                                       @ModelAttribute CashRequestDto cashRequestDto*/) {
//        return cashService.cashOperation(login, cashRequestDto)
//                .thenReturn(new RedirectView("/main"));
        return Mono.just(new RedirectView("/main"));
    }

    /*
    ж) GET "/signup" - страница регистрации нового пользователя
    Возвращает:
                шаблон "signup.html"
    */
    @GetMapping("/signup")
    public Mono<String> getSignupPage() {
        return Mono.just("signup");
    }


    /*
    е) POST "/user/{login}/transfer" - эндпоинт перевода денег между своими счетами и перевода денег на счёт другого пользователя (один эндпоинт для того и другого, записывает список ошибок, если есть, в transferErrors или в transferOtherErrors)
        Параметры:
            login - логин пользователя.
            from_currency - строка с валютой счета, с которого переводятся деньги.
            to_currency - строка с валютой счета, на который переводятся деньги.
            value - сумма внесения/снятия.
            to_login - логин пользователя, которому переводятся деньги.
        Возвращает:
            редирект на "/main"
    */
    @PostMapping("/user/{login}/transfer")
    public Mono<RedirectView> postTransferMoney(@PathVariable("login") String login) {
        return Mono.just(new RedirectView("/main"));
    }
}
