/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.city.acwf566.gui;

import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import uk.ac.city.acwf566.handler.PhysicsHandler;
import uk.ac.city.acwf566.math.Vec2f;
import uk.ac.city.acwf566.objects.Body;

/**
 *
 * @author Sam
 */
public class PhysicsPanel extends JPanel{
    private ArrayList<Body> bodies;
    private PhysicsHandler model;
    private Body bodyFocus;
    
    private Point screenPosition = new Point(0,0);
    private Point startPosition = new Point(0,0);
    private Point newPosition = new Point(0,0);
    private Point zoomedPosition = new Point(0,0);
    private boolean dragging;
    private boolean focus = false;
    private int counter = 0;
    
    public PhysicsPanel(PhysicsHandler model){
        this.model = model;
        this.bodies = model.getBodies();
        initComponents();
    }
    public void initComponents(){
        this.setBackground(new java.awt.Color(15, 15, 30));
        this.setName("physicsPanel"); // NOI18N

        javax.swing.GroupLayout physicsPanelLayout = new javax.swing.GroupLayout(this);
        this.setLayout(physicsPanelLayout);
        physicsPanelLayout.setHorizontalGroup(
            physicsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 911, Short.MAX_VALUE)
        );
        physicsPanelLayout.setVerticalGroup(
            physicsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 610, Short.MAX_VALUE)
        );
    }
    public void paintBackground(Graphics g){
        
        try{
            BufferedImage backgroundImage = ImageIO.read(new File("data/images/backgrounds/base_star.png"));
            g.drawImage(backgroundImage,0,0, this.getWidth(), this.getHeight(), this);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        paintBackground(g);
        
        for(Body body: bodies){
            if(!dragging){
                System.out.println(model.getZoom());
                if(focus == true){
                    zoomedPosition = new Point(this.getWidth()/2,this.getHeight()/2);
                    if(body == bodyFocus){
                        g.drawImage(body.getImage(),this.getWidth()/2-body.getPixelSize()/2,this.getHeight()/2-body.getPixelSize()/2, (int)(body.getPixelSize()*model.getZoom()), (int)(body.getPixelSize()*model.getZoom()), this);
                        screenPosition = new Point(-(int)bodyFocus.getPosition().x,-(int)bodyFocus.getPosition().y);
                    }else{
                        Vec2f newBodyPosition = bodyFocus.getPosition().subtract(body.getPosition());
                        g.drawImage(body.getImage(),(int)(newBodyPosition.x*model.getZoom())+this.getWidth()/2-body.getPixelSize()/2,(int)(newBodyPosition.y*model.getZoom())+this.getHeight()/2-body.getPixelSize()/2, (int)(body.getPixelSize()*model.getZoom()), (int)(body.getPixelSize()*model.getZoom()), this);
                    }
                }else{
                    Vec2f newBodyPos = new Vec2f(screenPosition.x+zoomedPosition.x-((int)body.getPosition().x+screenPosition.x+this.getWidth()/2-body.getPixelSize()/2),screenPosition.y+zoomedPosition.y-((int)body.getPosition().y+screenPosition.y+this.getHeight()/2-body.getPixelSize()/2));
                    newBodyPos.multiplyByScalar(model.getZoom());
                    newBodyPos = new Vec2f(zoomedPosition.x-newBodyPos.x,zoomedPosition.y-newBodyPos.y);
                    g.drawImage(body.getImage(), (int)body.getPosition().x+screenPosition.x+this.getWidth()/2-body.getPixelSize()/2, (int)body.getPosition().y+screenPosition.y+this.getHeight()/2-body.getPixelSize()/2, (int)(body.getPixelSize()*model.getZoom()), (int)(body.getPixelSize()*model.getZoom()), this);
                
                    //g.drawImage(body.getImage(), (int)newBodyPos.x+screenPosition.x+this.getWidth()/2-body.getPixelSize()/2, (int)newBodyPos.y+screenPosition.y+this.getHeight()/2-body.getPixelSize()/2, (int)(body.getPixelSize()*model.getZoom()), (int)(body.getPixelSize()*model.getZoom()), this);
                }
            }else{
                newPosition = new Point(screenPosition.x+MouseInfo.getPointerInfo().getLocation().x - startPosition.x,screenPosition.y+MouseInfo.getPointerInfo().getLocation().y - startPosition.y);
                //newPosition = new Point((int)((body.getPosition().x-zoomedPosition.x)*model.getZoom()),(int)((body.getPosition().y-zoomedPosition.y)*model.getZoom()));;
                g.drawImage(body.getImage(), (int)(body.getPosition().x*model.getZoom())+newPosition.x+this.getWidth()/2-body.getPixelSize()/2, (int)(body.getPosition().y*model.getZoom())+newPosition.y+this.getHeight()/2-body.getPixelSize()/2, (int)(body.getPixelSize()*model.getZoom()),(int)(body.getPixelSize()*model.getZoom()), this);
            }
        }
    }

    public void setScreenPosition(Point mousePosition) {
        this.screenPosition = mousePosition;
    }

    public Point getScreenPosition() {
        return screenPosition;
    }

    public Point getNewPosition() {
        return newPosition;
    }
    
    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public void setStartPosition(Point startPosition) {
        this.startPosition = startPosition;
    }
    
    public ArrayList<Body> getBodies() {
        return bodies;
    }

    public void setBodies(ArrayList<Body> bodies) {
        this.bodies = bodies;
    }

    public Body getBodyFocus() {
        return bodyFocus;
    }

    public void setBodyFocus(Body bodyFocus) {
        this.bodyFocus = bodyFocus;
    }

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public void setZoomedPosition(Point zoomedPosition) {
        this.zoomedPosition = zoomedPosition;
    }
}
