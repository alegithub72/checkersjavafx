/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.event;

import sa.fx.draugths.sprite.SpritePiece;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import sa.fx.draugths.BCDraugthsApp;

/**
 *
 * @author ale2s_000
 */
public class SelectEventPlayer implements EventHandler<MouseEvent> {

    BCDraugthsApp gtable;
    SpritePiece p;

    public SelectEventPlayer(BCDraugthsApp gtable,SpritePiece p) {
        this.gtable = gtable;
        this.p=p;
    }


    @Override
    public void handle(MouseEvent event) {
        if(!this.gtable.isOn()){
        if(this.gtable.getSelect()!=null && this.gtable.getSelect()!=p ){
            this.gtable.getSelect().setFrame(0);

        }
        gtable.getMousePlayer().deleteMoveChoose();
        gtable.setSelect(p);
        p.setFrame(1);
        gtable.getMousePlayer().visualizeMove();
                
        }
                  
        event.consume();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }




}
