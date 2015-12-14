/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bejeweled.utils;

import java.io.InputStream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author Xblade45
 */
public class Sound {
    
    public static void play(String input){
        
        try {
            
            InputStream is = Sound.class.getClassLoader().getResourceAsStream("audio/" + input + ".mp3");
            Player player = new Player(is);
            
            Thread t = new Thread("SoundThread" + input){
                
                @Override
                public void run() {
                    try {
                        player.play();
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }
                }
                
            };
            t.start();
            
        }catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }
}
