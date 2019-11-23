public class SnakeCell {
    private int x,y,prevX,prevY;

    public SnakeCell(int x,int y){
        this.x = x;
        this.y = y;
        prevY = this.y;
        prevX = this.x-MyGame.STEP;
    }

    public int getPrevX() {
        return prevX;
    }

    public int getPrevY() {
        return prevY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setXY(int x,int y) {
        this.prevY = this.y;
        this.y = y;
        this.prevX = this.x;
        this.x = x;
    }

}
