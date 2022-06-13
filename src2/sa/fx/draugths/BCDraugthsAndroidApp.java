/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths;

import static com.gluonhq.charm.glisten.application.AppManager.HOME_VIEW;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.gluonhq.attach.util.Platform;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sa.fx.draugths.utility.ScaleFactor;
import sa.fx.draugths.utility.SoundInterface;
import sa.fx.draugths.utility.SoundPlay;
import sa.fx.draugths.utility.SoundPlayMobile;
import sa.gameboard.core.Game;

/**
 *
 * @author Alessio Sardaro
 */
public class BCDraugthsAndroidApp extends Application {
	private final AppManager appManager = AppManager.initialize(this::postInit);
	private Game game;

	static public SoundInterface soundplay = null;

	FXBoard fxb;
	public static boolean debug;
	public static boolean loadScenario;
	public static boolean tracepath;
	public static java.util.logging.Logger log = Logger.getAnonymousLogger();
	double scaleX;
	double scaleY;
	public String confirmCommand;
//	Stage primaryStage;
//	Scene sceneX;
	static double scale = 0.78;
	int level;

	private static SoundInterface getSoundInterfaceInstance() {
		if(Platform.isDesktop())
			 return SoundPlay.getSoundInterfaceInstance();
		else
			return SoundPlayMobile.getSoundInterfaceInstance();

	}

	@Override
	public void init() {
		// Scene sceneW=this.sceneX;
		appManager.addViewFactory(HOME_VIEW, () -> {
			VBox controls = new VBox(15.0);
			controls.setAlignment(Pos.CENTER);

			View view = new View(controls) {
				@Override
				protected void updateAppBar(AppBar appBar) {
					appBar.setStyle("text-align: center");
					appBar.applyCss();
					appBar.setTitleText("Gluon Mobile FX Game:Checkers Invader ");
				}
			};

			Button button = new Button("Start the Game!");
			ImageView icon = new ImageView("cinvaders.png");
			icon.setFitHeight(16);
			icon.setFitWidth(16);
			button.setGraphic(icon);
			button.setOnAction(e -> {
				fxb.setView(view);
				Background focusBackground = new Background(
						new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
				view.setBackground(focusBackground);
				//SubScene subScene=new SubScene(fxb.getRoot(), 400, 400);
				//view.setCenter(subScene);
				view.setCenter(fxb.getRoot());

				view.getAppManager().getAppBar().setVisible(false);
				view.setVisible(true);
				// sceneX.setRoot(fxb.getRoot());
			});
			controls.getChildren().add(button);
			return view;
		});
	}

	private void postInit(Scene scene) {
		// Swatch.LIGHT_GREEN.assignTo(scene);
		initDama();
		FXBoard.SoundSystem = getSoundInterfaceInstance();
		fxb = new FXBoard(level);

		fxb.drawStartScreen();
		scene.setFill(Color.BLACK);
		System.out.println("1)" + scene.getHeight() + "," + scene.getWidth());
		Screen.getPrimary().getVisualBounds().getWidth();
//		scaleX = Screen.getPrimary().getVisualBounds().getWidth() / 800;
//		scaleY = Screen.getPrimary().getVisualBounds().getHeight() / 850;
		ParallelCamera camera=new ParallelCamera();

		scaleX = scene.getWidth() / 800;
		scaleY = scene.getHeight() / 850;
		FXBoard.scaleRoot=new ScaleFactor(scaleX,scaleY);
		fxb.scale();
		// sceneX=scene;

	}


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

	@Override
	public void start(Stage stage) {
		stage.getIcons().add(new Image("cinvaders.png"));
		// this.primaryStage=stage;
		appManager.start(stage);

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
