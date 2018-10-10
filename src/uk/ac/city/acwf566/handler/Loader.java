/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.city.acwf566.handler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import javax.imageio.ImageIO;
import uk.ac.city.acwf566.math.ExponentArithmetic;
import uk.ac.city.acwf566.math.Vec2f;
import uk.ac.city.acwf566.objects.Body;

/**
 *
 * @author Sam
 */
public class Loader {
    public static Body loadBody(String bodyName){
        Body body = new Body("0E0","0E0");
        try(FileReader reader = new FileReader("data/body_data/default/"+bodyName+".properties")){
            Properties properties = new Properties();
            properties.load(reader);
            String name = properties.getProperty("name");
            String mass = properties.getProperty("mass");
            String radius = properties.getProperty("radius");
            body = new Body(radius, mass);
            body.setId(body.toString());
            body.setName(name);
            body.setPosition(new Vec2f(0,0));
            body.setMathsMass((int)ExponentArithmetic.convertToNumber(ExponentArithmetic.divide(mass.split("E")[0], mass.split("E")[1], "10", "25")));
            if(bodyName.equals("saturn")){
                body.setRingSize(Float.parseFloat(properties.getProperty("ringSize")));
            }
            try{
                BufferedImage bodyImage = ImageIO.read(new File(properties.getProperty("image")));
                body.setImage(bodyImage);
            }catch(IOException e){

            }
        }catch(IOException e){
            e.getMessage();
        }
        return body;
    }
}
