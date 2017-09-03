package com.graphics.world.projectile;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.GFX;
import com.graphics.Textures;
import com.graphics.world.Entity;
import com.graphics.world.Particle;
import com.graphics.world.RectangleBox;
import com.main.Game;

/**
 * Basic Projectile class
 * 
 * @author Craig Ferris
 *
 */
public class Projectile extends Entity
{
	// private float offsetX = 0;

	private int	damage	= 10;
	private int	lifespan;		// the total time projectile remains
	private int	lifetime;		// the time elapsed

	/**
	 * Creates a new projectile
	 * 
	 * @param position
	 *            The position of the projectile
	 * @param texture
	 *            The texture of the projectile
	 * @param scale
	 *            The size at which to draw the sprite
	 * @param spriteSize
	 *            The size of the sprite
	 * @param velocity
	 *            The direction and speed of the projectile
	 */
	public Projectile(Vector3f position, Texture texture, Vector2f scale, Vector2f spriteSize, float angle, float speed)
	{
		super(new Vector3f(position.x - (spriteSize.x * Game.SCALE / 2), position.y - (spriteSize.y * Game.SCALE / 2), position.z), texture, spriteSize);
		lifespan = 30;
		lifetime = 0;
	}

	/**
	 * Creates a new projectile
	 * 
	 * @param position
	 *            The position of the projectile
	 * @param texture
	 *            The texture of the projectle
	 * @param numberOfFrames
	 *            The number of sprite animation frames
	 * @param row
	 *            The specific row of frames to animate, calculated as a multiple of the height of the sprite
	 * @param spriteSize
	 *            The size of the sprite
	 * @param velocity
	 *            the direction and speed of the projectile
	 */
	public Projectile(Vector3f position, Texture texture, int numberOfFrames, int row, Vector2f spriteSize, Vector2f velocity)
	{
		super(new Vector3f(position.x - (spriteSize.x * Game.SCALE / 2), position.y - (spriteSize.y * Game.SCALE / 2), position.z), texture, texture, numberOfFrames, row, spriteSize);
		lifespan = 30;
		lifetime = 0;
		this.velocity = new Vector3f(velocity.x, velocity.y, 0);
	}

