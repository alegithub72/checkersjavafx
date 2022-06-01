/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.sprite;

import javafx.scene.image.Image;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.utility.BoardHW;
import sa.fx.draugths.utility.SoundInterface;
import sa.gameboard.core.Piece;

/**
 *
 * @author Alessio Sardaro
 */
public class MoonSoldier extends SoldierPiece {
    
    final static  String DRAUGTH_IMAGE="white_dama_moonsoldier.png";
    static final  String CHEKCER_IMAGE="white_cheker_moonsoldier.png";
    @Override
    public void buildMoveSequence( boolean ciclyc) {

        if (!draugthTransform) {
//        	frameAnimTimer.add(  new FrameAnimationTimer(5, 6, 0,this, ciclyc, 100, FrameAnimationTimer.MOVESPACESOLDIER));
        } else {
//        	frameAnimTimer.add( new FrameAnimationTimer(1, 2,0, this, ciclyc, 100, FrameAnimationTimer.ELICOPTER));
            //t = new MoveAnimePedinaTimer(5, 6, this, frac, ciclyc, 100,MoveAnimePedinaTimer.DAMAMOVE_W);
        }

    }    


    public MoonSoldier( Piece boardPiece,  BoardHW boardHW, FXBoard bb) {
        super( boardPiece,  boardHW, bb);
    }
        

    
    @Override
    public SpritePiece loadDraugthFrame() {
            if (piece.getType() == Piece.DRAUGTH
                && draugthTransform == false) {
            draugthTransform = true;
            frameImages = new Image(DRAUGTH_IMAGE);
            setImage(frameImages);
            buildFrameImages();

            FXBoard.SoundSystem.playSound(SoundInterface.ACHB,1);


        }
        setFrame(0); //To change body of generated methods, choose Tools | Templates.
        return null;
    }

}
