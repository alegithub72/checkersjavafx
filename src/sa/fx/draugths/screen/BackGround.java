/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.screen;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author ale2s_000
 */
public class BackGround extends Group {
    ImageView background;
    double widthScreen;
    double heightScreen;
  

    public ImageView getBackground() {
        return background;
    }

    public void setBackground(ImageView background) {
        this.background = background;
    }

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
    
    
    public BackGround(int level){
        Image images;

        background = new ImageView();

        if (level==1) {
            //images = new Image("scacchieraLevel2.png");
            images = new Image("hearthBright.png");

        }
        else if (level==0) {
        images = new Image("startScreen2.png");
        }
        else images = new Image("scacchieraMosaic.png");
        

        widthScreen=images.getWidth();
        heightScreen=images.getHeight();
        background.setImage(images);
        
        
        getChildren().add(background);
        
        if(level==0){
            Font f = new Font(null, 20);
            Text start=new Text("- CLICK TO START -");
            start.setX((widthScreen-180)/2);
            start.setY(heightScreen/2);
            start.setFont(f);
            start.setFill(Color.ANTIQUEWHITE);
            getChildren().add(start);  
        }
        //background.setFocusTraversable(true);


        
    }
    
    
}
