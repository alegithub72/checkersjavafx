/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.screen;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Alessio Sardaro
 */
public class EndScreenII extends PresentationScreen {

	public EndScreenII() {
		super("endScreenII.png");
		
	}


	public void drawScreen(String image) {
		Image images = null;
		images = new Image(image);

		// TODO:Eliminare questa classe

		c.getGraphicsContext2D().drawImage(images, 0, 0,widthScreen,heightScreen);

	}

    
}
