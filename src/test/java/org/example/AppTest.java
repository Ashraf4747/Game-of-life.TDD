package org.example;



import org.example.components.Cell;
import org.example.components.GameBorad;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppTest 
{
    private GameBorad gameBoard;
    private int rows;
    private int columns;

    /**
     * Rigorous Test :-)
     */


    @BeforeAll
    public void setupRowsAndColumns(){
        this.rows = 20;
        this.columns = 20;
    }
    @BeforeEach
    public void setupGameBoard(){
        this.gameBoard= getGameBoardWithAllDeadCells(rows, columns);
    }

    @Test
    public void donothing(){
        System.out.println("Do Nothing");
    }

    private GameBorad getGameBoardWithAllDeadCells(int rows, int columns) {
        return new GameBorad(rows, columns);
    }

    @Test
    public void testCreatingBoardwithNegativeRowsAndColumns(){
        int rows = -20, columns = 0;
        assertThrows(IllegalArgumentException.class, () -> {
            new GameBorad(rows, columns);
        });
    }


    @Test
    public void testCreatingBoardWithDefaultConfiguration(){
        Cell[][] cells= gameBoard.getCells();
        assertNotNull(cells);
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                assertNotNull(cells[i][j].isDead());
            }
        }
    }

    @Test
    public void testNegativeCellPosition(){
        assertThrows(IllegalArgumentException.class, () -> {
            gameBoard.cellOf(-1, -1);
        });
    }

    @Test
    public void testCellPositionExceedLength(){
        assertThrows(IllegalArgumentException.class, () -> {
            gameBoard.cellOf(21, 21);
        });
    }

    @Test
    public void testValueOfPositiveCellPosition(){
        Cell cell = gameBoard.getCells()[0][0];
        cell.setDead(false);
        assertDoesNotThrow(() -> {
            assertEquals(cell.isDead(), gameBoard.cellOf(0, 0).isDead());
        });

    }

    @Test
    public void checkAreAllCellsAreDead(){
        Cell[][] cells= gameBoard.getCells();
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                assertTrue(gameBoard.cellOf(i, j).isDead());
            }
        }
    }

//    Test Any dead cell with three live neighbours becomes a live cell.
    @Test
    public void testMostLowerRightDeadCell(){
        Cell[][] cells= gameBoard.getCells();
        gameBoard.cellOf(0, 0).setDead(true);
        gameBoard.cellOf(0, 1).setDead(false);
        gameBoard.cellOf(1, 0).setDead(false);
        gameBoard.cellOf(1, 1).setDead(false);
        assertDoesNotThrow(() -> {
            gameBoard.applyRule(0, 0);
            assertFalse(gameBoard.cellOf(0, 0).isDead());
        });
    }
    @Test
    public void testMostUpperLeftDeadCell(){
        Cell[][] cells= gameBoard.getCells();
        gameBoard.cellOf(19, 19).setDead(true);
        gameBoard.cellOf(19, 18).setDead(false);
        gameBoard.cellOf(18, 18).setDead(false);
        gameBoard.cellOf(18, 19).setDead(false);

        assertDoesNotThrow(() -> {
            gameBoard.applyRule(19, 19);
            assertFalse(gameBoard.cellOf(19, 19).isDead());
        });
    }

    @Test
    public void testLiveCellWithLess2OrMore3LiveNeighbours(){
        Cell[][] cells= gameBoard.getCells();
        gameBoard.cellOf(0, 0).setDead(false);
        gameBoard.cellOf(0, 1).setDead(false);
        assertDoesNotThrow(() -> {
            gameBoard.applyRule(0, 0);
            assertTrue(gameBoard.cellOf(0, 0).isDead());
        });

        gameBoard.cellOf(0,0).setDead(false);
        gameBoard.cellOf(1, 0).setDead(false);
        gameBoard.cellOf(1, 1).setDead(false);
        gameBoard.cellOf(1, 2).setDead(false);
        assertDoesNotThrow(() -> {
            gameBoard.applyRule(0, 1);
            assertTrue(gameBoard.cellOf(0,1).isDead());
        });

    }


    @Test
    public void testMostLowerRightLiveCell(){
        Cell[][] cells= gameBoard.getCells();
        gameBoard.cellOf(19, 19).setDead(false);
        assertDoesNotThrow(() -> {
            gameBoard.applyRule(19, 19);
            assertTrue(gameBoard.cellOf(19, 19).isDead());
        });
    }


    @Test
    public void testNextGenerationOfGame(){
        Cell[][] cells= gameBoard.getCells();
        gameBoard.cellOf(0, 0).setDead(false);
        gameBoard.cellOf(0, 1).setDead(false);
        gameBoard.nextGeneration();
        assertTrue(gameBoard.cellOf(0, 0).isDead());
    }


    @Test
    public void testRandomLiveCells(){
        assertDoesNotThrow(() -> {
            gameBoard.setRandomLiveCells();
        });
    }


}
