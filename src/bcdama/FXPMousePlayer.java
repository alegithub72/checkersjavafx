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
public class FXPMousePlayer extends HumanKeyboard implements EventHandler<MouseEvent> {

    BCDamaGraphic gtable;
    

    public FXPMousePlayer(BCDamaGraphic gtable) {
        super(PedinaChar.WHITE, gtable);
        this.gtable = gtable;

        

    }

    @Override
    public void getCommand(int sca[][]) {
        this.r= "p" + gtable.select.getPedinaCharAssociated().getX() + gtable.select.getPedinaCharAssociated().getY();
        listMove(sca);
    }

    @Override
    public void handle(MouseEvent event) {
        if(!gtable.on) {
            if(gtable.select!=null) {
               // getCommand(gtable.game.getDamaSystem(0));
               // this.renderMove();
            }
            
        }
        event.consume();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void getConfirmCommand() {
        r="";
        r= gtable.confirmCommand; //To change body of generated methods, choose Tools | Templates.
    }



}
