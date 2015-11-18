/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bejeweled.gameState;

import bejeweled.board.Background;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 *
 * @author Xblade45
 */
public class MenuState extends GameState {
    
    private Background background;
    
    
    // Constructor
    public MenuState(GameStateManager gsm) {
        
        this.gsm = gsm;
        
        init();
    }
    
    // Methods
    @Override
    public final void init() {
        
        this.background = new Background("background1");
    }
    
    @Override
    public void update() {}
    
    @Override
    public void draw(Graphics g) {
        
        background.draw(g);
    }

    @Override
    public void mousePressed(MouseEvent me) {}
}
