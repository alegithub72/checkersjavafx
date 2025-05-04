package sa.fx.draugths;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.util.Duration;
import sa.boardgame.console.imp.AutomaPlayer;
import sa.boardgame.console.imp.ConsoleRendering;
import sa.boardgame.core.moves.Move;
import sa.boardgame.core.players.Player;
import sa.fx.draugths.animation.event.EventDraugthTransform;
import sa.fx.draugths.animation.event.EventEatAnimPiece;
import sa.fx.draugths.animation.event.EventRemoveEatPiece;
import sa.fx.draugths.board.event.EventEndTurn;
import sa.fx.draugths.board.event.EventPointUpdate;
import sa.fx.draugths.board.event.EventSelectionPlayerHandler;
import sa.fx.draugths.pieces.*;
import sa.fx.draugths.pieces.aliens.Alien;
import sa.fx.draugths.pieces.aliens.AlienKing;
import sa.fx.draugths.pieces.aliens.SkyAlien;
import sa.fx.draugths.pieces.aliens.SkyAlienKing;
import sa.fx.draugths.pieces.efa.*;
import sa.fx.draugths.players.FXAIPlayer1;
import sa.fx.draugths.players.FXPMousePlayer;
import sa.fx.draugths.screen.*;
import sa.fx.draugths.utility.BoardHW;
import sa.fx.draugths.utility.ScaleFactor;
import sa.fx.draugths.utility.SoundEffect;
import sa.fx.draugths.utility.SoundInterface;
import sa.gameboard.core.Board;
import sa.gameboard.core.Game;
import sa.gameboard.core.Piece;
import sa.gameboard.core.interfaces.GraficBoardInterface;

/**
 *
 * @author Alessio Sardaro
 */
public class FXBoard implements GraficBoardInterface {

	public static final int MAX_WAVE = 12;
	public static final int MAX_LEVEL = 9;
	static final public BoardHW boardHW = new BoardHW(100, 100);
	private Game game;
	private TextField command;

	private List<SpritePiece> pedinaList[];
	private StackPane root;
	private Group zGroup;
	public static SoundInterface SoundSystem;
	private PresentationScreen startScreen;
	private RecordScreen recordScreen;
	private BackGround backGround;
	public static ScaleFactor scaleRoot = null;
	private boolean animationOn = false;
	private SpritePiece select;

	private int level;
	private FXAIPlayer1 computerPlayer;
	private FXPMousePlayer mousePlayer;
	private boolean turn;
	private StackPane sceneStackPanel;

	public FXBoard(int level) {
		initLevel(level);
	}

