/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.city.acwf566.handler;

import java.util.ArrayList;
import uk.ac.city.acwf566.math.ExponentArithmetic;
import uk.ac.city.acwf566.objects.Body;

/**
 *
 * @author Sam
 */
public class PhysicsHandler {
    private ArrayList<Body> bodies = new ArrayList<Body>();
    private boolean bodyNeedsUpdating = false;
    private Body removedBody;
    private Body destroyerBody;
    private float zoom = 1;
    
    private final float MAX_ZOOM = 10;
    private final float MIN_ZOOM = 0.2f;
    
    public PhysicsHandler(){
        
    }

    public ArrayList<Body> getBodies() {
        return bodies;
    }
    
    public void addBodies(Body body){
        bodies.add(body);
    }
    public void removeBodies(Body body){
        bodies.remove(body);
        removedBody = body;
    }
    public void updateBodies(){
        for(Body body: bodies){
            for(Body secondBody: bodies){
                if(body != secondBody){
                    body.setMathsMass((int)ExponentArithmetic.convertToNumber(ExponentArithmetic.divide(body.getMass().split("E")[0], body.getMass().split("E")[1], "10", "25")));
                    secondBody.setMathsMass((int)ExponentArithmetic.convertToNumber(ExponentArithmetic.divide(secondBody.getMass().split("E")[0], secondBody.getMass().split("E")[1], "10", "25")));
                    body.gravitateTo(secondBody);
                }
            }
        }
        for(Body body: bodies){
            body.update();
        }
        destroyBody();
    }
    public void destroyBody(){
        Body toDestroy = checkCollision();
        if(!toDestroy.getId().equals("noCollision")){
            removeBodies(toDestroy);
            toDestroy = null;
        }
    }
    public Body checkCollision(){
        Body b = new Body("0E0","0E0");
        b.setId("noCollision");
        for(Body body: bodies){
            for(Body secondBody: bodies){
                if(body.distanceTo(secondBody) < body.getPixelSize()/2 && body!= secondBody){
                    Body smaller = getSmallerBody(body, secondBody);
                    if(smaller == body){
                        destroyerBody = secondBody;
                    }else{
                        destroyerBody = body;
                    }
                    return smaller;
                }
            }
        }
        return b;
    }
    public Body getSmallerBody(Body first, Body second){
        if(ExponentArithmetic.convertToNumber(ExponentArithmetic.divide(first.getRadius().split("E")[0], first.getRadius().split("E")[1], second.getRadius().split("E")[0], second.getRadius().split("E")[1])) < 1){
            return first;
        }else{
            return second;
        }
    }

    public boolean isBodyNeedsUpdating() {
        return bodyNeedsUpdating;
    }

    public void setBodyNeedsUpdating(boolean bodyNeedsUpdating) {
        this.bodyNeedsUpdating = bodyNeedsUpdating;
    }

    public Body getRemovedBody() {
        return removedBody;
    }

    public Body getDestroyerBody() {
        return destroyerBody;
    }

    public void setDestroyerBody(Body destroyerBody) {
        this.destroyerBody = destroyerBody;
    }
    
    
    public void setRemovedBody(Body removedBody) {
        this.removedBody = removedBody;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    public float getMaxZoom() {
        return MAX_ZOOM;
    }

    public float getMinZoom() {
        return MIN_ZOOM;
    }
}
