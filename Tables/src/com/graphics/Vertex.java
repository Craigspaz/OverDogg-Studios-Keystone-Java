package com.graphics;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Written by TheBennyBox on youtube
 *
 */
public class Vertex
{
	public static final int SIZE = 5;
	
	private Vector3f pos;
	private Vector2f texCoord;
	
	public Vertex(Vector3f pos)
	{
		this(pos, new Vector2f(0,0));
	}
	
	public Vertex(Vector3f pos, Vector2f texCoord)
	{
		this.pos = pos;
		this.texCoord = texCoord;
	}

	public Vector3f getPos()
	{
		return pos;
	}

	public void setPos(Vector3f pos)
	{
		this.pos = pos;
	}

	public Vector2f getTexCoord()
	{
		return texCoord;
	}

	public void setTexCoord(Vector2f texCoord)
	{
		this.texCoord = texCoord;
	}

	public static int getSize()
	{
		return SIZE;
	}
	
}
