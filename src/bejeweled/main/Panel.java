/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bejeweled.main;

import bejeweled.gameState.GameStateManager;
import bejeweled.gameState.MenuState;
import bejeweled.utils.InputManager;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Xblade45
 */
public class Panel extends JPanel implements MouseListener, Runnable {

    private GameStateManager gsm;
    
    private boolean isRunning;
    
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    
    private Thread t;
    public JFrame frame;
    
    private int fpsCounter;
    private int upsCounter;

    
    // Constructor
    public Panel(JFrame frame){
        this.frame = frame;
        init();
    }
    
    // Methods
    @Override
    public void paintComponent(Graphics g){
        draw(g);
    }

    public final void init() {
        this.gsm = new GameStateManager();
        
        gsm.push(new MenuState(gsm, this));
        
        addMouseListener(this);
        addKeyListener(new InputManager());
        setFocusable(true);
        requestFocus();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setVisible(true);
        
        isRunning = true;
        
        t = new Thread(this, "MainLoopThread");
        t.start();
    }
    
    @Override
    public void run() {

        double fps = 60d;
        long lastTime = System.nanoTime();
        double ns = 1000000000d / fps;
        double delta = 0d;
        int frames = 0;
        int updates = 0;
        double timer = System.currentTimeMillis();

        while(isRunning){

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >= 1){
                updates++;
                update();
                delta -= 1;
            }

            frames++;
            repaint();

            try{
                Thread.sleep(1);
            }catch(Exception e){
                e.printStackTrace();
            }

            if(System.currentTimeMillis() - timer >= 1000){
                timer += 1000;
                upsCounter = updates;
                fpsCounter = frames;
                updates = 0;
                frames = 0;
            }
        }
    }

    public void update() {
        frame.setTitle("FPS : " + fpsCounter + " UPS : " + upsCounter);
        gsm.update();
    }

    public void draw(Graphics g) {
        //clear screen (not necessary with fixed background)
        gsm.clear(g);
        
        // Draw panel
        gsm.draw(g);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {//envoie le mouseEvent a GSM
        gsm.mousePressed(mouseEvent);
    }
    
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {}
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {}
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}
    @Override
    public void mouseExited(MouseEvent mouseEvent) {}

}
