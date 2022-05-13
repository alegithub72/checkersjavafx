package sa.fx.draugths;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import static com.gluonhq.charm.glisten.application.AppManager.HOME_VIEW;

import java.net.URL;
import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;

import com.gluonhq.attach.audio.Audio;
import com.gluonhq.attach.audio.AudioService;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/**
 *
 * @author Alessio Sardaro
 */
public class BCDraugthsAppMobile extends BCDraugthsApp {
	private final AppManager appManager = AppManager.initialize(this::postInit);

	static Map<String, Audio> effettiAndMap = new Hashtable();


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


    @Override
    public void start(Stage stage) {
        appManager.start(stage);
    }


	public static void main(String[] args) {
		launch(args);
	}

}
