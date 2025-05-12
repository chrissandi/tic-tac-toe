# 🧠 Tic Tac Toe

A customizable, scalable, and extendable Tic Tac Toe backend built with **Java 21** and **Spring Boot 3.4.5**. This project supports variable board sizes, player customization, and multiple winning strategies using robust design patterns.

---

## 📌 Features

- ✅ Dynamic board sizes (e.g., 3x3, 10x10, etc)
- ✅ Player-defined winning rules (classic or required consecutive marks)
- ✅ Stateless REST API design
- ✅ Clear game status transitions: `IN_PROGRESS`, `PLAYER_X_WON`, `PLAYER_O_WON`, `DRAW`
- ✅ Scalable and maintainable codebase using design patterns

---

## 🧱 Design Patterns Used

### 🔧 Factory Pattern
Used to instantiate the correct **Winning Strategy** (Classic or Minimum Consecutive) based on user input when creating a new game.

### ♟ Strategy Pattern
Encapsulates different game-winning strategies:
- `ClassicWinnerCheckStrategy`: Traditional 3-in-a-row
- `MinimumConsecutiveWinnerCheckStrategy`: Custom n-in-a-row on larger boards

This allows easy extension for future strategies (e.g., diagonal-only, time-limited, etc.).

---


## 🔗 API Endpoints
### 🎲 Create Game
POST /api/games?boardSize=10&playerX=chris&playerO=sandi&requiredConsecutive=5
If `requiredConsecutive` is not provided, it defaults to `boardSize` (i.e., classic full-row rule).

**Response:**
```json
{
  "gameId": "uuid",
  "boardSize": 10,
  "currentPlayer": "chris",
  "status": "IN_PROGRESS"
}
```

### 📊 Get Game State
GET /api/games/{gameId}

**Response:**
```json
{
  "gameId": "uuid",
  "boardSize": 10,
  "currentPlayer": "sandi",
  "board": [
    ["X", " ", " ", ...],
    ...
  ],
  "status": "IN_PROGRESS"
}
```

### ✍️ Make a Move
POST /api/games/{gameId}/moves?row=0&col=1

**Response:**
```json
{
  "gameId": "uuid",
  "currentPlayer": "chris",
  "board": [...],
  "status": "IN_PROGRESS"
}

```


