package com.levelbuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.GFX;
import com.graphics.Loader;
import com.graphics.Textures;
import com.graphics.world.Entity;
import com.graphics.world.Player;
import com.graphics.world.RectangleBox;
import com.graphics.world.Tile;
import com.graphics.world.World;
import com.graphics.world.enemys.Enemy;
import com.graphics.world.util.Edge;
import com.graphics.world.util.Vertex;
import com.input.InputHandler;
import com.main.Window;
import com.util.Utils;

/**
 * Lets the user build a level
 * 
 * @author Craig Ferris
 *
 */
public class LevelBuilderGame
{
	private ArrayList<Tile>		tiles			= new ArrayList<Tile>();
	private ArrayList<Enemy>	enemies			= new ArrayList<Enemy>();

	private Player				player;

	private InputHandler		handler;
	private Texture				tileToPlace;
	private boolean				isMouseReady	= true;
	private boolean				isDeleting		= false;

	boolean						clickedATile	= false;										// as soon as a tile is clicked, the mouse should enter "delete mode" until the mouse is released

	private Texture				down			= Loader.loadTexture("borders/down");
	private Texture				downleft		= Loader.loadTexture("borders/downleft");
	private Texture				downleftright	= Loader.loadTexture("borders/downleftright");
	private Texture				downright		= Loader.loadTexture("borders/downright");
	private Texture				left			= Loader.loadTexture("borders/left");
	private Texture				leftright		= Loader.loadTexture("borders/leftright");
	private Texture				leftupright		= Loader.loadTexture("borders/leftupright");
	private Texture				right			= Loader.loadTexture("borders/right");
	private Texture				rightupdown		= Loader.loadTexture("borders/rightupdown");
	private Texture				topdown			= Loader.loadTexture("borders/topdown");
	private Texture				up				= Loader.loadTexture("borders/up");
	private Texture				updownleftright	= Loader.loadTexture("borders/updownleftright");
	private Texture				updownright		= Loader.loadTexture("borders/updownright");
	private Texture				upleft			= Loader.loadTexture("borders/upleft");
	private Texture				upright			= Loader.loadTexture("borders/upright");
	private Texture				saveLevel		= Loader.loadTexture("saveLevel");
	private Texture				door			= Loader.loadTexture("door");

	private int					y_offset, x_offset;
	private boolean readyAfterClickingSave = true;

	/**
	 * Creates a new level builder
	 */
	public LevelBuilderGame()
	{
		new Textures();
		handler = new InputHandler();
	}

