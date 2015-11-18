/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bejeweled.board;

import bejeweled.utils.Image;
import bejeweled.gameState.GameEngine;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Xblade45
 */
public class Tile implements GameEngine {
    
    
    // Self explanatory
    public static final int TILESIZE = 80;
    public static final int WIDTH = TILESIZE;
    public static final int HEIGHT = TILESIZE;
    
    // Position on board grid
    private int posX;
    private int posY;
    
    // Absolute position in pixels
    private int aPosX;
    private int aPosY;
    
    // Tile type (wich image)
    private int type;
    
    private Color color;
    
    private boolean selected;
    
    private BufferedImage spriteSheet;
    private BufferedImage image;
    
    private Animation animation;
    
    
    //constructor
    public Tile(int posX, int posY){
        
        init();
        changePosition(posX, posY);
    }

    //getter-setter
    public int getaPosX() {
        return aPosX;
    }
    public void setaPosX(int aPosX) {
        this.aPosX = aPosX;
    }
    public int getaPosY() {
        return aPosY;
    }
    public void setaPosY(int aPosY) {    
        this.aPosY = aPosY;
    }
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {    
        this.selected = selected;
    }
    public int getPosX() {
        return posX;
    }
    public int getPosY() {    
        return posY;
    }
    public int getType() {
        return type;
    }
    public Color getColor(){
        return color;
    }
    
    //methods
    private void setType(){

        this.type = (int) (Math.random()*7);
        setGem();
    }
        
    public void setType(int i){
        
        this.type = i;
        setGem();
    }
    
    public boolean isNeighbor(Tile t){
        
        if(getPosX()+1 == t.getPosX() && getPosY() == t.getPosY()
        || getPosX()-1 == t.getPosX() && getPosY() == t.getPosY()
        || getPosX() == t.getPosX() && getPosY()+1 == t.getPosY()
        || getPosX() == t.getPosX() && getPosY()-1 == t.getPosY()){
            System.out.println("isNeighbor");
            return true;
        }else
            return false;
    }
    
    public final void changePosition(int posX, int posY){
        
        this.aPosX = posX*WIDTH + 337;
        this.aPosY = posY*HEIGHT + 64;
        this.posX = posX;
        this.posY = posY;
    }
    
    private void loadImages(String input){
        
        this.spriteSheet = Image.loadImage("tiles", input);
        
        animation.setFrames(Image.getAnimationTab(spriteSheet));
        animation.setDelay(75);
        
        this.image = animation.getImage(0);
    }

    private void setGem(){

        int c = getType();
        
        switch(c){
        case -1:
            loadImages("vide");
            break;
        case 0:
            loadImages("gem1");
            break;
        case 1:
            loadImages("gem2");
            break;
        case 2:
            loadImages("gem3");
            break;
        case 3:
            loadImages("gem4");
            break;
        case 4:
            loadImages("gem5");
            break;
        case 5:
            loadImages("gem6");
            break;
        case 6:
            loadImages("gem7");
            break;
        }
    }
    
    private void drawTile(Graphics g){
        
        if(isSelected())
            g.drawImage(animation.getImage(), aPosX, aPosY, null);
        else
            g.drawImage(image, aPosX, aPosY, null);
    }

    //Overridable methods
    @Override
    public final void init() {
    
        this.animation = new Animation();
        setType();
    }
    
    @Override
    public void run() {}
    
    @Override
    public void update() {
        
        animation.update();
    }

    @Override
    public void draw(Graphics g) {
        
        drawTile(g);
    }
}
