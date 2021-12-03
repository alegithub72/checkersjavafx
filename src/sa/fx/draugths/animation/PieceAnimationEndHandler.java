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
import sa.fx.draugths.animation.event.EventDraugthTransform;
import sa.fx.draugths.board.event.EventEndTurn;
import sa.fx.draugths.board.event.EventPointUpdate;
import sa.fx.draugths.sprite.SpritePiece;
import sa.gameboard.core.Piece;


/**
 *
 * @author  Alessio Sardaro
 */
public class PieceAnimationEndHandler implements EventHandler<ActionEvent> {

    SpritePiece p;



    
    Move m;
    public PieceAnimationEndHandler(SpritePiece p, Move m) {
        this.p = p;
        this.m = m;

        
    }


    @Override
    public void handle(ActionEvent event) {
    	Node n =null;
    	BCDraugthsApp.log.info("----------------START-------------------"+p);
    	BCDraugthsApp.log.info("...stop animation...");
        p.stopPlayAnimation();
        FXBoard fxBoard=p.getFxBoard();
        if(m.getP().getColor()==Piece.WHITE) {
        	BCDraugthsApp.log.info("FIRE..EventPointUpdate.MOVE_UPDATE");
        	p.fireEvent(new EventPointUpdate(p,fxBoard,m, EventPointUpdate.MOVE_UPDATE));
        
        }
        if ( (m.getType() == Move.MOVE || m.getType()==Move.EAT) && 
            m.getI1()==7 && m.getP().getType()!=Piece.DRAUGTH) {
        	BCDraugthsApp.log.info("FIRE...PedinaAnimationEndHandler..EventDraugthTransform.DRAUGTH_EVENT....");
        	p.fireEvent(new EventDraugthTransform(p, fxBoard, EventDraugthTransform.DRAUGTH_EVENT));

        }else if((m.getType()==Move.MOVE || m.getType()==Move.EAT) &&
                m.getI1()==0 && m.getP().getType()!=Piece.DRAUGTH){
           	BCDraugthsApp.log.info("FIRE....EventDraugthTransform.DRAUGTH_EVENT.... ");
        	p.fireEvent(new EventDraugthTransform(p, fxBoard, EventDraugthTransform.DRAUGTH_EVENT));
 
           
        }
		

		 
		 
        	
        	BCDraugthsApp.log.info("EventEndTurn.END_TURN..."+p);
        	fxBoard.fireEvent(new EventEndTurn(p, fxBoard,EventEndTurn.END_TURN));
        	BCDraugthsApp.log.info("----------------END-------------------");
        	event.consume();
    }

}
