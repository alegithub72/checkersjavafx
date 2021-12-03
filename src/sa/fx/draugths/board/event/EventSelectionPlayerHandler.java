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
import sa.fx.draugths.sprite.SpritePiece;

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
				fxb.getMousePlayer().deleteMoveChoose();
				fxb.setSelect(p);
				p.setFrame(1);
				fxb.getMousePlayer().choosePiece();

			}
			BCDraugthsApp.log.info("HANDLE  EventSelectionPlayer clicked id=" + p);
			event.consume();
		}
    }




}
