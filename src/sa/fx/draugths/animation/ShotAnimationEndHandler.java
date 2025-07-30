package sa.fx.draugths.animation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.pieces.SpritePiece;

import java.util.logging.Level;

public class ShotAnimationEndHandler implements  EventHandler<ActionEvent>  {
	
	Sprite shot;
	SpritePiece owner;

	public ShotAnimationEndHandler(Sprite shot,SpritePiece owner) {
		super();
		this.shot = shot;
		this.owner=owner;
	}





	@Override
	public void handle(ActionEvent event) {
		BCDraugthsApp.log.log(Level.INFO,"shot:"+shot);
		owner.getFxBoard().remove(shot);
		
	}

}
