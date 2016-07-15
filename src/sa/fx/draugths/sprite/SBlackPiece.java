/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.sprite;



import javafx.scene.image.Image;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathBuilder;
import javafx.scene.shape.QuadCurveTo;

import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.PathTransitionBuilder;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.animation.FrameAnimationTimer;
import sa.fx.draugths.animation.TRANSITION_STEP;
import sa.gameboard.core.Piece;


/**
 *
 * @author ale2s_000
 */
public class SBlackPiece extends SpritePiece {





    int color;
    BCDraugthsApp bcdg;


     SBlackPiece(int color, BCDraugthsApp bcdg, Piece boardPiece, int w, int h,double wbBox,double hbBox, String img) {
        super(w, h, wbBox,hbBox,bcdg,img);
        this.color = color;
        this.bcdg = bcdg;
        this.boardPieceLink = boardPiece;
        
    }    

    
    



    private void buildGenericFrameAnimation(int f1, int f2, double frac, boolean ciclyc, long interval,String sound) {
        frameAnimTimer[0] = new FrameAnimationTimer(f1, f2, this, frac, ciclyc, interval,sound);
    }
   
    public void buildDestroyDamaAnimation(){
        if(this.boardPieceLink.getType()==Piece.PIECE) buildGenericFrameAnimation(12,19,0,false,100,FrameAnimationTimer.BIGEXPLOSION);
        else buildGenericFrameAnimation(5,10,0,false,50,FrameAnimationTimer.BIGEXPLOSION);
    }
    
    public void buildDestroyPedinaAnimation() {
        if(this.boardPieceLink.getType()==Piece.PIECE) buildGenericFrameAnimation(9, 19, 0.2d, false,100,FrameAnimationTimer.EXPLOSION);
        else buildGenericFrameAnimation(5, 10, 0.2d, false,50,FrameAnimationTimer.EXPLOSION);

    }

    public void buildFrameMoveAnimation( double frac, boolean ciclyc) {
            if (boardPieceLink.getType() == Piece.PIECE) {
                frameAnimTimer[0] = new FrameAnimationTimer(5, 6, this, frac, ciclyc, 100,FrameAnimationTimer.MOVEBLACK);
            } else {
                frameAnimTimer[0] = new FrameAnimationTimer(2, 4, this, frac, ciclyc, 2,FrameAnimationTimer.DAMAMOVE_B);
            }
        
    }

    public void buildFrameEatMoveAnimation( double frac, boolean ciclyc) {
            if (boardPieceLink.getType() == Piece.PIECE) {
                frameAnimTimer[0] = new FrameAnimationTimer(5, 6, this, frac, ciclyc, 100,FrameAnimationTimer.MOVEBLACK);
            } else {
                frameAnimTimer[0] = new FrameAnimationTimer(2, 4, this, frac, ciclyc, 2,FrameAnimationTimer.DAMAMOVE_B);
            }
        
    }





    public void setFrameDama() {
            if (boardPieceLink.getType() == Piece.PIECE) {
                frameImages = new Image("black_dama.png");
                pedina.setImage(frameImages);
                buildFrameImages();
                AudioClip ach=buildMedia(FrameAnimationTimer.ACHB);
                ach.setCycleCount(1);
                ach.play();                
            }
            setFrame(0);
        
    }



