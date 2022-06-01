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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sa.fx.draugths.animation.AnimationPedinaMove;
import sa.fx.draugths.screen.EndScreen;
import sa.fx.draugths.screen.EndScreenII;
import sa.fx.draugths.screen.PresentationScreen;
import sa.fx.draugths.screen.RecordScreen;
import sa.fx.draugths.screen.StartScreen;
import sa.fx.draugths.utility.SoundInterface;
import sa.fx.draugths.utility.SoundPlay;
import sa.gameboard.core.Game;

/**
 *
 * @author Alessio Sardaro
 */
public class BCDraugthsApp extends Application {

	private Game game;
	PresentationScreen startScreen;
	RecordScreen recordScreen;
	public static SoundInterface soundPlay;
	private AnimationPedinaMove anim;
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
		soundPlay = SoundPlay.getSoundInterfaceInstance();

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
		Scene scene = new Scene(fxb.getRoot(), fxb.getStartScreen().getWidthScreen() - 12,
				fxb.getStartScreen().getHeightScreen() - 18, Color.BLACK);


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

	public void playMousePlayer() {
		game.makeHumanMove();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void restartGame() {
		try {
			stop();
			start(primaryStage);

		} catch (Exception ex) {
			log.info(ex.getMessage());
		}

	}

	public void drawRecordScreen(int points) {

		root.getChildren().remove(fxb);
		BCDraugthsApp.log.info("index of fxb =" + root.getChildren().contains(fxb));
		// fxb=new FXBoardClass(0, this);

		recordScreen = new RecordScreen();
		char[] recordName = "AAA".toCharArray();

		if (!recordScreen.addRecordPlayer(new String(recordName), points)) {
			recordScreen.drawTableRecord();
			root.getChildren().add(recordScreen);

			recordScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (event.getButton() == MouseButton.PRIMARY)
						resetGame();
				}
			});

		} else {

			recordScreen.drawTableRecord();
			root.getChildren().add(recordScreen);
			recordScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {
				int only3 = 0;
				char name = 'A';

				@Override
				public void handle(MouseEvent event) {
					if (event.getButton() == MouseButton.SECONDARY) {
						if (only3 <= 2) {
							recordName[only3] = name;
							only3++;
							name = 'A';
							recordScreen.addRecordPlayer(new String(recordName), points);
							recordScreen.drawTableRecord();
						} else {
							resetGame();
							recordScreen.saveRecordPlayers();
							event.consume();
							if (debug)
								log.info("----FINE------>" + name + "-->" + only3 + "----->" + new String(recordName));
						}

						if (debug)
							log.info("---------->" + name + "-->" + only3 + "----->" + new String(recordName));

					} else {

						if (only3 <= 2) {
							name++;
							if (name > 'Z')
								name = 'A';
							recordName[only3] = name;

						}
						if (debug)
							log.info("---------->" + name + "-->" + only3 + "----->" + new String(recordName));
						recordScreen.addRecordPlayer(new String(recordName), points);
						recordScreen.drawTableRecord();
					}
				}

			});
		}

	}

	private void resetGame() {

		try {
			root.getChildren().remove(recordScreen);
			level = 1;
			drawStartScreen();
		} catch (Exception e) {
			log.info(e.toString());
		}

	}

	public void drawStartScreen() throws Exception {

		soundPlay.playSoundLoop(SoundInterface.MUSIC_SIGLA);
		root.getChildren().remove(fxb);
		fxb = new FXBoard(level);
		startScreen = new StartScreen();
		root.getChildren().add(startScreen);
		startScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				soundPlay.stopSound(SoundInterface.MUSIC_SIGLA);
				fxb.startLevel(level);
				root.getChildren().remove(startScreen);

				event.consume();
			}
		});
	}

	public void drawEndScreen() throws Exception {

		soundPlay.playSound(SoundInterface.MUSIC_CELEBRATION, 1);

		root.getChildren().remove(fxb);
		startScreen = new EndScreen();
		EndScreenII ii = new EndScreenII();
		root.getChildren().add(ii);
		root.getChildren().add(startScreen);

		root.setOnMouseClicked(new EventHandler<MouseEvent>() {
			boolean flip = true;

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					flip = !flip;
					startScreen.setVisibleBack(flip);
					soundPlay.playSound(SoundInterface.EFFECT_HEY, 1);

				} else {
					root.getChildren().remove(startScreen);
					root.getChildren().remove(ii);
					root.setOnMouseClicked(null);
					drawRecordScreen(level);
				}

				event.consume();
			}
		});

	}

}
