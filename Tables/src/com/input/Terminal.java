package com.input;

import java.util.ArrayList;

import com.graphics.GFX;
import com.graphics.Textures;
import com.graphics.world.Camera;
import com.graphics.world.Player;
import com.graphics.world.enemys.Enemy;
import com.main.Game;

/**
 * Handles the dev console
 * 
 * @author Kyle Falicov
 *
 */
public class Terminal
{
	private boolean				active;
	private InputHandler		handler;
	private boolean				canToggle		= true;
	private boolean				canEnter		= true;
	private String				command			= "";
	private ArrayList<String>	messages;
	int							lineheight;

	private Player				player;
	private Camera				camera;
	private Game				game;

	private static int			centerScreenX	= 666;
	private static int			centerScreenY	= 366;

	/**
	 * Creates a new terminal
	 * 
	 * @param handler
	 *            A pointer to the input handler
	 * @param player
	 *            A pointer to the player
	 * @param camera
	 *            A pointer to the camera
	 */
	public Terminal(InputHandler handler, Player player, Camera camera, Game game)
	{
		this.handler = handler;
		messages = new ArrayList<String>(5);
		this.player = player;
		this.camera = camera;
		this.game = game;
	}

	/**
	 * used to determine in Game whether to call update on things
	 * 
	 * @return true if terminal is up
	 */
	public boolean active()
	{
		return this.active;
	}

	/**
	 * handles command inputs and what is currently typed in the buffer
	 */
	public void update()
	{
		if (active)
		{

			if (handler.enter() && canEnter && !command.isEmpty())
			{
				read(command);
				handler.setPrevious(command);
				command = "";
				handler.clearBuffer();
				canEnter = false;
			} else if (handler.escape())
			{
				active = false;
				command = "";
			} else
			{
				command = handler.getCommand();
			}
		} else
		{
			handler.clearBuffer();
		}
		if (handler.tildedown() && canToggle)
		{
			command = "";
			handler.clearBuffer();
			active = !active;
			canToggle = false;
		}
		if (!handler.tildedown())
		{
			canToggle = true;
		}
		if (!handler.enter())
		{
			canEnter = true;
		}
	}

	/**
	 * draws the terminal box and the current typed text, plus 5 additional lines of previous input and output
	 * 
	 * @param x
	 *            the coordinate of the camera
	 * @param y
	 *            the coordinate of the camera
	 */
	public void render(float x, float y)
	{
		lineheight = GFX.font2.getLineHeight();
		if (active)
		{
			GFX.drawEntireSprite(512, 128, x, y - 128, Textures.terminalWindow);
			for (int i = 0; i < messages.size(); i++)
			{
				GFX.drawString(x + 10, (y - 2 * lineheight) - (lineheight * i) - 11, messages.get(i));
			}
			GFX.drawString(x + 10, y - lineheight - 10, "> " + command);
		}
	}

	/**
	 * interprets the entered command
	 * 
	 * @param cmd
	 *            the entered command
	 */
	private void read(String cmd)
	{
		String[] commands = cmd.split(" ");
		if (commands.length == 0)
		{
			messages.add(0, "please enter a command");
		} else
		{
			messages.add(0, "> " + cmd);
			if (commands[0].equals("nodamage"))// toggles invulnerability, takes no arguments
			{
				player.setImmune(!player.isImmune());
				messages.add(0, "nodamage is now " + player.isImmune());
			} else if (commands[0].equals("spawn"))// spawns the specified entity at the specified coordinates. Requires
													// valid integers and a valid entity name.
			{
				if (commands.length >= 4)
				{
					int x, y;
					try
					{
						if (commands[2].equals("."))
						{
							x = centerScreenX;
						} else
						{
							x = Integer.parseInt(commands[2]);
						}
						if (commands[3].equals("."))
						{
							y = centerScreenY;
						} else
						{
							y = Integer.parseInt(commands[3]);
						}
					} catch (NumberFormatException | NullPointerException e)
					{
						messages.add(0, "failed to read integers");
						return;
					}

					Enemy ee = Enemy.generateBasicEnemyBasedOnID(commands[1].toLowerCase(), x + camera.getPosition().x, y + camera.getPosition().y);
					if (ee == null)
					{
						messages.add(0, "requested entity does not exist");
						return;
					} else
					{
						ee.setHostileToPlayer(true);
						game.addEnemy(ee);
					}
					// TODO create enemy with ID, X, and Y coords
					messages.add(0, "spawned " + commands[1].toLowerCase() + " at " + x + ", " + y);
				} else
				{
					messages.add(0, "invalid arguments");
				}
			} else if (commands[0].equals("load"))// loads a level TODO broken with scaling for some reason
			{
				if (commands.length >= 2)
				{
					if (game.loadNewLevel("./res/world/" + commands[1] + ".ffw"))
					{
						messages.add(0, "loaded " + commands[1]);
					} else
					{
						messages.add(0, "could not load level");
					}

				} else
				{
					messages.add(0, "invalid arguments");
				}
			} else if (commands[0].equals("scale"))
			{
				if (commands.length >= 2)
				{
					try
					{
						if (commands[1].equals("reset"))
						{
							Game.setScale(1f);
						} else
						{
							Game.setScale(Float.parseFloat(commands[1]));
						}
					} catch (NumberFormatException e)
					{
						messages.add(0, "usage: \"> scale [float]\" or \"> scale reset\"");
					}
				}
			} else if (commands[0].equals("hello"))// a silly test command, we're keeping this in
			{
				messages.add(0, "hello :)");
			} else if (commands[0].equals("help"))// gives information on other commands
			{
				messages.add(0, "nodamage: toggles whether player can receive damage");
				messages.add(0, "spawn [ID] [x] [y]: spawns entity at x, y from camera corner");
				messages.add(0, "scale [SCALE]: changes the game's display scale to SCALE");
			} else
			{
				// messages.add(0, "unknown command");
			}

		}
		while (messages.size() > 5)// determines which messages to draw on screen
		{
			messages.remove(messages.size() - 1);
		}
	}
}
