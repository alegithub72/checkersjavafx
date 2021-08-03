/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.screen;



import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import sa.fx.draugths.FXBoard;

/**
 *
 * @author  Alessio Sardaro
 */
public class StartScreen extends Parent {

    double widthScreen;
    double heightScreen;
    Canvas c;
    int point;
    Text start;
    FXBoard fbx;
    int level;

    public double getWidthScreen() {
        return widthScreen;
    }

    public void setWidthScreen(double widthScreen) {
        this.widthScreen = widthScreen;
    }

    public double getHeightScreen() {
        return heightScreen;
    }

    public void setHeightScreen(double heightScreen) {
        this.heightScreen = heightScreen;
    }
    public GraphicsContext getBackgroundGrafic(){
        return c.getGraphicsContext2D();
    }
    
    public StartScreen(int level,FXBoard b){
        this.fbx=b;
        c=new Canvas(800, 830);
        getChildren().add(c);
        widthScreen=800;
        heightScreen=840 ; 
        this.level=level;
        drawScreen();

    }

    public void drawScreen(){
        Image images=null;
        

        images = new Image("startScreen2.png");
        
        
        

        c.getGraphicsContext2D().drawImage(images, 0, 31);


            Font f = new Font(null, 20);
            start=new Text("- CLICK TO START -");
            start.setX((widthScreen-180)/2);
            start.setY(heightScreen/2);
            start.setFont(f);
            start.setFill(Color.ANTIQUEWHITE);
            getChildren().add(start);  
            Animation t=new Transition() {
                {
                    setCycleCount(Animation.INDEFINITE);
                    setCycleDuration(Duration.millis(500));
                    
                }
                
                @Override
                protected void interpolate(double frac) {
                    //System.out.println("color="+Color.WHITE.interpolate(Color.BLACK, frac));
                       start.setFill(Color.WHITE.interpolate(Color.BLACK, frac));
                }
            };
            t.play();
        }
            

           
           
        
    

 


    public void setVisibleBack(boolean b){
        c.setVisible(b);
                
    }
    
}
