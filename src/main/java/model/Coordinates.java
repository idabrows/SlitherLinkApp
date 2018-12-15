package model;

public class Coordinates {
    private int x;
    private int y;
    private int value;
    public Coordinates(int x,int y,int newValue){
        this.x=x;
        this.y=y;
        this.value=newValue;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
