/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.screen;

import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author appleale
 */
public class RecordScreen  extends Parent{
   
    public RecordScreen(){
           

           drawTableRecord();
           
           
        
    }
    
    private void drawTableRecord(){
        Image images = new Image("background.png");
        Canvas c=new Canvas(images.getWidth(),images.getHeight());
        getChildren().add(c);
        Text record=new Text("RECORD PLAYER TABLE");
        Font f = new Font(50);
        record.setFont(f);
        record.setX(50);
        record.setY(160);
        record.setFill(Color.CRIMSON);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(20.0);
        dropShadow.setOffsetX(0.0);
        dropShadow.setOffsetY(0.0);
        record.setEffect(dropShadow);                
                
        getChildren().add(record);
            for (int i = 0; i < 10; i++) {
                Text player=new Text("NAME"+i+"     ???");
                 f = new Font(30);
                player.setFont(f);
                player.setX(230);
                player.setY(200+(30*i));
                player.setFill(Color.AQUA);
                getChildren().add(player);
                
            }
    }
    
}
