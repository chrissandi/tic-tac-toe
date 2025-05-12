package com.fwd.tictactoe.service;

import com.fwd.tictactoe.model.Game;
import com.fwd.tictactoe.model.GameFactory;
import com.fwd.tictactoe.model.GameStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class GameService {
    private static final Map<String, Game> gameRepository = new ConcurrentHashMap<>();

    public Game createGame(int boardSize, String playerXName, String playerOName, int requiredConsecutive) {
        Game game = GameFactory.createClassicGame(boardSize, playerXName, playerOName, requiredConsecutive);
        gameRepository.put(game.getId(), game);
        return game;
    }

    public Game getGame(String gameId) {
        Game game = gameRepository.get(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game not found with ID: " + gameId);
        }
        return game;
    }

    public boolean makeMove(String gameId, int row, int col) {
        Game game = getGame(gameId);
        return game.makeMove(row, col);
    }

    public GameStatus getGameStatus(String gameId) {
        Game game = getGame(gameId);
        return game.getStatus();
    }

    public List<Map<String, Object>> getAllGames() {
        return gameRepository.values().stream()
                .map(game -> {
                    Map<String, Object> gameMap = new HashMap<>();
                    gameMap.put("id", game.getId());
                    gameMap.put("boardSize", game.getBoard() != null ? game.getBoard().getSize() : null);
                    gameMap.put("status", game.getStatus());
                    gameMap.put("playerX", game.getPlayerX() != null ? game.getPlayerX().getName() : null);
                    gameMap.put("playerO", game.getPlayerO() != null ? game.getPlayerO().getName() : null);
                    return gameMap;
                })
                .collect(Collectors.toList());
    }

    public void deleteGame(String gameId) {
        if (!gameRepository.containsKey(gameId)) {
            throw new IllegalArgumentException("Game not found with ID: " + gameId);
        }
        gameRepository.remove(gameId);
    }
}