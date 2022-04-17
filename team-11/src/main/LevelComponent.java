package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import movingObject.AbstractAlien;
import movingObject.Alien1;
import movingObject.Alien2;
import movingObject.Bullets;
import movingObject.Hero;
import movingObject.HeroKeyListener;
import stationaryObject.AbstractFallingObject;
import stationaryObject.Fuel;
import stationaryObject.LevelAction;
import stationaryObject.Platforms;
import stationaryObject.SpaceshipParts_m1;
import stationaryObject.SpaceshipParts_m2;
import stationaryObject.SpaceshipParts_s;
import stationaryObject.StationaryObject;

@SuppressWarnings("serial")

/**
 * The LevelComponent class works as a drawing pad for all the various objects
 * that make up the JetPac Man game. It manages and holds these objects and
 * checks if they collide with one another.
 * 
 * @author Sam Walsh, Lyra Lee
 */
public class LevelComponent extends JComponent {

	// private static final int DELAY = 50;
	// public int currentLevel = 1;
	private static final int heroSpawnX = 425;
	private static final int heroSpawnY = 640;

	// Convenient object used to generate random numbers conveniently
	private Random rand = new Random();

	private ArrayList<Bullets> bullets = new ArrayList<Bullets>();
	private ArrayList<StationaryObject> stationaryObjects = new ArrayList<StationaryObject>();
	private ArrayList<AbstractAlien> aliens = new ArrayList<AbstractAlien>();
	private ArrayList<AbstractAlien> aliens1 = new ArrayList<AbstractAlien>();
	private ArrayList<AbstractAlien> aliens2 = new ArrayList<AbstractAlien>();
	private ArrayList<AbstractFallingObject> fallingObjects = new ArrayList<AbstractFallingObject>();
	private ArrayList<Integer> platformsX = new ArrayList<Integer>();
	private ArrayList<Integer> platformsY = new ArrayList<Integer>();

	private StationaryObject statObj;
	private SpaceshipParts_m1 sm1; // level 1
	private SpaceshipParts_m2 sm2; // level 1
	private SpaceshipParts_s sms; // level 1
	private SpaceshipParts_s sm3; // level 2
	private int currentLevel;
	private boolean isFuelOnScreen = false;
	private int fuelTime = 0;
	private int tempTopLeftY = 0;
	private int score = 0000;

	private Hero hero;

	/**
	 * Really really important constructor
	 */
	public LevelComponent() {

	}

	/**
	 * Loads a level based on its input level number: 1 or 2
	 * @param currentLevel - level number
	 */
	public void loadLevel(int currentLevel) {
		if (currentLevel == 1) {
			loadPlatform("levels/level1.txt");

			for (int i = 0; i < 5; i++) {
				addAliens1();
			}

		} else if (currentLevel == 2) {
			loadPlatform("levels/level2.txt");

			for (int i = 0; i < 5; i++) {
				addAliens2();
			}
			addSpaceParts(currentLevel);

		}
		this.currentLevel = currentLevel;
		addHero();
		addLevelKeyListeners();
		addKeyListener(new HeroKeyListener(hero));
		addSpaceParts(currentLevel);

	}

	/**
	 * Used by the Key Listeners to load the levels
	 * @param filepath - path to the level.txt
	 * @param levelNum - The level the listener is loading
	 */
	public void loadLevelFromListener(String filepath, int levelNum) {
		score = 0;
		hero.spawnIn(heroSpawnX, heroSpawnY);
		currentLevel = levelNum;
		bullets.clear();
		aliens.clear();
		if (levelNum == 1) {
			aliens2.clear();
			for (int i = 0; i < 6; i++) {
				addAliens1();
			}
		} else {
			aliens1.clear();
			for (int i = 0; i < 5; i++) {
				addAliens2();
			}
		}
		loadPlatform(filepath);
		currentLevel = levelNum;
		addSpaceParts(currentLevel);
	}

