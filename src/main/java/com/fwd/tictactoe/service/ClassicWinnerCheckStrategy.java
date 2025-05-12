package com.fwd.tictactoe.service;

import com.fwd.tictactoe.model.Board;
import com.fwd.tictactoe.model.Player;
import com.fwd.tictactoe.model.Symbol;

public class ClassicWinnerCheckStrategy implements WinnerCheckStrategy {

    @Override
    public boolean checkWinner(Board board, Player player) {
        Symbol symbol = player.getSymbol();
        int size = board.getSize();


        for (int i = 0; i < size; i++) {
            boolean rowWin = true;
            for (int j = 0; j < size; j++) {
                if (board.getSymbolAt(i, j) != symbol) {
                    rowWin = false;
                    break;
                }
            }
            if (rowWin) return true;
        }


        for (int j = 0; j < size; j++) {
            boolean colWin = true;
            for (int i = 0; i < size; i++) {
                if (board.getSymbolAt(i, j) != symbol) {
                    colWin = false;
                    break;
                }
            }
            if (colWin) return true;
        }


        boolean diagWin = true;
        for (int i = 0; i < size; i++) {
            if (board.getSymbolAt(i, i) != symbol) {
                diagWin = false;
                break;
            }
        }
        if (diagWin) return true;


        diagWin = true;
        for (int i = 0; i < size; i++) {
            if (board.getSymbolAt(i, size - 1 - i) != symbol) {
                diagWin = false;
                break;
            }
        }
        return diagWin;
    }
}
