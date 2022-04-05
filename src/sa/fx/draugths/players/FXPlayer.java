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
import sa.boardgame.core.players.HumanPlayer;
import sa.fx.draugths.FXBoard;
import sa.gameboard.core.Piece;

/**
 *
 * @author  Alessio Sardaro
 */
public class FXPlayer extends HumanPlayer  implements EventHandler<ActionEvent>  {

	FXBoard fxboard;
    Group table;
    public FXPlayer(Group table,FXBoard board) {
        super(Piece.WHITE);
        this.fxboard=board;
        this.table=table;
    }


	@Override
    public String getCommand() {
        String r= fxboard.getCommand().getText(); //To change body of generated methods, choose Tools | Templates.
        return r;
    }




    
    
    
    

    @Override
    public void handle(ActionEvent event) {
        
        fxboard.playComputerPlayer();
        
        fxboard.getCommand().setText("");
        if (fxboard.winCondition() == Piece.WHITE) table.getChildren().add(new Text("IL BIANCO HA VINTOOOOOOO"));
        else if (fxboard.winCondition() == Piece.BLACK) table.getChildren().add(new Text("IL NERO HA VINTOOOOOOO"));

        event.consume();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
}
