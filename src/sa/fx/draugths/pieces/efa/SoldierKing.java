/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.pieces.efa;


import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.*;
import sa.fx.draugths.animation.event.EventRemoveEatPiece;
import sa.fx.draugths.animation.Sprite;
import sa.fx.draugths.pieces.SpritePiece;
import sa.fx.draugths.utility.BoardHW;
import sa.fx.draugths.utility.SequenceSoundEffect;
import sa.fx.draugths.utility.SoundEffect;
import sa.gameboard.core.Piece;


/**
 * @author Alessio Sardaro
 */
public class SoldierKing extends SpritePiece {


    protected static  String PIECE_IMAGE  = "soldier_checker_dama.png";
    private static final String DAMA_TAKEOFF = "DAMA_TAKEOFF";


    public SoldierKing(Piece piece,
                       BoardHW boardHW, FXBoard board) {
        super(PIECE_IMAGE,"Soldier", boardHW, piece,  board);


    }

    public SoldierKing(String pieceImage, String moonSoldierDraught, Piece piece, BoardHW boardHW, FXBoard board) {
        super(pieceImage,moonSoldierDraught,boardHW,piece,board);
    }

    public void buildSoldierKingFrames() {
        crownedSound();
        FrameInfo[] move = {  new FrameInfo(2, 1), new FrameInfo(3, 1), new FrameInfo(4, 1),
                new FrameInfo(5, 1) };
        addMoveSequenceFrame(new FrameSequence[]{new FrameSequence(move,new SequenceSoundEffect(SequenceSoundEffect.SPREAD,SoundEffect.ELICOPTER))});
        // MOVE EAT SEQUENCE 1-5
        FrameInfo[] moveat = {  new FrameInfo(2, 1), new FrameInfo(3, 1),
                new FrameInfo(4, 1), new FrameInfo(5, 1) };
        addEatMoveSequenceFrame(new FrameSequence[]{new FrameSequence(moveat,new SequenceSoundEffect(SequenceSoundEffect.SPREAD,SoundEffect.ELICOPTER))});
        // KILLED SEQUENCE 7-10
        FrameInfo[] killed = { new FrameInfo(6, 1), new FrameInfo(7, 1), new FrameInfo(8, 1),
                new FrameInfo(9, 1), new FrameInfo(10, 1), };
        addKillSequenceFrame(new FrameSequence[]{new FrameSequence(killed,new SequenceSoundEffect(SequenceSoundEffect.ADD,SoundEffect.BITE))});
    }


