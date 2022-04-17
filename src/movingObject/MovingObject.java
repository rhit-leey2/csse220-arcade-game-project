package movingObject;

import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * This is the King Abstract class representing all moving Objects in the level
 * It handles its movements and checks its collisions
 * @author Sam Walsh, Lyra Lee
 *
 */
public abstract class MovingObject {

	protected int topleftX, topleftY, velX, velY, moveSpeed;
	protected ArrayList<Integer> platformsX;
	protected ArrayList<Integer> platformsY;
	protected boolean dead = false;
	private boolean shouldRemove;
	
	public final int width;
	public final int height;

	/**
	 * MovingObject constructor
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param platformsX - X coordinates of all platforms in the level
	 * @param platformsY - Y coordinates of all paltforms in the level
	 * @param width - width of the object
	 * @param height - height of the object
	 */
	public MovingObject(int x, int y, ArrayList<Integer> platformsX, 
			ArrayList<Integer> platformsY, int width, int height) {
		this.topleftX = x;
		this.topleftY = y;
		this.velX = 0;
		this.velY = 0;
		this.platformsX = platformsX;
		this.platformsY = platformsY;
		this.width = width;
		this.height = height;
	}

	public void drawOn(Graphics2D g) {
		g = (Graphics2D) g.create();
		g.translate(topleftX, topleftY);
	}

	public abstract void update(Dimension2D dim);

	/**
	 * Checks if the MovingObject object is interacting or colliding horizontally with any of the green platforms in the level.
	 * By default, sets objects to dead if they touch a platform.
	 */
	protected void checkPlatformsX() {
		try {
			for (int i = 0; i < platformsX.size(); i++) {
				if (topleftY > platformsY.get(i) - height && topleftY < platformsY.get(i) + height) {
					if (topleftX <= platformsX.get(i) && topleftX >= platformsX.get(i) - width) { // Checking left side
						if (velX >= 0) {
							dead = true;
						} else {
							throw new Error();
						}
					} else if (topleftX >= platformsX.get(i) && topleftX <= platformsX.get(i) + 28) { // Checking right
																										// side
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

	/**
	 * Checks if the MovingObject object is interacting or colliding vertically with any of the green platforms in the level.
	 * By default, sets objects to dead if they touch a platform.
	 */
	protected void checkPlatformsY() {
		try {
			for (int i = 0; i < platformsY.size(); i++) {
				if (topleftX > platformsX.get(i) - 32 && topleftX < platformsX.get(i) + 20) {
					if (topleftY <= platformsY.get(i) + 32 && topleftY >= platformsY.get(i)) {
						if (velY <= 0) {
							dead = true;
						} else {
							throw new Error();
						}
					} else if (topleftY >= platformsY.get(i) - 32 && topleftY <= platformsY.get(i)) {
						if (velY >= 0) {
							dead = true;
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

	// Make sure that velocity remains fixed and non-zero
	public void normalizeVelocity() {
		// move down if velocity is set to zero
		if (this.velX == 0 && this.velY == 0) {
			this.velX = 0;
			if (topleftY < 639) {
				this.velY = 1;
			} else {
				this.velY = 0;
			}
		}
		// normalize vector
		double vectorLength = Math.sqrt(velX * velX + velY * velY);
		this.velX = (int) (this.velX / vectorLength * 6 * 2);
		this.velY = (int) (this.velY / vectorLength * 6 * 2);
	}

	public boolean shouldRemove() {
		return this.shouldRemove;
	}
	
	public void markToRemove() {
		this.shouldRemove = true;
	}
	public int getTopleftX() {
		return topleftX;
	}

	public int getTopleftY() {
		return topleftY;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean getDead() {
		return dead;
	}

	public void setDead(boolean state) {
		dead = state;
	}
	
	public Rectangle2D.Double getBoundingBox() {
		return new Rectangle2D.Double(topleftX, topleftY, getWidth(), getHeight());
	}
}
