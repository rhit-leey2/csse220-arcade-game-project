package stationaryObject;

import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * AbstractFallingObject handles movement of objects like Fuel that needs to move only vertically down
 * @author Sam Walsh, Lyra Lee
 *
 */
public abstract class AbstractFallingObject extends StationaryObject {// only for fuel, valuable objects

	BufferedImage img = null;

	boolean isGrabbed;
	boolean canBePickedUp;
	int velY;
	String type;

	public AbstractFallingObject(String type, int x, int y, int velY) {
		super(x, y);
		try {
			if (type.equals("fuel")) {
				img = ImageIO.read(new File("images/Fuel.png"));
			} else if (type.equals("valuableObj")) {
				img = ImageIO.read(new File("images/ValObj1.png"));
			}
		} catch (IOException e) {
			//System.err.println("Wrong photo dummy");
		}
		this.canBePickedUp = true;
		topleftX = x;
		topleftY = y;
		this.velY = velY;
		this.type = type;
	}

	public void drawOn(Graphics2D g) {
		super.drawOn(g);
		g.drawImage(img, topleftX, topleftY, 60, 40, null);
	}

	public void update(Dimension2D dim) {
		//have to modify when the two objects placed on at the platform
		if(topleftY > dim.getHeight() - 65) {
			return;
		}else {
			topleftY += velY;
		}
	}
	
	public void move(int x, int y) {
		this.topleftX = x;
		this.topleftY = y;
	}

	public void spawn(AbstractFallingObject obj) {
		
	}

	public boolean isGrabbed() {
		return isGrabbed;
	}

	public int getTopleftX() {
		// TODO Auto-generated method stub
		return topleftX;
	}

	public int getTopleftY() {
		return topleftY;
	}

	public int getWidth() {
		return 60;
	}
	
	public int getHeight() {
		return 40;
	}
	
	public void setImg() {
		img = null;
	}
	
	public Rectangle2D.Double getBoundingBox() {
		return new Rectangle2D.Double(getTopleftX(), getTopleftY(), getWidth(), getHeight() );
	}
	
}
