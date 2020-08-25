package com.tuan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private int[] snakeLengthX = new int[750];
    private int[] snakeLengthY = new int[750];
    private int[] enemyX = new int[34]; // enemyX.length = this.getWidth()/2
    private int[] enemyY = new int[21]; // enemyY.length = this.getHeight()/2

    private int snakeLengthFirst = 3;

    private int moves = 0;

    private int scores = 0;

    private boolean directions = true;

    private boolean dirRight = false;
    private boolean dirLeft = false;
    private boolean dirUp = false;
    private boolean dirDown = false;

    private ImageIcon titleImage;
    private ImageIcon rightImage;
    private ImageIcon leftImage;
    private ImageIcon upImage;
    private ImageIcon downImage;
    private ImageIcon snakeImage;
    private ImageIcon enemyImage;

    private Random random = new Random();

    private int enemyRandomX = random.nextInt(34);
    private int enemyRandomY = random.nextInt(21);

    private Timer timer;
    private int delay = 250;

    public GamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(delay, this);
        timer.start();

        for (int i = 0; i < enemyX.length; i++) {
            enemyX[i] = 25 + (25 * i);
        }

        for (int i = 0; i < enemyY.length; i++) {
            if (i == 0) {
                enemyY[i] = 75;
            } else {
                enemyY[i] = enemyY[i - 1] + 25;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        //the snake's initial position
        if (moves == 0) {
            snakeLengthX[0] = 75;
            snakeLengthX[1] = 50;
            snakeLengthX[2] = 25;

            snakeLengthY[0] = 75;
            snakeLengthY[1] = 75;
            snakeLengthY[2] = 75;
        }

        // draw title image border
        g.setColor(Color.BLACK);
        g.drawRect(23, 10, 851, 55);

        //draw title image
        titleImage = new ImageIcon("src/com/tuan/img/snaketitle.jpg");
        titleImage.paintIcon(this, g, 24, 11);

        //draw score for game
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Scores: " + String.format("%06d", scores), 750, 30);

        //draw length of snake for game
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Length: " + String.format("%02d", snakeLengthFirst), 750, 55);

        //draw my name
        g.setColor(Color.BLACK);
        g.setFont(new Font("arial", Font.BOLD, 14));
        g.drawString("© ĐỖ NGỌC TUÂN - 2020", 710, 618);

        //draw border for gameplay
        g.setColor(Color.WHITE);
        g.drawRect(20, 71, 857, 532);

        //draw background for gameplay
        g.setColor(Color.LIGHT_GRAY);
        g.fill3DRect(21, 72, 856, 531, true);

        rightImage = new ImageIcon("src/com/tuan/img/rightmouth.png");
        rightImage.paintIcon(this, g, snakeLengthX[0], snakeLengthY[0]);

        for (int i = 0; i < snakeLengthFirst; i++) {
            if (i == 0 && dirRight) {
                rightImage = new ImageIcon("src/com/tuan/img/rightmouth.png");
                rightImage.paintIcon(this, g, snakeLengthX[i], snakeLengthY[i]);
            }

            if (i == 0 && dirLeft) {
                leftImage = new ImageIcon("src/com/tuan/img/leftmouth.png");
                leftImage.paintIcon(this, g, snakeLengthX[i], snakeLengthY[i]);
            }

            if (i == 0 && dirDown) {
                downImage = new ImageIcon("src/com/tuan/img/downmouth.png");
                downImage.paintIcon(this, g, snakeLengthX[i], snakeLengthY[i]);
            }

            if (i == 0 && dirUp) {
                upImage = new ImageIcon("src/com/tuan/img/upmouth.png");
                upImage.paintIcon(this, g, snakeLengthX[i], snakeLengthY[i]);
            }

            //if i!=0 paint snakeImage in snakeLengthX[1 and 2] and snakeLengthY[1 and 2] <=> x=50 and y=25
            if (i != 0) {
                snakeImage = new ImageIcon("src/com/tuan/img/snakeimage.png");
                snakeImage.paintIcon(this, g, snakeLengthX[i], snakeLengthY[i]);
            }
        }

        enemyImage = new ImageIcon("src/com/tuan/img/enemy.png");


        //If the snake's head touches the enemy, increase the snake's length
        if (enemyX[enemyRandomX] == snakeLengthX[0] && enemyY[enemyRandomY] == snakeLengthY[0]) {
            snakeLengthFirst++;

            scores += 100;

            if (delay >= 0) {
                delay -= 5;
                timer.setDelay(delay);
            }

            enemyRandomX = random.nextInt(34);
            enemyRandomY = random.nextInt(21);
        }

        for (int i = 1; i < snakeLengthFirst; i++) {
            if (snakeLengthX[0] == snakeLengthX[i] && snakeLengthY[0] == snakeLengthY[i]) {
                dirRight = false;
                dirLeft = false;
                dirUp = false;
                dirDown = false;

                //draw length of snake for game
                g.setColor(Color.WHITE);
                g.setFont(new Font("arial", Font.BOLD, 50));
                g.drawString("GAME OVER", 300, 300);

                g.setFont(new Font("arial", Font.BOLD, 20));
                g.drawString("Space to RESTART", 350, 340);

                directions = false;
            }
        }

        enemyImage.paintIcon(this, g, enemyX[enemyRandomX], enemyY[enemyRandomY]);

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (dirRight) {
            for (int i = snakeLengthFirst - 1; i >= 0; i--) {
                snakeLengthY[i + 1] = snakeLengthY[i];
            }

            for (int i = snakeLengthFirst; i >= 0; i--) {
                if (i == 0) {
                    snakeLengthX[i] = snakeLengthX[i] + 25;
                } else {
                    snakeLengthX[i] = snakeLengthX[i - 1];
                }

                if (snakeLengthX[i] > 850) {
                    snakeLengthX[i] = 25;
                }
            }

            repaint();
        }

        if (dirLeft) {
            for (int i = snakeLengthFirst - 1; i >= 0; i--) {
                snakeLengthY[i + 1] = snakeLengthY[i];
            }

            for (int i = snakeLengthFirst; i >= 0; i--) {
                if (i == 0) {
                    snakeLengthX[i] = snakeLengthX[i] - 25;
                } else {
                    snakeLengthX[i] = snakeLengthX[i - 1];
                }

                if (snakeLengthX[i] < 25) {
                    snakeLengthX[i] = 850;
                }
            }

            repaint();
        }

        if (dirUp) {
            for (int i = snakeLengthFirst - 1; i >= 0; i--) {
                snakeLengthX[i + 1] = snakeLengthX[i];
            }

            for (int i = snakeLengthFirst; i >= 0; i--) {
                if (i == 0) {
                    snakeLengthY[i] = snakeLengthY[i] - 25;
                } else {
                    snakeLengthY[i] = snakeLengthY[i - 1];
                }

                if (snakeLengthY[i] < 75) {
                    snakeLengthY[i] = 575;
                }
            }

            repaint();
        }

        if (dirDown) {
            for (int i = snakeLengthFirst - 1; i >= 0; i--) {
                snakeLengthX[i + 1] = snakeLengthX[i];
            }

            for (int i = snakeLengthFirst - 1; i >= 0; i--) {
                if (i == 0) {
                    snakeLengthY[i] = snakeLengthY[i] + 25;
                } else {
                    snakeLengthY[i] = snakeLengthY[i - 1];
                }

                if (snakeLengthY[i] > 575) {
                    snakeLengthY[i] = 75;
                }
            }

            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            moves = 0;

            snakeLengthFirst = 3;

            scores = 0;

            directions = true;

            delay = 250;
            timer.setDelay(delay);

            repaint();
        }

        if (directions) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT:
                    moves++;

                    dirRight = !dirLeft;

                    dirUp = false;
                    dirDown = false;
                    break;
                case KeyEvent.VK_LEFT:
                    moves++;

                    dirLeft = !dirRight;

                    dirUp = false;
                    dirDown = false;
                    break;
                case KeyEvent.VK_UP:
                    moves++;

                    dirUp = !dirDown;

                    dirLeft = false;
                    dirRight = false;
                    break;
                case KeyEvent.VK_DOWN:
                    moves++;

                    dirDown = !dirUp;

                    dirRight = false;
                    dirLeft = false;
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
