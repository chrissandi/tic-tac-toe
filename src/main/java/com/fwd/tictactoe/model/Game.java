package com.fwd.tictactoe.model;

import com.fwd.tictactoe.service.WinnerCheckStrategy;

import java.util.UUID;

public class Game {
    private final String id;
    private final Board board;
    private final Player playerX;
    private final Player playerO;
    private Player currentPlayer;
    private GameStatus status;
    private final WinnerCheckStrategy winnerCheckStrategy;

    private Game(Builder builder) {
        this.id = UUID.randomUUID().toString();
        this.board = builder.board;
        this.playerX = builder.playerX;
        this.playerO = builder.playerO;
        this.currentPlayer = builder.currentPlayer;
        this.status = GameStatus.IN_PROGRESS;
        this.winnerCheckStrategy = builder.winnerCheckStrategy;
    }

    public boolean makeMove(int row, int col) {
        if (status != GameStatus.IN_PROGRESS) {
            return false;
        }

        Move move = new Move(row, col, currentPlayer);
        boolean moveMade = board.makeMove(move);

        if (moveMade) {
            updateGameStatus();
            switchPlayer();
            return true;
        }

        return false;
    }

    private void updateGameStatus() {
        if (winnerCheckStrategy.checkWinner(board, currentPlayer)) {
            status = currentPlayer.getSymbol() == Symbol.X ?
                    GameStatus.PLAYER_X_WON : GameStatus.PLAYER_O_WON;
        } else if (board.isFull()) {
            status = GameStatus.DRAW;
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
    }

    public String getId() {
        return id;
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayerX() {
        return playerX;
    }

    public Player getPlayerO() {
        return playerO;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameStatus getStatus() {
        return status;
    }

    // Builder pattern for Game construction
    public static class Builder {
        private Board board;
        private Player playerX;
        private Player playerO;
        private Player currentPlayer;
        private WinnerCheckStrategy winnerCheckStrategy;

        public Builder board(Board board) {
            this.board = board;
            return this;
        }

        public Builder playerX(Player playerX) {
            this.playerX = playerX;
            return this;
        }

        public Builder playerO(Player playerO) {
            this.playerO = playerO;
            return this;
        }

        public Builder currentPlayer(Player currentPlayer) {
            this.currentPlayer = currentPlayer;
            return this;
        }

        public Builder winnerCheckStrategy(WinnerCheckStrategy winnerCheckStrategy) {
            this.winnerCheckStrategy = winnerCheckStrategy;
            return this;
        }

        public Game build() {
            validateState();
            return new Game(this);
        }

        private void validateState() {
            if (board == null || playerX == null || playerO == null ||
                    currentPlayer == null || winnerCheckStrategy == null) {
                throw new IllegalStateException("All game components must be initialized");
            }

            if (playerX.getSymbol() != Symbol.X || playerO.getSymbol() != Symbol.O) {
                throw new IllegalStateException("Players must have correct symbols");
            }

            if (currentPlayer != playerX && currentPlayer != playerO) {
                throw new IllegalStateException("Current player must be one of the players");
            }
        }
    }
}