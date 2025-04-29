package sa.fx.draugths.pieces.efa;

import javafx.animation.PathTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.SimpleFrameAnimation;
import sa.fx.draugths.utility.BoardHW;
import sa.fx.draugths.utility.SoundEffect;
import sa.gameboard.core.Piece;

public class Police extends Soldier {


    protected static  String PIECE_IMAGE = "soldier_checker_helmet.png";

    public Police(Piece piece,
                  BoardHW boardHW, FXBoard board) {
        super(PIECE_IMAGE,"HelmetSoldier", piece, boardHW, board);



    }

    public Police(String pieceImage, String moonSoldier, Piece piece, BoardHW boardHW, FXBoard board) {
        super(pieceImage,moonSoldier,piece,boardHW,board);
    }


    @Override
    public void buildMoveSequence(boolean ciclyc) {

        SimpleFrameAnimation transition = new SimpleFrameAnimation(moveSequenceFrame, this, ciclyc, 70, SoundEffect.POLICE_JUMP);
        transition.setDuration(pltransition.getTotalDuration());
        pltransition.getChildren().add(transition);


    }

@Override
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
    if(m.getP().getJ()>m.getJ1()) arc.setControlX(x1+100);
    else arc.setControlX(x1-100);
    arc.setControlY(y1-50);

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
}
