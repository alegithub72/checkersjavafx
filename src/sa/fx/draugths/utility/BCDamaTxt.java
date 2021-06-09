/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.utility;





import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sa.boardgame.core.moves.Move;
import sa.boardgame.core.players.Player;
import sa.fx.draugths.FXBoard;
import sa.fx.draugths.players.FXPlayer;
import sa.fx.draugths.sprite.HumanPiece;
import sa.fx.draugths.sprite.SpritePiece;
import sa.gameboard.core.Checker;
import sa.gameboard.core.Game;

/**
 *
 * @author ale2s_000
 */
public class BCDamaTxt extends Application{

    public TextArea t;
    Game game;
    Player player2;
    FXPlayer player1;
    TextField command;
    HumanPiece select;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group boardGroup=new Group();

        
        Image img = new Image("heartBoard.png");
        double w=img.getWidth();
        double h=img.getHeight();
        Canvas c=new Canvas(w, h);
        Checker ck=new Checker(0,0,Player.BOARDNORTH);
         Checker ck2=new Checker(1, 0, Player.BOARDNORTH);
         FXBoard fbx=new FXBoard(1, null);
         
        SpritePiece p=SpritePiece.buildPedina(64, 64, 0, ck, 1,null);
        SpritePiece p1=SpritePiece.buildPedina(64, 64, 0,ck2, 1,null);
        p.setFXBoard(fbx);
        p1.setFXBoard(fbx);
        c.getGraphicsContext2D().drawImage(img, 0, 0);
        boardGroup.getChildren().add(c);
        boardGroup.getChildren().add(p);
        boardGroup.getChildren().add(p1);
        p1.setX(64*1);
        p1.setY(0);
        Scene scene = new Scene(boardGroup,w,h, Color.BLACK);
        primaryStage.setScene(scene);
        Node n1=p1.getImg();
        Node n=p.getImg();
       // Point2D po= n1.localToScene(p1.getX(), p1.getY());
        
       // System.out.println("po1  ="+po);
       Bounds b= n.getBoundsInLocal();
       Bounds b1=n1.getBoundsInLocal();
       
        Move m=new Move(0, 0,ck2 ,ck, Move.EAT);
        //p1.play(m);
       System.out.println("--->"+b.contains(200, 0));
       System.out.println(b+"-test2-->"+b.intersects(b1)+"--->\n"+b1);
       // System.out.println( p1.getLayoutX()+","+p1.getLayoutY()+"--->"+ b.contains(b1)+b1.intersects(b));
        //n.fireEvent(new Event());
        primaryStage.show();
        
    
    }
    


    /* * @param args the command line arguments
     */
    
public static void main(String[] args) {

        launch(args);
        
    }


    
}
