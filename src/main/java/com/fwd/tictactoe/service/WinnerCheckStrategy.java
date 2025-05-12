package com.fwd.tictactoe.service;

import com.fwd.tictactoe.model.Board;
import com.fwd.tictactoe.model.Player;

public interface WinnerCheckStrategy {
    boolean checkWinner(Board board, Player player);
}
