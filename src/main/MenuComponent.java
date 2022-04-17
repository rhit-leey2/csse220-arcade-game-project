package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import stationaryObject.LevelAction;

@SuppressWarnings("serial")
/**
 * MenuComponent extends JComponent to be added to the JFrame;
 * shows the user the options to play the game.
 * @author Sam Walsh, Lyra Lee
 *
 */
public class MenuComponent extends JComponent {
	
	private int levelStarter;
	private static boolean gameState;
	private BufferedImage menuImg = null;

	/**
	 * MenuComponent constructor which loads the menu image and adds
	 * Key Listeners to see what the Menu should do.
	 */
	public MenuComponent() {
		gameState = false;
		try {
			menuImg = ImageIO.read(new File("images/Menu.png"));
		} catch (IOException e) {
			System.err.println("Wrong image file for Menu dummy");
		}
		addMenuListeners();
	}
	
	/**
	 * draws the current menu image onto the JFrame
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.drawImage(menuImg, 0, 0, 1008, 768, null);
	}
	
	/**
	 * Adds key listeners that change how the menu appears or whether a
	 * level of JetPac Man should be loaded.
	 */
	public void addMenuListeners() {
		LevelAction startLevel1 = new LevelAction() {
			public void actionPerformed(ActionEvent e) {
				gameState = true;
				levelStarter = 1;
			}
		};

		LevelAction startLevel2 = new LevelAction() {
			public void actionPerformed(ActionEvent e) {
				gameState = true;
				levelStarter = 2;
			}
		};
		
		LevelAction Instructions = new LevelAction() {
			public void actionPerformed(ActionEvent e) {
				try {
					menuImg = ImageIO.read(new File("images/Instructions.png"));
				} catch (IOException e1) {
					System.err.println("Wrong image file for Instructions dummy");
				}
			}
		};
		
		LevelAction Controls = new LevelAction() {
			public void actionPerformed(ActionEvent e) {
				try {
					menuImg = ImageIO.read(new File("images/Controls.png"));
				} catch (IOException e1) {
					System.err.println("Wrong image file for Controls dummy");
				}
			}
		};
		
		LevelAction Menu = new LevelAction() {
			public void actionPerformed(ActionEvent e) {
				try {
					menuImg = ImageIO.read(new File("images/Menu.png"));
				} catch (IOException e1) {
					System.err.println("Wrong image file for Menu dummy");
				}
			}
		};
		
		InputMap im = getInputMap();
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0), "level1");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0), "level2");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0), "instructions");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_4, 0), "controls");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_M, 0), "menu");

		ActionMap am = getActionMap();
		am.put("level1", startLevel1);
		am.put("level2", startLevel2);
		am.put("instructions", Instructions);
		am.put("controls", Controls);
		am.put("menu", Menu);

	}
	
	public int getLevelStarter() {
		return levelStarter;
	}

	public boolean isGameState() {
		return gameState;
	}
}
