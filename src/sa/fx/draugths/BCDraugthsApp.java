/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths;


import java.net.URL;
import java.util.Hashtable;
import java.util.Map;
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
 * @author  Alessio Sardaro
 */
public class BCDraugthsApp extends Application {

    private Game game;
    PresentationScreen startScreen;
    RecordScreen recordScreen;
    static Map<String,AudioClip> effettiMap=new Hashtable();
    private AnimationPedinaMove anim;
    FXBoard fxb;
    public static boolean debug;
    public static boolean loadScenario;
    public static boolean tracepath;
    public static java.util.logging.Logger  log=   Logger.getAnonymousLogger();
    //PathTransition pathTransition;
    Group root;
    //RotateTransition rotateTransition;
    public String confirmCommand;
    Stage primaryStage;
    ImageView description;
    static double scale = 0.78;
    int level;
	public static final String MUSIC_SIGLA="270545_jingle-win-01.wav";
	public static final String MUSIC_CELEBRATION="270545_jingle-win-01.wav";
	public static final String EFFECT_HEY="416507_hey.wav";





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

        if(System.getProperty("checkers.debug")!=null)
            debug="true".equals(""+System.getProperty("checkers.debug"));
        else debug=false;
        //log.info("System.getProperty(\"checkers.loadScenario\")="+System.getProperty("checkers.loadScenario"));
        if(System.getProperty("checkers.loadScenario")!=null)
        	loadScenario="true".equals(""+System.getProperty("checkers.loadScenario"));
        else loadScenario=false;
        if(System.getProperty("checkers.tracepath")!=null)
        	tracepath="true".equals(""+System.getProperty("checkers.tracepath"));
        else tracepath=false;        

		System.setProperty("java.util.logging.SimpleFormatter.format",  "%2$s   %4$s: %5$s %n");	
		//java.util.logging.SimpleFormatter.format
		if(debug) 
			log.setLevel(Level.INFO);  
		else 
			log.setLevel(Level.OFF);
		//log.info("System.getProperty(\"checkers.level\")="+System.getProperty("checkers.level"));
        if(System.getProperty("checkers.level")!=null)
            level=Integer.valueOf(System.getProperty("checkers.level"));
        else level=1;
        
        log.info("level system="+level);
        log.info("level system="+debug);


        


    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //TODO: gestire gli screen di inizio gioco non  con il background del gioco,
        // possibile di rinominare ed usare Background class per questo ruolo di screen before play
        initDama();
        root=new Group();
        drawStartScreen();
        Scene scene = new Scene(root,startScreen.getWidthScreen()
                ,startScreen.getHeightScreen()-12, Color.BLACK);
       primaryStage.setTitle("Checkers Invader");
       primaryStage.setScene(scene);
       primaryStage.getIcons().add(new Image("cinvaders.png"));
       primaryStage.setResizable(false);
       
       //primaryStage.initStyle(StageStyle.TRANSPARENT);
       primaryStage.show();
       

    }
   public void levelUp(int level,int point)throws Exception{
            root.getChildren().remove(fxb);

            log.info("system level="+level);
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

  public  static   void  playMedia(String sound,int count) {

        	Thread thread=new Thread(new Runnable() {
				
				@Override
				public void run() {
			        try {
			        	stopMedia(sound);
			        	effettiMap.remove(sound);
						 URL url = this.getClass().getClassLoader().getResource(sound);
						 if(url!=null) {
							 AudioClip clip= new AudioClip(url.toString());
							 clip.setCycleCount(count);
							 clip.play();
							effettiMap.put(sound, clip);
						}

					} catch (Exception   e) {
						e.printStackTrace();
					}
				}
			});
        	thread.start();

    }
public   static  void stopMedia(String sound) {
        try {
			AudioClip clip= effettiMap.get(sound);
			if(clip!=null)clip.stop();
		} catch (Exception e) {

			e.printStackTrace();
		}

    }
  public void drawRecordScreen(int points){

	  
        root.getChildren().remove(fxb);
        BCDraugthsApp.log.info("index of fxb ="+root.getChildren().contains(fxb));
        //fxb=new FXBoardClass(0, this);

        recordScreen = new RecordScreen();
        char[] recordName="AAA".toCharArray();

        if(!recordScreen.addRecordPlayer(new String(recordName), points)) {
	        recordScreen.drawTableRecord();
	        root.getChildren().add(recordScreen);
        	
          recordScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {
	           @Override
	           public void handle(MouseEvent event) {
	           	if(event.getButton()==MouseButton.PRIMARY) resetGame();
	           	}
          	});     
        	
        }else {

		        recordScreen.drawTableRecord();
		        root.getChildren().add(recordScreen);
		        recordScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {
		        	int only3=0;
		        	char name='A';
					@Override
					public void handle(MouseEvent event) {
		            	if(event.getButton()==MouseButton.SECONDARY) {
		            	if(only3<=2) {
		            		 recordName[only3]=name;
							 only3++;
							 name='A';
							 recordScreen.addRecordPlayer(new String(recordName), points);
							 recordScreen.drawTableRecord();
		            	}else {
		                    	resetGame();
		  						recordScreen.saveRecordPlayers();
		  						event.consume();
		  						if(debug) log.info("----FINE------>"+name+"-->"+only3+"----->"+new String(recordName));
		        			}
						
		            	if(debug) log.info("---------->"+name+"-->"+only3+"----->"+new String(recordName));

		            	}else {
		            		
		            		if(only3<=2) {
		            			name++;
		            			if(name>'Z') name='A';
		            			recordName[only3]=name;
		            			
		            		}
		            		if(debug) log.info("---------->"+name+"-->"+only3+"----->"+new String(recordName));
		            		recordScreen.addRecordPlayer(new String(recordName), points);
		                    recordScreen.drawTableRecord();
		            	}
					}
		        	
		        	
				});
        }
     
     
 }
  private void  resetGame() {
		
	    try {
			//music.play();
			root.getChildren().remove(recordScreen);
			level=1;
			drawStartScreen();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
  }
  public void drawStartScreen()throws Exception{
        
      

        playMedia(MUSIC_SIGLA,AudioClip.INDEFINITE);
        root.getChildren().remove(fxb);
        fxb=new FXBoard(level,this);
        startScreen=new StartScreen();
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
  }
public void drawEndScreen()throws Exception{
            
            
            playMedia(MUSIC_CELEBRATION,1);

            
            root.getChildren().remove(fxb);      
            startScreen=new EndScreen();
            EndScreenII ii=new EndScreenII();
            root.getChildren().add(ii);
            root.getChildren().add(startScreen);

            root.setOnMouseClicked(new EventHandler<MouseEvent>() {
                boolean flip=true;
                @Override
                public void handle(MouseEvent event) {
                		if(event.getButton()==MouseButton.PRIMARY) {
                			flip=!flip;
                			startScreen.setVisibleBack(flip);
                            playMedia(EFFECT_HEY,1);

                			
                		}else {
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
