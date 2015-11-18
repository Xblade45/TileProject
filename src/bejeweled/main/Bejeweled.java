/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bejeweled.main;

import javax.swing.JFrame;

/**
 *
 * @author Xblade45
 */
public class Bejeweled {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        
        JFrame frame = new JFrame("Bejeweled");
        Panel p = new Panel();
        
        frame.setContentPane(p);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
