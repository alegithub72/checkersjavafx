/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.fx.draugths;

import sa.fx.draugths.screen.BackGround;
import sa.fx.draugths.event.FXPMousePlayer;
import sa.fx.draugths.event.FXAIPlayer1;
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
import javafx.scene.control.TextArea;
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

    public TextArea t;
    Game game;
    FXAIPlayer1 computerPlayer;
    FXPMousePlayer mousePlayer;
    TextField command;
    List pedinaList[] = new ArrayList[2];
    boolean on = false;
    Group boardGroup;
    SpritePiece select;
    double widthScreen;
    double heightScreen;
    public int wBoardSquare;
    public int hBoardSquare;
    static final int h = 64;
    static final int w = 64;
    //PathTransition pathTransition;
    ParallelTransition pt;
    AnimationPedinaMove anim;
    boolean turn;

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
        if(m!=Move.NULLMOVE)
            {
            SpritePiece p = getSpritePiece(m.getI1(), 
                    m.getJ1(),
                    m.getP().getColor());
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

    /**
     * void animDamaEatPlayer(PedinaGeneral p, Move m,PedinaGeneral e){
     * removePuntatori(); missile=new ImageView(new Image("missile2.png"));
     * missile.setViewport(new Rectangle2D(0, 0, 64, 22));
     * table.getChildren().add(missile); int x=p.pedinaCharAssociated.getX();
     * int y=p.pedinaCharAssociated.getY(); missile.setX(x*wBoardSquare);
     * missile.setY((y*hBoardSquare)); Path path = PathBuilder.create()
     * .elements(new MoveTo(x*wBoardSquare+10,y*hBoardSquare+10), new
     * QuadCurveTo( (x+4*(wBoardSquare)), (y*hBoardSquare),
     * (e.pedinaCharAssociated.getX() *wBoardSquare+20),
     * (e.pedinaCharAssociated.getY()*hBoardSquare+10) ) ) .build();
     * PathTransition pathTransition = PathTransitionBuilder.create()
     * .duration(Duration.seconds(0.5)) .path(path) .node(missile)
     * .orientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT)
     * .cycleCount(1) .autoReverse(true) .build();
     * //table.getChildren().add(path); PathTransition pathTransition2 =
     * PathTransitionBuilder.create() .duration(Duration.seconds(2))
     * .path(p.getMovePath(m, wBoardSquare, hBoardSquare)) .node(p)
     * .orientation(PathTransition.OrientationType.NONE) .cycleCount(1)
     * .autoReverse(true) .build(); ParallelTransition pt2=new
     * ParallelTransition(pathTransition);
     *
     * pt=new ParallelTransition(pathTransition2); MoveAnimePedinaTimer mapt=new
     * MoveAnimePedinaTimer(3, 4, p, pt2, 0, true,
     * 100,MoveAnimePedinaTimer.MISSILE); mapt.start();
     * e.setEatedAnimationByDama(pt); pathTransition.setOnFinished(new
     * EventHandler<ActionEvent>() {
     *
     * @Override public void handle(ActionEvent event) {
     * missile.setVisible(false); mapt.stop(); p.play(); e.play(); pt.play();
     * event.consume();
     *
     *
     * }
     * }); p.setMoveAnimation(pt, 0, true); p.setFrame(3); pt2.play();
     *
     *
     * pt.setOnFinished(new GameAnimationHandler(p, m, e, this, wBoardSquare,
     * hBoardSquare));
     *
     *
     *
     *
     * }
     */
    /**
     * void animPlayer(PedinaGeneral p, Move m, PedinaGeneral eated) {
     *
     * removePuntatori(); RotateTransition rotateTransition =
     * RotateTransitionBuilder.create() // .node(p)
     * .duration(p.getAnimDuration()) .fromAngle(0) .toAngle(-720)
     * .cycleCount(1) .autoReverse(false) .build();
     *
     * Path path = p.getMovePath(m, wBoardSquare, hBoardSquare);
     * //table.getChildren().add(path); PathTransition pathTransition =
     * PathTransitionBuilder.create() .path(path) //.node(p.pedina)
     * .orientation(PathTransition.OrientationType.NONE) .cycleCount(1)
     * .autoReverse(false) .build(); this.pt=new
     * ParallelTransition(p,pathTransition);
     *
     * if(m.type==Move.MOVE || m.type==Move.DAMAMOVE ) p.setMoveAnimation(pt, 0,
     * true); else if(m.type==Move.EAT || m.type==Move.DAMAEEAT ) {
     * if(p.getPedinaCharAssociated().getColor()==PedinaChar.WHITE)
     * pt.getChildren().add(rotateTransition); else
     * if(p.getPedinaCharAssociated().getColor()==PedinaChar.BLACK){ //
     * rotateTransition.setFromAngle(0); // rotateTransition.setToAngle(90); //
     * rotateTransition.setCycleCount(2);
     *
     * // rotateTransition.setAutoReverse(true); //
     * pt.getChildren().add(rotateTransition); } p.setMoveEatAnimation(pt, 0f,
     * true); }
     *
     * if (eated != null) { eated.setEatedAnimation(pt); eated.play();
     * //rotateTransition.play(); }
     * pathTransition.setDuration(p.getAnimDuration()); pt.play(); p.play();
     *
     *
     *
     *
     *
     * pathTransition.setOnFinished(new GameAnimationHandler(p, m, eated, this,
     * wBoardSquare, hBoardSquare));
     *
     * }
     */
 

    @Override
    public void renderTable() {
       
        renderTablePlayer(mousePlayer);
        renderTablePlayer(computerPlayer);

    }

    public void renderTablePlayer(Player player) {
        Piece[] list=null;
        if(player.getColor()==Piece.BLACK)
            list=game.getBoard().getBlackPieces();
        else list=game.getBoard().getWhitePieces();

        int color = -1;
        color = decodeColor(player.getColor());
        for (int i = 0; i < list.length; i++) {
            hBoardSquare = (int) heightScreen / 8;
            wBoardSquare = (int) widthScreen / 8;
            Piece pedinaChar = list[i];
            int x1 = pedinaChar.getI();
            int y1 = pedinaChar.getJ();

            double x = (x1 * wBoardSquare) + ((wBoardSquare / 2) - (w / 2));
            double y = (y1 * hBoardSquare) + (hBoardSquare / 2 - (h / 2));
         
         
            SpritePiece pedina = SpritePiece.buildPedina(w, h, this.wBoardSquare, this.hBoardSquare, player.getColor(), pedinaChar, this, level);
         
            pedinaList[color].add(pedina);
            pedina.setX(x);
            pedina.setY(y);
            this.boardGroup.getChildren().add(pedina);
        }
    }

    public SpritePiece getSpritePiece(int i1, int j1, int color) {
        int decodeCol=decodeColor(color);
        for (int k = 0; k < pedinaList[decodeCol].size(); k++) {
            SpritePiece pedina = (SpritePiece) pedinaList[decodeCol].get(k);
            if (pedina.getBoardPieceLink().getI() == i1
                    && pedina.getBoardPieceLink().getJ() == j1) {
                return (SpritePiece) pedinaList[decodeCol].get(k);
            }

        }
        return null;
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
            //HBox infoPanel=new HBox();
            computerPlayer = new FXAIPlayer1(this.boardGroup, this, level);
            mousePlayer = new FXPMousePlayer(this);

            game = new Game(mousePlayer, computerPlayer);
            //game.setDamaSystem((DamaInterface) this);
            ConsoleRendering console=new ConsoleRendering(game.getBoard());
            game.addRenderInterface(console);
            game.addRenderInterface(this);
            this.pedinaList[0] = new ArrayList();
            this.pedinaList[1] = new ArrayList();

            //VBox root=new VBox();
            /**
             * Image winText = new Image("winwhite.png"); ImageView imageView =
             * new ImageView();
             *
             * imageView.setImage(winText); imageView.setX(150);
             * imageView.setY(250); imageView.setScaleX(1);
             * imageView.setScaleY(1); table.getChildren().add(imageView);
             */
            //command = new TextField();
            //Button btn = new Button("Confirm Comand");
            //btn.setAlignment(Pos.CENTER);
            //btn.setOnAction(player1);
            //root.getChildren().add(table);
            //infoPanel.getChildren().add(command);
            //infoPanel.getChildren().add(btn);
            //root.getChildren().add(infoPanel);
            renderTable();

            //select.setAnimation(0,6, widthSprite, heightSprite);
            //select.play();
            //table.getChildren().add(select);
            /**
             * QuadCurveTo quadTo = new QuadCurveTo();
             * quadTo.setControlX(select.getX()-100);
             * quadTo.setControlY(select.getY()+22);
             * quadTo.setX((3*widthSprite)+32);
             * quadTo.setY((3*heightSprite)+22); Path path =
             * PathBuilder.create() .elements( new
             * MoveTo(select.getX()+32,select.getY()+22), quadTo //, //new
             * CubicCurveTo(0, 120, 0, 240, 380, 240) ) .build(); Rectangle rect
             * = new Rectangle (0, 0, 40, 40); rect.setArcHeight(10);
             * rect.setArcWidth(10); rect.setFill(Color.ORANGE);
             * //table.getChildren().add(select.pedina);
             * table.getChildren().add(path);
             *
             * pathTransition = PathTransitionBuilder.create()
             * .duration(Duration.seconds(1)) .path(path) .node(select.pedina)
             * .orientation(PathTransition.OrientationType.NONE) .cycleCount(1)
             * .autoReverse(false) .build(); //.build();
             * //root.getChildren().add(path);
             *
             */
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
       if(game.getP1().getName().equals("Computer"))
           game.makePlayer1Move();
       else game.makePlayer2Move();
               
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

   public  void removeSpritePiece(SpritePiece p, int color) {
        p.setVisible(false);
        boardGroup.getChildren().remove(p);
        pedinaList[decodeColor(color)].remove(p);
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
