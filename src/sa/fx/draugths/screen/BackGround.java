/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.screen;



import java.net.URL;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.ShotDistanceFrameAnimationTimer;
import sa.fx.draugths.animation.ScreenPause;
import sa.fx.draugths.animation.ScreenPauseInterface;


/**
 *
 * @author  Alessio Sardaro
 */
public class BackGround extends Parent implements ScreenPauseInterface {

    double wBackground=800;
    double hBackgroud=800;
    static public double hPointTable=30;
    Canvas c;
    Text scoreLabel;
    Text score;
    Text levelLabel;
    int point;

    public int getPoint() {
        return point;
    }
    //Text start;
    FXBoard board; 
    int level;

    public double getWBackground() {
        return wBackground;
    }

   // public void setWBackground(double widthScreen) {
   //     this.wBackground = widthScreen;
    //}

    public double getHBackground() {
        return hBackgroud;
    }

   // public void setHBackground(double heightScreen) {
   //     this.hBackgroud = heightScreen;
    //}
    public GraphicsContext getBackgroundGrafic(){
        return c.getGraphicsContext2D();
    }
    
    public BackGround(int level,FXBoard board,int point){

        c=new Canvas(wBackground, hBackgroud+hPointTable+10);
        getChildren().add(c);
        this.board=board;
        this.level=level;
        this.point=point;

    }

    public void drawBackGround(){
            drawScoreBar();
            Image empty=null;
            Image full=null;
        if (level==1) {
        	
              //images = new Image("forestBoardsvg.png");
               empty=new Image("forestaBoxEmpty.png");
               full=new Image("forestaBox.png");
              

        }else if(level==2){
        	
            empty=new Image("desertBoxEmpty.png");
            full=new Image("desertBox.png");

        }else if(level==3){
            
            full=new Image("iceBoxEmpty.png");
            empty=new Image("iceBox.png");
            
        }
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                    	c.getGraphicsContext2D().drawImage(empty, i*100, (j*100)+30); 	
                    } else if (j % 2 != 0) {
                    	c.getGraphicsContext2D().drawImage(full, i*100, (j*100)+30); 	
                    }
                } else if (i % 2 != 0) {
                    if (j % 2 != 0) {
                    	 c.getGraphicsContext2D().drawImage(empty, i*100, (j*100)+30); 
                    } else if (j % 2 == 0) {
                    	c.getGraphicsContext2D().drawImage(full, i*100, (j*100)+30); 	
                    }
                }

            }

        }
        


    
    }
    void drawScoreBar() {
        
 
 
        c.getGraphicsContext2D().setFill(Color.rgb(255, 255, 128));
        c.getGraphicsContext2D().setLineWidth(4);
        c.getGraphicsContext2D().fillRect(0, 0, wBackground, hPointTable );
        c.getGraphicsContext2D().setStroke(Color.rgb(0, 204, 102));
        c.getGraphicsContext2D().strokeRect(0, 0, wBackground, hPointTable );
        scoreLabel = new Text("SCORE:");
        Font f = new Font(null, hPointTable);
            if (level <= 1) {
                score = new Text("0");
            } else {
                score = new Text("" + this.point);
            }
            
           

            score.setX(135);
            score.setY(25);
            scoreLabel.setX(20);
            scoreLabel.setY(25);
            score.setFont(f);
            scoreLabel.setFont(f);
            score.setFill(Color.rgb(0, 204, 102));
            scoreLabel.setFill(Color.rgb(0, 204, 102));
            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(20.0);
            dropShadow.setOffsetX(0.0);
            dropShadow.setOffsetY(0.0);

            scoreLabel.setEffect(dropShadow);
            score.setEffect(dropShadow);

            levelLabel = new Text("Level:" + level);
            levelLabel.setX(wBackground - 180);
            levelLabel.setY(25);
            levelLabel.setFont(f);
            levelLabel.setFill(Color.rgb(0, 204, 102));
            levelLabel.setEffect(dropShadow);

            getChildren().add(score);
            getChildren().add(scoreLabel);
            getChildren().add(levelLabel);        
            

    }
    public void updatePoint(int value) {
        this.point = this.point + value;
        score.setText("" + this.point);
        
        ClassLoader classLoader = getClass().getClassLoader();
       URL url=null;
       
      //URL url2=classLoader.getResource("pointUp.mp3"); 
      

      //if(value>15) url=classLoader.getResource("simpletone.mp3"); 
      //else url=classLoader.getResource("pointUp.mp3"); 
      url=classLoader.getResource(ShotDistanceFrameAnimationTimer.ACHW); 
      
      AudioClip a=  new AudioClip(url.toString());
      a.setVolume(1);
      a.setPriority(-1);
      a.setPan(-1);
      a.play();
      
      Animation t=new Transition() {
                {
                    setCycleCount(1);
                    setCycleDuration(Duration.millis(500));
                    
                }
                
                @Override
                protected void interpolate(double frac) {
                    //System.out.println("color="+Color.WHITE.interpolate(Color.BLACK, frac));
                       score.setFill(Color.WHITE.interpolate(Color.rgb(0, 204, 102), frac));
                       score.setScaleX(2-frac);
                       score.setScaleY(2-frac);
                       
                }
            };
            t.play();
    
        
    }

    public void resetPoint() {
        this.point = 0;
        score.setText("" + this.point);
    }
    public void setVisibleBack(boolean b){
        c.setVisible(b);
                
    }
    
    public void middleScreen(){

            Image imagesDesc = null;
            if (level == 1) {
                imagesDesc = new Image("desc1.png");
            } else if (level == 2) {
                imagesDesc = new Image("desc2.png");
            }else if (level >= 3) {
                imagesDesc = new Image("desc2.png");
            }
            c.getGraphicsContext2D().fillRect(0, 0, wBackground, wBackground);
            c.getGraphicsContext2D().drawImage(imagesDesc, 0, 0);
            ScreenPause p=new ScreenPause(1.5d,this);
            p.play();
            
        
    
    }
    public void goAhead() throws Exception{
        drawBackGround();
        board.drawBoard();
    }
}
