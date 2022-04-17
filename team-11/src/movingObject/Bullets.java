package movingObject;

import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * The damaging bullets in the game
 * @author Sam Walsh, Lyra Lee
 *
 */
public class Bullets extends MovingObject {
	BufferedImage img = null;
	String imgLeftPath = "images/bullet_left.png";
	String imgRightPath = "images/bullet_right.png";

	boolean heroBullet;
	
	/**
	 * Constructor of the bullet object
	 * 
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param direction - which way the bullet should go: 1 = right, 2 = left
	 * @param platformsX - all X coords of dangerous platforms
	 * @param platformsY - all Y coords of dangerous platforms
	 * @param heroBullet - true if the bullet was spawned by a Hero
	 */
	public Bullets(int x, int y, int direction, ArrayList<Integer> platformsX, ArrayList<Integer> platformsY, boolean heroBullet) {
		// 1 is right, 2 is left for int direction
		super(x, y, platformsX, platformsY, 16, 4);
		this.heroBullet = heroBullet;
		//this.hero = hero;
		try {
			if (direction == 1) {
				velX = 16;
				img = ImageIO.read(new File("images/bullet_right.png"));
			} else if (direction == 2) {
				velX = -16;
				img = ImageIO.read(new File("images/bullet_left.png"));
			}
		} catch (IOException e) {
			System.err.println("something's wrong with the bullet dumdum");
		}
	}

	public boolean isHeroBullet() {
		return heroBullet;
	}

	//Moves the bullet in the velX direction
	public void move(int x, int y) {
		// x and y are the directions which the topLeft X and Y should move.
		velX = x;
		velY = 0;
		normalizeVelocity();
		// Review this later based on what x and y are submitted with.
		// moves
	}

	@Override
	public void drawOn(Graphics2D g) {
		// TODO Auto-generated method stub
		super.drawOn(g);
		g.drawImage(img, topleftX, topleftY, width, height, null);
	}

	@Override
	public void update(Dimension2D dim) {
		if (topleftX > dim.getWidth() - 4 || topleftX < 0) {
			dead = true;
		} else {
			super.checkPlatformsX();
		}
	}

	/**
	 * Checks if the bullet object is interacting or colliding with any of the green platforms in the level.
	 * Deletes the bullet if so; moves the bullet if not.
	 */
	protected void checkPlatformsX() {
		try {
			for (int i = 0; i < platformsX.size(); i++) {
				if (topleftY > platformsY.get(i) - 4 && topleftY < platformsY.get(i) + 36) {
					if (topleftX <= platformsX.get(i) && topleftX >= platformsX.get(i) - 48) {
						if (velX >= 0) {
							dead = true;
						} else {
							throw new Error();
						}
					} else if (topleftX >= platformsX.get(i) && topleftX <= platformsX.get(i) + 28) {
						if (velX <= 0) {
							dead = true;
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
}