	/**
	 * Updates the level builder
	 */
	public void update()
	{
		if (handler.isMouseLeftClicking() == false)
		{
			isMouseReady = true;
		}
		if (handler.isMouseLeftClicking())
		{
			if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 0 && handler.getMousePosition().y < 32)
			{
				tileToPlace = Textures.testTile;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 32 && handler.getMousePosition().y < 64)
			{
				tileToPlace = Textures.grass;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 64 && handler.getMousePosition().y < 96)
			{
				tileToPlace = Textures.grassTop;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 96 && handler.getMousePosition().y < 128)
			{
				tileToPlace = Textures.dirt2;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 128 && handler.getMousePosition().y < 160)
			{
				tileToPlace = Textures.dirt;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 160 && handler.getMousePosition().y < 192)
			{
				tileToPlace = Textures.air;
			}

			else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 192 && handler.getMousePosition().y < 224)
			{
				tileToPlace = down;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 224 && handler.getMousePosition().y < 256)
			{
				tileToPlace = downleft;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 256 && handler.getMousePosition().y < 288)
			{
				tileToPlace = downleftright;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 288 && handler.getMousePosition().y < 320)
			{
				tileToPlace = downright;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 320 && handler.getMousePosition().y < 352)
			{
				tileToPlace = left;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 352 && handler.getMousePosition().y < 384)
			{
				tileToPlace = leftright;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 384 && handler.getMousePosition().y < 416)
			{
				tileToPlace = leftupright;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 416 && handler.getMousePosition().y < 448)
			{
				tileToPlace = right;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 448 && handler.getMousePosition().y < 480)
			{
				tileToPlace = rightupdown;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 480 && handler.getMousePosition().y < 512)
			{
				tileToPlace = topdown;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 512 && handler.getMousePosition().y < 544)
			{
				tileToPlace = up;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 544 && handler.getMousePosition().y < 576)
			{
				tileToPlace = updownleftright;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 576 && handler.getMousePosition().y < 608)
			{
				tileToPlace = updownright;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 608 && handler.getMousePosition().y < 640)
			{
				tileToPlace = upleft;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 640 && handler.getMousePosition().y < 672)
			{
				tileToPlace = upright;
			} else if (handler.getMousePosition().x > Window.width - 64 && handler.getMousePosition().x <= Window.width - 32 && handler.getMousePosition().y >= 0 && handler.getMousePosition().y < 32)
			{
				tileToPlace = Textures.playerFront;
			} else if (handler.getMousePosition().x > Window.width - 64 && handler.getMousePosition().x <= Window.width - 32 && handler.getMousePosition().y >= 32 && handler.getMousePosition().y < 64)
			{
				tileToPlace = Textures.crabman;
			} else if (handler.getMousePosition().x > Window.width - 64 && handler.getMousePosition().x <= Window.width - 32 && handler.getMousePosition().y >= 64 && handler.getMousePosition().y < 96)
			{
				tileToPlace = door;
			} else if (handler.getMousePosition().x > Window.width - 32 && handler.getMousePosition().x <= Window.width && handler.getMousePosition().y >= 672 && handler.getMousePosition().y < 704 && readyAfterClickingSave)
			{
				saveLevel();
				readyAfterClickingSave = false;
			} else
			{
				if (tileToPlace != null)
				{
					if (tileToPlace == Textures.playerFront)
					{
						if (player == null)
						{
							player = new Player(new Vector3f(handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16, 0), tileToPlace, tileToPlace, new Vector2f(512, 256), 0, 0, new Vector2f(16, 16), new Vector2f(32, 32),
									handler);
						} else
						{
							for (Tile t : tiles)
							{
								if (t.getPosition().x == handler.getMousePosition().x - handler.getMousePosition().x % 16 && t.getPosition().y == handler.getMousePosition().y - handler.getMousePosition().y % 16)
								{
									tiles.remove(t);
									break;
								}
							}
							for (Enemy e : enemies)
							{
								if (e.getPosition().x == handler.getMousePosition().x - handler.getMousePosition().x % 16 && e.getPosition().y == handler.getMousePosition().y - handler.getMousePosition().y % 16)
								{
									enemies.remove(e);
									break;
								}
							}
							if (handler.getMousePosition().x - handler.getMousePosition().x % 16 == player.getPosition().x && handler.getMousePosition().y - handler.getMousePosition().y % 16 == player.getPosition().y)
							{
								player = null;
							}
						}
					} else if (tileToPlace == Textures.crabman)
					{
						boolean removedItem = false;
						for (Tile t : tiles)
						{
							if (t.getPosition().x == handler.getMousePosition().x - handler.getMousePosition().x % 16 && t.getPosition().y == handler.getMousePosition().y - handler.getMousePosition().y % 16)
							{
								tiles.remove(t);
								removedItem = true;
								break;
							}
						}
						for (Enemy e : enemies)
						{
							if (e.getPosition().x == handler.getMousePosition().x - handler.getMousePosition().x % 16 && e.getPosition().y == handler.getMousePosition().y - handler.getMousePosition().y % 16)
							{
								enemies.remove(e);
								removedItem = true;
								break;
							}
						}
						if (player != null && handler.getMousePosition().x - handler.getMousePosition().x % 16 == player.getPosition().x && handler.getMousePosition().y - handler.getMousePosition().y % 16 == player.getPosition().y)
						{
							player = null;
							removedItem = true;
						}
						if (!removedItem)
						{
							enemies.add(new Enemy(new Vector3f(handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16, 0), Textures.crabman, Textures.crabman, new Vector2f(512, 128), 0, 0, new Vector2f(16, 16),
									new Vector2f(64, 64)));
						}

					} else
					{
						Vector2f pos = new Vector2f(handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16);
						boolean createTile = true;
						clickedATile = false;
						for (Tile t : tiles)
						{
							if (t.getPosition().x == handler.getMousePosition().x - handler.getMousePosition().x % 16 && t.getPosition().y == handler.getMousePosition().y - handler.getMousePosition().y % 16)
							{
								if (isMouseReady)
								{
									System.out.println("clicked a tile");
									clickedATile = true;
									isDeleting = true;
									isMouseReady = false;
								}
								if (isDeleting)
									tiles.remove(t);
								createTile = false;
								break;
							}
						}
						if (!clickedATile && isMouseReady)
						{
							isMouseReady = false;
							isDeleting = false;
							System.out.println("clicked an empty space");
						}

						if (player != null && handler.getMousePosition().x - handler.getMousePosition().x % 16 == player.getPosition().x && handler.getMousePosition().y - handler.getMousePosition().y % 16 == player.getPosition().y)
						{
							player = null;
							createTile = false;
						}
						for (Enemy e : enemies)
						{
							if (e.getPosition().x == handler.getMousePosition().x - handler.getMousePosition().x % 16 && e.getPosition().y == handler.getMousePosition().y - handler.getMousePosition().y % 16)
							{
								enemies.remove(e);
								createTile = false;
								break;
							}
						}
						if (createTile && !isDeleting)
						{
							tiles.add(new Tile(new Vector3f(pos.x, pos.y, 0), new Vector2f(16, 16), tileToPlace));
						}
					}
				}
			}
		}
	}

	/**
	 * Renders the level builder
	 */
	public void render()
	{
		for (Tile t : tiles)
		{
			t.render();
		}
		if (player != null)
		{
			player.render();
		}

		GFX.drawEntireSprite(16, 16, handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16, Textures.highlight);

		GFX.drawEntireSprite(32, 32, Window.width - 32, 0, Textures.testTile);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 32, Textures.grass);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 64, Textures.grassTop);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 96, Textures.dirt2);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 128, Textures.dirt);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 160, Textures.air);

		GFX.drawEntireSprite(32, 32, Window.width - 32, 192, down);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 224, downleft);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 256, downleftright);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 288, downright);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 320, left);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 352, leftright);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 384, leftupright);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 416, right);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 448, rightupdown);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 480, topdown);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 512, up);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 544, updownleftright);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 576, updownright);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 608, upleft);
		GFX.drawEntireSprite(32, 32, Window.width - 32, 640, upright);

		GFX.drawSpriteFromSpriteSheet(32, 32, Window.width - 64, 0, Textures.playerFront, new Vector2f(0, 0), new Vector2f((float) 32 / 512, (float) 32 / 256));

		GFX.drawEntireSprite(32, 32, Window.width - 32, 672, saveLevel);

		GFX.drawSpriteFromSpriteSheet(32, 32, Window.width - 64, 32, Textures.crabman, new Vector2f(0, 0), new Vector2f((float) 64 / 512, (float) 64 / 128));

		GFX.drawEntireSprite(32, 32, Window.width - 64, 64, door);

		if (tileToPlace != null)
		{
			if (tileToPlace == Textures.playerFront)
			{
				GFX.drawSpriteFromSpriteSheet(16, 16, handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16, Textures.playerFront, new Vector2f(0, 0), new Vector2f((float) 32 / 512, (float) 32 / 256));
			} else if (tileToPlace == Textures.crabman)
			{
				GFX.drawSpriteFromSpriteSheet(16, 16, handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16, Textures.crabman, new Vector2f(0, 0), new Vector2f((float) 64 / 512, (float) 64 / 128));
			} else
			{
				GFX.drawEntireSprite(16, 16, handler.getMousePosition().x - handler.getMousePosition().x % 16, handler.getMousePosition().y - handler.getMousePosition().y % 16, tileToPlace);
			}
		}
		for (Enemy e : enemies)
		{
			e.render();
		}
	}

	/**
	 * Generates colliders
	 * 
	 * @return Returns an arraylist of colliders
	 */
	private ArrayList<RectangleBox> generateColliders()
	{

		System.out.println("Generating Colliders...");
		ArrayList<RectangleBox> colliders = new ArrayList<RectangleBox>();
		for (Tile t : tiles)
		{
			Texture tex = t.getTexture();
			if (tex == down || tex == downleft || tex == downleftright || tex == downright || tex == left || tex == leftright || tex == leftupright || tex == right || tex == rightupdown || tex == topdown || tex == up || tex == updownleftright || tex == updownright || tex == upleft || tex == upright
					|| tex == door)
			{
				continue;
			}
			colliders.add(t.getCollider());
		}

		class ColliderComparatorX implements Comparator<RectangleBox>
		{

			@Override
			public int compare(RectangleBox o1, RectangleBox o2)
			{
				int val = (int) (o1.getPosition().y - o2.getPosition().y);
				if (val == 0)
				{
					val = (int) (o1.getPosition().x - o2.getPosition().x);
				}
				return val;
			}

		}
		class ColliderComparatorY implements Comparator<RectangleBox>
		{

			@Override
			public int compare(RectangleBox o1, RectangleBox o2)
			{
				return (int) (o2.getPosition().y - o1.getPosition().y);
			}

		}

		colliders.sort(new ColliderComparatorY());
		colliders.sort(new ColliderComparatorX());

		for (int i = 1; i < colliders.size(); i++)
		{
			RectangleBox currentBox = colliders.get(i);
			RectangleBox previousBox = colliders.get(i - 1);

			if (currentBox.getPosition().y == previousBox.getPosition().y)
			{
				if (currentBox.getPosition().x == previousBox.getPosition().x + previousBox.getSize().x)
				{
					RectangleBox temp = colliders.get(i - 1);
					temp.setSize(new Vector2f(previousBox.getSize().x + currentBox.getSize().x, previousBox.getSize().y));
					colliders.set(i - 1, temp);
					colliders.remove(i);
					i--;
					System.out.println("merged 2 colliders, now starts at " + colliders.get(i).getPosition().x + " and ends at " + (colliders.get(i).getPosition().x + colliders.get(i).getSize().x));
				}
			}
		}

		for (int i = 0; i < colliders.size(); i++)
		{
			colliders.get(i).setSize(new Vector2f(colliders.get(i).getSize().x * 4, colliders.get(i).getSize().y * 4));
		}

		return colliders;
	}

	/**
	 * Saves the level
	 */
	private void saveLevel()
	{
		ArrayList<RectangleBox> colliders = generateColliders();
		int blockSize = 64;

		System.out.println("Saving...");
		try
		{
			File output = new File("./res/generated/gen_" + System.currentTimeMillis() + "_level.ffw");
			PrintWriter writer = new PrintWriter(output);
			writer.println("<LEVEL name=\"" + output.getName() + "\">");
			if (player != null)
			{
				writer.println("<PLAYER x=\"" + (int) player.getPosition().x * 4 + "\" y=\"" + (int) player.getPosition().y * 4 + "\" z=\"" + (int) player.getPosition().z + "\" width=\"" + (int) player.getScale().x + "\" height=\"" + (int) player.getScale().y
						+ "\" tex=\"playerFront\" texOut=\"playerOutline\"/>");
			}
			writer.println("\t<TILES sizex=\"" + blockSize + "\" sizey=\"" + blockSize + "\">");
			for (int i = 0; i < tiles.size(); i++)
			{
				if (tiles.get(i).getTexture() == door)
				{
					writer.println("\t\t<DOOR x=\"" + (int) tiles.get(i).getPosition().x * 4 + "\" y=\"" + (int) tiles.get(i).getPosition().y * 4 + "\"/>");
					tiles.remove(i);
					i--;
				}
			}
			for (Tile t : tiles)
			{
				boolean[] neighbors = new boolean[8];// starts from north
				boolean nwestNeighbor = false;
				boolean northNeighbor = false;
				boolean neastNeighbor = false;
				boolean eastNeighbor = false;
				boolean seastNeighbor = false;
				boolean southNeighbor = false;
				boolean swestNeighbor = false;
				boolean westNeighbor = false;

				for (Tile q : tiles)// reads all tiles for possible neighbors
				{
					if (q.getPosition().x + q.getSize().x == t.getPosition().x)// is west
					{
						if (q.getPosition().y == t.getPosition().y)// is directly west
						{
							westNeighbor = true;
							neighbors[7] = true;
						} else if (q.getPosition().y + q.getSize().y == t.getPosition().y)// is northwest
						{
							nwestNeighbor = true;
							neighbors[0] = true;
						} else if (q.getPosition().y == t.getPosition().y + t.getSize().y)// must be southwest
						{
							swestNeighbor = true;
							neighbors[6] = true;
						}
					} else if (q.getPosition().x == t.getPosition().x + t.getSize().x)// is east
					{
						if (q.getPosition().y == t.getPosition().y)// is directly east
						{
							eastNeighbor = true;
							neighbors[3] = true;
						} else if (q.getPosition().y + q.getSize().y == t.getPosition().y)// is northeast
						{
							neastNeighbor = true;
							neighbors[2] = true;
						} else if (q.getPosition().y == t.getPosition().y + t.getSize().y)// must be southeast
						{
							seastNeighbor = true;
							neighbors[4] = true;
						}
					} else if (q.getPosition().x == t.getPosition().x)// must be above or below this one
					{
						if (q.getPosition().y + q.getSize().y == t.getPosition().y)// is directly north
						{
							northNeighbor = true;
							neighbors[1] = true;
						} else if (q.getPosition().y == t.getPosition().y + t.getSize().y)// is directly south
						{
							southNeighbor = true;
							neighbors[5] = true;
						}
					}
				}
				int count = 0;// the number of effective neighbors
				for (int i = 0; i < neighbors.length; i++)
				{
					if (neighbors[i])
						count++;
				}

				// re-evaluates "pointless" diagonal neighbors (they are only relevant if they are surrounded)
				if (neighbors[0])// upper left diagonal
				{
					if (!neighbors[7] || !neighbors[1])
						count--;
				}
				if (neighbors[2])// upper right diagonal
				{
					if (!neighbors[1] || !neighbors[3])
						count--;
				}
				if (neighbors[4])// lower right diagonal
				{
					if (!neighbors[3] || !neighbors[5])
						count--;
				}
				if (neighbors[6])// lower left diagonal
				{
					if (!neighbors[5] || !neighbors[7])
						count--;
				}

				x_offset = count; // this is which tile to display (0-8)
				switch (count)
				{
					case 0:
						y_offset = 0;
						break;
					case 1:
						if (neighbors[1])
							y_offset = 3;
						else if (neighbors[3])
							y_offset = 0;
						else if (neighbors[5])
							y_offset = 1;
						else if (neighbors[7])
							y_offset = 2;
						break;
					case 2:
						if (neighbors[1] && neighbors[3])
							y_offset = 3;
						else if (neighbors[3] && neighbors[5])
							y_offset = 0;
						else if (neighbors[5] && neighbors[7])
							y_offset = 1;
						else if (neighbors[7] && neighbors[1])
							y_offset = 2;
						else if (neighbors[1] && neighbors[5])
							y_offset = 4;
						else if (neighbors[3] && neighbors[7])
							y_offset = 5;
						break;
					case 3:
						if (neighbors[0] && neighbors[7] && neighbors[1])
							y_offset = 2;
						else if (neighbors[2] && neighbors[1] && neighbors[3])
							y_offset = 3;
						else if (neighbors[4] && neighbors[3] && neighbors[5])
							y_offset = 0;
						else if (neighbors[6] && neighbors[5] && neighbors[7])
							y_offset = 1;
						else if (neighbors[7] && neighbors[3] && neighbors[5])
							y_offset = 4;
						else if (neighbors[1] && neighbors[7] && neighbors[5])
							y_offset = 5;
						else if (neighbors[1] && neighbors[3] && neighbors[5])
							y_offset = 6;
						else if (neighbors[7] && neighbors[1] && neighbors[3])
							y_offset = 7;
						break;
					case 4:
						if (neighbors[7] && neighbors[3] && neighbors[4] && neighbors[5])
							y_offset = 0;
						else if (neighbors[3] && neighbors[5] && neighbors[6] && neighbors[7])
							y_offset = 1;
						else if (neighbors[1] && neighbors[6] && neighbors[7] && neighbors[5])
							y_offset = 2;
						else if (neighbors[5] && neighbors[7] && neighbors[0] && neighbors[1])
							y_offset = 3;
						else if (neighbors[3] && neighbors[7] && neighbors[0] && neighbors[1])
							y_offset = 4;
						else if (neighbors[7] && neighbors[1] && neighbors[2] && neighbors[3])
							y_offset = 5;
						else if (neighbors[1] && neighbors[2] && neighbors[3] && neighbors[5])
							y_offset = 6;
						else if (neighbors[1] && neighbors[3] && neighbors[4] && neighbors[5])
							y_offset = 7;
						else if (neighbors[1] && neighbors[3] && neighbors[5] && neighbors[7])
							y_offset = 8;
						break;
					case 5:
						if (neighbors[3] && neighbors[4] && neighbors[5] && neighbors[6] && neighbors[7])
							y_offset = 0;
						else if (neighbors[0] && neighbors[1] && neighbors[5] && neighbors[6] && neighbors[7])
							y_offset = 1;
						else if (neighbors[7] && neighbors[0] && neighbors[1] && neighbors[2] && neighbors[3])
							y_offset = 2;
						else if (neighbors[1] && neighbors[2] && neighbors[3] && neighbors[4] && neighbors[5])
							y_offset = 3;
						else if (neighbors[1] && neighbors[2] && neighbors[3] && neighbors[5] && neighbors[7])
							y_offset = 4;
						else if (neighbors[1] && neighbors[3] && neighbors[4] && neighbors[5] && neighbors[7])
							y_offset = 5;
						else if (neighbors[7] && neighbors[1] && neighbors[3] && neighbors[5] && neighbors[6])
							y_offset = 6;
						else if (neighbors[0] && neighbors[1] && neighbors[3] && neighbors[5] && neighbors[7])
							y_offset = 7;
						break;
					case 6:
						if (!neighbors[0] && !neighbors[2])
							y_offset = 0;
						else if (!neighbors[2] && !neighbors[4])
							y_offset = 1;
						else if (!neighbors[4] && !neighbors[6])
							y_offset = 2;
						else if (!neighbors[6] && !neighbors[0])
							y_offset = 3;
						else if (!neighbors[0] && !neighbors[4])
							y_offset = 4;
						else if (!neighbors[2] && !neighbors[6])
							y_offset = 5;
						break;
					case 7:
						if (!neighbors[0])
							y_offset = 3;
						if (!neighbors[2])
							y_offset = 0;
						if (!neighbors[4])
							y_offset = 1;
						if (!neighbors[6])
							y_offset = 2;
						break;
					case 8:
						y_offset = 0;
						break;
				}

				String textureName = "";
				Texture tex = t.getTexture();
				if (tex == Textures.testTile)// max height is 9 tiles, so for additional tilesheets you'll need to add 9 to get to the start of the next
				{
					textureName = "tilesheet";
					y_offset += 0;
				}
				// TODO add more of these as more tiles are made

				float vectorX = (float) (64 * x_offset) / 1024f;// these are the size of each sprite and size of the sheet itself
				float vectorY = (float) (64 * y_offset) / 1024f;

				writer.println("\t\t<TILE x=\"" + (int) t.getPosition().x * 4 + "\" y=\"" + (int) t.getPosition().y * 4 + "\" z=\"" + (int) t.getPosition().z + "\" texName=\"" + textureName + "\" texCoordX=\"" + vectorX + "\" texCoordY=\"" + vectorY + "\"/>");
			}
			writer.println("\t</TILES>");
			writer.println("\t<COLLIDERS>");
			for (RectangleBox box : colliders)
			{
				writer.println("\t<COLLIDER x=\"" + (int) box.getPosition().x * 4 + "\" y=\"" + (int) box.getPosition().y * 4 + "\" z=\"" + (int) box.getPosition().z + "\" width=\"" + (int) box.getSize().x + "\" height=\"" + (int) box.getSize().y + "\"/>");
			}
			writer.println("\t</COLLIDERS>");

			writer.println("\t<ENEMIES>");
			for (Enemy e : enemies)
			{
				String textureName = "";
				String outlineName = "";
				if (e.getTexture() == Textures.crabman)
				{
					textureName = "crabMan";
					outlineName = "crabMan";
				}
				writer.println(
						"\t\t<ENEMY x=\"" + (int) e.getPosition().x + "\" y=\"" + (int) e.getPosition().y + "\" z=\"" + (int) e.getPosition().z + "\" width=\"" + (int) e.getScale().x + "\" height=\"" + (int) e.getScale().y + "\" texName=\"" + textureName + "\" outlineName=\"" + outlineName + "\"");
			}

			writer.println("\t</ENEMIES>");
			
			for(Tile t : tiles)
			{
				t.setPosition(new Vector3f(t.getPosition().x * 4,t.getPosition().y * 4,t.getPosition().z));
				t.setSize(new Vector2f(t.getSize().x * 4,t.getSize().y * 4));
			}
			ArrayList<Tile> sortedTiles = World.sortTiles(tiles);
			//Generate path graph
			ArrayList<Tile> ti = new ArrayList<Tile>();
			
			//Gathers list of tiles that have nothing above them
			for(Tile t : sortedTiles)
			{
				boolean isAbleToBeWalkedOn = true;
				for(Tile t1: sortedTiles)
				{
					if(t.getPosition().x == t1.getPosition().x && t.getPosition().y == t1.getPosition().y + 64)
					{
						isAbleToBeWalkedOn = false;
						break;
					}
				}
				if(isAbleToBeWalkedOn)
				{
					ti.add(t);
				}
			}
			
			//Only uses the list of tiles that have nothing immidiately above them to check for nodes
			ArrayList<Vertex> vertices = new ArrayList<Vertex>();
			int counter = 0;
			for(Tile t : ti)
			{
				System.out.println("Processing Tile: " + t + " number: " + (counter++) + "/" + ti.size());
				Vertex v = new Vertex(t);
				for(Tile tt : ti)
				{
					boolean blockTop = false;
					for(Tile ttt : ti)
					{
						if(ttt.getPosition().x == t.getPosition().x && ttt.getPosition().y + ttt.getSize().y == t.getPosition().y) // Means there is a block ontop of t
						{
							blockTop = true;
							break;
						}
					}
					if(blockTop)
					{
						continue;
					}
					//System.out.println("\tChecking tile: " + tt);
					if((t.getPosition().x + t.getSize().x == tt.getPosition().x && tt.getPosition().y == tt.getPosition().y) || (tt.getPosition().x + tt.getSize().x == t.getPosition().x && tt.getPosition().y == tt.getPosition().y)) // This means tt is to the right of t || tt is to the left of t
					{
						boolean blockOnTop = false;
						for(Tile ttt : ti)
						{
							if(ttt.getPosition().x == tt.getPosition().x && ttt.getPosition().y + ttt.getSize().y == tt.getPosition().y) // Means there is a block ontop of tt
							{
								blockOnTop = true;
								break;
							}
						}
						if(!blockOnTop)
						{
							Vertex vT = Utils.fileTileVertexInVertices(t, vertices);
							Vertex vTT = Utils.fileTileVertexInVertices(tt, vertices);
							if(vT == null)
							{
								vT = new Vertex(t);
								vertices.add(vT);
							}
							if(vTT == null)
							{
								vTT = new Vertex(tt);
								vertices.add(vTT);
							}
							Edge edge = new Edge(vT,vTT,10);
							v.addEdge(edge);
							continue;
						}
					}
					// For each vertex check if a parabolic curve can make it 
					
					//Constant Acceleration
					float gravity = Entity.GRAVITY;
					float horizontalVelocity = Entity.MAX_SPEED_X;
					float time = 0;
					float initialY = t.getPosition().y - t.getSize().y;
					float initialX = t.getPosition().x;
					
					float yPos = initialY + ((0.5f) * gravity * time);
					float xPos = initialX + (horizontalVelocity * time);
					int direction = 1;
					if(tt.getPosition().x < t.getPosition().x)
					{
						direction = -1;
					}
					RectangleBox box = new RectangleBox(new Vector3f(xPos,yPos,0),new Vector2f(t.getSize().x,t.getSize().y));
					while(time < 100000)
					{
						yPos = initialY + ((0.5f) * gravity * time);
						xPos = initialX + ((horizontalVelocity * time) * direction);
						box.getPosition().x = xPos;
						box.getPosition().y = yPos;
						boolean foundSolution = false;
						for(Tile ttt : ti)
						{
							
							//System.out.println("For that tile checking tile: " + ttt + " Time: " + time);
							if(box.isCollidingWithBox(ttt.getCollider()))
							{
								if(ttt == tt) // Found destination tile
								{
									//create edge
									int deltaX = Math.abs(((int)(ttt.getPosition().x - tt.getPosition().x))/(int)t.getSize().x);
									int deltaY = Math.abs(((int)(ttt.getPosition().y - tt.getPosition().y))/(int)t.getSize().y);
									
									int weight = 0;
									while(deltaX > 0 && deltaY > 0) // Account for diagonal cost
									{
										weight += 14;
										deltaX--;
										deltaY--;
									}
									while(deltaX > 0) // Account for horizontal cost
									{
										weight += 10;
										deltaX--;
									}
									while(deltaY > 0) // Account for vertical cost
									{
										weight += 10;
										deltaY--;
									}
									if(weight == 0)
									{
										break;
									}
									Vertex vTT = Utils.fileTileVertexInVertices(t, vertices);
									Vertex vTTT = Utils.fileTileVertexInVertices(tt, vertices);
									if(vTT == null)
									{
										vTT = new Vertex(tt);
										vertices.add(vTT);
									}
									if(vTTT == null)
									{
										vTTT = new Vertex(ttt);
										vertices.add(vTTT);
									}									
									Edge edge = new Edge(vTT,vTTT,weight);
									v.addEdge(edge);
									foundSolution = true;
									break;
								}
								else if(ttt == t) // Found source tile
								{
									continue;
								}
								else // No direct path exists
								{
									foundSolution = true;
									break;
								}
							}
							
						}
						if(foundSolution)
						{
							break;
						}
						time++;
					}
					
				}
				vertices.add(v);
			}
			
			for(Vertex v : vertices)
			{
				System.out.println(v);
				writer.println("\t<VERTEX x=\"" + (int)v.getTile().getPosition().x +"\" y=\"" + (int)v.getTile().getPosition().y + "\">");
				
				for(Edge e: v.getEdges())
				{
					writer.println("\t\t<EDGE D x=\"" + (int)e.getDestination().getTile().getPosition().x + "\" y=\"" + (int)e.getDestination().getTile().getPosition().y + "\" weight=\"" + (int)e.getWeight() + "\"/>");
				}
				
				writer.println("\t</VERTEX>");
			}
			
			writer.println("</LEVEL>");
			writer.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		readyAfterClickingSave = true;
	}
}