/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.animation;

import sa.fx.draugths.sprite.SpritePiece;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.FXBoardClass;
import sa.gameboard.core.Checker;


/**
 *
 * @author ale2s_000
 */
public class PedinaAnimationEndHandler implements EventHandler<ActionEvent> {

    SpritePiece p;
    SpritePiece e;
    FXBoardClass fxboard;
    double wBoardBox, hBoadBoxs;
    
    Move m;
    public PedinaAnimationEndHandler(SpritePiece p, Move m, double wBoardBox, double hBoadBoxs,FXBoardClass bb) {
        this.p = p;
        this.fxboard = bb;
        this.wBoardBox = wBoardBox;
        this.hBoadBoxs = hBoadBoxs;
        this.m = m;
        this.e = null;
        
    }
    public PedinaAnimationEndHandler(SpritePiece p, Move m, SpritePiece e, double wBoardBox, double hBoadBoxs,FXBoardClass bb) {
        this.p = p;
        this.fxboard = bb;
        this.wBoardBox = wBoardBox;
        this.hBoadBoxs = hBoadBoxs;
        this.m = m;
        this.e = e;
         
    }

    @Override
    public void handle(ActionEvent event) {

        p.stop();
        if(e!=null) e.stop();
        if(m.getP().getColor()==Checker.WHITE) fxboard.getBackGround().updatePoint(m.calculateValue());
       if ( (m.getType() == Move.MOVE || m.getType()==Move.EAT) && 
            fxboard.getGame().getBoard().getBoardSideNorth()==m.getP().getColor() &&
            m.getJ1()==7) {
            p.setFrameDama();


        }else if((m.getType()==Move.MOVE || m.getType()==Move.EAT) &&
                this.fxboard.getGame().getBoard().getBoardSideSouth()==m.getP().getColor()  &&
                m.getJ1()==0){
                p.setFrameDama();
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
