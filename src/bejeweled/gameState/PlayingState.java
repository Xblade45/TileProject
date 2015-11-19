/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bejeweled.gameState;

import bejeweled.utils.Timer;
import bejeweled.board.Background;
import bejeweled.board.Board;
import bejeweled.main.Panel;
import bejeweled.utils.Sound;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 *
 * @author Xblade45
 */
public class PlayingState extends GameState {
    
    private Background background;
    private Board board;
    private Font generalFont;
    private Font countdownFont;
    private Timer gameTimer;
    private Timer startGameTimer;
    
    private boolean gameUnPaused;
    
    
    // Constructor
    public PlayingState(GameStateManager gsm){
        
        this.gsm = gsm;
        
        init();
    }
    
    //Getters
    public boolean isGameUnPaused() {
        return gameUnPaused;
    }
    
    // Methods    
    @Override
    public final void init() {
        
        this.background = new Background("background1");
        this.gameTimer = new Timer(60);// Hardcoded countdown timer
        this.startGameTimer = new Timer(6);// Hardcoded countdown timer
        this.generalFont = new Font("Calibri", Font.BOLD, 30);
        this.countdownFont = new Font("Calibri", Font.PLAIN, 300);
        this.board = new Board(this);
        this.gameUnPaused = false;

        startGameTimer.start();
        
        Sound.play("start");
    }

    @Override
    public void update() {
        
        board.update();
        
        if(startGameTimer.getPresentTime() == -1 && !gameUnPaused){
            
            this.gameUnPaused = true;
            gameTimer.start();
        }
    }
    
    @Override
    public void draw(Graphics g) {
        
        background.draw(g);
        board.draw(g);
        
        g.setFont(generalFont);
        g.setColor(Color.CYAN);
        g.drawString(board.getScoreCounter().getScoreString(), 55, 108);// Hardcoded score position
        g.drawString(String.format("%02d", gameTimer.getPresentTime()), 55, 180);// Hardcoded timer position
        
        if(!gameUnPaused){
            g.setColor(Color.WHITE);
            g.setFont(countdownFont);
            
            String str = ((startGameTimer.getPresentTime()!=0)? startGameTimer.getPresentTimeString():"GO");
            
            g.drawString(str, 
                    Panel.GETWIDTH/2 - g.getFontMetrics().stringWidth(str)/2, 
                    Panel.GETHEIGHT/2 + 50);
        }
            
    }
    
    @Override
    public void mousePressed(MouseEvent me) {
        
        if(gameUnPaused)
            board.mousePressed(me);
    }
}
