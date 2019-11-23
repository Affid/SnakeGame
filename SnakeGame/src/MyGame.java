
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.*;
import org.newdawn.slick.command.*;

public class MyGame extends BasicGame implements  InputProviderListener{
    private int[] lastAction = new int[2];
    private int count=0;
    protected static final int STEP =40, HEIGHT =768, WIDTH =1366;
    private Command left = new BasicCommand("LEFT");
    private Command right = new BasicCommand("RIGHT");
    private Command up = new BasicCommand("UP");
    private Command down = new BasicCommand("DOWN");
    private String title;
    private Snake snake;
    private String message=right.toString();
    private static ArrayList<Image> img = new ArrayList<>();

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
        gameContainer.getGraphics().setBackground(Color.darkGray    );
        provider.addListener(this);
        provider.bindCommand(new KeyControl(Input.KEY_LEFT), left);
        provider.bindCommand(new KeyControl(Input.KEY_RIGHT), right);
        provider.bindCommand(new KeyControl(Input.KEY_DOWN), up);
        provider.bindCommand(new KeyControl(Input.KEY_UP), down);
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

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        if(message.equals(left.toString())&&snake.getFirst().getX()> STEP &&snake.get(snake.size()-snake.size()).getX()<=snake.get(snake.size()-snake.size()+1).getX()) //!prevMessage.equals(right.toString()
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
        count++;
        if(count==100){
            img.add(new Image("D:\\work\\12.png"));
            snake.addCell();
            count=0;
        }
        try {
            Thread.sleep(170);
        }catch (InterruptedException e){
            snake.size();
        }
    }

    public void controllerLeftPressed(int controller) {

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {

        for(int i=snake.size()-1;i>=0;i--){
            img.get(i).draw(snake.get(i).getX(),snake.get(i).getY());
        }
    }

    public static void main(String[] args) {
        try{
            AppGameContainer appGC;
            appGC = new AppGameContainer(new MyGame("SnakeGame"));
            appGC.setDisplayMode(WIDTH, HEIGHT,false);
            appGC.setShowFPS(false);
            appGC.start();

        }catch (SlickException e){
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE,null,e);
        }
    }
}
