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
public class Timer {
    
    private int countDown;
    private long presentTime, startTime;
    private boolean isPaused;
    private volatile boolean isRunning;
    
    private Thread thread;
    
    
    // Constructor
    public Timer(int countDown){// timer in seconds
        
        this.countDown = countDown;
        init();
    }
    
    // Getters
    public long getPresentTime() {
        return presentTime;
    }
    public String getPresentTimeString(){
        return presentTime + "";
    }
    
    // Methods
    private void init(){
        
        this.isPaused = false;
        this.isRunning = false;
        this.presentTime = countDown;
    }
    
    public void start(){
        
        this.startTime = System.currentTimeMillis();
        this.isRunning = true;
        
        updateLoop();
    }
    
    private void updateLoop(){
        
        thread = new Thread(){
            
            public void run(){
                
                while(isRunning){
                    
                    if(!isPaused){
                        if(System.currentTimeMillis() - startTime > 1000 || presentTime == countDown){
                            
                            presentTime -= 1;
                            startTime = System.currentTimeMillis();
                        }
                    }
                    
                    if(presentTime < 0)
                        stopIt();
                    
                    try{
                        Thread.sleep(100);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }
    
    public void pause(){
        
        this.isPaused = true;
    }
    
    public void resume(){
        
        this.isPaused = false;
    }
    
    public void stopIt(){
        
        this.isRunning = false;
    }
}
