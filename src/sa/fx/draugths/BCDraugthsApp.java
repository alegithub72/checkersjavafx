/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths;

import java.net.URL;
import java.util.logging.StreamHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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
import sa.fx.draugths.animation.AnimationPedinaMove;
import sa.fx.draugths.animation.FrameAnimationTimer;
import sa.fx.draugths.screen.RecordScreen;
import sa.fx.draugths.screen.StartScreen;
import sa.gameboard.core.Game;

/**
 *
 * @author ale2s_000
 */
public class BCDraugthsApp extends Application {

    private Game game;
    StartScreen startScreen;
    RecordScreen recordScreen;
    AudioClip music;
    private ParallelTransition pt;
    private AnimationPedinaMove anim;
    FXBoard fxb;
    public static boolean debug;
	public static java.util.logging.Logger  log;
    //PathTransition pathTransition;
    Group root;
    //RotateTransition rotateTransition;
    public String confirmCommand;
    Stage primaryStage;
    ImageView description;
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

        debug = true;
    	log=   Logger.getAnonymousLogger();
		System.setProperty("java.util.logging.SimpleFormatter.format",  "%4$s: %5$s %n");	
		if(debug)  log.setLevel(Level.INFO);  
		else log.setLevel(Level.OFF);
        

            
            

        


    }

    @Override
    public void start(Stage primaryStage) {
        //TODO: gestire gli screen di inizio gioco non  con il background del gioco,
        // possibile di rinominare ed usare Background class per questo ruolo di screen before play
        initDama();
        root=new Group();
        drawStartScreen();
        Scene scene = new Scene(root,startScreen.getWidthScreen()
                ,startScreen.getHeightScreen(), Color.BLACK);

       primaryStage.setScene(scene);
       primaryStage.show();

    }
   public void levelUp(int level,int point){
            root.getChildren().remove(fxb);
            fxb=new FXBoard(level,this);
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

    AudioClip buildMedia(String sound) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(sound);
        return new AudioClip(url.toString());

    }

  public void drawRecordScreen(){
      
        root.getChildren().remove(fxb);
        BCDraugthsApp.log.info("index of fxb ="+root.getChildren().contains(fxb));
        //fxb=new FXBoardClass(0, this);
        recordScreen = new RecordScreen();
        root.getChildren().add(recordScreen);
        recordScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {
           
            @Override
            public void handle(MouseEvent event) {
                    music.play();
                    root.getChildren().remove(recordScreen);
                    drawStartScreen();
                event.consume();
            }
        });     
     
     
 }
  
  public void drawStartScreen(){
        
      
        music = buildMedia(FrameAnimationTimer.MUSIC);
        music.setCycleCount(AudioClip.INDEFINITE);
        music.play(); 
        root.getChildren().remove(fxb);
        fxb=new FXBoard(0,this);
        startScreen=new StartScreen(0,fxb);
        root.getChildren().add(startScreen);
        startScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent event) {
                    //level++;
                    music.stop();
                   
                    fxb.setLevel(1);
                    //System.out.println("level="+level);
                    fxb.startLevel(0);
                    root.getChildren().remove(startScreen);
                    root.getChildren().add(fxb);                
                event.consume();
            }
        });        
  }
  
}
