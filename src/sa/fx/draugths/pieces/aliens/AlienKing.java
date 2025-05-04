/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.pieces.aliens;

import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.FrameInfo;
import sa.fx.draugths.animation.FrameSequence;
import sa.fx.draugths.animation.SimpleFrameAnimation;
import sa.fx.draugths.animation.event.EventEatAnimPiece;
import sa.fx.draugths.pieces.SpritePiece;
import sa.fx.draugths.utility.BoardHW;
import sa.fx.draugths.utility.SoundEffect;
import sa.gameboard.core.Piece;

/**
 * @author Alessio Sardaro
 */
public class AlienKing extends SpritePiece {


    protected static  String PIECE_IMAGE = "alien_checker_dama.png";


    public AlienKing(Piece piece, BoardHW boardHW, FXBoard board) {
        super(PIECE_IMAGE,"Alien", boardHW, piece,  board);

    }
    public AlienKing(String pieceImage, String moonSoldierDraught, Piece piece, BoardHW boardHW, FXBoard board) {
        super(pieceImage,moonSoldierDraught,boardHW,piece,board);
    }
    public void builAnimKing() {
        crownedSound();
        // MOVE SEQUENCE 1-3
        FrameInfo[] move = { new FrameInfo(1, 1), new FrameInfo(2, 1), new FrameInfo(3, 1) };
        addMoveSequenceFrame(new FrameSequence[]{new FrameSequence(move)});
        // MOVE EAT SEQUENCE 1-4}
        FrameInfo[] moveat = { new FrameInfo(1, 1), new FrameInfo(2, 1), new FrameInfo(3, 1),
                new FrameInfo(4, 1) };
        addEatMoveSequenceFrame(new FrameSequence[]{new FrameSequence(moveat)});
        // KILLED SEQUENCE 7-12})
        FrameInfo[] killed = { new FrameInfo(0, 1), new FrameInfo(7, 1), new FrameInfo(8, 1),
                new FrameInfo(9, 1), new FrameInfo(10, 1), new FrameInfo(11, 1), new FrameInfo(12, 1) };
        addKillSequenceFrame(new FrameSequence[]{new FrameSequence(killed)});

    }


    @Override
    protected void init() {
        builAnimKing();
    }

    @Override
    public void buildKilledSequence(Move m) {
        pltransition = new ParallelTransition(this);

        buildDefaultKillAnimation(killSequenceFrame, m, false, 25, SoundEffect.EXPLOSION);

    }