	public void initLevel(int level) {
		try {

			root = new StackPane();
			zGroup = new Group();
			this.animationOn = false;
			this.pedinaList = new LinkedList[2];
			turn = false;
			this.level = level;
			this.pedinaList[0] = new LinkedList<>();
			this.pedinaList[1] = new LinkedList<>();

			String[] part1 = new String[] { "p55", "0", "p35", "0", "p46", "1", "p24", "1", "p44", "1", "p66", "0",
					"p15", "1", "p26", "0", "p15", "0", "p77", "0", "p33", "1", "p55", "0", "p04", "0", "p22", "0",
					"p11", "0" };
			// String[] part2=new String[]{""};
			BCDraugthsApp.log.info("Log Level:"+BCDraugthsApp.log.getLevel());
			BCDraugthsApp.log.log(Level.INFO,"level up level={0},loger name={1}" ,new Object[] {level,BCDraugthsApp.log.getName()});

			// HBox infoPanel=new HBox();
			computerPlayer = new FXAIPlayer1();
			mousePlayer = new FXPMousePlayer(this);
			AutomaPlayer automa = new AutomaPlayer(mousePlayer.getColor(), "Automa");
			automa.setCommand(part1);

			game = new Game(automa, computerPlayer, Board.CHECKERS_GAME);

			// game.setDamaSystem((DamaInterface) this);
			

			ConsoleRendering console = new ConsoleRendering();
			game.addRenderInterface(console);

			// game.playGame();

			game.setHuman(mousePlayer);
			buildWaveLevel();
			if (BCDraugthsApp.loadScenario) {
				String scen = "boardPedinaBlackTest.txt";
				if (System.getProperty("checkers.fileScenario") != null)
					scen = System.getProperty("checkers.fileScenario");
				File scenFile = new File(scen);
				if (scenFile.exists())
					load(scen);
			}

			if (game != null)
				game.addRenderInterface(this);
			root.addEventHandler(EventPointUpdate.MOVE_UPDATE, new EventHandler<EventPointUpdate>() {

				@Override
				public void handle(EventPointUpdate event) {
					if (event.getEventType() == EventPointUpdate.MOVE_UPDATE) {
						Move m = event.getMove();
						BCDraugthsApp.log.info("HANDLE EventPointUpdate.MOVE_UPDATE-->" + m + " event:" + event);
						backGround.updatePoint(m.calculateValue());
						event.consume();
					}
				}

			});
			root.addEventHandler(EventDraugthTransform.DRAUGTH_EVENT, new EventHandler<EventDraugthTransform>() {

				@Override
				public void handle(EventDraugthTransform event) {
					if (event.getEventType() == EventDraugthTransform.DRAUGTH_EVENT) {
						try {
							SpritePiece p = event.getPiece();
							BCDraugthsApp.log.info("HANDLE EventDraugthTransform.DRAUGTH_EVENT->" + p);
							transformInDraugth(p);
							event.consume();
						} catch (Exception e) {
							BCDraugthsApp.log.log(Level.SEVERE,"EventDraugthTransform.DRAUGTH_EVENT-->",e);
						}
					}
				}

			});

			root.addEventHandler(EventRemoveEatPiece.REMOVE_PIECE_EVENT, new EventHandler<EventRemoveEatPiece>() {

				@Override
				public void handle(EventRemoveEatPiece event) { //
					if (event.getEventType() == EventRemoveEatPiece.REMOVE_PIECE_EVENT) {
						SpritePiece eated = event.getPiece();
						BCDraugthsApp.log.info("EventRemoveEatPiece.REMOVE_PIECE_EVENT ->" + eated + " event:" + event); //
						eated.stopPlayAnimation();
						removeSpritePiece(eated);
						event.consume();
					}
				}

			});

			root.addEventHandler(EventEndTurn.END_TURN, new EventHandler<EventEndTurn>() {

				@Override
				public void handle(EventEndTurn event) {
					if (event.getEventType() == EventEndTurn.END_TURN) {
						BCDraugthsApp.log.info("HANDLE EventEndTurn.END_TURN-->" + event.getEventType().getName() + ","
								+ event.getSrpitePiece().getNameType());
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
			root.addEventHandler(EventEatAnimPiece.KILLPLAY_EVENT, new EventHandler<EventEatAnimPiece>() {

				@Override
				public void handle(EventEatAnimPiece event) {
					if (event.getEventType() == EventEatAnimPiece.KILLPLAY_EVENT) {
						SpritePiece eated = event.getPiece();
						BCDraugthsApp.log.info("HANDLE EventEatAnimPiece.KILLPLAY_EVENT->" + eated);
						// eated.setViewOrder(-1000);
						eated.buildKilledSequence(event.getMove());
						// eated.toFront();
						eated.playKilled();
						// eated=null;
						event.consume();
					}
				}
			});
		} catch (Exception e1) {

			BCDraugthsApp.log.log(Level.SEVERE,"Exception:",e1);
		}
	}

	public void scale() {
		if (scaleRoot != null && scaleRoot.getScaleX() < 1) {

			root.setScaleX(scaleRoot.getScaleX());
			double transalteX = ((1d - scaleRoot.getScaleX()) * 800) / 2;
			root.setTranslateX(-transalteX);
			BCDraugthsApp.log.info("x translate:" + transalteX);
		}

		if (scaleRoot != null && scaleRoot.getScaleY() < 1) {
			root.setScaleY(scaleRoot.getScaleY());
			double transalteY = ((1d - scaleRoot.getScaleY()) * 800) / 2;
			root.setTranslateY(-transalteY);
			BCDraugthsApp.log.info("y translate:" + transalteY);
		}
		BCDraugthsApp.log.info("Scale Factor:" + scaleRoot);

		BCDraugthsApp.log.info("Screeen Size:" + Screen.getPrimary().getVisualBounds().getWidth() + ","
				+ Screen.getPrimary().getVisualBounds().getHeight());

	}



	public PresentationScreen getStartScreen() {
		return startScreen;
	}



	public StackPane getRoot() {
		return root;
	}

	public void setRoot(StackPane root) {
		this.root = root;
	}

	public void levelUp(int level, int point) {
		// root.getChildren().remove(this);

		BCDraugthsApp.log.info("game level=" + level);
		initLevel(level);
		startLevel(point);
		scale();
		sceneStackPanel.getChildren().add(root);

		root.getChildren().remove(startScreen);
		// root.getChildren().add(fxb);

	}

	public void drawRecordScreen(int points) {

		// fxb=new FXBoardClass(0, this);
		root.getChildren().clear();

		recordScreen = new RecordScreen();
		char[] recordName = "AAA".toCharArray();

		if (!recordScreen.addRecordPlayer(new String(recordName), points)) {
			recordScreen.drawTableRecord();
			root.getChildren().add(recordScreen);

			recordScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {

					if (event.getButton() == MouseButton.PRIMARY)
						resetGame();
				}
			});

		} else {

			recordScreen.drawTableRecord();
			root.getChildren().add(recordScreen);
			recordScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {
				int only3 = 0;
				char name = 'A';

				@Override
				public void handle(MouseEvent event) {
					if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() > 1) {
						if (only3 <= 2) {
							recordName[only3] = name;
							only3++;
							name = 'A';
							recordScreen.addRecordPlayer(new String(recordName), points);
							recordScreen.drawTableRecord();
						} else {
							resetGame();
							recordScreen.saveRecordPlayers();
							event.consume();
							BCDraugthsApp.log.info("----FINE------>" + name + "-->" + only3 + "----->" + new String(recordName));
						}

						BCDraugthsApp.log.info("---------->" + name + "-->" + only3 + "----->" + new String(recordName));

					} else {

						if (only3 <= 2) {
							name++;
							if (name > 'Z')
								name = 'A';
							recordName[only3] = name;

						}

						BCDraugthsApp.log.info("---------->" + name + "-->" + only3 + "----->" + new String(recordName));
						recordScreen.addRecordPlayer(new String(recordName), points);
						recordScreen.drawTableRecord();
					}
				}

			});
		}

	}

