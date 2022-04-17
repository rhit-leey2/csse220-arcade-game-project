package stationaryObject;

import java.util.ArrayList;

import main.LevelComponent;

/**
 * Fuel class which handles score updates
 * @author Sam Walsh, Lyra Lee
 *
 */
public class Fuel extends AbstractFallingObject{

	String type;
	
	//should implement to make more fuel after certain amount of time passed & when fuel is already
	public Fuel(String type, int x, int y, int velo) {
		super(type, x, y, velo);
		this.type = type;
	}
	
	public void fuelLoad(LevelComponent lc) {
		lc.scoreUpdate(1500);
	}

	public void onDropped(LevelComponent lc) {
		lc.dropNextFuel();
	}
	
	public void setTopleftX() {
		topleftX = 1000;
	}
	
	public void setTopleftY() {
		topleftY = 1000;
	}
	
}
