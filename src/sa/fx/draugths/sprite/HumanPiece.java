/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.sprite;


import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;
import sa.boardgame.core.moves.*;
import sa.gameboard.core.Checker;
import sa.gameboard.core.Piece;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.FrameAnimationTimer;
import sa.fx.draugths.animation.PedinaAnimationEndHandler;
import sa.fx.draugths.animation.TRANSITION_STEP;
import sa.fx.draugths.event.CollisionSpriteEvent;
import sa.gameboard.core.Piece;

/**
 *
 * @author ale2s_000
 */
public class HumanPiece extends SpritePiece {

    int color;

   
    HumanPiece(int color, Piece boardPiece,
            int wbBox, int hbBox, String img, FXBoard board) {
        super( wbBox, hbBox, img,board);
        this.color = color;
       
        this.boardPieceLink = boardPiece;

    }
    

     void setEatedAnimation(int f1, int f2, double frac, boolean ciclyc, long interval, String sound) {
        frameAnimTimer[0] = new FrameAnimationTimer(f1, f2, this, frac, ciclyc, interval, sound);
    }



    public void buildDestroyAnimation(int by) {
        if (by ==Piece.CHECKER) {
           
            if(!draugthTransform)  setEatedAnimation(10, 12, 0.35d, false, 100, FrameAnimationTimer.BITE);
            else System.out.println("Errorre.........");
        
        } else if (by ==Piece.DRAUGTH)  {
        
          if(draugthTransform) setEatedAnimation(7, 11, 0.4d, false, 100, FrameAnimationTimer.BITE);
          else setEatedAnimation(10, 12, 0.35d, false, 100, FrameAnimationTimer.BITE);
              
        }
    }

    public void buildFrameMoveAnimation(double frac, boolean ciclyc) {

        if (!draugthTransform) {
            frameAnimTimer[0] = new FrameAnimationTimer(5, 6, this, frac, ciclyc, 100, FrameAnimationTimer.MOVEWHITE);
        } else {
            frameAnimTimer[0] = new FrameAnimationTimer(1, 2, this, frac, ciclyc, 0, FrameAnimationTimer.DAMAMOVE_W);
            //t = new MoveAnimePedinaTimer(5, 6, this, frac, ciclyc, 100,MoveAnimePedinaTimer.DAMAMOVE_W);
        }

    }

    @Override
    public void animPedinaEat(Move m) {
        super.animPedinaEat(m);
        RotateTransition rotateTransition = new RotateTransition();
                // .node(p)
        rotateTransition.setDuration(getAnimDuration());
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(-720);
        rotateTransition.setCycleCount(1);
        rotateTransition.setAutoReverse(false);
                //.build();
        ((ParallelTransition) ptList[TRANSITION_STEP.FULL_STEP]).getChildren().add(rotateTransition);
    }

    public void buildFrameEatMoveAnimation(double frac, boolean ciclyc) {
        frameAnimTimer[0] = new FrameAnimationTimer(2, 3, this, frac, ciclyc, 100, FrameAnimationTimer.FIRE);

    }

    public void setFrameDama() {
        if (boardPieceLink.getType() == Checker.DRAUGTH &&
                draugthTransform==false) {
            draugthTransform=true;
            frameImages = new Image("white_dama.png");
            imgView.setImage(frameImages);
            AudioClip ach = buildMedia(FrameAnimationTimer.ACHB);
            ach.setCycleCount(1);
            ach.play();
            buildFrameImages();
        }
        setFrame(0);

    }

    public void buildPedinaMovePath(Move m) {
        ParallelTransition pt = new ParallelTransition(this);
        QuadCurveTo arc = new QuadCurveTo();
        
        //javafx.scene.shape.
        double x0 =Sprite.convertBoardJpositionCenter(m.getP().getJ(),wSquare); 
       
               // (m.getP().getI() * wboardBox) + ((wboardBox / 2));
        double y0 = Sprite.convertBoardIpositionCenter(m.getP().getI() ,hSquare); 
                
                //(m.getP().getJ() * hBoardBox) + ((hBoardBox / 2));

        Color color = Color.ANTIQUEWHITE;
        double x1 = Sprite.convertBoardJpositionCenter(m.getJ1(),wSquare); 
                
                //(m.getI1() * wboardBox) + ((wboardBox / 2));
        double y1 = Sprite.convertBoardIpositionCenter(m.getI1() ,hSquare);
                
                //(m.getJ1() * hBoardBox) + ((hBoardBox / 2));
        arc.setX(x1);
        arc.setY(y1);
       // arc.setRadiusX(-10);
       // arc.setRadiusY(-10);
        arc.setControlX(x1);
        arc.setControlY(y1);

        MoveTo to = new MoveTo();
        //to.setAbsolute(true);
        to.setX(x0);
        to.setY(y0);
        System.out.println("x0="+x0+",y0="+y0+",x1="+x1+",y1="+y1);
        Path path = new Path();
        path.getElements().addAll(
           
                        to,
                        arc
              )
                ;
        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(2));
        pathTransition.setPath(path);
        pathTransition.setNode(this);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(true);
                //
        
