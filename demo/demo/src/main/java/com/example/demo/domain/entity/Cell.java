package com.example.demo.domain.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
public class Cell {
    private int amount = 0;
    private boolean pushed = false;
    private boolean bomb = false;

    @Setter
    private String revealedSymbol = "M"; // Символ по умолчанию

    public void markAsBomb() {
        this.amount = 9;
        this.bomb = true;
    }

    public void markAsPushed() {
        this.pushed = true;
    }

    public void incrementAmount() {
        if (!this.bomb) {
            this.amount++;
        }
    }
}


