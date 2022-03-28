/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.screen;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import sa.fx.draugths.utility.RecordPlayer;

/**
 *
 * @author Alessio Sardaro
 */
public class RecordScreen  extends Parent{
	Properties prop;
	List<RecordPlayer> players;
    public RecordScreen(){
        this.prop=new Properties();
        FileInputStream in=null;
		try {
			in = new FileInputStream("records.properties");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {


            if(in==null)
			prop.load(ClassLoader.getSystemResourceAsStream("records.properties"));
            else prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        players=ordredrecordsPlayers();

           
           
        
    }
    public boolean addRecordPlayer(String name,Integer points) {
        //players.remove(9);
    	RecordPlayer alreadyPresent=null;
        for (int i = 0; i < players.size(); i++) {
        	if(players.get(i).getPropName().indexOf("X")>0)  alreadyPresent=players.get(i);
		}
        if(alreadyPresent!=null) {
        	alreadyPresent.setMame(name);
        }else {
            alreadyPresent=new RecordPlayer(name , points,"recordsX",10);
        	players.add(alreadyPresent);
        	
        }
        Collections.sort(players, new RecordPlayer());
        //players.remove(players.size()-1);
        boolean recordMade=false;
        for (int i = 0; i < players.size() ;i++) {
        	if(players.get(i).getPropName().indexOf("X")>0) {
        		
        		if(i<10)recordMade=true;
        }	
        	if(i==10)alreadyPresent.setI(players.get(i).getI()); 

        }

        if(players.size()>=11) players.remove(10);
        


        return recordMade;
    }
    
public void saveRecordPlayers() {
    try {
    	for (Iterator iterator = players.iterator(); iterator.hasNext();) {
			RecordPlayer recordPlayer = (RecordPlayer) iterator.next();
			if(recordPlayer.getPropName().indexOf("X")>0)
				recordPlayer.setPropName("records");
	        prop.replace("record"+recordPlayer.getI()+".name", recordPlayer.getMame());
	        prop.replace("record"+recordPlayer.getI()+".points",""+ recordPlayer.getPoints());
		}
        FileOutputStream out=new FileOutputStream("records.properties");
		prop.store(out, "Nuovo Tabella Record");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public   List<RecordPlayer> ordredrecordsPlayers() {
    	List<RecordPlayer> list=new ArrayList<>();
    	for (int i = 1; i < 11; i++) {
        	String name=prop.getProperty("record"+i+".name");
        	Integer pointsTmp=Integer.parseInt( prop.getProperty("record"+i+".points"));
        	RecordPlayer recordPl=new RecordPlayer(name,pointsTmp,"record"+i,i);
        	list.add(recordPl);
		}
    	return  list;
    }
    
    public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}

	public  void drawTableRecord(){
		getChildren().clear();
        Image images = new Image("background.png");
        Canvas c=new Canvas(images.getWidth(),images.getHeight());
        //c.getGraphicsContext2D().drawImage(images, 0, 0);
        getChildren().add(c);
        Text record=new Text("RECORD PLAYER TABLE");
        Font f = new Font(50);
        record.setFont(f);
        record.setX((BackGround.wBackground/18)+90 );
        record.setY(BackGround.hBackgroud/12);
        record.setFill(Color.CRIMSON);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(20.0);
        dropShadow.setOffsetX(0.0);
        dropShadow.setOffsetY(0.0);
        record.setEffect(dropShadow);  
        

        
        getChildren().add(record);
        int i=0;
        Iterator<RecordPlayer> iter=players.iterator();
        while (iter.hasNext()) {
			RecordPlayer pl1 = (RecordPlayer) iter.next();
			String pointZero="0000000000";
				if(pl1.getPoints()>0) {
					pointZero="";
					for( int j=0;j<(10-(""+pl1.getPoints()).length());j++) {
						pointZero=pointZero+"0";
					}
					pointZero=pointZero+pl1.getPoints();
				}
                Text player=new Text(pl1.getMame().substring(0,3)+" : "+pointZero);
                if(pl1.getPropName().indexOf("X")>0) {
                    Animation t=new Transition() {
                        {
                            setCycleCount(Animation.INDEFINITE);
                            setCycleDuration(Duration.millis(500));
                            
                        }
                        
                        @Override
                        protected void interpolate(double frac) {
                            //System.out.println("color="+Color.WHITE.interpolate(Color.BLACK, frac));
                        	player.setFill(Color.WHITE.interpolate(Color.BLACK, frac));
                        }
                    };
                    t.play();
                }
                 f = new Font(30);
                player.setFont(f);
                player.setX((BackGround.wBackground/18)+200);
                player.setY(((BackGround.hBackgroud/12)+130)+(40*i));
                player.setFill(Color.AQUA);
                getChildren().add(player);
                i++;
                
            }
    }
    
}
