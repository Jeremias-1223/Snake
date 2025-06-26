package Snake2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanelAdapt extends JPanel implements ActionListener, KeyListener {
    private final int TILE_SIZE = 25;
    private final int WIDTH = 30;
    private final int HEIGHT = 30;
    private Timer timer;
    private Snake_B snake;
    private Snake_B snake2;
    private Snake_B snakeNPC;
    private java.util.List<Food> foods;
    private boolean gameOver;

    public GamePanelAdapt() {
        this.setPreferredSize(new Dimension(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE));
        this.setBackground(Color.WHITE);
        this.setFocusable(true);
        this.addKeyListener(this);

        snake = new Snake(WIDTH, HEIGHT, Color.GREEN);
        snake2 = new Snake(WIDTH, HEIGHT, Color.RED);
        snakeNPC = new Snake_NPC(WIDTH,HEIGHT, Color.BLACK);
        generateRandomFoodItems();
        timer = new Timer(100, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        snake.draw(g, TILE_SIZE);
        snake2.draw(g, TILE_SIZE);
        snakeNPC.draw(g, TILE_SIZE);
        for (Food f : foods) {
            f.draw(g, TILE_SIZE);
        }

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            String lostText = "Game Over..";
            FontMetrics fm = g.getFontMetrics();

            g.drawString(lostText, (getWidth() - fm.stringWidth(lostText)) / 2, getHeight() / 2);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            String restartText = "Presiona R para reiniciar";

            g.drawString(restartText, (getWidth() - g.getFontMetrics().stringWidth(restartText)) / 2,  + 40);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            snake.move();
            snake2.move();
            snakeNPC.move(foods);
            if ((snake.checkCollisionWithWall() || snake.checkCollisionWithItself()) && (snake2.checkCollisionWithWall() || snake2.checkCollisionWithItself())) {
                gameOver = true;
                timer.stop();
            }
                for(Food f : foods){
                    if(snake.getHead().equals(f.getPosition())){
                        snake.grow();
                        f.respawn(snake);}

                    if(snake2.getHead().equals(f.getPosition())){
                        snake2.grow();
                        f.respawn(snake2);
                    }
                    if(snakeNPC.getHead().equals(f.getPosition())){
                        snakeNPC.grow();
                        f.respawn(snakeNPC);
                    }
            }
            repaint();
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    snake.setDirection(Direction.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    snake.setDirection(Direction.DOWN);
                    break;
                case KeyEvent.VK_LEFT:
                    snake.setDirection(Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    snake.setDirection(Direction.RIGHT);
                    break;
                case KeyEvent.VK_W:
                    snake2.setDirection(Direction.UP );
                    break;
                case KeyEvent.VK_S:
                    snake2.setDirection(Direction.DOWN );
                    break;
                case KeyEvent.VK_A:
                    snake2.setDirection(Direction.LEFT );
                    break;
                case KeyEvent.VK_D:
                    snake2.setDirection(Direction.RIGHT );
                    break;
            }

        }
        else{
            if(e.getKeyCode() == KeyEvent.VK_R){
                resetGame();
            }
        }
    }


    public void resetGame(){
        snake = new Snake(WIDTH, HEIGHT, Color.GREEN);
        snake2 = new Snake(WIDTH, HEIGHT, Color.RED);
        snakeNPC = new Snake_NPC(WIDTH,HEIGHT, Color.BLACK);

        generateRandomFoodItems();

        gameOver = false;
        timer.start();
        repaint();
    }

    private void generateRandomFoodItems() {
        Random rand = new Random();
        foods = new java.util.ArrayList<>();
        int contador = 1 + rand.nextInt(8);
        contador=1;

        for(int i = 0; i<contador; i++){
            foods.add(new Food(WIDTH, HEIGHT, snake));

        }

    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}