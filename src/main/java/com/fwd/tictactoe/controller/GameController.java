package com.fwd.tictactoe.controller;

import com.fwd.tictactoe.model.Game;
import com.fwd.tictactoe.model.GameStatus;
import com.fwd.tictactoe.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createGame(
            @RequestParam int boardSize,
            @RequestParam String playerX,
            @RequestParam String playerO,
            @RequestParam int requiredConsecutive) {

        if (boardSize < 3) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Board size must be at least 3")
            );
        }

        if(requiredConsecutive < 3){
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Minimum required consecutive is 3")
            );
        }

        if(requiredConsecutive > boardSize){
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Maximum required consecutive is board size")
            );
        }

        Game game = gameService.createGame(boardSize, playerX, playerO, requiredConsecutive);

        Map<String, Object> response = new HashMap<>();
        response.put("gameId", game.getId());
        response.put("boardSize", game.getBoard().getSize());
        response.put("currentPlayer", game.getCurrentPlayer().getName());
        response.put("status", game.getStatus());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<Map<String, Object>> getGameState(@PathVariable String gameId) {
        try {
            Game game = gameService.getGame(gameId);

            Map<String, Object> response = new HashMap<>();
            response.put("gameId", game.getId());
            response.put("boardSize", game.getBoard().getSize());
            response.put("currentPlayer", game.getCurrentPlayer().getName());
            response.put("status", game.getStatus());

            // Create a representation of the board
            int size = game.getBoard().getSize();
            String[][] boardState = new String[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    boardState[i][j] = game.getBoard().getSymbolAt(i, j).toString();
                }
            }
            response.put("board", boardState);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{gameId}/moves")
    public ResponseEntity<Map<String, Object>> makeMove(
            @PathVariable String gameId,
            @RequestParam int row,
            @RequestParam int col) {

        try {
            Game game = gameService.getGame(gameId);

            if (game.getStatus() != GameStatus.IN_PROGRESS) {
                return ResponseEntity.badRequest().body(
                        Map.of("error", "Game is already over")
                );
            }

            boolean moveMade = gameService.makeMove(gameId, row, col);

            if (!moveMade) {
                return ResponseEntity.badRequest().body(
                        Map.of("error", "Invalid move")
                );
            }

            // Get updated game state
            game = gameService.getGame(gameId);

            Map<String, Object> response = new HashMap<>();
            response.put("gameId", game.getId());
            response.put("currentPlayer", game.getCurrentPlayer().getName());
            response.put("status", game.getStatus());

            // Create a representation of the board
            int size = game.getBoard().getSize();
            String[][] boardState = new String[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    boardState[i][j] = game.getBoard().getSymbolAt(i, j).toString();
                }
            }
            response.put("board", boardState);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllGames() {
        Map<String, Object> response = new HashMap<>();
        response.put("games", gameService.getAllGames());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable String gameId) {
        try {
            gameService.deleteGame(gameId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}