	/**
	 * loads the Platforms in 32x32 chunks by images. Adds hitbox values to
	 * certain platforms via ArrayLists
	 * @param filepath - path to the level.txt
	 */
	public void loadPlatform(String filepath) {
		platformsX.clear();
		platformsY.clear();

		// movingObjects.clear();

		stationaryObjects.clear();
		ArrayList<String[]> LevelLayout = readLevel(filepath);
		for (int x = 0; x < LevelLayout.size(); x++) {
			for (int y = 0; y < LevelLayout.get(0).length; y++) {
				// Please note x and y's are swapped. The game works fine, but is improperly
				// notated.
				if (LevelLayout.get(x)[y].equals(".")) {
					this.stationaryObjects.add(new Platforms(y * 32, x * 32, "images/BlackSquare.png"));
				} else if (y == 0) {
					if (LevelLayout.get(x)[y].equals("P")) {
						this.stationaryObjects.add(new Platforms(y * 32, x * 32, "images/PlatformMiddle.png"));
						addingXYplatforms(x, y);
					} else if (LevelLayout.get(x)[y].equals("-")) {
						this.stationaryObjects.add(new Platforms(y * 32, x * 32, "images/GroundLeft.png"));
					}
				} else if (y == LevelLayout.get(0).length - 1) {
					if (LevelLayout.get(x)[y].equals("P")) {
						this.stationaryObjects.add(new Platforms(y * 32, x * 32, "images/PlatformMiddle.png"));
						addingXYplatforms(x, y);
					} else if (LevelLayout.get(x)[y].equals("-")) {
						this.stationaryObjects.add(new Platforms(y * 32, x * 32, "images/GroundRight.png"));
					}
				} else {
					if (LevelLayout.get(x)[y].equals("P")) {
						if (LevelLayout.get(x)[y - 1].equals("P") == false) {
							this.stationaryObjects.add(new Platforms(y * 32, x * 32, "images/PlatformLeft.png"));
							addingXYplatforms(x, y);
						} else if (LevelLayout.get(x)[y + 1].equals("P") == false) {
							this.stationaryObjects.add(new Platforms(y * 32, x * 32, "images/PlatformRight.png"));
							addingXYplatforms(x, y);
						} else {
							this.stationaryObjects.add(new Platforms(y * 32, x * 32, "images/PlatformMiddle.png"));
							addingXYplatforms(x, y);
						}
					} else {
						this.stationaryObjects.add(new Platforms(y * 32, x * 32, "images/GroundMiddle.png"));
					}
				}
			}
		}
	}

