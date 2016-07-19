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
import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.PathTransitionBuilder;
import javafx.animation.RotateTransition;
import javafx.animation.RotateTransitionBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.animation.FrameAnimationTimer;
import sa.fx.draugths.animation.PedinaAnimationEndHandler;
import sa.fx.draugths.animation.TRANSITION_STEP;
import sa.gameboard.core.Piece;

/**
 *
 * @author ale2s_000
 */
public class SWhitePiece extends SpritePiece {

    int color;
    ImageView missile;

    SWhitePiece(int color, BCDraugthsApp bcdg, Piece boardPiece, int w, int h, double wbBox, double hbBox, String img) {
        super(w, h, wbBox, hbBox, bcdg, img);
        this.color = color;
        this.bcdg = bcdg;
        this.boardPieceLink = boardPiece;
    }

    private void setEatedAnimation(int f1, int f2, double frac, boolean ciclyc, long interval, String sound) {
        frameAnimTimer[0] = new FrameAnimationTimer(f1, f2, this, frac, ciclyc, interval, sound);
    }

    public void buildDestroyDamaAnimation() {
        if (!draugthTransform) {
            setEatedAnimation(12, 18, 0, false, 100, FrameAnimationTimer.BIGEXPLOSION);
        } else {
            setEatedAnimation(5, 10, 0, false, 50, FrameAnimationTimer.BIGEXPLOSION);
        }
    }

    public void buildDestroyPedinaAnimation() {
        if (!draugthTransform) {
            setEatedAnimation(10, 13, 0.35d, false, 100, FrameAnimationTimer.BITE);
        } else {
            setEatedAnimation(7, 11, 0.4d, false, 100, FrameAnimationTimer.BITE);
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
        RotateTransition rotateTransition = RotateTransitionBuilder.create()
                // .node(p)
                .duration(getAnimDuration())
                .fromAngle(0)
                .toAngle(-720)
                .cycleCount(1)
                .autoReverse(false)
                .build();
        ((ParallelTransition) ptList[TRANSITION_STEP.FULL_STEP]).getChildren().add(rotateTransition);
    }

    public void buildFrameEatMoveAnimation(double frac, boolean ciclyc) {
        frameAnimTimer[0] = new FrameAnimationTimer(2, 3, this, frac, ciclyc, 100, FrameAnimationTimer.FIRE);

    }

    public void setFrameDama() {
        if (boardPieceLink.getType() == Piece.DRAUGTH &&
                draugthTransform==false) {
            draugthTransform=true;
            frameImages = new Image("white_dama.png");
            pedina.setImage(frameImages);
            AudioClip ach = buildMedia(FrameAnimationTimer.ACHW);
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
        double x0 = (m.getP().getI() * wboardBox) + ((wboardBox / 2));
        double y0 = (m.getP().getJ() * hBoardBox) + ((hBoardBox / 2));

        Color color = Color.CHARTREUSE;
        double x1 = (m.getI1() * wboardBox) + ((wboardBox / 2));
        double y1 = (m.getJ1() * hBoardBox) + ((hBoardBox / 2));
        arc.setX(x1);
        arc.setY(y1);

        arc.setControlX(x1);
        arc.setControlY(y1);

        MoveTo from = new MoveTo(x0, y0);
        Path path = PathBuilder.create()
                .elements(
                        from,
                        arc
                )
                .build();
        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);

        PathTransition pathTransition = PathTransitionBuilder.create()
                .duration(Duration.seconds(2))
                .path(path)
                .node(this)
                .orientation(PathTransition.OrientationType.NONE)
                .cycleCount(1)
                .autoReverse(true)
                .build();
        ptList[TRANSITION_STEP.FULL_STEP] = pt;
        pt.getChildren().add(pathTransition);

    }

    public void buildDamaMovePath(Move m) {
        ParallelTransition pt = new ParallelTransition(this);
        QuadCurveTo arc = new QuadCurveTo();
        //javafx.scene.shape.
        double x0 = (m.getP().getI() * wboardBox) + ((wboardBox / 2));
        double y0 = (m.getP().getJ() * hBoardBox) + ((hBoardBox / 2));

        Color color = Color.CHARTREUSE;
        double x1 = (m.getI1() * wboardBox) + ((wboardBox / 2));
        double y1 = (m.getJ1() * hBoardBox) + ((hBoardBox / 2));
        arc.setX(x1);
        arc.setY(y1);
        arc.setControlX(x1);
        arc.setControlY(y1);

        MoveTo from = new MoveTo(x0, y0);
        Path path = PathBuilder.create()
                .elements(
                        from,
                        arc
                )
                .build();
        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);

        PathTransition pathTransition = PathTransitionBuilder.create()
                .duration(Duration.seconds(2))
                .path(path)
                .node(this)
                .orientation(PathTransition.OrientationType.NONE)
                .cycleCount(1)
                .autoReverse(true)
                .build();
        ptList[TRANSITION_STEP.FULL_STEP] = pt;
        pt.getChildren().add(pathTransition);

    }

