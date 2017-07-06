/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.players;


import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sa.boardgame.core.players.AIPlayer;
import sa.fx.draugths.BCDraugthsApp;
import sa.gameboard.core.Checker;

/**
 *
 * @author ale2s_000
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
