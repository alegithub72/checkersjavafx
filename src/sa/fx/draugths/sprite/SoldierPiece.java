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
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.FrameAnimationTimer;
import sa.fx.draugths.animation.PedinaAnimationEndHandler;
import sa.fx.draugths.animation.TRANSITION_STEP;
import sa.fx.draugths.event.CollisionSpriteEvent;
import sa.fx.draugths.utility.BoardHW;
import sa.gameboard.core.Checker;
import sa.gameboard.core.Piece;


/**
 *
 * @author ale2s_000
 */
public class SoldierPiece extends SpritePiece {

    int color;
    static final String CHEKCER_IMAGE="soldier_checker.png";
    static final String DRAUGTH_IMAGE="soldier_checker_dama.png";

    
    public SoldierPiece( Piece boardPiece,
            BoardHW boardHW,  FXBoard board) {
        super( "Soldier",boardHW, CHEKCER_IMAGE,board);
        this.color = boardPiece.getColor();
        this.boardPieceLink = boardPiece;

    }
    
    private SoldierPiece(String img,Piece boardPiece,
            BoardHW boardHW,  FXBoard board) {
        super( "Soldier",boardHW, img,board);
        this.color = boardPiece.getColor();
        this.boardPieceLink = boardPiece;

    }




    public void buildDestroyAnimation(int by) {
        if (by ==Piece.CHECKER) {
           
            if(!draugthTransform)  frameAnimTimer[0] =new FrameAnimationTimer(EATED_ANIM_FRAME[0], EATED_ANIM_FRAME[1], this,  0.35d, false, 100, FrameAnimationTimer.BITE);
            else BCDraugthsApp.log.info("Errorre.........");
        
        } else if (by ==Piece.DRAUGTH)  {
        
          if(draugthTransform) frameAnimTimer[0] =new FrameAnimationTimer(EATED_ANIM_FRAME[0], EATED_ANIM_FRAME[1], this,  0.4d, false, 100, FrameAnimationTimer.BITE) ;
          else frameAnimTimer[0] =new FrameAnimationTimer(EATED_ANIM_FRAME[0], EATED_ANIM_FRAME[1], this, 0.35d, false, 100, FrameAnimationTimer.BITE) ; 
              
        }
    }

