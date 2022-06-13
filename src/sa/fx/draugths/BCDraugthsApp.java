/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sa.fx.draugths.screen.EndScreen;
import sa.fx.draugths.screen.EndScreenII;
import sa.fx.draugths.screen.PresentationScreen;
import sa.fx.draugths.screen.RecordScreen;
import sa.fx.draugths.screen.StartScreen;
import sa.fx.draugths.utility.SoundInterface;
import sa.fx.draugths.utility.SoundPlay;

/**
 *
 * @author Alessio Sardaro
 */
public class BCDraugthsApp extends Application {


	PresentationScreen startScreen;
	RecordScreen recordScreen;

	FXBoard fxb;
	public static boolean debug;
	public static boolean loadScenario;
	public static boolean tracepath;
	public static java.util.logging.Logger log = Logger.getAnonymousLogger();
	// PathTransition pathTransition;
	Group root;
	// RotateTransition rotateTransition;
	public String confirmCommand;
	Stage primaryStage;
	ImageView description;
	static double scale = 0.78;
	int level;

	public void initDama() {

		if (System.getProperty("checkers.debug") != null)
			debug = "true".equals("" + System.getProperty("checkers.debug"));
		else
			debug = false;
		// log.info("System.getProperty(\"checkers.loadScenario\")="+System.getProperty("checkers.loadScenario"));
		if (System.getProperty("checkers.loadScenario") != null)
			loadScenario = "true".equals("" + System.getProperty("checkers.loadScenario"));
		else
			loadScenario = false;
		if (System.getProperty("checkers.tracepath") != null)
			tracepath = "true".equals("" + System.getProperty("checkers.tracepath"));
		else
			tracepath = false;

		System.setProperty("java.util.logging.SimpleFormatter.format", "%2$s   %4$s: %5$s %n");
		// java.util.logging.SimpleFormatter.format
		if (debug)
			log.setLevel(Level.INFO);
		else
			log.setLevel(Level.OFF);
		// log.info("System.getProperty(\"checkers.level\")="+System.getProperty("checkers.level"));
		if (System.getProperty("checkers.level") != null)
			level = Integer.valueOf(System.getProperty("checkers.level"));
		else
			level = 1;

		log.info("level system=" + level);
		log.info("level system=" + debug);


	}

	private static SoundInterface getSoundInterfaceInstance() {

		return SoundPlay.getSoundInterfaceInstance();

	}

	@Override
	public void start(Stage primaryStage) {
		initDama();
		FXBoard.SoundSystem = getSoundInterfaceInstance();
		fxb = new FXBoard(level);

		fxb.drawStartScreen();
//		SubScene subScene=new SubScene(fxb.getRoot(), 400, 400);
//		Group superRoot=new Group();
//		superRoot.getChildren().add(subScene);
		BorderPane borderPane=new BorderPane(fxb.getRoot());
		Scene scene = new Scene(borderPane, fxb.getStartScreen().getWidthScreen() - 12,
				fxb.getStartScreen().getHeightScreen() - 18, Color.BLACK);

		fxb.setView(borderPane);
		primaryStage.setTitle("Checkers Invader");
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("cinvaders.png"));
		primaryStage.setResizable(false);

		// primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.show();
	}

	/*
	 * @Override public void start(Stage primaryStage) throws Exception{
	 * 
	 * initDama(); root=new Group();
	 * 
	 * drawStartScreen(); Scene scene = new
	 * Scene(root,startScreen.getWidthScreen()-12 ,startScreen.getHeightScreen()-18,
	 * Color.BLACK); primaryStage.setTitle("Checkers Invader");
	 * primaryStage.setScene(scene); primaryStage.getIcons().add(new
	 * Image("cinvaders.png")); primaryStage.setResizable(false);
	 * 
	 * //primaryStage.initStyle(StageStyle.TRANSPARENT); primaryStage.show();
	 * 
	 * 
	 * }
	 */



	public static void main(String[] args) {
		launch(args);
	}





	@Override
	public void stop() throws Exception {
		if(FXBoard.SoundSystem!=null)
			FXBoard.SoundSystem.stopExecutor();
		super.stop();
	}

}
