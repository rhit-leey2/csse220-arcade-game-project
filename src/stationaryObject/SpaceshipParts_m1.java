package stationaryObject;

import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Top space ship part; manages its own falling speed
 * @author Sam Walsh, Lyra Lee
 *
 */
public class SpaceshipParts_m1 extends StationaryObject {

	BufferedImage img1 = null;
	BufferedImage img2 = null;
	int currentLevel;
	public boolean imageChange = false;

	public SpaceshipParts_m1(int x, int y, int level) {
		super(x, y);
		try {
			img1 = ImageIO.read(new File("images/Spaceship top.png"));
		} catch (IOException e) {
			System.err.println("Wrong image file for spaceship top part dummy");
		}
		this.topleftX = x;
		this.topleftY = y;
		this.currentLevel = level;
	}

	@Override
	public void drawOn(Graphics2D g) {
		// TODO Auto-generated method stub
		super.drawOn(g);
		g.drawImage(img1, topleftX, topleftY, 67, 70, null);
	}
	
	public void fallingDown(Dimension2D dim) {
		if(topleftY > dim.getHeight() - 215+70*2) {
			return;
		}else {
			this.topleftY += 8 ;
		}
	}
	
	public void move(int x, int y) {
		this.topleftX = x;
		this.topleftY = y;
	}

	public Rectangle2D.Double getBoundingBox() {
		return new Rectangle2D.Double(topleftX, topleftY, getWidth(), getHeight());
	}

	public int getTopleftX() {
		return topleftX;
	}

	public int getTopleftY() {
		return topleftY;
	}
	
	public void setTopleftX() {
		topleftX = 655;
	}

	public void setTopleftY() {
		topleftY = 665-140;
	}


	public int getWidth() {
		return 67;
	}

	public int getHeight() {
		return 70;
	}

}
