/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.test.canvas;
 
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

 
/**
 *
 * @author Alessio Sardaro
 */
public class CanvasTest extends Application {
    
    double width=300;
    double height=250;

 
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Drawing Operations Test");
        Group group1 = new Group();
       // Group group2 = new Group();
        Group root =new Group();
        Canvas canvas = new Canvas(width, height);
        Canvas canvas2 = new Canvas(width, height);
          //URL url=      Thread.currentThread().getContextClassLoader().getSystemResource(MoveAnimePedinaTimer.BITE);
        //url= this.getClass().getClassLoader().
        //InputStream in=ClassLoader.getSystemResourceAsStream("src/"+MoveAnimePedinaTimer.MOVEWHITE);
            //java.io.DataInputStream r=new DataInputStream(in);
         // URL url=  URL   .getSystemResource(MoveAnimePedinaTimer.BITE);
        //URL url=new URL(MoveAnimePedinaTimer.BITE);
       // ClassLoader classLoader = getClass().getClassLoader();
        //URL url=classLoader.getResource(
        //BCDraugthsApp.getSoundInterfaceInstance().playSound(SoundInterface.BIG_BITE,1);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gc2 = canvas2.getGraphicsContext2D();
                
        drawShapes(gc);
        drawShapes2(gc2);
        canvas2.setTranslateY(height);
        group1.getChildren().add(canvas);
        group1.getChildren().add(canvas2);
        //group2.setScaleX(0.5);
        //group2.setScaleY(0.5);
        
        root.getChildren().add(group1);
       // root.getChildren().add(group2);
         Scene sc=new Scene(root,width,height*2,Color.BROWN);
        //primaryStage.setWidth(300);
        //primaryStage.setHeight(500);
        
        primaryStage.setScene(sc);
        primaryStage.show();
    }

    private void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.AZURE);
        gc.setStroke(Color.BLUE);
        //Rectangle rect = new Rectangle (0, height-50, width, 50); 
        //gc.strokeRect(0, height-50, width, 50);
        gc.strokeRect(0, 0, width,height);
        //rect.setArcHeight(10);
        //rect.setStroke();
       // rect.setFill(Color.rgb(255, 255, 128));
       gc.setStroke(Color.BISQUE);
         Font f = new Font(null, 30);
         gc.setFont(f);
        
       gc.strokeRect(0, height-30, width,30);
       gc.fillText("SCORE:0",0,height);
      
       //if(level<=1)  score=new Text("0");
       //else score=new Text(""+this.point);

        
       // score.setX(120);
        //score.setY(heightScreen-50);
        //scoreLabel.setX(20);
        //scoreLabel.setY(heightScreen-50);
        //score.setFont(f);
        //scoreLabel.setFont(f);
        //score.setFill(Color.rgb(0, 204, 102));
        //scoreLabel.setFill(Color.rgb(0, 204, 102));
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(20.0);
        dropShadow.setOffsetX(0.0);
        dropShadow.setOffsetY(0.0);
        
        //scoreLabel.setEffect(dropShadow);
        //score.setEffect(dropShadow);
        
       // gc.setEffect(dropShadow);
        //
        
        gc.fillText("Level:"+1,width-100,height);
       // levelLabel.setX(widthScreen-50);
       // levelLabel.setY(heightScreen-50);
        //levelLabel.setFont(f);
        //levelLabel.setFill(Color.rgb(0, 204, 102));
          //levelLabel.setEffect(dropShadow);

        
        
    }
   private void drawShapes2(GraphicsContext gc) {
       
        gc.setFill(Color.BROWN);
        gc.setStroke(Color.CORAL);
        gc.setLineWidth(5);
        gc.setGlobalAlpha(0.5);
        gc.strokeRect(0, 0, width, height);
        gc.fillOval(30, 30, 10, 10);

        
        
    }    
}
    
    
    

