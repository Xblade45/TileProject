/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bejeweled.board;

import java.awt.image.BufferedImage;

/**
 *
 * @author Xblade45
 */
public class Animation {
    
    private BufferedImage[] frames;
    private int currentFrame;
    
    private long startTime;
    private long delay;
    
    private boolean playedOnce;
    
    
    // Constructor
    public Animation(){
        playedOnce = false;
    }
    
    // Methods
    public void setFrames(BufferedImage[] frames){
        
        this.frames = frames;
        this.currentFrame = 0;
        this.startTime = System.currentTimeMillis();
        this.playedOnce = false;
    }
    
    public void setDelay(long d) {
        this.delay = d;
    }
    
    public void setFrame(int i) {
        this.currentFrame = i;
    }
    
    public void update(){
        
        if(delay == -1)
            return;
            
        long elapsed = (System.nanoTime() - startTime) / 1000000;

        if(elapsed > delay) {
            
            currentFrame++;
            startTime = System.nanoTime();
        }

        if(currentFrame == frames.length){
            
            currentFrame = 0;
            playedOnce = true;
        }
    }
    
    public int getFrame(){
        return currentFrame;
    }
    
    public BufferedImage getImage(){
        return frames[currentFrame];
    }
    
    public BufferedImage getImage(int index){
        return frames[index];
    }
    
    public boolean hasPlayedOnce(){
        return playedOnce;
    }
}
