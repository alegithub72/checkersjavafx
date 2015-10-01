/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcdama;

import dama.core.AIDama;
import dama.core.DamaInterface;
import dama.core.Game;
import dama.core.HumanKeyboard;
import dama.core.Move;
import dama.core.PedinaChar;
import dama.core.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 *
 * @author ale2s_000
 */
public class FXPlayer extends HumanKeyboard  implements EventHandler<ActionEvent>  {

    BCDamaGraphic gtable;
    Group table;
    public FXPlayer(Group table,BCDamaGraphic gtable) {
        super(PedinaChar.WHITE);
        gtable=gtable;
        this.table=table;
    }

    @Override
    public void getCommand(int sca[][]) {
        this.r= gtable.command.getText(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    

    @Override
    public void handle(ActionEvent event) {
        
        gtable.playPlayer1();
        
        gtable.command.setText("");
        if (gtable.winCondition() == PedinaChar.WHITE) table.getChildren().add(new Text("IL BIANCO HA VINTOOOOOOO"));
        else if (gtable.winCondition() == PedinaChar.BLACK) table.getChildren().add(new Text("IL NERO HA VINTOOOOOOO"));
        else {
            gtable.playPlayer2();
        
        }
        event.consume();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
}
