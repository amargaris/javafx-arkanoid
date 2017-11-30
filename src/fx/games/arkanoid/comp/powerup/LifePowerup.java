package fx.games.arkanoid.comp.powerup;

import fx.games.arkanoid.ArkanoidApplication;
import fx.games.arkanoid.comp.Brick;
import javafx.scene.image.Image;

public class LifePowerup extends Powerup{
	static Image image=new Image(ArkanoidApplication.class.getResourceAsStream("images/heart.png"));
	public LifePowerup(ArkanoidApplication tapp, Brick father) {
		super(tapp, father);
		im=new Image(ArkanoidApplication.class.getResourceAsStream("images/heart.png"));
		x=father.x+Brick.WIDTH/2-im.getWidth()/2;
		y=father.y+Brick.HEIGHT/2;
	}
	public void applyPowerup() {
		mother.score=mother.score+Powerup.SCORE_VALUE_POWERUP;
		mother.lives=mother.lives+1;
		mother.removepws.add(this);
	}
	public static Image getImage(){
		return image;
	}

}
