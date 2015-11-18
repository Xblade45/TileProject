/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bejeweled.main;

import bejeweled.gameState.GameEngine;
import bejeweled.gameState.GameStateManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 *
 * @author Xblade45
 */
public class Panel extends JPanel implements GameEngine, MouseListener {

    private GameStateManager gsm;
    
    private boolean isRunning;
    
    public static final int GETWIDTH = 1024;
    public static final int GETHEIGHT = 768;
    
    private Thread t;
    
    
    // Constructor
    public Panel(){

        run();
    }
    
    
    // Methods
    @Override
    public void paintComponent(Graphics g){
        
        draw(g);
    }

    @Override
    public void init() {
        
        this.isRunning = true;
        this.gsm = new GameStateManager();
        
        addMouseListener(this);
        setPreferredSize(new Dimension(GETWIDTH, GETHEIGHT));
        setVisible(true);
    }

    @Override
    public final void run() {
        
        init();
        
        t = new Thread(){
            
            @Override
            public void run(){
                
                long start,  wait, elapsed;
                final int FPS = 60;
                
                while(isRunning){
                    
                    start = System.currentTimeMillis();
                    
                    update();
                    repaint();
                    
                    elapsed = System.currentTimeMillis() - start;
                    
                    wait = elapsed + (1000 / FPS);
                    
                    try{
                        Thread.sleep(wait);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }

    @Override
    public void update() {
        
        gsm.update();
    }

    @Override
    public void draw(Graphics g) {
        
        //Refresh screen
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        // Draw panel
        gsm.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {}
    
    @Override
    public void mousePressed(MouseEvent mouseEvent) {//envoie le mouseEvent a GSM
        
        gsm.mousePressed(mouseEvent);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {}
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}
    @Override
    public void mouseExited(MouseEvent mouseEvent) {}
}
