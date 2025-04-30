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
import java.util.Properties;
import sa.fx.draugths.BCDraugthsApp;
import java.io.IOException;
import java.util.logging.Level;

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
		c.getGraphicsContext2D().setFill(Color.BLACK);
		c.getGraphicsContext2D().fillRect(0,0,widthScreen+BackGround.scrollW,heightScreen+BackGround.scrollH);
		c.getGraphicsContext2D().drawImage(images, 0, 0,widthScreen,heightScreen);

		Font f2 = new Font(null, 10);
		Text version = new Text("version:"+prop.getProperty("checkers.version"));
		version.setX((widthScreen-150 ) / 2);
		version.setY(heightScreen-50 );
		
		version.setFont(f2);
		version.setFill(Color.ANTIQUEWHITE);
		
		Font f = new Font(null, 20);
		start = new Text("- CLICK TO START -");
		start.setX((widthScreen - 180) / 2);
		start.setY(heightScreen / 2 + 50);
		
		start.setFont(f);
		start.setFill(Color.ANTIQUEWHITE);
		getChildren().add(start);
				getChildren().add(version);
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
