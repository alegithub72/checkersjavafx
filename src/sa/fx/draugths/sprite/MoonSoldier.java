/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.sprite;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.FrameAnimationTimer;
import sa.fx.draugths.utility.BoardHW;
import sa.gameboard.core.Checker;
import sa.gameboard.core.Piece;

/**
 *
 * @author appleale
 */
public class MoonSoldier extends SoldierPiece {
    
    final static  String DRAUGTH_IMAGE="white_dama_moonsoldier.png";
    static final  String CHEKCER_IMAGE="white_cheker_moonsoldier.png";
    @Override
    public void buildFrameMoveAnimation(double frac, boolean ciclyc) {

        if (!draugthTransform) {
            frameAnimTimer[0] = new FrameAnimationTimer(5, 6, this, frac, ciclyc, 100, FrameAnimationTimer.MOVESPACESOLDIER);
        } else {
            frameAnimTimer[0] = new FrameAnimationTimer(1, 2, this, frac, ciclyc, 100, FrameAnimationTimer.DAMAMOVE_W);
            //t = new MoveAnimePedinaTimer(5, 6, this, frac, ciclyc, 100,MoveAnimePedinaTimer.DAMAMOVE_W);
        }

    }    


    public MoonSoldier( Piece boardPiece,  BoardHW boardHW, FXBoard bb) {
        super( boardPiece,  boardHW, bb);
    }
        

    
    @Override
    public SpritePiece loadDraugthFrame() {
            if (boardPieceLink.getType() == Checker.DRAUGTH
                && draugthTransform == false) {
            draugthTransform = true;
            frameImages = new Image("white_dama_moonsoldier.png");
            setImage(frameImages);
            buildFrameImages();
            AudioClip ach = buildMedia(FrameAnimationTimer.ACHB);
            ach.setCycleCount(1);
            ach.play();
        }
        setFrame(0); //To change body of generated methods, choose Tools | Templates.
        return null;
    }

}
