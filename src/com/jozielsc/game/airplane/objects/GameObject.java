package com.jozielsc.game.airplane.objects;

import com.jozielsc.g4a.math.Circle;
import com.jozielsc.g4a.math.Rectangle;
import com.jozielsc.g4a.math.Vector2;

public class GameObject {

	public final Vector2 position;
	public final Rectangle bounds;
	public final Circle boundsCircle;
	public GameObject(float x, float y, float width, float height) {
		this.position = new Vector2(x, y);
		this.bounds = new Rectangle(x, y, width, height);
		this.boundsCircle = new Circle(x, y, ((width / 2)+(height / 2)) / 2);
	}
}