package movingObject;

import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * Alien2 for the second level; these ones shoot back!
 * @author Sam Walsh, Lyra Lee
 *
 */
public class Alien2 extends AbstractAlien {

	// countdown Timer on when the alien shoots
	int bulletCountDown = 25;
	
	/**
	 * Constructor of the second Alien
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param platformsX - all X coords of dangerous platforms
	 * @param platformsY - all Y coords of dangerous platforms
	 */
	public Alien2(int x, int y, ArrayList<Integer> platformsX, ArrayList<Integer> platformsY) {
		super(x, y, "alien2", platformsX, platformsY);
	}
	
	/**
	 * uses the dimensions to determine if the alien is off-screen or
	 * whether it should look at the platforms.
	 * @param dim - dimensions in X and Y of the game's screen
	 */
	@Override
	public void update(Dimension2D dim) {
		if (topleftX > dim.getWidth() + 16 || topleftX < 0) {
			dead = true;
		} else {
			super.checkPlatformsX();
		}

		if (topleftY <= 0 || topleftY > dim.getHeight() - 68) {
			dead = true;
		} else {
			super.checkPlatformsY();
		}
	}
	
	/**
	 * trigger the countdown for the alien shooting; if its reached then a bullet is fired
	 * @return true - if the countdown reaches 0; signifies a bullet creation
	 */
	public boolean updateShooting() {
		if (bulletCountDown <= 0) {
			bulletCountDown = 40;
			return true;
		} else {
			bulletCountDown -= 1;
			return false;
		}
	}

}