	/**
	 * Reads the level.txt file and converts it to a usable ArrayList
	 * @param filename - path to the level.txt
	 * @return ArrayList of the Strings contained within the level.txt
	 */
	public ArrayList<String[]> readLevel(String filename) {
		ArrayList<String[]> LevelLayoutTemp = new ArrayList<String[]>();
		try {
			File myObj = new File(filename);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				LevelLayoutTemp.add(myReader.nextLine().split(""));
			}
			myReader.close();
			return LevelLayoutTemp;
		} catch (FileNotFoundException e) {
			System.out.println("Level file not found");
		}
		return null;

	}
	
	/**
	 * adds an X and Y value to the X and Y ArrayLists
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 */
	public void addingXYplatforms(int x, int y) {
		platformsX.add(y * 32);
		platformsY.add(x * 32);
	}

	public ArrayList<Integer> getPlatformsX() {
		return platformsX;
	}

	public ArrayList<Integer> getPlatformsY() {
		return platformsY;
	}

	/**
	 * Adds the Hero and its bullet ActionListeners
	 */
	public void addHero() {
		hero = new Hero(heroSpawnX, heroSpawnY, platformsX, platformsY);
		addBulletActionListeners();
	}

	public Hero getHero() {
		return hero;
	}
	
	/**
	 * Used by Fuel.java to restart the fuel counter before the next
	 * fuel object spawns.
	 */
	public void dropNextFuel() {
		isFuelOnScreen = false;
	}

	/**
	 * Adds the Space Parts based on which level is loaded
	 * @param level - level loaded
	 */
	public void addSpaceParts(int level) {
		if (level == 1) {
			sms = new SpaceshipParts_s(655, 665, 1);
			this.stationaryObjects.add(sms);
			sm1 = new SpaceshipParts_m1(175, 155, 1);
			this.stationaryObjects.add(sm1);
			sm2 = new SpaceshipParts_m2(415, 315, 1);
			this.stationaryObjects.add(sm2);
		} else if (level == 2) {
			sm3 = new SpaceshipParts_s(495, -300, 2);
			this.stationaryObjects.add(sm3);
		}

	}

	/**
	 * Loads aliens for their respective level
	 */
	public void addAliens1() {
		AbstractAlien tempAlien = new Alien1(rand.nextInt(2) * 1019, rand.nextInt(639), platformsX, platformsY);
		this.aliens1.add(tempAlien);
		this.aliens.add(tempAlien);
	}

	/**
	 * Loads aliens for their respective level
	 */
	public void addAliens2() {
		AbstractAlien tempAlien = new Alien2(rand.nextInt(2) * 1019, rand.nextInt(639), platformsX, platformsY);
		this.aliens2.add(tempAlien);
		this.aliens.add(tempAlien);
	}

	/**
	 * Adds a Fuel object on queue to the Component
	 */
	public void addFuel() {
		this.fallingObjects.add(new Fuel("fuel", rand.nextInt(950), 0, 9));
	}

	/**
	 * Adds actionListeners for the bullets. Bullets spawn when activated.
	 */
	public void addBulletActionListeners() {
		InputMap im = getInputMap();
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "bullet");

		ActionMap am = getActionMap();
		am.put("bullet", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				bullets.add(new Bullets(hero.getTopleftX() + 32, hero.getTopleftY() + 45, hero.getDirection(),
						platformsX, platformsY, true));
			}

		});
	}

	/**
	 * Adds Level Key Listeners that change the Level respectively when activated
	 */
	public void addLevelKeyListeners() {
		LevelAction changeLevelU = new LevelAction() {
			public void actionPerformed(ActionEvent e) {
				if (currentLevel == 1) {
					loadLevelFromListener("levels/level2.txt", 2);
				}
			}
		};

		LevelAction changeLevelD = new LevelAction() {
			public void actionPerformed(ActionEvent e) {
				if (currentLevel == 2) {
					loadLevelFromListener("levels/level1.txt", 1);
				}
			}
		};
		InputMap im = getInputMap();
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0), "level2");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "level1");

		ActionMap am = getActionMap();
		am.put("level2", changeLevelU);
		am.put("level1", changeLevelD);

	}

	/**
	 * Paints all objects onto the JComponent with their respective drawOn method.
	 * Also checks for win condition and ends the game when completed
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		for (StationaryObject s : this.stationaryObjects) { // draws not moving spaceship part and platforms
			s.drawOn(g2);
		}

		for (AbstractAlien a : this.aliens) {
			a.drawOn(g2);
		}

		for (Bullets b : this.bullets) { // draws moving spaceship parts and aliens
			b.drawOn(g2);
		}

		for (AbstractFallingObject f : this.fallingObjects) { // draws moving spaceship parts and aliens
			f.drawOn(g2);
		}

		hero.drawOn(g2); // draws hero

		g2.setColor(Color.WHITE);
		g.setFont(new Font("Courier", Font.BOLD, 36));
		g2.drawString("LIVES: " + hero.getLives() + "", 10, 40);
		g2.drawString("SCORE: " + score + "", 740, 40);

		if ((this.score >= 6000 && currentLevel == 1) || (this.score >= 6000 && currentLevel == 2)) {
			BufferedImage img = null;
			try {
				img = ImageIO.read(new File("images/WinScreen.png"));
			} catch (IOException e) {
				System.err.println("Wrong Winning Page dummy");
			}
			g2.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
		}

	}

	/**
	 * Checks for when the spaceship hits the ground; relative to Level 2
	 */
	public void whenSpaceshipOnGround() {
		// when spaceship touched ground in level 2
		if (sm3 != null) {
			sm3.update(this.getSize());

			if (tempTopLeftY == 544) {
				sms = new SpaceshipParts_s(495, 525, 2);
				this.stationaryObjects.add(sms);
			}

		}
	}

	public void setTempTopLeftY(int tempTopLeftY) {
		this.tempTopLeftY = tempTopLeftY;
	}

	/**
	 * update method that, with a timer in GameViewer, updates and maintains all objects
	 * within the Component. Each object updates themselves or has collision checks in
	 * LevelComponent. Fuel is also managed here.
	 */
	public void update() {

		if (!isFuelOnScreen) { // when fuel is not on the screen
			fuelTime++;
			if (fuelTime == 100) {
				addFuel();
				fuelTime = 0;
				isFuelOnScreen = true;
			}
		}

		whenSpaceshipOnGround();
		handleCollisions();
		fallingObjects();
		grabbingObjects();
		droppingObjects();
		scoreCheck();
	}

	/**
	 * Updates the AbstractFallingObjects in the fallingObjects ArrayList
	 */
	private void fallingObjects() {
		for (int i = 0; i < fallingObjects.size(); i++) {
			fallingObjects.get(i).update(this.getSize()); // objects are falling down
		}
	}

	/**
	 * Updates and Checks grabbable objects for Hero's interactions.
	 */
	private void grabbingObjects() {

		// Made if statement because sm1 and sm2 are only present on level1
		if (currentLevel == 1) {
			if (hero.overlapsWith(sm1) && sm1.isPickupable()) {
				sm1.move(hero.getTopleftX() + hero.getWidth() / 2, hero.getTopleftY() + hero.getHeight() / 2);
				hero.setIsGrabbed(sm1);
				statObj = sm1;

			} else if (hero.overlapsWith(sm2) && sm2.isPickupable()) {
				sm2.move(hero.getTopleftX() + hero.getWidth() / 2, hero.getTopleftY() + hero.getHeight() / 2);
				hero.setIsGrabbed(sm2);
				statObj = sm2;
			}
		}
		for (AbstractFallingObject f : fallingObjects) {
			if (hero.overlapsWith(f) && f.isPickupable()) {
				f.move(hero.getTopleftX() + hero.getWidth() / 2, hero.getTopleftY() + hero.getHeight() / 2);
				hero.setIsGrabbed(f);
				statObj = f;
			}
		}
	}

	/**
	 * Updates AbstractFallingObjects as they come into the 
	 * range of their drop point: The grounded space part
	 */
	private void droppingObjects() {
		if (statObj != null) {
			if (currentLevel == 1) {
				if (hero.getIsGrabbed() && hero.getTopleftX() <= 495 + 67 * 2 && hero.getTopleftX() >= 495 + 67) {
					droppingObjectsUpdate();
				}
			} else if (currentLevel == 2) {
				if (hero.getIsGrabbed() && hero.getTopleftX() <= 495 + 67 && hero.getTopleftX() >= 495 - 67) {
					droppingObjectsUpdate();
				}
			}
		}
	}

	/**
	 * Manages an object as they drop onto or into the rocket
	 */
	private void droppingObjectsUpdate() {
		statObj.canPickupFalse();
		statObj.onDropped(this);
		hero.setIsDropped();
		statObj.setTopleftX();
		statObj.setTopleftY();
		statObj.fuelLoad(this);
	}

	public void scoreUpdate(int score) {
		this.score += score;
	}

	/**
	 * Checks if the score from adding fuel qualifies for the win condition of 4 fuel cans
	 * added to the Spaceship.
	 */
	public void scoreCheck() {
		if ((this.score >= 4500 && currentLevel == 1) || (this.score >= 6000 && currentLevel == 2)) {
			try {
				BufferedImage img = ImageIO.read(new File("images/WinScreen.png"));
			} catch (IOException e) {
				System.err.println("Wrong Winning Page dummy");
			}
		}
	}

	/**
	 * Checks all interactions between objects and initiates any
	 * collisions. Followed by removal of objects or updating of
	 * alive objects. Hero is always updated first.
	 */
	private void handleCollisions() {
		ArrayList<AbstractAlien> removeAliens = new ArrayList<AbstractAlien>();
		ArrayList<Bullets> removeBullets = new ArrayList<Bullets>();

		hero.update(this.getSize());
		
		//All bullet updates and checks
		//Hero collisions
		for (Bullets b : bullets) {
			b.update(this.getSize());
			if (b.getDead()) {
				removeBullets.add(b);
			}
			if (hero.overlapsWith(b)) {
				hero.collideWith(b);
				removeBullets.add(b);
			}
		}

		//All alien updates and checks
		//Hero and Bullet collisions
		for (AbstractAlien a : aliens) {
			a.update(this.getSize());
			if (hero.overlapsWith(a)) {
				hero.collideWith(a);
				removeAliens.add(a);
			}
			if (a.getDead()) {
				removeAliens.add(a);
			}
			for (Bullets b : bullets) {
				if (a.overlapsWith(b)) {
					a.collideWith(b);
					removeBullets.add(b);
					removeAliens.add(a);
				}
			}
			for (AbstractAlien a1 : aliens) {
				if (a != a1 && a.overlapsWith(a1)) {
					a.collideWith(a1);
					removeAliens.add(a1);
					removeAliens.add(a);
				}
			}

		}

		// Update aliens2 to start blastin'
		for (AbstractAlien a : aliens2) {
			if (((Alien2) a).updateShooting()) {
				bullets.add(new Bullets(a.getTopleftX() + a.getWidth(), a.getTopleftY() + a.getHeight() / 2,
						((Alien2) a).getDirection(), platformsX, platformsY, false));
			}
		}

		// Remove any dead Aliens
		for (AbstractAlien a : removeAliens) {
			removeAliens(aliens, a);
			for (int i = 0; i < removeAliens(aliens1, a); i++) {
				addAliens1();
			}
			for (int i = 0; i < removeAliens(aliens2, a); i++) {
				addAliens2();
			}
		}

		// Remove any dead bullets
		for (Bullets b : removeBullets) {
			int noErrorCounter = 0;
			for (int i = 0; i < bullets.size(); i++) {
				if (b.equals(bullets.get(i))) {
					bullets.remove(i - noErrorCounter);
					noErrorCounter += 1;
				}
			}
		}
	}

	/**
	 * Duplicate Code reducer to remove an Alien from the
	 * ArrayList within interrupting the thread.
	 * @param aliens - aliens ArrayList
	 * @param a - alien to remove
	 * @return - returns how many aliens were removed; should be 0 or 1.
	 */
	public int removeAliens(ArrayList<AbstractAlien> aliens, AbstractAlien a) {
		int returnAdds = 0;
		for (int y = 0; y < aliens.size(); y++) {
			int noErrorCounter = 0;
			if (a.equals(aliens.get(y))) {
				aliens.remove(y - noErrorCounter);
				noErrorCounter += 1;
				returnAdds += 1;
			}
		}
		return returnAdds;
	}

}
