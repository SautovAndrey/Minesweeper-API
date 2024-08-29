package com.example.demo.controller;


import com.example.demo.domain.dto.NewGameRequest;
import com.example.demo.service.Sapper;
import com.example.demo.domain.dto.GameInfoResponse;
import com.example.demo.domain.dto.GameTurnRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MinesweeperController {

    private final Sapper sapper;

    @PostMapping("/new")
    public ResponseEntity<GameInfoResponse> createNewGame(@RequestBody NewGameRequest newGameRequest) {
        return ResponseEntity.ok(sapper.createNewGame(newGameRequest));
    }

    @PostMapping("/turn")
    public ResponseEntity<GameInfoResponse> createNewTurn(@RequestBody GameTurnRequest request) {
        log.info("Received turn request: {}", request);
        return ResponseEntity.ok(sapper.createNewTurn(request));
    }
}


