/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.sprite;


import java.net.URL;

import javafx.animation.Animation;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sa.boardgame.core.moves.Move;
import sa.fx.draugths.BCDraugthsApp;
import sa.fx.draugths.animation.FrameAnimationTimer;
import sa.fx.draugths.animation.PedinaAnimationEndHandler;
import sa.fx.draugths.event.SelectEventPlayer;
import sa.fx.draugths.animation.TRANSITION_STEP;
import sa.gameboard.core.Checker;
import sa.gameboard.core.Piece;

/**
 *
 * @author ale2s_000
 */
public abstract class SpritePiece extends Sprite{


    Piece boardPieceLink;
    boolean draugthTransform=false;


    
    
    
    
    public Piece getBoardPieceLink() {
        return boardPieceLink;
    }

    public void setBoardPieceLink(Piece boardPiece) {
        this.boardPieceLink = boardPiece;
    }

    
    
    
    SpritePiece eated;
    
    
    
    
    public SpritePiece(int w, int h,double wboardBox,double hBoardBox, BCDraugthsApp bcdg,String img) {
        super(w, h, wboardBox, hBoardBox, bcdg, img);

    }
    void buildGenericFrameAnimation(int f1, int f2, double frac, boolean ciclyc, long interval, String sound) {
        frameAnimTimer[0] = new FrameAnimationTimer(f1, f2, this, frac, ciclyc, interval, sound);
    }    

    
    
    


    public int getFrame() {
        return this.nframes;
    }    
    public void play(Move m) {

         if (m.getType() == Move.MOVE ) {           
         if( Checker.DRAUGTH==m.getP().getType() ) {
             animDamaMove(m);
             playAnimDamaMove(m);
         }else{
             animPedinaMove(m);
             playAnimPedinaMove(m);
         }

            
        } else if (m.getType() == Move.EAT) {
            
            eated = bcdg.getSpritePiece(m.getEat().getI(), m.getEat().getJ(), m.getEat().getColor(),m.getEat().isEated()); 
          //  System.out.println("EAT ELIMATION of"+eated.getK()+")");
           if(m.getP().getType()==Checker.DRAUGTH){
               animDamaEat(m);
               playAnimDamaEat(m);
           }else {
               animPedinaEat(m);
               playAnimPedinaEat(m);
           
           }
        } 
        

    }
    protected void playAnimDamaMove(Move m){
        start(m);
        
    }
    
    protected void playAnimPedinaMove(Move m){
        start(m);
        
    }
    protected void playAnimPedinaEat(Move m){
        start(m);
        
    }
    
    protected void playAnimDamaEat(Move m){
        start(m);
        
    }
    
    public void start(Move m){

        if(ptList!=null && ptList.length>0 && ptList[TRANSITION_STEP.FULL_STEP]!=null) 
            ptList[TRANSITION_STEP.FULL_STEP].play();
        for(int h=0;h<frameAnimTimer.length;h++)if(frameAnimTimer[h]!=null) frameAnimTimer[0].start();
        
        if(eated!=null) eated.start(m);
        //check this code.................when intersect lauch the destruction 
        //event...
        //Bounds d= eated.getBoundsInLocal();
        //this.intersects(d);
        //bcdg.removePuntatori();
        bcdg.setOn(true);
      
    }    
    
    public void stop() {

        frameAnimTimer[0].stop();
        for (int i = 0; i < ptList.length; i++) {
            Animation a = ptList[i];
            if(a!=null) a.stop();
            if(a!=null) bcdg.getBoardGroup().getChildren().remove(a);
            ptList[i]=null;
        }
        setFrame(0);

        
    }
    
    public Duration getAnimDuration() {
        return Duration.seconds(1.5);
    }   
    
