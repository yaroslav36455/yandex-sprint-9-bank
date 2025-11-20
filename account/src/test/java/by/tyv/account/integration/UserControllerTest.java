package by.tyv.account.integration;

import by.tyv.account.SpringBootIntegrationTest;
import by.tyv.account.model.bo.SignUpForm;
import by.tyv.account.model.dto.ErrorResponseDto;
import by.tyv.account.model.dto.SignUpFormDto;
import by.tyv.account.model.entity.UserEntity;
import by.tyv.account.repository.UserRepository;
import by.tyv.account.util.TestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@Disabled
public class UserControllerTest extends SpringBootIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Sql({"/sql/clean.sql", "/sql/insert_users.sql"})
    @DisplayName("GET /user, успешное чтение всех пользователей")
    public void getUsers() throws Exception {
        webClient.mutateWith(mockJwt().jwt(jwt -> jwt
                        .claim("sub", "some-subject")
                        .claim("client_id", "some-client-id")
                        .claim("scope", "internal_call")))
                .get().uri(fromPath("/user").toUriString())
                .header(ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(TestUtils.readResource("json/user_response.json"));
    }

    @Test
    @Sql({"/sql/clean.sql"})
    @DisplayName("GET /user, успешное чтение всех пользователей, пользователи отсутствуют")
    public void getUsersNoAnyUser() {
        webClient.mutateWith(mockJwt().jwt(jwt -> jwt
                        .claim("sub", "some-subject")
                        .claim("client_id", "some-client-id")
                        .claim("scope", "internal_call")))
                .get().uri(fromPath("/user").toUriString())
                .header(ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("[]");
    }

    @Test
    @Sql({"/sql/clean.sql"})
    @DisplayName("POST /signup, успешное создание нового пользователя, возвращение статуса 200")
    public void createNewUser() {
        SignUpFormDto signUpFormDto = new SignUpFormDto()
                .setName("Maria")
                .setLogin("someLogin_1")
                .setPassword("somePassword_1")
                .setConfirmPassword("somePassword_1")
                .setBirthDate(LocalDate.of(1988, 8, 12));
        UserEntity expecetdUserEntity = new UserEntity()
                .setId(1L)
                .setName("Maria")
//                .setLogin("someLogin_1")
//                .setPassword("somePassword_1")
                .setBirthDate(LocalDate.of(1988, 8, 12));

        webClient.post().uri(fromPath("/signup").toUriString())
                .bodyValue(signUpFormDto)
                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isCreated();

        StepVerifier.create(userRepository.findAll().collectList())
                .assertNext(userEntities -> {
                    Assertions.assertThat(userEntities)
                            .singleElement()
                            .usingRecursiveComparison()
                            .ignoringFields("createdAt", "password")
                            .isEqualTo(expecetdUserEntity);
                    Assertions.assertThat(userEntities)
                            .singleElement()
                            .satisfies(userEntity -> {
                                        Assertions.assertThat(userEntity.getId()).isNotNull();
                                        Assertions.assertThat(userEntity.getCreatedAt()).isNotNull();
//                                        Assertions.assertThat(userEntity.getPassword()).isNotNull();
                                    }
                            );
                })
                .verifyComplete();
    }

    @Test
    @Sql({"/sql/clean.sql", "/sql/insert_user_for_duplication.sql"})
    @DisplayName("POST /signup, попытка создания нового пользователя, пользователь с таким логином уже существует")
    public void createNewUserButUserAlreadyExists() {
        SignUpForm signUpForm = new SignUpForm()
                .setName("Maria")
                .setLogin("someLogin_1")
                .setPassword("somePassword_2")
                .setConfirmPassword("somePassword_2")
                .setBirthDate(LocalDate.of(1989, 9, 13));

        webClient.post().uri(fromPath("/signup").toUriString())
                .bodyValue(signUpForm)
                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorResponseDto.class)
                .consumeWith(result -> {
                    Assertions.assertThat(result.getResponseBody()).isNotNull();
                    Assertions.assertThat(result.getResponseBody().getErrorMessage()).isEqualTo("Пользователь с login=someLogin_1 уже существует");
                });
    }

    @Test
    @Sql("/sql/clean.sql")
    @DisplayName("POST /signup, попытка создания нового пользователя, тело не проходит валидацию из-за несовпадения паролей")
    public void createNewUserButUserHasIllegalCreatedFields() {
        SignUpForm  signUpForm = new SignUpForm()
                .setName("Maria")
                .setLogin("someLogin_1")
                .setPassword("somePassword_2")
                .setConfirmPassword("somePassword_thatIsNotEqualsToPassword")
                .setBirthDate(LocalDate.of(1989, 9, 13));

        webClient.post().uri(fromPath("/signup").toUriString())
                .bodyValue(signUpForm)
                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorResponseDto.class)
                .consumeWith(result -> {
                    Assertions.assertThat(result.getResponseBody()).isNotNull();
                    Assertions.assertThat(result.getResponseBody().getErrorMessage()).isEqualTo("Проверочный пароль не совпадает");
                });
    }
}
