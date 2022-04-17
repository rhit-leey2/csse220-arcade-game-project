package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * GameViewer is responsible for maintaining the JFrame and loads
 * either the levels or the menu.
 * 
 * @author Sam Walsh, Lyra Lee
 *
 */
public class GameViewer {
	public static final int FRAME_WIDTH = 1024;
	public static final int FRAME_HEIGHT = 804;
	public static final Color BLACK = new Color(0, 0, 0);

	// How long to wait in milliseconds between each step of the simulation
	private static final int DELAY = 50;
	private LevelComponent levelComponent;
	private JFrame menuFrame;
	private JFrame gameFrame;
	private MenuComponent menuComponent;

	/**
	 * GameViewer() which adds to the JFrame
	 */
	public GameViewer() {
		menuFrame = new JFrame();
		gameFrame = new JFrame();
		levelComponent = new LevelComponent();
		menuComponent = new MenuComponent();

		menuFrame.add(menuComponent, BorderLayout.CENTER);

		menuFrame.setTitle("JetPac Man ! ");
		menuFrame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		menuFrame.setResizable(false);
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuFrame.pack();
		menuFrame.setVisible(true);
		
		//While a menu option is not selected
		while (menuComponent.isGameState() == false) {
			menuComponent.repaint();
			menuFrame.repaint();
		}

		menuFrame.setVisible(false);
		
		//Now load a level that was picked from menu
		gameFrame = new JFrame();
		gameFrame.setTitle("JetPac Man ! ");
		gameFrame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		gameFrame.setResizable(false);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		levelComponent.loadLevel(menuComponent.getLevelStarter());
		gameFrame.add(levelComponent, BorderLayout.CENTER);

		Timer t = new Timer(DELAY, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				levelComponent.update();
				levelComponent.repaint();
				gameFrame.repaint();
			}
		});

		t.start();
		gameFrame.pack();
		gameFrame.setVisible(true);
	}
}
