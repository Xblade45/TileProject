/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bejeweled.utils;

/**
 *
 * @author Xblade45
 */
public class ScoreCounter {
    
    private int score;
    private final int baseScore = 10;
    private final int baseScoreIncrement = 30;
    
    
    // Constructor
    public ScoreCounter(){
        
        init();
    }
    
    private void init(){
        
        this.score = 0;
    }
    
    // Getters & Setters
    public int getScoreInt() {
        return score;
    }
    
    // Methods
    public void setScore(int nbOfTiles){
        
        this.score += baseScore + (nbOfTiles*baseScoreIncrement);
    }
    
    public String getScoreString(){
        
        return String.format("%04d", score);
    }
}
