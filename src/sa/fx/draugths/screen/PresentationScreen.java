package sa.fx.draugths.screen;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;
import sa.fx.draugths.BCDraugthsApp;

public abstract class PresentationScreen extends Parent {

	protected double widthScreen;
	protected double heightScreen;
	protected Canvas c;
	protected Text start;
	protected int level;
	protected Properties prop;
	
	private void loadProperties() {
    	prop= new Properties();
    	try {
			prop.load(ClassLoader.getSystemClassLoader().getResourceAsStream("version.properties"));
		} catch (IOException e) {
			BCDraugthsApp.log.log(Level.SEVERE,"Exception:",e);
			e.printStackTrace();
		}
		
	}
	protected PresentationScreen(String image) {
		super();
		c = new Canvas(800, 840);

		getChildren().add(c);
		widthScreen = 800;
		heightScreen = 840;
		this.level = 0;
		loadProperties();
		drawScreen(image);

	}
	protected PresentationScreen(int level,String image) {
		super();
		c = new Canvas(800, 840);
		getChildren().add(c);
		widthScreen = 800;
		heightScreen = 840;
		this.level = level;
		drawScreen(image);

	}
	public double getWidthScreen() {
		return widthScreen;
	}

	public void setWidthScreen(double widthScreen) {
		this.widthScreen = widthScreen;
	}

	public double getHeightScreen() {
		return heightScreen;
	}

	public void setHeightScreen(double heightScreen) {
		this.heightScreen = heightScreen;
	}

	public GraphicsContext getBackgroundGrafic() {
		return c.getGraphicsContext2D();
	}

	public void setVisibleBack(boolean b) {
		c.setVisible(b);
	
	}
	public abstract void drawScreen(String image);
}