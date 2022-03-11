/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.Group;
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
import sa.fx.draugths.animation.FrameInfo;
import sa.fx.draugths.animation.event.EventDraugthTransform;
import sa.fx.draugths.animation.event.EventEatAnimPiece;
import sa.fx.draugths.animation.event.EventRemoveEatPiece;
import sa.fx.draugths.board.event.EventEndTurn;
import sa.fx.draugths.board.event.EventPointUpdate;
import sa.fx.draugths.board.event.EventSelectionPlayerHandler;
import sa.fx.draugths.players.FXAIPlayer1;
import sa.fx.draugths.players.FXPMousePlayer;
import sa.fx.draugths.screen.BackGround;
import sa.fx.draugths.sprite.AlienPiece;
import sa.fx.draugths.sprite.HelmetSoldierPiece;
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
 * @author Alessio Sardaro
 */
public class FXBoard extends Parent implements GraficBoardInterface  {

    private final List<SpritePiece> pedinaList[];
    private boolean animationOn = false;
    private Game game;
    private SpritePiece select;
    private Group  zGroup;
    private FXAIPlayer1 computerPlayer;
    private FXPMousePlayer mousePlayer;
    private BCDraugthsApp app;
    private boolean turn;
    BackGround backGround;
    private TextField command;
    static final  public BoardHW boardHW=  new BoardHW(100, 100); 
    int level;
	public static final int MAX_WAVE=12;
	public static final int MAX_LEVEL=5;
    
