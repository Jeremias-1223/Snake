package Snake2;

import java.awt.*;
import java.util.LinkedList;

public class Snake extends Snake_B {

    public Snake (double width, double height, Color color, boolean NPC){
        super (width, height, color, NPC);
    }

    @Override
    public void setDirection(Direction dir) {
        if ((dir == Direction.UP && direction != Direction.DOWN) ||
                (dir == Direction.DOWN && direction != Direction.UP) ||
                (dir == Direction.LEFT && direction != Direction.RIGHT) ||
                (dir == Direction.RIGHT && direction != Direction.LEFT)) {
            direction = dir;
        }
    }
}