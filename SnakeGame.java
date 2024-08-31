import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;            //it is used for storing the segments of snake body
import java.util.Random;            //it is use for getting random x and y values to place our food on the screen
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    //snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    //food
    Tile food;
    Random random;

    //game logic 
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);                 //use this to listening for key presses

        snakeHead = new Tile(5,5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // //grid
        // for(int i=0; i < boardWidth/tileSize; i++) {
        //     g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
        //     g.drawLine(0, i*tileSize, boardWidth, i*tileSize);
        // }

        //food
        g.setColor(Color.red);
        // g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize , true);

        //snake head 
        g.setColor(Color.green);
        // g.fillRect(snakeHead.x * tileSize , snakeHead.y * tileSize , tileSize, tileSize);     // x and y tilesize(width), tilesize(height)
        g.fill3DRect(snakeHead.x * tileSize , snakeHead.y * tileSize , tileSize, tileSize, true);

        //snake body
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            // g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }

        //score 
        g.setFont(new Font("Arial" , Font.PLAIN, 16));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game Over:" + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        else {
            g.drawString("Score:" + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }

    }

    public void placeFood() {                                //this funtion randomly set x and y cordinates of the food
        food.x = random.nextInt(boardWidth/tileSize);       // 600/24 = 24  (x position will be a random number from 0 to 24)
        food.y = random.nextInt(boardHeight/tileSize);       // 600/24 = 24  (y position will be a random number from 0 to 24)
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        //eat food 
        if (collision(snakeHead, food )) {
            snakeBody.add(new Tile(food.x , food.y));
            placeFood();
        }

        //snake Body
        for (int i = snakeBody.size()-1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else {
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        //snake head 
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // game over conditions
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }

        if (snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth || snakeHead.y*tileSize < 0 || snakeHead.y * tileSize > boardHeight) {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {             //this means that we press the up key
            velocityX = 0;
            velocityY = -1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
     }

    //do not need 
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e)  {}
}
