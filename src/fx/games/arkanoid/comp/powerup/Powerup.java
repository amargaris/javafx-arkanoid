package fx.games.arkanoid.comp.powerup;

import fx.games.arkanoid.ArkanoidApplication;
import fx.games.arkanoid.comp.Brick;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Powerup {
	private Brick father;
	protected ArkanoidApplication mother;
	protected double x;
	protected double y;
	protected Image im;
	public static final int SCORE_VALUE_POWERUP=10;
	public static final Class<?>[] list={
		LifePowerup.class,
		SpeedPowerup.class,
		ExpandPowerup.class,
		RetractPowerup.class};
	public static Powerup create(int index,ArkanoidApplication tapp,Brick br){
		Powerup p =null;
		Class<?> c=list[index];
		try{
			p=(Powerup)c.getConstructors()[0].newInstance(tapp,br);
		}catch(Exception e){
			e.printStackTrace();
		}
		return p;
	}
	public Powerup(ArkanoidApplication tapp,Brick generated){
		mother=tapp;
		father=generated;
		x=father.x;
		y=father.y;
	}
	public void draw(GraphicsContext cx){
		cx.drawImage(im,x,y);
	}
	public abstract void applyPowerup();
	public void applyGravity(){
		y = y+0.5;
	}
	public void checkWithBar(){
		if(x>=mother.x-(mother.barwidth/2)&&x<=mother.x+(mother.barwidth/2)){
			if(y<=mother.y&&y>=mother.y-10){
				applyPowerup();
				mother.removepws.add(this);
			}
		}
	}
}
