package movingObject;

import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import stationaryObject.AbstractFallingObject;
import stationaryObject.SpaceshipParts_m1;
import stationaryObject.SpaceshipParts_m2;
import stationaryObject.StationaryObject;

/**
 * Hero object which is controlled by the player
 * @author Sam Walsh, Lyra Lee
 *
 */
public class Hero extends MovingObject {

	BufferedImage img = null;
	BufferedImage livesImg = null;
	String imgLeftGround = "images/hero_standing_left.png";
	String imgLeftFlying = "images/hero_flying_left.png";
	String imgRightGround = "images/hero_standing_right.png";
	String imgRightFlying = "images/hero_flying_right.png";

	int direction;
	int lives;
	boolean isGrabbed;
	//public Bullets bullets;
	public StationaryObject obj;

	/**
	 * Hero constructor 
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param platformsX - All platform X coords which the Hero can bump into.
	 * @param platformsY - All platform Y coords which the Hero can stand on.
	 */
	public Hero(int x, int y, ArrayList<Integer> platformsX, ArrayList<Integer> platformsY) {
		super(x, y, platformsX, platformsY, 64, 96);
		direction = 1;
		lives = 6;
		handleLives();
		// i and j are the topLeft positions of X and Y
		try {
			img = ImageIO.read(new File(imgRightGround));
		} catch (IOException e) {
			System.err.println("Wrong image file for hero dummy");
		}
	}

