/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths;

import sa.fx.draugths.screen.BackGround;
import sa.fx.draugths.players.FXPMousePlayer;
import sa.fx.draugths.players.FXAIPlayer1;
import sa.fx.draugths.animation.FrameAnimationTimer;
import sa.fx.draugths.animation.AnimationPedinaMove;
import sa.fx.draugths.sprite.SpritePiece;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.ParallelTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sa.boardgame.core.moves.Move;
import sa.boardgame.core.players.AutomaPlayer;
import sa.boardgame.core.players.Player;
import sa.boardgame.shell.imp.ConsoleRendering;
import sa.gameboard.core.Board;
import sa.gameboard.core.Game;
import sa.gameboard.core.Piece;
import sa.gameboard.core.interfaces.GraficBoardInterface;

/**
 *
 * @author ale2s_000
 */
public class BCDraugthsApp extends Application implements GraficBoardInterface {

    private Game game;
    private FXAIPlayer1 computerPlayer;
    private FXPMousePlayer mousePlayer;
    private TextField command;
    private List pedinaList[] = new ArrayList[2];
    private boolean on = false;
    private Group boardGroup;
    private SpritePiece select;
    private double widthScreen;
    private double heightScreen;
    private static final int h = 64;
    private static final int w = 64;
    private ParallelTransition pt;
    private AnimationPedinaMove anim;
    private boolean turn;

    public int wBoardSquare;
    public int hBoardSquare;

    //PathTransition pathTransition;
    public FXPMousePlayer getMousePlayer() {
        return mousePlayer;
    }

    public void setMousePlayer(FXPMousePlayer mousePlayer) {
        this.mousePlayer = mousePlayer;
    }

    public SpritePiece getSelect() {
        return select;
    }

    public void setSelect(SpritePiece select) {
        this.select = select;
    }

    public TextField getCommand() {
        return command;
    }

