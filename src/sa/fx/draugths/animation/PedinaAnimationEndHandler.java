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
    	BCDraugthsApp.log.info("PedinaAnimationEndHandler..start fire...."+p.getColorFX());
    	//BCDraugthsApp.log.info(" associated to sprite :"+p);
//    	BCDraugthsApp.log.info(" eventSource:"+event.getSource());
//    	BCDraugthsApp.log.info(" eventTarget:"+event.getTarget());
    	//BCDraugthsApp.log.info(" eventType:"+event.getEventType());
        p.stop();
        FXBoard fxBoard=p.getFxBoard();

        //TODO: fire point event...
        if(m.getP().getColor()==Checker.WHITE) {
        	BCDraugthsApp.log.info("PedinaAnimationEndHandler fired PointUpdateEvent..."+p.getColorFX());
        	fxBoard.fireEvent(new EventPointUpdate(m,fxBoard, EventPointUpdate.MOVE_UPDATE));
        
        }
        // p.removeAnimationSetting();

        if ( (m.getType() == Move.MOVE || m.getType()==Move.EAT) && 
            m.getI1()==7 && m.getP().getType()!=Checker.DRAUGTH) {
        	fxBoard.fireEvent(new EventDraugthTransform(p, fxBoard, EventDraugthTransform.DRAUGTH_EVENT));
        	BCDraugthsApp.log.info("PedinaAnimationEndHandler..fire....EventDraugthTransform...."+p.getColorFX());
        }else if((m.getType()==Move.MOVE || m.getType()==Move.EAT) &&
                m.getI1()==0 && m.getP().getType()!=Checker.DRAUGTH){
        	fxBoard.fireEvent(new EventDraugthTransform(p, fxBoard, EventDraugthTransform.DRAUGTH_EVENT));
        	BCDraugthsApp.log.info("PedinaAnimationEndHandler..fire....EventDraugthTransform "+p.getColorFX());
           
        }



       if(m.getType()==Move.EAT) {
    	   SpritePiece eated= p.getFxBoard().getSpritePiece(m.getEat().getI(), m.getEat().getJ(), m.getEat().getColor(), false);
    	   fxBoard.removeSpritePiece(eated);
       }

       
        for(int h=0;h<2;h++){
            p.removeExtraSprite(h);
        }
        //TODO:rimuovere da qui mettere da un altra parte...
        	BCDraugthsApp.log.info("PedinaAnimationEndHandler..fire...EventEndTurn..."+p.getColorFX());
        	BCDraugthsApp.log.info("PedinaAnimationEndHandler..end...."+p.getColorFX());
        	fxBoard.fireEvent(new EventEndTurn(p, fxBoard,EventEndTurn.END_TURN));

            //fxBoard.setAnimationOn(false);
    }

}
