package sa.fx.draugths;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import static com.gluonhq.charm.glisten.application.AppManager.HOME_VIEW;

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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sa.fx.draugths.utility.SoundPlay;
/**
 *
 * @author Alessio Sardaro
 */
public class BCDraugthsAppMobile extends BCDraugthsApp {
	private final AppManager appManager = AppManager.initialize(this::postInit);




    @Override
    public void init() {
        appManager.addViewFactory(HOME_VIEW, () -> {
            VBox controls = new VBox(15.0);
            controls.setAlignment(Pos.CENTER);
            soundplay=SoundPlay.getSoundInterfaceInstance();

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
