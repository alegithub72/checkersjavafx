/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.event;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.sprite.SpritePiece;

/**
 *
 * @author ale2s_000
 */
public class SelectEventPlayer implements EventHandler<MouseEvent> {

    FXBoard fxb;
    SpritePiece p;

    public SelectEventPlayer(FXBoard board,SpritePiece p) {
        this.fxb = board;
        this.p=p;
    }


    @Override
    public void handle(MouseEvent event) {
        if(!this.fxb.isOn()){
        if(this.fxb.getSelect()!=null && this.fxb.getSelect()!=p ){
            this.fxb.getSelect().setFrame(0);

        }
        fxb.getMousePlayer().deleteMoveChoose();
        fxb.setSelect(p);
        p.setFrame(1);
        fxb.getMousePlayer().choosePiece();
                
        }
                  
        event.consume();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }




}
