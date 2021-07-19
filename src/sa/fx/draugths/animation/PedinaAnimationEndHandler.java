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
import sa.fx.draugths.event.DraugthTransformEvent;
import sa.fx.draugths.event.EatPieceEvent;
import sa.fx.draugths.event.EndTurnEvent;
import sa.fx.draugths.event.PointUpdateEvent;
import sa.fx.draugths.sprite.SpritePiece;
import sa.gameboard.core.Checker;


/**
 *
 * @author ale2s_000
 */
public class PedinaAnimationEndHandler implements EventHandler<ActionEvent> {

    SpritePiece p;
    SpritePiece e;


    
    Move m;
    public PedinaAnimationEndHandler(SpritePiece p, Move m) {
        this.p = p;
        this.m = m;
        this.e = null;
        
    }
    public PedinaAnimationEndHandler(SpritePiece p, Move m, SpritePiece e) {
        this.p = p;
        this.m = m;
        this.e = e;
         
    }

    @Override
    public void handle(ActionEvent event) {
    	Node n =null;
    	BCDraugthsApp.log.info(" associated to sprite :"+p);
//    	BCDraugthsApp.log.info(" eventSource:"+event.getSource());
//    	BCDraugthsApp.log.info(" eventTarget:"+event.getTarget());
    	BCDraugthsApp.log.info(" eventType:"+event.getEventType());
        p.stop();
        if(e!=null) e.stop();
        //TODO: fire point event...
        if(m.getP().getColor()==Checker.WHITE) {
        	BCDraugthsApp.log.info("Event fired PointUpdateEvent");
        	p.fireEvent(new PointUpdateEvent(m,null, PointUpdateEvent.MOVE_UPDATE));
        
        }
      
        if ( (m.getType() == Move.MOVE || m.getType()==Move.EAT) && 
            m.getI1()==7 && m.getP().getType()!=Checker.DRAUGTH) {
        	p.fireEvent(new DraugthTransformEvent(p, p, DraugthTransformEvent.DRAUGTH_EVENT));

        }else if((m.getType()==Move.MOVE || m.getType()==Move.EAT) &&
                m.getI1()==0 && m.getP().getType()!=Checker.DRAUGTH){
        	p.fireEvent(new DraugthTransformEvent(p, p, DraugthTransformEvent.DRAUGTH_EVENT));

           
        }

        if (e != null) {
        	e.fireEvent(new EatPieceEvent(e, p, EatPieceEvent.EAT_EVENT));
            //fxboard.removeSpritePiece(e);
        }

        for(int h=0;h<2;h++){
            p.removeExtraSprite(h);
        }
        p.removeAnimationSetting();
        p.fireEvent(new EndTurnEvent(p, null,EndTurnEvent.END_TURN));

    }

}
