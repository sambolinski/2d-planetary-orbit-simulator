/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.city.acwf566.objects;

import java.awt.Image;
import java.awt.image.BufferedImage;
import uk.ac.city.acwf566.math.Vec2f;
import uk.ac.city.acwf566.math.Constants;
import uk.ac.city.acwf566.math.ExponentArithmetic;
/**
 *
 * @author Sam
 */
public class Body {
    private String id;
    private String name;
    private BufferedImage image;
    private String standardPixelLength = "6.957E6";
    //physical properties
    private String mass;
    private float density;
    private String radius;
    //mathematical properties
    private int mathsMass;
    private Vec2f velocity = new Vec2f(0,0);
    private Vec2f position;
    private float ringSize = 1;
    public Body(String radius, String mass){
        this.mass = mass;
        this.radius = radius;
        
        calculateDensity();
    }
    public void updatePhysicalProperties(String constantValue, String changed, String firstValue, String secondValue){
        if(constantValue.equals("radius")){
            mass = firstValue;
            density = Integer.parseInt(secondValue);
            if(changed.equals("mass")){
                calculateDensity();
            }else{
                calculateMass();
            }
        }else if(constantValue.equals("mass")){
            radius = firstValue;
            density = Float.parseFloat(secondValue);
            if(changed.equals("radius")){
                calculateDensity();
            }else{
                calculateRadius();
            }
        }else{
            mass = firstValue;
            radius = secondValue;
            if(changed.equals("mass")){
                calculateRadius();
            }else{
                calculateMass();
            } 
        }
    }
    public void calculateMass(){
        mass = ExponentArithmetic.multiply(calculateVolume().split("E")[0], calculateVolume().split("E")[1], Float.toString(density), "0");
        setMass(mass);
    }
    public void calculateRadius(){
        String numerator = ExponentArithmetic.multiply("3", "0", mass.split("E")[0], mass.split("E")[1]);
        String denominator = ExponentArithmetic.multiply(Double.toString(4*Math.PI*density), "0", "1", "0");
        String beforeCube = ExponentArithmetic.divide(numerator.split("E")[0], numerator.split("E")[1], denominator.split("E")[0], denominator.split("E")[1]);
        radius = ExponentArithmetic.root(beforeCube, 3);
    }
    public void calculateDensity(){
        density = (int)ExponentArithmetic.convertToNumber(ExponentArithmetic.divide(mass.split("E")[0], mass.split("E")[1], calculateVolume().split("E")[0], calculateVolume().split("E")[1]));
    }
    public String calculateVolume(){
        return ExponentArithmetic.multiply(ExponentArithmetic.power(radius, 3).split("E")[0], ExponentArithmetic.power(radius, 3).split("E")[1], Double.toString((4*Math.PI)/3), "0");
    }
    public String calculateSurfaceArea(){
        return ExponentArithmetic.multiply(ExponentArithmetic.power(radius, 2).split("E")[0], ExponentArithmetic.power(radius, 2).split("E")[0], Double.toString(4*Math.PI), "0");
    }
    public String calculateSchwarzschildRadius(){
        String massMantissa = mass.split("E")[0];
        String massExponent = mass.split("E")[1];
        String gravMantissa = Constants.GRAVITATIONAL_CONSTANT.split("E")[0];
        String gravExponent = Constants.GRAVITATIONAL_CONSTANT.split("E")[1];
        String numeratorFirstStep = ExponentArithmetic.multiply(massMantissa, massExponent, gravMantissa, gravExponent);
        String numeratorSecondStep = ExponentArithmetic.multiply("2", "0", numeratorFirstStep.split("E")[0], numeratorFirstStep.split("E")[1]);
        String denominator = ExponentArithmetic.multiply(Double.toString(Math.pow(Constants.LIGHT_SPEED, 2)),"0","1","0");
        return ExponentArithmetic.divide(numeratorSecondStep.split("E")[0], numeratorSecondStep.split("E")[1], denominator.split("E")[0], denominator.split("E")[1]);
    }
    public String calculateSurfaceGravity(){
        String massMantissa = mass.split("E")[0];
        String massExponent = mass.split("E")[1];
        String gravMantissa = Constants.GRAVITATIONAL_CONSTANT.split("E")[0];
        String gravExponent = Constants.GRAVITATIONAL_CONSTANT.split("E")[1];
        String numerator = ExponentArithmetic.multiply(gravMantissa, gravExponent, massMantissa, massExponent);
        String denominator = ExponentArithmetic.power(radius, 2);
        return ExponentArithmetic.divide(numerator.split("E")[0], numerator.split("E")[1], denominator.split("E")[0], denominator.split("E")[1]);
    }
    //Movements
    public void accelerate(Vec2f acceleration){
        this.velocity.addTo(acceleration);
        
    }
    public double angleTo(Body secondBody){
        return Math.atan2(secondBody.getPosition().y - this.getPosition().y, secondBody.getPosition().x - this.getPosition().x);
    }
    public int distanceTo(Body secondBody){
        return (int)Math.sqrt(Math.pow((secondBody.getPosition().x+secondBody.getPixelSize()/2)-(this.getPosition().x+this.getPixelSize()/2), 2)+Math.pow((secondBody.getPosition().y+secondBody.getPixelSize()/2)-(this.getPosition().y+this.getPixelSize()/2), 2));
    }
    public void gravitateTo(Body secondBody){
        Vec2f gravity = new Vec2f(0,0);
        int distance = this.distanceTo(secondBody);
        gravity.setLength((secondBody.mathsMass) / (float)Math.pow(distance, 2));
        gravity.setAngle((float)this.angleTo(secondBody));
        velocity.addTo(gravity);
    }
    public void update(){
        position.addTo(velocity);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public Vec2f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vec2f velocity) {
        this.velocity = velocity;
    }

    public Vec2f getPosition() {
        return position;
    }

    public void setPosition(Vec2f position) {
        this.position = position;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
    public int toPixel(String number){
        return (int)ExponentArithmetic.convertToNumber(ExponentArithmetic.divide(number.split("E")[0], number.split("E")[1], standardPixelLength.split("E")[0], standardPixelLength.split("E")[1]));
    }
    public String toSiPixel(float number){
        return ExponentArithmetic.multiply(Float.toString(number), "0", standardPixelLength.split("E")[0], standardPixelLength.split("E")[1]);
    }
    
    public int getPixelSize(){
        return (int)(ExponentArithmetic.convertToNumber(ExponentArithmetic.divide(radius.split("E")[0], radius.split("E")[1], standardPixelLength.split("E")[0], standardPixelLength.split("E")[1]))*ringSize);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMathsMass() {
        return mathsMass;
    }

    public void setMathsMass(int mathsMass) {
        this.mathsMass = mathsMass;
    }

    public float getRingSize() {
        return ringSize;
    }

    public void setRingSize(float ringSize) {
        this.ringSize = ringSize;
    }
    
}
