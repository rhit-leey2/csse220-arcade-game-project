package movingObject;

import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import stationaryObject.Platforms;

/**
 * Abstract class of the aliens which try to kill the hero!
 * @author Sam Walsh, Lyra Lee
 *
 */
public abstract class AbstractAlien extends MovingObject {

	BufferedImage img = null;
	private int direction;

	public static final int ALIEN_SPEED = 5;

	/**
	 * Abstract Alien Constructor
	 * @param x - location in X direction
	 * @param y - location in Y direction
	 * @param type - which Alien is it? Determines image used
	 * @param platformsX - All X coords of platforms the aliens can die to
	 * @param platformsY - All Y coords of platforms the aliens can die to
	 */
	public AbstractAlien(int x, int y, String type, ArrayList<Integer> platformsX, ArrayList<Integer> platformsY) {
		super(x, y, platformsX, platformsY, 60, 48);
		topleftX = x;
		topleftY = y;
		try {
			if (x < 100) {
				if (type.equals("alien1")) {
					img = ImageIO.read(new File("images/Alien1_right.png"));
				} else {
					img = ImageIO.read(new File("images/Alien2_right.png"));
				}
				velX = 8;
				velY = 2;
				direction = 1;
			} else {
				if (type.equals("alien1")) {
					img = ImageIO.read(new File("images/Alien1_left.png"));
				} else {
					img = ImageIO.read(new File("images/Alien2_left.png"));
				}
				direction = 2;
				velX = -8;
				velY = 2;
			}
		} catch (IOException e) {
			System.err.println("Wrong photo dummy");
		}
	}

	/**
	 * Draws the alien on the JComponent
	 * @param g - Graphics that enable the drawing on the Component
	 */
	public void drawOn(Graphics2D g) {
		super.drawOn(g);
		g.drawImage(img, topleftX, topleftY, width, height, null);
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

		if (topleftY <= 0 || topleftY > dim.getHeight() - 36 - height) {
			dead = true;
		} else {
			super.checkPlatformsY();
		}
	}

	// Make sure that velocity remains fixed and non-zero
	public void normalizeVelocity() {
		// move down, right if velocity is set to zero
		if (this.velX == 0 && this.velY == 0) {
			this.velX = 1;
			this.velY = 1;
		}
	}

	public int getDirection() {
		return direction;
	}

	/**
	 * Sees if hero overlaps with an alien
	 * @param h - Hero object
	 * @return true if alien is in the hitbox of the Hero
	 */
	public boolean overlapsWith(Hero h) {
		return getBoundingBox().intersects(h.getBoundingBox());
	}

	/**
	 * Sees if a bullet overlaps with an alien
	 * @param b - Bullet object
	 * @return true if alien is in the hitbox of the Bullet & The bullet is from the Hero
	 */
	public boolean overlapsWith(Bullets b) {
		if (b.isHeroBullet()) {
			return getBoundingBox().intersects(b.getBoundingBox());
		} else {
			return false;
		}
	}

	/**
	 * Sees if another alien overlaps with an alien
	 * @param a - AbstractAlien
	 * @return true if alien is in the hitbox of the other alien
	 */
	public boolean overlapsWith(AbstractAlien a) {
		return getBoundingBox().intersects(a.getBoundingBox());
	}

	/**
	 * Acts on if the Hero is colliding with the alien.
	 * @param h - Hero object
	 */
	public void collideWith(Hero h) {
		h.handleLives();
		markToRemove();
	}

	/**
	 * Acts on if the alien is colliding with the other alien.
	 * @param h - Hero object
	 */
	public void collideWith(AbstractAlien a1) {
		markToRemove();
		a1.markToRemove();
	}

	/**
	 * Acts on if the alien is colliding with the bullet.
	 * @param b - Bullet object
	 */
	public void collideWith(Bullets b) {
		b.setDead(true);
		markToRemove();
	}
}
