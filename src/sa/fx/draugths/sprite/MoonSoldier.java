/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.sprite;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoardClass;
import sa.fx.draugths.animation.FrameAnimationTimer;
import sa.gameboard.core.Checker;
import sa.gameboard.core.Piece;

/**
 *
 * @author appleale
 */
public class MoonSoldier extends HumanPiece {
    
    
    @Override
    public void buildFrameMoveAnimation(double frac, boolean ciclyc) {

        if (!draugthTransform) {
            frameAnimTimer[0] = new FrameAnimationTimer(5, 6, this, frac, ciclyc, 100, FrameAnimationTimer.MOVEWHITE);
        } else {
            frameAnimTimer[0] = new FrameAnimationTimer(1, 2, this, frac, ciclyc, 100, FrameAnimationTimer.DAMAMOVE_W);
            //t = new MoveAnimePedinaTimer(5, 6, this, frac, ciclyc, 100,MoveAnimePedinaTimer.DAMAMOVE_W);
        }

    }    


    public MoonSoldier(int color, Piece boardPiece,  int wbBox, int hbBox, String img,FXBoardClass bb) {
        super(color, boardPiece,  wbBox, hbBox, img,bb);
    }
        
    @Override
    public void buildDestroyAnimation(int by) {
        String effect=FrameAnimationTimer.EXPLOSIONMONSTER;
        setEatedAnimation(10, 13,0, false, 100, effect);
    }
    
    @Override
    public void setFrameDama() {
            if (boardPieceLink.getType() == Checker.DRAUGTH
                && draugthTransform == false) {
            draugthTransform = true;
            frameImages = new Image("white_dama_moonsoldier.png");
            imgView.setImage(frameImages);
            buildFrameImages();
            AudioClip ach = buildMedia(FrameAnimationTimer.ACHB);
            ach.setCycleCount(1);
            ach.play();
        }
        setFrame(0); //To change body of generated methods, choose Tools | Templates.
    }

}
