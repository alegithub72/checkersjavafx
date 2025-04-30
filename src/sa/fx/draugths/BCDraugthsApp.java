/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sa.fx.draugths.screen.PresentationScreen;
import sa.fx.draugths.screen.RecordScreen;
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
	public static java.util.logging.Logger log = Logger.getLogger("sa.fx.draugths");
	// PathTransition pathTransition;
	Group root;
	// RotateTransition rotateTransition;
	public String confirmCommand;
	Stage primaryStage;
	ImageView description;
	static double scale = 0.78;
	int level;

	public void initDama() {
		BCDraugthsApp.log.info("---->"+System.getProperty("checkers.debug"));
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

		System.setProperty("java.util.logging.SimpleFormatter.format", "%2$s   %4$s:   %5$s %6$s   %n");
		// java.util.logging.SimpleFormatter.format
		BCDraugthsApp.log.info("---->"+debug);
		if (debug) {
			Level logLevel=Level.INFO;
			log.setLevel(logLevel);
			if(log.getParent()!=null && log.getParent().getHandlers()!=null) {
				Handler[] hanlders=log.getParent().getHandlers();
					log.info("lentgth="+hanlders.length);
					for(int i=0; i<hanlders.length;i++) {
						log.info("--->"+hanlders[i]);
						if(hanlders[i]!=null)
							hanlders[i].setLevel(logLevel);
						//log.getParent().removeHandler(hanlders[i]);

				}

			}
			
		}
		else{
			Level logLevel=Level.OFF;
			log.setLevel(logLevel);
			//log.setLevel(Level.SEVERE);
		}
		//ConsoleHandler handler=new ConsoleHandler();
		//handler.setLevel(Level.FINEST);

		//log.addHandler(handler);
		
		//System.setProperty("java.util.logging.ConsoleHandler.level","FINEST");
		// log.info("System.getProperty(\"checkers.level\")="+System.getProperty("checkers.level"));
		if (System.getProperty("checkers.level") != null)
			level = Integer.valueOf(System.getProperty("checkers.level"));
		else
			level = 1;

		log.info("level system=" + level);
		log.info("level system=" + debug);
		log.info("level log=" + log.getLevel());

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
		StackPane stackPanel=new StackPane(fxb.getRoot());
		Scene scene = new Scene(stackPanel, fxb.getStartScreen().getWidthScreen(),
				fxb.getStartScreen().getHeightScreen() , Color.BLACK);

		fxb.setSceneStakPanel(stackPanel);
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
