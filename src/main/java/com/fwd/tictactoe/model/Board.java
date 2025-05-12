package com.fwd.tictactoe.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int size;
    private final Symbol[][] grid;
    private final List<Move> moves;

    public Board(int size) {
        if (size < 3) {
            throw new IllegalArgumentException("Board size must be at least 3");
        }

        this.size = size;
        this.grid = new Symbol[size][size];
        this.moves = new ArrayList<>();

        // Initialize the grid with empty symbols
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = Symbol.EMPTY;
            }
        }
    }

    public boolean makeMove(Move move) {
        int row = move.getRow();
        int col = move.getCol();

        if (row < 0 || row >= size || col < 0 || col >= size) {
            return false; // Out of bounds
        }

        if (grid[row][col] != Symbol.EMPTY) {
            return false; // Cell already taken
        }

        grid[row][col] = move.getPlayer().getSymbol();
        moves.add(move);
        return true;
    }

    public Symbol getSymbolAt(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IndexOutOfBoundsException("Invalid position");
        }
        return grid[row][col];
    }

    public int getSize() {
        return size;
    }

    public List<Move> getMoves() {
        return new ArrayList<>(moves);
    }

    public boolean isFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == Symbol.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(grid[i][j]);
                if (j < size - 1) {
                    sb.append(" | ");
                }
            }
            sb.append("\n");
            if (i < size - 1) {
                for (int j = 0; j < size; j++) {
                    sb.append("---");
                    if (j < size - 1) {
                        sb.append("+");
                    }
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}