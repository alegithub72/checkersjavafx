/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sa.boardgame.core.moves.Move;
import sa.boardgame.console.imp.AutomaPlayer;
import sa.boardgame.core.players.Player;
import sa.boardgame.console.imp.ConsoleRendering;
import sa.fx.draugths.players.FXAIPlayer1;
import sa.fx.draugths.players.FXPMousePlayer;
import sa.fx.draugths.screen.BackGround;
import sa.fx.draugths.sprite.Sprite;
import sa.fx.draugths.sprite.SpritePiece;
import sa.gameboard.core.Board;
import sa.gameboard.core.Checker;
import sa.gameboard.core.Game;
import sa.gameboard.core.Piece;
import sa.gameboard.core.interfaces.GraficBoardInterface;

/**
 *
 * @author appleale
 */
public class FXBoardClass extends Parent implements GraficBoardInterface  {

    private final List pedinaList[];
    private boolean on = false;
    private Game game;
    private SpritePiece select;
    private FXAIPlayer1 computerPlayer;
    private FXPMousePlayer mousePlayer;
    private BCDraugthsApp app;
    private boolean turn;
    private BackGround back;
    public FXPMousePlayer getMousePlayer() {
        return mousePlayer;
    }

    public void setMousePlayer(FXPMousePlayer mousePlayer) {
        this.mousePlayer = mousePlayer;
    }    
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    
    public int wBoardSquare;
    public int hBoardSquare;
    BackGround backGround;

    
    public SpritePiece getSelect() {
        return select;
    }

    public void setSelect(SpritePiece select) {
        this.select = select;
    }

    
    public BackGround getBackGround() {
        return backGround;
    }

