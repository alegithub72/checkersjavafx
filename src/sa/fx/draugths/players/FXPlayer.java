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
import sa.boardgame.core.moves.*;
import sa.boardgame.core.players.HumanPlayer;
import sa.fx.draugths.FXBoardClass;
import sa.gameboard.core.Checker;

/**
 *
 * @author ale2s_000
 */
public class FXPlayer extends HumanPlayer  implements EventHandler<ActionEvent>  {

    FXBoardClass board;
    Group table;
    public FXPlayer(Group table,FXBoardClass board) {
        super(Checker.WHITE);
        this.board=board;
        this.table=table;
    }

    @Override
    public String getCommand() {
        String r= board.getCommand().getText(); //To change body of generated methods, choose Tools | Templates.
        return r;
    }

    @Override
    public Move chooseMoveList(int n,int n2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void possibleMove(String c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void drawMoveChoose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteMoveChoose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    

    @Override
    public void handle(ActionEvent event) {
        
        board.playComputerPlayer();
        
        board.getCommand().setText("");
        if (board.winCondition() == Checker.WHITE) table.getChildren().add(new Text("IL BIANCO HA VINTOOOOOOO"));
        else if (board.winCondition() == Checker.BLACK) table.getChildren().add(new Text("IL NERO HA VINTOOOOOOO"));

        event.consume();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
}
