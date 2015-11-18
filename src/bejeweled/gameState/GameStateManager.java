/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bejeweled.gameState;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 *
 * @author Xblade45
 */
public class GameStateManager implements GameEngine, MouseListener{
    
    private final ArrayList<GameState> gameStates;
    private int currentState;
    
    public static final int MENUSTATE = 0;
    public static final int PLAYINGSTATE = 1;
    
    
    // Constructor
    public GameStateManager(){
        
        this.gameStates = new ArrayList<>();
        this.currentState = PLAYINGSTATE;
        gameStates.add(new MenuState(this));
        gameStates.add(new PlayingState(this));
    }
    
    // Methods
    public void setState(int currentState){
        
        this.currentState = currentState;
        gameStates.get(currentState).init();
    }

    @Override
    public final void init() {}
    
    @Override
    public void run() {
        
        gameStates.get(currentState).run();
    }
    
    @Override
    public void update() {
        
        gameStates.get(currentState).update();
    }
    
    @Override
    public void draw(Graphics g) {
        
        gameStates.get(currentState).draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent me) {}
    
    @Override
    public void mousePressed(MouseEvent me) {
        
        gameStates.get(currentState).mousePressed(me);
    }
    
    @Override
    public void mouseReleased(MouseEvent me) {}
    @Override
    public void mouseEntered(MouseEvent me) {}
    @Override
    public void mouseExited(MouseEvent me) {}

}
