/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bejeweled.board;

import bejeweled.utils.Image;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Xblade45
 */
public class Background {
    
    private BufferedImage image;
    
    
    // Constructor
    public Background(String input){
        
        this.image = Image.loadImage("backgrounds", input);
    }
    
    //Methods
    public void draw(Graphics g){
        
        g.drawImage(image, 0, 0, null);
    }
}
