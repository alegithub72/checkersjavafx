/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.board.event;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.players.FXPMousePlayer;
import sa.gameboard.core.events.ComandEvent;
import sa.gameboard.core.moves.Move;

import java.util.logging.Level;


/**
 *
 * @author  Alessio Sardaro
 */
public class EventConfirmCommandHandler implements EventHandler<MouseEvent>{
    int n;
    FXBoard bb;
    FXPMousePlayer mousePlayer;
    
  public   EventConfirmCommandHandler(FXPMousePlayer mousePLayer,int n,FXBoard bb){
	  	super();
        this.n=n;
        this.bb=bb;
        mousePlayer=mousePLayer;
         
    }
    @Override
    public void handle(MouseEvent event) {
        EventType eventHappened=event.getEventType();
        ComandEvent.ComandState state=mousePlayer.getComandEventDispatcher().getCurrentEvent().getComandState();
        BCDraugthsApp.log.log(Level.INFO,"HANDLE EventConfirmCommand Game State {0}  ",new Object[]{state.name(),"xxx"});
    	if(eventHappened==MouseEvent.MOUSE_CLICKED && event.getButton()==MouseButton.PRIMARY) {
//        mousePlayer.setMoveChoose(n);
//        mousePlayer.setCp(""+n);


            try {

                switch(state) {
                    case CHOOSE_PIECE:
                        mousePlayer.setMoveChoose(n);
                        mousePlayer.deleteUnselectedChoose(n);
                        mousePlayer.setCp(""+n);
                        BCDraugthsApp.log.log(Level.INFO,"HANDLE EventConfirmCommand Hai scelto la mossa "+n+"°");
                        break;
                    case CHOOSE_MOVE:
                        mousePlayer.setCp("yes");
                        //TODO: aggiungi un mouseImage....
                        BCDraugthsApp.log.log(Level.INFO,"------------->aggiungere image mouse click icon<-----------------");
                        break;

                    default:
                        break;
                }


            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            //BCDraugthsApp.log.log(Level.INFO,"m="+m+", p.i="+m.getP().getI()+",p.j="+m.getP().getJ());
//        if(!mousePlayer.makeMove(m)) throw new RuntimeException("Move not executed");
//        mousePlayer.setMoveChoose(-1);
//        bb.updateInterface(mousePlayer.getName(),m);
//        mousePlayer.deleteMoveChoose();
//        //app.makePlayer2Move();
        event.consume();
    	}
        if (event.getEventType()==MouseEvent.MOUSE_CLICKED && event.getButton()==MouseButton.SECONDARY) {
            try {
                BCDraugthsApp.log.log(Level.INFO,"HANDLE EventConfirmCommand Hai scelto la mossa no");
                BCDraugthsApp.log.log(Level.INFO,"screen x {0}, screen y {1}",new Object[] {event.getSceneX(),event.getSceneY()});
                mousePlayer.setCp("no");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    
}
