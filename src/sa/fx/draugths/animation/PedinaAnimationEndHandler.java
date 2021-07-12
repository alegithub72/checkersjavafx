/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.animation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.event.SelectEventPlayer;
import sa.fx.draugths.sprite.SpritePiece;
import sa.gameboard.core.Checker;


/**
 *
 * @author ale2s_000
 */
public class PedinaAnimationEndHandler implements EventHandler<ActionEvent> {

    SpritePiece p;
    SpritePiece e;
    FXBoard fxboard;
    double wBoardBox, hBoadBoxs;
    
    Move m;
    public PedinaAnimationEndHandler(SpritePiece p, Move m, double wBoardBox, double hBoadBoxs,FXBoard bb) {
        this.p = p;
        this.fxboard = bb;
        this.wBoardBox = wBoardBox;
        this.hBoadBoxs = hBoadBoxs;
        this.m = m;
        this.e = null;
        
    }
    public PedinaAnimationEndHandler(SpritePiece p, Move m, SpritePiece e, double wBoardBox, double hBoadBoxs,FXBoard bb) {
        this.p = p;
        this.fxboard = bb;
        this.wBoardBox = wBoardBox;
        this.hBoadBoxs = hBoadBoxs;
        this.m = m;
        this.e = e;
         
    }

    @Override
    public void handle(ActionEvent event) {
    	Node n =null;
    	BCDraugthsApp.log.info(" associated to sprite :"+p);
    	BCDraugthsApp.log.info(" eventSource:"+event.getSource());
    	BCDraugthsApp.log.info(" eventTarget:"+event.getTarget());
        p.stop();
        if(e!=null) e.stop();
        //TODO: fire point event...
        if(m.getP().getColor()==Checker.WHITE) fxboard.getBackGround().updatePoint(m.calculateValue());
      
        if ( (m.getType() == Move.MOVE || m.getType()==Move.EAT) && 
            fxboard.getGame().getBoard().getColorSideNorth()==m.getP().getColor() &&
            m.getI1()==7 && m.getP().getType()!=Checker.DRAUGTH) {
    	   
    	    fxboard.remove(p);
    	    SpritePiece dama=  fxboard.buildPedina(p.getBoardPieceLink().getColor(), 
    	    		p.getBoardPieceLink(), fxboard.getLevel());
    	    		
    	    dama.setDraugthTransform(true);
            dama.recalculateXYPosition();
            dama.setOnMouseClicked(new SelectEventPlayer(fxboard,dama));
            fxboard.add(dama);
            fxboard.replace(  p.getK() , p.getBoardPieceLink().getColor(), dama);            

        }else if((m.getType()==Move.MOVE || m.getType()==Move.EAT) &&
                this.fxboard.getGame().getBoard().getBoardSideSouth()==m.getP().getColor()  &&
                m.getI1()==0 && m.getP().getType()!=Checker.DRAUGTH){
     	   fxboard.remove(p);
     	   SpritePiece dama=fxboard.buildPedina(p.getBoardPieceLink().getColor(), 
   	    		p.getBoardPieceLink(), fxboard.getLevel());
     	   dama.setDraugthTransform(true);
           fxboard.add(dama);
           fxboard.replace(  p.getK(),p.getBoardPieceLink().getColor(),   dama);
           dama.recalculateXYPosition();
           dama.setOnMouseClicked(new SelectEventPlayer(fxboard,dama));
           
        }

        if (e != null) {
            fxboard.removeSpritePiece(e);
        }

        for(int h=0;h<2;h++){
            p.removeExtraSprite(h);
        }
        p.removeAnimationSetting();
        fxboard.turnEnd();
        event.consume();
    }

}
