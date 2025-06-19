# Quest-Game

**Quest-Game** é uma plataforma multiplayer de quiz com tabuleiro virtual.  
Frontend em React+Vite comunica-se com um backend em Spring Boot via REST e WebSocket (STOMP). Jogadores respondem perguntas, movimentam tokens, usam habilidades especiais e competem em tempo real.

---

## 📁 Estrutura de Diretórios do backend

```text
Quest-Game/
├── client/                            # Front-end (React + Vite + TypeScript)
│   ├── public/
│   ├── src/
│   ├── package.json
│   └── vite.config.ts
└── server/                            # Back-end (Java 17 + Spring Boot)
    ├── Dockerfile
    ├── docker-compose.yml
    ├── .env / .dockerignore / .gitignore
    ├── pom.xml
    ├── README.md                     # API Endpoints detalhado
    └── src/
        ├── main/
        │   ├── java/com/quest/
        │   │   ├── config/           # WebSocketSessionRegistry, DataInitializer…
        │   │   ├── controllers/
        │   │   │   ├── auth/         # [AuthController](server/src/main/java/com/quest/controllers/auth/AuthController.java)
        │   │   │   ├── rest/         # [PlayerController](server/src/main/java/com/quest/controllers/rest/PlayerController.java),
        │   │   │   │                   [BoardController](server/src/main/java/com/quest/controllers/rest/BoardController.java), [QuestionController](server/src/main/java/com/quest/controllers/rest/QuestionController.java), [SpecialCardController](server/src/main/java/com/quest/controllers/rest/SpecialCardController.java), [ThemeController](server/src/main/java/com/quest/controllers/rest/ThemeController.java)
        │   │   │   └── ws/           # [GameRoomWsController](server/src/main/java/com/quest/controllers/ws/GameRoomWsController.java)
        │   │   ├── dto/
        │   │   │   ├── rest/         # DTOs para Players, Boards, Questions, SpecialCards, Themes…
        │   │   │   └── ws/           # DTOs de sala (RoomCreateRequestDTO, RoomSummaryDTO…)
        │   │   ├── enums/           # [AbilityType](server/src/main/java/com/quest/enums/AbilityType.java), [Difficulty]…
        │   │   ├── engine/          # Núcleo de regras de jogo
        │   │   │   ├── core/         # [GameEngine](server/src/main/java/com/quest/engine/core/GameEngine.java)
        │   │   │   ├── managers/     # TurnManager, BoardManager, QuestionManager…
        │   │   │   └── state/        # BoardState, PlayerState, TileState…
        │   │   ├── models/          # JPA Entities: Player, Board, Question, QuestionOption, PlayerTheme…
        │   │   ├── repositories/    # Spring Data JPA: PlayerRepository, BoardRepository, …
        │   │   ├── mappers/         # Mapeamento Entity ↔ DTO
        │   │   └── services/        # Lógica de negócio
        │   │       ├── auth/        # [AuthService](server/src/main/java/com/quest/services/auth/AuthService.java)
        │   │       ├── rest/        # [PlayerServices](server/src/main/java/com/quest/services/rest/PlayerServices.java),
        │   │       │                   [BoardServices](server/src/main/java/com/quest/services/rest/BoardServices.java),
        │   │       │                   [QuestionServices], [SpecialCardServices], [ThemeServices]
        │   │       └── ws/          # [GameRoomService](server/src/main/java/com/quest/services/ws/GameRoomService.java)
        │   └── resources/
        │       ├── application.properties
        │       ├── application-docker.properties
        │       └── json/           # Carga inicial: temas, questões…
        └── test/                   # Testes unitários
```

---

## 🚀 Tech Stack