	/**
	 * removes lives whenever the Hero collideWith something painful.
	 */
	public void handleLives() {
		lives -= 1; // Starts with 5 lives, instantiated with 6 as handleLives() removes 1 when
					// called.
		if (lives == 0) {
			try {
				livesImg = ImageIO.read(new File("images/lives" + lives + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("Wrong image file for lives dummy");
			}
		}
	}

	public int getDirection() {
		return direction;
	}

	public int getLives() {
		return lives;
	}

	/**
	 * Puts the Hero in the spawn point when switching levels.
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 */
	public void spawnIn(int x, int y) {
		lives = 6;
		handleLives();
		topleftX = x;
		topleftY = y;
	}

	/**
	 * Moves the Hero based on X and Y vectors
	 * @param x - X component of a direction vector
	 * @param y - Y component of a direction vector
	 */
	public void move(int x, int y) {
		// x and y are the directions which the topLeft X and Y should move.
		velX = x;
		velY = y;
		normalizeVelocity();
		try {
			if (velX > 0) {
				direction = 1;
			} else if (velX < 0) {
				direction = 2;
			}
			if (velY < 0) {
				if (direction == 1) {
					img = ImageIO.read(new File(imgRightFlying));
				} else {
					img = ImageIO.read(new File(imgLeftFlying));
				}
			} else {
				if (direction == 1) {
					img = ImageIO.read(new File(imgRightGround));
				} else {
					img = ImageIO.read(new File(imgLeftGround));
				}
			}
		} catch (IOException e) {
			System.err.println("Wrong image file for hero dummy");
		}
	}

	@Override
	public void drawOn(Graphics2D g) {
		// TODO Auto-generated method stub
		super.drawOn(g);
		g.drawImage(img, topleftX, topleftY, 64, 96, null); // 485, 635 starting position
		if (lives == 0) {
			g.drawImage(livesImg, 0, 0, 1018, 804, null);
		}
	}

	public void update(Dimension2D dim) {
		if (topleftX > dim.getWidth() - width) {
			topleftX = 0;
		} else if (topleftX < 0) {
			topleftX = (int) (dim.getWidth() - width);
		}
		if (topleftY <= 0) {
			if (velY >= 0) {
				checkPlatformsY();
			}
		} else if (topleftY > dim.getHeight() - 36 - height) {
			if (velY <= 0) {
				checkPlatformsY();
			}
		} else {
			checkPlatformsY();
		}
		if (topleftX >= dim.getWidth() - 4) {
			topleftX = 0;
		} else {
			checkPlatformsX();
		}
	}
	
	/**
	 * Checks if the Hero object is interacting or colliding horizontally with any of the green platforms in the level.
	 * Prevents the Hero from going through it if so.
	 */
	@Override
	protected void checkPlatformsX() {
		try {
			for (int i = 0; i < platformsX.size(); i++) {
				if (topleftY > platformsY.get(i) - 90 && topleftY < platformsY.get(i) + 32) {
					if (topleftX <= platformsX.get(i) && topleftX >= platformsX.get(i) - 48) {
						if (velX >= 0) {
							velX = 0;
						} else {
							throw new Error();
						}
					} else if (topleftX >= platformsX.get(i) && topleftX <= platformsX.get(i) + 28) {
						if (velX <= 0) {
							velX = 0;
						} else {
							throw new Error();
						}
					}
				}
			}
			throw new Error();
		} catch (Error e) {
			topleftX += velX;
		}
	}

	/**
	 * Checks if the Hero object is interacting or colliding vertically with any of the green platforms in the level.
	 * Prevents the Hero from going through it if so.
	 */
	@Override
	protected void checkPlatformsY() {
		try {
			for (int i = 0; i < platformsY.size(); i++) {
				if (topleftX > platformsX.get(i) - 32 && topleftX < platformsX.get(i) + 20) {
					if (topleftY <= platformsY.get(i) + 32 && topleftY >= platformsY.get(i)) {
						if (velY <= 0) {
							velY = 0;
						} else {
							throw new Error();
						}
					} else if (topleftY >= platformsY.get(i) - 96 && topleftY <= platformsY.get(i)) {
						if (velY >= 0) {
							velY = 0;
						} else {
							throw new Error();
						}
					}
				}
			}
			throw new Error();
		} catch (Error e) {
			topleftY += velY;
		}
	}

	/**
	 * Sees if Hero object overlaps with Bullet b and the bullet is not a Hero's
	 * @param b - Bullet Object
	 * @return true - if Hero overlaps with the Bullet object
	 */
	public boolean overlapsWith(Bullets b) {
		if (b.isHeroBullet()) {
			return false;
		}
		return getBoundingBox().intersects(b.getBoundingBox());
	}

	/**
	 * Sees if Hero object overlaps with Alien a
	 * @param a - AbstractAlien object
	 * @return true - if Hero overlaps with the AbstractAlien object
	 */
	public boolean overlapsWith(AbstractAlien a) {
		return getBoundingBox().intersects(a.getBoundingBox());
	}

	/**
	 * Sees if Hero object overlaps with SpaceshipParts_m1
	 * @param other - Spaceship Part 1
	 * @return true - if Hero overlaps with the ship object
	 */
	public boolean overlapsWith(SpaceshipParts_m1 other) {
		return getBoundingBox().intersects(other.getBoundingBox());
	}

	/**
	 * Sees if Hero object overlaps with SpaceshipParts_m2
	 * @param other - Spaceship Part 2
	 * @return true - if Hero overlaps with the ship object
	 */
	public boolean overlapsWith(SpaceshipParts_m2 other) {
		return getBoundingBox().intersects(other.getBoundingBox());
	}

	/**
	 * Sees if Hero overlaps with a fallingObject, like fuel
	 * @param other - FallingObject
	 * @return true - if Hero overlaps with another object
	 */
	public boolean overlapsWith(AbstractFallingObject other) {
		return getBoundingBox().intersects(other.getBoundingBox());
	}

	@Override
	public void normalizeVelocity() {
		// TODO Auto-generated method stub
		super.normalizeVelocity();
	}

	/**
	 * Handles what should happen to the colliding objects; 
	 * Hero loses a life and the Alien dies
	 * @param alien - Alien colliding with the Hero
	 */
	public void collideWith(AbstractAlien alien) {
		alien.setDead(true);
		if (lives > 0) {
			handleLives();
		}
	}

	/**
	 * Handles what should happen to the colliding objects; 
	 * Hero loses a life and the Bullet dies
	 * @param b - Bullet colliding with the Hero
	 */
	public void collideWith(Bullets b) {
		b.setDead(true);
		if (lives > 0) {
			handleLives();
		}
	}

	public void setIsGrabbed(StationaryObject s) { // hero needs to track if an object is grabbed or not
		if (!isGrabbed) {
			isGrabbed = true;
			this.obj = s;
		}
	}

	public boolean getIsGrabbed() {
		return isGrabbed;
	}

	public void setIsDropped() {
		isGrabbed = false;
	}

}
