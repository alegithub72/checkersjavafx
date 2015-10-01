/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcdama;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author ale2s_000
 */
public class ConfirmCommandEvent implements EventHandler<MouseEvent>{
    int n;
    BCDamaGraphic bcdg;

    ConfirmCommandEvent(BCDamaGraphic bcdg,int n){
        this.n=n;
        this.bcdg=bcdg;
    }
    @Override
    public void handle(MouseEvent event) {
        bcdg.confirmCommand=""+n;
        bcdg.player2.getConfirmCommand();
        System.out.println("Hai scelto "+n);
        bcdg.playPlayer2();
        event.consume();
    }
    
}
