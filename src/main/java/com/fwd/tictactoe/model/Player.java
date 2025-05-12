package com.fwd.tictactoe.model;

import java.util.UUID;

public class Player {
    private final String id;
    private final String name;
    private final Symbol symbol;

    public Player(String name, Symbol symbol) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.symbol = symbol;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Symbol getSymbol() {
        return symbol;
    }
}