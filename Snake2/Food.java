package Snake2;
import Snake2.Snake;
import java.awt.*;
import java.util.Random;

public class Food {
    private Point position;
    private int width, height;
    private Random rand = new Random();
    private Color color;

    public Food(int width, int height, Snake_B snake) {
        this.width = width;
        this.height = height;
        respawn(snake);
    }

    public void respawn(Snake_B snake) {
        do {
            position = new Point(rand.nextInt(width), rand.nextInt(height));
        } while (snake.contains(position));
        color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    public void draw(Graphics g, int size) {
        g.setColor(color);
        g.fillOval(position.x * size, position.y * size, size, size);
    }

    public Point getPosition() {
        return position;
    }
}