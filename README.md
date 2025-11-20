## Функциональность

#### Микросервисное приложение «Банк» — это приложение с веб-интерфейсом, которое позволяет пользователю (клиенту банка):
* регистрироваться в системе по логину и паролю (заводить аккаунт);
* добавлять счета в различных валютах;
* класть виртуальные деньги на счёт и снимать их;
* переводить деньги между своими счетами с учётом конвертации в различные валюты;
* переводить деньги на другой счёт с учётом конвертации в различные валюты.

#### Приложение состоит из следующих микросервисов:
* фронта (Front UI);
* сервиса аккаунтов (Accounts);
* сервиса обналичивания денег (Cash);
* сервиса перевода денег между счетами одного или двух аккаунтов (Transfer);
* сервиса конвертации валют (Exchange);
* сервиса генерации курсов валют (Exchange Generator);
* сервиса блокировки подозрительных операций (Blocker);
* сервиса уведомлений (Notifications).

#### Приложение работает в Docker контейнерах следующих образов
* yandex-sprint-9-gateway
* yandex-sprint-9-keycloak
* yandex-sprint-9-consul
* yandex-sprint-9-account
* yandex-sprint-9-blocker
* yandex-sprint-9-cash
* yandex-sprint-9-exchange
* yandex-sprint-9-exchange-generator
* yandex-sprint-9-front-ui
* yandex-sprint-9-notification
* yandex-sprint-9-transfer
* yandex-sprint-9-account-postgres
* yandex-sprint-9-transfer-postgres
* yandex-sprint-9-cash-postgres
* yandex-sprint-9-exchange-postgres

## Требования
* Docker 28.2.x

## 1) Сборка

### Linux:
```bash
gradle build
```

_Если отсутствует Gradle в системе, то вызывать `./gradlew` вместо `gradle`_.

### Если контейнер Keycloak запускается локально, то необходимо добавить маппинг IP-адресов:
Файл `/etc/hosts`
```bash
127.0.0.1 yandex-sprint-8-keycloak
```

### Windows:
```bash
gradle build
```

### Если контейнер Keycloak запускается локально, то необходимо добавить маппинг IP-адресов:
Файл `C:\Windows\System32\drivers\etc\hosts`
```bash
127.0.0.1 yandex-sprint-8-keycloak
```


## 2) Настройка Keycloak

Сервис запускается на localhost:8080
login:admin
password:admin
(параметры можно переопределить в файле `.env`)
Для работы сервисов необходимо создать в realm c именем bank-realm (имя можно переопределить в переменной `YANDEX_SPRINT_9_KEYCLOAK_REALM`) три клиента.

```bash
docker compose up --build yandex-sprint-9-keycloak
```

#### 1. Клиент для межсервисного общения (технический токен)
* Client ID = service-client-id
* Client authentication = ON
* Standard flow = ON
* Service accounts roles = ON
* Создать Client Scope с именем `internal_call`, указав ему в настройках `Include in token scope=ON` и добавить его этому клиенту
* Установить секретный ключ в переменную окружения `YANDEX_SPRINT_9_KEYCLOAK_INTERNAL_CALL_CLIENT_SECRET`.

#### 2. Клиент для создания и управления пользователями (технический токен)
* Client ID = user-management-client-id
* Client authentication = ON
* Standard flow = ON
* Service accounts roles = ON
* Добавить роли в `Service accounts roles` с названиями `manage-users`, `view-users`, `query-users`
* Установить секретный ключ в переменную окружения `YANDEX_SPRINT_9_KEYCLOAK_USER_MANAGEMENT_CLIENT_SECRET`.

#### 3. Пользовательский клиент
* Client ID = user-client-id
* Client authentication = OFF
* Standard flow = ON
* Valid redirect URIs = http://localhost:9090/login/oauth2/code/user-token-ac
* Valid post logout redirect URIs = http://localhost:9090/
* Создать Client Scope с именем `user`, указав ему в настройках `Include in token scope=ON` и добавить его этому клиенту

**!!! В файле `.env` можно переопределить некоторые другие переменные такие как логины и пароли к базе.**


## Запуск

```bash
docker compose up --build
```