- Front-end: **Vite**, **React**, **TypeScript**, **ESLint**, **Prettier**  
- Back-end: **Java 17**, **Spring Boot** (MVC, WebSocket STOMP), **Spring Data JPA**, **Hibernate**, **Jackson**, **Maven**  
- Infra: **Docker**, **Docker Compose**, deploy do Backend dockerizado com **Render** e frontend no **Vercel**,(Jogue agora em **(https://quest-game-peach.vercel.app)**)

---

## 🖥️ Como Executar

### Local

1. **Back-end**  
   ```bash
   cd server
   mvn spring-boot:run
   ```
2. **Front-end**  
   ```bash
   cd client
   npm install
   npm run dev
   ```
3. Acesse `http://localhost:5173` (UI) e `http://localhost:8080` (API).

### Com Docker

```bash
docker-compose up --build
```

---

## 🛠 Back-end Overview

### 1. Autenticação

- **AuthController** (`POST /login`) → `AuthService.authenticate` → retorna `PlayerResponseDTO`

### 2. API REST (src/main/java/com/quest/controllers/rest)

#### PlayerController (`/players`)
- `POST /players` → cria (`PlayerCreateDTO` → `PlayerResponseDTO`, **201**)
- `PUT /players/{id}` → atualiza (`PlayerUpdateDTO` → **200**)
- `GET /players` → lista todos (`200`)
- `GET /players/{id}` → busca por ID
- `GET /players/email/{email}` → busca por email
- `GET /players/name/{name}` → busca por nome
- `GET /players/{id}/themes` → retorna temas do jogador
- `PATCH /players/{id}/addTheme` → adiciona tema + tokens (`PlayerThemesDTO`)
- `PATCH /players/{id}/addBalance` → incrementa balance (`PlayerBalanceDTO`)
- `PATCH /players/{id}/decreaseBalance` → decrementa balance
- `DELETE /players/{id}` → remove (`204`)

#### BoardController (`/boards`)
- `POST /boards` → cria (`BoardCreateDTO` → `BoardResponseDTO`, **201**)
- `GET /boards` → lista (`200`)
- `GET /boards/{id}` → busca por ID
- `PUT /boards/{id}` → atualiza (`BoardUpdateDTO`)
- `DELETE /boards/{id}` → remove (`204`)

#### QuestionController (`/questions`)
- `POST /questions` → cria (`QuestionCreateDTO`)
- `POST /questions/many` → batch
- `GET /questions` → lista
- `GET /questions/{id}` → busca
- `PUT /questions/{id}` → atualiza
- `DELETE /questions/{id}` → remove

#### SpecialCardController (`/specialCards`)
- `POST /specialCards` → cria
- `GET /specialCards` → lista
- `GET /specialCards/{id}` → busca
- `PUT /specialCards/{id}` → atualiza
- `DELETE /specialCards/{id}` → remove

#### ThemeController (`/themes`)
- `POST /themes` → cria (`ThemeCreateDTO`)
- `POST /themes/many` → batch
- `GET /themes` → lista
- `GET /themes/{id}` → busca
- `GET /themes/findByName` → busca por nome
- `PUT /themes/{id}` → atualiza (`ThemeUpdateDTO`)
- `PATCH /themes/{id}/availability` → altera campo `free`
- `DELETE /themes/{id}` → remove

### 3. WebSocket STOMP (src/main/java/com/quest/controllers/ws)

- **Endpoint**: `/ws`  
- **`GameRoomWsController`**:
  - `/room/create` → cria sala (envia `RoomCreateResponseDTO`)
  - `/room/join` → registra sessão e player em sala
  - `/rooms/public` → lista salas públicas
  - `/room/start` → inicia partida
  - `/room/leave` → sai da sala
  - `/room/state` → muda visibilidade
  - `/room/close` → fecha sala

### 4. Services Layer (src/main/java/com/quest/services)

- **AuthService**: valida login  
- **PlayerServices**: CRUD jogador, balance, temas  
- **BoardServices**: validação de tiles, CRUD boards  
- **QuestionServices**: CRUD perguntas, batch, busca aleatória  
- **ThemeServices**: CRUD temas, disponibilidade, remoção com clean-up  
- **GameRoomService**: gerencia sessões/salas, chama `GameEngine`, notifica via STOMP  

### 5. Mecânicas de Jogo (src/main/java/com/quest/engine)

- **GameSessionManager** & **GameSession**: mantém estado por sala  
- **GameEngine**:
  - Distribui `BoardState` (tiles + temas)  
  - Gerencia tokens iniciais, turnos (`TurnManager`)  
  - `prepareQuestion` → marca passos pendentes + questão  
  - `answerQuestion` → processa acerto/erro, movimenta (`BoardManager`), aplica habilidades:
    - **ROLL_DICE**, **SKIP_OPPONENT_TURN**, **RETURN_TILE**, FUTURO **REVERSE_MOVEMENT**
  - Recompensas de streak (`applyStreakReward`)  
  - Reset de tokens e recarga de habilidades em falha total  
  - Forçar falha/perda de turno (`forceFailQuestion`, `forceSkipTurn`)

---

## 📜 Observações Gerais

- **Validações**: DTOs anotados com Jakarta Validation (`@Valid`, `@NotNull`, `@Size`, etc.)  
- **HTTP Status**: `201 Created`, `200 OK`, `204 No Content`  
- **Carga inicial**: JSONs em `server/src/main/resources/json/` via `DataInitializer`  
- **Docker**: multi-stage build para imagens leves  


## 💻 Front-end (client) Overview

### Pré-requisitos
- Node.js ≥ 16.x e npm ou Yarn  
- Porta 5173 livre (padrão do Vite)  
- Backend rodando em `http://localhost:8080`  

### Instalação e execução
```bash
cd client
npm install        
npm run dev        # dev server em http://localhost:5173
npm run build      # gera build em client/dist
npm run preview    # preview do build local
```