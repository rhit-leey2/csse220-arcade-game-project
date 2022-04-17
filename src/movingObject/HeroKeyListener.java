package movingObject;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Takes key inputs from the keyboard to move or manipulate the Hero
 * @author Sam Walsh, Lyra Lee
 *
 */
public class HeroKeyListener implements KeyListener {
	private Hero hero;
	boolean right;
	boolean left;
	boolean up;

	public HeroKeyListener(Hero hero) {
		this.hero = hero;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int tempCheck = 0;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) { // Right arrow key code
			right = true;
			tempCheck = 1;
			// change the image to hero_standing right
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) { // Left arrow key code
			left = true;
			tempCheck = 1;
			// change the image to hero_standing left
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) { // Up arrow key code
			up = true;
			tempCheck = 1;
			// change the image location to go up one pixel
		}
		if (tempCheck == 1) {
			moveHero();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int tempCheck = 0;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) { // Right arrow key code
			right = false;
			tempCheck = 1;
			// change the image to hero_standing right
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) { // Left arrow key code
			left = false;
			tempCheck = 1;
			// change the image to hero_standing left
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) { // Up arrow key code
			up = false;
			tempCheck = 1;
			// change the image location to go up one pixel
		}
		if (tempCheck == 1) {
			moveHero();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	/**
	 * moves the Hero in the X and Y direction based on calcHorizontal() and calcVertical()
	 */
	public void moveHero() {
		hero.move(calcHorizontal(), calcVertical());
	}

	/**
	 * moves the Hero in the X direction
	 * @return - the value by which X changes
	 */
	public int calcHorizontal() {
		int movementX = 0;
		if (right) {
			movementX += 16;
		}
		if (left) {
			movementX += -16;
		}
		return movementX;
	}

	/**
	 * moves the Hero in the Y direction
	 * @return - the value by which Y changes
	 */
	public int calcVertical() {
		int movementY = 16;
		if (up) {
			movementY += -32;
		}
		return movementY;
	}

}
