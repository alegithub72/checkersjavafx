/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths;

import sa.fx.draugths.animation.FrameAnimationTimer;
import sa.fx.draugths.animation.AnimationPedinaMove;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.ParallelTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sa.fx.draugths.screen.StartScreen;
import sa.gameboard.core.Game;

/**
 *
 * @author ale2s_000
 */
public class BCDraugthsApp extends Application {

    private Game game;

 
   // 
    StartScreen startScreen;
    //private Group boardGroup;

    private double widthScreen;
    private double heightScreen;

    private ParallelTransition pt;
    private AnimationPedinaMove anim;

    FXBoardClass fxb;

    public FXBoardClass getFxb() {
        return fxb;
    }
    public int level;

    //PathTransition pathTransition;

    Group root;












    //RotateTransition rotateTransition;
    public String confirmCommand;

    Stage primaryStage;
  

    ImageView description;
    boolean debug;
    static double scale = 0.78;









    void waitAnimation() {
        try {
            if (anim != null) {
                anim.wait();
            }
        } catch (InterruptedException ex) {
            //   Logger.getLogger(FXAIPlayer1.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        ///if(anim!=null)
        // while(anim.getStatus()==Animation.Status.RUNNING)System.out.println("runnigggg");

    }









    
    public void initDama() {


            root=new Group();

        

            
            

        


    }

    @Override
    public void start(Stage primaryStage) {
        //TODO: gestire gli screen di inizio gioco non  con il background del gioco,
        // possibile di rinominare ed usare Background class per questo ruolo di screen before play
        debug = true;
        initDama();
        this.primaryStage = primaryStage;
        AudioClip music = buildMedia(FrameAnimationTimer.MUSIC);
        music.setCycleCount(AudioClip.INDEFINITE);
        music.play();
            fxb=new FXBoardClass(level,this);
        startScreen=new StartScreen(level,fxb);
        root.getChildren().add(startScreen);
        
        Scene scene = new Scene(root,startScreen.getWidthScreen()
                ,startScreen.getHeightScreen(), Color.BLACK);
        
        
        startScreen.drawScreen(level);
        startScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent event) {
                    level++;
                    music.stop();
                   
                    fxb.setLevel(level);
                    System.out.println("level="+level);
                    fxb.startLevel();
                    root.getChildren().remove(startScreen);
                    root.getChildren().add(fxb);
                    //scene.setRoot(fxb);

                    // backGround.middleScreen(level);
                    //restartGame();
        if (level== 1) {
           // backGround.middleScreen(level);
           // renderTable();
            primaryStage.setTitle("Grafic BC Dama:EARTH");
        } else {
            
            primaryStage.setTitle("Grafic BC Dama:MARS");
        }
                
                event.consume();
            }
        });

        primaryStage.setScene(scene);
        
       
        primaryStage.show();

    }





    public void playMousePlayer() {
        game.makeHumanMove();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);

    }





    public void restartGame() {
        try {
            stop();
            start(primaryStage);
          
        } catch (Exception ex) {
            Logger.getLogger(BCDraugthsApp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    AudioClip buildMedia(String sound) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(sound);
        return new AudioClip(url.toString());

    }


}
