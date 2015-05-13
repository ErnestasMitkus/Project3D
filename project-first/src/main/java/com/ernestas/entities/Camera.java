package com.ernestas.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private Vector3f position = new Vector3f(100, 30, 50);
    private float pitch = 15f;    //How high or low the camera is aimed
    private float yaw;      //How much left or right the camera is aiming
    private float roll;     //Camera rotation. 180 - upside down

    public Camera() {
    }

    public void move() {
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}
