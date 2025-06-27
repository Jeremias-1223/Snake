package Snake2;

import java.awt.*;

public class Snake_NPC extends AbstractSnake{
    private Food Target; //Evita bucles en casos donde hay comidas equidistantes
    public Snake_NPC(int width, int height, Color color) {
        super(width, height, color, true);
    }

    //Permite que el NPC busque un Food
    public void AppleSense(java.util.List<Food> foods){
        Point p= new Point();
        if (Target!=null){
            //Si ya hay una Food en la mira, se utiliza para mantener su camino
            p=Target.getPosition();
        }
        if (!foods.contains(Target)) {
            p = foods.getFirst().getPosition();
            Target = foods.getFirst();
        }

        //Direccionamiento de NPC
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

        //Evita colisiones con la pared y que el NPC se vaya del mapa
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
                    break;
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
