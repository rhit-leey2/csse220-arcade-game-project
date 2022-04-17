package stationaryObject;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import movingObject.Hero;

/**
 * Platforms that make up the back drop of the JetPac Man game.
 * @author Sam Walsh, Lyra Lee
 *
 */
public class Platforms extends StationaryObject {

	public Platforms(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	BufferedImage img = null;
	
	public Platforms(int x, int y, String fileName) {
		super(x, y);
		try {
			img = ImageIO.read(new File(fileName));
		}
		catch (IOException e) {
			System.err.println("Wrong file dummy");
		}
		// TODO Auto-generated constructor stub
	}

	public void drawOn(Graphics2D g) {
		//avoid having to untranslate by mutating a copy of the graphics content
		g = (Graphics2D)g.create();
		g.setColor( this.color );
		//g.translate(topleftX, topleftY);
		g.drawImage(img, topleftX, topleftY, 32, 32, null);
	}
}
