import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Apple extends Image implements Runnable, Eatable{
    private static boolean isStopped = false;
    private static int count=0;
    private int x,y,coins;
    private static Apple rotApple, freshApple,deadApple,empty;
    private static volatile Apple apple;

    @Override
    public void eat(MyGame game) {
            if(apple.coins==-1) {
                isStopped = true;
                game.stopIt();
            }
            else {
                game.setCount(game.getCount()+apple.coins);
                apple = empty;
            }
    }

    @Override
    public void rot() {
        int x = apple.x;
        int y= apple.y;
            apple = rotApple;
            apple.x = x;
            apple.y = y;
    }

    @Override
    public void dead() {
        int x = apple.x;
        int y= apple.y;
            apple = deadApple;
            apple.x = x;
            apple.y = y;
    }
    
    private Apple(String path) throws SlickException {
        super(path);
        apple = this;
        count+=1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void run() {
        while (!isStopped) {
            try {
                Thread.sleep(4000);
                rot();
                Thread.sleep(3000);
                dead();
                Thread.sleep(1800);
            }catch (InterruptedException e){}
                apple = freshApple;
                apple.x = (int)(Math.random()*1200+MyGame.STEP);
                apple.y = (int)(Math.random()*680+MyGame.STEP);

        }
    }

    public static Apple getApple() {
        if(apple==null){
            try {
                freshApple = new Apple("D:\\work\\apple.png");
                rotApple = new Apple("D:\\work\\rotApple.png");
                deadApple = new Apple("D:\\work\\rotRotApple.png");
                empty = new Apple("D:\\work\\empty.png");
                freshApple.coins=2;
                rotApple.coins=1;
                deadApple.coins=-1;
                empty.coins=0;
                empty.x=-40;
                empty.y=-40;
                apple = freshApple;
                apple.x = (int)(Math.random()*1200+MyGame.STEP);
                apple.y = (int)(Math.random()*680+MyGame.STEP);

            }catch (SlickException e){}
        }
        return apple;
    }

    public void resetCount(){
        count=0;
    }
}
