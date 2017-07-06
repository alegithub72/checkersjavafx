/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.event;

import sa.fx.draugths.sprite.SpritePiece;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import sa.fx.draugths.FXBoardClass;

/**
 *
 * @author ale2s_000
 */
public class SelectEventPlayer implements EventHandler<MouseEvent> {

    FXBoardClass fxb;
    SpritePiece p;

    public SelectEventPlayer(FXBoardClass board,SpritePiece p) {
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
        fxb.getMousePlayer().visualizeMove();
                
        }
                  
        event.consume();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }




}
