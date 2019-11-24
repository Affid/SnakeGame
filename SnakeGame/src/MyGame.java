
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.*;
import org.newdawn.slick.command.*;

public class MyGame extends BasicGame implements  InputProviderListener{
    private int[] lastAction = new int[2];
    private int count=0,coin=0;
    private static int delay=170;
    protected static final int STEP =40, HEIGHT =768, WIDTH =1366;
    private Command left = new BasicCommand("LEFT");
    private Command right = new BasicCommand("RIGHT");
    private Command up = new BasicCommand("UP");
    private Command down = new BasicCommand("DOWN");
    private Command click = new BasicCommand("CLICK");
    private String title;
    private Snake snake;
    private String message=right.toString();
    private static ArrayList<Image> img = new ArrayList<>();
    private Apple apple;
    private boolean isStopped=false;
    private static AppGameContainer appGC;

    @Override
    public void controlReleased(Command command) {
    }

    @Override
    public boolean closeRequested() {
        return true;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public int getCount(){
        return count;
    }

    public void setCount(int newCount){
        if(newCount>0){
            coin+=newCount-count;
            count=newCount;
        }
    }



    @Override
    public void controlPressed(Command command) {
        message = command.toString();
    }



    private MyGame(String gamename){
        super(gamename);
        this.title = gamename;
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        InputProvider provider = new InputProvider(gameContainer.getInput());
        gameContainer.getGraphics().setBackground(new Color(1,50,32));
        apple = Apple.getApple();
        Thread appleThread = new Thread(apple);
        appleThread.start();
        provider.addListener(this);
        provider.bindCommand(new KeyControl(Input.KEY_LEFT), left);
        provider.bindCommand(new KeyControl(Input.KEY_RIGHT), right);
        provider.bindCommand(new KeyControl(Input.KEY_DOWN), up);
        provider.bindCommand(new KeyControl(Input.KEY_UP), down);
        provider.bindCommand(new MouseButtonControl(Input.MOUSE_LEFT_BUTTON),click);
        snake = Snake.getSnake();
        snake.addCell();
        snake.addCell();
        snake.addCell();
        snake.addCell();
        snake.addCell();
        img.add(new Image("D:\\work\\13.png"));
        img.add(new Image("D:\\work\\12.png"));
        img.add(new Image("D:\\work\\12.png"));
        img.add(new Image("D:\\work\\12.png"));
        img.add(new Image("D:\\work\\12.png"));
        img.add(new Image("D:\\work\\12.png"));

    }

    private void checkSnakePos(){
        if(Math.abs(snake.getFirst().getX()-apple.getX())<40&&Math.abs(snake.getFirst().getY()-apple.getY())<40){
            apple.eat(this);
        }
    }

    private void messageAnalize(){
        if(message.equals(left.toString())&&snake.getFirst().getX()> STEP &&snake.get(snake.size()-snake.size()).getX()<=snake.get(snake.size()-snake.size()+1).getX())
        {
            lastAction[0] = -STEP;
            lastAction[1] = 0;
            snake.move(-STEP, 0);
        }else
        if(message.equals(right.toString())&&snake.getFirst().getX()< WIDTH -2* STEP &&snake.get(snake.size()-snake.size()).getX()>=snake.get(snake.size()-snake.size()+1).getX())
        {
            lastAction[0] = STEP;
            lastAction[1] = 0;
            snake.move(STEP,0);
        }
        else
        if(message.equals(up.toString())&&snake.getFirst().getY()< HEIGHT -2* STEP &&snake.get(snake.size()-snake.size()).getY()>=snake.get(snake.size()-snake.size()+1).getY())
        {
            lastAction[0] = 0;
            lastAction[1] = STEP;
            snake.move(0, STEP);
        }
        else
        if(message.equals(down.toString())&&snake.getFirst().getY()> STEP &&snake.get(snake.size()-snake.size()).getY()<=snake.get(snake.size()-snake.size()+1).getY())
        {
            lastAction[0] = 0;
            lastAction[1] = -STEP;
            snake.move(0,-STEP);
        }
        else {
            if (!(snake.getFirst().getX() > STEP)||!(snake.getFirst().getX() < WIDTH -2* STEP))
                lastAction[0]=0;
            if(!(snake.getFirst().getY() > STEP)||!(snake.getFirst().getY() < HEIGHT -2* STEP))
                lastAction[1]=0;
            snake.move(lastAction[0], lastAction[1]);
        }
    }

    public void stopIt(){
        isStopped=true;
    }

    private void growSnake()throws SlickException{
        if (coin >= 8) {
            coin = 0;
            snake.addCell();
            img.add(new Image("D:\\work\\12.png"));
        }
    }

    public void speedUp(){
        if(message.equals(click.toString())&&count>2) {
            delay = 100;
            System.out.println(delay);
            count-=2;
        }
        else
            delay=170;
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        apple = Apple.getApple();
        checkSnakePos();
        messageAnalize();
        growSnake();
        speedUp();
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            snake.size();
        }
    }

    public void controllerLeftPressed(int controller) {

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        if (!isStopped) {
            graphics.drawString("Coins: " + this.count, 1250, 50);
            apple.draw(apple.getX(), apple.getY());
            for (int i = snake.size() - 1; i >= 0; i--) {
                img.get(i).draw(snake.get(i).getX(), snake.get(i).getY());
            }
        } else {
            graphics.drawString("Your lost!", 610, 350);
            graphics.drawString("Your coins: " + this.count, 610, 380);
        }
    }

    public static void main(String[] args) {
        try{
            appGC = new AppGameContainer(new MyGame("SnakeGame"));
            appGC.setDisplayMode(WIDTH, HEIGHT,false);
            appGC.setShowFPS(false);
            appGC.start();

        }catch (SlickException e){
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    public static class SpeedControl extends Thread{
        @Override
        public void run() {

        }
    }
}
