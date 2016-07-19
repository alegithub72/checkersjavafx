/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.event;

import sa.fx.draugths.players.FXPMousePlayer;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;


/**
 *
 * @author ale2s_000
 */
public class ConfirmCommandEvent implements EventHandler<MouseEvent>{
    int n;
    BCDraugthsApp app;
    FXPMousePlayer mousePlayer;
    
  public   ConfirmCommandEvent(FXPMousePlayer mousePLayer,int n,BCDraugthsApp app){
        this.n=n;
        this.app=app;
        mousePlayer=mousePLayer;
         
    }
    @Override
    public void handle(MouseEvent event) {
        mousePlayer.setMoveChoose(n);
        System.out.println("Hai scelto "+n);
        Move m=mousePlayer.confirmMove();

        System.out.println("m="+m+", p.i="+m.getP().getI()+",p.j="+m.getP().getJ());
        if(!mousePlayer.makeMove(m)) throw new RuntimeException("Move not executed");
        mousePlayer.setMoveChoose(-1);
        app.updateInterface(m);
        //app.makePlayer2Move();
        event.consume();
    }
    
}
