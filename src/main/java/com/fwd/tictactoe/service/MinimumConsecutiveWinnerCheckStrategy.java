package com.fwd.tictactoe.service;

import com.fwd.tictactoe.model.Board;
import com.fwd.tictactoe.model.Player;
import com.fwd.tictactoe.model.Symbol;

public class MinimumConsecutiveWinnerCheckStrategy implements WinnerCheckStrategy {
    private final int requiredConsecutive;

    public MinimumConsecutiveWinnerCheckStrategy(int requiredConsecutive) {
        this.requiredConsecutive = requiredConsecutive;
    }

    @Override
    public boolean checkWinner(Board board, Player player) {
        Symbol symbol = player.getSymbol();
        int size = board.getSize();

        for (int i = 0; i < size; i++) {
            int consecutive = 0;
            for (int j = 0; j < size; j++) {
                if (board.getSymbolAt(i, j) == symbol) {
                    consecutive++;
                    if (consecutive >= requiredConsecutive) {
                        return true;
                    }
                } else {
                    consecutive = 0;
                }
            }
        }

        for (int j = 0; j < size; j++) {
            int consecutive = 0;
            for (int i = 0; i < size; i++) {
                if (board.getSymbolAt(i, j) == symbol) {
                    consecutive++;
                    if (consecutive >= requiredConsecutive) {
                        return true;
                    }
                } else {
                    consecutive = 0;
                }
            }
        }

        for (int startRow = 0; startRow <= size - requiredConsecutive; startRow++) {
            for (int startCol = 0; startCol <= size - requiredConsecutive; startCol++) {
                boolean win = true;
                for (int k = 0; k < requiredConsecutive; k++) {
                    if (board.getSymbolAt(startRow + k, startCol + k) != symbol) {
                        win = false;
                        break;
                    }
                }
                if (win) return true;
            }
        }

        for (int startRow = 0; startRow <= size - requiredConsecutive; startRow++) {
            for (int startCol = requiredConsecutive - 1; startCol < size; startCol++) {
                boolean win = true;
                for (int k = 0; k < requiredConsecutive; k++) {
                    if (board.getSymbolAt(startRow + k, startCol - k) != symbol) {
                        win = false;
                        break;
                    }
                }
                if (win) return true;
            }
        }

        return false;
    }
}