    public void setBackGround(BackGround backGround) {
        this.backGround = backGround;
    }
    int level = 0;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    public FXBoardClass(int l,BCDraugthsApp app) {
        this.pedinaList = new ArrayList[2];
        turn=false;
        this.app=app;
        this.pedinaList[0] = new ArrayList();
        this.pedinaList[1] = new ArrayList();
        String[] part1=new String[]{"p55","0","p35","0","p46","1"
        ,"p24","1","p44","1","p66","0","p15","1","p26","0","p15","0",
        "p77","0","p33","1","p55","0","p04","0","p22","0","p11","0"};
            String[] part2=new String[]{""};
        this.level=l;
            //HBox infoPanel=new HBox();
            computerPlayer = new FXAIPlayer1();
            mousePlayer = new FXPMousePlayer(this);
            AutomaPlayer automa=new AutomaPlayer(mousePlayer.getColor(), "Automa");
            automa.setCommand(part1);
            
            game = new Game(automa, computerPlayer,Board.CHECKERS_GAME);
            //game.setDamaSystem((DamaInterface) this);
            ConsoleRendering console = new ConsoleRendering(game.getBoard());
            game.addRenderInterface(console);
            game.playGame();
            game.setHuman(mousePlayer);           

 
            if(game!=null) game.addRenderInterface(this);


               
        
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public SpritePiece getSpritePiece(int i1, int j1, int color, boolean eated) {
        int decodeCol = decodeColor(color);
        SpritePiece sp = null;
        int k = -1;
        System.out.println("--------" + pedinaList[decodeCol].size() + "-------------");
        for (k = 0; k < pedinaList[decodeCol].size(); k++) {
            SpritePiece pedina = (SpritePiece) pedinaList[decodeCol].get(k);
            if (pedina != null) {
                Piece p = pedina.getBoardPieceLink();

                System.out.println(k + ")" + "p.i=" + p.getI() + ",p.j=" + p.getJ() + ",p.color=" + (p.getColor() == Checker.BLACK ? "BLACK" : "WHITE"));
                if (!eated && p.getI() == i1
                        && p.getJ() == j1) {
                    sp = (SpritePiece) pedinaList[decodeCol].get(k);

                } else if (p.getI() == i1
                        && p.getJ() == j1) {
                    sp = (SpritePiece) pedinaList[decodeCol].get(k);

                }
            } else {
                System.out.println(k + ")sp=null");
            }
            if (sp != null) {
                break;
            }

        }
        //if(sp==null ) System.exit(-1);

        System.out.println("The sprite is k=" + k+" ,sprite scelto "+sp);
        return sp;
    }

    int decodeColor(int charcolor) {
        if (charcolor == Checker.BLACK) {
            return 0;
        } else {
            return 1;
        }
    }

    public void add(SpritePiece p, int color) {
        pedinaList[color].add(p);
    }

    public int size(int color) {
        return pedinaList[decodeColor(color)].size();
    }

    public void set(int n, int color, SpritePiece p) {
        pedinaList[decodeColor(color)].set(n, p);
    }

    private void drawPiecePlayer(Player player) {
        Piece[] list = null;
        if (player.getColor() == Checker.BLACK) {
            list = game.getBoard().getBlackPieces();
        } else {
            list = game.getBoard().getWhitePieces();
        }

        int color = -1;
        color = decodeColor(player.getColor());
        
        

        
        for (int i = 0; i < list.length; i++) {
            
            
            double wBoard = backGround.getWBackground();
            double hBoard = backGround.getHBackground(); 
            hBoardSquare = (int) hBoard / 8;
            wBoardSquare = (int) wBoard / 8;
            Piece pedinaChar = list[i];
            if(pedinaChar!=null){
            int x1 = pedinaChar.getI();
            int y1 = pedinaChar.getJ();

            double x = Sprite.convertBoardIposition(x1, wBoardSquare);
                    
            double y = Sprite.convertBoardJposition(y1,hBoardSquare);
            
            SpritePiece pedina = SpritePiece.buildPedina(
                    wBoardSquare, hBoardSquare, 
                    player.getColor(), pedinaChar, level,this);
            pedina.setK(i);
            add(pedina,color);
            pedina.setX(x
              //      -(SpritePiece.SPRITE_W/2)
            );
            pedina.setY(y
            //        -(SpritePiece.SPRITE_H/2)
            );
            getChildren().add(pedina);
            }else add(null,color);
        }
    }    

    
    public void winConditionMessage(int win){
            Image winText = null;
            ImageView imageView = new ImageView();
            
            if (win == Checker.WHITE) {
            winText = new Image("winwhite.png");
            imageView.setImage(winText);
            imageView.setX(150);
            imageView.setY(250);
            imageView.setScaleX(1);
            imageView.setScaleY(1);
             level++;
            getChildren().add(imageView);
            
        } else if (win == Checker.BLACK) {
            
            winText = new Image("winblack.png");
            imageView = new ImageView();
            //background.setFocusTraversable(true);
            imageView.setImage(winText);
            imageView.setX(150);
            imageView.setY(250);
            imageView.setScaleX(1);
            imageView.setScaleY(1);
            level = 0;
            getChildren().add(imageView);
            
        }
            if(winText!=null) imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                   if(isLastLevel() || level==0)  app.drawRecordScreen();
                   else  app.levelUp(level,backGround.getPoint());
                   //TODO mettere una classe che per record of fame
                    event.consume();
                }
            }); 
                       
    }
    
    public void removeSpritePiece(SpritePiece p) {
        p.setVisible(false);
        getChildren().remove(p);
        int color=p.getBoardPieceLink().getColor();
        int n = p.getK();
        set(n,color, null);
        System.out.println("list after remove"+ this);
    }
    

    
    @Override
    public void drawBoard(){
        
        
        drawPiecePlayer(mousePlayer);
        drawPiecePlayer(computerPlayer);
         
    
    }
    public void  startLevel(int point){
        backGround = new BackGround(level,this,point);
        getChildren().add(backGround);
        backGround.middleScreen();
    
    }
    public void remove(Object  o){
        getChildren().remove(o);
    }
    
    public void add(Node o){
        getChildren().add(o);
            
        }
            
    public void updateInterface(Move m) {
        game.updateInterface(m);
    }
    
    public void playComputerPlayer() {
        if (game.getAI().getName().equals("Computer")) {
            game.makeAIMove();
        } else {
            game.makeHumanMove();
        }

    }    
    
    public void turnEnd() {
        on=false;
        turn = !turn;

        if (winCondition() != Checker.WHITE && winCondition() != Checker.BLACK) {
            if (turn) {
                this.playComputerPlayer();
            }
        }

    }    
    
    public int winCondition() {

        int win = game.winCondition();
        if(win == Checker.WHITE) {
            getBackGround().updatePoint(size(Checker.WHITE) * 10);
            
        }
        if(win!=-1) winConditionMessage(win);

        return win;
    }    
    private TextField command;
    public TextField getCommand() {
        return command;
    }    
    
    public void setCommand(TextField command) {
        this.command = command;
    }    

    @Override
    public void addBoard(Board b) {
        //???? rivedere
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void renderMove(Move m) {
        if (m != Move.NULLMOVE) {
            SpritePiece p;
            p = getSpritePiece(m.getI1(),
                    m.getJ1(),
                    m.getP().getColor(), m.getP().isEated());
            p.play(m);
            //game.getBoard().makeMove(m);
            //turnEnd();
        }
        
 
    }
     public boolean isLastLevel(){
        return (level==3);
    }       
}
