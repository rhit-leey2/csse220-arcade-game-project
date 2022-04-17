package stationaryObject;

import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.LevelComponent;
import movingObject.Hero;

/**
 * Middle space ship part; manages falling speed
 * @author Sam Walsh, Lyra Lee
 *
 */
public class SpaceshipParts_m2 extends StationaryObject {

	BufferedImage img2 = null;
	int currentLevel; 

	public SpaceshipParts_m2(int x, int y, int level) {
		super(x, y);
		try {
			img2 = ImageIO.read(new File("images/Spaceship middle.png"));

		} catch (IOException e) {
			System.err.println("Wrong image file for spaceship middle part dummy");
		}
		this.topleftX = x;
		this.topleftY = y;
		this.currentLevel = level;
	}

	public Rectangle2D.Double getBoundingBox() {
		return new Rectangle2D.Double(topleftX, topleftY, getWidth(), getHeight());
	}

	@Override
	public void drawOn(Graphics2D g) {
		// TODO Auto-generated method stub
		super.drawOn(g);
		g.drawImage(img2, topleftX, topleftY, 67, 70, null);
	}
	
	public void fallingDown(Dimension2D dim) {
		//have to modify when the two objects placed on at the platform
		if(this.topleftY > dim.getHeight() - 215+70) {
			return;
		}else {
			this.topleftY += 8 ;
			
		System.out.println("falling down");
		}
	}

	public void move(int x, int y) {
		this.topleftX = x;
		this.topleftY = y;
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
		topleftY = 665-70;
	}

	public int getWidth() {
		return 67;
	}

	public int getHeight() {
		return 70;
	}

}
