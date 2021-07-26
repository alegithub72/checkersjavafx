/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import sa.boardgame.console.imp.AutomaPlayer;
import sa.boardgame.console.imp.ConsoleRendering;
import sa.boardgame.core.moves.Move;
import sa.boardgame.core.players.Player;
import sa.fx.draugths.animation.event.EventDraugthTransform;
import sa.fx.draugths.animation.event.EventEatAnimPiece;
import sa.fx.draugths.event.EventEatPieceSelect;
import sa.fx.draugths.event.EventEndTurn;
import sa.fx.draugths.event.EventPointUpdate;
import sa.fx.draugths.event.EventSelectionPlayer;
import sa.fx.draugths.players.FXAIPlayer1;
import sa.fx.draugths.players.FXPMousePlayer;
import sa.fx.draugths.screen.BackGround;
import sa.fx.draugths.sprite.AlienPiece;
import sa.fx.draugths.sprite.MonsterSprite;
import sa.fx.draugths.sprite.MoonSoldier;
import sa.fx.draugths.sprite.SoldierPiece;
import sa.fx.draugths.sprite.SpritePiece;
import sa.fx.draugths.utility.BoardHW;
import sa.gameboard.core.Board;
import sa.gameboard.core.Checker;
import sa.gameboard.core.Game;
import sa.gameboard.core.Piece;
import sa.gameboard.core.interfaces.GraficBoardInterface;

/**
 *
 * @author appleale
 */
public class FXBoard extends Parent implements GraficBoardInterface  {

    private final List pedinaList[];
    private boolean animationOn = false;
    private Game game;
    private SpritePiece select;
    //TODO: fire event to valorize eated anim element....
    private SpritePiece eated;
    private FXAIPlayer1 computerPlayer;
    private FXPMousePlayer mousePlayer;
    private BCDraugthsApp app;
    private boolean turn;
    BackGround backGround;
    private TextField command;
    final static public BoardHW boardHW=  new BoardHW(100, 100); 
    
    public FXBoard(int l,BCDraugthsApp app) {
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
            
            ConsoleRendering console = new ConsoleRendering();
            game.addRenderInterface(console);
           // game.playGame();

            game.setHuman(mousePlayer);           
           // load("board.txt");
 
            if(game!=null) game.addRenderInterface(this);
            addEventHandler( EventPointUpdate.MOVE_UPDATE, new EventHandler<EventPointUpdate>() {

    			@Override
    			public void handle(EventPointUpdate event) {

    				Move m=event.getMove();
    				BCDraugthsApp.log.info("EventPointUpdate HANLDER..."+m);
    				backGround.updatePoint(m.calculateValue());
    				event.consume();
    			}
            	
            });
            addEventHandler(EventDraugthTransform.DRAUGTH_EVENT, new EventHandler<EventDraugthTransform>() {

				@Override
				public void handle(EventDraugthTransform event) {

					SpritePiece p= event.getPieceToTrasform();
					BCDraugthsApp.log.info("EventDraugthTransform HANLDER..."+p);
					transformInDraugth(p);
					event.consume();
				}

	
			});

			/*
			 * addEventHandler(EventRemoveEatPiece.EAT_EVENT, new
			 * EventHandler<EventRemoveEatPiece>() {
			 * 
			 * @Override public void handle(EventRemoveEatPiece event) { //
			 * BCDraugthsApp.log.info("EventRemoveEatPiece HANLDER..."+eated); //
			 * BCDraugthsApp.log.info("eated "+eated); //
			 * if(eated!=null)removeSpritePiece(eated); // eated=null; event.consume();
			 * 
			 * } });
			 */
            addEventHandler(EventEndTurn.END_TURN, new EventHandler<EventEndTurn>() {

				@Override
				public void handle(EventEndTurn event) {
					BCDraugthsApp.log.info("Turn end...EventEndTurn HANLDER..."+event.getEventType().getName()+","+event.getP().getColorFX());
					turnEnd();
			
					event.consume();
				}
			});
            addEventHandler(EventEatPieceSelect.EAT_SELECT, new EventHandler<EventEatPieceSelect>() {

				@Override
				public void handle(EventEatPieceSelect event) {

					Move m=	event.getMove();
					BCDraugthsApp.log.info("EventEatPieceSelect HANLDER..."+eated);
					eated=getSpritePiece(m.getEat().getI(), m.getEat().getJ(), m.getEat().getColor(),m.getEat().isEated());
					eated.buildDestroyAnimation(m.getEat().getType());
					eated.setEatedAnim(true);
					BCDraugthsApp.log.info("EventEatPieceSelect HANLDER..."+eated);
					event.consume();
				}
			});
            addEventHandler(EventEatAnimPiece.EATANIM_EVENT, new EventHandler<EventEatAnimPiece>() {

				@Override
				public void handle(EventEatAnimPiece event) {
					BCDraugthsApp.log.info("EventEatAnimPiece HANLDER start anim..."+eated);
					if(eated!=null)eated.start();
					eated=null;
					event.consume();
				}
			});
        
    }
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
    

