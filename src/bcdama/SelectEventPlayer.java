/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcdama;

import dama.core.AIDama;
import dama.core.Console;
import dama.core.DamaInterface;
import dama.core.Game;
import dama.core.HumanKeyboard;
import dama.core.Move;
import dama.core.PedinaChar;
import dama.core.Player;
import java.util.Iterator;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 *
 * @author ale2s_000
 */
public class SelectEventPlayer implements EventHandler<MouseEvent> {

    BCDamaGraphic gtable;
    PedinaGeneral p;

    public SelectEventPlayer(BCDamaGraphic gtable,PedinaGeneral p) {
        this.gtable = gtable;
        this.p=p;
    }


    @Override
    public void handle(MouseEvent event) {
        if(!this.gtable.on){
        if(this.gtable.select!=null && this.gtable.select!=p ){
            this.gtable.select.setFrame(0);
            gtable.removePuntatori();
        }
        this.gtable.select=this.p;
        p.setFrame(1);
        gtable.player2.getCommand(gtable.game.getDamaSystem(0));
        gtable.player2.renderMove();
        }
        event.consume();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }




}