    public void buildPedinaMoveEatPath(Move m) {
        ParallelTransition pt = new ParallelTransition(this);
        QuadCurveTo arc = new QuadCurveTo();
        //javafx.scene.shape.
        double x0 = (m.getP().getI() * wboardBox) + ((wboardBox / 2));
        double y0 = (m.getP().getJ() * hBoardBox) + ((hBoardBox / 2));

        Color color = Color.CHARTREUSE;
        double x1 = (m.getI1() * wboardBox) + ((wboardBox / 2));
        double y1 = (m.getJ1() * hBoardBox) + ((hBoardBox / 2));
        arc.setX(x1);
        arc.setY(y1);
        arc.setControlX(x0);
        arc.setControlY(y1 - (h * 2));

        MoveTo from = new MoveTo(x0, y0);
        Path path = PathBuilder.create()
                .elements(
                        from,
                        arc
                )
                .build();
        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);

        PathTransition pathTransition = PathTransitionBuilder.create()
                .duration(Duration.seconds(2))
                .path(path)
                .node(this)
                .orientation(PathTransition.OrientationType.NONE)
                .cycleCount(1)
                .autoReverse(true)
                .build();
        ptList[TRANSITION_STEP.FULL_STEP] = pt;
        pt.getChildren().add(pathTransition);

    }

    public void buildDamaMoveEatPath(Move m) {
        ParallelTransition pt = new ParallelTransition(this);
        QuadCurveTo arc = new QuadCurveTo();
        //javafx.scene.shape.
        double x0 = (m.getP().getI() * wboardBox) + ((wboardBox / 2));
        double y0 = (m.getP().getJ() * hBoardBox) + ((hBoardBox / 2));

        Color color = Color.CHARTREUSE;
        double x1 = (m.getI1() * wboardBox) + ((wboardBox / 2));
        double y1 = (m.getJ1() * hBoardBox) + ((hBoardBox / 2));
        arc.setX(x1);
        arc.setY(y1);
        /**
         * if (m.x > m.getP().getX()) arc.setXAxisRotation(3.14/3); else arc.setXAxisRotation(3.14);
         */

        arc.setControlX(x0);
        arc.setControlY(y1 - (h * 2));

        MoveTo from = new MoveTo(x0, y0);
        Path path = PathBuilder.create()
                .elements(
                        from,
                        arc
                )
                .build();
        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);

        PathTransition pathTransition = PathTransitionBuilder.create()
                .duration(Duration.seconds(2))
                .path(path)
                .node(this)
                .orientation(PathTransition.OrientationType.NONE)
                .cycleCount(1)
                .autoReverse(true)
                .build();
        ptList[TRANSITION_STEP.FULL_STEP] = pt;
        pt.getChildren().add(pathTransition);

    }

    @Override
    public void animDamaEat(Move m) {
        ParallelTransition ptMissile = new ParallelTransition();
        missile = new ImageView(new Image("missile2.png"));
        missile.setViewport(new Rectangle2D(0, 0, 64, 22));
        bcdg.getBoardGroup().getChildren().add(missile);
        //x missile.toFront();
        int x = m.getP().getI();
        int y = m.getP().getJ();
        /**
         * missile.setX(x*wBoardSquare);
        missile.setY((y*hBoardSquare));
         */
        Path path = PathBuilder.create()
                .elements(new MoveTo(x * wboardBox + 10, y * hBoardBox + 10),
                        new QuadCurveTo((x + 4 * (wboardBox)), (y * hBoardBox),
                                (eated.boardPieceLink.getI() * wboardBox + 20), (eated.boardPieceLink.getJ() * wboardBox + 10)
                        )
                )
                .build();
        PathTransition pathMissileTransition = PathTransitionBuilder.create()
                .duration(Duration.seconds(0.5))
                .path(path)
                .node(missile)
                .orientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT)
                .cycleCount(1)
                .autoReverse(false)
                .build();

        //table.getChildren().add(path);
        ptMissile.getChildren().add(pathMissileTransition);

        buildDamaMoveEatPath(m);

        ptList[TRANSITION_STEP.MISSILE_FULL_STEP] = ptMissile;
        frameAnimTimer[1] = new FrameAnimationTimer(3, 4, this, 0, true, 10, FrameAnimationTimer.MISSILE);
        frameAnimTimer[1].start();

        eated.buildDestroyDamaAnimation();
        buildFrameMoveAnimation(0, true);
        //setFrame(3);

        ptMissile.play();

        ptList[TRANSITION_STEP.FULL_STEP].setOnFinished(new PedinaAnimationEndHandler(this, m, eated, bcdg, wboardBox, hBoardBox));

    }

    @Override
    protected void playAnimDamaEat(Move m) {

        ptList[TRANSITION_STEP.MISSILE_FULL_STEP].setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                missile.setVisible(false);
                frameAnimTimer[1].stop();
                start(m);
                eated.start(m);
                event.consume();

            }
        });

    }

}
