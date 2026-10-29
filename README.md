```markdown
# Client Accounting Service

Сервис для учёта клиентов компании "Лига Цифровой Экономики". 
Реализует REST API для создания, чтения, обновления и удаления клиентов с хранением данных в PostgreSQL.

## Стек

- Java 21
- Spring Boot 4.1.2 (WebMVC, Data JPA, Validation, Flyway)
- PostgreSQL 15
- Lombok
- Docker / Docker Compose

## Запуск

### Через Docker Compose

1. Убедитесь, что в корне проекта есть файл `.env` со следующими переменными:

```
POSTGRES_PASSWORD=secret
```

2. Запустите:

```bash
docker compose up -d --build
```

Сервис будет доступен по адресу `http://localhost:8081`.
PostgreSQL будет доступен на `localhost:5432` (база `client_accounts_db`, пользователь `postgres`).

### Локально (без Docker для приложения)

1. Поднимите PostgreSQL (можно только контейнер с БД: `docker compose up -d postgres-client-accounts`).
2. Настройте подключение в `application.yml` или через переменные окружения.
3. Запустите:

```bash
mvn spring-boot:run
```

Сервис будет доступен по адресу `http://localhost:8081`.

## API

Базовый путь: `/api/clients`

| Метод | Путь | Описание | Тело запроса | Ответ |
|---|---|---|---|---|
| POST | `/api/clients` | Создать клиента | `fullName`, `gender` | 201, `ClientAccountResponse` |
| GET | `/api/clients` | Получить всех клиентов | — | 200, `List<ClientAccountResponse>` |
| GET | `/api/clients/{id}` | Получить клиента по id | — | 200, `ClientAccountResponse` / 404 |
| PUT | `/api/clients/{id}` | Обновить клиента | `fullName`, `gender`, `status` | 200, `ClientAccountResponse` / 404 |
| DELETE | `/api/clients/{id}` | Удалить клиента | — | 204 / 404 |

### Пример запроса на создание

```json
POST /api/clients
Content-Type: application/json

{
  "fullName": "Лепс Григорий Григорьевич",
  "gender": "M"
}
```

### Модель клиента (ответ)

```json
{
  "id": 1,
  "fullName": "Лепс Григорий Григорьевич",
  "gender": "M",
  "status": "ACTIVE",
  "createDttm": "2026-10-29T21:05:54Z",
  "modifyDttm": "2026-10-29T21:05:54Z"
}
```

**Поля:**
- `fullName` — ФИО, обязательное, до 255 символов.
- `gender` — `M` или `F`, опционально.
- `status` — `ACTIVE` или `INACTIVE`, по умолчанию `ACTIVE` при создании.
- `createDttm` — дата создания, заполняется Hibernate (`@CreationTimestamp`).
- `modifyDttm` — дата обновления, заполняется Hibernate (`@UpdateTimestamp`) и триггером в БД.

## Обработка ошибок

Ошибки возвращаются в формате `ProblemDetail` (RFC 9457).

| Код | Ситуация |
|---|---|
| 400 | Невалидные данные (валидация DTO, нарушение CHECK, неверный тип параметра) |
| 404 | Клиент не найден |

Пример ответа с ошибкой валидации:

```json
{
  "detail": "Check 'errors' field",
  "instance": "/api/clients",
  "status": 400,
  "title": "Validation Failed",
  "errors": {
    "fullName": "must not be blank"
  }
}
```

## Особенности реализации

- **Статус при создании** — всегда `ACTIVE`, клиент не может его задать.
- **`gender`** — опциональное поле, может быть `null`.
- **`createDttm` / `modifyDttm`** — заполняются Hibernate через `@CreationTimestamp` и `@UpdateTimestamp`. В БД также есть `DEFAULT now()` и триггер `set_modify_dttm` как страховка для прямых SQL-вставок.
- **`id`** — генерируется базой через `GENERATED ALWAYS AS IDENTITY`.
- **Валидация** — на уровне DTO (`@NotBlank`, `@Size`) и на уровне БД (`CHECK`, `NOT NULL`).
- **Аннотация `@ById`** — кастомная аннотация для сокращения маппинга эндпоинтов с `{id}`.

## Структура проекта

```
src/main/java/com/lde/clientaccountingservice/
├── annotation/       # Кастомная аннотация @ById
├── controller/       # REST-контроллеры
├── dto/              # Request/Response DTO
├── entity/           # JPA-сущности и enum (ClientAccount, Gender, ClientStatus)
├── error/            # Маппер SQLState в понятные сообщения
├── exception/        # Кастомные исключения и GlobalExceptionHandler
├── repository/       # Spring Data JPA репозитории
└── service/          # Бизнес-логика
```

## Тесты

В проекте подключены зависимости для тестов (Testcontainers, JUnit, Mockito, Data JPA Test), но сами тесты не включены из-за ограничения по времени на выполнение задания. Архитектура готова для их добавления.
```