    public FXBoard(int l,BCDraugthsApp app)throws Exception {
        this.pedinaList = new LinkedList[2];
        turn=false;
        this.app=app;
        this.pedinaList[0] = new LinkedList<>();
        this.pedinaList[1] = new LinkedList<>();
        zGroup = new Group();

        String[] part1=new String[]{"p55","0","p35","0","p46","1"
        ,"p24","1","p44","1","p66","0","p15","1","p26","0","p15","0",
        "p77","0","p33","1","p55","0","p04","0","p22","0","p11","0"};
        //    String[] part2=new String[]{""};
        this.level=l;
        BCDraugthsApp.log.info(" level="+level);
            //HBox infoPanel=new HBox();
            computerPlayer = new FXAIPlayer1();
            mousePlayer = new FXPMousePlayer(this);
            AutomaPlayer automa=new AutomaPlayer(mousePlayer.getColor(), "Automa");
            automa.setCommand(part1);
            
            game = new Game(automa, computerPlayer,Board.CHECKERS_GAME);
            //game.setDamaSystem((DamaInterface) this);
            //TODO cambiare le  pedine in base alla wave....
           
            
            ConsoleRendering console = new ConsoleRendering();
            game.addRenderInterface(console);
           // game.playGame();

            game.setHuman(mousePlayer); 
            buildWaveLevel();
            if(BCDraugthsApp.loadScenario) load("boardEndLevel.txt");
 
            if(game!=null) game.addRenderInterface(this);
            addEventHandler( EventPointUpdate.MOVE_UPDATE, new EventHandler<EventPointUpdate>() {

    			@Override
    			public void handle(EventPointUpdate event) {
    				if(event.getEventType()==EventPointUpdate.MOVE_UPDATE) {
    					Move m=event.getMove();
    					BCDraugthsApp.log.info("HANDLE EventPointUpdate.MOVE_UPDATE-->"+m+" event:"+event);
    					backGround.updatePoint(m.calculateValue());
    					event.consume();
    				}
    			}
            	
            });
            addEventHandler(EventDraugthTransform.DRAUGTH_EVENT, new EventHandler<EventDraugthTransform>() {

				@Override
				public void handle(EventDraugthTransform event) {
					if(event.getEventType()==EventDraugthTransform.DRAUGTH_EVENT) {
						try {
							SpritePiece p= event.getPiece();
							BCDraugthsApp.log.info("HANDLE EventDraugthTransform.DRAUGTH_EVENT->"+p);
							transformInDraugth(p);
							event.consume();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

	
			});

			
			addEventHandler(EventRemoveEatPiece.REMOVE_PIECE_EVENT, new EventHandler<EventRemoveEatPiece>() {

				@Override
				public void handle(EventRemoveEatPiece event) { //
					if(event.getEventType()==EventRemoveEatPiece.REMOVE_PIECE_EVENT) {
						SpritePiece eated = event.getPiece();
						BCDraugthsApp.log.info("EventRemoveEatPiece.REMOVE_PIECE_EVENT ->" + eated+" event:"+event); //
						eated.stopPlayAnimation();
						removeSpritePiece(eated);
						event.consume();
					}
				}
				
			});
			 
            addEventHandler(EventEndTurn.END_TURN, new EventHandler<EventEndTurn>() {

				@Override
				public void handle(EventEndTurn event) {
						if(event.getEventType()==EventEndTurn.END_TURN) {
						BCDraugthsApp.log.info("HANDLE EventEndTurn.END_TURN-->"+event.getEventType().getName()+","+event.getP().getColorFX());
						turnEnd();
						event.consume();
					}
				}
			});
//            addEventHandler(EventBuildSequence.KILL_SEQUENCE, new EventHandler<EventBuildSequence>() {
//
//				@Override
//				public void handle(EventBuildSequence event) {
//
//					Move m=	event.getMove();
//					SpritePiece eated=event.getPiece();
//					BCDraugthsApp.log.info("HANDLE EventBuildSequence.KILL_SEQUENCE eated->"+eated);
//					eated.buildKilledSequence(m);
//					eated.setEatedAnim(true);
//					event.consume();
//				}
//			});
            addEventHandler(EventEatAnimPiece.KILLPLAY_EVENT,new EventHandler<EventEatAnimPiece>() {

            	
            	@Override
            	public void handle(EventEatAnimPiece event) {
            		if(event.getEventType()==EventEatAnimPiece.KILLPLAY_EVENT) {
	            		SpritePiece eated=event.getPiece();
	            		BCDraugthsApp.log.info("HANDLE EventEatAnimPiece.KILLPLAY_EVENT->"+eated);
	            		//eated.setViewOrder(-1000);
	            		eated.buildKilledSequence(event.getMove());
	            			//eated.toFront();
	            		eated.playKilled();
	            		//eated=null;
	            		event.consume();
            		}
            	}
			});
        
    }
    
    private void buildWaveLevel() {
    	 for (int i = 11; i >(11-(waveFromLevel()-1)); i--) {
    		 Piece p=  game.getAI().getBoard().getBlackPieces()[i];
    		if(p!=null && waveFromLevel()>1) p.setType(Piece.DRAUGTH);
		}
    	
    	
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
            SpritePiece pedina = pedinaList[decodeCol].get(k);
            if (pedina != null) {
                Piece p = pedina.getBoardPieceLink();

              //  BCDraugthsApp.log.info(k + ")" + pedina);
                if (eated && p.getI() == i1
                        && p.getJ() == j1 && p.isEated()) {
                    sp = pedinaList[decodeCol].get(k);

                } else if (!eated && p.getI() == i1
                        && p.getJ() == j1 && !p.isEated()) {
                    sp =  pedinaList[decodeCol].get(k);

                }
            } else {
            	//BCDraugthsApp.log.info(k + ")sp=null");
            }
            if (sp != null) {
                break;
            }

        }
        //if(sp==null ) System.exit(-1);

        BCDraugthsApp.log.info(" ..The sprite is k=" + k+" ,sprite scelto "+sp+" isEated="+sp.getBoardPieceLink().isEated());
        return sp;
    }

    int decodeColor(int charcolor) {
        if (charcolor == Piece.BLACK) {
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

    private void drawPiecePlayer(Player player)throws Exception {
        Piece[] list = null;
        if (player.getColor() == Piece.BLACK) {
            list = game.getBoard().getBlackPieces();
        } else {
            list = game.getBoard().getWhitePieces();
        }

        
        

    	BCDraugthsApp.log.info("BUILD "+player.getName()+" LEVEL="+this.level);
        for (int i = 0; i < list.length; i++) {
            
            

            Piece pedinaChar = list[i];
            if(pedinaChar!=null){


            
            SpritePiece pedina = buildPedina(player.getColor(), pedinaChar, level);
            positionPedina(pedina,pedinaChar);
            //getChildren().add(pedina);
           zGroup.getChildren().add(pedina);

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

        BCDraugthsApp.log.info("pos:"+pedinaChar.getPos());
        BCDraugthsApp.log.info("(i,j)=("+i1+","+j1+")");
        BCDraugthsApp.log.info("(x,y)=("+x+","+y+")");
        pedina.setX(x);
        pedina.setY(y);
        if(pedinaChar.getColor()==Piece.BLACK && pedinaChar.getType()==Piece.DRAUGTH) {
        		BCDraugthsApp.log.info("also scale");
                pedina.setScaleX(0.64);
                pedina.setScaleY(0.64);
            	pedina.setW((int)(pedina.getW()*0.64));
            	pedina.setH((int)(pedina.getH()*0.64));           		
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
            
            if (win == Piece.WHITE) {
            winText = new Image("winwhite.png");
            imageView.setImage(winText);
            imageView.setX(150);
            imageView.setY(250);
            imageView.setScaleX(1);
            imageView.setScaleY(1);
             level++;
            getChildren().add(imageView);
            
        } else if (win == Piece.BLACK) {
            
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
                   if(isLastLevel() || level==0)  {
                	   app.drawRecordScreen(backGround.getPoint());
              
                   }
                   else  {
                	   try {
						app.levelUp(level,backGround.getPoint());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                   }
                   //TODO mettere una classe che per record of fame
                    event.consume();
                }
            }); 
                       
    }
    
    public synchronized void removeSpritePiece(SpritePiece p) {

        remove(p);
        p.setVisible(false);
        int color=p.getBoardPieceLink().getColor();
        int n = p.getK();
        replace(n,color, null);
        BCDraugthsApp.log.info(" remove "+ p);
    }
    

    
    @Override
    public void drawBoard()throws Exception{
        
        drawPiecePlayer(computerPlayer);
        drawPiecePlayer(mousePlayer);

    
    }
    public void  startLevel(int point){
        backGround = new BackGround(level,this,point);
        getChildren().add(backGround);
        backGround.middleScreen();
        getChildren().add(zGroup);
    
    }
    public void remove(Object  o){
    	zGroup.getChildren().remove(o);
    }
    
    public void add(Node o){
    	zGroup.getChildren().add(o);
            
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

        if (winCondition() != Piece.WHITE && winCondition() != Piece.BLACK) {
            if (turn) {
                this.playComputerPlayer();
            }else{
            	animationOn=false;
            }
        }

    }    
    
    public int winCondition() {

        int win = game.winCondition();
        if(win == Piece.WHITE) {
            getBackGround().updatePoint(size(Piece.WHITE) * 10);
            
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
                    m.getP().getColor(),false);
            p.play(m);
            //game.getBoard().makeMove(m);
            //turnEnd();
        }
        
 
    }
    
    public  SpritePiece buildPedina(int color,Piece charPiece,int level)throws Exception{

        if(FXBoard.levelWave(level)==1)  return buildPedinaLevel1( color, charPiece);
        else if(FXBoard.levelWave(level)==2) return  buildPedinaLevel2( color, charPiece);
        else if(FXBoard.levelWave(level)==3) return  buildPedinaLevel2( color, charPiece);
        else if(FXBoard.levelWave(level)==4) return  buildPedinaLevel2( color, charPiece);
        else if(FXBoard.levelWave(level)==5) return  buildPedinaLevel2( color, charPiece);
        else return null;
    }
    
	public SpritePiece buildPedinaLevel1(int playerColor, Piece charPiece) throws Exception {
		if (playerColor != charPiece.getColor())
			throw new Exception("Disegual color");
		SpritePiece pedina = null;

		if (Piece.BLACK == playerColor) {

			pedina = new AlienPiece(charPiece, boardHW, this);
			if (charPiece.getType() == Piece.CHECKER) {
				//MOVE SEQUENCE 0-3
				FrameInfo[] move = { new FrameInfo(0, 1), new FrameInfo(1, 1) , new FrameInfo(2, 1) , new FrameInfo(3, 1) };
				pedina.addMoveSequenceFrame(move);		
				//MOVE EAT SEQUENCE 4-6
				FrameInfo[] moveeat = { new FrameInfo(4, 1), new FrameInfo(5, 1),
						new FrameInfo(6, 1)};
				pedina.addEatMoveSequenceFrame(moveeat); 	
				//KILLED SEQUENCE 7-13
				FrameInfo[] killed = { new FrameInfo(7, 1), new FrameInfo(8, 1), new FrameInfo(9, 1),
						new FrameInfo(10, 1), new FrameInfo(11, 1), new FrameInfo(12, 1), new FrameInfo(13, 1) };				

				pedina.addKillSequenceFrame(killed);		
				
			} else if (charPiece.getType() == Piece.DRAUGTH) {
				pedina = new AlienPiece(charPiece, boardHW, this);
				pedina = pedina.loadDraugthFrame();
				//MOVE SEQUENCE 1-3
				FrameInfo[] move = { new FrameInfo(1, 1), new FrameInfo(2, 1), new FrameInfo(3, 1) };
				pedina.addMoveSequenceFrame(move);	
				//MOVE EAT SEQUENCE 1-4
				FrameInfo[] moveat = { new FrameInfo(1, 1),
						new FrameInfo(2, 1), new FrameInfo(3, 1), new FrameInfo(4, 1)};
				pedina.addEatMoveSequenceFrame(moveat);		
				//KILLED SEQUENCE 7-12
				FrameInfo[] killed = { new FrameInfo(0, 1),new FrameInfo(7, 1), new FrameInfo(8, 1), new FrameInfo(9, 1),
						new FrameInfo(10, 1), new FrameInfo(11, 1), new FrameInfo(12, 1)};
				pedina.addKillSequenceFrame(killed); 				

			}

		} else if (Piece.WHITE == playerColor) {
			pedina = new SoldierPiece(charPiece, boardHW, this);
			if (charPiece.getType() == Checker.CHECKER) {
				//MOVE SEQUENCE 1-3
				FrameInfo[] move = { new FrameInfo(7, 1), new FrameInfo(8, 1) };
				pedina.addMoveSequenceFrame(move);
				//MOVE EAT SEQUENCE 2-5
				FrameInfo[] moveat = { new FrameInfo(2, 1), new FrameInfo(3, 1), new FrameInfo(4, 1),
						new FrameInfo(5, 1) };
				pedina.addEatMoveSequenceFrame(moveat);				
				//KILLED SEQUENCE 10-17
				FrameInfo[] killed = { new FrameInfo(10, 1), new FrameInfo(11, 1), new FrameInfo(12, 1),
						new FrameInfo(13, 1), new FrameInfo(14, 1), new FrameInfo(15, 1), new FrameInfo(16, 1),
						new FrameInfo(17, 1) };
				pedina.addKillSequenceFrame(killed);				

			} else if (charPiece.getType() == Piece.DRAUGTH) {
				pedina = new SoldierPiece(charPiece, boardHW, this);
				pedina = pedina.loadDraugthFrame();
				//MOVE SEQUENCE 1-5
				FrameInfo[] move = { new FrameInfo(1, 1), new FrameInfo(2, 1) ,
						new FrameInfo(3, 1), new FrameInfo(4, 1), new FrameInfo(5, 1)};
				pedina.addMoveSequenceFrame(move);
				//MOVE EAT SEQUENCE 1-5
				FrameInfo[] moveat = { new FrameInfo(1, 1), new FrameInfo(2, 1), new FrameInfo(3, 1),
						new FrameInfo(4, 1),new FrameInfo(5, 1)  };				
				pedina.addEatMoveSequenceFrame(moveat);
				//KILLED SEQUENCE 7-10
				FrameInfo[] killed = { new FrameInfo(6, 1), new FrameInfo(7, 1), new FrameInfo(8, 1), new FrameInfo(9, 1),new FrameInfo(10, 1),
						 };
				pedina.addKillSequenceFrame(killed);				

			}

		}
		Reflection reflection = new Reflection();
		reflection.setFraction(0.1);
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(20.0);
		dropShadow.setOffsetX(0.0);
		dropShadow.setOffsetY(0.0);
		dropShadow.setColor(Color.BLACK);
		if (pedina != null) {
			pedina.setEffect(dropShadow);
			//pedina.setEffect(reflection);
			pedina.setOnMouseClicked(new EventSelectionPlayerHandler(this, pedina));
		}

		return pedina;

	}
    
	public SpritePiece buildPedinaLevel2(int playerColor, Piece charPiece) throws Exception {
		if (playerColor != charPiece.getColor())
			throw new Exception("Disegual color");
		SpritePiece pedina = null;

		if (Piece.BLACK == playerColor) {

			pedina = new AlienPiece(charPiece, boardHW, this);
			if (charPiece.getType() == Piece.CHECKER) {
				//MOVE SEQUENCE 0-3
				FrameInfo[] move = { new FrameInfo(0, 1), new FrameInfo(1, 1) , new FrameInfo(2, 1) , new FrameInfo(3, 1) };
				pedina.addMoveSequenceFrame(move);		
				//MOVE EAT SEQUENCE 4-6
				FrameInfo[] moveeat = { new FrameInfo(4, 1), new FrameInfo(5, 1),
						new FrameInfo(6, 1)};
				pedina.addEatMoveSequenceFrame(moveeat); 	
				//KILLED SEQUENCE 7-13
				FrameInfo[] killed = { new FrameInfo(7, 1), new FrameInfo(8, 1), new FrameInfo(9, 1),
						new FrameInfo(10, 1), new FrameInfo(11, 1), new FrameInfo(12, 1), new FrameInfo(13, 1) };				

				pedina.addKillSequenceFrame(killed);		
				
			} else if (charPiece.getType() == Piece.DRAUGTH) {
				pedina = new AlienPiece(charPiece, boardHW, this);
				pedina = pedina.loadDraugthFrame();
				//MOVE SEQUENCE 1-3
				FrameInfo[] move = { new FrameInfo(1, 1), new FrameInfo(2, 1), new FrameInfo(3, 1) };
				pedina.addMoveSequenceFrame(move);	
				//MOVE EAT SEQUENCE 1-4
				FrameInfo[] moveat = { new FrameInfo(1, 1),
						new FrameInfo(2, 1), new FrameInfo(3, 1), new FrameInfo(4, 1)};
				pedina.addEatMoveSequenceFrame(moveat);		
				//KILLED SEQUENCE 7-12
				FrameInfo[] killed = { new FrameInfo(0, 1),new FrameInfo(7, 1), new FrameInfo(8, 1), new FrameInfo(9, 1),
						new FrameInfo(10, 1), new FrameInfo(11, 1), new FrameInfo(12, 1)};
				pedina.addKillSequenceFrame(killed); 				

			}

		} else if (Piece.WHITE == playerColor) {
			pedina = new HelmetSoldierPiece(charPiece, boardHW, this);
			if (charPiece.getType() == Checker.CHECKER) {
				//MOVE SEQUENCE 1-3
				FrameInfo[] move = {new FrameInfo(6, 1), new FrameInfo(7, 1), new FrameInfo(8, 1) };
				pedina.addMoveSequenceFrame(move);
				//MOVE EAT SEQUENCE 2-5
				FrameInfo[] moveat = { new FrameInfo(2, 1), new FrameInfo(3, 1)  };
				pedina.addEatMoveSequenceFrame(moveat);				
				//KILLED SEQUENCE 10-17
				FrameInfo[] killed = { new FrameInfo(10, 1), new FrameInfo(11, 1), new FrameInfo(12, 1),
						new FrameInfo(13, 1), new FrameInfo(14, 1), new FrameInfo(15, 1), new FrameInfo(16, 1),
						new FrameInfo(17, 1) };
				pedina.addKillSequenceFrame(killed);				

			} else if (charPiece.getType() == Piece.DRAUGTH) {
				pedina = new HelmetSoldierPiece(charPiece, boardHW, this);
				pedina = pedina.loadDraugthFrame();
				//MOVE SEQUENCE 1-5
				FrameInfo[] move = { new FrameInfo(1, 1), new FrameInfo(2, 1) ,
						new FrameInfo(3, 1), new FrameInfo(4, 1), new FrameInfo(5, 1)};
				pedina.addMoveSequenceFrame(move);
				//MOVE EAT SEQUENCE 1-5
				FrameInfo[] moveat = { new FrameInfo(1, 1), new FrameInfo(2, 1), new FrameInfo(3, 1),
						new FrameInfo(4, 1),new FrameInfo(5, 1)  };				
				pedina.addEatMoveSequenceFrame(moveat);
				//KILLED SEQUENCE 7-10
				FrameInfo[] killed = { new FrameInfo(6, 1), new FrameInfo(7, 1), new FrameInfo(8, 1), new FrameInfo(9, 1),new FrameInfo(10, 1),
						 };
				pedina.addKillSequenceFrame(killed);				

			}

		}
		Reflection reflection = new Reflection();
		reflection.setFraction(0.1);
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(20.0);
		dropShadow.setOffsetX(0.0);
		dropShadow.setOffsetY(0.0);
		dropShadow.setColor(Color.BLACK);
		if (pedina != null) {
			pedina.setEffect(dropShadow);
			//pedina.setEffect(reflection);
			pedina.setOnMouseClicked(new EventSelectionPlayerHandler(this, pedina));
		}

		return pedina;

	}
	
    public  SpritePiece  buildPedinaLevelX( int color,
            Piece pedinassociated) {
    if(color!=pedinassociated.getColor()) throw new RuntimeException("Disegual color");
    String imagePedina=null;
    SpritePiece pedina=null;    
    if (Piece.BLACK == color) {
         imagePedina = "black_chekers4.png";
         pedina= new MonsterSprite(Piece.BLACK, pedinassociated,  boardHW,imagePedina,this);
         if(pedinassociated.getType()==Piece.DRAUGTH) pedina.loadDraugthFrame();
     } else if (Piece.WHITE == color) {
         imagePedina = "white_cheker_moonsoldier.png";//TODO:fare il liveloo 2
         pedina= new MoonSoldier( pedinassociated,boardHW, this);
         if(pedinassociated.getType()==Piece.DRAUGTH) pedina.loadDraugthFrame();
     }

     if(pedina!=null) {
	     Reflection reflection = new Reflection();
	     reflection.setFraction(0.7);
	     DropShadow dropShadow = new DropShadow();
	     dropShadow.setColor(Color.BLACK);
	     dropShadow.setRadius(20.0);
	     dropShadow.setOffsetX(0.0);
	     dropShadow.setOffsetY(0.0);  
	     
	     pedina.setEffect(dropShadow);
	     pedina.setOnMouseClicked(new EventSelectionPlayerHandler(this,pedina));
     }
     return pedina;

 }     
    
    void transformInDraugth(SpritePiece p)throws Exception {
	    //remove(p);
	    SpritePiece dama=  buildPedina(p.getBoardPieceLink().getColor(), 
	    		p.getBoardPieceLink(), getLevel());
	    		
	    dama.setDraugthTransform(true);
        positionPedina(dama, p.getBoardPieceLink());
        dama.setOnMouseClicked(new EventSelectionPlayerHandler(this,dama));
        add(dama);
        
        replace(  p.getK() , p.getBoardPieceLink().getColor(), dama);        	
        remove(p);
    }
    
     @Override
	public void renderCommad() {
		// TODO Auto-generated method stub
		
	}

	public boolean isLastLevel(){
        return (level>(MAX_LEVEL*MAX_WAVE));
    }
	public static int levelWave(int level) {
	      if(level==1 ||(level % MAX_LEVEL==1 && level>MAX_LEVEL))  return 1;
	      else if(level==2 || level % MAX_LEVEL==2) return  2;
	      else if(level==3 || level % MAX_LEVEL==3) return 3;
	      else if(level==4 || level % MAX_LEVEL==1) return 4;
	      else if(level==5 || level % MAX_LEVEL==0) return 5;
	      else return -1;
		
	  } 
	private int waveFromLevel() {
    	if(level%FXBoard.MAX_LEVEL==0)  return java.lang.Math.round(level/FXBoard.MAX_LEVEL);
    	else return java.lang.Math.round(level/FXBoard.MAX_LEVEL)+1;
    	
		
	}
}
