/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths.utility;




import sa.fx.draugths.event.FXPlayer;
import sa.fx.draugths.sprite.SWhitePiece;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sa.boardgame.core.moves.Move;
import sa.boardgame.core.players.Player;
import sa.gameboard.core.Board;
import sa.gameboard.core.Game;
import sa.gameboard.core.interfaces.GraficBoardInterface;

/**
 *
 * @author ale2s_000
 */
public class BCDamaTxt extends Application implements GraficBoardInterface{

    public TextArea t;
    Game game;
    Player player2;
    FXPlayer player1;
    TextField command;
    SWhitePiece select;

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addBoard(Board b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void renderMove(Move m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void renderTable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
/**
    public void makeMove(Move move) {
        return;
    }


    public int[][] getTableTxt() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void renderTable() {
        String tabledama="";
        t.clear();
        int[][] sca=game.getDamaSystem(0);
   		for (int i = 0; i < sca.length; i++) {
				if(i==0)tabledama=tabledama+"   0 1 2 3 4 5 6 7 \n";
				for (int j = 0; j < sca[i].length; j++) {
					if(j==0)tabledama=tabledama+i+" ";
					if(sca[i][j]==DamaTxt.VUOTOW) tabledama=tabledama+("- ");
					else if(sca[i][j]==DamaTxt.VUOTOB) tabledama=tabledama+("¨ ");
					else if(sca[i][j]==DamaTxt.BLACK) tabledama=tabledama+("o ");
					else if(sca[i][j]==DamaTxt.WHITE) tabledama=tabledama+("u ");
					else if(sca[i][j]==DamaTxt.DAMAWHITE) tabledama=tabledama+("8 ");
					else if(sca[i][j]==DamaTxt.DAMABLACK) tabledama=tabledama+("Z ");
				}
				tabledama=tabledama+"\n";
			}
         
                
                t.setText(tabledama);
    }
    
    
    
    @Override
    public void start(Stage primaryStage) {
        //player1=new FXPlayer(this);
        player2=new AIDama(PedinaChar.BLACK);
        game=new Game(player1  , player2);
        game.setDamaSystem((DamaInterface)this);
        
        Image images;
        VBox root=new VBox();
        double width=600;
        double height=800;
        t = new TextArea();
        Font font=new Font(40);
        
        t.setFont(font);
        t.setPrefSize(width,height );
        t.setText("First row\nSecond row");
        t.setEditable(false);
        command=new TextField();
        Button btn=new Button("Confirm Comand");
        btn.setAlignment(Pos.CENTER);
        btn.setOnAction(player1);

        Scene scene = new Scene(root,width,
        height);


        root.getChildren().add(t);
        root.getChildren().add(command);
        root.getChildren().add(btn);
        renderTable();

        primaryStage.setTitle("TXT BC Dama");
        primaryStage.setScene(scene);



        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
                      

        primaryStage.show();


    }
    public void playPlayer1(){
        game.playPlayer1();
    }
    public int winCondition(){
        return game.winCondition();
    }
    public void playPlayer2(){
         game.playPlayer2();
    }

    /**
     * @param args the command line arguments
     
    public static void main(String[] args) {

        launch(args);
        
    }


    */
}
