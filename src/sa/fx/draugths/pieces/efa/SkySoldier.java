package sa.fx.draugths.pieces.efa;

import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
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
import sa.fx.draugths.pieces.SpritePiece;
import sa.fx.draugths.utility.BoardHW;
import sa.fx.draugths.utility.SequenceSoundEffect;
import sa.fx.draugths.utility.SoundEffect;
import sa.gameboard.core.Piece;

public class SkySoldier extends Soldier {

    protected static  String PIECE_IMAGE  = "soldier_checker_fly.png";


    public SkySoldier(Piece piece,
                      BoardHW boardHW, FXBoard board) {
        super( PIECE_IMAGE,"SkySoldier", piece, boardHW, board);



    }

    @Override
    protected void init() {
        buildSkySoldierFrames();
    }

    public  void buildSkySoldierFrames() {
        // MOVE SEQUENCE 1-3
        FrameInfo[] move = { new FrameInfo(6, 1), new FrameInfo(7, 1), new FrameInfo(8, 1) };
        addMoveSequenceFrame(new FrameSequence[]{new FrameSequence(move,new SequenceSoundEffect(SequenceSoundEffect.SPREAD,SoundEffect.JETPACK))});
        // MOVE EAT SEQUENCE 2-5
        FrameInfo[] moveat = { new FrameInfo(2, 1), new FrameInfo(3, 1) };
        addEatMoveSequenceFrame(new FrameSequence[]{new FrameSequence(moveat,new SequenceSoundEffect(SequenceSoundEffect.ADD,SoundEffect.LASER))});

    }

