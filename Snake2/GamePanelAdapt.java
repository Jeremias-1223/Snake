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
    private AbstractSnake snakePlayer1;
    private AbstractSnake snakePlayer2;
    private AbstractSnake snakeNPC;
    private java.util.List<Food> foods;
    private boolean Player1over; //Estado de juego de los jugadores (En juego/Perdido)
    private boolean Player2over;

    //Construccion del juego
    public GamePanelAdapt() {
        this.setPreferredSize(new Dimension(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE));
        this.setBackground(Color.WHITE);
        this.setFocusable(true);
        this.addKeyListener(this);

        snakePlayer1 = new Snake(WIDTH, HEIGHT, Color.GREEN);
        snakePlayer2 = new Snake(WIDTH, HEIGHT, Color.RED);
        snakeNPC = new Snake_NPC(WIDTH, HEIGHT, Color.BLACK);
        generateRandomFoodItems();
        timer = new Timer(100, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        snakePlayer1.draw(g, TILE_SIZE);
        snakePlayer2.draw(g, TILE_SIZE);
        snakeNPC.draw(g, TILE_SIZE);
        for (Food f : foods) {
            f.draw(g, TILE_SIZE);
        }

        if (Player1over && Player2over) {
            //Mensaje de perdida cuando ambos jugadores pierden
            timer.stop();
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            String lostText = "Game Over...";
            FontMetrics fm = g.getFontMetrics();

            g.drawString(lostText, (getWidth() - fm.stringWidth(lostText)) / 2, getHeight() / 2);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            String restartText = "Presiona R para reiniciar";

            g.drawString(restartText, (getWidth() - g.getFontMetrics().stringWidth(restartText)) / 2, +40);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!Player1over || !Player2over) {
            snakeNPC.move(foods); //NPC siempre se mueve, recibe la Lista foods para poder dirigirse hacia los objetos Food
            if (!Player1over) {
                snakePlayer1.move();
            }
            if (!Player2over) {
                snakePlayer2.move();
            }

            //Si los jugadores se chocan contra una pared, si mismos o el NPC, pierden
            if ((snakePlayer1.checkCollisionWithWall() || snakePlayer1.checkCollisionWithItself() || snakePlayer1.checkCollisionWithSnake(snakeNPC))) {
                Player1over = true;
            }
            if ((snakePlayer2.checkCollisionWithWall() || snakePlayer2.checkCollisionWithItself() || snakePlayer2.checkCollisionWithSnake(snakeNPC))) {
                Player2over = true;
            }

            //Si una Snake pasa por un Food, crece
            for (Food f : foods) {
                if (snakePlayer1.getHead().equals(f.getPosition())) {
                    snakePlayer1.grow();
                    f.respawn(snakePlayer1);
                }

                if (snakePlayer2.getHead().equals(f.getPosition())) {
                    snakePlayer2.grow();
                    f.respawn(snakePlayer2);
                }
                if (snakeNPC.getHead().equals(f.getPosition())) {
                    snakeNPC.grow();
                    f.respawn(snakeNPC);
                }
            }
            repaint();
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!Player1over || !Player2over) {
            switch (e.getKeyCode()) {
                //Controles Jugador 1 [W,A,S,D]
                case KeyEvent.VK_UP:
                    snakePlayer1.setDirection(Direction.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    snakePlayer1.setDirection(Direction.DOWN);
                    break;
                case KeyEvent.VK_LEFT:
                    snakePlayer1.setDirection(Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    snakePlayer1.setDirection(Direction.RIGHT);
                    break;
                //Controles Jugador 2 [Flechas]
                case KeyEvent.VK_W:
                    snakePlayer2.setDirection(Direction.UP);
                    break;
                case KeyEvent.VK_S:
                    snakePlayer2.setDirection(Direction.DOWN);
                    break;
                case KeyEvent.VK_A:
                    snakePlayer2.setDirection(Direction.LEFT);
                    break;
                case KeyEvent.VK_D:
                    snakePlayer2.setDirection(Direction.RIGHT);
                    break;
            }

        } else {
            //Si ambos jugadores perdieron, se reinicia el juego al presionar 'R'
            if (e.getKeyCode() == KeyEvent.VK_R) {
                resetGame();
            }
        }
    }

    public void resetGame() {
        snakePlayer1 = new Snake(WIDTH, HEIGHT, Color.GREEN);
        snakePlayer2 = new Snake(WIDTH, HEIGHT, Color.RED);
        snakeNPC = new Snake_NPC(WIDTH, HEIGHT, Color.BLACK);

        generateRandomFoodItems();

        Player1over = false;
        Player2over = false;
        timer.start();
        repaint();
    }

    //Genera una cantidad aleatoria inicial de Food, esta cantidad sera constante durante el resto del juego
    private void generateRandomFoodItems() {
        Random rand = new Random();
        foods = new java.util.ArrayList<>();
        int contador = 1 + rand.nextInt(8);

        for (int i = 0; i < contador; i++) {
            foods.add(new Food(WIDTH, HEIGHT, snakePlayer1));

        }

    }
    //no utilizado
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
