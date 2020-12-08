package org.example.components;

import java.util.concurrent.ThreadLocalRandom;

public class GameBorad {

    Cell[][] cells;

    private int rows, columns;
    public GameBorad(int rows, int columns) {
        setRows(rows);
        setColumns(columns);
        cells = new Cell[rows][columns];
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                cells[i][j] = new Cell();
            }
        }
    }

    private void setColumns(int columns) {
        throwIFNegative(columns);
        this.columns = columns;
    }

    private void throwIFNegative(int value) {
        if(value < 0)
            throw new IllegalArgumentException("Invalid Number");
    }


    private void setRows(int rows) {
        throwIFNegative(rows);
        this.rows = rows;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void addLiveCell(int row, int column) {
        cells[row][column].setDead(false);
    }

    public void nextGeneration() {
        // For Each Tick
            // For Each Cell
                // is live or die to the next generation
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                applyRule(i, j);
            }
        }
    }


    public void applyRule(int i, int j) {

        int counter = countLiveNeighbours(i, j);
        // Check Cell Dead && counter is 3 make is live again
        if(cells[i][j].isDead() && counter == 3)
            cells[i][j].setDead(false);
        // Check Live Cell has less than 2 or more than 3 will make it died
        else if(!cells[i][j].isDead() && (counter < 2 || counter > 3))
            cells[i][j].setDead(true);
    }

    private int countLiveNeighbours(int i, int j) {
        int counter = 0;
        // North
        if(i-1 >= 0 && !cells[i - 1][j].isDead())
            ++counter;
        // North-east
        if(i-1 >= 0 && j+1 < columns && !cells[i - 1][j + 1].isDead())
            ++counter;
        // East
        if(j+1 < columns && !cells[i][j + 1].isDead())
            ++counter;
        // South-east
        if(j+1 < columns && i+1 < rows && !cells[i + 1][j + 1].isDead())
            ++counter;
        // South
        if(i+1 < rows && !cells[i + 1][j].isDead())
            ++counter;
        // South-west
        if(i+1 < rows &&  j-1 >= 0 && !cells[i + 1][j - 1].isDead())
            ++counter;
        // West
        if(j-1 >= 0 && !cells[i][j - 1].isDead())
            ++counter;
        // North-west
        if(i-1 >= 0 && j-1 >= 0 && !cells[i - 1][j - 1].isDead())
            ++counter;
        return  counter;
    }

    public Cell cellOf(int row, int column) {
        throwIFNegative(row);
        throwIFNegative(column);
        if(row >= rows || column >= columns)
            throw new IllegalArgumentException("Number Exceed Length");
        return cells[row][column];
    }

    public int getRowsNo() {
        return rows;
    }

    public int getColumnsNo() {
        return columns;
    }

    public void setRandomLiveCells() {
        for(int i=0; i<(rows*columns)/3; i++){
            int randomRow = ThreadLocalRandom.current().nextInt(0, (rows));
            int randomColumn = ThreadLocalRandom.current().nextInt(0, (columns));
            cellOf(randomRow, randomColumn).setDead(false);
        }
    }
}
