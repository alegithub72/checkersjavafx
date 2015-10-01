/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcdama;

import dama.core.AIDama;
import dama.core.AIDamaExpert;
import dama.core.DamaInterface;
import dama.core.DamaTxt;
import dama.core.Game;
import dama.core.Move;
import dama.core.PedinaChar;
import dama.core.Player;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.PathTransitionBuilder;
import javafx.animation.RotateTransition;
import javafx.animation.RotateTransitionBuilder;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathBuilder;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author ale2s_000
 */
public class BCDamaGraphic extends Application implements dama.core.DamaInterface,dama.core.Console {

    public TextArea t;
    Game game;
    Application app=this;
    FXPMousePlayer player2;
    FXAIPlayer1 player1;
    TextField command;
    List pedinaList[] = new ArrayList[2];
    boolean on = false;
    Group table;
    PedinaGeneral select;
    double widthScreen;
    double heightScreen;
    int widthSprite;
    int heightSprite;
    //PathTransition pathTransition;
    ParallelTransition pt;
    AnimationPedinaMove anim;
    boolean turn;
    ImageView punteImage[] ;
    List movesMouse;
    //RotateTransition rotateTransition;
    public String confirmCommand;
    ImageView missile;
    Stage primaryStage;
    int level=1;
    Text scoreLabel; 
    Text score; 
    Text levelLabel; 
    int point;
    
