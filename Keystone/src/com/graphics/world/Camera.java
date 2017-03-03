package com.graphics.world;

import org.lwjgl.util.vector.Vector2f;

/**
 * Handles the camera movement
 * 
 * @author Craig Ferris
 *
 */
public class Camera
{
	private Vector2f	position;
	private Vector2f	size;

	private Vector2f	offset;
	private Vector2f	previousPosition;

	private Vector2f	totalCameraOffset;
	private Vector2f	startPosition;

	private float		movementSpeed;

	/**
	 * Creates a new camera
	 * 
	 * @param position
	 *            The position of the top left corner of the camera
	 * @param size
	 *            The size of the camera
	 */
	public Camera(Vector2f position, Vector2f size)
	{
		this.position = position;
		this.size = size;
		movementSpeed = 9;

		previousPosition = new Vector2f(position.x, position.y);
		offset = new Vector2f(0, 0);
		totalCameraOffset = new Vector2f(0, 0);
		startPosition = new Vector2f(position.x, position.y);
	}

	/**
	 * Sets the cameras position to be around the player
	 * 
	 * @param entity
	 *            The entity to set the camera around
	 * @param width
	 *            The width of the screen
	 * @param height
	 *            The height of the screen
	 */
	public void setPositionToPlayer(Entity entity, int width, int height)
	{
		float playerPositionX = entity.getPosition().x;
		float playerPositionY = entity.getPosition().y;

		previousPosition.x = position.x;
		previousPosition.y = position.y;

		float halfMarkX = playerPositionX + (entity.getScale().x / 2);
		float halfMarkY = playerPositionY + (entity.getScale().y / 2);

		float newCameraPositionX = halfMarkX - (width / 2);
		float newCameraPositionY = halfMarkY - (height / 2);
		position.x = newCameraPositionX;
		position.y = newCameraPositionY;

		offset.x = position.x - previousPosition.x;
		offset.y = position.y - previousPosition.y;
	}

	/**
	 * Updates the camera
	 */
	public void update()
	{
		offset.x = position.x - previousPosition.x;
		offset.y = position.y - previousPosition.y;
		totalCameraOffset.x = position.x - startPosition.x;
		totalCameraOffset.y = position.y - startPosition.y;
	}

	/**
	 * Moves the camera right
	 */
	public void moveRight()
	{
		position.x += movementSpeed;
	}

	/**
	 * Moves the camera left
	 */
	public void moveLeft()
	{
		position.x -= movementSpeed;
	}

	/**
	 * Moves the camera up
	 */
	public void moveUp()
	{
		position.y += movementSpeed;
	}

	/**
	 * Moves the camera down
	 */
	public void moveDown()
	{
		position.y -= movementSpeed;
	}

	/**
	 * Returns the position of the top left corner camera
	 * 
	 * @return Returns the position of the top left corner of the camera
	 */
	public Vector2f getPosition()
	{
		return position;
	}

	/**
	 * Sets the position of the top left corner of the camera
	 * 
	 * @param position
	 *            The position of the top left corner of the camera
	 */
	public void setPosition(Vector2f position)
	{
		this.position = position;
	}

	/**
	 * Returns the size of the camera
	 * 
	 * @return Returns the size of the camera
	 */
	public Vector2f getSize()
	{
		return size;
	}

	/**
	 * Sets the size of the camera
	 * 
	 * @param size
	 *            The size of the camera
	 */
	public void setSize(Vector2f size)
	{
		this.size = size;
	}

	/**
	 * Returns the offset of the camera relative to it's previous position
	 * 
	 * @return Returns the offset of the camera relative to it's previous position
	 */
	public Vector2f getOffset()
	{
		return offset;
	}

	/**
	 * Sets the offset of the camera relative to it's previous position
	 * 
	 * @param offset
	 *            The offset relative to it's previous position
	 */
	public void setOffset(Vector2f offset)
	{
		this.offset = offset;
	}

	/**
	 * Returns the previous position of the camera
	 * 
	 * @return Returns the previous position of the camera
	 */
	public Vector2f getPreviousPosition()
	{
		return previousPosition;
	}

	/**
	 * Sets the previous position of the camera
	 * 
	 * @param previousPosition
	 *            The previous position of the camera
	 */
	public void setPreviousPosition(Vector2f previousPosition)
	{
		this.previousPosition = previousPosition;
	}

	/**
	 * Returns the offset of the camera from the start position
	 * 
	 * @return Returns the offset of the camera from the start position
	 */
	public Vector2f getTotalCameraOffset()
	{
		return totalCameraOffset;
	}

	/**
	 * Sets the offset of the camera from the start position
	 * 
	 * @param totalCameraOffset
	 *            The offset of the camera from the start position
	 */
	public void setTotalCameraOffset(Vector2f totalCameraOffset)
	{
		this.totalCameraOffset = totalCameraOffset;
	}

	/**
	 * Returns the start position of the camera
	 * 
	 * @return Returns the start position of the camera
	 */
	public Vector2f getStartPosition()
	{
		return startPosition;
	}

	/**
	 * Sets the start position of the camera
	 * 
	 * @param startPosition
	 *            The start position of the camera
	 */
	public void setStartPosition(Vector2f startPosition)
	{
		this.startPosition = startPosition;
	}

	/**
	 * Returns the movement speed of the camera
	 * 
	 * @return Returns the movement speed of the camera
	 */
	public float getMovementSpeed()
	{
		return movementSpeed;
	}

	/**
	 * Sets the movement speed
	 * 
	 * @param movementSpeed
	 *            The new movement speed
	 */
	public void setMovementSpeed(float movementSpeed)
	{
		this.movementSpeed = movementSpeed;
	}
}
