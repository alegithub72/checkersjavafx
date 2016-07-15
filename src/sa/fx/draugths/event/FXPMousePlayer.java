/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.event;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathBuilder;
import javafx.scene.shape.QuadCurveTo;
import sa.boardgame.core.moves.Move;
import sa.boardgame.core.players.HumanPlayer;
import sa.fx.draugths.BCDraugthsApp;
import sa.gameboard.core.Piece;

/**
 *
 * @author ale2s_000
 */
public class FXPMousePlayer extends HumanPlayer implements EventHandler<MouseEvent> {

    BCDraugthsApp graficBoard;
    ImageView punteImage[];
    List movesMouse;


    @Override
    public void possibleMove(String c) {
            int i=-1,j=-1;
            char c0 = c.charAt(0);
            char c1 = c.charAt(1);
            char c2 = c.charAt(2);
            try {
                i = Integer.parseInt("" + c1);
                j = Integer.parseInt("" + c2);

            } catch (NumberFormatException e) {
                //  e.printStackTrace();
             
            }        
            List<Move> lm=this.listPedineMoves(i, j);
        if(lm!=null && lm.size()>0){
            l=new Move[lm.size()];
        l=lm.toArray(l);

        }

       
    }

    @Override
    public void deleteMoveChoose() {
        if (movesMouse != null) {
            for (int i = 0; i < movesMouse.size(); i++) {

                this.graficBoard.getBoardGroup().getChildren().remove(movesMouse.get(i));
                movesMouse.remove(i);
            }

        }
    }

    public FXPMousePlayer(BCDraugthsApp graficBoard) {
        super(Piece.WHITE);
        this.graficBoard = graficBoard;

    }

    @Override
    public Move chooseMoveList( int n) {
        return l[n];
    }

    @Override
    public String getCommand() {
        
        String r =null;
         if(moveChoose==-1)
         r="p" + graficBoard.getSelect().getBoardPieceLink().getI() + graficBoard.getSelect().getBoardPieceLink().getJ();
         else r=""+moveChoose;
        return r;
    }

    public boolean makeMove(Move m) {
        return board.makeMove(m);
    }



    
    
    @Override
    public void handle(MouseEvent event) {
        if (!graficBoard.isOn()) {
            if (graficBoard.getSelect() != null) {
                visualizeMove();
            }

        }
        event.consume();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void renderMoveChoose() {
        for (int i = 0; i < l.length; i++) {
            Move m = l[i];

            if (m != Move.NULLMOVE) {
                QuadCurveTo quadTo = new QuadCurveTo();
                double x0 = m.getP().getI() * graficBoard.wBoardSquare;
                double y0 = m.getP().getJ() * graficBoard.hBoardSquare;
                double inc = 100;
                Color color = Color.CHARTREUSE;
                if (m.getI1() > m.getP().getI()) {
                    quadTo.setControlX(x0 + inc);
                } else {
                    quadTo.setControlX(x0 - inc);
                }
                quadTo.setControlY(y0 + 22);
                double x1 = (m.getI1() * graficBoard.wBoardSquare) + 32;
                double y1 = (m.getJ1() * graficBoard.hBoardSquare) + 32;

                quadTo.setX(x1);
                quadTo.setY(y1);
                Path path = PathBuilder.create()
                        .elements(
                                new MoveTo(x0 + 32, y0 + 32),
                                quadTo
                        //,
                        //new CubicCurveTo(0, 120, 0, 240, 380, 240)
                        )
                        .build();
                path.setStroke(Color.WHITESMOKE);
                path.setOpacity(0.6);
                path.setStrokeWidth(2);
                path.getStrokeDashArray().setAll(5d, 5d);
                if (i == 0) {
                    punteImage = new ImageView[l.length];
                    movesMouse = new ArrayList(l.length * 2);
                }

                ImageView imagePunt = new ImageView(new Image("puntatore.png"));

                punteImage[i] = imagePunt;
                punteImage[i].setOnMouseClicked(new ConfirmCommandEvent(this, i,this.graficBoard));
                punteImage[i].setX(x1 - 32);
                punteImage[i].setY(y1 - 22);
                this.graficBoard.getBoardGroup().getChildren().add(punteImage[i]);
                movesMouse.add(punteImage[i]);
                this.graficBoard.getBoardGroup().getChildren().add(path);
                movesMouse.add(path);
                System.out.println(i + ")" + m);

            }
        }
    }

    
    
    
}
