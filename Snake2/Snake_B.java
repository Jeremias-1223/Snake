package Snake2;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public abstract class Snake_B {

    protected LinkedList<Point> body;
    protected Direction direction;
    private final Double width, height;
    private final Color color;

    public Snake_B (Double width, Double height, Color color, boolean NPC) {

        body = new LinkedList<>();

            Random rand = new Random();
            int i = rand.nextInt(4);
            if (!NPC) {
                switch (i) {
                    case 0:
                        Double SpawnX = 0.0;
                        Double SpawnY = 0.0;
                        SpawnX = width / 4;
                        SpawnY = height / 4;
                        body.add(new Point(SpawnX.intValue(), SpawnY.intValue()));
                        break;
                    case 1:
                        SpawnX = width * 0.75;
                        SpawnY = height * 0.75;
                        body.add(new Point(SpawnX.intValue(), SpawnY.intValue()));
                        break;
                    case 2:
                        SpawnX = width / 4;
                        SpawnY = height * 0.75;
                        body.add(new Point(SpawnX.intValue(), SpawnY.intValue()));
                        break;
                    case 3:
                        SpawnX = width * 0.75;
                        SpawnY = height / 4;
                        body.add(new Point(SpawnX.intValue(), SpawnY.intValue()));
                }
            } else{
                body.add(new Point(width.intValue() / 2, height.intValue() / 2));
            }

        this.width = width;
        this.height = height;
        this.color = color;

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

    public void move(java.util.List<Food> foods){}

    public void grow() {
        Point tail = body.getLast();
        body.addLast(new Point(tail));
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
        g.setColor(color);
        for (Point p : body) {
            g.fillRect(p.x * size, p.y * size, size, size);
        }
    }

    public boolean checkCollisionWithWall() {
        Point head = getHead();
        return head.x < 0 || head.y < 0 || head.x >= width.intValue() || head.y >= height.intValue();
    }

    public boolean checkCollisionWithItself() {
        Point head = getHead();
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) return true;
        }
        return false;
    }

    public boolean checkCollisionWithSnake(Snake_B snake) {
        Point head = getHead();
        for (int i = 1; i < snake.body.size(); i++) {
            if (head.equals(snake.body.get(i))) return true;
        }
        return false;
    }

    public boolean contains(Point p) {
        return body.contains(p);
    }
}

