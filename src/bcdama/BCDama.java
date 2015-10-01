/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcdama;


import dama.core.PedinaChar;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathBuilder;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author ale2s_000
 */
public class BCDama extends Application {
    PedinaWhite pedinaB[];
    PedinaWhite pedinaW[];
    PedinaWhite pedina;
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }//test
        });

        Image images;
        Group root=new Group();
       
        images = new Image("sc2.png");
        ImageView background = new ImageView();
        background.setFocusTraversable(true);
        background.setImage(images);
        
        double width=images.getWidth()*2;
        double height=images.getHeight()*2;
        background.setFitHeight(width);
        background.setFitWidth(height);
        
        //Image pedinaB = new Image("pedinaNeraAnim2.png");
        //ImageView background2 = new ImageView();
        //background2.setFocusTraversable(true);
        //background2.setImage(images);
        
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        //background.setFitWidth(primaryScreenBounds.getWidth());
        //background.setFitHeight(primaryScreenBounds.getWidth());
        
       /** Rectangle r = new Rectangle();
        r.setX(50);
        r.setY(50);
        r.setWidth(30);
        r.setHeight(30);
        r.setArcWidth(20);
        r.setArcHeight(20);
        Rectangle2D frame1 = new Rectangle2D(0, 0, 64, 44);
        Rectangle2D frame2 = new Rectangle2D(64,0, 64, 44);
        ImageView pedina = new ImageView(pedinaB);
        pedina.setViewport(frame1);
        pedina.setViewport(frame1);
//ImagePattern pater=new ImagePattern(pedinaB,);
        pedina.setOpacity(1);
        pedina.setVisible(true);
        pedina.setMouseTransparent(false);*/
   //r.setFill(pater);     
        
        
        
        //root.getChildren().add(viewportRect);
        Canvas canvas = new Canvas(width,height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        canvas.relocate(20, 20);
        gc.setFill(Color.BLUE);
        gc.fillRect(0,0,100,100);
        Text txt=new Text("Bianchi: Neri:");
       


        
        RotateTransition rt = new RotateTransition(Duration.millis(3000), background);
        rt.setByAngle(45);
        //rt.setCycleCount(4);
        rt.setAutoReverse(true);
 
   
        //background.setViewport(viewportRect);
        //StackPane root = new StackPane();
       
        //HBox box = new HBox();
        //box.getChildren().add(background);
        //root.getChildren().add(background);
         //root.getChildren().add(btn);
         //root.getChildren().add(canvas);
        //Scene scene = new Scene(root, 300,200);
        
        Scene scene = new Scene(root,width,
        height);


        /**BackgroundImage back=new BackgroundImage(images, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, 
                BackgroundPosition.DEFAULT, 
                BackgroundSize.DEFAULT);
        Background background1=new Background(back);*/

        primaryStage.setTitle("BC Dama");
        primaryStage.setScene(scene);


 //set Stage boundaries to visible bounds of the main screen
        //primaryStage.setX(primaryScreenBounds.getMinX());
        //primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
       
        root.getChildren().add(background);
        
        
        
        
        
        
          
        
        
        
        
        
        
        
        
        //root.getChildren().add(canvas);
       // root.getChildren().add(background2);
        // root.getChildren().add(canvas);
         //pedina=new Pedina(64 ,44,root,PedinaChar.WHITE);
         
            //    pedina.setX(30);
            //    pedina.setY(50);
                
       // primaryStage.setBackground(background1);
        primaryStage.show();
        //gc.drawImage(images, 0 ,0);
          //rt.play();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BCDama bc=new BCDama();
        bc.launch(args);
        //launch(args);
        
    }
    
}
