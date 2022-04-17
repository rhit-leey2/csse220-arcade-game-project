package stationaryObject;

import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.LevelComponent;

/**
 * Grounded space ship part; manages location of drop off for other objects.
 * @author Sam Walsh, Lyra Lee
 *
 */
public class SpaceshipParts_s extends StationaryObject {

	BufferedImage img1 = null;
	BufferedImage img2 = null;
	BufferedImage img3 = null;
	int currentLevel;
	
	private int velY = 8;

	/**
	 * Constructor
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param level - which level the user is on. Determines spaceship layout.
	 */
	public SpaceshipParts_s(int x, int y, int level) {
		super(x, y);
		if (y == 525) {
			try {
				img1 = ImageIO.read(new File("images/Spaceship all.png"));

			} catch (IOException e) {
				System.err.println("Wrong image file for spaceship dummy");
			}
		} else if (y == 665) {
			try {
				img2 = ImageIO.read(new File("images/Spaceship bottom.png"));

			} catch (IOException e) {
				System.err.println("Wrong image file for spaceship part dummy");
			}

		} else {
			try {
				img3 = ImageIO.read(new File("images/Spaceship_fuel_down.png"));

			} catch (IOException e) {
				System.err.println("Wrong image file for spaceship part dummy");
			}
		}
		this.topleftX = x;
		this.topleftY = y;
		this.currentLevel = level;
	}

	@Override
	public void drawOn(Graphics2D g) {
		super.drawOn(g);
		if (topleftY >= 544 & currentLevel == 2) {
			try {
				img1 = ImageIO.read(new File("images/Spaceship all.png"));
			} catch (IOException e) {
				System.err.println("Wrong image file for spaceship dummy");
			}
			g.drawImage(img1, topleftX, topleftY - 20, 67, 210, null);
		} else {
			g.drawImage(img3, topleftX, topleftY, 67, 250, null);
		}
		g.drawImage(img2, topleftX, topleftY, 67, 70, null);

	}

	public void update(Dimension2D dim) {
		if (topleftY > dim.getHeight() - 215) {
			return;
		} else {
			this.topleftY += velY;
		}
	}

	public Rectangle2D.Double getBoundingBox() {
		return new Rectangle2D.Double(topleftX, topleftY, getWidth(), getHeight());
	}

	public int getTopleftX() {
		return this.topleftX;
	}

	public int getTopleftY() {
		return this.topleftY;
	}

	public int getWidth() {
		return 67;
	}

	public int getHeight() {
		return 70;
	}

	public boolean getIsGrabbed() {
		return isGrabbed;
	}

	public void getTopLeftY(LevelComponent lc) {
		lc.setTempTopLeftY(this.topleftY);
	}

}