    public void buildMoveSequence(boolean ciclyc) {
        SimpleFrameAnimation transition = null;

        transition = new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 50, SoundEffect.CLOPETE_DOUBLE);
        pltransition.getChildren().add(transition);
        transition.setDuration(pltransition.getTotalDuration());


    }

    @Override
    public void buildMoveEatSequence(Move m, boolean ciclyc) {
        //SpritePiece eated=getFxBoard().getSpritePiece(m.getEat().getI(), m.getEat().getJ(),m.getEat().getColor(), false);
        SimpleFrameAnimation transition = null;

        transition = new SimpleFrameAnimation(eatMoveSequenceFrame, this, m, ciclyc, 50, SoundEffect.CLOPETE_DOUBLE);
        transition.setDuration(pltransition.getTotalDuration());
        pltransition.getChildren().add(transition);


    }

    @Override
    public void buildPedinaMovePath(Move m) {
        throw new RuntimeException("Not allowed");
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
        return this;

    }


    @Override
    public void buildDamaMoveEatPath(Move m) {
        SequentialTransition sq = new SequentialTransition(this);
        QuadCurveTo quadTo = new QuadCurveTo();
        QuadCurveTo quadTo2 = new QuadCurveTo();
        double x0 = convertBoardJtoPositionXCenter(m.getP().getJ(), wSquare);
        //m.getP().getI() * wboardBox + ((wboardBox / 2));
        double y0 = convertBoardItoPositionY(m.getP().getI(), hSquare);
        //m.getP().getJ() * hBoardBox + ((hBoardBox / 2));
        double x1 = convertBoardJtoPositionXCenter(m.getJ1(), wSquare);
        //(m.getI1() * wboardBox) + ((wboardBox / 2));
        double y1 = convertBoardItoPositionYCenter(m.getI1(), hSquare);
        //(m.getJ1() * hBoardBox) + ((hBoardBox / 2));
        double xe = 0;
        double ye = 0;

        Color color = Color.CHARTREUSE;
        quadTo.setControlX(x1);
        quadTo.setControlY(y1 - (hSquare * 2));
        quadTo2.setControlX(x1);
        quadTo2.setControlY(y1 - (hSquare * 2));
        xe = convertBoardJtoPositionXCenter(m.getEat().getJ(), wSquare);

        //(m.getEat().getI() * wSquare) + (wSquare / 2);
        ye = convertBoardItoPositionYCenter(m.getEat().getI(), hSquare);

        //(m.getEat().getJ() * hSquare) + (hSquare / 2);
        quadTo.setX(xe);
        quadTo.setY(ye);
        quadTo2.setX(x1);
        quadTo2.setY(y1);
        Path path = new Path();

        path.getElements().add(new MoveTo(x0, y0));
        path.getElements().add(quadTo);
        Path path2 = new Path();
        path2.getElements().add(new MoveTo(xe, ye));
        path2.getElements().add(quadTo2);

        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);
        path2.setStroke(color);
        path2.setStrokeWidth(2);
        path2.getStrokeDashArray().setAll(5d, 5d);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(0.5));
        pathTransition.setPath(path);
        pathTransition.setNode(this);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(true);
        //.build();
        SpritePiece eated = fxBoard.getSpritePiece(m.getEat().getI(), m.getEat().getJ(), m.getEat().getColor(), true);
        pathTransition.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                BCDraugthsApp.log.info("FIRE EventEatAnimPiece.KILLPLAY_EVENT At end of animation...");
                fireEvent(new EventEatAnimPiece(eated, eated.getParent(), m, EventEatAnimPiece.KILLPLAY_EVENT));

            }
        });
        PathTransition pathTransition2 = new PathTransition();
        pathTransition2.setDuration(Duration.seconds(1));
        pathTransition2.setPath(path2);
        pathTransition2.setNode(this);
        pathTransition2.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition2.setCycleCount(1);
        pathTransition2.setAutoReverse(true);
        pathTransition2.setDelay(Duration.seconds(0.5));


        if (BCDraugthsApp.tracepath) {
            this.getFxBoard().add(path);
            this.getFxBoard().add(path2);
        }
        sq.getChildren().add(pathTransition);
        sq.getChildren().add(pathTransition2);
        pltransition.getChildren().add(sq);
    }

    @Override
    public void buildDamaMovePath(Move m) {

        QuadCurveTo quadTo = new QuadCurveTo();
        QuadCurveTo quadTo2 = new QuadCurveTo();
        double x0 = convertBoardJtoPositionXCenter(m.getP().getJ(), wSquare);
        //m.getP().getI() * wSquare + ((wSquare / 2));
        double y0 = convertBoardItoPositionYCenter(m.getP().getI(), hSquare);
        //m.getP().getJ() * hSquare + ((hSquare / 2));
        double x1 = convertBoardJtoPositionXCenter(m.getJ1(), wSquare);
        //(m.getI1() * wSquare) + ((wSquare / 2));
        double y1 = convertBoardItoPositionYCenter(m.getI1(), hSquare);
        //(m.getJ1() * hSquare) + ((hSquare / 2));
        double xe = 0;
        double ye = 0;
        Path path = null;
        Color color = Color.CHARTREUSE;

        quadTo.setControlX(x1);
        quadTo.setControlY(y1);
        quadTo.setX(x1);
        quadTo.setY(y1);
        path = new Path();
        path.getElements().add(new MoveTo(x0, y0));
        path.getElements().add(quadTo);
        //.build();

        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(2));
        pathTransition.setPath(path);
        pathTransition.setNode(this);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        // .build();
        pltransition.getChildren().add(pathTransition);
        if (BCDraugthsApp.tracepath) this.getFxBoard().add(path);


    }

    @Override
    public void buildPedinaMoveEatPath(Move m) {
        throw new RuntimeException("Not allowed");
    }

}
