package com.example.demo.domain.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
public class NewGameRequest {
    @Setter
    private int width;
    @Setter
    private int height;
    @JsonProperty("mines_count")
    private int minesCount;

    public void setMinesCount(int minesCount) {
        if (minesCount >= width * height) {
            throw new IllegalArgumentException("Mines count cannot be greater than or equal to the total number of cells.");
        }
        this.minesCount = minesCount;
    }
}