	private void buildWaveLevel() {
		for (int i = 11; i > (11 - (waveFromLevel() - 1)); i--) {
			Piece p = game.getAI().getBoard().getBlackPieces()[i];
			if (p != null && waveFromLevel() > 1)
				p.setType(Piece.DRAUGTH);
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
			BCDraugthsApp.log.log(Level.SEVERE,"Exception:",e);
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
		 BCDraugthsApp.log.info("--------" + pedinaList[decodeCol].size() +
		 "-------------");
		for (k = 0; k < pedinaList[decodeCol].size(); k++) {
			SpritePiece pedina = pedinaList[decodeCol].get(k);
			if (pedina != null) {
				Piece p = pedina.getBoardPieceLink();

				 BCDraugthsApp.log.info(k + ")" + pedina);
				if (eated && p.getI() == i1 && p.getJ() == j1 && p.isEated()) {
					sp = pedinaList[decodeCol].get(k);

				} else if (!eated && p.getI() == i1 && p.getJ() == j1 && !p.isEated()) {
					sp = pedinaList[decodeCol].get(k);

				}
			} else {
				 BCDraugthsApp.log.info(k + ")sp=null");
			}
			if (sp != null) {
				break;
			}

		}
		// if(sp==null ) System.exit(-1);

		BCDraugthsApp.log.info(" ..The sprite is k=" + k + " ,sprite scelto " + sp + " isEated=" + sp.getBoardPieceLink().isEated());
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
		int z = decodeColor(color);
		pedinaList[z].set(n, p);
	}

	private void drawPiecePlayer(Player player) throws Exception {
		Piece[] list = null;
		if (player.getColor() == Piece.BLACK) {
			list = game.getBoard().getBlackPieces();
		} else {
			list = game.getBoard().getWhitePieces();
		}

		BCDraugthsApp.log.info("BUILD " + player.getName() + " LEVEL=" + this.level);
		for (int i = 0; i < list.length; i++) {

			Piece pedinaChar = list[i];
			if (pedinaChar != null) {

				SpritePiece pedina = buildPedina(player.getColor(), pedinaChar, level);
				positionPedina(pedina, pedinaChar);
				// getChildren().add(pedina);
				zGroup.getChildren().add(pedina);

			} else
				add(null, player.getColor());
		}
	}

	public void positionPedina(SpritePiece pedina, Piece pedinaChar) {
		int j1 = pedinaChar.getJ();
		int i1 = pedinaChar.getI();
		double x = pedina.convertBoardJtoPositionX(j1, boardHW.getW());
		double y = pedina.convertBoardItoPositionY(i1, boardHW.getH());
		pedina.setK(pedinaChar.getPos());
		add(pedina, pedinaChar.getColor());

		BCDraugthsApp.log.info("pos:" + pedinaChar.getPos());
		BCDraugthsApp.log.info("(i,j)=(" + i1 + "," + j1 + ")");
		BCDraugthsApp.log.info("(x,y)=(" + x + "," + y + ")");
		pedina.setX(x);
		pedina.setY(y);
		if (pedinaChar.getColor() == Piece.BLACK && pedinaChar.getType() == Piece.DRAUGTH) {
			BCDraugthsApp.log.info("also scale");
			pedina.setScaleX(0.64);
			pedina.setScaleY(0.64);
			pedina.setW((int) (pedina.getW() * 0.64));
			pedina.setH((int) (pedina.getH() * 0.64));
		} else {
			pedina.setScaleX(0.64);
			pedina.setScaleY(0.64);
			pedina.setW((int) (pedina.getW() * 0.64));
			pedina.setH((int) (pedina.getH() * 0.64));
		}

	}

	public void winConditionMessage(int win) {
		Image winText = null;
		ImageView imageView = new ImageView();
		Text start = new Text("- CLICK HERE TO CONTINUE -");
		start.setFont(new Font(20));
		start.setFill(Color.ANTIQUEWHITE);
		Animation t = new Transition() {
			{
				setCycleCount(Animation.INDEFINITE);
				setCycleDuration(Duration.millis(500));

			}

			@Override
			protected void interpolate(double frac) {
				// BCDraugthsApp.log.info("color="+Color.WHITE.interpolate(Color.BLACK, frac));
				start.setFill(Color.WHITE.interpolate(Color.BLACK, frac));
			}
		};
		t.play();
		if (win == Piece.WHITE) {
			winText = new Image("winwhite.png");

			imageView.setImage(winText);
			imageView.setX(150);
			imageView.setY(250);
			imageView.setScaleX(1);
			imageView.setScaleY(1);
			level++;

		} else if (win == Piece.BLACK) {

			winText = new Image("winblack.png");
			imageView = new ImageView();
			// GLevel.setFocusTraversable(true);
			imageView.setImage(winText);
			imageView.setX(150);
			imageView.setY(250);
			imageView.setScaleX(1);
			imageView.setScaleY(1);
			level = 0;

		}
		start.setX(150 + (imageView.maxWidth(win) / 4));
		start.setY(300 + (imageView.maxHeight(win) / 2));
		root.getChildren().add(imageView);
		root.getChildren().add(start);

		if (winText != null) {
			start.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					if (isLastLevel() || level == 0) {

						if (isLastLevel()) {

							drawEndScreen();

						} else
							drawRecordScreen(backGround.getPoint());

					} else {

						levelUp(level, backGround.getPoint());

					}

					event.consume();
				}
			});