        public static SpritePiece buildPedina(int w,int h,double wb,double hb,int c,Piece pa,BCDraugthsApp app,int level){
            if(level==1)  return buildPedinaLevel1(w, h, wb, hb, c, pa, app);
            else if(level==2) return  buildPedinaLevel2(w, h, wb, hb, c, pa, app);
            return null;
        }
    
    
       public static SpritePiece  buildPedinaLevel1(int w, int h,double wboardBox,double hBoardBox, int color,Piece pedinassociated,BCDraugthsApp app) {
       String imagePedina=null;
       SpritePiece pedina=null;    
       if(color!=pedinassociated.getColor()) throw new RuntimeException("Disegual color");
        if (Checker.BLACK == color) {
            imagePedina = "black_checker.png";
            pedina= new AlienPiece(Checker.BLACK, app, pedinassociated, w, h,  wboardBox, hBoardBox,imagePedina);
            if(pedinassociated.getType()==Checker.DRAUGTH) pedina.setFrameDama();
        } else {
            imagePedina = "white_cheker.png";
            pedina= new HumanPiece(Checker.WHITE, app, pedinassociated, w, h,wboardBox,hBoardBox, imagePedina);
            if(pedinassociated.getType()==Checker.DRAUGTH) pedina.setFrameDama();
        }
        Reflection reflection = new Reflection();
        reflection.setFraction(0.7);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(20.0);
        dropShadow.setOffsetX(0.0);
        dropShadow.setOffsetY(0.0);
        dropShadow.setColor(Color.BLACK);
        pedina.setEffect(dropShadow);
        pedina.setOnMouseClicked(new SelectEventPlayer(app,pedina));
    
        return pedina;

    }
       
       public static SpritePiece  buildPedinaLevel2(int w, int h,double wboardBox,double hBoardBox, int color,Piece pedinassociated,BCDraugthsApp app) {
       String imagePedina=null;
       SpritePiece pedina=null;    
       if(color!=pedinassociated.getColor()) throw new RuntimeException("Disegual color");
        if (Checker.BLACK == color) {
            imagePedina = "black_chekers4.png";
            pedina= new MonsterSprite(Checker.BLACK, app, pedinassociated, w, h,  wboardBox, hBoardBox,imagePedina);
            if(pedinassociated.getType()==Checker.DRAUGTH) pedina.setFrameDama();
        } else {
            imagePedina = "white_cheker_moonsoldier.png";
            pedina= new MoonSoldier(Checker.WHITE, app, pedinassociated, w, h,wboardBox,hBoardBox, imagePedina);
            if(pedinassociated.getType()==Checker.DRAUGTH) pedina.setFrameDama();
        }
        Reflection reflection = new Reflection();
        reflection.setFraction(0.7);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(20.0);
        dropShadow.setOffsetX(0.0);
        dropShadow.setOffsetY(0.0);
        dropShadow.setColor(Color.BLACK);
        pedina.setEffect(dropShadow);
        pedina.setOnMouseClicked(new SelectEventPlayer(app,pedina));
    
        return pedina;

    }       
    
    AudioClip buildMedia(String sound){
        ClassLoader classLoader = getClass().getClassLoader();
        URL url=classLoader.getResource(sound); 
        return new AudioClip(url.toString());
                
    }       
    
    
    

   protected void  animPedinaMove(Move m) {
    
        buildPedinaMovePath(m);
        buildFrameMoveAnimation( 0, true);
        ptList[TRANSITION_STEP.FULL_STEP].setOnFinished(new PedinaAnimationEndHandler(this, m,this.bcdg, w, h));

    }  
   
   protected void  animDamaMove(Move m) {
    
        buildDamaMovePath(m);
        buildFrameMoveAnimation( 0, true);
        ptList[TRANSITION_STEP.FULL_STEP].setOnFinished(new PedinaAnimationEndHandler(this, m,this.bcdg, w, h));

    }     
   
   protected void  animPedinaEat(Move m) {
    
        buildPedinaMoveEatPath(m);
        buildFrameEatMoveAnimation( 0f, true);
        ptList[TRANSITION_STEP.FULL_STEP].setOnFinished(new PedinaAnimationEndHandler(this, m, eated,this.bcdg, w, h));
        eated.buildDestroyAnimation(m.getP().getType());


    } 
   protected void  animDamaEat(Move m) {
    
        buildDamaMoveEatPath(m);
        buildFrameEatMoveAnimation(0f, true);
        ptList[TRANSITION_STEP.FULL_STEP].setOnFinished(new PedinaAnimationEndHandler(this, m, eated,this.bcdg, w, h));
       // this.eated=eated;
       eated.buildDestroyAnimation(m.getP().getType());
    }       
    
    public void removeAnimationSetting() {
        super.removeAnimationSetting();
        eated = null;
    }
    public abstract void buildDestroyAnimation(int by);
    public abstract void buildFrameMoveAnimation( double frac, boolean ciclyc);
    public abstract void buildFrameEatMoveAnimation( double frac, boolean ciclyc);
    public abstract void setFrameDama();
    public abstract void buildPedinaMovePath(Move m);
    public abstract void buildDamaMovePath(Move m);
    public abstract void buildPedinaMoveEatPath(Move m);
    public abstract void buildDamaMoveEatPath(Move m);    


    
}
