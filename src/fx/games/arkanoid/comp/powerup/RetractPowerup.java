package fx.games.arkanoid.comp.powerup;

import fx.games.arkanoid.ArkanoidApplication;
import fx.games.arkanoid.comp.Brick;
import javafx.scene.image.Image;

public class RetractPowerup extends Powerup{

	public RetractPowerup(ArkanoidApplication tapp, Brick generated) {
		super(tapp, generated);
		im=new Image(ArkanoidApplication.class.getResourceAsStream("images/small.png"));
		x=generated.x+Brick.WIDTH/2-im.getWidth()/2;
		y=generated.y+Brick.HEIGHT/2;
	}

	@Override
	public void applyPowerup() {
		mother.barwidth*=0.8;
	}

}
