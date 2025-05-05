package sa.fx.draugths.pieces.efa;

import javafx.animation.ParallelTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.FrameInfo;
import sa.fx.draugths.animation.FrameSequence;
import sa.fx.draugths.animation.SimpleFrameAnimation;
import sa.fx.draugths.animation.event.EventRemoveEatPiece;
import sa.fx.draugths.pieces.SpritePiece;
import sa.fx.draugths.utility.BoardHW;
import sa.fx.draugths.utility.SequenceSoundEffect;
import sa.fx.draugths.utility.SoundEffect;
import sa.gameboard.core.Piece;

public class SkySoldierKing extends SoldierKing {


    protected static  String PIECE_IMAGE =  "soldier_checker_dama_fly.png";


    public SkySoldierKing(Piece piece,
                          BoardHW boardHW, FXBoard board) {
        super(PIECE_IMAGE,"SkySoldier",  piece, boardHW, board);

    }


    @Override
    protected void init() {
        buildSkySoldierKingFrames();
    }

    public  void buildSkySoldierKingFrames() {
        crownedSound();
        // MOVE SEQUENCE 1-5
        FrameInfo[] move = { new FrameInfo(3, 1), new FrameInfo(4, 1), new FrameInfo(3, 1), new FrameInfo(4, 1),
                new FrameInfo(3, 1) };
        addMoveSequenceFrame(new FrameSequence[]{new FrameSequence(move,new SequenceSoundEffect(SequenceSoundEffect.SPREAD,SoundEffect.DRONE))});
        // MOVE EAT SEQUENCE 1-5
        FrameInfo[] moveat = { new FrameInfo(3, 1), new FrameInfo(4, 1), new FrameInfo(3, 1),
                new FrameInfo(4, 1), new FrameInfo(5, 1) };
        addEatMoveSequenceFrame(new FrameSequence[]{new FrameSequence(moveat,new SequenceSoundEffect(SequenceSoundEffect.SPREAD,SoundEffect.DRONE))});
        // KILLED SEQUENCE 7-10
        FrameInfo[] killed = { new FrameInfo(6, 1), new FrameInfo(7, 1), new FrameInfo(8, 1),
                new FrameInfo(9, 1), new FrameInfo(10, 1),new FrameInfo(10, 1) };
        addKillSequenceFrame(new FrameSequence[]{new FrameSequence(killed,new SequenceSoundEffect(SequenceSoundEffect.ADD,SoundEffect.SAPCESHIP_BUZZ))});

    }

    @Override
    public void buildMoveSequence(boolean ciclyc) {


        SimpleFrameAnimation transition = new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 20);
        transition.setDuration(pltransition.getTotalDuration());
        pltransition.getChildren().add(transition);


    }

    public synchronized void buildKilledSequence(Move m) {
        pltransition = new ParallelTransition(this);
        SimpleFrameAnimation transition = null;
        BCDraugthsApp.log.info("buildDefaultKillAnimation....11");
        transition = new SimpleFrameAnimation(killSequenceFrame, this, false, 40);
        transition.setDuration(Duration.seconds(0.5));
        BCDraugthsApp.log.info("buildDefaultKillAnimation....12--->"+m.toString());
        SpritePiece eated = this;
        transition.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                BCDraugthsApp.log.info("buildDefaultKillAnimation....13");
                if (m.getType() == Move.EAT) {
                    BCDraugthsApp.log.info("buildDefaultKillAnimation....14");
                    BCDraugthsApp.log.info("FIRE EventRemoveEatPiece.REMOVE_PIECE_EVENT: " + eated);
                    fireEvent(new EventRemoveEatPiece(eated, eated.getParent(), EventRemoveEatPiece.REMOVE_PIECE_EVENT));


                }

            }
        });
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
    public SpritePiece crownedSound() {
        if (piece.getType() == Piece.DRAUGTH &&
                !draugthTransform) {
            draugthTransform = true;

            FXBoard.SoundSystem.playSound(SoundEffect.ACHB, 1);
            this.setDraugthTransform(true);
            //buildFrameImages();

//            setScaleX(0.64);
//            setScaleY(0.64);
            // setW(96);setH(96);

        }
        setFrame(0);
        //recalculateXYPosition();
        return this;

    }


}
