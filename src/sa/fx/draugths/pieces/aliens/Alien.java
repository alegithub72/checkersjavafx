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
import sa.fx.draugths.animation.SimpleFrameAnimation;
import sa.fx.draugths.animation.event.EventEatAnimPiece;
import sa.fx.draugths.pieces.SpritePiece;
import sa.fx.draugths.utility.BoardHW;
import sa.fx.draugths.utility.SoundEffect;
import sa.gameboard.core.Piece;

/**
 * @author Alessio Sardaro
 */
public class Alien extends SpritePiece {


    protected static  String PIECE_IMAGE ="alien_checker.png";

    public Alien(Piece piece, BoardHW boardHW, FXBoard board) {
        super(PIECE_IMAGE,"Alien", boardHW, piece,  board);


    }

    public Alien(String pieceImage, String skyAlien, Piece piece, BoardHW boardHW, FXBoard board) {
        super(pieceImage,skyAlien,boardHW,piece,board);
    }


    @Override
    public void buildKilledSequence(Move m) {
        pltransition = new ParallelTransition(this);
        buildDefaultKillAnimation(killSequenceFrame, m, false, 25, SoundEffect.EXPLOSION);

    }

    public void buildMoveSequence(boolean ciclyc) {
        SimpleFrameAnimation transition = null;
        transition = new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 50, SoundEffect.CLOPETE);
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


    public void buildPedinaMovePath(Move m) {

        QuadCurveTo quadTo = new QuadCurveTo();
        QuadCurveTo quadTo2 = new QuadCurveTo();
        double x0 = convertBoardJtoPositionXCenter(m.getP().getJ(), wSquare);
        //m.getP().getI() * wboardBox + ((wboardBox / 2));
        double y0 = convertBoardItoPositionYCenter(m.getP().getI(), hSquare);
        //m.getP().getJ() * hBoardBox + ((hBoardBox / 2));
        double x1 = convertBoardJtoPositionXCenter(m.getJ1(), wSquare);
        //(m.getI1() * wboardBox) + ((wboardBox / 2));
        double y1 = convertBoardItoPositionYCenter(m.getI1(), hSquare);
        //(m.getJ1() * hBoardBox) + ((hBoardBox / 2));

        Path path = null;
        Color color = Color.CHARTREUSE;

        quadTo.setControlX(x1);
        quadTo.setControlY(y1);
        quadTo.setX(x1);
        quadTo.setY(y1);
        path = new Path();
        path.getElements().add(new MoveTo(x0, y0));
        path.getElements().add(quadTo);


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

        pltransition.getChildren().add(pathTransition);
        if (BCDraugthsApp.tracepath) this.getFxBoard().add(path);


    }

    @Override
    public void buildDamaMovePath(Move m) {
        throw new RuntimeException("Not allowed");

    }

    public void buildPedinaMoveEatPath(Move m) {
        SequentialTransition sq = new SequentialTransition(this);
        QuadCurveTo quadTo = new QuadCurveTo();
        QuadCurveTo quadTo2 = new QuadCurveTo();
        double x0 = convertBoardJtoPositionXCenter(m.getP().getJ(), wSquare);
        //( m.getP().getI() * wboardBox) + ((wboardBox / 2));
        double y0 = convertBoardItoPositionYCenter(m.getP().getI(), hSquare);
        //(m.getP().getJ() * hBoardBox) + ((hBoardBox / 2));
        double x1 = convertBoardJtoPositionXCenter(m.getJ1(), wSquare);
        //(m.getI1() * wboardBox) + ((wboardBox / 2));
        double y1 = convertBoardItoPositionYCenter(m.getI1(), hSquare);
        //(m.getJ1() * hBoardBox) + ((hBoardBox / 2));
        double xe = 0;
        double ye = 0;
        Path path = null;
        Color color = Color.CHARTREUSE;
        quadTo.setControlX(x1);
        quadTo.setControlY(y1 - (hSquare * 2));
        quadTo2.setControlX(x1);
        quadTo2.setControlY(y1 - (hSquare * 2));
        xe = convertBoardJtoPositionXCenter(m.getEat().getJ(), wSquare);
        //(m.getEat().getI() * wboardBox) + (wboardBox / 2);
        ye = convertBoardItoPositionYCenter(m.getEat().getI(), hSquare) - 20;
        //(m.getEat().getJ() * hBoardBox) + (hBoardBox / 2);
        quadTo.setX(xe);
        quadTo.setY(ye);
        quadTo2.setX(x1);
        quadTo2.setY(y1);
        path = new Path();
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
        pathTransition.setDuration(Duration.seconds(0.4));
        pathTransition.setPath(path);
        pathTransition.setNode(this);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(true);
        SpritePiece eated = fxBoard.getSpritePiece(m.getEat().getI(), m.getEat().getJ(), m.getEat().getColor(), true);
        pathTransition.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                BCDraugthsApp.log.info("FIRE EventEatAnimPiece.KILLPLAY_EVENT at end on animation...:" + this);
                fireEvent(new EventEatAnimPiece(eated, eated.getParent(), m, EventEatAnimPiece.KILLPLAY_EVENT));


            }
        });


        PathTransition pathTransition2 = new PathTransition();
        pathTransition2.setDuration(Duration.seconds(0.5));
        pathTransition2.setPath(path2);
        pathTransition2.setNode(this);
        pathTransition2.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition2.setCycleCount(1);
        pathTransition2.setAutoReverse(true);
        //.build();
        pathTransition2.setDelay(Duration.seconds(0.6));
        //pathTransition.setNode(laser);

        sq.getChildren().add(pathTransition);
        sq.getChildren().add(pathTransition2);
        pltransition.getChildren().add(sq);// = sq;
        if (BCDraugthsApp.tracepath) {
            this.getFxBoard().add(path);
            this.getFxBoard().add(path2);
        }

    }

    @Override
    public void buildDamaMoveEatPath(Move m) {
        throw new RuntimeException("Not allowed");
    }

    @Override
    public SpritePiece crownedSound() {
        throw new RuntimeException("Not allowed");
    }


}
