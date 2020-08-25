package com.tuan;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        JFrame snakeGame = new JFrame();

        GamePlay gamePlay = new GamePlay();

        snakeGame.setTitle("Snake game");
        snakeGame.setBounds(0, 0, 913, 660);
        snakeGame.setBackground(Color.WHITE);
        snakeGame.setResizable(false);
        snakeGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        snakeGame.setLocationRelativeTo(null);
        snakeGame.setVisible(true);

        snakeGame.add(gamePlay);
    }
}
