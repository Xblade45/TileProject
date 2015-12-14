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
import bejeweled.utils.InputManager;
import bejeweled.utils.Sound;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
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
    private Font endingFont;
    private Font pauseFont;
    private Timer gameTimer;
    private Timer startGameTimer;
    private Timer endGameTimer;
    
    private int currentState;
    private int gameTimerValue;
    
    private int endScreenOpacity;
    private int openingScreenOpacity;
    
    private final int START_GAME_TIMER_VALUE = 6;
    private final int GAME_TIMER_VALUE = 60;
    private final int END_GAME_TIMER_VALUE = 4;
    
    
    // Constructor
    public PlayingState(GameStateManager gsm){
        
        this.gsm = gsm;
        
        init();
    }

    // Getters
    public int getCurrentState() {
        return currentState;
    }

    // Methods    
    @Override
    public final void init() {
        
        this.background = new Background("background1");
        this.generalFont = new Font("Calibri", Font.BOLD, 30);
        this.countdownFont = new Font("Calibri", Font.PLAIN, 300);
        this.endingFont = new Font("Calibri", Font.BOLD, 100);
        this.pauseFont = new Font("Calibri", Font.PLAIN, 30);
        this.board = new Board(this);
        this.currentState = GameStateManager.STARTING_STATE;
        
        this.endScreenOpacity = 0;
        this.openingScreenOpacity = 255;
    }

    @Override
    public void update() {
        if(InputManager.keys[KeyEvent.VK_ESCAPE]){
            if(currentState == GameStateManager.PLAYING_STATE)
                currentState = GameStateManager.PAUSE_STATE;
        }
        
        board.update();
        
        switch(currentState){
        case GameStateManager.PAUSE_STATE:
            gameTimer.pause();
        break;
        case GameStateManager.STARTING_STATE:
            if(startGameTimer == null){
                startGameTimer = new Timer(START_GAME_TIMER_VALUE);
                startGameTimer.start();
                Sound.play("start");
            }
            if(startGameTimer.getPresentTime() > 0 && openingScreenOpacity > 1){
                openingScreenOpacity--;
            }
            if(startGameTimer.getPresentTime() < 0){
                currentState = GameStateManager.PLAYING_STATE;
            }
        break;
        case GameStateManager.PLAYING_STATE:
            if(gameTimer == null){
                gameTimer = new Timer(GAME_TIMER_VALUE);
                gameTimer.start();
            }
            if(gameTimer.getPresentTime() == 0){
                if(gsm.bestScore < board.getScoreCounter().getScoreInt())
                    gsm.bestScore = board.getScoreCounter().getScoreInt();
                
                currentState = GameStateManager.ENDING_STATE;
            }
        break; 
        case GameStateManager.ENDING_STATE:
            if(endGameTimer == null){
                endGameTimer = new Timer(END_GAME_TIMER_VALUE);
                endGameTimer.start();
            }
            if(endGameTimer.getPresentTime() >= 0){
                if(endScreenOpacity < 254)
                    endScreenOpacity+=2;
            }
            if(endGameTimer.getPresentTime() < 0){
                gsm.remove();
            }
        break;
        }
    }
    
    @Override
    public void draw(Graphics g) {
        background.draw(g);
        board.draw(g);
        
        drawHUD(g);
        
        switch(currentState){
        case GameStateManager.PAUSE_STATE:
            drawPauseMenu(g);
        break;
        case GameStateManager.STARTING_STATE:
            if(startGameTimer != null){
                g.setColor(new Color(0, 0, 0, openingScreenOpacity));
                g.fillRect(0, 0, Panel.WIDTH, Panel.HEIGHT);
                drawStartingState(g);
            }
        break;
        case GameStateManager.ENDING_STATE:
            g.setColor(new Color(0, 0, 0, endScreenOpacity));
            g.fillRect(0, 0, Panel.WIDTH, Panel.HEIGHT);
            drawEndingState(g);
        break;
        }
    }
    
    private void drawHUD(Graphics g){
        g.setFont(generalFont);
        g.setColor(Color.CYAN);
        
        g.drawString(board.getScoreCounter().getScoreString(), 55, 108);// Hardcoded score position
        
        if(gameTimer == null) gameTimerValue = 60;
        else if(gameTimer.getPresentTime() < 0) gameTimerValue = 0;
        else gameTimerValue = (int)gameTimer.getPresentTime();
            
        g.drawString(String.format("%02d", gameTimerValue), 55, 180);// Hardcoded timer position
    }
    
    private void drawStartingState(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(countdownFont);

        String str = ((startGameTimer.getPresentTime()!=0) ? startGameTimer.getPresentTimeString() : "GO");

        g.drawString(str, 
                Panel.WIDTH/2 - g.getFontMetrics().stringWidth(str)/2, 
                Panel.HEIGHT/2 + 50);
    }
    
    private void drawEndingState(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(endingFont);

        String str = "Votre Score";
        String str2 = board.getScoreCounter().getScoreInt() + "";

        g.drawString(str,
                Panel.WIDTH/2 - g.getFontMetrics().stringWidth(str)/2, 
                Panel.HEIGHT/2 );
        g.drawString(str2,
                Panel.WIDTH/2 - g.getFontMetrics().stringWidth(str2)/2, 
                Panel.HEIGHT/2 + 70);
    }
    
    private void drawPauseMenu(Graphics g){
        
        String str = "BACK TO GAME";
        String str2 = "QUIT";
        
        g.setFont(pauseFont);
        g.setColor(new Color(0, 0, 0, 240));
        g.fillRect(Panel.WIDTH/2 - 200, Panel.HEIGHT/2 - 150, 400, 300);
        
        g.setColor(Color.WHITE);
        g.drawString(str, 
                Panel.WIDTH/2 - g.getFontMetrics().stringWidth(str)/2, 
                Panel.HEIGHT/2 - 20);
        g.drawString(str2, 
                Panel.WIDTH/2 - g.getFontMetrics().stringWidth(str2)/2, 
                Panel.HEIGHT/2 + 20);
    }
    
    @Override
    public void mousePressed(MouseEvent me) {
        
        switch(currentState){
        case GameStateManager.PLAYING_STATE:
            board.mousePressed(me);
        break;
        case GameStateManager.PAUSE_STATE:
            if(me.getX() > Panel.WIDTH/2 - 50 && me.getX() < Panel.WIDTH/2 + 50){// X position BOTH
                if(me.getY() > Panel.HEIGHT/2 - 40 && me.getY() < Panel.HEIGHT/2 -2){// Y positions BACK TO GAME
                    gameTimer.resume();
                    currentState = GameStateManager.PLAYING_STATE;
                }
                if(me.getY() > Panel.HEIGHT/2 +2 && me.getY() < Panel.HEIGHT/2 + 40){// Y positions QUIT
                    gameTimer.stopIt();
                    gsm.remove();
                }
            }
        break;    
        }
    }
    
}
