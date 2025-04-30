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
public class EndScreen extends PresentationScreen {

	public EndScreen() {
		super("endScreen.png");
		
	}


	public void drawScreen(String image) {
		Image images = null;
		images = new Image(image);

		// TODO:do somenthing more fun

		c.getGraphicsContext2D().drawImage(images, 0, 0,widthScreen,heightScreen);

		Font f = Font.loadFont(ClassLoader.getSystemResourceAsStream("SHOWG.TTF"), 36);
		start = new Text("- RIGHT CLICK FOR ANOTHER CAMPAIGN -");
		start.setX((widthScreen) / 30);
		start.setY(heightScreen / 2 + 50);
		start.setFont(f);
		start.setFill(Color.WHITE);
		getChildren().add(start);
		Animation t = new Transition() {
			{
				setCycleCount(Animation.INDEFINITE);
				setCycleDuration(Duration.millis(500));

			}

			@Override
			protected void interpolate(double frac) {
				// BCDraugthsApp.log.info("color="+Color.WHITE.interpolate(Color.BLACK, frac));
				start.setFill(Color.WHITE.interpolate(Color.BLACK, frac));
			}
		};
		t.play();
	}

    
}
