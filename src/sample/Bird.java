package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas
 * Date: 3-9-13
 */
public class Bird extends Circle{

    public double x;
    public double y;

    private int speed = 2;
    private double dir;

    private final int radius;
    private final int turnRadius = 2;
    private List<Bird> otherBirds;

    public Bird(int radius){
        super(3, Color.BLACK);
        this.radius = radius;
        dir = Math.random()*360;
    }

    public void update(){
        averagePosition();
        moveInDir(dir);

        setLayoutX(x);
        setLayoutY(y);
    }

    private void moveInDir(double angle) {
        x += (speed * Math.cos(Math.toRadians(angle - 90)));
        y += (speed * -Math.sin(Math.toRadians(angle - 90)));
    }

    private void averagePosition() {
        double totalX = 0;
        double totalY = 0;
        int birdsInRange = 0;
        for(Bird b : otherBirds){
            if(inRange(b)){
                totalX += b.x;
                totalY += b.y;
                birdsInRange++;
            }
        }
        if(birdsInRange != 0){
            double avgX = totalX / birdsInRange;
            double avgY = totalY / birdsInRange;
            dir = calcAngleToPos(avgX,avgY);
        }
    }

    private double calcAngleToPos(double newX, double newY) {
        double dx = newX - x;
        double dy = newY - y;
        double angle = Math.atan2(dx, dy) * 180 / Math.PI;
        System.out.println(angle);
        return angle;
    }

    private boolean inRange(Bird b){
        double aSide = Math.abs(x - b.x);
        double bSide = Math.abs(y - b.y);
        double cSide = Math.sqrt((aSide*aSide)+(bSide*bSide));
        return cSide < radius;
    }

    //Getters and Setters
    public void setOtherBirds(List<Bird> otherBirds) {
        this.otherBirds = otherBirds;
        if (otherBirds.contains(this)){
            otherBirds.remove(this);
        }
    }
}
