package sa.fx.draugths.animation;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.sprite.Sprite;
import sa.fx.draugths.sprite.SpritePiece;

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
		BCDraugthsApp.log.info("shot:"+shot);
		owner.getFxBoard().remove(shot);
		
	}

}
