package Snake2;

import java.awt.*;

public class Snake_NPC extends Snake_B{
    private Food Target;
    public Snake_NPC(double width, double height, Color color) {
        super(width, height, color, true);
    }

    public void AppleSense(java.util.List<Food> foods){
        Point p= new Point();
        double vector=0.0;
        if (Target!=null){
            p=Target.getPosition();
            vector = Math.sqrt((p.x - getHead().x) * (p.x - getHead().x)) + ((p.y - getHead().y) * (p.y - getHead().y));
        }
        if (!foods.contains(Target)) {
            for (Food food : foods) {
                if (((vector==0.0) || vector >= Math.sqrt((food.getPosition().x - getHead().x) * (food.getPosition().x - getHead().x)) + ((food.getPosition().y - getHead().y) * (food.getPosition().y - getHead().y)))) {
                    p = food.getPosition();
                    Target = food;
                    vector = Math.sqrt((p.x - getHead().x) * (p.x - getHead().x)) + ((p.y - getHead().y) * (p.y - getHead().y));

                }
            }
        }

        if (p.y<getHead().y){
            setDirection(Direction.UP);
        }else if (p.y>getHead().y){
            setDirection(Direction.DOWN);
        }
        if (p.x<getHead().x){
            setDirection(Direction.LEFT);
        }else if (p.x>getHead().x){
            setDirection(Direction.RIGHT);
        }

        if (checkCollisionWithWall()) {
            switch (direction) {
                case UP:
                    setDirection(Direction.LEFT);
                    break;
                    case DOWN:
                        setDirection(Direction.RIGHT);
                        break;
                        case LEFT:
                            setDirection(Direction.UP);
                            break;
                            case RIGHT:
                                setDirection(Direction.DOWN);
            }
        }
    }


    public void move(java.util.List<Food> foods) {

        Point head = getHead();
        Point newHead = new Point(head);

        AppleSense(foods);
        switch(direction) {
            case UP:    newHead.y--; break;
            case DOWN:  newHead.y++; break;
            case LEFT:  newHead.x--; break;
            case RIGHT: newHead.x++; break;
        }

        body.addFirst(newHead);
        body.removeLast();

    }
}
