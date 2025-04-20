/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.animation;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sa.fx.draugths.screen.BackGround;


/**
 *
 * @author Alessio Sardaro
 */
public class Sprite extends ImageView {




    protected Image frameImages;
    Rectangle2D[] frames;
    String imgUrl;
    protected int w;
    protected int h;
    protected int nframes = 0;
    int k;
    String name;
    public Sprite(String img,String name) {
		super();
		this.name=name;
		this.imgUrl=img;
        frameImages = new Image(img);
        this.h=(int)frameImages.getHeight();
        this.w=(int)frameImages.getHeight();
        setImage(frameImages);
        buildFrameImages();
        setViewport(frames[0]); 

        
    }
    





	@Override
	public String toString() {
		return "Sprite [k=" + k + ", name=" + name + "]";
	}






	public void buildFrameImages(){
        int n = (frameImages.widthProperty().intValue() / w);
        frames = new Rectangle2D[n];
        for (int i = 0; i < n; i++) {
            frames[i] = new Rectangle2D(i *(double)w, 0, w, h);
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
     	return( (i * hboard )+(BackGround.hPointTable-hadjust))+BackGround.scrollH
                    ;
        
    }   

    public  double convertBoardJtoPositionX(int j,int wboard){
    	int wadjust=0;
    	
    	if(this.w>wboard) {
    		wadjust=(this.w-wboard)/2;
    	}
        return ((j * wboard )-((double)wadjust))+BackGround.scrollW;
         
    }       

    public  double convertBoardItoPositionYCenter(int i,int hboard){
     	int hadjust=0;
     	
     	if(this.h>hboard) {
     		hadjust=(this.h-hboard)/2;
     	}
        return ((i * hboard) + (hboard / 2d)+
        		(BackGround.hPointTable-hadjust))+BackGround.scrollH;
                        

    }

    public  double convertBoardJtoPositionXCenter(int j,int wboard){
    	int wadjust=0;
    	
    	if(this.w>wboard) {
    		wadjust=(this.w-wboard)/2;
    	}
        return ((j * wboard)+ (wboard / 2)-wadjust)+BackGround.scrollW;
        
    }
    

    
}