    public void setCommand(TextField command) {
        this.command = command;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Group getBoardGroup() {
        return boardGroup;
    }

    public void setBoardGroup(Group boardGroup) {
        this.boardGroup = boardGroup;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    //RotateTransition rotateTransition;
    public String confirmCommand;

    Stage primaryStage;
    int level = 0;
    Text scoreLabel;
    Text score;
    Text levelLabel;
    int point;
    ImageView description;
    boolean debug;
    static double scale = 0.78;

    @Override
    public void addBoard(Board b) {
        //this.b=game.getBoard();
    }

    @Override
    public void renderMove(Move m) {
        if (m != Move.NULLMOVE) {
            SpritePiece p = getSpritePiece(m.getI1(),
                    m.getJ1(),
                    m.getP().getColor(), m.getP().isEated());
            p.play(m);
        }
    }

    public void updateInterface(Move m) {
        game.updateInterface(m);
    }

    public void turnEnd() {
        on = false;
        turn = !turn;

        if (winCondition() != Piece.WHITE && winCondition() != Piece.BLACK) {
            if (turn) {
                this.playComputerPlayer();
            }
        }

    }

    void waitAnimation() {
        try {
            if (anim != null) {
                anim.wait();
            }
        } catch (InterruptedException ex) {
            //   Logger.getLogger(FXAIPlayer1.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        ///if(anim!=null)
        // while(anim.getStatus()==Animation.Status.RUNNING)System.out.println("runnigggg");

    }

    @Override
    public void renderTable() {

        renderTablePlayer(mousePlayer);
        renderTablePlayer(computerPlayer);

    }

    public void renderTablePlayer(Player player) {
        Piece[] list = null;
        if (player.getColor() == Piece.BLACK) {
            list = game.getBoard().getBlackPieces();
        } else {
            list = game.getBoard().getWhitePieces();
        }

        int color = -1;
        color = decodeColor(player.getColor());
        for (int i = 0; i < list.length; i++) {
            hBoardSquare = (int) heightScreen / 8;
            wBoardSquare = (int) widthScreen / 8;
            Piece pedinaChar = list[i];
            if(pedinaChar!=null){
            int x1 = pedinaChar.getI();
            int y1 = pedinaChar.getJ();

            double x = (x1 * wBoardSquare) + ((wBoardSquare / 2) - (w / 2));
            double y = (y1 * hBoardSquare) + (hBoardSquare / 2 - (h / 2));

            SpritePiece pedina = SpritePiece.buildPedina(w, h, this.wBoardSquare, this.hBoardSquare, player.getColor(), pedinaChar, this, level);
            pedina.setK(i);
            pedinaList[color].add(pedina);
            pedina.setX(x);
            pedina.setY(y);
            this.boardGroup.getChildren().add(pedina);
            }else pedinaList[color].add(null);
        }
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

                System.out.println(k + ")" + "p.i=" + p.getI() + ",p.j=" + p.getJ() + ",p.color=" + (p.getColor() == Piece.BLACK ? "BLACK" : "WHITE"));
                if (!eated && p.getI() == i1
                        && p.getJ() == j1) {
                    sp = (SpritePiece) pedinaList[decodeCol].get(k);

                } else if (p.getI() == i1
                        && p.getJ() == j1) {
                    sp = (SpritePiece) pedinaList[decodeCol].get(k);

                }
            }else System.out.println(k+")sp=null");
            if (sp != null) {
                break;
            }

        }
        //if(sp==null ) System.exit(-1);

        System.out.println("The sprite is k=" + k);
        return sp;
    }

    int decodeColor(int charcolor) {
        if (charcolor == Piece.BLACK) {
            return 0;
        } else {
            return 1;
        }
    }

    public void initDama() {

        boardGroup = new Group();
        BackGround backGround = new BackGround(level);
        boardGroup.getChildren().add(backGround);
        widthScreen = backGround.getWidthScreen();
        heightScreen = backGround.getHeightScreen();
        if (level > 0) {
        String[] part1=new String[]{"p55","0","p35","0","p46","1"
        ,"p24","1","p44","1","p66","0","p15","1","p26","0","p15","0",
        "p77","0","p33","1","p55","0","p04","0","p22","0","p11","0"};
            String[] part2=new String[]{""};

            //HBox infoPanel=new HBox();
            computerPlayer = new FXAIPlayer1(this.boardGroup, this, level);
            mousePlayer = new FXPMousePlayer(this);
            AutomaPlayer automa=new AutomaPlayer(mousePlayer.getColor(), "Automa");
            automa.setCommand(part1);
            
            game = new Game(automa, computerPlayer);
            //game.setDamaSystem((DamaInterface) this);
            ConsoleRendering console = new ConsoleRendering(game.getBoard());
            game.addRenderInterface(console);
            game.playGame();
            game.setP1(mousePlayer);
            game.addRenderInterface(this);
            this.pedinaList[0] = new ArrayList();
            this.pedinaList[1] = new ArrayList();

            renderTable();

            Rectangle rect = new Rectangle(0, heightScreen - 30, widthScreen, 30);
            //rect.setArcHeight(10);
            rect.setStroke(Color.CHARTREUSE);
            rect.setStrokeWidth(4);
            //rect.setStroke();
            rect.setFill(Color.rgb(255, 255, 128));
            scoreLabel = new Text("SCORE:");
            Font f = new Font(null, 30);
            if (level <= 1) {
                score = new Text("0");
            } else {
                score = new Text("" + this.point);
            }
            boardGroup.getChildren().add(rect);

            score.setX(135);
            score.setY(heightScreen);
            scoreLabel.setX(20);
            scoreLabel.setY(heightScreen);
            score.setFont(f);
            scoreLabel.setFont(f);
            score.setFill(Color.rgb(0, 204, 102));
            scoreLabel.setFill(Color.rgb(0, 204, 102));
            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(20.0);
            dropShadow.setOffsetX(0.0);
            dropShadow.setOffsetY(0.0);

            scoreLabel.setEffect(dropShadow);
            score.setEffect(dropShadow);

            levelLabel = new Text("Level:" + level);
            levelLabel.setX(widthScreen - 180);
            levelLabel.setY(heightScreen);
            levelLabel.setFont(f);
            levelLabel.setFill(Color.rgb(0, 204, 102));
            levelLabel.setEffect(dropShadow);

            boardGroup.getChildren().add(score);
            boardGroup.getChildren().add(scoreLabel);
            boardGroup.getChildren().add(levelLabel);
        }
        if (level > 0) {
            description = new ImageView();
            Image imagesDesc = null;
            if (level == 1) {
                imagesDesc = new Image("desc1.png");
            } else if (level == 2) {
                imagesDesc = new Image("desc2.png");
            }
            description.setImage(imagesDesc);
            boardGroup.getChildren().add(description);
            description.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    boolean b = boardGroup.getChildren().remove(description);

                    event.consume();
                }
            });
        }

    }

    @Override
    public void start(Stage primaryStage) {

        debug = true;
        initDama();
        this.primaryStage = primaryStage;
        AudioClip music = buildMedia(FrameAnimationTimer.MUSIC);

        if (level == 0) {
            music.setCycleCount(AudioClip.INDEFINITE);
            music.play();
        }
        Scene scene = new Scene(boardGroup, Color.BLACK);
        scene.getRoot().setScaleY(scale);
        scene.getRoot().setScaleX(scale);

        Point2D dot = boardGroup.localToParent(0, 0);

        boardGroup.setTranslateX(-dot.getX());
        boardGroup.setTranslateY(-dot.getY());

        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.setWidth(widthScreen * scale);
        primaryStage.setHeight(heightScreen * (scale + 0.05));

        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (level == 0) {
                    level = 1;
                    music.stop();
                    restartGame();

                }
                event.consume();
            }
        });
        if (level == 1) {
            primaryStage.setTitle("Grafic BC Dama:EARTH");
        } else {
            primaryStage.setTitle("Grafic BC Dama:MARS");
        }
        primaryStage.setScene(scene);

        primaryStage.show();

    }

    public void playComputerPlayer() {
        if (game.getP1().getName().equals("Computer")) {
            game.makePlayer1Move();
        } else {
            game.makePlayer2Move();
        }

    }

    public int winCondition() {
        Image winText = new Image("winwhite.png");
        ImageView imageView = new ImageView();
        int win = game.winCondition();
        if (win == Piece.WHITE) {

            imageView.setImage(winText);
            imageView.setX(150);
            imageView.setY(250);
            imageView.setScaleX(1);
            imageView.setScaleY(1);
            updatePoint(pedinaList[decodeColor(Piece.WHITE)].size() * 10);
            level++;
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    restartGame();
                    event.consume();
                }
            });
            boardGroup.getChildren().add(imageView);
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
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    restartGame();
                    event.consume();
                }
            });
            boardGroup.getChildren().add(imageView);
        }
        return win;
    }

    public void playMousePlayer() {
        game.makePlayer2Move();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);

    }

    public void removeSpritePiece(SpritePiece p) {
        p.setVisible(false);
        boardGroup.getChildren().remove(p);
        int color=p.getBoardPieceLink().getColor();
        int n = p.getK();
        pedinaList[decodeColor(color)].set(n, null);
        System.out.println("list after remove"+ pedinaList[decodeColor(color)]);
    }

    public void updatePoint(int value) {
        this.point = this.point + value;
        score.setText("" + this.point);
    }

    public void resetPoint() {
        this.point = 0;
        score.setText("" + this.point);
    }

    private void restartGame() {
        try {
            stop();
            start(primaryStage);
        } catch (Exception ex) {
            Logger.getLogger(BCDraugthsApp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    AudioClip buildMedia(String sound) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(sound);
        return new AudioClip(url.toString());

    }

}
