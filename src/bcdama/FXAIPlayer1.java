/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcdama;

import dama.core.AIDama;
import dama.core.AIDamaExpert;
import dama.core.AIDamaExpertPlus;
import dama.core.DamaInterface;
import dama.core.Game;
import dama.core.HumanKeyboard;
import dama.core.Move;
import dama.core.PedinaChar;
import dama.core.Player;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;

/**
 *
 * @author ale2s_000
 */
public class FXAIPlayer1 extends AIDamaExpertPlus implements EventHandler<MouseEvent> {

    BCDamaGraphic gtable;
    Group table;

    public FXAIPlayer1(Group table, BCDamaGraphic gtable,int level) {
        super(PedinaChar.BLACK,level);
        this.gtable = gtable;
        this.table = table;
    }

    @Override
    public void handle(MouseEvent event) {
        gtable.command.setText("");
        Image winText;
        ImageView imageView;
        if (gtable.winCondition() == PedinaChar.WHITE) {

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
        } else if (gtable.winCondition() == PedinaChar.BLACK) {
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
            if (gtable.on) {
                if (!gtable.turn) {
                    gtable.playPlayer1();
                } else {
                    gtable.playPlayer2();
                }
            }

        }
        event.consume();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
