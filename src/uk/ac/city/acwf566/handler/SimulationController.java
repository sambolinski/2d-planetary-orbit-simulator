/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.city.acwf566.handler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import uk.ac.city.acwf566.gui.View;
import uk.ac.city.acwf566.math.Vec2f;
import uk.ac.city.acwf566.objects.Body;

/**
 *
 * @author Sam
 */
public class SimulationController{
    private View view;
    private PhysicsHandler world;
    private int simulationCounter = 0;
    private int simulationDelay = 128;
    public SimulationController(View view, PhysicsHandler world){
        this.view = view;
        this.world = world;
        this.view.addMouseListener(new ControllerListener());
        this.view.addMouseMotionListener(new ControllerListener());
        this.view.addMouseWheelListener(new ControllerListener());
    }
    public void initWorld(){
        world.addBodies(Loader.loadBody("saturn"));
        world.addBodies(Loader.loadBody("jupiter"));
        view.setWorld(world);
        for(int i = 0; i < world.getBodies().size(); i++){
            if(i == 0){
                world.getBodies().get(i).setPosition(new Vec2f(0,0));
                world.getBodies().get(i).setVelocity(new Vec2f(0,0));
            }
            if(i == 1){
                world.getBodies().get(i).setPosition(new Vec2f(200,0));
                world.getBodies().get(i).setVelocity(new Vec2f(0,0.1f));
            }
            
            if(i == 2){
                world.getBodies().get(i).setPosition(new Vec2f(100,120));
                world.getBodies().get(i).setVelocity(new Vec2f(0,0.1f));
            }
        }
        view.updateInfoBox(world.getBodies().get(0));
        view.getPhysicsPanel().setBodies(world.getBodies());
        view.setBodyFocus(world.getBodies().get(0));
        
    }

    public int getSimulationCounter() {
        return simulationCounter;
    }

    public void setSimulationCounter(int simulationCounter) {
        this.simulationCounter = simulationCounter;
    }

    public int getSimulationDelay() {
        return simulationDelay;
    }

    public void setSimulationDelay(int simulationDelay) {
        this.simulationDelay = simulationDelay;
    }
    public boolean isFocused(){
        if(world.getRemovedBody() == view.getBodyFocus()){
            return true;
        }else{
            return false;
        }
    }
    class ControllerListener implements MouseListener, MouseMotionListener, MouseWheelListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            
        }

        @Override
        public void mousePressed(MouseEvent e) {
            view.getPhysicsPanel().setDragging(true);
            view.getPhysicsPanel().setStartPosition(e.getPoint());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            view.getPhysicsPanel().setDragging(false);
            view.getPhysicsPanel().setScreenPosition(view.getPhysicsPanel().getNewPosition());
        }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e){}

        @Override
        public void mouseDragged(MouseEvent e) {
            if(view.getLockCameraButton().getText().equals("unlock")){
                view.lockCameraButtonActionPerformed();
            }
        }  

        @Override
        public void mouseMoved(MouseEvent e) {}

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            world.setZoom(world.getZoom()-(((float)e.getWheelRotation()/25)*(world.getZoom())));
            
            if(world.getZoom()<world.getMinZoom()){
                world.setZoom(world.getMinZoom());
            }else if(world.getZoom() > world.getMaxZoom()){
                world.setZoom(world.getMaxZoom());
            }
            view.getPhysicsPanel().setZoomedPosition(e.getPoint());
        }  
    }
}
