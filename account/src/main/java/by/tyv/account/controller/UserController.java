package by.tyv.account.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.result.view.RedirectView;
import reactor.core.publisher.Mono;

@RestController
public class UserController {
    /*
    в) POST "/user/{login}/editPassword" - эндпоинт смены пароля (записывает список ошибок, если есть, в passwordErrors)
        Параметры:
            login - логин пользователя
            password - новый пароль
            confirm_password - новый пароль второй раз
        Возвращает:
            редирект на "/main"
    */
    @PostMapping("/user/{login}/editPassword")
    public Mono<RedirectView> postEditPassword(@PathVariable("login") String login) {
        return Mono.just(new RedirectView("/main"));
    }

    /*
    г) POST "/user/{login}/editUserAccounts" - эндпоинт редактирования аккаунта (записывает список ошибок, если есть, в userAccountsErrors)
        Параметры:
            login - логин пользователя
            name - фамилия и имя пользователя
            birthdate - дата рождения пользователя (LocalDate)
            account - список строк с валютами пользователя, для которых у него есть счета
        Возвращает:
            редирект на "/main"
    */
    @PostMapping("/user/{login}/editUserAccounts")
    public Mono<RedirectView> postEditAccount(@PathVariable("login") String login) {
        return Mono.just(new RedirectView("/main"));
    }

    /*
    з) POST "/signup" - эндпоинт регистрации нового пользователя
        Параметры:
            login - логин пользователя
            password - пароль пользователя
            confirm_password - пароль пользователя второй раз
            name - фамилия и имя пользователя
            birthdate - дата рождения пользователя (LocalDate)
            @RequestParam("login") String login,
    Возвращает:
                редирект на "/main"
            В случае ошибок возвращает:
                шаблон "signup.html"
                используется модель для заполнения шаблона:
                    "login" - строка с логином пользователя
                    "name" - строка с фамилией и именем пользователя
                    "birthdate" - LocalDate с датой рождения пользователя
                    "accounts" - список всех зарегистрированных пользователей
                    "errors" - список ошибок при регистрации
    */
    @PostMapping("/signup")
    public Mono<RedirectView> postSignup() {
        return Mono.just(new RedirectView("/main"));
    }
}
