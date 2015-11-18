/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bejeweled.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author Xblade45
 */
public class Sound {
    
    public static void play(String input){
        
        try {
            
            File file = new File(Sound.class.getClassLoader().getResource("audio/" + input + ".mp3").toURI());
            FileInputStream fis = new FileInputStream(file);
            final Player player = new Player(fis);
            
            Thread t = new Thread(){

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
            
        }catch (FileNotFoundException | URISyntaxException | JavaLayerException e) {
            e.printStackTrace();
        }
    }
}
