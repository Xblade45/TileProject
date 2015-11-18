/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bejeweled.utils;

import bejeweled.board.Tile;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Xblade45
 */
public class Image {
    
    public static BufferedImage loadImage(String folder, String input){
        
        BufferedImage sprite = null;
        
        try{
            sprite = ImageIO.read(Image.class.getClassLoader().getResource(folder + "/" + input + ".png"));
        }catch(IOException e){
            e.printStackTrace();
        }
        return sprite;
    }
    
    public static BufferedImage[] getAnimationTab(BufferedImage img){
        
        BufferedImage[] animation = new BufferedImage[6];
        
        for(int i=0; i<6; i++)
            animation[i] = img.getSubimage(i*Tile.TILESIZE, 0, Tile.TILESIZE, Tile.TILESIZE);
        
        return animation;
    }
}

