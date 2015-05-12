package com.ernestas.renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

public class DisplayManager {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int FPS = 120;
    public static final String TITLE = "PROJECT 3D";

    public static void createDisplay(){
        ContextAttribs attribs = new ContextAttribs(3, 2)
            .withForwardCompatible(true)
            .withProfileCore(true)
        ;
        
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat(), attribs);
            Display.setTitle(TITLE);
            Display.setLocation(0, 0);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        GL11.glViewport(0, 0, WIDTH, HEIGHT);
    }

    public static void updateDisplay() {
        Display.sync(FPS);
        Display.update();
    }

    public static void closeDisplay() {
        Display.destroy();
    }

}
