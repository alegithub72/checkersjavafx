/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.sprite;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.animation.FrameAnimationTimer;
import sa.fx.draugths.animation.TRANSITION_STEP;
import sa.fx.draugths.screen.BackGround;

/**
 *
 * @author appleale
 */
public class Sprite extends ImageView {
  
    
    
	public static int MOVE_TRANSITION = 0;
    Image frameImages;
    Rectangle2D[] frames;
    int w;
    int h;
    int nframes = 0;
    int k;
    public Sprite(String img) {
		super();
        frameImages = new Image(img);
        this.h=(int)frameImages.getHeight();
        this.w=(int)frameImages.getHeight();
        setImage(frameImages);
        buildFrameImages();
        setViewport(frames[0]); 

        
    }
    

    
    public void buildFrameImages(){
        int n = (frameImages.widthProperty().intValue() / w);
        frames = new Rectangle2D[n];
        for (int i = 0; i < n; i++) {
            frames[i] = new Rectangle2D(i * w, 0, w, h);
        }
    }    



    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public void setFrame(int i) {
        this.nframes = i;
        setViewport(frames[i]);
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


     public  double convertBoardItoPositionY(int i,int hboard){
     	int hadjust=0;
     	if(this.h>hboard) hadjust=(this.h-hboard)/2;
        double y = (i * hboard )+(BackGround.hPointTable-hadjust)
                    ;
        return y;
    }   

    public  double convertBoardJtoPositionX(int j,int wboard){
    	int wadjust=0;
    	if(this.w>wboard) wadjust=(this.w-wboard)/2;
                double x = (j * wboard )-(wadjust);
                return x;
    }       
    public  double convertBoardJtoScenePositionX(int j,int wboard){
    	int wadjust=0;
    	if(this.w>wboard) wadjust=(this.w-wboard)/2;
                double x = (j * wboard )-(wadjust) - (wboard / 4);
                return x;
    }     
    public  double convertBoardItoScenePositionY(int i,int hboard){
     	int hadjust=0;
     	if(this.h>hboard) hadjust=(this.h-hboard)/2;
        double y = (i * hboard )
                    ;
        return y;
    } 
    public  double convertBoardItoPositionYCenter(int i,int hboard){
     	int hadjust=0;
     	if(this.h>hboard) hadjust=(this.h-hboard)/2;
                double y = ((i * hboard)
                        + (hboard / 2)+(BackGround.hPointTable-hadjust)
                       ) ;
                        
                return y;
    }

    public  double convertBoardJtoPositionXCenter(int j,int wboard){
    	int wadjust=0;
    	if(this.w>wboard) wadjust=(this.w-wboard)/2;
                double x = ((j * wboard) 
                        + (wboard / 2)-wadjust
                        );
                return x;
    }
    

    
}