    void removePuntatori(){
        if(movesMouse!=null) 
        for (int i = 0; i < movesMouse.size(); i++) {
            
            table.getChildren().remove(movesMouse.get(i));
        }
    }
    @Override
    public void makeMove(Move m) {
        if (m.type == Move.MOVE || m.type == Move.DAMAMOVE) {
            PedinaGeneral p = getPedina(m.getP().getX(), m.getP().getY(), m.getP().getColor());
            // p.setX(m.xtY());
            System.out.println("-------cooord nuovbe prima anim--->" + m.x * widthSprite + "," + m.y * heightSprite);
            System.out.println("-------Pedina--->" + p);

            animPlayer(p, m, null);
            p.getPedinaCharAssociated().setX(m.x);
            p.getPedinaCharAssociated().setY(m.y);
            
           /** if (m.type == Move.DAMAMOVE) {
                p.setFrameDama();
                p.getPedinaCharAssociated().setType(PedinaChar.DAMA);

            }*/
        } else if (m.type == Move.EAT || m.type == Move.DAMAEEAT) {
       PedinaGeneral e = getPedina(m.getEated().getX(), m.getEated().getY(), m.getEated().getColor());
            //e.setVisible(false);

        PedinaGeneral p = getPedina(m.getP().getX(), m.getP().getY(), m.getP().getColor());
            if(m.type==Move.EAT && p.pedinaCharAssociated.getColor()==PedinaChar.WHITE && p.pedinaCharAssociated.getType()==PedinaChar.DAMA)
                animDamaEatPlayer(p, m, e);
            else 
            animPlayer(p, m, e);
            //p.setX(m.x*widthSprite);
            //p.setY(m.y*heightSprite);
            /**if (m.type == Move.DAMAEEAT) {
                p.setFrameDama();
                p.getPedinaCharAssociated().setType(PedinaChar.DAMA);
            }*/
            p.getPedinaCharAssociated().setX(m.x);
            p.getPedinaCharAssociated().setY(m.y);

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

    void animDamaEatPlayer(PedinaGeneral p, Move m,PedinaGeneral e){
        missile=new ImageView(new Image("missile2.png"));
        missile.setViewport(new Rectangle2D(0, 0, 64, 22));        
        table.getChildren().add(missile);
        int x=p.pedinaCharAssociated.getX();
        int y=p.pedinaCharAssociated.getY();
        missile.setX(x*widthSprite);
        missile.setY((y*heightSprite));
        Path path = PathBuilder.create()
                .elements(
                    new MoveTo(x*widthSprite+10,y*heightSprite+10),
                    new QuadCurveTo( (x+4*(widthSprite)), (y*heightSprite),
                            (e.pedinaCharAssociated.getX() *widthSprite+20), (e.pedinaCharAssociated.getY()*heightSprite+10)
                            )
                    )
                .build();
      PathTransition  pathTransition = PathTransitionBuilder.create()
                .duration(Duration.seconds(0.5))
                .path(path)
                .node(missile)
                .orientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT)
                .cycleCount(1)
                .autoReverse(true)
                .build();
        //table.getChildren().add(path);
      PathTransition  pathTransition2 = PathTransitionBuilder.create()
                .duration(Duration.seconds(2))
                .path(p.getMovePath(m, widthSprite, heightSprite))
                .node(p)
                .orientation(PathTransition.OrientationType.NONE)
                .cycleCount(1)
                .autoReverse(true)
                .build(); 
        ParallelTransition pt2=new ParallelTransition(pathTransition);
        
        pt=new ParallelTransition(pathTransition2);
        MoveAnimePedinaTimer mapt=new MoveAnimePedinaTimer(3, 4, p, pt2, 0, true, 100,MoveAnimePedinaTimer.MISSILE);
        mapt.start();
        e.setEatedAnimationByDama(pt);
        pathTransition.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
        missile.setVisible(false);
        mapt.stop();
        p.play();
        e.play();
        pt.play();
        event.consume();

      
            }
        });
        p.setMoveAnimation(pt, 0, true);
        p.setFrame(3);
       pt2.play();


        pt.setOnFinished(new GameAnimationHandler(p, m, e, this, widthSprite, heightSprite));
        

    
    
    }
   void animPlayer(PedinaGeneral p, Move m, PedinaGeneral eated) {
         this.on = true;
        RotateTransition  rotateTransition = RotateTransitionBuilder.create()
               // .node(p)
                .duration(p.getAnimDuration())
                .fromAngle(0)
                .toAngle(-720)
                .cycleCount(1)
                .autoReverse(false)
                .build();

        Path path = p.getMovePath(m, widthSprite, heightSprite);
        //table.getChildren().add(path);
        PathTransition pathTransition = PathTransitionBuilder.create()
                .path(path)
                //.node(p.pedina)
                .orientation(PathTransition.OrientationType.NONE)
                .cycleCount(1)
                .autoReverse(false)
                .build();
        this.pt=new ParallelTransition(p,pathTransition);
       
        if(m.type==Move.MOVE || m.type==Move.DAMAMOVE  ) p.setMoveAnimation(pt, 0, true);
        else if(m.type==Move.EAT  || m.type==Move.DAMAEEAT   ) {
           if(p.getPedinaCharAssociated().getColor()==PedinaChar.WHITE)
               pt.getChildren().add(rotateTransition);
           else if(p.getPedinaCharAssociated().getColor()==PedinaChar.BLACK){
             //  rotateTransition.setFromAngle(0);
             //  rotateTransition.setToAngle(90);
             //  rotateTransition.setCycleCount(2);
               
             //  rotateTransition.setAutoReverse(true);
              // pt.getChildren().add(rotateTransition);
           }
               p.setMoveEatAnimation(pt, 0f, true);
        }
        
        if (eated != null) {
            eated.setEatedAnimation(pt);
            eated.play();
            //rotateTransition.play();
        }
        pathTransition.setDuration(p.getAnimDuration());
        p.play();
        pt.play();
       



        pathTransition.setOnFinished(new GameAnimationHandler(p, m, eated, this, widthSprite, heightSprite));

    }

    @Override
    public int[][] getTableTxt() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void renderTable() {
        System.out.println("-------------------->WHITE");
        renderTablePlayer(player2);
        renderTablePlayer(player1);
        System.out.println("-------------------->BLACK");
    }

    public void renderTablePlayer(Player player) {
        List list2 = player.listPedine(game.getDamaSystem(0));
        int color = -1;
        color = decodeColor(player.getColor());
        for (int i = 0; i < list2.size(); i++) {
            heightSprite = (int) heightScreen / 8;
            widthSprite = (int) widthScreen / 8;
            PedinaChar pedinaChar = ((PedinaChar) list2.get(i));
            int x1 = pedinaChar.getX();
            int y1 = pedinaChar.getY();
            double x = x1 * widthSprite;
            double y = y1 * heightSprite;
            System.out.println(" w,h" + widthSprite + "," + heightSprite + "---x,y--->" + x + "," + y + " x1,y1 " + x1 + " ," + y1);
            System.out.println("-----pedinaChar--------->" + pedinaChar);
      PedinaGeneral pedina = PedinaGeneral.buildPedina(64, 44, player.getColor(), pedinaChar, this,level);
                    //new Pedina(64, 44,  player.getColor(),this);
            pedinaList[color].add(pedina);
            pedina.setX(x);
            pedina.setY(y);
            this.table.getChildren().add(pedina);
        }
    }

    PedinaGeneral getPedina(int x, int y, int color) {
        for (int k = 0; k < pedinaList[decodeColor(color)].size(); k++) {
            PedinaGeneral pedina = (PedinaGeneral) pedinaList[decodeColor(color)].get(k);
            if (pedina.pedinaCharAssociated.getX() == x
                    && pedina.pedinaCharAssociated.getY() == y) {
                return (PedinaGeneral) pedinaList[decodeColor(color)].get(k);
            }

        }
        return null;
    }

    int decodeColor(int charcolor) {
        if (charcolor == PedinaChar.BLACK) {
            return 0;
        } else {
            return 1;
        }
    }

    public void initDama(){
        
        table = new Group();
        BackGround backGround = new BackGround();
        table.getChildren().add(backGround);
        widthScreen = backGround.getWidthScreen();
        heightScreen = backGround.getHeightScreen();
        
        //HBox infoPanel=new HBox();
        player1 = new FXAIPlayer1(this.table,this ,level);
        player2 = new FXPMousePlayer(this);
       
        game = new Game(player1, player2);
        game.setDamaSystem((DamaInterface) this);
        this.pedinaList[0] = new ArrayList();
        this.pedinaList[1] = new ArrayList();

        //VBox root=new VBox();
        /**     Image winText = new Image("winwhite.png");
           ImageView imageView = new ImageView();
           
            imageView.setImage(winText);
            imageView.setX(150);
            imageView.setY(250);
            imageView.setScaleX(1);
            imageView.setScaleY(1);
            table.getChildren().add(imageView);*/

        
        command = new TextField();
        Button btn = new Button("Confirm Comand");
        btn.setAlignment(Pos.CENTER);
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
         * quadTo.setX((3*widthSprite)+32); quadTo.setY((3*heightSprite)+22);
         * Path path = PathBuilder.create() .elements( new
         * MoveTo(select.getX()+32,select.getY()+22), quadTo //, //new
         * CubicCurveTo(0, 120, 0, 240, 380, 240) ) .build(); Rectangle rect =
         * new Rectangle (0, 0, 40, 40); rect.setArcHeight(10);
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
        Rectangle rect = new Rectangle (0, heightScreen, widthScreen, 50); 
        //rect.setArcHeight(10);
        rect.setStroke(Color.CADETBLUE);
        rect.setStrokeWidth(4);
        //rect.setStroke();
        rect.setFill(Color.rgb(255, 255, 128));
        scoreLabel=new Text("SCORE:");
        Font f = new Font("Impact", 30);
        score=new Text("0");
                table.getChildren().add(rect);
        score.setX(120);
        score.setY(heightScreen+30);
        scoreLabel.setX(20);
        scoreLabel.setY(heightScreen+30);
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
        
        
        levelLabel=new Text("Level:"+level);
        levelLabel.setX(600);
        levelLabel.setY(heightScreen+30);
        levelLabel.setFont(f);
        levelLabel.setFill(Color.rgb(0, 204, 102));
          levelLabel.setEffect(dropShadow);
        
        table.getChildren().add(score);
        table.getChildren().add(scoreLabel);
        table.getChildren().add(levelLabel);
        
        
    }
    
    @Override
    public void start(Stage primaryStage) {

        initDama();
        
        this.primaryStage=primaryStage;
        Scene scene = new Scene(table, widthScreen,
                heightScreen + 50);
        primaryStage.setTitle("Grafic BC Dama");
        primaryStage.setScene(scene);
        
        primaryStage.setWidth(widthScreen+15);
        primaryStage.setHeight(heightScreen + 90);

      /**  missile=new ImageView(new Image("missile.png"));
        Pedina pedina=getPedina(1, 5, PedinaChar.WHITE);
        missile.setViewport(new Rectangle2D(0, 0, 64, 22));
        //pedina.pedinaCharAssociated.setType(PedinaChar.DAMA);
        pedina.setFrameDama();
        pedina.setFrame(3);
        table.getChildren().add(missile);
        missile.setX(1*widthSprite);
        missile.setY((5*heightSprite));
        Path path = PathBuilder.create()
                .elements(
                    new MoveTo(1*widthSprite+10,5*heightSprite+10),
                    new QuadCurveTo( (5*(widthSprite)), (5*heightSprite),
                            (2*widthSprite+20), (4*heightSprite+10)
                            )
                    )
                .build();
      PathTransition  pathTransition = PathTransitionBuilder.create()
                .duration(Duration.seconds(1))
                .path(path)
                .node(missile)
                .orientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT)
                .cycleCount(5)
                .autoReverse(true)
                .build();
        pathTransition.play();
        table.getChildren().add(path);*/
        
        
        primaryStage.show();
        System.out.println("--------LEVEL-------------------->"+level);

    }

    public void playPlayer1() {
        game.playPlayer1();
    }

    public int winCondition() {
         Image winText = new Image("winwhite.png");
           ImageView imageView = new ImageView();
           int win=game.winCondition();
   if (win == PedinaChar.WHITE) {
            imageView.setImage(winText);
            imageView.setX(150);
            imageView.setY(250);
            imageView.setScaleX(1);
            imageView.setScaleY(1);
            level=2;
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    try {
                        app.start(primaryStage);
                    } catch (Exception ex) {
                        Logger.getLogger(BCDamaGraphic.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    event.consume();
                }
            });
            table.getChildren().add(imageView);
        } else if (win == PedinaChar.BLACK) {
            winText = new Image("winblack.png");
            imageView = new ImageView();
            //background.setFocusTraversable(true);
            imageView.setImage(winText);
            imageView.setX(150);
            imageView.setY(250);
            imageView.setScaleX(1);
            imageView.setScaleY(1);
      
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    try {
                        app.start(primaryStage);
                    } catch (Exception ex) {
                        Logger.getLogger(BCDamaGraphic.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    event.consume();
                }
            });
            table.getChildren().add(imageView);
    }
    return win;
    }

    public void playPlayer2() {
        game.playPlayer2();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){

        launch(args);

    }

    void removePedina(PedinaGeneral p, int color) {
        p.setVisible(false);
        table.getChildren().remove(p);
        pedinaList[decodeColor(color)].remove(p);
    }

    @Override
    public void renderCommand(int i, Move m,int size) {
        if(m!=Move.NULLMOVE){ 
        QuadCurveTo quadTo = new QuadCurveTo();
        double x0 = m.getP().getX() * widthSprite;
        double y0 = m.getP().getY() * heightSprite;
        double inc = 100;
        Color color = Color.CHARTREUSE;
        if (m.x > m.getP().getX()) {
            quadTo.setControlX(x0 + inc);
        } else {
            quadTo.setControlX(x0 - inc);
        }
        quadTo.setControlY(y0 + 22);
        double x1 =(m.x * widthSprite) + 32;
        double y1 = (m.y * heightSprite) + 22;

        quadTo.setX(x1);
        quadTo.setY(y1);
        Path path = PathBuilder.create()
                .elements(
                        new MoveTo(x0 + 32, y0 + 22),
                        quadTo
                //,
                //new CubicCurveTo(0, 120, 0, 240, 380, 240)
                )
                .build();
        path.setStroke(color);
        path.setStrokeWidth(2);
        path.getStrokeDashArray().setAll(5d, 5d);
        if(i==0){
            punteImage = new ImageView[size];
            movesMouse=new ArrayList(size*2);
        }

            punteImage[i]=new ImageView(new Image("puntatore.png"));
            punteImage[i].setOnMouseClicked(new ConfirmCommandEvent(this, i));
            punteImage[i].setX(x1);
            punteImage[i].setY(y1);
            table.getChildren().add(punteImage[i]);
            movesMouse.add(punteImage[i]);
        table.getChildren().add(path);
        movesMouse.add(path);
        System.out.println(i+")"+m);
        
    }
    }
    public void updatePoint(int value){
        this.point=this.point+value;
        score.setText(""+this.point);
    }
    public void resetPoint(){
        this.point=0;
        score.setText(""+this.point);
    }
}
