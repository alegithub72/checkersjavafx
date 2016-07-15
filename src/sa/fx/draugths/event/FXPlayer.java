/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.event;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.text.Text;
import sa.boardgame.core.moves.Move;
import sa.boardgame.core.players.HumanPlayer;
import sa.fx.draugths.BCDraugthsApp;
import sa.gameboard.core.Piece;

/**
 *
 * @author ale2s_000
 */
public class FXPlayer extends HumanPlayer  implements EventHandler<ActionEvent>  {

    BCDraugthsApp gtable;
    Group table;
    public FXPlayer(Group table,BCDraugthsApp gtable) {
        super(Piece.WHITE);
        gtable=gtable;
        this.table=table;
    }

    @Override
    public String getCommand() {
        String r= gtable.getCommand().getText(); //To change body of generated methods, choose Tools | Templates.
        return r;
    }

    @Override
    public Move chooseMoveList(int n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void possibleMove(String c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void renderMoveChoose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteMoveChoose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    

    @Override
    public void handle(ActionEvent event) {
        
        gtable.playComputerPlayer();
        
        gtable.getCommand().setText("");
        if (gtable.winCondition() == Piece.WHITE) table.getChildren().add(new Text("IL BIANCO HA VINTOOOOOOO"));
        else if (gtable.winCondition() == Piece.BLACK) table.getChildren().add(new Text("IL NERO HA VINTOOOOOOO"));

        event.consume();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
}
