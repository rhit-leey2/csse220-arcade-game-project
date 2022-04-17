package stationaryObject;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;

import main.LevelComponent;

/**
 * Adds Key Listeners to always be prepared to swap levels.
 * @author Sam Walsh, Lyra Lee
 *
 */
@SuppressWarnings("serial")
public class LevelAction extends AbstractAction {
	private LevelComponent levelComponent;
	private String filepath;

	/**
	 * LevelAction constructor
	 * @param levelComponent - levelComponent being acted on.
	 * @param filename - file path to the txt used to load the level.
	 */
	public LevelAction(LevelComponent levelComponent, String filename) {
		this.levelComponent = levelComponent;
		this.filepath = filename;
	}

	public LevelAction() {
	}
	
	public LevelComponent getLevelComponent() {
		return levelComponent;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		levelComponent.loadPlatform(filepath);
	}
}
