/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcdama;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import dama.core.Move;
import dama.core.PedinaChar;

/**
 *
 * @author ale2s_000
 */
public class GameAnimationHandler implements EventHandler<ActionEvent> {

    PedinaGeneral p;
    PedinaGeneral e;
    BCDamaGraphic bCDamaGraphic;
    double widthSprite, heightSprite;
    Move m;

    public GameAnimationHandler(PedinaGeneral p, Move m, PedinaGeneral e, BCDamaGraphic bCDamaGraphic, double widthSprite, double heightSprite) {
        this.p = p;
        this.bCDamaGraphic = bCDamaGraphic;
        this.widthSprite = widthSprite;
        this.heightSprite = heightSprite;
        this.m = m;
        this.e = e;
    }

    @Override
    public void handle(ActionEvent event) {
        //System.out.println("FINTO ANIME PEDINA");
        //bCDamaGraphic.pathTransition.stop();
        bCDamaGraphic.pt.stop();
        if(m.p.getColor()==PedinaChar.WHITE) bCDamaGraphic.updatePoint(m.value);
        if (m.type == Move.DAMAMOVE) {
            p.setFrameDama();
            p.getPedinaCharAssociated().setType(PedinaChar.DAMA);

        } else if (m.type == Move.DAMAEEAT) {
            p.setFrameDama();
            p.getPedinaCharAssociated().setType(PedinaChar.DAMA);
        }

        if (e != null) {
            bCDamaGraphic.removePedina(e, m.getEated().getColor());
        }
        p.stop();
        if (e != null) {
            e.stop();
        }
        //System.out.println("-------cooord fine anim--->"+p.getX()+","+p.getY());
        System.out.println("-------cooord fine prima anim--->" + m.x * widthSprite + "," + m.y * heightSprite);
        //p.setX(m.x*widthSprite);
        // p.setY(m.y*heightSprite);
        //p.pedina.setLayoutX(0);
        //p.pedina.setLayoutY(0);
        //System.out.println("-------cooord layout anim--->"+p.getX()+","+p.getY());
        //p.pedina.setTranslateX(m.x*widthSprite);
        //p.pedina.setTranslateY(m.y*heightSprite);
        // p.setX(m.x*widthSprite);
        //p.setY(m.y*heightSprite);
        bCDamaGraphic.on = false;
        bCDamaGraphic.turn = !bCDamaGraphic.turn;
        bCDamaGraphic.removePuntatori();
        if(bCDamaGraphic.winCondition()!=PedinaChar.WHITE && bCDamaGraphic.winCondition()!=PedinaChar.BLACK)
        if(bCDamaGraphic.turn) bCDamaGraphic.playPlayer1();
        event.consume();
    }

}
