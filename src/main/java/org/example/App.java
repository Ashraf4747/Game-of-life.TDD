package org.example;

import org.example.components.GameBorad;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){
        JFrame frame = new JFrame("Game Of Life");
        final int ROWS_NO = 30, COLUMNS_NO = 30;
        GameBorad gameBoard = new GameBorad(ROWS_NO, COLUMNS_NO);
        gameBoard.setRandomLiveCells();
        GridLayout gridLayout = new GridLayout(gameBoard.getRowsNo(), gameBoard.getColumnsNo(), 1, 1);
        frame.setSize(500, 500);
        frame.getContentPane().setBackground(Color.WHITE);;
        frame.setLayout(gridLayout);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        do{
            try{
            System.out.println("Next Generation");
            gameBoard.nextGeneration();
            frame.getContentPane().removeAll();
            for(int i=0; i< gridLayout.getRows() * gridLayout.getColumns(); i++){
                JPanel cellPanel = new JPanel();
                cellPanel.setBackground(gameBoard.cellOf(i / gameBoard.getRowsNo() , i % gameBoard.getColumnsNo()).isDead() ? Color.black: Color.blue);
                frame.add(cellPanel);

            }
            frame.revalidate();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }while(true);
    }
}
