/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.pieces.efa;


import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
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
import sa.fx.draugths.animation.ShotDistanceFrameAnimation;
import sa.fx.draugths.animation.SimpleFrameAnimation;
import sa.fx.draugths.animation.event.EventRemoveEatPiece;
import sa.fx.draugths.pieces.SpritePiece;
import sa.fx.draugths.utility.BoardHW;
import sa.fx.draugths.utility.SoundEffect;
import sa.gameboard.core.Piece;


/**
 * @author Alessio Sardaro
 */
public class Soldier extends SpritePiece {


    private static final String CHECKER_SOLDIER_IMAGE = "soldier_checker.png";


    public Soldier(Piece piece,
                   BoardHW boardHW, FXBoard board) {
        super("Soldier", boardHW, piece, CHECKER_SOLDIER_IMAGE, board);


    }

    public Soldier(String img, String colofrFX, Piece piece,
                   BoardHW boardHW, FXBoard board) {
        super(colofrFX, boardHW, piece, img, board);


    }


    public synchronized void buildKilledSequence(Move m) {
        pltransition = new ParallelTransition(this);
        SimpleFrameAnimation transition = null;
        if (m.getP().getType() == Piece.CHECKER) {

            if (!draugthTransform) {

                transition = new SimpleFrameAnimation(killSequenceFrame, this, false, 25, SoundEffect.BITE);
                transition.setDuration(Duration.seconds(0.5));


            } else {
                BCDraugthsApp.log.severe("Errorre...draugthTransform......?????");

            }

        } else if (m.getP().getType() == Piece.DRAUGTH) {

            if (draugthTransform) {
                transition = new SimpleFrameAnimation(killSequenceFrame, this, false, 40, SoundEffect.BITE);
                transition.setDuration(Duration.seconds(0.5));
            } else {
                transition = new SimpleFrameAnimation(killSequenceFrame, this, false, 25, SoundEffect.BITE);
                transition.setDuration(Duration.seconds(0.5));
            }

        }
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

        if (!draugthTransform) {
            SimpleFrameAnimation transition = new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 100, SoundEffect.JUNGLE);
            transition.setDuration(pltransition.getTotalDuration());
            pltransition.getChildren().add(transition);
        } else {
            SimpleFrameAnimation transition = new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 20, SoundEffect.ELICOPTER);
            transition.setDuration(pltransition.getTotalDuration());
            pltransition.getChildren().add(transition);

        }

    }

    @Override
    public void buildMoveEatSequence(Move m, boolean ciclyc) {

        SimpleFrameAnimation transition = null;
        if (!draugthTransform) {

            transition = new ShotDistanceFrameAnimation(eatMoveSequenceFrame, this, m, ciclyc, 20, SoundEffect.FIRE);
            transition.setDuration(pltransition.getTotalDuration());


        } else {

            transition = new SimpleFrameAnimation(eatMoveSequenceFrame, this, ciclyc, 20, SoundEffect.ELICOPTER);
            transition.setDuration(pltransition.getTotalDuration());
        }
        pltransition.getChildren().add(transition);

    }


    public void buildPedinaMovePath(Move m) {

        QuadCurveTo arc = new QuadCurveTo();

        //javafx.scene.shape.
        double x0 = convertBoardJtoPositionXCenter(m.getP().getJ(), wSquare);

        // (m.getP().getI() * wboardBox) + ((wboardBox / 2));
        double y0 = convertBoardItoPositionYCenter(m.getP().getI(), hSquare);

        //(m.getP().getJ() * hBoardBox) + ((hBoardBox / 2));

        Color color = Color.ANTIQUEWHITE;
        double x1 = convertBoardJtoPositionXCenter(m.getJ1(), wSquare);

        //(m.getI1() * wboardBox) + ((wboardBox / 2));
        double y1 = convertBoardItoPositionYCenter(m.getI1(), hSquare);

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

        pltransition.getChildren().add(pathTransition);
        BCDraugthsApp.log.info(this + "  from (" + x0 + "," + y0 + ") to (" + x1 + "," + y1 + ")");

        if (BCDraugthsApp.tracepath) this.getFxBoard().add(path);
    }

    @Override
    public void buildDamaMovePath(Move m) {
        throw new RuntimeException("Not allowed");
    }


    public void buildPedinaMoveEatPath(Move m) {


        QuadCurveTo arc = new QuadCurveTo();
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
        pathTransition.setDuration(Duration.seconds(1.5));
        pathTransition.setPath(path);
//        pathTransition.setNode(this);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
//        
//        pathTransition.setCycleCount(1);
//        pathTransition.setAutoReverse(false);


        //.build();
        RotateTransition rotateTransition = new RotateTransition();
        // .node(p)
        rotateTransition.setDuration(Duration.seconds(1.5));
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(+1080);
//        rotateTransition.setCycleCount(1);
//        rotateTransition.setNode(this);
//        rotateTransition.setAutoReverse(false);
        //.build();
        // rotateTransition;

//        pt.setCycleCount(1);
//        pt.setAutoReverse(true);
        pltransition.getChildren().add(rotateTransition);
        pltransition.getChildren().add(pathTransition);
        pltransition.setNode(this);
        pltransition.setAutoReverse(true);
        pltransition.setCycleCount(1);

//        pt.getChildren().add(pathTransition);
//        pt.getChildren().add(rotateTransition);
//        transition.add(pt); 
        if (BCDraugthsApp.tracepath) this.getFxBoard().add(path);


    }

    @Override
    public void buildDamaMoveEatPath(Move m) {
        throw new RuntimeException("Not allowed");
    }


    public SpritePiece loadDraugthFrame() {

        throw new RuntimeException("Not allowed");

    }


}