    void load(String file) {
    	
        try {
			game.getBoard().loadBoard(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    public boolean isAnimationOn() {
        return animationOn;
    }

    public void setAnimationOn(boolean on) {
        this.animationOn = on;
    }

    public SpritePiece getSpritePiece(int i1, int j1, int color, boolean eated) {
        int decodeCol = decodeColor(color);
        SpritePiece sp = null;
        int k = -1;
        //BCDraugthsApp.log.info("--------" + pedinaList[decodeCol].size() + "-------------");
        for (k = 0; k < pedinaList[decodeCol].size(); k++) {
            SpritePiece pedina = (SpritePiece) pedinaList[decodeCol].get(k);
            if (pedina != null) {
                Piece p = pedina.getBoardPieceLink();

              //  BCDraugthsApp.log.info(k + ")" + pedina);
                if (!eated && p.getI() == i1
                        && p.getJ() == j1) {
                    sp = (SpritePiece) pedinaList[decodeCol].get(k);

                } else if (p.getI() == i1
                        && p.getJ() == j1) {
                    sp = (SpritePiece) pedinaList[decodeCol].get(k);

                }
            } else {
            	//BCDraugthsApp.log.info(k + ")sp=null");
            }
            if (sp != null) {
                break;
            }

        }
        //if(sp==null ) System.exit(-1);

        BCDraugthsApp.log.info("getSpritePiece ..The sprite is k=" + k+" ,sprite scelto "+sp);
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
        pedinaList[decodeColor(color)].add(p);
    }

    public int size(int color) {
        return pedinaList[decodeColor(color)].size();
    }

    public void replace(int n, int color, SpritePiece p) {
    	int z=decodeColor(color);
        pedinaList[z].set(n, p);
    }

    private void drawPiecePlayer(Player player) {
        Piece[] list = null;
        if (player.getColor() == Checker.BLACK) {
            list = game.getBoard().getBlackPieces();
        } else {
            list = game.getBoard().getWhitePieces();
        }

        
        

    	BCDraugthsApp.log.info("BUILD "+player.getName()+" LEVEL="+level);
        for (int i = 0; i < list.length; i++) {
            
            

            Piece pedinaChar = list[i];
            if(pedinaChar!=null){


            
            SpritePiece pedina = buildPedina(player.getColor(), pedinaChar, level);
            positionPedina(pedina,pedinaChar);
            getChildren().add(pedina);
            }else add(null,player.getColor());
        }
    }    

    public void positionPedina(SpritePiece pedina,Piece pedinaChar) {
        int j1 = pedinaChar.getJ(); 
        int i1 = pedinaChar.getI();
        double x = pedina.convertBoardJtoPositionX(j1, boardHW.getW());
        double y = pedina.convertBoardItoPositionY(i1,boardHW.getH());
        pedina.setK(pedinaChar.getPos());
        add(pedina,pedinaChar.getColor());


        BCDraugthsApp.log.info("(i,j)=("+i1+","+j1+")");
        BCDraugthsApp.log.info("(x,y)=("+x+","+y+")");
        pedina.setX(x);
        pedina.setY(y);
        if(pedinaChar.getColor()==Checker.BLACK && pedinaChar.getType()==Checker.DRAUGTH) {
        		BCDraugthsApp.log.info("no scale");
        }else {
            pedina.setScaleX(0.64);
            pedina.setScaleY(0.64);
        	pedina.setW((int)(pedina.getW()*0.64));
        	pedina.setH((int)(pedina.getH()*0.64));        	
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
    
    public synchronized void removeSpritePiece(SpritePiece p) {

        getChildren().remove(p);
        p.setVisible(false);
        int color=p.getBoardPieceLink().getColor();
        int n = p.getK();
        replace(n,color, null);
        BCDraugthsApp.log.info(" remove "+ p);
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
            
    public void updateInterface(String namePlayer,Move m) {
        game.updateInterface(namePlayer,m);
    }
    
    public void playComputerPlayer() {
        if (game.getAI().getName().equals("Computer")) {
            game.makeAITurn();
        }
    }    
    
    public synchronized void turnEnd() {

        turn = !turn;

        if (winCondition() != Checker.WHITE && winCondition() != Checker.BLACK) {
            if (turn) {
                this.playComputerPlayer();
            }else{
            	animationOn=false;
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
    public void renderMove(String namePlayer,Move m) {
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
    
    public  SpritePiece buildPedina(int color,Piece charPiece,int level){

        if(level==1)  return buildPedinaLevel1( color, charPiece);
        else if(level==2) return  buildPedinaLevel2( color, charPiece);
        return null;
    }
    
    public  SpritePiece  buildPedinaLevel1(int playerColor,Piece charPiece) {
    SpritePiece pedina=null;    
    if(playerColor!=charPiece.getColor()) throw new RuntimeException("Disegual color");
     if (Checker.BLACK == playerColor) {

         pedina= new AlienPiece(charPiece,  boardHW,this);
         pedina.MOVE_FRAME[0]=4;
         pedina.MOVE_FRAME[1]=5;
         pedina.EATED_ANIM_FRAME[0]=6;
         pedina.EATED_ANIM_FRAME[1]=12;
         pedina.EAT_MOVE_FRAME[0]=3;
         pedina.EAT_MOVE_FRAME[1]=5;

         if(charPiece.getType()==Checker.DRAUGTH) {
             pedina= new AlienPiece(charPiece, boardHW,this);
             pedina=pedina.loadDraugthFrame();
	         pedina.MOVE_FRAME[0]=2;
	         pedina.MOVE_FRAME[1]=4;
	         pedina.EATED_ANIM_FRAME[0]=9;
	         pedina.EATED_ANIM_FRAME[1]=19;
	         pedina.EAT_MOVE_FRAME[0]=3;
	         pedina.EAT_MOVE_FRAME[1]=5;
         }

     } else {
             pedina= new SoldierPiece( charPiece,boardHW, this);
	         pedina.MOVE_FRAME[0]=5;
	         pedina.MOVE_FRAME[1]=6;
	         pedina.EATED_ANIM_FRAME[0]=10;
	         pedina.EATED_ANIM_FRAME[1]=12;
	         pedina.EAT_MOVE_FRAME[0]=2;
	         pedina.EAT_MOVE_FRAME[1]=3;

         if(charPiece.getType()==Checker.DRAUGTH) {
        	 pedina= new SoldierPiece( charPiece,boardHW, this);
        	 pedina=pedina.loadDraugthFrame();
             pedina.MOVE_FRAME[0]=5;
             pedina.MOVE_FRAME[1]=6;
             pedina.EATED_ANIM_FRAME[0]=7;
             pedina.EATED_ANIM_FRAME[1]=11;
             pedina.EAT_MOVE_FRAME[0]=2;
             pedina.EAT_MOVE_FRAME[1]=3;
         }

     }
     Reflection reflection = new Reflection();
     reflection.setFraction(0.7);
     DropShadow dropShadow = new DropShadow();
     dropShadow.setRadius(20.0);
     dropShadow.setOffsetX(0.0);
     dropShadow.setOffsetY(0.0);
     dropShadow.setColor(Color.BLACK);
     pedina.setEffect(dropShadow);

     pedina.setOnMouseClicked(new EventSelectionPlayer(this,pedina));
 
     return pedina;

 }
    
    public  SpritePiece  buildPedinaLevel2( int color,
            Piece pedinassociated) {
    String imagePedina=null;
    SpritePiece pedina=null;    
    if(color!=pedinassociated.getColor()) throw new RuntimeException("Disegual color");
     if (Checker.BLACK == color) {
         imagePedina = "black_chekers4.png";
         pedina= new MonsterSprite(Checker.BLACK, pedinassociated,  boardHW,imagePedina,this);
         if(pedinassociated.getType()==Checker.DRAUGTH) pedina.loadDraugthFrame();
     } else {
         imagePedina = "white_cheker_moonsoldier.png";
         pedina= new MoonSoldier( pedinassociated,boardHW, this);
         if(pedinassociated.getType()==Checker.DRAUGTH) pedina.loadDraugthFrame();
     }
     Reflection reflection = new Reflection();
     reflection.setFraction(0.7);
     DropShadow dropShadow = new DropShadow();
     dropShadow.setRadius(20.0);
     dropShadow.setOffsetX(0.0);
     dropShadow.setOffsetY(0.0);
     dropShadow.setColor(Color.BLACK);
     pedina.setEffect(dropShadow);
     pedina.setOnMouseClicked(new EventSelectionPlayer(this,pedina));
 
     return pedina;

 }     
    
    void transformInDraugth(SpritePiece p) {
	    //remove(p);
	    SpritePiece dama=  buildPedina(p.getBoardPieceLink().getColor(), 
	    		p.getBoardPieceLink(), getLevel());
	    		
	    dama.setDraugthTransform(true);
        positionPedina(dama, p.getBoardPieceLink());
        dama.setOnMouseClicked(new EventSelectionPlayer(this,dama));
        add(dama);
        replace(  p.getK() , p.getBoardPieceLink().getColor(), dama);        	
        remove(p);
    }
    
     @Override
	public void renderCommad() {
		// TODO Auto-generated method stub
		
	}

	public boolean isLastLevel(){
        return (level==3);
    }       
}
