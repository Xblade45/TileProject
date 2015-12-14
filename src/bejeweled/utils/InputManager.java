/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bejeweled.utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 *
 * @author Xblade45
 */
public class InputManager implements KeyListener {
    
    public static boolean keys[] = new boolean[65536];
    
    
    // Constructor
    public InputManager(){}
    
    
    // Methods
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
    
}
