package com.fwd.tictactoe.model;


import com.fwd.tictactoe.service.ClassicWinnerCheckStrategy;
import com.fwd.tictactoe.service.MinimumConsecutiveWinnerCheckStrategy;
import com.fwd.tictactoe.service.WinnerCheckStrategy;

public class GameFactory {
    public static Game createClassicGame(int boardSize, String playerXName, String playerOName, int requiredConsecutive) {
        Board board = new Board(boardSize);
        Player playerX = new Player(playerXName, Symbol.X);
        Player playerO = new Player(playerOName, Symbol.O);

        // Use different win checking strategies based on board size
        WinnerCheckStrategy winnerCheckStrategy;
        if (boardSize == requiredConsecutive) {
            winnerCheckStrategy = new ClassicWinnerCheckStrategy();
        } else {
            // For larger boards, require a minimum of 5 consecutive symbols to win
            winnerCheckStrategy = new MinimumConsecutiveWinnerCheckStrategy(requiredConsecutive);
        }

        return new Game.Builder()
                .board(board)
                .playerX(playerX)
                .playerO(playerO)
                .currentPlayer(playerX) // X goes first
                .winnerCheckStrategy(winnerCheckStrategy)
                .build();
    }

    // Alternative game creation method with custom winning strategy
    public static Game createGameWithCustomStrategy(
            int boardSize,
            String playerXName,
            String playerOName,
            ClassicWinnerCheckStrategy winnerCheckStrategy) {

        Board board = new Board(boardSize);
        Player playerX = new Player(playerXName, Symbol.X);
        Player playerO = new Player(playerOName, Symbol.O);

        return new Game.Builder()
                .board(board)
                .playerX(playerX)
                .playerO(playerO)
                .currentPlayer(playerX) // X goes first
                .winnerCheckStrategy(winnerCheckStrategy)
                .build();
    }
}