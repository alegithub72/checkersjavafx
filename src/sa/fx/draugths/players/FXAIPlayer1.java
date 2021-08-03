/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.players;


import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import sa.boardgame.core.players.AIPlayer;
import sa.gameboard.core.Checker;

/**
 *
 * @author  Alessio Sardaro
 */
public class FXAIPlayer1 extends AIPlayer implements EventHandler<MouseEvent> {



    public FXAIPlayer1() {
        super(Checker.BLACK);

    }

    @Override
    public void handle(MouseEvent event) {
    
        event.consume();
        
    }

}
