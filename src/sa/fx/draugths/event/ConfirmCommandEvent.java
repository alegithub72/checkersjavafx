/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.event;

import sa.boardgame.core.moves.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.players.FXPMousePlayer;


/**
 *
 * @author ale2s_000
 */
public class ConfirmCommandEvent implements EventHandler<MouseEvent>{
    int n;
    FXBoard bb;
    FXPMousePlayer mousePlayer;
    
  public   ConfirmCommandEvent(FXPMousePlayer mousePLayer,int n,FXBoard bb){
        this.n=n;
        this.bb=bb;
        mousePlayer=mousePLayer;
         
    }
    @Override
    public void handle(MouseEvent event) {
        mousePlayer.setMoveChoose(n);
        BCDraugthsApp.log.info("Hai scelto "+n);
        Move m=mousePlayer.chooseMove();

        //System.out.println("m="+m+", p.i="+m.getP().getI()+",p.j="+m.getP().getJ());
        if(!mousePlayer.makeMove(m)) throw new RuntimeException("Move not executed");
        mousePlayer.setMoveChoose(-1);
        bb.updateInterface(mousePlayer.getName(),m);
        mousePlayer.deleteMoveChoose();
        //app.makePlayer2Move();
        event.consume();
    }
    
}
