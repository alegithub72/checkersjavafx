/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.players;



import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.text.Text;

import sa.fx.draugths.FXBoard;
import sa.gameboard.core.Piece;
import sa.gameboard.core.players.HumanPlayer;
import sa.gameboard.core.players.Player;

/**
 *
 * @author  Alessio Sardaro
 */
public class FXPlayer extends HumanPlayer  {

	FXBoard fxboard;
    Group table;
    public FXPlayer(Group table,FXBoard board) {
        super(Piece.WHITE);
        this.fxboard=board;
        this.table=table;
    }


    @Override
    public void chooseMoveStep() throws Exception {
        getCommand();
    }

    @Override
    public void getCommand() {
        String r= fxboard.getCommand().getText(); //To change body of generated methods, choose Tools | Templates.
       Player player= fxboard.getGame().getHuman();
       if(player instanceof  HumanPlayer)   ((HumanPlayer)player).setCp(r);
       else new RuntimeException("Player is not a HumanPlayer");
    }

    @Override
    public boolean isLastComnad() {
        return false;
    }

    @Override
    public boolean isHuman() {
        return false;
    }


//    @Override
//    public void handle(ActionEvent event) {
//
//        fxboard.playComputerPlayer();
//
//        fxboard.getCommand().setText("");
//        if (fxboard.winCondition() == Piece.WHITE) table.getChildren().add(new Text("IL BIANCO HA VINTOOOOOOO"));
//        else if (fxboard.winCondition() == Piece.BLACK) table.getChildren().add(new Text("IL NERO HA VINTOOOOOOO"));
//
//        event.consume();
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }


    
}
