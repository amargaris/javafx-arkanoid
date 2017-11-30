package fx.games.arkanoid;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import fx.games.arkanoid.comp.Brick;
import fx.games.arkanoid.comp.powerup.LifePowerup;
import fx.games.arkanoid.comp.powerup.Powerup;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;


public class ArkanoidApplication extends Application implements EventHandler<MouseEvent>{
		//private ObservableList<String> ob1=null;
		private Canvas canvas;
		private Timeline timeLine;
		public double x=50,y=290;
		public double barheight=10;
		public double barwidth=ArkanoidApplication.default_barwidth;
		public static final double default_barwidth=50;
		public double bx=150,by=150;
		public double bdx=0,bdy=1;
		public double bv=1;
		public int canvasx=300;
		public int canvasy=300;
		public int lives=5;
		public int numberOfBricks =70;
		public int score=0;
		public double powerupChance=0.9;
		
		public String paused="Paused!";
		
		private Image ball = new Image(getClass().getResourceAsStream("images/ball.png"));

		public ArrayList<Brick> obs=new ArrayList<Brick>();
		public ArrayList<Brick> remove=new ArrayList<Brick>();
		public ArrayList<Powerup> pws = new ArrayList<Powerup>();
		public ArrayList<Powerup> removepws = new ArrayList<Powerup>();

	    public static void main(String[] args) {
	        launch(args);
	    }
	    
