package Snake2;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public abstract class AbstractSnake implements DrawInterface{

    protected LinkedList<Point> body;
    protected Direction direction;
    private final int width, height;
    private final Color snakeColor;

    public AbstractSnake(int width, int height, Color snakeColor, boolean NPC) {
        body = new LinkedList<>();
        Random rand = new Random();
        int i = rand.nextInt(4);
        int SpawnX;
        int SpawnY;

        if (!NPC) { //Spawn random solo para jugadores
            switch (i) {
                case 0: //Spawn en el 1er cuadrante
                    SpawnX = width / 4;
                    SpawnY = height / 4;
                    body.add(new Point(SpawnX, SpawnY));
                    break;
                case 1: //Spawn en el 2do cuadrante
                    SpawnX = (int) (width * 0.75);
                    SpawnY = (int) (height * 0.75);
                    body.add(new Point(SpawnX, SpawnY));
                    break;
                case 2: //Spawn en el 3er cuadrante
                    SpawnX = width / 4;
                    SpawnY = (int) (height * 0.75);
                    body.add(new Point(SpawnX, SpawnY));
                    break;
                case 3: //Spawn en el 4to cuadrante
                    SpawnX = (int) (width * 0.75);
                    SpawnY = height / 4;
                    body.add(new Point(SpawnX, SpawnY));
            }
        } else{ //Spawn del NPC en el centro
            body.add(new Point(width/ 2, height/ 2));
        }
        this.width = width;
        this.height = height;
        this.snakeColor = snakeColor;
        direction = Direction.LEFT;
    }

    public void move() {
        Point head = getHead();
        Point newHead = new Point(head);

        switch(direction) {
            case UP:    newHead.y--; break;
            case DOWN:  newHead.y++; break;
            case LEFT:  newHead.x--; break;
            case RIGHT: newHead.x++; break;
        }

        body.addFirst(newHead);
        body.removeLast();
    }

    //Polimorfismo para poder utilizar AppleSense en Snake_NPC
    public void move(java.util.List<Food> foods){}

    public void grow() {
        Point tail = body.getLast();
        body.addLast(new Point(tail));
    }

    public void setDirection(Direction dir) {
        if ((dir == Direction.UP && direction != Direction.DOWN) ||
                (dir == Direction.DOWN && direction != Direction.UP) ||
                (dir == Direction.LEFT && direction != Direction.RIGHT) ||
                (dir == Direction.RIGHT && direction != Direction.LEFT)) {
            direction = dir;
        }
    }

    public Point getHead() {
        return body.getFirst();
    }

    public void draw(Graphics g, int size) {
        g.setColor(snakeColor);
        for (Point p : body) {
            g.fillRect(p.x * size, p.y * size, size, size);
        }
    }

    public boolean checkCollisionWithWall() {
        Point head = getHead();
        return head.x < 0 || head.y < 0 || head.x >= width || head.y >= height;
    }

    public boolean checkCollisionWithItself() {
        Point head = getHead();
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) return true;
        }
        return false;
    }

    public boolean checkCollisionWithSnake(AbstractSnake snake) {
        Point head = getHead();
        return snake.contains(head);
    }

    public boolean contains(Point p) {
        return body.contains(p);
    }
}

