/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bejeweled.gameState;

import bejeweled.board.Background;
import bejeweled.main.Panel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 *
 * @author Xblade45
 */
public class ScoreBoardState extends GameState {
    
    private Background background;
    private String bestScore;
    
    private final Font SCOREBOARDFONT = new Font("Calibri", Font.BOLD, 45);
    
    private final String SCOREBOARDSTR = "SCOREBOARD";
    
    // Constructor
    public ScoreBoardState(GameStateManager gsm) {
        
        this.gsm = gsm;
        
        init();
    }
    
    // Methods
    @Override
    public final void init() {
        
        this.background = new Background("backgroundMenu");
        
        bestScore = "BEST SCORE : " + gsm.bestScore;
    }
    
    @Override
    public void update() {}
    
    @Override
    public void draw(Graphics g) {
        
        background.draw(g);
        
        g.setFont(gsm.TITLEFONT);
        g.setColor(gsm.COLOR);
        g.drawString(SCOREBOARDSTR, 
                Panel.WIDTH/2 - g.getFontMetrics().stringWidth(SCOREBOARDSTR)/2, 
                150);
        
        g.setFont(SCOREBOARDFONT);
        g.setColor(Color.WHITE);
        g.drawString(bestScore, 
                Panel.WIDTH/2 - g.getFontMetrics().stringWidth(bestScore)/2, 
                Panel.HEIGHT/2);
        
        g.setColor(Color.red);
        g.drawString("BACK", 
                Panel.WIDTH/2 - g.getFontMetrics().stringWidth("BACK")/2, 
                Panel.HEIGHT/2 + 100);
    }

    @Override
    public void mousePressed(MouseEvent me) {
    
        if(me.getX() > Panel.WIDTH/2 - 50 && me.getX() < Panel.WIDTH/2 + 50){
            if(me.getY() > Panel.HEIGHT/2 +70 && me.getY() < Panel.HEIGHT/2 +110){
                gsm.remove();
            }
        }
    
    }
}