    public void buildFrameMoveAnimation(double frac, boolean ciclyc) {

        if (!draugthTransform) {
            frameAnimTimer[0] = new FrameAnimationTimer(MOVE_FRAME[0], MOVE_FRAME[1], this, frac, ciclyc, 100, FrameAnimationTimer.MOVEWHITE);
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
        rotateTransition.setDuration(Duration.seconds(1));
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(+1080);
        rotateTransition.setCycleCount(1);
        rotateTransition.setAutoReverse(false);
                //.build();
        ((ParallelTransition) ptList[TRANSITION_STEP.FULL_STEP]).getChildren().add(rotateTransition);
    }

    public void buildFrameEatMoveAnimation(double frac, boolean ciclyc) {
        frameAnimTimer[0] = new FrameAnimationTimer(EAT_MOVE_FRAME[0], EAT_MOVE_FRAME[1], this, frac, ciclyc, 100, FrameAnimationTimer.FIRE);

    }



    public void buildPedinaMovePath(Move m) {
        ParallelTransition pt = new ParallelTransition(this);
        QuadCurveTo arc = new QuadCurveTo();
        
        //javafx.scene.shape.
        double x0 =convertBoardJtoPositionXCenter(m.getP().getJ(),wSquare); 
       
               // (m.getP().getI() * wboardBox) + ((wboardBox / 2));
        double y0 = convertBoardItoPositionYCenter(m.getP().getI() ,hSquare); 
                
                //(m.getP().getJ() * hBoardBox) + ((hBoardBox / 2));

        Color color = Color.ANTIQUEWHITE;
        double x1 = convertBoardJtoPositionXCenter(m.getJ1(),wSquare); 
                
                //(m.getI1() * wboardBox) + ((wboardBox / 2));
        double y1 = convertBoardItoPositionYCenter(m.getI1() ,hSquare);
                
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
        pathTransition.setDuration(Duration.seconds(1));
        pathTransition.setPath(path);
        pathTransition.setNode(this);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(true);
                //
        
        ptList[TRANSITION_STEP.FULL_STEP] = pt;
        BCDraugthsApp.log.info(this+"  from ("+x0+","+ y0+") to ("+x1+","+y1+")");
        pt.getChildren().add(pathTransition);

    }

    public void buildDamaMovePath(Move m) {
        ParallelTransition pt = new ParallelTransition(this);
        QuadCurveTo arc = new QuadCurveTo();
        QuadCurveTo arc2 = new QuadCurveTo();
        //javafx.scene.shape.
        double x0 = convertBoardJtoPositionXCenter(m.getP().getJ(), wSquare);
                //(m.getP().getI() * wSquare) + ((wSquare / 2));
        double y0 = convertBoardItoPositionYCenter(m.getP().getI(), hSquare);
                //(m.getP().getJ() * hSquare) + ((hSquare / 2));

        Color color = Color.CHARTREUSE;
        double x1 =convertBoardJtoPositionXCenter(m.getJ1(), wSquare);
                //(m.getI1() * wSquare) + ((wSquare / 2));
        double y1 = convertBoardItoPositionYCenter(m.getI1(), hSquare);
                //(m.getJ1() * hSquare) + ((hSquare / 2));
        BCDraugthsApp.log.info(" move from (x0,y0)=("+x0+","+y0+")");
        arc.setX(x1);
        arc.setY(y1);
        if(y0<y1) {
            arc.setControlX(x0);
            arc.setControlY(y0-100);        	
        }
        else {
            arc.setControlX(x0);
            arc.setControlY(y0-200);       
        }

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
        if(BCDraugthsApp.debug)  this.fbx.add(path);

    }

    public void buildPedinaMoveEatPath(Move m) {
        ParallelTransition pt = new ParallelTransition(this);
        QuadCurveTo arc = new QuadCurveTo();
        //javafx.scene.shape.
        double x0 = convertBoardJtoPositionXCenter(m.getP().getJ(), wSquare);
                //(m.getP().getI() * wSquare) + ((wSquare / 2));
        double y0 =convertBoardItoPositionYCenter(m.getP().getI(), hSquare);
                //(m.getP().getJ() * hSquare) + ((hSquare / 2));

        Color color = Color.CHARTREUSE;
        double x1 = convertBoardJtoPositionXCenter(m.getJ1(), wSquare);
        //(m.getI1() * wSquare) + ((wSquare / 2));
        double y1 = convertBoardItoPositionYCenter(m.getI1(), hSquare);
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
        pathTransition.setDuration(Duration.seconds(1));
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
        double x0 = convertBoardJtoPositionXCenter(m.getP().getJ(), wSquare);
                //(m.getP().getI() * wSquare) + ((wSquare / 2));
        double y0 = convertBoardItoPositionYCenter(m.getP().getI(), hSquare);
                //(m.getP().getJ() * hSquare) + ((hSquare / 2));

        Color color = Color.CHARTREUSE;
        double x1 =convertBoardJtoPositionXCenter(m.getJ1(), wSquare);
                //(m.getI1() * wSquare) + ((wSquare / 2));
        double y1 =convertBoardItoPositionYCenter(m.getI1(), hSquare);
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
        if(BCDraugthsApp.debug)  this.fbx.add(path);

    }

    @Override
    public void animDamaEat(Move m) {
        ParallelTransition ptMissile = new ParallelTransition();
        extraSprite[0]= new Sprite("missile2.png");
      
        this.fbx.add(extraSprite[0]);
        //x missile.toFront();
        double x =convertBoardJtoPositionXCenter( m.getP().getJ(),wSquare);
        double y = convertBoardItoPositionYCenter(m.getP().getI(), hSquare);
                //m.getP().getJ();
        double xe=convertBoardJtoPositionXCenter(eated.boardPieceLink.getJ(), wSquare);
        double ye=convertBoardItoPositionYCenter(eated.boardPieceLink.getI(), hSquare);
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

    public SpritePiece loadDraugthFrame() {
    	SpritePiece sp=null;
        if (boardPieceLink.getType() == Checker.DRAUGTH &&
                draugthTransform==false) {
            draugthTransform=true;
            sp=new SoldierPiece(DRAUGTH_IMAGE, this.boardPieceLink, FXBoard.boardHW, this.fbx);       
            AudioClip ach = buildMedia(FrameAnimationTimer.ACHB);
            ach.setCycleCount(1);
            ach.play();
            sp.setDraugthTransform(true);
            //buildFrameImages();

//            setScaleX(0.64);
//            setScaleY(0.64);
          // setW(96);setH(96);

        }
        setFrame(0);
        //recalculateXYPosition();
        return sp;

    }


}
