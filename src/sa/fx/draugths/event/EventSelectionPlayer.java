/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.event;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.sprite.SpritePiece;

/**
 *
 * @author  Alessio Sardaro
 */
public class EventSelectionPlayer implements EventHandler<MouseEvent> {

	FXBoard fxb;
    SpritePiece p;

    public EventSelectionPlayer(FXBoard board,SpritePiece p) {
        this.fxb = board;
        this.p=p;
    }


    @Override
    public void handle(MouseEvent event) {
        if(!this.fxb.isAnimationOn()){
        if(this.fxb.getSelect()!=null && this.fxb.getSelect()!=p ){
            this.fxb.getSelect().setFrame(0);

        }
        fxb.getMousePlayer().deleteMoveChoose();
        fxb.setSelect(p);
        p.setFrame(1);
        fxb.getMousePlayer().choosePiece();
                
        }
       BCDraugthsApp.log.info(" cliecked id="+p.getId());
        event.consume();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }




}
