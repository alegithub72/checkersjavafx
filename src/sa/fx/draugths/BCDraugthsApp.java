/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths;

import static com.gluonhq.charm.glisten.application.AppManager.HOME_VIEW;

import java.net.URL;
import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gluonhq.attach.audio.Audio;
import com.gluonhq.attach.audio.AudioService;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.Icon;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sa.fx.draugths.animation.AnimationPedinaMove;
import sa.fx.draugths.screen.EndScreen;
import sa.fx.draugths.screen.EndScreenII;
import sa.fx.draugths.screen.PresentationScreen;
import sa.fx.draugths.screen.RecordScreen;
import sa.fx.draugths.screen.StartScreen;
import sa.gameboard.core.Game;
/**
 *
 * @author Alessio Sardaro
 */
public class BCDraugthsApp extends Application {
	private final AppManager appManager = AppManager.initialize(this::postInit);
	private Game game;
	PresentationScreen startScreen;

	RecordScreen recordScreen;
	static Map<String, AudioClip> effettiMap = new Hashtable();
	static Map<String, Audio> effettiAndMap = new Hashtable();
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
	public static final String MUSIC_SIGLA = "muppet.wav";
	public static final String MUSIC_CELEBRATION = "270545_jingle-win-01.wav";
	public static final String EFFECT_HEY = "416507_hey.wav";

	void waitAnimation() {
		try {
			if (anim != null) {
				anim.wait();
			}
		} catch (InterruptedException ex) {
			// Logger.getLogger(FXAIPlayer1.class.getName()).log(Level.SEVERE, null, ex);
			ex.printStackTrace();
		}
		/// if(anim!=null)
		// while(anim.getStatus()==Animation.Status.RUNNING)System.out.println("runnigggg");

	}


    @Override
    public void init() {
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
            ImageView icon=new ImageView("cinvaders.png");
            icon.setFitHeight(16);
            icon.setFitWidth(16);
            button.setGraphic(icon);
            button.setOnAction(e -> {
        		Background focusBackground = new Background( new BackgroundFill( Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY ));
        		view.setBackground(focusBackground);
        		view.setCenter(root);
        		view.getAppManager().getAppBar().setVisible(false);
        		view.setVisible(true);
            });
            controls.getChildren().add(button);
            return view;
        });
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
        appManager.start(stage);
    }

    
    
    private void postInit(Scene scene){
        //Swatch.LIGHT_GREEN.assignTo(scene);
		initDama();
		root = new Group();
		drawStartScreen();
		scene.setFill(Color.BLACK);
		System.out.println("1)" + scene.getHeight() + "," + scene.getWidth());
		double scaleX = scene.getWidth() / 800;
		double scaleY = scene.getHeight() / 850;
		if(scaleX<1) {
			root.setScaleX(scaleX);
			double transalteX = ((1d - scaleX) * 800) / 2;
			root.setTranslateX(-transalteX);
			System.out.println("1translate)" + transalteX );
		}
		
		if(scaleY<1) {
			root.setScaleY(scaleY);
			double transalteY = ((1d - scaleY) * 800) / 2;
			root.setTranslateY(-transalteY);
			System.out.println("1translate)"+ transalteY);
		}
		System.out.println("1scale)" + scaleX + "," + scaleY);


		System.out.println("2)" + scene.getHeight() + "," + scene.getWidth());


	


	}

	public void levelUp(int level, int point) throws Exception {
		root.getChildren().remove(fxb);

		log.info("system level=" + level);
		fxb = new FXBoard(level, this);
		fxb.startLevel(point);

		root.getChildren().remove(startScreen);
		root.getChildren().add(fxb);

	}

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

	public static void playMedia(String sound, int count) {

		Optional<AudioService> opt = AudioService.create();

		URL url = BCDraugthsApp.class.getClassLoader().getResource(sound);
		System.out.println("--->" + url);
	
		Thread th=new Thread(new Runnable() {
			
			@Override
			public void run() {
				AudioService.create().ifPresentOrElse(service -> {
					BCDraugthsApp.log.info("--->present service");
					if(effettiAndMap.get(url)==null)
					service.loadMusic(url).ifPresent(audio -> {
						effettiAndMap.put(sound,audio);
						BCDraugthsApp.log.info("--->present audio");
						audio.play();
					});
					else {
						Audio audioTmp= effettiAndMap.get(url);
						audioTmp.play();
					}

				}, new Runnable() {

					@Override
					public void run() {
						try {
							stopMedia(sound);
							if(effettiMap.get(sound)==null) {
							URL url = this.getClass().getClassLoader().getResource(sound);
							if (url != null) {
								AudioClip clip = new AudioClip(url.toString());
								clip.setCycleCount(count);
								clip.play();
								effettiMap.put(sound, clip);
							}
							}else {
								AudioClip clipTmp=effettiMap.get(sound);
								clipTmp.play();
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
			}
		});
		th.start();


	}

	public static void stopMedia(String sound) {

		try {
			Audio audio = effettiAndMap.get(sound);
			if (audio != null) {
				audio.stop();
			}
			AudioClip clip = effettiMap.get(sound);
			if (clip != null)
				clip.stop();
		} catch (Exception e) {

			e.printStackTrace();
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
					if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() > 1) {
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
			// music.play();
			root.getChildren().remove(recordScreen);
			level = 1;
			drawStartScreen();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void drawStartScreen()  {
		try {
			playMedia(MUSIC_SIGLA, AudioClip.INDEFINITE);
			root.getChildren().remove(fxb);
	
				fxb = new FXBoard(level, this);
	
			startScreen = new StartScreen();
			root.getChildren().add(startScreen);
			startScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {
	
				@Override
				public void handle(MouseEvent event) {
					stopMedia(MUSIC_SIGLA);
					fxb.startLevel(level);
					root.getChildren().remove(startScreen);
					root.getChildren().add(fxb);
					event.consume();
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public void drawEndScreen() throws Exception {

		playMedia(MUSIC_CELEBRATION, 1);

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
					playMedia(EFFECT_HEY, 1);

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
