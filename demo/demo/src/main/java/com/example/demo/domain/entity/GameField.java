package com.example.demo.domain.entity;

import com.example.demo.domain.dto.GameInfoResponse;
import com.example.demo.domain.dto.NewGameRequest;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameField {
    private final UUID gameId;
    private final int width;
    private final int height;
    private final int minesCount;
    private boolean completed = false;
    private final Cell[][] field;

    public GameField(NewGameRequest params, UUID gameId) {
        this.gameId = gameId;
        this.width = params.getWidth();
        this.height = params.getHeight();
        this.minesCount = params.getMinesCount();
        this.field = new Cell[height][width];
        initField();
        createMines();
        initRelations();
    }

    private void initField() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                field[i][j] = new Cell();
            }
        }
    }

    private void createMines() {
        Random random = new Random();
        int count = minesCount;

        while (count != 0) {
            int row = random.nextInt(height);
            int col = random.nextInt(width);
            Cell cell = field[row][col];
            if (!cell.isBomb()) {
                cell.markAsBomb();
                count--;
            }
        }
    }

    private void initRelations() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell cell = field[i][j];
                if (cell.isBomb()) {
                    incrementNeighbors(i, j);
                }
            }
        }
    }

    private void incrementNeighbors(int row, int col) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isInBounds(row + i, col + j) && !(i == 0 && j == 0)) {
                    field[row + i][col + j].incrementAmount();
                }
            }
        }
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }

    public void createNewTurn(int row, int col) {
        if (completed) {
            return;
        }
        Cell cell = field[row][col];
        cell.markAsPushed();
        if (cell.isBomb()) {
            boolean defeat = true;
            completed = true;
            revealAllMines("X");
            return;
        }

        if (cell.getAmount() == 0) {
            cascadeOpenCells(row, col);
        }

        if (isComplete()) {
            completed = true;
            revealAllMines("M");
        }
    }

    private boolean isComplete() {
        int pushedCount = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (field[i][j].isPushed() && !field[i][j].isBomb()) {
                    pushedCount++;
                }
            }
        }
        return (pushedCount + minesCount) == (width * height);
    }

    private void revealAllMines(String mineSymbol) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (field[i][j].isBomb()) {
                    field[i][j].setRevealedSymbol(mineSymbol);
                }
            }
        }
    }

    public GameInfoResponse getGameInfoResponse() {
        List<List<String>> fieldView = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            List<String> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                Cell cell = field[i][j];
                if (completed && cell.isBomb()) {
                    row.add(cell.getRevealedSymbol());
                } else if (cell.isPushed()) {
                    row.add(String.valueOf(cell.getAmount()));
                } else {
                    row.add(" ");
                }
            }
            fieldView.add(row);
        }
        GameInfoResponse response = new GameInfoResponse();
        response.setGameId(gameId);
        response.setWidth(width);
        response.setHeight(height);
        response.setMinesCount(minesCount);
        response.setCompleted(completed);
        response.setField(fieldView);
        return response;
    }

    private void cascadeOpenCells(int row, int col) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                if (isInBounds(newRow, newCol) && !field[newRow][newCol].isPushed()) {
                    field[newRow][newCol].markAsPushed();
                    if (field[newRow][newCol].getAmount() == 0) {
                        cascadeOpenCells(newRow, newCol);
                    }
                }
            }
        }
    }
}