    public synchronized void buildKilledSequence(Move m) {
        pltransition = new ParallelTransition(this);
        SimpleFrameAnimation transition = null;
                transition = new SimpleFrameAnimation(killSequenceFrame, this, false, 40);
                transition.setDuration(Duration.seconds(0.5));



        SpritePiece eated = this;
        transition.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                if (m.getType() == Move.EAT) {

                    BCDraugthsApp.log.info("FIRE EventRemoveEatPiece.REMOVE_PIECE_EVENT: " + eated);
                    fireEvent(new EventRemoveEatPiece(eated, eated.getParent(), EventRemoveEatPiece.REMOVE_PIECE_EVENT));


                }

            }
        });
        pltransition.getChildren().add(transition);

    }

    public void buildMoveSequence(boolean ciclyc) {


        SimpleFrameAnimation transition = new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 20);
        transition.setDuration(pltransition.getTotalDuration());
        pltransition.getChildren().add(transition);


    }

    @Override
    public void buildMoveEatSequence(Move m, boolean ciclyc) {

        SimpleFrameAnimation transition = null;


        transition = new SimpleFrameAnimation(eatMoveSequenceFrame, this, ciclyc, 20);
        transition.setDuration(pltransition.getTotalDuration());

        pltransition.getChildren().add(transition);

    }

    @Override
    public void buildPedinaMovePath(Move m) {
        throw new RuntimeException("Not allowed");
    }


    public void buildDamaMovePath(Move m) {

        QuadCurveTo arc = new QuadCurveTo();
        QuadCurveTo arc2 = new QuadCurveTo();
        //javafx.scene.shape.
        double x0 = convertBoardJtoPositionXCenter(m.getP().getJ(), wSquare);
        //(m.getP().getI() * wSquare) + ((wSquare / 2));
        double y0 = convertBoardItoPositionYCenter(m.getP().getI(), hSquare);
        //(m.getP().getJ() * hSquare) + ((hSquare / 2));

        Color color = Color.CHARTREUSE;
        double x1 = convertBoardJtoPositionXCenter(m.getJ1(), wSquare);
        //(m.getI1() * wSquare) + ((wSquare / 2));
        double y1 = convertBoardItoPositionYCenter(m.getI1(), hSquare);
        //(m.getJ1() * hSquare) + ((hSquare / 2));
        BCDraugthsApp.log.info(" move from (x0,y0)=(" + x0 + "," + y0 + ")");
        arc.setX(x1);
        arc.setY(y1);
        if (y0 < y1) {
            arc.setControlX(x0);
            arc.setControlY(y0 - 100);
        } else {
            arc.setControlX(x0);
            arc.setControlY(y0 - 200);
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
        pathTransition.setDuration(Duration.seconds(1.5));
        pathTransition.setPath(path);
        pathTransition.setNode(this);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(true);
        //.build();
        pltransition.getChildren().add(pathTransition);

        if (BCDraugthsApp.tracepath) this.getFxBoard().add(path);

    }

    @Override
    public void buildPedinaMoveEatPath(Move m) {
        throw new RuntimeException("Not allowed");
    }


    public void buildDamaMoveEatPath(Move m) {
//        ParallelTransition pt = new ParallelTransition(this);
//        ParallelTransition pt2 = new ParallelTransition(this);
        //javafx.scene.shape.

        double x0 = convertBoardJtoPositionXCenter(m.getP().getJ(), wSquare);
        //(m.getP().getI() * wSquare) + ((wSquare / 2));
        double y0 = convertBoardItoPositionYCenter((m.getP().getI()), hSquare);
        double ydecollo0 = convertBoardItoPositionYCenter((m.getP().getI() - 1), hSquare);
        //(m.getP().getJ() * hSquare) + ((hSquare / 2));

        Color color = Color.CHARTREUSE;
        double x1 = convertBoardJtoPositionXCenter(m.getJ1(), wSquare);
        //(m.getI1() * wSquare) + ((wSquare / 2));
        double ydecollo1 = convertBoardItoPositionYCenter(m.getI1() - 1, hSquare);
        double y1 = convertBoardItoPositionYCenter(m.getI1(), hSquare);
        //(m.getJ1() * hSquare) + ((hSquare / 2));
        LineTo moveDecollo0 = new LineTo();
        moveDecollo0.setX(x0);
        moveDecollo0.setY(ydecollo0);
        /**
         * if (m.x > m.getP().getX()) arc.setXAxisRotation(3.14/3); else arc.setXAxisRotation(3.14);
         */

        //arc.setControlX(x0);
        //arc.setControlY(y1 - (hSquare * 2));
        LineTo moveAree = new LineTo();
        moveAree.setX(x1);
        moveAree.setY(ydecollo1);
        LineTo moveAtteraggio = new LineTo();
        moveAtteraggio.setX(x1);
        moveAtteraggio.setY(y1);
        MoveTo from = new MoveTo(x0, y0);
        MoveTo start2 = new MoveTo(x0, ydecollo0);
        Path path = new Path();
        path.getElements().add(from);
        path.getElements().add(moveDecollo0);


        Path pathSecondHalf = new Path();
        pathSecondHalf.getElements().add(start2);
        pathSecondHalf.getElements().add(moveAree);
        pathSecondHalf.getElements().add(moveAtteraggio);

        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);

        Color color2 = Color.BROWN;
        pathSecondHalf.setStroke(color2);
        pathSecondHalf.setStrokeWidth(2);
        pathSecondHalf.getStrokeDashArray().setAll(5d, 5d);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(0.5));
        pathTransition.setPath(path);
        pathTransition.setNode(this);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(true);
        //

        PathTransition pathTransition2 = new PathTransition();
        pathTransition2.setDuration(Duration.seconds(1.5));
        pathTransition2.setPath(pathSecondHalf);
        pathTransition2.setNode(this);
        pathTransition2.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition2.setCycleCount(1);
        pathTransition2.setAutoReverse(true);
        pathTransition2.setDelay(Duration.seconds(1));

        // transition[TRANSITION_STEP.FIRST_HALF_STEP] = pt;

        // transition[TRANSITION_STEP.SECOND_HALF_STEP] = pt2;

