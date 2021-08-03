/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.players;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import sa.boardgame.core.moves.Move;
import sa.boardgame.core.players.HumanPlayer;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.event.EventConfirmCommand;
import sa.fx.draugths.sprite.SpritePiece;
import sa.gameboard.core.Checker;

/**
 *
 * @author ale2s_000
 */
public class FXPMousePlayer extends HumanPlayer implements EventHandler<MouseEvent> {


	FXBoard board;
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
            possibleMove=this.listPedineMoves(i, j);
            

       
    }

    @Override
    public void deleteMoveChoose() {
        if (movesMouse != null) {
            for (int i = 0; i < movesMouse.size(); i++) {
                //TODO: internare la funzione remove 
                this.board.remove(movesMouse.get(i));
               // movesMouse.remove(i);
            }
            movesMouse=new ArrayList();

        }
    }

    public FXPMousePlayer(FXBoard board) {
        super(Checker.WHITE);
        this.board = board;
 

    }

    @Override
    public Move chooseMoveList( int n,int n2) {
        return possibleMove.get(n);
    }

    @Override
    public String getCommand() {
        
        String r =null;
         if(moveChoose==-1)
         r="p" + board.getSelect().getBoardPieceLink().getI() + board.getSelect().getBoardPieceLink().getJ();
         else r=""+moveChoose;
        return r;
    }

    public boolean makeMove(Move m) {
        return this.board.getGame().getBoard().makeMove(m);
    }



    
    
    @Override
    public void handle(MouseEvent event) {
        if (!board.isAnimationOn()) {
            if (board.getSelect() != null) {
                choosePiece();
            }

        }
        event.consume();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public String drawMoveChoose() {
    	String str="";
    
        for (int i = 0; i < possibleMove.size(); i++) {
            Move m = possibleMove.get(i);
        	SpritePiece sp= board.getSpritePiece(m.getP().getI(), m.getP().getJ(),m.getP().getColor(),false);
            if (m != Move.NULLMOVE) {
                QuadCurveTo quadTo = new QuadCurveTo();
                quadTo.setAbsolute(true);
                double x0 =sp.convertBoardJtoPositionXCenter(m.getP().getJ() , FXBoard.boardHW.getW());
                       // m.getP().getI() * board.wBoardSquare;
                double y0 =sp.convertBoardItoPositionYCenter(m.getP().getI() , FXBoard.boardHW.getH());
                       // m.getP().getJ() * board.hBoardSquare;
                double inc = 100;
                Color color = Color.WHITE;
                if (m.getI1() > m.getP().getI()) {
                    quadTo.setControlX(x0 + inc);
                } else {
                    quadTo.setControlX(x0 - inc);
                }
                
                double x1 =sp.convertBoardJtoPositionXCenter(m.getJ1()  ,FXBoard.boardHW.getW());
                        //(m.getI1() * board.wBoardSquare) + 32;
                double y1 =sp.convertBoardItoPositionYCenter(m.getI1() ,FXBoard.boardHW.getH());
                       // (m.getJ1() * board.hBoardSquare) + 32;
                quadTo.setControlY(y0);
                quadTo.setX(x0);
                quadTo.setY(y0);
                
                Path path = new Path();
                MoveTo mt=new MoveTo();
                mt.setX(x1);
                mt.setY(y1);
                mt.setAbsolute(true);
                path.getElements().addAll(
                        
                                mt
                                ,quadTo
                        //,
                        //new CubicCurveTo(0, 120, 0, 240, 380, 240)
                        );
                str=str+(" cursor  x0="+x0+",y0="+y0+",x1="+x1+",y1="+y1);
                path.setStroke(Color.BLACK);
                path.setOpacity(0.6);
                path.setStrokeWidth(2);
                path.getStrokeDashArray().setAll(5d, 5d);
                if (i == 0) {
                    punteImage = new ImageView[possibleMove.size()];
                    movesMouse = new ArrayList(possibleMove.size() * 2);
                }

                ImageView imagePunt = new ImageView(new Image("puntatore.png"));
                imagePunt.setScaleX(0.64);
                imagePunt.setScaleY(0.64);
                imagePunt.setOpacity(0.6);
                punteImage[i] = imagePunt;
                punteImage[i].setOnMouseClicked(new EventConfirmCommand(this, i,board));
                double xp =sp.convertBoardJtoPositionX(m.getJ1()  , FXBoard.boardHW.getW());
                        //(m.getI1() * board.wBoardSquare) + 32;
                double yp =sp.convertBoardItoPositionY(m.getI1() , FXBoard.boardHW.getH());
                       // (m.getJ1() * board.hBoardSquare) + 32;
                punteImage[i].setX(xp);
                punteImage[i].setY(yp);
                this.board.add(punteImage[i]);
                movesMouse.add(punteImage[i]);
                this.board.add(path);
                movesMouse.add(path);
                str=str+" "+i + ")" + m;

            }
        }
        return str;
    }




    
}