    public void buildPedinaMovePath(Move m) {
        ParallelTransition pt=new ParallelTransition(this);
        QuadCurveTo quadTo = new QuadCurveTo();
        QuadCurveTo quadTo2 = new QuadCurveTo();
        double x0 = m.getP().getI() * wboardBox+((wboardBox/2));
        double y0 = m.getP().getJ() * hBoardBox+((hBoardBox/2));
        double x1=(m.getI1() * wboardBox)+((wboardBox/2));
        double y1=(m.getJ1() * hBoardBox)+((hBoardBox/2));
        double xe=0;
        double ye=0;
        Path path=null;
        Color color = Color.CHARTREUSE;

                quadTo.setControlX(x1);
                quadTo.setControlY(y1);
                        quadTo.setX(x1);
                quadTo.setY(y1);
                path = PathBuilder.create()
                    .elements(
                            new MoveTo(x0 , y0 ),
                            quadTo
                    )
                    .build();
        

        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);
        PathTransition  pathTransition = PathTransitionBuilder.create()
                .duration(Duration.seconds(2))
                .path(path)
                .node(this)
                .orientation(PathTransition.OrientationType.NONE)
                .cycleCount(1)
                .autoReverse(true)
                .build(); 
        ptList[TRANSITION_STEP.FULL_STEP]=pt;
        pt.getChildren().add(pathTransition);
        
    }

   public void buildPedinaMoveEatPath(Move m) {
        ParallelTransition  pt=new ParallelTransition(this);
        QuadCurveTo quadTo = new QuadCurveTo();
        QuadCurveTo quadTo2 = new QuadCurveTo();
        double x0 = m.getP().getI() * wboardBox+((wboardBox/2));
        double y0 = m.getP().getJ() * hBoardBox+((hBoardBox/2));
        double x1=(m.getI1() * wboardBox)+((wboardBox/2));
        double y1=(m.getJ1() * hBoardBox)+((hBoardBox/2));
        double xe=0;
        double ye=0;
        Path path=null;
        Color color = Color.CHARTREUSE;
        quadTo.setControlX(x1);
        quadTo.setControlY(y1-(h*2));
        quadTo2.setControlX(x1);
        quadTo2.setControlY(y1-(h*2));
        xe=(m.getEat().getI() * wboardBox)+(wboardBox/2);
        ye=(m.getEat().getJ() * hBoardBox)+(hBoardBox/2);
        quadTo.setX(xe);
        quadTo.setY(ye);
        quadTo2.setX(x1);
        quadTo2.setY(y1);
        path = PathBuilder.create()
                .elements(
                        new MoveTo(x0 , y0 ),
                        quadTo
                )
                .build();         
        path.getElements().add(new MoveTo(xe , ye ));
        path.getElements().add(quadTo2);            
            


        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);
        PathTransition  pathTransition = PathTransitionBuilder.create()
                .duration(Duration.seconds(2))
                .path(path)
                .node(this)
                .orientation(PathTransition.OrientationType.NONE)
                .cycleCount(1)
                .autoReverse(true)
                .build(); 
       ptList[TRANSITION_STEP.FULL_STEP]=pt;
        pt.getChildren().add(pathTransition);
        
    }

    @Override
    public void buildDamaMoveEatPath(Move m) {
        ParallelTransition  pt=new ParallelTransition(this);
        QuadCurveTo quadTo = new QuadCurveTo();
        QuadCurveTo quadTo2 = new QuadCurveTo();
        double x0 = m.getP().getI() * wboardBox+((wboardBox/2));
        double y0 = m.getP().getJ() * hBoardBox+((hBoardBox/2));
        double x1=(m.getI1() * wboardBox)+((wboardBox/2));
        double y1=(m.getJ1() * hBoardBox)+((hBoardBox/2));
        double xe=0;
        double ye=0;
        Path path=null;
        Color color = Color.CHARTREUSE;
        quadTo.setControlX(x1);
        quadTo.setControlY(y1-(h*2));
        quadTo2.setControlX(x1);
        quadTo2.setControlY(y1-(h*2));
        xe=(m.getEat().getI() * wboardBox)+(wboardBox/2);
        ye=(m.getEat().getJ() * hBoardBox)+(hBoardBox/2);
        quadTo.setX(xe);
        quadTo.setY(ye);
        quadTo2.setX(x1);
        quadTo2.setY(y1);
        path = PathBuilder.create()
                .elements(
                        new MoveTo(x0 , y0 ),
                        quadTo
                )
                .build();         
        path.getElements().add(new MoveTo(xe , ye ));
        path.getElements().add(quadTo2);            
            


        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);
        PathTransition  pathTransition = PathTransitionBuilder.create()
                .duration(Duration.seconds(2))
                .path(path)
                .node(this)
                .orientation(PathTransition.OrientationType.NONE)
                .cycleCount(1)
                .autoReverse(true)
                .build(); 
        ptList[TRANSITION_STEP.FULL_STEP]=pt;
        pt.getChildren().add(pathTransition);        
    }

    @Override
    public void buildDamaMovePath(Move m) {
        ParallelTransition  pt=new ParallelTransition(this);
        QuadCurveTo quadTo = new QuadCurveTo();
        QuadCurveTo quadTo2 = new QuadCurveTo();
        double x0 = m.getP().getI() * wboardBox+((wboardBox/2));
        double y0 = m.getP().getJ() * hBoardBox+((hBoardBox/2));
        double x1=(m.getI1() * wboardBox)+((wboardBox/2));
        double y1=(m.getJ1() * hBoardBox)+((hBoardBox/2));
        double xe=0;
        double ye=0;
        Path path=null;
        Color color = Color.CHARTREUSE;

                quadTo.setControlX(x1);
                quadTo.setControlY(y1);
                        quadTo.setX(x1);
                quadTo.setY(y1);
                path = PathBuilder.create()
                    .elements(
                            new MoveTo(x0 , y0 ),
                            quadTo
                    )
                    .build();
        

        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);
        PathTransition  pathTransition = PathTransitionBuilder.create()
                .duration(Duration.seconds(2))
                .path(path)
                .node(this)
                .orientation(PathTransition.OrientationType.NONE)
                .cycleCount(1)
                .autoReverse(true)
                .build(); 
        ptList[TRANSITION_STEP.FULL_STEP]=pt;
        pt.getChildren().add(pathTransition);
        
    

    }
    
 
 

}
