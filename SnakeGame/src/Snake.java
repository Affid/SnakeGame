import java.util.LinkedList;

public class Snake extends LinkedList<SnakeCell> {
    private static Snake snake;


    private Snake(SnakeCell first){
        super();
        snake = this;
        snake.add(first);
    }

    public void addCell(){

        snake.add(new SnakeCell(snake.getLast().getPrevX(),snake.getLast().getPrevY()));
    }

    public static Snake getSnake() {
        if(snake==null){
            snake = new Snake(new SnakeCell(MyGame.STEP*6,MyGame.STEP));
        }
        return snake;
    }

    public void move(int x, int y){
        if(x!=0||y!=0) {
            if (x != 0) {
                snake.getFirst().setXY(snake.getFirst().getX() + x, snake.getFirst().getY());
            }
            if (y != 0) {
                snake.getFirst().setXY(snake.getFirst().getX(), snake.getFirst().getY() + y);
            }
            for (int i = 1; i < snake.size(); i++) {
                snake.get(i).setXY(snake.get(i - 1).getPrevX(), snake.get(i - 1).getPrevY());
            }
        }
    }


}
