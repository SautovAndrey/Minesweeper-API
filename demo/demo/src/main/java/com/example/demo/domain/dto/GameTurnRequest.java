package com.example.demo.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

@ToString
@Slf4j
@Getter
@Setter
public class GameTurnRequest {

    @JsonProperty("game_id")
    private UUID gameId;
    private int row;
    private int col;

    public GameTurnRequest fromJson(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        log.info("Received JSON: {}", jsonString);
        return mapper.readValue(jsonString, GameTurnRequest.class);
    }
}