        ptList[TRANSITION_STEP.FULL_STEP] = pt;
        
        pt.getChildren().add(pathTransition);

    }

    public void buildDamaMovePath(Move m) {
        ParallelTransition pt = new ParallelTransition(this);
        QuadCurveTo arc = new QuadCurveTo();
        //javafx.scene.shape.
        double x0 = Sprite.convertBoardJpositionCenter(m.getP().getJ(), wSquare);
                //(m.getP().getI() * wSquare) + ((wSquare / 2));
        double y0 = Sprite.convertBoardIpositionCenter(m.getP().getI(), hSquare);
                //(m.getP().getJ() * hSquare) + ((hSquare / 2));

        Color color = Color.CHARTREUSE;
        double x1 =Sprite.convertBoardJpositionCenter(m.getJ1(), wSquare);
                //(m.getI1() * wSquare) + ((wSquare / 2));
        double y1 = Sprite.convertBoardIpositionCenter(m.getI1(), hSquare);
                //(m.getJ1() * hSquare) + ((hSquare / 2));
        arc.setX(x1);
        arc.setY(y1);
        arc.setControlX(x1);
        arc.setControlY(y1);

        MoveTo from = new MoveTo(x0, y0);
        Path path = new Path();
        path.getElements().add(from);
        path.getElements().add(arc);
                //
        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(2));
        pathTransition.setPath(path);
        pathTransition.setNode(this);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(true);
                //.build();
        ptList[TRANSITION_STEP.FULL_STEP] = pt;
        pt.getChildren().add(pathTransition);

    }

    public void buildPedinaMoveEatPath(Move m) {
        ParallelTransition pt = new ParallelTransition(this);
        QuadCurveTo arc = new QuadCurveTo();
        //javafx.scene.shape.
        double x0 = Sprite.convertBoardJpositionCenter(m.getP().getJ(), wSquare);
                //(m.getP().getI() * wSquare) + ((wSquare / 2));
        double y0 =Sprite.convertBoardIpositionCenter(m.getP().getI(), hSquare);
                //(m.getP().getJ() * hSquare) + ((hSquare / 2));

        Color color = Color.CHARTREUSE;
        double x1 = Sprite.convertBoardJpositionCenter(m.getJ1(), wSquare);
        //(m.getI1() * wSquare) + ((wSquare / 2));
        double y1 = Sprite.convertBoardIpositionCenter(m.getI1(), hSquare);
                //(m.getJ1() * hSquare) + ((hSquare / 2));
        arc.setX(x1);
        arc.setY(y1);
        arc.setControlX(x0);
        arc.setControlY(y1 - (h * 2));

        MoveTo from = new MoveTo(x0, y0);
        Path path = new Path();
               path.getElements().add(from);
               path.getElements().add(arc);
              
        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(2));
        pathTransition.setPath(path);
        pathTransition.setNode(this);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(true);
                //.build();
        ptList[TRANSITION_STEP.FULL_STEP] = pt;
        pt.getChildren().add(pathTransition);

    }

    public void buildDamaMoveEatPath(Move m) {
        ParallelTransition pt = new ParallelTransition(this);
        QuadCurveTo arc = new QuadCurveTo();
        //javafx.scene.shape.
        double x0 = Sprite.convertBoardJpositionCenter(m.getP().getJ(), wSquare);
                //(m.getP().getI() * wSquare) + ((wSquare / 2));
        double y0 = Sprite.convertBoardIpositionCenter(m.getP().getI(), hSquare);
                //(m.getP().getJ() * hSquare) + ((hSquare / 2));

        Color color = Color.CHARTREUSE;
        double x1 =Sprite.convertBoardJpositionCenter(m.getJ1(), wSquare);
                //(m.getI1() * wSquare) + ((wSquare / 2));
        double y1 =Sprite.convertBoardIpositionCenter(m.getI1(), hSquare);
                //(m.getJ1() * hSquare) + ((hSquare / 2));
        arc.setX(x1);
        arc.setY(y1);
        /**
         * if (m.x > m.getP().getX()) arc.setXAxisRotation(3.14/3); else arc.setXAxisRotation(3.14);
         */

        arc.setControlX(x0);
        arc.setControlY(y1 - (hSquare * 2));

        MoveTo from = new MoveTo(x0, y0);
        Path path = new Path();
             path.getElements().add(from);
             path.getElements().add(arc);
             
        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);

        PathTransition pathTransition = new PathTransition();
          pathTransition.setDuration(Duration.seconds(2));
          pathTransition.setPath(path);
          pathTransition.setNode(this);
          pathTransition.setOrientation(PathTransition.OrientationType.NONE);
          pathTransition.setCycleCount(1);
          pathTransition.setAutoReverse(true);
                //
        ptList[TRANSITION_STEP.FULL_STEP] = pt;
        pt.getChildren().add(pathTransition);

    }

    @Override
    public void animDamaEat(Move m) {
        ParallelTransition ptMissile = new ParallelTransition();
        extraSprite[0]= new Sprite(64, 64, 64, 64, "missile2.png",this.fbx);
      
        this.fbx.add(extraSprite[0]);
        //x missile.toFront();
        double x =Sprite.convertBoardJpositionCenter( m.getP().getJ(),wSquare);
        double y = Sprite.convertBoardIpositionCenter(m.getP().getI(), hSquare);
                //m.getP().getJ();
        double xe=Sprite.convertBoardJpositionCenter(eated.boardPieceLink.getJ(), wSquare);
        double ye=Sprite.convertBoardIpositionCenter(eated.boardPieceLink.getI(), hSquare);
        extraSprite[0].setX(x);
        extraSprite[0].setY(y);
        QuadCurveTo qTO=new QuadCurveTo();
        MoveTo mt=new MoveTo();
        mt.setX(x);
        mt.setY(y);
        qTO.setX(xe);
        qTO.setY(ye);
        qTO.setControlX(x);
        qTO.setControlY(y);
        /**
         * missile.setX(x*wBoardSquare);
        missile.setY((y*hBoardSquare));
         */
        Path path = new Path();
        path.getElements().addAll(
                mt,
                qTO
                )
                ;
        PathTransition pathMissileTransition = new PathTransition();
        pathMissileTransition.setDuration(Duration.seconds(0.2));
        pathMissileTransition.setPath(path);
        pathMissileTransition.setNode(extraSprite[0]);
        pathMissileTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathMissileTransition.setCycleCount(1);
        pathMissileTransition.setAutoReverse(false);
                //.build();

        //table.getChildren().add(path);
        ptMissile.getChildren().add(pathMissileTransition);

        buildDamaMoveEatPath(m);

        ptList[TRANSITION_STEP.MISSILE_FULL_STEP] = ptMissile;
        frameAnimTimer[1] = new FrameAnimationTimer(3, 4, this,extraSprite[0],eated, 0, true, 10, FrameAnimationTimer.MISSILE);
        frameAnimTimer[1].start();
        

        
        eated.buildDestroyAnimation(Piece.DRAUGTH);
        buildFrameMoveAnimation(0, true);
        //setFrame(3);
        ptMissile.play();
        ptList[TRANSITION_STEP.FULL_STEP].setOnFinished(new PedinaAnimationEndHandler(this, m, eated, wSquare, hSquare,this.fbx));
        
    }

    @Override
    protected void playAnimDamaEat(Move m) {

       /* ptList[TRANSITION_STEP.MISSILE_FULL_STEP].setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                extraSprite[0].setVisible(false);
                frameAnimTimer[1].stop();
                start(m);
                eated.start(m);
                event.consume();

            }
        });*/
        this.getParent().addEventHandler(CollisionSpriteEvent.COLLISION_SPRITE, new EventHandler<CollisionSpriteEvent>(){
            @Override
            public void handle(CollisionSpriteEvent event) {
               extraSprite[0].setVisible(false);
               frameAnimTimer[1].stop();
                start(m);
                eated.start(m);
                event.consume();
            }
        }
        );       

    }

}
