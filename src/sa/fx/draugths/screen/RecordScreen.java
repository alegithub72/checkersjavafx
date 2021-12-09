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

import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    public void addRecordPlayer(String name,Integer points) {

        RecordPlayer newOne=new RecordPlayer(name+players.get(9).getI() , points,"records"+players.get(9).getI(),players.get(9).getI());
        players.add(newOne);
        Collections.sort(players, new RecordPlayer());
        prop.replace("record"+players.get(9).getI()+".name", players.get(9).getMame(),name);
        prop.remove("record"+players.get(9).getI()+".points");
        prop.put("record"+players.get(9).getI()+".points",""+ points);
        try {
            FileOutputStream out=new FileOutputStream("records.properties");
			prop.store(out, "Nuovo Tabella Record");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    List<RecordPlayer> ordredrecordsPlayers() {
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