    @Override
    public void buildMoveSequence(boolean ciclyc) {


        SimpleFrameAnimation transition = new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 70);
        transition.setDuration(pltransition.getTotalDuration());
        pltransition.getChildren().add(transition);


    }

    public synchronized void buildKilledSequence(Move m) {
        pltransition=new ParallelTransition(this);
        SimpleFrameAnimation transition=null;
        BCDraugthsApp.log.info("buildDefaultKillAnimation....1");

        if (m.getP().getType() ==Piece.CHECKER) {

            if(!draugthTransform) {
                // KILLED SEQUENCE 10-17
                FrameInfo[] killed = { new FrameInfo(10, 1), new FrameInfo(11, 1), new FrameInfo(12, 1),
                        new FrameInfo(13, 1), new FrameInfo(14, 1), new FrameInfo(15, 1), new FrameInfo(16, 1),
                        new FrameInfo(17, 1) };
                addKillSequenceFrame(new FrameSequence[]{new FrameSequence(killed,new SequenceSoundEffect(SequenceSoundEffect.ADD,SoundEffect.BITE))});
                BCDraugthsApp.log.info("buildDefaultKillAnimation....2");
                transition= new SimpleFrameAnimation(killSequenceFrame, this, false, 25);
                transition.setDuration(Duration.seconds(0.5));


            }
            else {
                BCDraugthsApp.log.info("errror....3");
//                transition= new SimpleFrameAnimation(killSequenceFrame, this, false, 25, SoundEffect.SAPCESHIP_BUZZ);
//                transition.setDuration(Duration.seconds(0.5));

            }

        } else if (m.getP().getType()  ==Piece.DRAUGTH)  {
            BCDraugthsApp.log.info("buildDefaultKillAnimation....4");
                BCDraugthsApp.log.info("buildDefaultKillAnimation....5");
                FrameInfo[] killed = { new FrameInfo(10, 1), new FrameInfo(11, 1), new FrameInfo(12, 1),
                        new FrameInfo(13, 1), new FrameInfo(14, 1), new FrameInfo(15, 1), new FrameInfo(16, 1),
                        new FrameInfo(17, 1) };
                addKillSequenceFrame(new FrameSequence[]{new FrameSequence(killed,new SequenceSoundEffect(SequenceSoundEffect.SPREAD,SoundEffect.SAPCESHIP_BUZZ))});
                transition=new SimpleFrameAnimation(killSequenceFrame, this, false,40);
                transition.setDuration(Duration.seconds(0.5));


        }
        BCDraugthsApp.log.info("buildDefaultKillAnimation....7");
        SpritePiece eated=this;
        transition.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                if(m.getType()==Move.EAT) {

                    BCDraugthsApp.log.info("FIRE EventRemoveEatPiece.REMOVE_PIECE_EVENT: "+eated);
                    fireEvent(new EventRemoveEatPiece(eated, eated.getParent(),EventRemoveEatPiece.REMOVE_PIECE_EVENT));


                }

            }
        });
        pltransition.getChildren().add( transition);

    }
    @Override
    public void buildMoveEatSequence(Move m, boolean ciclyc) {

        SimpleFrameAnimation transition = null;

        transition = new ShotDistanceFrameAnimation(eatMoveSequenceFrame, this, m, ciclyc, 150);
        transition.setDuration(pltransition.getTotalDuration());


        pltransition.getChildren().add(transition);

    }

    @Override
    public SpritePiece crownedSound() {

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

    @Override
    protected void buildAnimPedinaEat(Move m) {

        buildPedinaMoveEatPath(m);
        buildMoveEatSequence(m, false);
        pltransition.setOnFinished(new PieceAnimationEndHandler(this, m));


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
        pathTransition.setNode(this);
//        
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);


        //.build();
        RotateTransition rotateTransition = new RotateTransition();
        // .node(p)
        rotateTransition.setDuration(Duration.seconds(1.5));
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(+1080);
        rotateTransition.setNode(this);

        rotateTransition.setCycleCount(1);
//        rotateTransition.setNode(this);
        rotateTransition.setAutoReverse(false);
        //.build();
        // rotateTransition;

//        pt.setCycleCount(1);
//        pt.setAutoReverse(true);
        extraSprite[0] = new Sprite("laser.png", "laser");
        extraSprite[0].setVisible(true);
        extraSprite[0].setX(x0);
        extraSprite[0].setY(y0);
        extraSprite[0].toFront();
        this.getFxBoard().add(extraSprite[0]);

        SkySoldier questo = this;


        Path laserPath = new Path();
        laserPath.setStroke(color);
        laserPath.setStrokeWidth(2);
        laserPath.getStrokeDashArray().setAll(5d, 5d);

        double x = questo.getLayoutX();
        double y = questo.getLayoutY();
        BCDraugthsApp.log.info("x,y=" + x + "," + y);
        double xn = convertBoardJtoPositionXCenter(m.getEat().getJ(), wSquare);
        //(m.getI1() * wSquare) + ((wSquare / 2));
        double yn = convertBoardItoPositionYCenter(m.getEat().getI(), hSquare);
        MoveTo laserFrom = new MoveTo(x0, y0);
        LineTo lineTo = new LineTo(xn, yn);
        laserPath.getElements().addAll(laserFrom, lineTo);
        PathTransition shootLaser = new PathTransition();
        shootLaser = new PathTransition();
        shootLaser.setNode(extraSprite[0]);
        shootLaser.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        shootLaser.setPath(laserPath);
        questo.getFxBoard().add(laserPath);
        shootLaser.setDuration(Duration.seconds(0.3));

        shootLaser.setCycleCount(1);
        shootLaser.setOnFinished(actionEvent -> {
            questo.getFxBoard().remove(extraSprite[0]);

        });
        //translateLaser[0].play();


        //timer.start();
        pltransition.getChildren().add(shootLaser);
        pltransition.getChildren().add(rotateTransition);
        pltransition.getChildren().add(pathTransition);
        //   pltransition.setNode(this);
        pltransition.setAutoReverse(true);
        pltransition.setCycleCount(1);

//        pt.getChildren().add(pathTransition);
//        pt.getChildren().add(rotateTransition);
//        transition.add(pt); 
        if (BCDraugthsApp.tracepath) this.getFxBoard().add(path);


    }

}
