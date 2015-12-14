/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bejeweled.gameState;

import bejeweled.main.Panel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Stack;

/**
 *
 * @author Xblade45
 */
public class GameStateManager implements MouseListener{
    
    protected static Stack<GameState> states;
    
    public static final int STARTING_STATE = 0;
    public static final int PLAYING_STATE = 1;
    public static final int ENDING_STATE = 2;
    public static final int PAUSE_STATE = 3;
    
    protected final String BACK = "BACK";
    
    protected int bestScore;
    
    protected final Font TITLEFONT = new Font("Calibi", Font.BOLD, 100);
    protected final Color COLOR = new Color(125, 25, 25);
    
    
    // Constructor
    public GameStateManager(){
        
        states = new Stack<>();
        bestScore = 0;
    }
    
    
    // Methods
    public void push(GameState gamestate){
        states.push(gamestate);
    }
    public void remove(){
        states.pop();
    }
    
    public void init(){
        states.peek().init();
    }
    
    public void update(){
        states.peek().update();
    }
    
    public void draw(Graphics g){
        states.peek().draw(g);
    }
    
    public void clear(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Panel.WIDTH, Panel.HEIGHT);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        states.peek().mousePressed(e);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    
}