	    //starting point for the application
	    //this is where we put the code for the user interface
	    @Override
	    public void start(Stage primaryStage) {
	        //The primaryStage is the top-level container
	        primaryStage.setTitle("Arkanoid Game");
	        primaryStage.centerOnScreen();
	        //The BorderPane has the same areas laid out as the
	        //BorderLayout layout manager
	        BorderPane componentLayout = new BorderPane();
	        componentLayout.setPadding(new Insets(0,0,0,0));
	        
	        createStage();
	        final Image back2 = new Image(getClass().getResourceAsStream("images/back.png"),canvasx,canvasy,false,false);
	       // back2.
	        //The FlowPane is a conatiner that uses a flow layout
	        //final FlowPane choicePane = new FlowPane();
	        //choicePane.setHgap(100);
	        
	       // Label choiceLbl = new Label("Fruits");
	        /*
	        final Slider slide = new Slider(1,10,1);
	        slide.valueProperty().addListener(new ChangeListener<Number>(){
				@Override
				public void changed(ObservableValue<? extends Number> arg0,Number arg1, Number arg2) {
					double d=Double.parseDouble(arg0.getValue().toString());
					bv=(int)d;
				}
	        });
	        //The choicebox is populated from an observableArrayList
	        /*final ObservableList<String>list1=FXCollections.observableArrayList("Asparagus", "Beans", "Broccoli", "Cabbage"
	   	         , "Carrot", "Celery", "Cucumber", "Leek", "Mushroom"
		         , "Pepper", "Radish", "Shallot", "Spinach", "Swede"
		         , "Turnip");*/
	        //ChoiceBox<String> fruits = new ChoiceBox<String>(list1);
	        canvas = new Canvas(canvasx,canvasy);
	        canvas.setOnMouseMoved(this);
	        //choicePane.getChildren().add(canvas);
	       // choicePane.getChildren().add(slide);
	        //put the flowpane in the top area of the BorderPane
	        componentLayout.setCenter(canvas);///choicePane);
	        /*
	        final FlowPane listPane = new FlowPane();
	        //listPane.setHgap(10);
	        Label listLbl = new Label("Vegetables");
	        final TextArea txt = new TextArea();
	        txt.setPromptText("Enter your thoughts in here");
	        txt.setPrefColumnCount(10);
	        txt.setOnKeyPressed(new EventHandler<KeyEvent>(){
				@Override
				public void handle(KeyEvent arg0) {
					if(arg0.getCode()==KeyCode.F1){
						txt.clear();
					}
					else if(arg0.getCode()==KeyCode.ENTER){
						String s=txt.getText();
						ob1.add(s);
						txt.clear();
					}
				}
	        	
	        });
	        Image imag = new Image("http://docs.oracle.com/javafx/javafx/images/javafx-documentation.png");
	        ImageView im= new ImageView(imag);
	        ob1=FXCollections.observableArrayList("Apple", "Apricot", "Banana"
	   	         ,"Cherry", "Date", "Kiwi", "Orange", "Pear", "Strawberry");
	        final ListView<String> vegetables = new ListView<String>(ob1);
	        final Button applyselection = new Button("Remove");
	        applyselection.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent arg0) {
					ob1.remove(vegetables.getSelectionModel().getSelectedItem());
				}
	        	
	        });
	        listPane.getChildren().add(listLbl);
	        listPane.getChildren().add(vegetables);
	        listPane.getChildren().add(im);
	        listPane.getChildren().add(txt);
	        listPane.getChildren().add(applyselection);
	        listPane.setVisible(false);
	        */
	        //~~~~~~~~~~~~~~~~~
	        timeLine = new Timeline();
	        timeLine.setCycleCount(Timeline.INDEFINITE);
	        timeLine.getKeyFrames().add(
	                new KeyFrame(Duration.millis(10), 
	                    new EventHandler<ActionEvent>() {
	                        @Override
	                        public void handle(ActionEvent t) {
	                        	GraphicsContext cx = canvas.getGraphicsContext2D();
	                        	cx.setFill(Color.BLACK);
	                        	cx.drawImage(back2,0.0 ,0.0);
	                        	cx.fillRoundRect(x-barwidth/2, y-(barheight/2), barwidth, barheight,5.0,5.0);
	                        	cx.setFill(Color.RED);
	                        	cx.drawImage(ball,bx-ball.getWidth()/2,by-ball.getHeight()/2 );
	                            for(Brick br:obs){
	                            	br.paint(cx);
	                            }
	                            for(Powerup pr:pws){
	                            	pr.draw(cx);
	                            }
	                            cx.setFill(Color.BLACK);
	                            cx.setTextAlign(TextAlignment.LEFT);
	                            cx.strokeText("Lives:", 2, 10);
	                            //Draw lives
	                            if(lives<6){
	                            	for(int i=0;i<lives;i++){
	                            		cx.drawImage(LifePowerup.getImage(), 30+i*15, 0);
	                            	}
	                            }
	                            else{
	                            	cx.drawImage(LifePowerup.getImage(), 33, 0);
	                            	cx.strokeText("x "+lives, 50, 10);
	                            }
	                            //Draw score
	                            cx.setTextAlign(TextAlignment.CENTER);
	                            cx.strokeText("Score: "+score, canvasx/2, 10);
	                            cx.setTextAlign(TextAlignment.RIGHT);
	                            cx.strokeText("Speed: "+bv, canvasx, 10);
	                            if(paused!=null){
	                            	cx.setTextAlign(TextAlignment.CENTER);
	                            	cx.strokeText(paused,canvasx/2,canvasy/2);
	                            	cx.setTextAlign(TextAlignment.LEFT);
	                            }
	                        }
	                    }, 
	                    new KeyValue[0]) // don't use binding
	        );
	        timeLine.playFromStart();
	        final Timer timer = new Timer();
	        timer.scheduleAtFixedRate(new TimerTask() {
	            @Override
	            public void run() {
	            	if(paused!=null){
	            		return;
	            	}
	               // System.out.print("I would be called every 2 seconds");
	                if(bdx*bv+bx<=canvas.getWidth()&&bdx*bv+bx>=0){
						}
						else{
							bdx=-bdx;
						}
						//Tabani
						if(bdy*bv+by<=canvas.getHeight()&&bdy*bv+by>=0){
							
						}
						else{
							bdy=-bdy;
						}
						//Check for collision with each brick
						for(Brick br:obs){
							if(br.collide(bx+ball.getWidth()/2, by+ball.getHeight()/2)){
								//break;
							}
						}
						//Check for broken Bricks
						for(Brick br:remove){
							obs.remove(br);
						}
						remove.clear();
						//Move the powerups
						for(Powerup pr:pws){
							pr.applyGravity();
							pr.checkWithBar();
						}
						//Clear lost powerups
						for(Powerup pr:removepws){
							pws.remove(pr);
						}
						removepws.clear();
						//Bara
						if(bx>x-barwidth/2&&bx<x+barwidth/2){
							if(by>y-barheight){
								bdy=-bdy;
								bdx=-(x-bx)/(barwidth/2);
							}
						}
						by=by+bdy*bv;
						bx=bx+bdx*bv;
						if(by>=canvasy-barheight+1){
							lives=lives-1;
							if(lives==0){
								paused="Game Over!\nYour Score is: "+score+"\nRetry? (y/n)";
							}
							else{
								failed();
								
							}
						}
						if(bx<0||bx>=canvasx||by<0||by>=canvasy){
							resetBall();//Failsafe mechanism
						}
	            }
	        }, 0, 10);
	        
	        //~~~~~~~~~~~~~~~~~
	        //componentLayout.setCenter(listPane);
	        
	        //The button uses an inner class to handle the button click event
	        /*
	        Button vegFruitBut = new Button("Fruit or Veg");
	        vegFruitBut.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					choicePane.setVisible(!choicePane.isVisible());
	                listPane.setVisible(!listPane.isVisible());
				}
	        });
	        componentLayout.setBottom(vegFruitBut);*/
	        //Add the BorderPane to the Scene
	        Scene appScene = new Scene(componentLayout,canvasx,canvasy);
	        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
				@Override
				public void handle(WindowEvent arg0) {
					timer.cancel();
				}
	        });
	        primaryStage.setResizable(false);
	        primaryStage.setScene(appScene);
	        primaryStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>(){
				@Override
				public void handle(KeyEvent arg0) {
					if(arg0.getCode()==KeyCode.A){
						x=x-3;
					}
					else if(arg0.getCode()==KeyCode.D){
						x=x+3;
					}
					else if(arg0.getCode()==KeyCode.F1){
						reset();
					}
					if(paused==null){
						if(arg0.getCode()==KeyCode.SPACE){
							paused="Paused!";
						}
					}
					else{
						if(paused.equalsIgnoreCase("Paused!")){
							if(arg0.getCode()==KeyCode.SPACE){
								paused=null;
							}
						}
						else{
							if(paused.indexOf("Game")!=-1){
								if(arg0.getCode()==KeyCode.Y){
									reset();
								}
								else if(arg0.getCode()==KeyCode.N){
									System.exit(0);
								}
								else{
									
								}
							}
						}
					}
				}
	        	
	        });
	        primaryStage.show();
	    }
	    public void failed(){
	    	resetBall();
			paused="Paused!";
			pws.clear();
			barwidth=default_barwidth;
	    }
		@Override
		public void handle(MouseEvent arg0) {
			try{
				double d=arg0.getX();
				if((d+barwidth/2<=canvasx)&&(d-barwidth/2>=0)){
					x=arg0.getX();
				}
			}catch(Exception e){
				
			}
		}
		public void ballReverse(){
			bdy=-bdy;
		}
		public void ballReverse2(){
			bdx=-bdx;
		}
		public void createStage(){
			obs.clear();
			 Random rand = new Random();
		        for(int i=0;i<numberOfBricks;i++){
		        	double d1=i*Brick.WIDTH;
		        	int int1=(int)d1;
		        	int int2=int1%canvasx;
		        	double d2=(double)int2;
		        	int test2 = int1/canvasy;
		        	double ddd=(double)test2*Brick.HEIGHT+Brick.PAD;
		        	Color col = new Color(rand.nextDouble(),rand.nextDouble(),rand.nextDouble(),1);
		        	Brick temp = new Brick(d2,ddd,col,this);
		        	obs.add(temp);
		        }
		}
		public void reset(){ 
			paused="Paused!";
			score=0;
			lives=5;
			resetBall();
			createStage();
			pws.clear();
		}
		public void resetBall(){
			bv=1;
			bdx=0;
			bdy=1;
			bx=canvasx/2;
			by=canvasy/2;
		}
	}