/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.animation;

import javafx.animation.AnimationTimer;

/**
 *
 * @author appleale
 */
public class ScreenPause extends AnimationTimer{
    long duration=0;
    boolean start=true;
    ScreenPauseInterface i;
    public ScreenPause(long duration,ScreenPauseInterface i){
    this.duration=duration;
            this.i=i;
    }
    @Override
    public void handle(long now) {
        long startL=0;
        if(start) startL=now;
        start=false;
        if(now-startL>=duration) 
        {
            i.goAhead();
            stop();
        }
        
       
    }
    
}