	/**
	 * Updates the projectile
	 * 
	 * @param colliders
	 *            The colliders in the world to check for collisions with
	 */
	public void update(ArrayList<RectangleBox> colliders)
	{
		if (animateTime >= 2)
		{
			animSpriteFrameX++;
			if (animSpriteFrameX >= numberOfFrames)
			{
				animSpriteFrameX = 0;
			}
			animateTime = 0.0f;
		} else
		{
			animateTime += animateSpeed;
		}

		if (affectedByGravity)
		{
			velocity.y += GRAVITY;
		}
		if (velocity.y > MAX_SPEED_Y)
		{
			velocity.y = MAX_SPEED_Y;
		} else if (velocity.y < -MAX_SPEED_Y)
		{
			velocity.y = -MAX_SPEED_Y;
		}
		for (RectangleBox t : colliders)
		{
			if (t.isCollidingWithBox(collider))
			{
				for (int i = 0; i < 18; i++)// small fire
				{
					particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 8, 5, true, new Vector2f(16, 16), false, new Vector2f(velocity.x / 6, velocity.y / 6), new Vector2f(5f, 5f), new Vector2f(10f, 10f), 4));// small fire
				}
				for (int i = 0; i < 10; i++)// big fire
				{
					particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 10, 4, true, new Vector2f(16, 16), false, new Vector2f(velocity.x / 6, velocity.y / 6), new Vector2f(10f, 10f), new Vector2f(3f, 3f), 4));// big fire
				}
				for (int i = 0; i < 10; i++)// sparks
				{
					particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 16, 7, true, new Vector2f(16, 16), false, new Vector2f(velocity.x / 6, velocity.y / 6), new Vector2f(16f, 16f), new Vector2f(3f, 3f), 4));// big fire
				}
				super.setDead(true);
			}
		}
		lifetime++;
		if (lifetime >= lifespan)
		{
			for (int i = 0; i < 18; i++)// small fire
			{
				particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 8, 5, true, new Vector2f(16, 16), false, new Vector2f(velocity.x / 6, velocity.y / 6), new Vector2f(5f, 5f), new Vector2f(10f, 10f), 4));// small fire
			}
			for (int i = 0; i < 10; i++)// big fire
			{
				particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 10, 4, true, new Vector2f(16, 16), false, new Vector2f(velocity.x / 6, velocity.y / 6), new Vector2f(10f, 10f), new Vector2f(3f, 3f), 4));// big fire
			}
			for (int i = 0; i < 10; i++)// sparks
			{
				particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 16, 7, true, new Vector2f(16, 16), false, new Vector2f(velocity.x / 6, velocity.y / 6), new Vector2f(16f, 16f), new Vector2f(3f, 3f), 4));// big fire
			}
			super.setDead(true);
		}
		collider.setPosition(new Vector3f(velocity.x + position.x, velocity.y + position.y, position.z));
		position.x = collider.getPosition().x;
		position.y = collider.getPosition().y;

		// if (angle == 180)
		// {
		// offsetX += (float) ((super.getSpriteSize().x - velocity.x) / super.getSpriteSize().x);
		// } else
		// {
		// offsetX += (float) ((super.getSpriteSize().x - Math.abs(velocity.x)) /
		// super.getSpriteSize().x);
		// }
		// if (angle == 90)
		// {
		// offsetX += (float) ((super.getSpriteSize().x - Math.abs(velocity.y)) /
		// super.getSpriteSize().x);
		// } else if (angle == 270)
		// {
		// offsetX += (float) ((super.getSpriteSize().x - velocity.y) / super.getSpriteSize().x);
		// }

		// the smoke and fire effects generated by the fireball

		// particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 14, 3, true, new Vector2f(16, 16), new Vector2f(256, 128), false, new Vector2f(0, 0), 5f, 5f, .5f, .5f));//smoke
		// particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 14, 3, true, new Vector2f(16, 16), new Vector2f(256, 128), false, new Vector2f(0, 0), 5f, 5f, .5f, .5f));//smoke
		particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 10, 4, true, new Vector2f(16, 16), false, new Vector2f(0, 0), new Vector2f(5f, 5f), new Vector2f(.5f, .5f), 4));// big fire
		particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 10, 4, false, new Vector2f(16, 16), false, new Vector2f(0, 0), new Vector2f(5f, 5f), new Vector2f(.5f, .5f), 4));// big fire
		particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 8, 5, true, new Vector2f(16, 16), false, new Vector2f(0, 0), new Vector2f(5f, 5f), new Vector2f(1f, 1f), 2));// small fire
		// particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 10, 6, true, new Vector2f(16, 16), new Vector2f(256, 128), false, new Vector2f(velocity.x / 4, velocity.y / 4), 5f, 5f, .5f, .5f));
		particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 16, 7, true, new Vector2f(16, 16), false, new Vector2f(0, 0), new Vector2f(5f, 5f), new Vector2f(1.5f, 1.5f), 2));// sparks
		particles.add(new Particle(new Vector2f(position.x, position.y), new Vector2f(16, 16), Textures.particles, 16, 7, true, new Vector2f(16, 16), false, new Vector2f(0, 0), new Vector2f(5f, 5f), new Vector2f(1f, 1f), 2));// sparks
	}

	/**
	 * Returns the amount of damage it does
	 * 
	 * @return Returns the amount of damage it does
	 */
	public int getDamage()
	{
		return damage;
	}

	/**
	 * Sets the amount of damage it does
	 * 
	 * @param damage
	 *            The amount of damage it does
	 */
	public void setDamage(int damage)
	{
		this.damage = damage;
	}

	/**
	 * Renders the projectile
	 */
	public void render()
	{
		if (numberOfFrames == 1)
		{
			GFX.drawEntireSprite(super.getSpriteSize().x * Game.SCALE, super.getSpriteSize().y * Game.SCALE, position.x, position.y, super.getTexture());
		} else
		{
			Vector2f offset = new Vector2f(((float) (super.getSpriteSize().x * animSpriteFrameX)) / super.getSizeOfSpriteSheet().x, (float) (super.getSpriteSize().y * row) / super.getSizeOfSpriteSheet().y);
			Vector2f sizey = new Vector2f((float) (super.getSpriteSize().x / super.getSizeOfSpriteSheet().x), (float) (super.getSpriteSize().y / super.getSizeOfSpriteSheet().y));
			if (velocity.x < 0 || left)
			{
				GFX.drawSpriteFromSpriteSheetInverse(super.getSpriteSize().x * Game.SCALE, super.getSpriteSize().y * Game.SCALE, position.x, position.y, super.getTexture(), offset, sizey);
			} else
			{
				GFX.drawSpriteFromSpriteSheet(super.getSpriteSize().x * Game.SCALE, super.getSpriteSize().y * Game.SCALE, position.x, position.y, super.getTexture(), offset, sizey);
			}
		}
		// GFX.drawSpriteFromSpriteSheetAtAngle(super.getSpriteSize().x, super.getSpriteSize().y, super.position.x,
		// super.position.y, super.getTexture(), new Vector2f(-offsetX, (float) (super.getSpriteSize().y *
		// numberOfSpritesY) / getSizeOfSpriteSheet().y), new Vector2f(1f, (float) 1 / 8), angle);
	}

}
