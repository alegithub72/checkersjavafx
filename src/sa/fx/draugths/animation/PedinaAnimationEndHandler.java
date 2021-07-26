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
import sa.fx.draugths.animation.event.EventRemoveEatPiece;
import sa.fx.draugths.event.EventEndTurn;
import sa.fx.draugths.event.EventPointUpdate;
import sa.fx.draugths.sprite.SpritePiece;
import sa.gameboard.core.Checker;


/**
 *
 * @author ale2s_000
 */
public class PedinaAnimationEndHandler implements EventHandler<ActionEvent> {

    SpritePiece p;



    
    Move m;
    public PedinaAnimationEndHandler(SpritePiece p, Move m) {
        this.p = p;
        this.m = m;

        
    }


    @Override
    public void handle(ActionEvent event) {
    	Node n =null;
    	BCDraugthsApp.log.info(" associated to sprite :"+p);
//    	BCDraugthsApp.log.info(" eventSource:"+event.getSource());
//    	BCDraugthsApp.log.info(" eventTarget:"+event.getTarget());
    	BCDraugthsApp.log.info(" eventType:"+event.getEventType());
        p.stop();
        FXBoard fxBoard=p.getFxBoard();

        //TODO: fire point event...
        if(m.getP().getColor()==Checker.WHITE) {
        	BCDraugthsApp.log.info("Event fired PointUpdateEvent");
        	fxBoard.fireEvent(new EventPointUpdate(m,null, EventPointUpdate.MOVE_UPDATE));
        
        }
        // p.removeAnimationSetting();

        if ( (m.getType() == Move.MOVE || m.getType()==Move.EAT) && 
            m.getI1()==7 && m.getP().getType()!=Checker.DRAUGTH) {
        	fxBoard.fireEvent(new EventDraugthTransform(p, p, EventDraugthTransform.DRAUGTH_EVENT));

        }else if((m.getType()==Move.MOVE || m.getType()==Move.EAT) &&
                m.getI1()==0 && m.getP().getType()!=Checker.DRAUGTH){
        	fxBoard.fireEvent(new EventDraugthTransform(p, p, EventDraugthTransform.DRAUGTH_EVENT));

           
        }



       if(m.getType()==Move.EAT) fxBoard.fireEvent(new EventRemoveEatPiece(m, p, EventRemoveEatPiece.EAT_EVENT));
       fxBoard.fireEvent(new EventEndTurn(m, p,EventEndTurn.END_TURN));

        for(int h=0;h<2;h++){
            p.removeExtraSprite(h);
        }


    }

}
