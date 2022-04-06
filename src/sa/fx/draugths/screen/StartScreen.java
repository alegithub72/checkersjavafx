/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.screen;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Alessio Sardaro
 */
public class StartScreen extends PresentationScreen {



	public StartScreen() {
		super("startScreen2.png");

	}

	public void drawScreen(String image) {
		Image images = null;
		images = new Image(image);
		c.getGraphicsContext2D().setEffect(null);
		c.getGraphicsContext2D().drawImage(images, 0, 0,widthScreen,heightScreen);

		Font f = new Font(null, 20);
		start = new Text("- CLICK TO START -");
		start.setX((widthScreen - 180) / 2);
		start.setY(heightScreen / 2 + 50);

		start.setFont(f);
		start.setFill(Color.ANTIQUEWHITE);
		getChildren().add(start);
		Animation t = new Transition() {
			{
				setCycleCount(Animation.INDEFINITE);
				setCycleDuration(Duration.millis(500));

			}

			@Override
			protected void interpolate(double frac) {
				// System.out.println("color="+Color.WHITE.interpolate(Color.BLACK, frac));
				start.setFill(Color.WHITE.interpolate(Color.BLACK, frac));
			}
		};
		t.play();
	}

}
