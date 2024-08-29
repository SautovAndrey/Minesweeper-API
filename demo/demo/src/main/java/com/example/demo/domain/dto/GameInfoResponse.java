package com.example.demo.domain.dto;


import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class GameInfoResponse {
    @JsonProperty("game_id")
    private UUID gameId;

    @JsonProperty("mines_count")
    private int minesCount;

    private int width;
    private int height;
    private boolean completed;
    private List<List<String>> field;
}