//        pt2.setDelay(Duration.seconds(1));
//        pt.getChildren().add(pathTransition);
//        pt2.getChildren().add(pathTransition2);
        steps.put(DAMA_TAKEOFF, pathTransition);

        SequentialTransition seq = new SequentialTransition();
        seq.getChildren().add(pathTransition);
        seq.getChildren().add(pathTransition2);
        pltransition.getChildren().add(seq);
        if (BCDraugthsApp.tracepath) {
            this.getFxBoard().add(path);
            this.getFxBoard().add(pathSecondHalf);
        }

    }

    @Override
    protected void init() {
        buildSoldierKingFrames();
    }

    @Override
    public void builAnimDamaEat(Move m) {

        ParallelTransition ptMissile = new ParallelTransition();
        extraSprite[0] = new Sprite("missile2.png", "missile");

        this.getFxBoard().add(extraSprite[0]);

        double x = convertBoardJtoPositionXCenter(m.getP().getJ(), wSquare);
        double y = convertBoardItoPositionYCenter(m.getP().getI() - 1, hSquare);

        double xe = convertBoardJtoPositionXCenter(m.getEat().getJ(), wSquare);
        double ye = convertBoardItoPositionYCenter(m.getEat().getI(), hSquare);
        extraSprite[0].setVisible(false);
        extraSprite[0].setX(x);
        extraSprite[0].setY(y);
        extraSprite[0].toFront();
        QuadCurveTo qTO = new QuadCurveTo();
        MoveTo mt = new MoveTo();
        mt.setX(x);
        mt.setY(y);
        qTO.setX(xe);
        qTO.setY(ye);
        qTO.setControlX(x - 150);
        qTO.setControlY(y - 150);
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
        pathMissileTransition.setDuration(Duration.seconds(0.8));
        pathMissileTransition.setPath(path);
        pathMissileTransition.setNode(extraSprite[0]);
        pathMissileTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathMissileTransition.setCycleCount(1);
        pathMissileTransition.setAutoReverse(true);

        ptMissile.getChildren().add(pathMissileTransition);
        if (BCDraugthsApp.tracepath) {
            this.getFxBoard().add(path);
        }

       FrameInfo misileFreame[]= new FrameInfo[]{new FrameInfo(0, 1),
                new FrameInfo(1, 1), new FrameInfo(2, 1), new FrameInfo(3, 1),
                new FrameInfo(4, 1), new FrameInfo(5, 1), new FrameInfo(6, 1), new FrameInfo(7, 1), new FrameInfo(5, 1), new FrameInfo(6, 1), new FrameInfo(7, 1)};
        ShotCollisionFrameAnimation missileAnim = new ShotCollisionFrameAnimation(new FrameSequence[]{new FrameSequence(misileFreame,new SequenceSoundEffect(SequenceSoundEffect.SPREAD,SoundEffect.MISSILE))}
                , m, this, extraSprite[0], true, 100);
        missileAnim.setDuration(ptMissile.getTotalDuration());

        ptMissile.getChildren().add(missileAnim);

        buildDamaMoveEatPath(m);


        steps.get(DAMA_TAKEOFF).setOnFinished(new EventHandler<ActionEvent>() {


            @Override
            public void handle(ActionEvent event) {
                extraSprite[0].setVisible(true);
                ptMissile.play();


            }
        });
        buildMoveEatSequence(m, true);
        pathMissileTransition.setOnFinished(new ShotAnimationEndHandler(extraSprite[0], this));
        pltransition.setOnFinished(new PieceAnimationEndHandler(this, m));

    }


    public SpritePiece crownedSound() {
        SpritePiece sp = null;
        if (piece.getType() == Piece.DRAUGTH &&
                !draugthTransform) {
            draugthTransform = true;
            FXBoard.SoundSystem.playSound(SoundEffect.ACHB, 1);


            this.setDraugthTransform(true);


        }
        setFrame(0);
        //recalculateXYPosition();
        return this;

    }


}
