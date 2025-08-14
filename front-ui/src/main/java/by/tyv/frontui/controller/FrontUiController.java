package by.tyv.frontui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    ж) GET "/signup" - страница регистрации нового пользователя
    Возвращает:
                шаблон "signup.html"
    */
    @GetMapping("/signup")
    public Mono<String> getSignupPage() {
        return Mono.just("signup");
    }
}
