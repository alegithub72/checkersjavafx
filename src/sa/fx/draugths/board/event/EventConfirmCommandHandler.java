/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.board.event;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.players.FXPMousePlayer;


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
    	if(event.getEventType()==MouseEvent.MOUSE_CLICKED && event.getButton()==MouseButton.PRIMARY) {
        mousePlayer.setMoveChoose(n);
        BCDraugthsApp.log.info("HANDLE EventConfirmCommand Hai scelto la mossa "+n+"°");
        Move m=mousePlayer.chooseMove();

        //BCDraugthsApp.log.info("m="+m+", p.i="+m.getP().getI()+",p.j="+m.getP().getJ());
        if(!mousePlayer.makeMove(m)) throw new RuntimeException("Move not executed");
        mousePlayer.setMoveChoose(-1);
        bb.updateInterface(mousePlayer.getName(),m);
        mousePlayer.deleteMoveChoose();
        //app.makePlayer2Move();
        event.consume();
    	}
    }
    
}
