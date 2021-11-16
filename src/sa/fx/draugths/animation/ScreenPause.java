/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.animation;

import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
 * @author Alessio Sardaro
 */
public class ScreenPause extends Transition{
    long duration=0;
    boolean start=true;
    ScreenPauseInterface i;
    public ScreenPause(double duration,ScreenPauseInterface i){
        //Duration d= Duration.ofSeconds(duration);
        //this.duration=d.getNano();
        setCycleDuration(javafx.util.Duration.seconds(duration));
        start=true;
        this.i=i;
        this.setOnFinished(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
					i.goAhead();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            
    
    });
        
    }
    
   
        
     

    @Override
    protected void interpolate(double frac) {
        
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
