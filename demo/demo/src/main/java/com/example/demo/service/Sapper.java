package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.example.demo.domain.dto.GameInfoResponse;
import com.example.demo.domain.dto.GameTurnRequest;
import com.example.demo.domain.dto.NewGameRequest;
import com.example.demo.domain.entity.GameField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Sapper {

    private final Map<UUID, GameField> games = new HashMap<>();

    public GameInfoResponse createNewGame(NewGameRequest params) {
        UUID gameId = UUID.randomUUID();
        GameField gameField = new GameField(params, gameId);
        games.put(gameId, gameField);
        log.info("New game created with ID: {}", gameId);
        return gameField.getGameInfoResponse();
    }

    public GameInfoResponse createNewTurn(GameTurnRequest params) {
        UUID gameId = params.getGameId();
        log.info("Received turn request for gameId: {}", gameId);

        if (gameId == null) {
            log.error("Game ID is null in the turn request!");
            throw new IllegalArgumentException("Game ID cannot be null.");
        }

        GameField gameField = games.get(gameId);

        if (gameField == null) {
            log.error("Game with ID {} not found", gameId);
            throw new IllegalArgumentException("Game with ID " + gameId + " not found.");
        }

        gameField.createNewTurn(params.getRow(), params.getCol());
        log.info("Turn processed for gameId: {}, row: {}, col: {}", gameId, params.getRow(), params.getCol());
        return gameField.getGameInfoResponse();
    }
}

