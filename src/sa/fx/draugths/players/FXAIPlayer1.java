/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.players;


import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import sa.fx.draugths.BCDraugthsApp;
import sa.gameboard.core.Piece;
import sa.gameboard.core.players.AIPlayer;

import java.util.logging.Level;

/**
 *
 * @author  Alessio Sardaro
 */
public class FXAIPlayer1 extends AIPlayer implements EventHandler<MouseEvent> {



    public FXAIPlayer1() {
        super(Piece.BLACK,false);

    }

    @Override
    public void handle(MouseEvent event) {
    	BCDraugthsApp.log.log(Level.INFO,".........................event:"+event.getEventType());
        event.consume();
        
    }

}
