/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bejeweled.gameState;

import bejeweled.board.Background;
import bejeweled.main.Panel;
import bejeweled.utils.Sound;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 *
 * @author Xblade45
 */
public class MenuState extends GameState {
    
    private Background background;
    
    private final String PLAY = "PLAY";
    private final String SCOREBOARD = "SCOREBOARD";
    private final String QUIT = "QUIT";
    
    private final Font MENUFONT = new Font("Calibri", Font.BOLD, 32);
    private final String MENUSTR = "TILE PROJECT";
    
    private int buttonWidth;
    private int buttonHeight;
    
    private Font playFont;
    private Font scoreboardFont;
    private Font quitFont;

    private int[] counters;
    private Rectangle[] buttons;
    
    private JPanel panel;

    
    // Constructor
    public MenuState(GameStateManager gsm, JPanel panel) {
        
        this.gsm = gsm;
        this.panel = panel;
        init();
    }
    
    // Methods
    @Override
    public final void init() {
        
        //Background
        this.background = new Background("backgroundMenu");
        
        //Buttons size
        buttonWidth = 200;
        buttonHeight = 45;
        
        //buttons font
        playFont = MENUFONT;
        scoreboardFont = MENUFONT;
        quitFont = MENUFONT;
        
        //Resize text parameter
        counters = new int[3];
        counters[0] = 32;// PlayFontCounter
        counters[1] = 32;// ScoreboardFontCounter
        counters[2] = 32;// QuitFontCounter
        
        //Buttons bounds for mouse intersect
        buttons = new Rectangle[3];
        buttons[0] = new Rectangle(Panel.WIDTH/2 -(buttonWidth/2), Panel.HEIGHT * 7 / 16 - buttonHeight/2, buttonWidth, buttonHeight);
        buttons[1] = new Rectangle(Panel.WIDTH/2 -(buttonWidth/2), Panel.HEIGHT * 8 / 16 - buttonHeight/2, buttonWidth, buttonHeight);
        buttons[2] = new Rectangle(Panel.WIDTH/2 -(buttonWidth/2), Panel.HEIGHT * 9 / 16 - buttonHeight/2, buttonWidth, buttonHeight);
    }
    
    @Override
    public void update() {
        Point a = panel.getMousePosition();
        
        if(a != null){// if mouse on component (panel)
            Rectangle mouse = new Rectangle(a.x, a.y, 1, 1);
            
            for(int i = 0; i < buttons.length; i++){
                if(buttons[i].intersects(mouse)){
                    
                    counters[i] += 3;
                }else
                    counters[i] -= 3;
                
                if(counters[i] < 32)
                    counters[i] = 32;
                if(counters[i] > 60)
                    counters[i] = 60;
                
                if(counters[i] == 35)
                        Sound.play("sound64");
            }
            
        }
        
        playFont = playFont.deriveFont((float)counters[0]);
        scoreboardFont = playFont.deriveFont((float)counters[1]);
        quitFont = playFont.deriveFont((float)counters[2]);
    }
    
    @Override
    public void draw(Graphics g) {
        
        background.draw(g);
        
        g.setColor(gsm.COLOR);
        
        g.setFont(gsm.TITLEFONT);
        g.drawString(MENUSTR, 
                Panel.WIDTH/2 - g.getFontMetrics().stringWidth(MENUSTR)/2, 
                150);
        
        g.setFont(playFont);
        g.drawString(PLAY, 
                buttons[0].x + (buttonWidth/2) - g.getFontMetrics().stringWidth(PLAY)/2, 
                buttons[0].y + buttonHeight/2);
        g.setFont(scoreboardFont);
        g.drawString(SCOREBOARD, 
                buttons[1].x + (buttonWidth/2) - g.getFontMetrics().stringWidth(SCOREBOARD)/2, 
                buttons[1].y + buttonHeight/2);
        g.setFont(quitFont);
        g.drawString(QUIT, 
                buttons[2].x + (buttonWidth/2) - g.getFontMetrics().stringWidth(QUIT)/2, 
                buttons[2].y + buttonHeight/2);
    }

    @Override
    public void mousePressed(MouseEvent me) {

        Rectangle mouse = new Rectangle(me.getX(), me.getY(), 1, 1);
        
        if(buttons[0].intersects(mouse)){
            gsm.push(new PlayingState(gsm));
        }
        if(buttons[1].intersects(mouse)){
            gsm.push(new ScoreBoardState(gsm));
        }
        if(buttons[2].intersects(mouse)){
            System.exit(0);
        }

    }
}
