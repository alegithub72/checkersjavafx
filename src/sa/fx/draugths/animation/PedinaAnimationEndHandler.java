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
import sa.fx.draugths.BCDraugthsApp;
import sa.gameboard.core.Piece;


/**
 *
 * @author ale2s_000
 */
public class PedinaAnimationEndHandler implements EventHandler<ActionEvent> {

    SpritePiece p;
    SpritePiece e;
    BCDraugthsApp bCDamaGraphic;
    double wBoardBox, hBoadBoxs;
    Move m;
    public PedinaAnimationEndHandler(SpritePiece p, Move m,  BCDraugthsApp bCDamaGraphic, double wBoardBox, double hBoadBoxs) {
        this.p = p;
        this.bCDamaGraphic = bCDamaGraphic;
        this.wBoardBox = wBoardBox;
        this.hBoadBoxs = hBoadBoxs;
        this.m = m;
        this.e = null;
    }
    public PedinaAnimationEndHandler(SpritePiece p, Move m, SpritePiece e, BCDraugthsApp bCDamaGraphic, double wBoardBox, double hBoadBoxs) {
        this.p = p;
        this.bCDamaGraphic = bCDamaGraphic;
        this.wBoardBox = wBoardBox;
        this.hBoadBoxs = hBoadBoxs;
        this.m = m;
        this.e = e;
    }

    @Override
    public void handle(ActionEvent event) {

        p.stop();
        if(e!=null) e.stop();
        if(m.getP().getColor()==Piece.WHITE) bCDamaGraphic.updatePoint(m.calculateValue());
        if ( (m.getType() == Move.MOVE || m.getType()==Move.EAT) && 
            this.bCDamaGraphic.getGame().getBoard().getBoardSideNorth()==m.getP().getColor() &&
            m.getJ1()==7) {
            p.setFrameDama();


        }else if((m.getType()==Move.MOVE || m.getType()==Move.EAT) &&
                this.bCDamaGraphic.getGame().getBoard().getBoardSideSouth()==m.getP().getColor()  &&
                m.getP().getJ()==0)

        if (e != null) {
            bCDamaGraphic.removeSpritePiece(e, m.getEat().getColor());
        }


        p.removeAnimationSetting();
        bCDamaGraphic.turnEnd();
        event.consume();
    }

}
