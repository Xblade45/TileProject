/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bejeweled.board;

import bejeweled.gameState.GameEngine;
import bejeweled.gameState.PlayingState;
import bejeweled.utils.ScoreCounter;
import bejeweled.utils.Timer;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Xblade45
 */
public class Board implements GameEngine, MouseListener, Runnable{

    public static final int COLUMN = 8;// X Axis
    public static final int ROW = 8;// Y Axis
    
    private final int BOARDX = 337;// X0
    private final int BOARDY = 64;// Y0
    
    private Tile[][] tab;
    private Tile selectedTile;

    private ScoreCounter scoreCounter;
    
    private PlayingState playingState;// reference
    
    private Timer clusterTimer;
    
    private final int HORIZONTAL = 0;
    private final int VERTICAL = 1;

    
    // Constructor
    public Board(PlayingState playingState){
        
        this.playingState = playingState;
        init();
    }
    
    // Getters-Setters
    public ScoreCounter getScoreCounter() {
        return scoreCounter;
    }
    
    // Methods
    private Tile getTile(int posX, int posY){
        
        return tab[posX][posY];
    }
    
    private void generate(){// create an array of tiles and put it in class Tile[] tab array
        
        Tile[][] t = new Tile[COLUMN][ROW];
        
        for(int i=0; i<COLUMN; i++){
            for(int j=0; j<ROW; j++){
                Tile temp = new Tile(i, j);
                t[i][j] = temp;
            }
        }
        this.tab = t;
    }
    
    private void swapTiles(Tile t1, Tile t2){// swap two tiles in the tab array

        int posXt1 = t1.getPosX();
        int posYt1 = t1.getPosY();
        int posXt2 = t2.getPosX();
        int posYt2 = t2.getPosY();
        
        t1.changePosition(posXt2, posYt2);
        t2.changePosition(posXt1, posYt1);

        tab[posXt1][posYt1] = t2;
        tab[posXt2][posYt2] = t1;
    }
    
    private void drawBoard(Graphics g){// call each tile to draw itself 
        
        for(int i=0; i<COLUMN; i++){
            
            for(int j=0; j<ROW; j++){
                
                getTile(i, j).draw(g);
            }
        }
    }
    
    private boolean findCluster(int posX, int posY){
        
        for(int i=0; i < COLUMN-1; i++){// horizontal
            
            int counter = 1;
            while(i+counter < COLUMN 
            && getTile(i, posY).getType() == getTile(i+counter, posY).getType()){
                counter++;
            }

            if(counter>=3){
                
                clusterTimer = new Timer(1);
                clusterTimer.start();

                if(playingState.isGameUnPaused())
                    scoreCounter.setScore(counter);

                removeCluster(counter, i, posY, HORIZONTAL);
                
                return true;
            }
        }
        
        for(int j=0; j < ROW-1; j++){// vertical
            
            int counter = 1;
            while(j+counter < ROW 
                    && getTile(posX, j).getType() == getTile(posX, j+counter).getType()){
                counter++;
            }
                
            if(counter>=3){
                
                clusterTimer = new Timer(1);
                clusterTimer.start();

                if(playingState.isGameUnPaused())
                    scoreCounter.setScore(counter);

                removeCluster(counter, posX, j, VERTICAL);

                return true;
            }
                
        }
        return false;
    }
    
    private void removeCluster(int counter, int posX, int posY, int type){
        
        if(type == HORIZONTAL){
            
            for(int k=0; k<counter; k++)
                tab[posX+k][posY].setType(-1);
        }
        
        if(type == VERTICAL){
            
            for(int k=0; k<counter; k++)
                tab[posX][posY+k].setType(-1);
        }
    }
    
    private void dropTile(int posX, int posY){

        if(posY == 0){
            
            if(getTile(posX, posY).getType() == -1)
                tab[posX][posY] = new Tile(posX, posY);
        }else{
            
            if(getTile(posX, posY).getType() == -1)
                swapTiles(getTile(posX, posY), getTile(posX, posY-1));
        }
    }
    
    //Overridable methods
    @Override
    public final void init() {
        
        this.clusterTimer = new Timer(1);
        this.selectedTile = null;
        this.scoreCounter = new ScoreCounter();
        
        clusterTimer.start();
        generate();
    }
    
    @Override
    public void run() {}
    
    @Override
    public void update() {
        
        for(int i = 0; i < COLUMN; i++){
            
            for(int j = 0; j < ROW; j++){
                
                getTile(i, j).update();
                
                if(clusterTimer.getPresentTime() == -1){
                    
                    findCluster(i, j);
                }
                
                dropTile(i, j);
            }
        }
    }
        
    @Override
    public void draw(Graphics g) {
        
        drawBoard(g);
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {}
    
    @Override
    public void mousePressed(MouseEvent me) {

        // Is it Inside the board?
        if(me.getX() < BOARDX + (Board.COLUMN * Tile.TILESIZE)
        && me.getX() > BOARDX
        && me.getY() < BOARDY + (Board.ROW * Tile.TILESIZE)
        && me.getY() > BOARDY){
            
            int clickX = (me.getX() - BOARDX)/Tile.WIDTH;
            int clickY = (me.getY() - BOARDY)/Tile.HEIGHT;

            if(getTile(clickX, clickY).isSelected()){// if mousepressed tile is selected, unselect
                
                getTile(clickX, clickY).setSelected(false);
                this.selectedTile = null;
            }else{
                if(!getTile(clickX, clickY).isSelected() && selectedTile == null){// if mousepressed tile is unselected, select
                    
                    getTile(clickX, clickY).setSelected(true);
                    this.selectedTile = getTile(clickX, clickY);
                }else{
                    
                    if(selectedTile.isNeighbor(getTile(clickX, clickY))){// if selected tile has neighbor(+-1(XY) distance) in mousepressed, swap
                        
                        int selX = selectedTile.getPosX();
                        int selY = selectedTile.getPosY();
                        this.selectedTile = null;
                        
                        getTile(selX, selY).setSelected(false);
                        swapTiles(getTile(clickX, clickY), getTile(selX, selY));
                        
                        if(!(findCluster(clickX, clickY) || findCluster(selX, selY)))
                            swapTiles(getTile(clickX, clickY), getTile(selX, selY));
                    }
                }
            }
            System.out.println(clickX + "-" + clickY);
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent me) {}
    @Override
    public void mouseEntered(MouseEvent me) {}
    @Override
    public void mouseExited(MouseEvent me) {}
    
}
