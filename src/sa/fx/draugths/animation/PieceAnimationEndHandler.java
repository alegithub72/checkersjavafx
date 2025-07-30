/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.animation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import sa.gameboard.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.animation.event.EventDraugthTransform;
import sa.fx.draugths.board.event.EventEndTurnFX;
import sa.fx.draugths.board.event.EventPointUpdate;
import sa.fx.draugths.pieces.SpritePiece;
import sa.gameboard.core.Piece;

import java.util.logging.Level;


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
    	BCDraugthsApp.log.log(Level.INFO,"----------------START-------------------"+p);
    	BCDraugthsApp.log.log(Level.INFO,"...stop animation...");
        p.stopPlayAnimation();
        Parent parent=p.getParent();
        if(m.getP().getColor()==Piece.WHITE) {
        	BCDraugthsApp.log.log(Level.INFO,"FIRE..EventPointUpdate.MOVE_UPDATE");
        	p.fireEvent(new EventPointUpdate(p,parent,m, EventPointUpdate.MOVE_UPDATE));
        
        }
        if ( (m.getType() == Move.MOVE || m.getType()==Move.EAT) && 
            m.getI1()==7 && m.getP().getType()!=Piece.DRAUGTH) {
        	BCDraugthsApp.log.log(Level.INFO,"FIRE...PedinaAnimationEndHandler..EventDraugthTransform.DRAUGTH_EVENT....");
        	p.fireEvent(new EventDraugthTransform(p, parent, EventDraugthTransform.DRAUGTH_EVENT));

        }else if((m.getType()==Move.MOVE || m.getType()==Move.EAT) &&
                m.getI1()==0 && m.getP().getType()!=Piece.DRAUGTH){
           	BCDraugthsApp.log.log(Level.INFO,"FIRE....EventDraugthTransform.DRAUGTH_EVENT.... ");
        	p.fireEvent(new EventDraugthTransform(p, parent, EventDraugthTransform.DRAUGTH_EVENT));
 
           
        }
		

		 
		 
        	
        	BCDraugthsApp.log.log(Level.INFO,"EventEndTurn.END_TURN..."+p);
        	BCDraugthsApp.log.log(Level.INFO,"----------------END-------------------");
        	event.consume();
    }

}
