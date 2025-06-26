package Snake2;
import javax.swing.text.Position;
import java.awt.*;

public class Snake_NPC extends Snake_B{
    public Snake_NPC(double width, double height, Color color) {
        super(width, height, color, true);
    }

    public void AppleSense(java.util.List<Food> foods){
        Point p= new Point();
        boolean flag = false;
        double vector=0.0;
        for (Food food : foods ){
            if (!flag || vector> Math.sqrt((food.getPosition().x -getHead().x)*(food.getPosition().x -getHead().x))+((food.getPosition().y -getHead().y)*(food.getPosition().y -getHead().y))) {
                flag=true;
                p = food.getPosition();
                vector =Math.sqrt((food.getPosition().x -getHead().x)*(food.getPosition().x -getHead().x))+((food.getPosition().y -getHead().y)*(food.getPosition().y -getHead().y));
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
            setDirection(Direction.LEFT);
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
