/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.screen;



import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sa.fx.draugths.FXBoardClass;
import sa.fx.draugths.animation.ScreenPause;
import sa.fx.draugths.animation.ScreenPauseInterface;

/**
 *
 * @author ale2s_000
 */
public class BackGround extends Parent implements ScreenPauseInterface {

    double wBackground=700;
    double hBackgroud=700;
    static public double hPointTable=30;
    Canvas c;
    Text scoreLabel;
    Text score;
    Text levelLabel;
    int point;
    //Text start;
    FXBoardClass board; 
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
    
    public BackGround(int level,FXBoardClass board){

        c=new Canvas(wBackground, hBackgroud+hPointTable+10);
        getChildren().add(c);
        this.board=board;
        this.level=level;

    }

    public void drawBackGround(int level){
        Image images;
     
           // getChildren().remove(start);
            drawScoreBar(level);
        
   
        
        if (level==1) {
              images = new Image("heartBoard.png");
              

        }else{
            images = new Image("AmlienBaseBoard.png");

        }
        c.getGraphicsContext2D().drawImage(images, 0, 31);


    
    }
    void drawScoreBar(int level) {
        
 
 
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
    }

    public void resetPoint() {
        this.point = 0;
        score.setText("" + this.point);
    }
    public void setVisibleBack(boolean b){
        c.setVisible(b);
                
    }
    
    public void middleScreen(int level){
                

          
            
            Image imagesDesc = null;
            if (level == 1) {
                imagesDesc = new Image("desc1.png");
            } else if (level == 2) {
                imagesDesc = new Image("desc2.png");
            }
            
            c.getGraphicsContext2D().fillRect(0, 0, wBackground, wBackground);
            c.getGraphicsContext2D().drawImage(imagesDesc, 0, 0);
            ScreenPause p=new ScreenPause(2500, this);
            p.start();
           // SequentialTransition seq=new SequentialTransition(new PauseTransition(
            //        Duration.millis(1500)));
             // seq.setNode(c);
             // seq.play();
          //  c.setOnMouseClicked(new EventHandler<MouseEvent>() {
            
             //   @Override
            ///    public void handle(MouseEvent event) {
              //      c.getGraphicsContext2D().fillRect(0, 0, widthScreen, widthScreen);
                    //backGround.setVisibleBack(true);

                //    event.consume();
               // }
            //});
        
    
    }
    public void goAhead(){
        drawBackGround(level);
        board.drawBoard();
    }
}
