package by.tyv.account.controller;

import by.tyv.account.mapper.UserMapper;
import by.tyv.account.model.dto.PasswordUpdateDto;
import by.tyv.account.model.dto.SignUpFormDto;
import by.tyv.account.model.dto.UserInfoDto;
import by.tyv.account.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/user")
    public Flux<UserInfoDto> getUsers() {
        return userService.getUsers()
                .map(userMapper::toDTO);
    }

    @PostMapping("/user/{login}/editPassword")
    public Mono<Void> postEditPassword(@PathVariable("login") String login,
                                       @RequestBody PasswordUpdateDto passwordUpdate) {
        return userService.updatePassword(login, passwordUpdate.getPassword(), passwordUpdate.getConfirmPassword());
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
    public Mono<Void> postEditAccount(@PathVariable("login") String login) {
        return Mono.empty();
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> postSignup(@RequestBody SignUpFormDto form) {
        return userService.signUp(userMapper.toBO(form));
    }
}
