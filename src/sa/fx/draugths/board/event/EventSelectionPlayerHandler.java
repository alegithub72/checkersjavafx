/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.board.event;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.pieces.SpritePiece;
import sa.gameboard.core.events.ComandEvent;

import java.util.logging.Level;

/**
 *
 * @author  Alessio Sardaro
 */
public class EventSelectionPlayerHandler implements EventHandler<MouseEvent> {

	FXBoard fxb;
    SpritePiece p;

    public EventSelectionPlayerHandler(FXBoard board,SpritePiece p) {
    	super();
        this.fxb = board;
        this.p=p;
    }


    @Override
    public void handle(MouseEvent event) {
    	
		if (event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getButton()==MouseButton.PRIMARY) {
			if (!this.fxb.isAnimationOn()) {
				if (this.fxb.getSelect() != null && this.fxb.getSelect() != p) {
					this.fxb.getSelect().setFrame(0);

				}
                //fxb.getMousePlayer().setCp("p"+p.getBoardPieceLink().getI()+p.getBoardPieceLink().getJ());

				//TODO: fare animazione up pedina!!!
				p.setFrame(1);
                try {
                    ComandEvent.ComandState state= fxb.getMousePlayer().getComandEventDispatcher().getCurrentEvent().getComandState();
                    switch (state) {
                        case CHOOSE_PIECE:
                           // fxb.getMousePlayer().deleteMoveChoose();
                            fxb.setSelect(p);
                            fxb.getMousePlayer().setMoveChoose(-1);
                            fxb.getMousePlayer().setCp("p"+p.getBoardPieceLink().getI()+p.getBoardPieceLink().getJ());

                            break;
                        default:
                            break;
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
			BCDraugthsApp.log.log(Level.INFO,"HANDLE  EventSelectionPlayer clicked id=" + p);
			event.consume();
		}
    }




}
