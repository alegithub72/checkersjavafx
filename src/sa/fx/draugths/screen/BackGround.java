/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.screen;



import java.io.InputStream;

import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.ScreenPause;
import sa.fx.draugths.animation.ScreenPauseInterface;
import sa.fx.draugths.utility.SoundInterface;


/**
 *
 * @author  Alessio Sardaro
 */
public class BackGround extends Parent implements ScreenPauseInterface {

    public static double wBackground=800;
    public static double hBackgroud=800;
    public double hBackgroundTot;
    public static  double hPointTable=30;
    public static int LVL_JUNGLE=1,
    		LVL_DESERT=2,
    		LVL_MOUNTAIN=3,
    		LVL_FOREST=4,
    		LVL_SEA=5,
    		LVL_POLE=6,
    		LVL_CITY=7,
    		LVL_SKY=8,    		
    		LVL_MOON=9;


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
        this.hBackgroundTot=hBackgroud+hPointTable+10;
        c=new Canvas(wBackground, hBackgroundTot);

        getChildren().add(c);
        this.board=board;
        this.level=level;
        this.point=point;

    }

	public void drawBackGround() {
		drawScoreBar();
		Image[] empty = new Image[10];
		Image[] full = new Image[10];
		BCDraugthsApp.log.info("Level:" + level + " modulo:" + level % FXBoard.MAX_LEVEL);

	if (FXBoard.levelWave(level) == LVL_JUNGLE) {
			loadTiles("giungla", full);
			loadTiles("giunglaEmpty", empty);

		} else if (FXBoard.levelWave(level) == LVL_DESERT) {

			loadTiles("desertBoxEmpty", empty);
			loadTiles("desertBox", full);

		} else if (FXBoard.levelWave(level) == LVL_POLE) {
			loadTiles("iceBoxEmpty", full);
			loadTiles("iceBox", empty);

		} else if (FXBoard.levelWave(level) == LVL_FOREST) {

			// images = new Image("forestBoardsvg.png");
			loadTiles("forestaBoxEmpty", empty);
			loadTiles("forestaBox", full);

		} else if (FXBoard.levelWave(level) == LVL_MOUNTAIN) {

			// images = new Image("forestBoardsvg.png");
			loadTiles("montagnaEmpty", empty);
			loadTiles("montagna", full);

		} else if (FXBoard.levelWave(level) == LVL_SEA) {

			// images = new Image("mare.png");
			loadTiles("mareEmpty", empty);
			loadTiles("mare", full);

		} else if (FXBoard.levelWave(level) == LVL_MOON) {

			// images = new Image("mare.png");
			loadTiles("lunaEmpty", empty);
			loadTiles("luna", full);

		} else if (FXBoard.levelWave(level) == LVL_SKY) {

			// images = new Image("mare.png");
			loadTiles("cieloEmpty", empty);
			loadTiles("cielo", full);

		} else if (FXBoard.levelWave(level) == LVL_CITY) {

			// images = new Image("mare.png");
			loadTiles("cittaEmpty", empty);
			loadTiles("citta", full);

		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				if (i % 2 == 0) {
					if (j % 2 == 0) {
						int random = (int) (10 * Math.random()) / 2;
						if (empty[random] != null)
							c.getGraphicsContext2D().drawImage(empty[random], i * 100, (j * 100) + 30);
						else
							c.getGraphicsContext2D().drawImage(empty[0], i * 100, (j * 100) + 30);
					} else if (j % 2 != 0) {
						int random = (int) (10 * Math.random()) / 2;
						if (full[random] != null)
							c.getGraphicsContext2D().drawImage(full[random], i * 100, (j * 100) + 30);
						else
							c.getGraphicsContext2D().drawImage(full[0], i * 100, (j * 100) + 30);
					}
				} else if (i % 2 != 0) {
					if (j % 2 != 0) {
						int random = (int) (10 * Math.random()) / 2;
						if (empty[random] != null)
							c.getGraphicsContext2D().drawImage(empty[random], i * 100, (j * 100) + 30);
						else
							c.getGraphicsContext2D().drawImage(empty[0], i * 100, (j * 100) + 30);
					} else if (j % 2 == 0) {
						int random = (int) (10 * Math.random()) / 2;
						if (full[random] != null)
							c.getGraphicsContext2D().drawImage(full[random], i * 100, (j * 100) + 30);
						else
							c.getGraphicsContext2D().drawImage(full[0], i * 100, (j * 100) + 30);
					}
				}

			}

		}

	}
     private void loadTiles(String name,Image[] full) {
        for (int i = 0; i < full.length; i++) {
     	  InputStream in= ClassLoader.getSystemResourceAsStream(name+i+".png");
     	   if(in!=null && i>0) full[i]=new Image(in);
     	   else if(i==0) full[i]=new Image(name+".png");
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

        FXBoard.getSoundInterfaceInstance().playSound(SoundInterface.ACHW, 1);
      Animation t=new Transition() {
                {
                    setCycleCount(1);
                    setCycleDuration(Duration.millis(500));
                    
                }
                
                @Override
                protected void interpolate(double frac) {
                    //System.out.println("color="+Color.WHITE.interpolate(Color.BLACK, frac));
                       score.setFill(Color.WHITE.interpolate(Color.rgb(0, 204, 102), frac));
                       score.setScaleX(frac);
                       score.setScaleY(frac);
                       
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
    		Image imagesDesc = new Image("desc1.png");
    		if(this.level>FXBoard.MAX_LEVEL) {
    			imagesDesc = new Image("desc2.png");
    		}

            c.getGraphicsContext2D().fillRect(0, 0, wBackground, hBackgroundTot);

            
            Font f= Font.loadFont(ClassLoader.getSystemResourceAsStream("SHOWG.TTF"), 48);
            if(f==null) f=Font.font(48);
            //interessante effeto mattonella....
            Light.Distant light = new Light.Distant();
            light.setAzimuth(-135.0);
            Lighting lighting = new Lighting();
            lighting.setLight(light);
            lighting.setSurfaceScale(5.0);
            //interessante effeto mattonella....
   	     	DropShadow dropShadow = new DropShadow();
   	     //	dropShadow.setColor(Color.rgb(128,0,128));
   	     	dropShadow.setColor(Color.GREY);
   	     	dropShadow.setRadius(2.0);
   	     	dropShadow.setOffsetX(0.0);
   	     	dropShadow.setOffsetY(0.0);  
   	     	dropShadow.setSpread(1);
//            c.getGraphicsContext2D().setEffect(lighting);
   	     	c.getGraphicsContext2D().setEffect(dropShadow);
            c.getGraphicsContext2D().setFont( f);
         //  c.getGraphicsContext2D().setFill(Color.rgb(255,0,255));
         //   c.getGraphicsContext2D().setFontSmoothingType(FontSmoothingType.LCD);
           // c.getGraphicsContext2D().setFill(Color.MEDIUMVIOLETRED);
          // c.getGraphicsContext2D().setFill(Color.rgb(255,0,255));
            c.getGraphicsContext2D().setFill(Color.WHITE);
			c.getGraphicsContext2D().setFill(Color.BLACK);

			c.getGraphicsContext2D().drawImage(imagesDesc, 0, 0);
			c.getGraphicsContext2D().fillText("WAVE "+romanWave(level), wBackground/4+wBackground/8,hBackgroundTot/2+hBackgroundTot/8);            	


            BackGround back=this;
            Transition pause=new PauseTransition(Duration.seconds(2));
            pause.play();
            
            pause.setOnFinished(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {

		            c.getGraphicsContext2D().fillRect(0, 0, wBackground, hBackgroundTot);
		            Font f= Font.loadFont(ClassLoader.getSystemResourceAsStream("SHOWG.TTF"), 36);
		            if(f==null) f=Font.font(36);
		            c.getGraphicsContext2D().setFont( f);
		            double centerX=(wBackground/4)-(wBackground/24);
		            double centerY=(hBackgroundTot/2)+(hBackgroundTot/12);
		            if (FXBoard.levelWave(level) == BackGround.LVL_JUNGLE) {
		            	c.getGraphicsContext2D().fillText("Level "+level+" : Clear the Jungle", centerX,centerY);
		            } else if (FXBoard.levelWave(level) == BackGround.LVL_DESERT) {
		            	c.getGraphicsContext2D().fillText("Level "+level+" : Clear the Desert", centerX,centerY);
		            }else if (FXBoard.levelWave(level) == BackGround.LVL_POLE) {
		            	c.getGraphicsContext2D().fillText("Level "+level+" : Clear the Poles", centerX,centerY);
		            }else if (FXBoard.levelWave(level) == BackGround.LVL_FOREST) {
		            	c.getGraphicsContext2D().fillText("Level "+level+" : Clear the Forest",centerX,centerY);
		            }else if (FXBoard.levelWave(level) == BackGround.LVL_MOUNTAIN) {
		            	c.getGraphicsContext2D().fillText("Level "+level+" : Clear the Mountains",centerX,centerY);
		            }else if (FXBoard.levelWave(level) == BackGround.LVL_SEA) {
		            	c.getGraphicsContext2D().fillText("Level "+level+" : Clear the Sea",centerX,centerY);
		            }else if (FXBoard.levelWave(level) == BackGround.LVL_MOON) {
		            	c.getGraphicsContext2D().fillText("Level "+level+" : Clear the Moon",centerX,centerY);
		            }else if (FXBoard.levelWave(level) == BackGround.LVL_SKY) {
		            	c.getGraphicsContext2D().fillText("Level "+level+" : Clear the Sky",centerX,centerY);
		            }else if (FXBoard.levelWave(level) == BackGround.LVL_CITY) {
		            	c.getGraphicsContext2D().fillText("Level "+level+" : Clear the City",centerX,centerY);
		            }           
		            //f= Font.loadFont(ClassLoader.getSystemResourceAsStream("SHOWG.TTF"),24);
		            //c.getGraphicsContext2D().setFont(f);
		          

		            c.getGraphicsContext2D().setEffect(null);
//		            c.getGraphicsContext2D().fillText("WAVE I", wBackground/2, hBackgroud/2);
		            ScreenPause p=new ScreenPause(2.5d,back);
		            p.play();
					
				}
			});

            
        
    
    }
    public void goAhead() throws Exception{
        drawBackGround();
        board.drawBoard();
    }
    private String romanWave(int n) {
    	String romanNumber="";
    	
    	if(n%FXBoard.MAX_LEVEL==0) n=java.lang.Math.round(n/FXBoard.MAX_LEVEL);
    	else n=java.lang.Math.round(n/FXBoard.MAX_LEVEL)+1;
    	BCDraugthsApp.log.info("Wave:"+n);

    	switch (n) {
		case 1:
			romanNumber="I";
			break;
		case 2:
			romanNumber="II";
			break;
		case 3:
			romanNumber="III";
			break;
		case 4:
			romanNumber="IV";
			break;
		case 5:
			romanNumber="V";
			break;
		case 6:
			romanNumber="VI";
			break;
		case 7:
			romanNumber="VII";
			break;
		case 8:
			romanNumber="VIII";
			break;
		case 9:
			romanNumber="IX";
			break;
		case 10:
			romanNumber="X";
			break;
		case 11:
			romanNumber="XI";
			break;
		case 12:
			romanNumber="XII";
			
			break;

		default:
			romanNumber="notknow";
			break;
		}
    	return romanNumber;
    } 
}
