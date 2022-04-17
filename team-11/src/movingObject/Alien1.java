package movingObject;

import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * Alien1 for the first level
 * @author Sam Walsh, Lyra Lee
 *
 */
public class Alien1 extends AbstractAlien { // Alien that does not shoot bullets

	/**
	 * Constructor which creates our level 1 Alien
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param platformsX - all X coords of dangerous platforms
	 * @param platformsY - all Y coords of dangerous platforms
	 */
	public Alien1(int x, int y, ArrayList<Integer> platformsX, ArrayList<Integer> platformsY) {
		super(x, y, "alien1", platformsX, platformsY);
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
}
