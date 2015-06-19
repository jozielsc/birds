package com.jozielsc.game.airplane.objects;

import com.jozielsc.g4a.math.Vector2;

public class DynamicObject extends GameObject{
    public final Vector2 velocity;
    public final Vector2 accel;
    
    public DynamicObject(float x, float y, float width, float height) {
        super(x, y, width, height);
        velocity = new Vector2();
        accel = new Vector2();
    }
    
}
