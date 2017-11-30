package fx.games.arkanoid.comp;
import java.util.Random;

import fx.games.arkanoid.ArkanoidApplication;
import fx.games.arkanoid.comp.powerup.Powerup;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Brick {
	public Color col;
	public double x,y;
	public int state;
	private Random rand;
	private ArkanoidApplication mother;
	
	public static final int STATE_FRESH=0;
	public static final int STATE_BROKEN=1;
	
	public static final double WIDTH=20;
	public static final double HEIGHT=20;
	public static final double PAD=15;
	
	public final double threshold=2;
	
	public static final int SCORE_VALUE_CRUNCH=5;
	public static final int SCORE_VALUE_BREAK=20;
	
	public Brick(double xx,double yy,Color color,ArkanoidApplication themother){
		x=xx;
		y=yy;
		col=color;
		state=Brick.STATE_FRESH;
		mother=themother;
		rand=new Random();
	}
	public void paint(GraphicsContext cx){
		if(state==Brick.STATE_FRESH){
			cx.setFill(col);
			cx.fillRect(x, y,WIDTH, HEIGHT);
		}
		else{
			cx.setFill(Color.BLACK);
			cx.fillRect(x, y,WIDTH, HEIGHT);
		}
		cx.setFill(Color.BLACK);
		cx.strokeRect(x, y, WIDTH, HEIGHT);
	}
	public boolean collide(double x1,double y1){
		if(x1>x&&x1<=x+WIDTH){
			double diff =Math.abs(y-y1);
			if(diff<=threshold){
			//if(y>=y1&&y<=y1+HEIGHT){
				if(state==Brick.STATE_FRESH){
					state=Brick.STATE_BROKEN;
					//mother.by=mother.by+mother.bv;
					//mother.by=mother.by+mother.bdy*mother.bv;
					mother.ballReverse();
					
					mother.score = mother.score+Brick.SCORE_VALUE_CRUNCH;
					//return true;
				}
				else if(state==Brick.STATE_BROKEN){
					mother.remove.add(this);
					brickBreak();
					//mother.by=mother.by+mother.bv;
					//mother.by=mother.by+mother.bdy*mother.bv;
					mother.ballReverse();
					
					mother.score= mother.score+Brick.SCORE_VALUE_BREAK;
					//brickBreak();
					//return true;
				}
			}
		}
		
		if(y1>y&&y1<=y+HEIGHT){
			double diff=Math.abs(x-x1);
			if(diff<=threshold){
				if(state==Brick.STATE_FRESH){
					state=Brick.STATE_BROKEN;
					mother.by=mother.by+mother.bv;
					mother.ballReverse2();
					mother.score = mother.score+Brick.SCORE_VALUE_CRUNCH;
					//return true;
				}
				else if(state==Brick.STATE_BROKEN){
					mother.remove.add(this);
					brickBreak();
					mother.by=mother.by+mother.bv;
					mother.ballReverse2();
					mother.score=mother.score+Brick.SCORE_VALUE_BREAK;
					//return true;
				}
			}
		}
		
		return false;
	}
	private void brickBreak(){
		if(rand.nextDouble()<mother.powerupChance){
			//Powerup pr = new Powerup(mother,rand.nextInt(2),this);
			Powerup gl =Powerup.create(rand.nextInt(Powerup.list.length), mother, this);
			mother.pws.add(gl);
		}
	}
}
