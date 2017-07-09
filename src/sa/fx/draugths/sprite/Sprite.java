/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.sprite;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sa.fx.draugths.FXBoardClass;
import sa.fx.draugths.animation.FrameAnimationTimer;
import sa.fx.draugths.animation.TRANSITION_STEP;
import sa.fx.draugths.screen.BackGround;

/**
 *
 * @author appleale
 */
public class Sprite extends Parent {
  
    
    
    ImageView imgView;


    public static int MOVE_TRANSITION = 0;
    Image frameImages;
    Sprite[] extraSprite=new Sprite[2];
    Rectangle2D[] frames;
    int w;
    int h;
    int wSquare;
    int hSquare;
    int nframes = 0;
    FrameAnimationTimer[] frameAnimTimer;
    int k;
    Animation[] ptList;
    FXBoardClass fbx;

    public Sprite(int w, int h,int wboardBox,int hBoardBox,String img,FXBoardClass b) {
        this.w = w;
        this.h = h;
        this.wSquare=wboardBox;
        this.hSquare=hBoardBox;
        this.fbx=b;
        frameImages = new Image(img);
        
        buildFrameImages();
        imgView = new ImageView(frameImages);
        imgView.setViewport(frames[0]);  
        getChildren().add(imgView);
        ptList=new Animation[5];

        frameAnimTimer=new FrameAnimationTimer[2];
        
    }
    
    public void setFXBoard(FXBoardClass b){
        this.fbx=b;
    }
    
    
    public void buildFrameImages(){
        int n = (frameImages.widthProperty().intValue() / w);
        frames = new Rectangle2D[n];
        for (int i = 0; i < n; i++) {
            frames[i] = new Rectangle2D(i * w, 0, w, h);
        }
    }    
    public Node getImg(){
    return imgView;
    }
    protected Sprite() {
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public void setFrame(int i) {
        this.nframes = i;
        imgView.setViewport(frames[i]);
    }

    public void setX(double x) {
        imgView.setX(x);
    }

    public void setY(double y) {
        imgView.setY(y);
    }

    public double getX(){
       return  imgView.getX();
    }    
    public double getY(){
        return imgView.getY();
    }
    
    
    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public boolean isAnimMoveFinish() {
        return ptList[TRANSITION_STEP.FULL_STEP].getStatus() == Animation.Status.STOPPED;
    }

    public void removeAnimationSetting() {
        for (int i = 0; i < ptList.length; i++) {
            ptList[i] = null;
        }
        for (int i = 0; i < frameAnimTimer.length; i++) {
             if(frameAnimTimer[i]!=null) frameAnimTimer[i].stop();
            frameAnimTimer[i] = null;
        }

    }
    
    public void stopAnimation(int n){
       if(frameAnimTimer[n]!=null) frameAnimTimer[n].stop();
    }
    public void removeExtraSprite(int n){
        if(extraSprite[n]!=null) fbx.remove(extraSprite[n]);
    }
    public static double convertBoardIposition(int i,int w){
    
                double x = ((i * w) + (w / 2)) 
                    - (SpritePiece.SPRITE_W/2);
                return x;
    }
     public static double convertBoardJposition(int j,int h){
        double y = ((j * h) + (h / 2)) 
                    - (SpritePiece.SPRITE_H/2)
                    +BackGround.hPointTable+10;
        return y;
    }   
    public static double convertBoardIpositionCenter(int i,int w){
    
                double x = ((i * w) 
                        + (w / 2)
                        );
                return x;
    }
    public static double convertBoardJpositionCenter(int j,int h){
    
                double y = ((j * h)
                        + (h / 2)
                       )  + BackGround.hPointTable+10;
                        
                return y;
    }    
    
    
}
