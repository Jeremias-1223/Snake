package Snake2;
import java.awt.*;
import java.util.Random;

public class Food implements DrawInterface{
    private Point position;
    private final int width, height;
    private Color color;

    public Food(int width, int height, AbstractSnake snake) {
        this.width = width;
        this.height = height;
        respawn(snake);
    }

    public void respawn(AbstractSnake snake) {
        Random rand = new Random(); //Posicion aleatoria del nuevo Food
        do {
            position = new Point(rand.nextInt(width), rand.nextInt(height));
        } while (snake.contains(position));
        color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)); //Color aleatorio
    }

    public void draw(Graphics g, int size) {
        g.setColor(color);
        g.fillOval(position.x * size, position.y * size, size, size);
    }

    public Point getPosition() {
        return position;
    }
}