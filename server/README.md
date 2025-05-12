**API Endpoints**

Este documento lista todos os endpoints expostos pelas Controllers do projeto, descrevendo URL, entidade, DTOs envolvidos e funcionalidades.

---

## 1. PlayerController (`/players`)

**Entidade:** Player
**DTOs:** `PlayerCreateDTO`, `PlayerUpdateDTO`, `PlayerResponseDTO`

|     Método | Caminho               | Descrição                                                                                                            |
| ---------: | --------------------- | -------------------------------------------------------------------------------------------------------------------- |
|   **POST** | `/players`            | Cria um novo jogador. Recebe um `PlayerCreateDTO` no corpo e retorna `PlayerResponseDTO` com **201 Created**.        |
|    **PUT** | `/players/{id}`       | Atualiza o jogador de ID informado. Recebe um `PlayerUpdateDTO` (campos obrigatórios) e retorna `PlayerResponseDTO`. |
|    **GET** | `/players/{id}`       | Busca um jogador pelo ID. Retorna `PlayerResponseDTO` com **200 OK**.                                                |
|    **GET** | `/players/email`      | Busca um jogador por email. (Necessita correção para usar `@RequestParam` ou placeholder `{email}`).                 |
|    **GET** | `/players`            | Lista todos os jogadores. Retorna `List<PlayerResponseDTO>` com **200 OK**.                                          |
|    **GET** | `/players/findByName` | Busca jogadores por nome. Recebe `PlayerUpdateDTO` (apenas nome) no corpo e retorna `List<PlayerResponseDTO>`.       |
| **DELETE** | `/players/{id}`       | Remove o jogador de ID informado. Retorna **204 No Content**.                                                        |

---

## 2. QuestionController (`/questions`)

**Entidade:** Question
**DTOs:** `QuestionCreateDTO`, `QuestionUpdateDTO`, `QuestionResponseDTO`

|     Método | Caminho           | Descrição                                                                                                    |
| ---------: | ----------------- | ------------------------------------------------------------------------------------------------------------ |
|   **POST** | `/questions`      | Cria uma nova questão. Recebe `QuestionCreateDTO` e retorna `QuestionResponseDTO` com **201 Created**.       |
|    **GET** | `/questions`      | Lista todas as questões. Retorna `List<QuestionResponseDTO>` com **200 OK**.                                 |
|    **GET** | `/questions/{id}` | Busca questão pelo ID. Retorna `QuestionResponseDTO` com **200 OK**.                                         |
|    **PUT** | `/questions/{id}` | Atualiza questão de ID informado. Recebe `QuestionUpdateDTO` e retorna `QuestionResponseDTO` com **200 OK**. |
| **DELETE** | `/questions/{id}` | Exclui a questão de ID informado. Retorna **204 No Content**.                                                |

---

## 3. SpecialCardController (`/specialCards`)

**Entidade:** SpecialCard
**DTOs:** `SpecialCardCreateDTO`, `SpecialCardUpdateDTO`, `SpecialCardResponseDTO`

|     Método | Caminho              | Descrição                                                                                                              |
| ---------: | -------------------- | ---------------------------------------------------------------------------------------------------------------------- |
|   **POST** | `/specialCards`      | Cria um novo SpecialCard. Recebe `SpecialCardCreateDTO` e retorna `SpecialCardResponseDTO` com **201 Created**.        |
|    **GET** | `/specialCards`      | Lista todos os SpecialCards. Retorna `List<SpecialCardResponseDTO>` com **200 OK**.                                    |
|    **GET** | `/specialCards/{id}` | Busca um SpecialCard pelo ID. Retorna `SpecialCardResponseDTO` com **200 OK**.                                         |
|    **PUT** | `/specialCards/{id}` | Atualiza SpecialCard de ID informado. Recebe `SpecialCardUpdateDTO` e retorna `SpecialCardResponseDTO` com **200 OK**. |
| **DELETE** | `/specialCards/{id}` | Exclui o SpecialCard de ID informado. Retorna **204 No Content**.                                                      |

---

## 4. ThemeController (`/themes`)

**Entidade:** Theme
**DTOs:** `ThemeCreateDTO`, `ThemeUpdateDTO`, `ThemeResponseDTO`

|     Método | Caminho              | Descrição                                                                                                               |
| ---------: | -------------------- | ----------------------------------------------------------------------------------------------------------------------- |
|   **POST** | `/themes`            | Cria um novo tema. Recebe `ThemeCreateDTO` e retorna `ThemeResponseDTO` com **201 Created**.                            |
|    **GET** | `/themes`            | Lista todos os temas. Retorna `List<ThemeResponseDTO>` com **200 OK**.                                                  |
|    **GET** | `/themes/{id}`       | Busca tema pelo ID. (Corrigir `@PathVariable ThemeUpdateDTO` para `@PathVariable long id`). Retorna `ThemeResponseDTO`. |
|    **GET** | `/themes/findByName` | Busca tema por nome. Recebe `ThemeUpdateDTO` (apenas nome) no corpo e retorna `ThemeResponseDTO` com **200 OK**.        |
|    **PUT** | `/themes/{id}`       | Atualiza tema de ID informado. Recebe `ThemeUpdateDTO` e retorna `ThemeResponseDTO` com **200 OK**.                     |
| **DELETE** | `/themes/{id}`       | Exclui o tema de ID informado. Retorna **204 No Content**.                                                              |

---

### Observações Gerais

* **Códigos de status HTTP**:

  * **201 Created** para criação.
  * **200 OK** para consultas e atualizações.
  * **204 No Content** para exclusões.

* **Validações**: DTOs com `@Valid` utilizam Jakarta Validation para garantir campos obrigatórios, formatos, etc.

* **Possíveis correções**:

  1. `PlayerController`: ajustar endpoint de email para `@RequestParam("email")` ou `@GetMapping("/email/{email}")`.
  2. `ThemeController`: consertar método GET por ID para usar `@PathVariable long id`.

* **Arquitetura**: cada Controller delega a lógica à camada de serviços (ex.: `PlayerServices`, `QuestionServices`, etc.), retornando DTOs para desacoplar da persistência.
