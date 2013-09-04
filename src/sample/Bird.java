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
    private final int turnRadius = 5;
    private List<Bird> otherBirds;

    public Bird(int radius){
        super(3, Color.BLACK);
        this.radius = radius;
        dir = Math.random()*360;
    }

    public void update(){
//        double cohDir = applyCohesion();
        double sepDir = applySeparation();
//        dir = steerTowards(cohDir);
        moveInDir(sepDir);

        setLayoutX(x);
        setLayoutY(y);
    }

    private double applySeparation() {
        double totalDir = 0;
        int birdsInRange = 0;
        for(Bird b : otherBirds){
            if(inRange(b)){
                totalDir += calcAngleToPos(b.x,b.y) - 180;
                birdsInRange++;
            }
        }
        if(birdsInRange != 0){
            return totalDir / birdsInRange;
        }
        return dir;
    }

    private double steerTowards(double dir) {
        if(Math.abs(dir - this.dir) < turnRadius){
            return dir;
        }
        if(dir > this.dir){
            return this.dir + turnRadius;
        }else{
            return this.dir - turnRadius;
        }
    }

    private void moveInDir(double angle) {
        x += (speed * Math.cos(Math.toRadians(angle - 90)));
        y += (speed * -Math.sin(Math.toRadians(angle - 90)));
    }

    private double applyCohesion() {
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
            return calcAngleToPos(avgX,avgY);
        }
        return dir;
    }

    private double calcAngleToPos(double newX, double newY) {
        double dx = newX - x;
        double dy = newY - y;
        return Math.atan2(dx, dy) * 180 / Math.PI;
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
