package stationaryObject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
//import java.awt.geom.Dimension2D;
import java.awt.geom.Dimension2D;

import main.LevelComponent;
import movingObject.Hero;

/**
 * StationaryObject which includes all objects that do not
 * have interactions that result in death with the hero.
 * @author Sam Walsh, Lyra Lee
 *
 */
public abstract class StationaryObject {
	
	protected Color color;
	protected int topleftX, topleftY;
	protected boolean isGrabbed;
	protected boolean isDropped;
	boolean canBePickedUp;
	public int fuelCount;
	
	/**
	 * Constructor for object
	 * @param x - topLeft X Coordinate
	 * @param y - topLeft Y Coordinate
	 */
	public StationaryObject(int x, int y) {
		canBePickedUp = true;
		this.topleftX = x;
		this.topleftY = y;	
		color = new Color(0,0,0); //Default black
	}

	public void drawOn(Graphics2D g) {
		g = (Graphics2D)g.create();
		g.setColor( this.color );
		g.translate(topleftX, topleftY);
	}
	
	public boolean isGrabbed() {
		return isGrabbed;
	}

	public void setGrabbed(boolean isGrabbed) {
		this.isGrabbed = isGrabbed;
	}
	
	public void isDropped(StationaryObject obj) {
		
	}
	
	public void setDropped(boolean isGrabbed) {
		this.isDropped = isGrabbed; 
	}
	
	public void onDropped(LevelComponent lc) {
		
	}

	public void fallingDown(Dimension2D dim) {
		// TODO Auto-generated method stub
		
	}

	public void fuelLoad(LevelComponent lc) {
		
	}
	
	public int getFuelCount() {
		return fuelCount;
	}

	public int getTopleftX() {
		return topleftX;
	}

	public int getTopleftY() {
		return topleftY;
	}
	
	public void setTopleftX() {
		
	}
	public void setTopleftY() {
		
	}
	
	public boolean isPickupable() {
		return canBePickedUp;
	}
	
	public void canPickupFalse() {
		canBePickedUp = false;
	}
	
}
