import java.awt.*;
import java.awt.geom.*;

/**
 * A circle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0.  (15 July 2000) 
 */

public class Particle{

    public static final double PI=3.1416;
    
    private int diameter;
    private int xPosition;
    private int yPosition;
    private int xVelocidad;
    private int yVelocidad;
    private String color;
    private boolean isVisible;
    
    /**
     * Constructor for the Circle class.
     * Initializes the circle with default values.
     */
    public Particle(String color, boolean isRed, int xPosition, int yPosition, int xVelocidad, int yVelocidad){
        this.xPosition = 200;
        this.yPosition = 100;
        this.xVelocidad = xVelocidad;
        this.yVelocidad = yVelocidad;
        this.diameter = 15;
        this.color = color;
        this.isVisible = false;
    }
    
    public void move() {
        xPosition += xVelocidad;
        yPosition += yVelocidad;
    }
    
    public void checkBounds(int width, int height) {
        if (xPosition <= 0 || xPosition >= width - diameter) {
            invertVX();
        }
        if (yPosition <= 0 || yPosition >= height - diameter) {
            invertVY();
        }
    }
    
    public int getX(){
        return xPosition; 
    }
    
    public int getY(){ 
        return yPosition; 
    }
    
    public int getVX(){ 
        return xVelocidad; 
    }
    
    public int getVY() { 
        return yVelocidad; 
    }

    public void invertVX(){ 
        xVelocidad = -xVelocidad; 
    }
    
    public void invertVY(){ 
        yVelocidad = -yVelocidad; 
    }
    
    public String getColor(){
        return color;
    }
    
    public boolean isRed() {
        return color.equals("red");
    }
    
    public boolean isInside(Hole h) {
        int holeX = h.getXPosition();
        int holeY = h.getYPosition();
        int holeRadius = h.getRadius();
    
        int deltaX = this.xPosition - holeX;
        int deltaY = this.yPosition - holeY;
        return (deltaX * deltaX + deltaY * deltaY) <= (holeRadius * holeRadius);
    }
    
    public void setX(int x) {
        this.xPosition = x;
    }

    public void setY(int y) {
        this.yPosition = y;
    }
    
    public void atravesarParedCentral() {
        if (xVelocidad > 0) {
            xPosition += 2;
        } else {
            xPosition -= 2;
        }
    }

    /** Makes the circle visible on the canvas. 
    */  
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    
    /** Makes the circle invisible on the canvas. 
    */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    
    public int getDiameter(){
        return diameter;
    }

    /** Draws the circle on the canvas if it is visible. 
    */
    private void draw(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, 
                new Ellipse2D.Double(xPosition, yPosition, 
                diameter, diameter));
            canvas.wait(10);
        }
    }

    /** Erases the circle from the canvas. 
    */
    private void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
    
    /**
     * Move the circle a few pixels to the right.
     */
    public void moveRight(){
        moveHorizontal(20);
    }

    /**
     * Move the circle a few pixels to the left.
     */
    public void moveLeft(){
        moveHorizontal(-20);
    }

    /**
     * Move the circle a few pixels up.
     */
    public void moveUp(){
        moveVertical(-20);
    }

    /**
     * Move the circle a few pixels down.
     */
    public void moveDown(){
        moveVertical(20);
    }

    /**
     * Move the circle horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Move the circle vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance){
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Slowly move the circle horizontally.
     * @param distance the desired distance in pixels
     */
    public void slowMoveHorizontal(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            xPosition += delta;
            draw();
        }
    }

    /**
     * Slowly move the circle vertically
     * @param distance the desired distance in pixels
     */
    public void slowMoveVertical(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        }else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            yPosition += delta;
            draw();
        }
    }

    /**
     * Change the size.
     * @param newDiameter the new size (in pixels). Size must be >=0.
     */
    public void changeSize(int newDiameter){
        erase();
        diameter = newDiameter;
        draw();
    }

    /**
     * Change the color. 
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    public void changeColor(String newColor){
        color = newColor;
        draw();
    }

}
