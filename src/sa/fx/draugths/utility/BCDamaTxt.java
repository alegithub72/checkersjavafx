/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.utility;





import javafx.application.Application;
import javafx.scene.Group;
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
import sa.fx.draugths.sprite.SoldierPiece;
import sa.fx.draugths.sprite.SpritePiece;
import sa.gameboard.core.Checker;
import sa.gameboard.core.Game;
import sa.gameboard.core.Piece.Side;

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
    SoldierPiece select;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group boardGroup=new Group();

        
        Image img = new Image("heartBoard.png");
        double w=img.getWidth();
        double h=img.getHeight();
        Canvas c=new Canvas(w, h);
        Checker ck=new Checker(0,0,Side.NORTH_BOARD);
         Checker ck2=new Checker(1, 0, Side.SOUTH_BOARD);
         FXBoard fbx=new FXBoard(1, null);
         
        SpritePiece p=fbx.buildPedina( 0, ck, 1);
        SpritePiece p1=fbx.buildPedina( 0,ck2, 1);

        c.getGraphicsContext2D().drawImage(img, 0, 0);
        boardGroup.getChildren().add(c);
        boardGroup.getChildren().add(p);
        boardGroup.getChildren().add(p1);
        p1.setX(64*1);
        p1.setY(0);
        Scene scene = new Scene(boardGroup,w,h, Color.BLACK);
        primaryStage.setScene(scene);
//        Node n1=p1.getImg();
//        Node n=p.getImg();
       // Point2D po= n1.localToScene(p1.getX(), p1.getY());
        
       // System.out.println("po1  ="+po);
//       Bounds b= n.getBoundsInLocal();
//       Bounds b1=n1.getBoundsInLocal();
       
        Move m=new Move(0, 0,ck2 ,ck, Move.EAT);
        //p1.play(m);
        //BCDraugthsApp.log.info("--->"+b.contains(200, 0));
        //BCDraugthsApp.log.info(b+"-test2-->"+b.intersects(b1)+"--->\n"+b1);
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
