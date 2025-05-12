package com.fwd.tictactoe.model;

public enum Symbol {
    X("X"),
    O("O"),
    EMPTY(" ");

    private final String representation;

    Symbol(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}