			System.gc();
		}
	}

	public void drawStartScreen() {
		try {

			startScreen = new StartScreen();
			root.getChildren().add(startScreen);
			FXBoard fxb = this;
			startScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() > 1) {
						SoundSystem.playSound(SoundEffect.EFFECT_COIN, 1);
						SoundSystem.stopSound(SoundEffect.MUSIC_SIGLA);
						waitCoin();
						startLevel(level);
						root.getChildren().remove(startScreen);

						// root.getChildren().add(fxb);
						event.consume();
					}
				}
			});
//			startScreen.setOnTouchPressed(new EventHandler<TouchEvent>() {
//
//				@Override
//				public void handle(TouchEvent event) {
//					SoundSystem.stopSound(SoundEffect.MUSIC_SIGLA);
//					startLevel(level);
//					root.getChildren().remove(startScreen);
//					// root.getChildren().add(fxb);
//					event.consume();
//				}
//			});
			SoundSystem.playSoundLoop(SoundEffect.MUSIC_SIGLA);
		} catch (Exception e) {
			BCDraugthsApp.log.log(Level.SEVERE,"Exception:",e);
		}
	}
	private void waitCoin() {
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			BCDraugthsApp.log.log(Level.SEVERE,"Sleep Excption:",e);
		}
	}
	private void resetGame() {

		try {
			// music.play();
			root.getChildren().clear();
			initLevel(1);
			scale();
			sceneStackPanel.getChildren().add(root);
			drawStartScreen();
		} catch (Exception e) {
			BCDraugthsApp.log.log(Level.SEVERE,"Exception:",e);

		}

	}

	public void drawEndScreen() {
		root.getChildren().removeAll();
		SoundSystem.playSound(SoundEffect.MUSIC_CELEBRATION, 1);

		startScreen = new EndScreen();
		EndScreenII ii = new EndScreenII();
		root.getChildren().add(ii);
		root.getChildren().add(startScreen);

		root.setOnMouseClicked(new EventHandler<MouseEvent>() {
			boolean flip = true;

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					flip = !flip;
					startScreen.setVisibleBack(flip);
					SoundSystem.playSound(SoundEffect.EFFECT_HEY, 1);

				} else {
					root.getChildren().remove(startScreen);
					root.getChildren().remove(ii);
					root.setOnMouseClicked(null);
					drawRecordScreen(level);
				}

				event.consume();
			}
		});

	}

	public synchronized void removeSpritePiece(SpritePiece p) {

		remove(p);
		p.setVisible(false);
		int color = p.getBoardPieceLink().getColor();
		int n = p.getK();
		replace(n, color, null);
		BCDraugthsApp.log.info(" remove " + p);
	}

	@Override
	public void drawBoard() throws Exception {

		drawPiecePlayer(computerPlayer);
		drawPiecePlayer(mousePlayer);

	}

	public void startLevel(int point) {
		backGround = new BackGround(level, this, point);
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(backGround);
		scrollPane.setPrefSize(800, 800);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Barre orizzontali
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.addEventFilter(javafx.scene.input.ScrollEvent.SCROLL, event -> {
			event.consume(); // Consuma l'evento per bloccarne l'elaborazione

		});
		scrollPane.vvalueProperty().addListener((observable, oldValue, newValue) -> {

			BCDraugthsApp.log.info("Vvalue cambiato: " + newValue);
		});

		scrollPane.hvalueProperty().addListener((observable, oldValue, newValue) -> {


			BCDraugthsApp.log.info("Hvalue cambiato: " + newValue);
		});

		root.getChildren().add(scrollPane);
		scrollPane.setVvalue(0.9856701030927835);
		scrollPane.setHvalue(0.985360824742268);


		backGround.middleScreen();
		backGround.addPedine(zGroup);

	}

	public void remove(Object o) {
		zGroup.getChildren().remove(o);
	}

	public void add(Node o) {
		zGroup.getChildren().add(o);

	}

	public void updateInterface(String namePlayer, Move m) {
		game.updateInterface(namePlayer, m);
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
			} else {
				animationOn = false;
			}
		}

	}

	public int winCondition() {

		int win = game.winCondition();
		if (win == Piece.WHITE) {
			getBackGround().updatePoint(size(Piece.WHITE) * 10);

		}
		if (win != -1)
			winConditionMessage(win);

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
		// ???? rivedere
		// throw new UnsupportedOperationException("Not supported yet."); //To change
		// body of generated methods, choose Tools | Templates.
	}

	@Override
	public void renderMove(String namePlayer, Move m) {
		if (m != Move.NULLMOVE) {
			SpritePiece p;

			p = getSpritePiece(m.getI1(), m.getJ1(), m.getP().getColor(), false);

			p.play(m);
			// game.getBoard().makeMove(m);
			// turnEnd();
		}

	}

	public SpritePiece buildPedina(int color, Piece charPiece, int level) throws Exception {

		if (FXBoard.levelWave(level) == GLevel.LVL1_JUNGLE.n())
			return buildPedinaLevel1(color, charPiece);
		else if (FXBoard.levelWave(level) == GLevel.LVL7_CITY.n())
			return buildPedinaLevelPolice(color, charPiece);
		else if (FXBoard.levelWave(level) == GLevel.LVL2_DESERT.n())
			return buildPedinaLevel2(color, charPiece);
		else if (FXBoard.levelWave(level) == GLevel.LVL4_FOREST.n())
			return buildPedinaLevel2(color, charPiece);
		else if (FXBoard.levelWave(level) == GLevel.LVL9_MOON.n())
			return buildPedinaLevelMoon(color, charPiece);
		else if (FXBoard.levelWave(level) == GLevel.LVL3_MOUNTAIN.n())
			return buildPedinaLevel2(color, charPiece);
		else if (FXBoard.levelWave(level) == GLevel.LVL6_POLE.n())
			return buildPedinaLevel2(color, charPiece);
		else if (FXBoard.levelWave(level) == GLevel.LVL5_SEA.n())
			return buildPedinaLevel2(color, charPiece);
		else if (FXBoard.levelWave(level) == GLevel.LVL8_SKY.n())
			return buildPedinaLevelSky(color, charPiece);
		else
			return null;
	}

	public SpritePiece buildPedinaLevel1(int playerColor, Piece charPiece) throws Exception {
		if (playerColor != charPiece.getColor())
			throw new Exception("Disegual color");
		SpritePiece pedina = null;

		if (Piece.BLACK == playerColor) {


			if (charPiece.getType() == Piece.CHECKER) {

				pedina = new Alien(charPiece, boardHW, this);

			} else if (charPiece.getType() == Piece.DRAUGTH) {
				pedina = new AlienKing(charPiece, boardHW, this);


			}

		} else if (Piece.WHITE == playerColor) {

			if (charPiece.getType() == Piece.CHECKER) {
				pedina = new Soldier(charPiece, boardHW, this);

			} else if (charPiece.getType() == Piece.DRAUGTH) {
				pedina = new SoldierKing(charPiece, boardHW, this);
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
			// pedina.setEffect(reflection);
			pedina.setOnMouseClicked(new EventSelectionPlayerHandler(this, pedina));
		}

		return pedina;

	}

	public SpritePiece buildPedinaLevelMoon(int playerColor, Piece charPiece) throws Exception {
		if (playerColor != charPiece.getColor())
			throw new Exception("Disegual color");
		SpritePiece pedina = null;

		if (Piece.BLACK == playerColor) {


			if (charPiece.getType() == Piece.CHECKER) {
				pedina = new Alien(charPiece, boardHW, this);

			} else if (charPiece.getType() == Piece.DRAUGTH) {
				pedina = new AlienKing(charPiece, boardHW, this);

			}

		} else if (Piece.WHITE == playerColor) {

			if (charPiece.getType() == Piece.CHECKER) {
				pedina = new MoonSoldier(charPiece, boardHW, this);

			} else if (charPiece.getType() == Piece.DRAUGTH) {
				pedina = new MoonSoldierKing(charPiece, boardHW, this);

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
			// pedina.setEffect(dropShadow);
			// pedina.setEffect(reflection);
			pedina.setOnMouseClicked(new EventSelectionPlayerHandler(this, pedina));
		}

		return pedina;

	}

	public SpritePiece buildPedinaLevel2(int playerColor, Piece charPiece) throws Exception {
		if (playerColor != charPiece.getColor())
			throw new Exception("Disegual color");
		SpritePiece pedina = null;

		if (Piece.BLACK == playerColor) {


			if (charPiece.getType() == Piece.CHECKER) {
				pedina = new Alien(charPiece, boardHW, this);
			} else if (charPiece.getType() == Piece.DRAUGTH) {
				pedina = new AlienKing(charPiece, boardHW, this);


			}

		} else if (Piece.WHITE == playerColor) {
			pedina = new Helmet(charPiece, boardHW, this);
			if (charPiece.getType() == Piece.CHECKER) {

			} else if (charPiece.getType() == Piece.DRAUGTH) {
				pedina = new SoldierKing(charPiece, boardHW, this);



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
			// pedina.setEffect(dropShadow);
			// pedina.setEffect(reflection);
			pedina.setOnMouseClicked(new EventSelectionPlayerHandler(this, pedina));
		}

		return pedina;

	}

	public SpritePiece buildPedinaLevelPolice(int playerColor, Piece charPiece) throws Exception {
		if (playerColor != charPiece.getColor())
			throw new Exception("Disegual color");
		SpritePiece pedina = null;

		if (Piece.BLACK == playerColor) {


			if (charPiece.getType() == Piece.CHECKER) {
				pedina = new Alien(charPiece, boardHW, this);

			} else if (charPiece.getType() == Piece.DRAUGTH) {
				pedina = new AlienKing(charPiece, boardHW, this);


			}

		} else if (Piece.WHITE == playerColor) {
			pedina = new Police(charPiece, boardHW, this);
			if (charPiece.getType() == Piece.CHECKER) {
				// MOVE SEQUENCE 1-3


			} else if (charPiece.getType() == Piece.DRAUGTH) {
				pedina = new PoliceKing(charPiece, boardHW, this);


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
			// pedina.setEffect(dropShadow);
			// pedina.setEffect(reflection);
			pedina.setOnMouseClicked(new EventSelectionPlayerHandler(this, pedina));
		}

		return pedina;

	}

	public SpritePiece buildPedinaLevelSky(int playerColor, Piece charPiece) throws Exception {
		if (playerColor != charPiece.getColor())
			throw new Exception("Disegual color");
		SpritePiece pedina = null;

		if (Piece.BLACK == playerColor) {


			if (charPiece.getType() == Piece.CHECKER) {

				pedina = new SkyAlien(charPiece, boardHW, this);

			} else if (charPiece.getType() == Piece.DRAUGTH) {
				pedina = new SkyAlienKing(charPiece, boardHW, this);

			}

		} else if (Piece.WHITE == playerColor) {
			pedina = new SkySoldier(charPiece, boardHW, this);
			if (charPiece.getType() == Piece.CHECKER) {


			} else if (charPiece.getType() == Piece.DRAUGTH) {
				pedina = new SkySoldierKing(charPiece, boardHW, this);


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
			// pedina.setEffect(dropShadow);
			// pedina.setEffect(reflection);
			pedina.setOnMouseClicked(new EventSelectionPlayerHandler(this, pedina));
		}

		return pedina;

	}

	void transformInDraugth(SpritePiece p) throws Exception {
		// remove(p);
		SpritePiece dama = buildPedina(p.getBoardPieceLink().getColor(), p.getBoardPieceLink(), getLevel());

		dama.setDraugthTransform(true);
		positionPedina(dama, p.getBoardPieceLink());
		dama.setOnMouseClicked(new EventSelectionPlayerHandler(this, dama));
		add(dama);

		replace(p.getK(), p.getBoardPieceLink().getColor(), dama);
		remove(p);
	}

	@Override
	public void renderCommad() {
		BCDraugthsApp.log.info("not implemented");

	}

	public boolean isLastLevel() {
		return (level > (MAX_LEVEL * MAX_WAVE));
	}

	public static int levelWave(int level) {
		if (level == GLevel.LVL1_JUNGLE.n() || level % MAX_LEVEL == GLevel.LVL1_JUNGLE.n())
			return GLevel.LVL1_JUNGLE.n();
		else if (level == GLevel.LVL2_DESERT.n() || level % MAX_LEVEL == GLevel.LVL2_DESERT.n())
			return GLevel.LVL2_DESERT.n();
		else if (level == GLevel.LVL4_FOREST.n() || level % MAX_LEVEL == GLevel.LVL4_FOREST.n())
			return GLevel.LVL4_FOREST.n();
		else if (level == GLevel.LVL7_CITY.n() || level % MAX_LEVEL == GLevel.LVL7_CITY.n())
			return GLevel.LVL7_CITY.n();
		else if (level == GLevel.LVL8_SKY.n() || level % MAX_LEVEL == GLevel.LVL8_SKY.n())
			return GLevel.LVL8_SKY.n();
		else if (level == GLevel.LVL3_MOUNTAIN.n() || level % MAX_LEVEL == GLevel.LVL3_MOUNTAIN.n())
			return GLevel.LVL3_MOUNTAIN.n();
		else if (level == GLevel.LVL6_POLE.n() || level % MAX_LEVEL == GLevel.LVL6_POLE.n())
			return GLevel.LVL6_POLE.n();
		else if (level == GLevel.LVL5_SEA.n() || level % MAX_LEVEL == GLevel.LVL5_SEA.n())
			return GLevel.LVL5_SEA.n();
		else if (level == GLevel.LVL9_MOON.n() || level % MAX_LEVEL == 0)
			return GLevel.LVL9_MOON.n();
		else
			return -1;

	}

	private int waveFromLevel() {
		if (level % FXBoard.MAX_LEVEL == 0)
			return java.lang.Math.round(level / FXBoard.MAX_LEVEL);
		else
			return java.lang.Math.round(level / FXBoard.MAX_LEVEL) + 1;

	}

	public void setSceneStakPanel(StackPane stackPanel) {
		this.sceneStackPanel =stackPanel;
	}
}
