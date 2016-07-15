/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.event;


import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sa.boardgame.core.players.AIPlayer;
import sa.fx.draugths.BCDraugthsApp;
import sa.gameboard.core.Piece;

/**
 *
 * @author ale2s_000
 */
public class FXAIPlayer1 extends AIPlayer implements EventHandler<MouseEvent> {

    BCDraugthsApp gtable;
    Group table;

    public FXAIPlayer1(Group table, BCDraugthsApp gtable,int level) {
        super(Piece.BLACK);
        this.gtable = gtable;
        this.table = table;
    }

    @Override
    public void handle(MouseEvent event) {
        gtable.getCommand().setText("");
        Image winText;
        ImageView imageView;
        if (gtable.winCondition() == Piece.WHITE) {

            winText = new Image("winblack.png");
            imageView = new ImageView();
            //background.setFocusTraversable(true);
            imageView.setImage(winText);
            table.getChildren().add(imageView);
            imageView.setX(250);
            imageView.setY(350);
            imageView.setScaleX(2);
            imageView.setScaleY(2);
            event.consume();
            return;
        } else if (gtable.winCondition() == Piece.BLACK) {
            winText = new Image("winwhite.png");
            imageView = new ImageView();
            //background.setFocusTraversable(true);
            imageView.setImage(winText);
            imageView.setX(250);
            imageView.setY(350);
            imageView.setScaleX(2);
            imageView.setScaleY(2);
            table.getChildren().add(imageView);
            event.consume();
            return;
        } else {
            if (gtable.isOn()) {
                gtable.playComputerPlayer();
            }

        }
        event.consume();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
