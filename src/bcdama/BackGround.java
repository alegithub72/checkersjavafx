/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcdama;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 *
 * @author ale2s_000
 */
public class BackGround extends Parent {
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
    
    
    public BackGround(){
        Image images;
        images = new Image("scacchieraLevel2.png");
        background = new ImageView();
        //background.setFocusTraversable(true);
        background.setImage(images);
        getChildren().add(background);
        widthScreen=images.getWidth();
        heightScreen=images.getHeight();
        System.out.println(widthScreen);
        System.out.println(heightScreen);
        //background.setFitHeight(widthScreen);
        //background.setFitWidth(heightScreen);  
        
    }
    
    
}
